package com.setvect.bokslportal.attach.service;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.BokslPortalConstant;
import com.setvect.bokslportal.attach.repository.AttachFileRepository;
import com.setvect.bokslportal.attach.vo.AttachFileVo;
import com.setvect.bokslportal.user.vo.UserVo;
import com.setvect.bokslportal.util.FileUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("AttachFileService")
public class AttachFileService {

  @Autowired
  private AttachFileRepository attachFileRepository;

  /**
   * 첨부파일 저장
   *
   * @param attachFile 첨부파일
   * @param moduleName 모듈 이름
   * @param moduleId   모듈 데이터 항목 일련번호(키)
   * @param userId     사용자 ID
   * @return 업로드한 첨부파일 정보
   */
  public AttachFileVo process(MultipartFile attachFile, AttachFileModule moduleName, int moduleId,
                              String userId) {
    if (attachFile == null) {
      return null;
    }
    List<AttachFileVo> list = process(new MultipartFile[]{attachFile}, moduleName, String.valueOf(moduleId),
      userId);
    if (list.size() != 1) {
      return null;
    }
    return list.get(0);
  }

  /**
   * 첨부파일 저장
   *
   * @param attachFiles 첨부파일
   * @param moduleName  모듈 이름
   * @param moduleId    모듈 데이터 항목 일련번호(키)
   * @return 업로드한 첨부파일 정보
   */
  public List<AttachFileVo> process(MultipartFile[] attachFiles, AttachFileModule moduleName, int moduleId) {
    UserVo user = ApplicationUtil.getLoginUser();
    return process(attachFiles, moduleName, String.valueOf(moduleId), user.getUserId());
  }

  /**
   * 첨부파일 저장
   *
   * @param attachFiles 첨부파일
   * @param moduleName  모듈 이름
   * @param moduleId    모듈 데이터 항목 일련번호(키)
   * @param userId      사용자 ID
   * @return 업로드한 첨부파일 정보
   */
  public List<AttachFileVo> process(MultipartFile[] attachFiles, AttachFileModule moduleName, int moduleId,
                                    String userId) {
    return process(attachFiles, moduleName, String.valueOf(moduleId), userId);
  }

  /**
   * 첨부파일 저장
   *
   * @param attachFiles 첨부파일
   * @param moduleName  모듈 이름
   * @param moduleId    모듈 데이터 항목 일련번호(키)
   * @param userId      사용자 ID
   * @return 업로드한 첨부파일 정보
   */
  public List<AttachFileVo> process(MultipartFile[] attachFiles, AttachFileModule moduleName, String moduleId,
                                    String userId) {

    if (attachFiles == null || attachFiles.length == 0) {
      // empty list
      return new ArrayList<AttachFileVo>();
    }

    File saveDir = FileUtil.makeDayDir(BokslPortalConstant.Attach.BASE_DIR);
    String dayPath = ApplicationUtil.getDayPath(BokslPortalConstant.Attach.BASE_DIR, saveDir);

    List<AttachFileVo> result = new ArrayList<AttachFileVo>();
    for (MultipartFile file : attachFiles) {
      if (StringUtils.isEmpty(file.getOriginalFilename())) {
        continue;
      }
      String fileName = moduleName + "_" + moduleId + "." + FilenameUtils.getExtension(file.getOriginalFilename());
      // 허용 확장자 체크
      ApplicationUtil.checkAllowUploadFile(file.getOriginalFilename());

      File destination;
      try {
        destination = File.createTempFile("file", fileName, saveDir);
        FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(destination));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      AttachFileVo attach = new AttachFileVo();
      attach.setModuleName(moduleName);
      attach.setModuleId(moduleId);
      attach.setOriginalName(file.getOriginalFilename());
      attach.setSaveName(dayPath + destination.getName());
      attach.setSize((int) file.getSize());
      attach.setRegDate(new Date());
      createAttachFile(attach);
      result.add(attach);
    }
    return result;
  }


  /**
   * @param deleteSeq 첨부파일 시퀀스 번호
   * @param destDir   OS 기준 webroot 디렉토리
   */
  public void deleteFile(String[] deleteSeq, String destDir) {
    for (String s : deleteSeq) {
      int seq = Integer.parseInt(s);

      AttachFileVo file = getAttachFile(seq);
      if (file != null) {
        file.getSavePath().delete();
      }
      deleteFile(seq);
    }
  }

  /**
   * 첨부파일 정보 삭제, 물리적인 첨부파일은 삭제 하지 않음
   *
   * @param attachFileSeq 첨부파일 일련번호
   */
  public void deleteFile(int attachFileSeq) {
    deleteFile(new HashSet<>(Arrays.asList(attachFileSeq)));
  }

  /**
   * 첨부파일 정보 삭제, 물리적인 첨부파일은 삭제 하지 않음
   *
   * @param attachFileSeq 첨부파일 일련번호
   */
  public void deleteFile(Set<Integer> attachFileSeq) {
    for (int seq : attachFileSeq) {
      removeAttachFile(seq);
    }
  }

  public AttachFileVo getAttachFile(int attachFileSeq) {

    return attachFileRepository.getOne(attachFileSeq);
  }

  public List<AttachFileVo> listAttachFile(AttachFileModule moduleName, int moduleItemId) {
    return attachFileRepository.findByModuleNameAndModuleId(moduleName, String.valueOf(moduleItemId));
  }

  public List<AttachFileVo> listAttachFile(AttachFileModule moduleName, String moduleItemId) {
    return attachFileRepository.findByModuleNameAndModuleId(moduleName, moduleItemId);
  }

  public void createAttachFile(AttachFileVo attachFile) {
    attachFileRepository.save(attachFile);
  }

  public void removeAttachFile(int seq) {
    attachFileRepository.deleteById(seq);
  }

}
