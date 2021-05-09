package com.setvect.bokslportal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import com.setvect.bokslportal.photo.service.ThumbnailImageConvert;
import com.setvect.bokslportal.user.vo.UserVo;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 어플리케이션 전반에 사용되는 공통 함수 제공.
 */
@Log4j2
public abstract class ApplicationUtil {
  /**
   * 허용 이미지 파일 확장자.
   */
  public static final Set<String> IMAGE_EXT = new HashSet<>(Arrays.asList("jpg", "jpge", "png", "gif"));

  /**
   * 로깅
   */
  private static Logger logger = LoggerFactory.getLogger(ApplicationUtil.class);

  /**
   * filePath에서 basePath 경로를 제외.<br>
   * 예)<br>
   * basePath = /home/user/<br>
   * filePath = /home/user/temp/readme.txt<br>
   * 리턴값: temp/read.txt
   *
   * @param basePath 기준 경로(OS Full Path)
   * @param filePath 파일 경로(OS Full Path)
   * @return filePath에서 basePath 경로를 제외된 값
   */
  public static String getRelativePath(final File basePath, final File filePath) {
    String dir = basePath.toURI().relativize(filePath.toURI()).getPath();
    return dir;
  }

  /**
   * @param keyword 검색어
   * @return 주어진 검색어에 like 검색을 할 수 있도록 양쪽에 % 넣기
   */
  public static String makeLikeString(final String keyword) {
    return "%" + keyword + "%";
  }

  /**
   * @param word
   * @return sql String 값에 들어가도록 변경
   */
  public static String makeSqlString(String word) {
    word = StringUtils.replace(word, "'", "''");
    word = word.trim();
    return "'" + word + "'";
  }

  /**
   * 파일 다운로드
   *
   * @param response         .
   * @param targetFile       다운로드 대상 파일
   * @param downloadFileName 다운로드 파일 이름
   * @throws IOException .
   */
  public static void downloadFile(final HttpServletResponse response, final File targetFile,
                                  final String downloadFileName) throws IOException {
    String fileName = URLEncoder.encode(downloadFileName.replace(" ", "_"), "UTF-8");
    response.setContentType("application/octet-stream");
    response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
    try (FileInputStream fis = new FileInputStream(targetFile); OutputStream os = response.getOutputStream()) {
      FileCopyUtils.copy(fis, os);
    }
  }

  /**
   * 파일 다운로드
   *
   * @param response         .
   * @param is               입력 스트림
   * @param downloadFileName 다운로드 파일 이름
   * @throws IOException .
   */
  public static void downloadFile(final HttpServletResponse response, final InputStream is,
                                  final String downloadFileName) throws IOException {
    response.setContentType("application/octet-stream");
    response.setHeader("Content-Disposition", "attachment;filename=" + downloadFileName);
    try (OutputStream os = response.getOutputStream()) {
      FileCopyUtils.copy(is, os);
    }
  }

  /**
   * val 객체를 json 문자열로 변환 한다.
   *
   * @param val    대상 객체
   * @param filter 변환 필터링 조건
   * @return json
   */
  public static String toJson(final Object val, final String filter) {
    if (val == null) {
      return null;
    }
    ObjectMapper objectMapper = Squiggly.init(new ObjectMapper(), filter);
    return SquigglyUtils.stringify(objectMapper, val);
  }

  /**
   * val 객체를 json 문자열로 변환 한다.<br>
   * 하이버네이트 관련 proxy 객체를 제거함
   *
   * @param val 객체
   * @return json
   */
  public static String toJsonWtihRemoveHibernate(final Object val) {
    return toJsonWtihRemoveHibernate(val, "");
  }

  /**
   * val 객체를 json 문자열로 변환 한다.<br>
   * 하이버네이트 관련 proxy 객체를 제거함
   *
   * @param val          객체
   * @param appendFilter 추가 필터 조건
   * @return json
   */
  public static String toJsonWtihRemoveHibernate(final Object val, final String appendFilter) {
    String filter = "-handler,-hibernateLazyInitializer";
    if (StringUtils.isNotBlank(appendFilter)) {
      filter += "," + appendFilter;
    }
    return ApplicationUtil.toJson(val, filter);
  }

  /**
   * @param session HTTP 세션
   * @return 현재 로그인한 사용자 정보 반환
   */
  public static UserVo getLoginUser(final HttpSession session) {
    SecurityContext securityContext = (SecurityContext) session
      .getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
    if (securityContext == null) {
      return null;
    }
    Authentication auth = securityContext.getAuthentication();
    UserVo regUser = (UserVo) auth.getPrincipal();
    return regUser;
  }

  /**
   * 현재 쓰레드의 HttpSession를 이용해 로그인 정보 얻음
   *
   * @return 로그인 사용자. 없으면 null
   */
  public static UserVo getLoginUser() {
    try {
      HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
        .getRequest();
      if (req != null) {
        UserVo loginUser = getLoginUser(req.getSession());
        return loginUser;
      }
      return null;
    } catch (Exception e) {
      logger.warn(e.getMessage());
      return null;
    }
  }

  /**
   * @param value 변환 날짜 값
   * @return LocalDateTime를 Date로 변환된 값
   */
  public static Date getDate(final LocalDateTime value) {
    ZonedDateTime zdt = value.atZone(ZoneId.systemDefault());
    Date d = Date.from(zdt.toInstant());
    return d;
  }

  /**
   * MD5 변환.
   *
   * @param file 대상 파일
   * @return MD5
   */
  public static String getMd5(final File file) {
    String md5 = null;
    try (FileInputStream fis = new FileInputStream(file)) {
      md5 = DigestUtils.md5Hex(fis);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return md5;
  }

  /**
   * 파일 업로드 체크. 허용된 확장자가 아니면 예외 발생
   *
   * @param filename 파일명
   */
  public static void checkAllowUploadFile(String filename) {
    String ext = FilenameUtils.getExtension(filename).toLowerCase();

    boolean deny = !BokslPortalConstant.Attach.ALLOW_EXT.contains(ext);
    if (deny) {
      throw new RuntimeException("[" + filename + "] is not an authorized file.");
    }
  }

  /**
   * 파일 업로드 체크. 허용된 확장자가 아니면 예외 발생
   *
   * @param filename 파일명
   */
  public static void checkAllowUploadImage(String filename) {
    String ext = FilenameUtils.getExtension(filename).toLowerCase();

    boolean deny = !BokslPortalConstant.Attach.IMAGE_EXT.contains(ext);
    if (deny) {
      throw new RuntimeException("[" + filename + "] is not an authorized image file.");
    }
  }


  /**
   * @param imagePath 이미지 경로
   * @param prefix    섬네일 이미지 파일명  prefix
   * @param width     최대 넓이
   * @param height    최대 높이
   * @return 섬네일 이미지 byte
   * @throws IOException
   */
  public static byte[] makeImageThumbimage(File imagePath, String prefix, int width, int height) throws IOException {
    // 파일이 존재 하지 않으면 그냥 종료
    if (!imagePath.exists()) {
      log.warn("{} not exist.", imagePath);
      return null;
    }

    // 섬네일 이미지 파일이름 만들기
    // e.g) imagename_w33_h44.jpg
    String name = imagePath.getName();
    String tempImg = prefix + "_w" + width + "_h" + height + "." + FilenameUtils.getExtension(name);

    if (!BokslPortalConstant.Photo.THUMBNAIL_DIR.exists()) {
      BokslPortalConstant.Photo.THUMBNAIL_DIR.mkdirs();
      log.info("make thumbnail directory: ", BokslPortalConstant.Photo.THUMBNAIL_DIR.getAbsolutePath());
    }

    // 섬네일 버전된 경로
    File toThumbnailFile = new File(BokslPortalConstant.Photo.THUMBNAIL_DIR, tempImg);
    boolean thumbnailExist = toThumbnailFile.exists();
    boolean oldThumbnail = toThumbnailFile.lastModified() < imagePath.lastModified();

    // 기존에 섬네일로 변환된 파일이 있는냐?
    // 섬네일로 변환된 파일이 없거나, 파일이 수정되었을 경우 섬네일 다시 만들기
    if (!thumbnailExist || oldThumbnail) {
      ThumbnailImageConvert.makeThumbnail(imagePath, toThumbnailFile, width, height);
    }

    try (InputStream in = new FileInputStream(toThumbnailFile)) {
      return IOUtils.toByteArray(in);
    }
  }

  /**
   * @param file
   * @return 이미지 확장자면 true, 아니면 false
   */
  public static boolean isImage(File file) {
    return isImage(file.getName());
  }

  /**
   * @param fileName
   * @return 이미지 확장자면 true, 아니면 false
   */
  public static boolean isImage(String fileName) {
    String ext = FilenameUtils.getExtension(fileName);
    return IMAGE_EXT.contains(ext);
  }


  /**
   * @param destDir
   * @param saveDir
   * @return 저장 경로에서 기본 경로를 제외한 즉 날짜로 이루어진 경로 <br/>
   * ex) /2011/02/11/
   */
  public static String getDayPath(File destDir, File saveDir) {
    String dd = destDir.getAbsolutePath();
    String sd = saveDir.getAbsolutePath();
    String dayPath = sd.substring(dd.length()) + "/";
    dayPath = dayPath.replace('\\', '/');
    return dayPath;
  }

}
