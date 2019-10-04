package com.setvect.bokslportal.knowledge.controller;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.attach.service.AttachFileModule;
import com.setvect.bokslportal.attach.service.AttachFileService;
import com.setvect.bokslportal.attach.vo.AttachFileVo;
import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.knowledge.repository.KnowledgeRepository;
import com.setvect.bokslportal.knowledge.service.KnowledgeSearch;
import com.setvect.bokslportal.knowledge.service.KnowledgeService;
import com.setvect.bokslportal.knowledge.vo.KnowledgeVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/knowledge/")
@Log4j2
public class KnowledgeController {
  @Autowired
  private KnowledgeRepository knowledgeRepository;

  @Autowired
  private KnowledgeService knowledgeService;

  @Autowired
  private AttachFileService attachFileService;


  // ============== 조회 ==============

  /**
   * @param param 검색 조건
   * @return 할일 목록
   */
  @GetMapping("page")
  public ResponseEntity<String> page(KnowledgeSearch param) {
    GenericPage<KnowledgeVo> page = knowledgeRepository.getPagingList(param);
    String json = ApplicationUtil.toJsonWtihRemoveHibernate(page);
    return ResponseEntity.ok().body(json);
  }

  /**
   * @param categorySeq 일련번호
   * @return 할일 목록
   */
  @GetMapping("item/{id}")
  public ResponseEntity<String> getItem(@PathVariable("id") int categorySeq) {
    KnowledgeVo item = knowledgeRepository.findById(categorySeq).get();
    List<AttachFileVo> attach = attachFileService.listAttachFile(AttachFileModule.KNOWLEDGE, item.getKnowledgeSeq());
    item.setAttach(attach);
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(item, "**,attach[-savePath,-saveName]"));
  }

  // ============== 등록 ==============

  /**
   * @param knowledge 항목
   * @return 등록된 항목
   */
  @PostMapping("item")
  public ResponseEntity<String> addItem(KnowledgeVo knowledge, @RequestParam("attachList") MultipartFile[] attach) {
    knowledge.setRegDate(new Date());
    knowledgeRepository.save(knowledge);

    attachFileService.process(attach, AttachFileModule.KNOWLEDGE, knowledge.getKnowledgeSeq());
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(knowledge));
  }

  // ============== 수정 ==============

  /**
   * @param knowledgeVo         노트
   * @param deleteAttachFileSeq 삭제 첨부파일 번호
   * @param attach              업로드 첨부파일 정보
   * @return 수정된 항목
   */
  @PostMapping("item-edit")
  public ResponseEntity<String> editItem(KnowledgeVo knowledgeVo, @RequestParam(name = "deleteAttachFileSeq", required = false) Set<Integer> deleteAttachFileSeq, @RequestParam("attachList") MultipartFile[] attach) {
    KnowledgeVo saveData = knowledgeRepository.findById(knowledgeVo.getKnowledgeSeq()).get();
    saveData.setProblem(knowledgeVo.getProblem());
    saveData.setSolution(knowledgeVo.getSolution());
    knowledgeRepository.save(knowledgeVo);

    attachFileService.process(attach, AttachFileModule.KNOWLEDGE, knowledgeVo.getKnowledgeSeq());
    if (deleteAttachFileSeq != null) {
      attachFileService.deleteFile(deleteAttachFileSeq);
    }
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(knowledgeVo));
  }


  // ============== 삭제 ==============

  /**
   * @param knowledgeSeq 일련번호
   * @return 성공여부
   */
  @DeleteMapping(value = "item/{id}")
  public ResponseEntity<Void> deleteItem(@PathVariable("id") int knowledgeSeq) {
    KnowledgeVo saveData = knowledgeRepository.getOne(knowledgeSeq);
    saveData.setDeleteF(true);
    knowledgeRepository.save(saveData);
    return ResponseEntity.noContent().build();
  }
}
