package com.setvect.bokslportal.attach.controller;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.attach.service.AttachFileModule;
import com.setvect.bokslportal.attach.service.AttachFileService;
import com.setvect.bokslportal.attach.vo.AttachFileVo;
import com.setvect.bokslportal.user.vo.UserVo;
import com.setvect.bokslportal.util.FileUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 첨부파일 조회
 */
@Controller
@RequestMapping(value = "/attach/")
public class AttachFileController {
  @Autowired
  private AttachFileService attachFileService;


  /**
   * @param attachFileSeq 첨부파일 번호
   * @param request
   * @param response
   * @throws IOException
   */
  @GetMapping("/download/{id}")
  public void process(@PathVariable("id") int attachFileSeq, HttpServletRequest request, HttpServletResponse response) throws IOException {
    AttachFileVo attach = attachFileService.getAttachFile(attachFileSeq);
    File attachFile = attach.getSavePath();
    try {
      FileUtil.fileDown(attachFile, attach.getOriginalName(), request, response);
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }
  }

  /**
   * 썸네일 사진 정보를 byte로 전송
   *
   * @param attachFileSeq 첨부파일 번호
   * @param width         넓이 픽셀
   * @param height        높이 픽셀
   * @return 섬네일 이미지 byte
   * @throws IOException 파일 처리 오류
   */
  @GetMapping("/thumbimage")
  @ResponseBody
  public byte[] getThumb(@RequestParam("attachFileSeq") int attachFileSeq, @RequestParam("w") int width,
                         @RequestParam("h") int height) throws IOException {
    AttachFileVo attach = attachFileService.getAttachFile(attachFileSeq);
    File attachFile = attach.getSavePath();
    checkImage(attachFile);
    return ApplicationUtil.makeImageThumbimage(attachFile, "attach_" + attach.getAttachFileSeq() + "_", width, height);
  }

  /**
   * 사진 데이터를 byte로 전송
   *
   * @param attachFileSeq 첨부파일 번호
   * @return 섬네일 이미지 byte
   * @throws IOException 파일 처리 오류
   */
  @GetMapping("/image")
  @ResponseBody
  public byte[] getImage(@RequestParam("attachFileSeq") int attachFileSeq) throws IOException {
    AttachFileVo attach = attachFileService.getAttachFile(attachFileSeq);
    File attachFile = attach.getSavePath();
    checkImage(attachFile);
    try (InputStream in = new FileInputStream(attachFile)) {
      return IOUtils.toByteArray(in);
    }
  }


  /**
   * @param file
   * @return
   * @throws IOException
   */
  @PostMapping("/uploadImage")
  public ResponseEntity<ImageUploadResult> process(@RequestPart("file") MultipartFile file) throws IOException {
    UserVo user = ApplicationUtil.getLoginUser();
    AttachFileVo attach = attachFileService.process(file, AttachFileModule.IMAGE, 1, user.getUserId());
    ImageUploadResult result = new ImageUploadResult("/attach/image?attachFileSeq=" + attach.getAttachFileSeq());
    return ResponseEntity.ok().body(result);
  }


  private void checkImage(File attachFile) {
    if (!ApplicationUtil.isImage(attachFile)) {
      new RuntimeException(String.format("%s is not image file.", attachFile.getName()));
    }
  }

  @RequiredArgsConstructor
  @Getter
  static class ImageUploadResult {
    private final String filename;
  }
}
