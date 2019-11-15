package com.setvect.bokslportal.image.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 이미지 업로드
 */
@Controller
public class ImageUploadController {
  /**
   * 서브 명령어 정의
   */
  public enum Mode {
    UPLOAD_FORM, UPLOAD_ACTION
  }

  @PutMapping("/image/upload")
  public void upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return;
  }
}
