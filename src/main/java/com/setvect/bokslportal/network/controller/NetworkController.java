package com.setvect.bokslportal.network.controller;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.network.repository.NetworkRepository;
import com.setvect.bokslportal.network.service.NetworkSearch;
import com.setvect.bokslportal.network.vo.NetworkVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "/network/")
@Log4j2
public class NetworkController {
  @Autowired
  private NetworkRepository networkRepository;

  // ============== 조회 ==============

  /**
   * @param param 검색 조건
   * @return 할일 목록
   */
  @GetMapping("page")
  public ResponseEntity<String> page(NetworkSearch param) {
    GenericPage<NetworkVo> page = networkRepository.getPagingList(param);
    String json = ApplicationUtil.toJson(page, "**,list[**]");
    return ResponseEntity.ok().body(json);
  }

  /**
   * @param networkSeq 일련번호
   * @return 할일 목록
   */
  @GetMapping("item/{id}")
  public ResponseEntity<String> getItem(@PathVariable("id") int networkSeq) {
    NetworkVo item = networkRepository.getOne(networkSeq);
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(item));
  }

  // ------- 등록

  /**
   * @param network 항목
   * @return 등록된 항목 일련번호
   */
  @PostMapping("item")
  public ResponseEntity<String> addItem(NetworkVo network) {
    network.setRegDate(new Date());
    network.setEditDate(network.getRegDate());
    networkRepository.save(network);
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(network));
  }

  // ------- 수정

  /**
   * @param network 항목
   * @return 할일 정보
   */
  @PutMapping("item")
  public ResponseEntity<String> editItem(NetworkVo network) {
    NetworkVo saveData = networkRepository.getOne(network.getNetworkSeq());
    saveData.setContent(network.getContent());
    saveData.setTitle(network.getTitle());
    saveData.setEditDate(new Date());
    networkRepository.save(network);
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(network));
  }

  // ------- 삭제

  /**
   * @param networkSeq 일련번호
   * @return 성공여부
   */
  @DeleteMapping(value = "item/{id}")
  public ResponseEntity<Void> deleteItem(@PathVariable("id") int networkSeq) {
    NetworkVo saveData = networkRepository.getOne(networkSeq);
    saveData.setDeleteF(true);
    networkRepository.save(saveData);
    return ResponseEntity.noContent().build();
  }
}
