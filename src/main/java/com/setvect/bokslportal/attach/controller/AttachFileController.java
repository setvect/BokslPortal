package com.setvect.bokslportal.attach.controller;

import com.setvect.bokslportal.attach.service.AttachFileService;
import com.setvect.bokslportal.attach.vo.AttachFileVo;
import com.setvect.bokslportal.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * 첨부파일 조회
 */
@Controller
@RequestMapping(value = "/attach/")
public class AttachFileController {
  @Autowired
  private AttachFileService attachFileService;

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
}
