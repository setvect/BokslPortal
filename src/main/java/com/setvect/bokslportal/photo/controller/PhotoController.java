package com.setvect.bokslportal.photo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.photo.repository.PhotoRepository;
import com.setvect.bokslportal.photo.service.PhotoSearch;
import com.setvect.bokslportal.photo.vo.PhotoVo;
import com.setvect.bokslportal.photo.vo.PhotoVo.ShotDateType;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/photo/")
@Log4j2
public class PhotoController {
  @Autowired
  private PhotoRepository photoRepository;

  // ============== 조회 ==============

  /**
   * @param param
   *          검색 조건
   * @return 사진 목록
   */
  @GetMapping("page")
  public ResponseEntity<String> page(PhotoSearch param) {
    GenericPage<PhotoVo> page = photoRepository.getPagingList(param);
    String json = ApplicationUtil.toJson(page, "**,list[**]");
    return ResponseEntity.ok().body(json);
  }

  /**
   * @param phtooSeq
   *          일련번호
   * @return 사진 목록
   */
  @GetMapping("item/{id}")
  public ResponseEntity<String> getItem(@PathVariable("id") String photoId) {
    PhotoVo item = photoRepository.getOne(photoId);
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(item));
  }

  // ------- 등록

  /**
   * @param photo
   *          사진
   * @return 등록된 항목 일련번호
   */
  @PostMapping("image")
  public ResponseEntity<Void> addImage(PhotoVo photo) {
    photoRepository.save(photo);
    return ResponseEntity.noContent().build();
  }

  // ------- 수정

  /**
   * @param photo
   *          일일
   * @return 사진 정보
   */
  @PatchMapping("item")
  public ResponseEntity<String> editItem(PhotoVo photo) {
    PhotoVo saveData = photoRepository.getOne(photo.getPhotoId());
    saveData.setMemo(photo.getMemo());
    if (photo.getShotDateType() == ShotDateType.MANUAL) {
      saveData.setShotDateType(ShotDateType.MANUAL);
      saveData.setShotDate(photo.getShotDate());
    }
    photoRepository.save(photo);
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(photo));
  }

  // ------- 삭제

  /**
   * @param networkSeq
   *          일련번호
   * @return 성공여부
   */
  @DeleteMapping(value = "item/{id}")
  public ResponseEntity<Void> deleteItem(@PathVariable("id") String photoId) {
    photoRepository.deleteById(photoId);
    return ResponseEntity.noContent().build();
  }
}
