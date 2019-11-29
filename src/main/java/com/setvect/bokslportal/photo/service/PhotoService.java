package com.setvect.bokslportal.photo.service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.BokslPortalConstant;
import com.setvect.bokslportal.photo.repository.PhotoRepository;
import com.setvect.bokslportal.photo.vo.PhotoVo;
import com.setvect.bokslportal.photo.vo.PhotoVo.ShotDateType;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Log4j2
public class PhotoService {
  @Autowired
  private PhotoRepository photoRepository;

  /**
   * 업로드 디렉토리 존재 여부를 검사하여 없으면 만듦.
   */
  public void createUploadDir() {
    if (!BokslPortalConstant.Photo.BASE_DIR.exists()) {
      BokslPortalConstant.Photo.BASE_DIR.mkdirs();
    }
  }

  /**
   * 사진을 저장
   *
   * @param imageFile
   */
  public void addPhoto(File imageFile) {
    File baseFile = BokslPortalConstant.Photo.BASE_DIR;

    File dirFile = imageFile.getParentFile();
    String dir = ApplicationUtil.getRelativePath(baseFile, dirFile);
    dir = "/" + dir;

    if (!imageFile.exists()) {
      log.warn("Skip. Image not exist.({})", imageFile.getAbsolutePath());
      return;
    }

    PhotoVo photo = new PhotoVo();
    Date shotDate = getShotDate(imageFile);
    String photoId = ApplicationUtil.getMd5(imageFile);

    photoRepository.findById(photoId).ifPresent((beforePhoto) -> {
      File beforeFile = beforePhoto.getFullPath();
      log.info("Already have the same file. ({})", beforeFile.getAbsolutePath());
      // 업로드된 이미지와 기존 저장된 이미지가 다르면 기존 이미지 삭제
      if (!imageFile.equals(beforeFile)) {
        deleteImageFile(beforeFile);
      }
    });

    photo.setPhotoId(photoId);
    photo.setDirectory(dir);
    photo.setName(imageFile.getName());
    photo.setShotDate(shotDate);
    photo.setShotDateType(shotDate != null ? ShotDateType.META : ShotDateType.MANUAL);
    photo.setRegData(new Date());

    try {
      Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
      GeoCoordinates geo = getGeo(metadata);
      if (geo != null) {
        photo.setLatitude(geo.getLatitude());
        photo.setLongitude(geo.getLongitude());
      }
      int orientationValue = getOrientation(metadata);
      photo.setOrientation(orientationValue);
    } catch (ImageProcessingException | IOException e) {
      log.error(e.getMessage() + ": " + imageFile.getAbsolutePath(), e);
    }
    photoRepository.save(photo);
  }

  /**
   * @param metadata 이미지 메타 테그
   * @return 회원 정보. 추출하지 못하면 0을 반환
   */
  private int getOrientation(final Metadata metadata) {
    Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
    if (directory != null) {
      try {
        int result = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
        return result;
      } catch (MetadataException e) {
        log.warn("fail extract TAG_ORIENTATION.");
        return 0;
      }
    }
    return 0;
  }

  /**
   * 메타 정보
   *
   * @param imageFile 이미지 파일
   * @return Key: 메타 이름, Value: 값
   */
  public static Map<String, String> getImageMeta(final File imageFile) {
    try {
      Metadata metadata = ImageMetadataReader.readMetadata(imageFile);

      Map<String, String> result = StreamSupport.stream(metadata.getDirectories().spliterator(), false)
        .flatMap(p -> p.getTags().stream()).filter(Objects::nonNull).collect(Collectors.toMap(p -> {
          return "[" + p.getDirectoryName() + "]" + p.getTagName();
        }, p -> {
          return p.getDescription() == null ? "" : p.getDescription();
        }, (v1, v2) -> v1, TreeMap::new));

      return result;
    } catch (Exception e) {
      log.error(e.getMessage() + ": " + imageFile.getAbsolutePath(), e);
      return null;
    }
  }

  /**
   * GEO 좌표
   *
   * @param metadata 이미지 파일
   * @return GEO 좌표
   */
  private static GeoCoordinates getGeo(final Metadata metadata) {
    GpsDirectory meta = metadata.getFirstDirectoryOfType(GpsDirectory.class);

    if (meta == null) {
      return null;
    }
    Pattern regex = Pattern.compile(BokslPortalConstant.Photo.GPS_REGEX);

    // 37° 28' 46.12"
    Map<String, Double> geo = meta.getTags().stream()
      .filter(tag -> tag.getTagName().equals(BokslPortalConstant.Photo.GPS_LATITUDE)
        || tag.getTagName().equals(BokslPortalConstant.Photo.GPS_LONGITUDE))
      .filter(tag -> tag.getDescription() != null).collect(Collectors.toMap(p -> p.getTagName(), p -> {
        String coordinates = p.getDescription();
        Matcher matcher = regex.matcher(coordinates);
        if (!matcher.find()) {
          return null;
        }

        double degree = Double.parseDouble(matcher.group(1));
        double minutes = Double.parseDouble(matcher.group(2));
        double seconds = Double.parseDouble(matcher.group(3));
        double value = degree + minutes / 60 + seconds / 3600;
        return value;
      }));
    if (geo.size() == 2) {
      Double latitude = geo.get(BokslPortalConstant.Photo.GPS_LATITUDE);
      Double longitude = geo.get(BokslPortalConstant.Photo.GPS_LONGITUDE);

      if (latitude == null || longitude == null) {
        return null;
      }
      return new GeoCoordinates(latitude, longitude);
    }
    return null;
  }

  /**
   * 이미지 파일 삭제
   *
   * @param imageFile 이미지 파일
   */
  private void deleteImageFile(final File imageFile) {
    boolean delete = imageFile.delete();
    log.info("file delete. ({}) ({})", delete, imageFile.getAbsolutePath());
  }

  /**
   * 사진 촬영일
   *
   * @param imageFile 이미지 파일
   * @return 촬영일
   */
  private static Date getShotDate(final File imageFile) {
    Date date = null;
    try {
      Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
      ExifSubIFDDirectory exifSubIFDDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
      if (exifSubIFDDirectory == null) {
        return null;
      }

      Optional<LocalDateTime> shotDate = exifSubIFDDirectory.getTags().stream()
        .filter(tag -> tag != null && tag.getTagName().equals(BokslPortalConstant.Photo.DATE_TIME_ORIGINAL))
        .map(Tag::getDescription).findAny()
        .map(dateStr -> LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss")));

      if (shotDate.isPresent()) {
        LocalDateTime value = shotDate.get();
        date = ApplicationUtil.getDate(value);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
    return date;
  }

  /**
   * 원본이미지 정보. 디카 이미지 메타 정보를 파악해 회전 정보를 보정해서 반환
   *
   * @param photoId 아이디
   * @return 이미지 바이너리 정보
   * @throws IOException 예외
   */
  public byte[] getImageOrg(final String photoId) throws IOException {
    PhotoVo photo = photoRepository.getOne(photoId);
    File photoFile;

    if (photo.isRotate()) {
      photoFile = rotateCorrection(photo);
    } else {
      photoFile = photo.getFullPath();
    }

    try (InputStream in = new FileInputStream(photoFile)) {
      return IOUtils.toByteArray(in);
    }
  }

  /**
   * 섬네일 이미지 만들기
   *
   * @param photo  이미지
   * @param width  최대 넓이
   * @param height 최대 높이
   * @return 섬네일 이미지 byte
   * @throws IOException 예외
   */
  public byte[] makeThumbimage(final PhotoVo photo, final int width, final int height) throws IOException {
    // 입력값이 재대로 입력되지 않으면 그냥 리턴
    if (photo == null || width == 0 || height == 0) {
      log.warn("thumbnail error.");
      return null;
    }

    File photoFile = photo.getFullPath();
    return ApplicationUtil.makeImageThumbimage(photoFile, photo.getPhotoId(), width, height);
  }

  /**
   * 이미지 회전 보정
   *
   * @param photo 포토
   * @return 회전 된 파일 이름
   * @throws IOException 예외
   */
  private File rotateCorrection(final PhotoVo photo) throws IOException {
    if (!BokslPortalConstant.Photo.ROTATE_DIR.exists()) {
      BokslPortalConstant.Photo.ROTATE_DIR.mkdirs();
      log.info("make rotate directory: ", BokslPortalConstant.Photo.ROTATE_DIR.getAbsolutePath());
    }

    File photoFile = photo.getFullPath();
    String ext = FilenameUtils.getExtension(photoFile.getName());
    File toRotate = new File(BokslPortalConstant.Photo.ROTATE_DIR, photo.getPhotoId() + "." + ext);

    boolean rotateExist = toRotate.exists();
    boolean oldImage = toRotate.lastModified() < photoFile.lastModified();

    // 기존에 회전 보정 변환된 파일이 있는냐?
    // 회전 보정 변환된 파일이 없거나, 파일이 수정되었을 경우 회전 보정 다시 만들기
    if (!rotateExist || oldImage) {
      Thumbnails.of(photoFile).scale(1).toFile(toRotate);
      log.info(String.format("rotate corrention: %s -> %s", photoFile.getName(), toRotate.getName()));
      return toRotate;
    }
    return toRotate;
  }

  /**
   * 사진 파일을 삭제한다. DB에서 해당 정보를 삭제함
   *
   * @param photoId 사진 아이디
   */
  public void delete(String photoId) {
    PhotoVo photo = photoRepository.getOne(photoId);
    photo.getFullPath().delete();
    photoRepository.delete(photo);
  }
}
