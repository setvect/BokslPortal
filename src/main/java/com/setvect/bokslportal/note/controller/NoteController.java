package com.setvect.bokslportal.note.controller;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.attach.service.AttachFileModule;
import com.setvect.bokslportal.attach.service.AttachFileService;
import com.setvect.bokslportal.attach.vo.AttachFileVo;
import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.network.vo.NetworkVo;
import com.setvect.bokslportal.note.repository.NoteCategoryRepository;
import com.setvect.bokslportal.note.repository.NoteRepository;
import com.setvect.bokslportal.note.service.NoteSearch;
import com.setvect.bokslportal.note.service.NoteService;
import com.setvect.bokslportal.note.vo.NoteCategoryVo;
import com.setvect.bokslportal.note.vo.NoteVo;
import com.setvect.bokslportal.user.vo.UserVo;
import com.setvect.bokslportal.util.TreeNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/note/")
@Log4j2
public class NoteController {
  @Autowired
  private NoteCategoryRepository noteCategoryRepository;
  @Autowired
  private NoteRepository noteRepository;

  @Autowired
  private NoteService noteService;

  @Autowired
  private AttachFileService attachFileService;


  // ============== 조회 ==============

  /**
   * @return 카테고리 정보를 상하위 관계로 된 트리 형태로
   */
  @GetMapping("tree")
  public ResponseEntity<String> getTree() {
    TreeNode<NoteCategoryVo> tree = noteService.getCategoryTree();
    // ROOT를 제외하고 5 Depth까지 하여 하위 카테고리 반환
    // 이 방법이 최선이다. ㅡㅡ;
    return ResponseEntity.ok().body(ApplicationUtil.toJson(tree, "data[-parent,-children],children[data[-parent,-children],children[data[-parent,-children],children[data[-parent,-children],children[data[-parent,-children]]]]]"));
  }

  /**
   * @param categorySeq 기준 카테고리
   * @return ROOT부터 기준 카테고리 까지 경로 제공
   */
  @GetMapping("category-path/{id}")
  public ResponseEntity<String> getCategoryPath(@PathVariable("id") int categorySeq) {
    List<NoteCategoryVo> categoryPath = noteService.geCategoryPath(categorySeq);
    return ResponseEntity.ok().body(ApplicationUtil.toJson(categoryPath, "**,-parent,-children"));
  }

  /**
   * @param param 검색 조건
   * @return 할일 목록
   */
  @GetMapping("page")
  public ResponseEntity<String> page(NoteSearch param) {
    int categorySeq = param.getCategorySeq();

    Set<Integer> categorySeqSet = Collections.emptySet();
    if (categorySeq != 0) {
      categorySeqSet = noteService.getSubCategorySeq(categorySeq);
    }
    param.setCategorySeqSet(categorySeqSet);

    GenericPage<NoteVo> page = noteRepository.getPagingList(param);
    String json = ApplicationUtil.toJson(page, "**,list[**,category[name]]");
    return ResponseEntity.ok().body(json);
  }

  /**
   * @param noteSeq 일련번호
   * @return 할일 목록
   */
  @GetMapping("item/{id}")
  public ResponseEntity<String> getItem(@PathVariable("id") int noteSeq) {
    NoteVo item = noteRepository.findById(noteSeq).get();
    List<AttachFileVo> attach = attachFileService.listAttachFile(AttachFileModule.NOTE, noteSeq);
    item.setAttach(attach);
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(item, "**,category[name],attach[-savePath,-saveName]"));
  }

  // ============== 등록 ==============

  /**
   * @param note 항목
   * @return 등록된 항목 일련번호
   */
  @PostMapping("item")
  public ResponseEntity<String> addItem(NoteVo note, @RequestParam("attachList") MultipartFile[] attach) {
    note.setRegDate(new Date());
    note.setEditDate(new Date());
    noteRepository.saveAndFlush(note);

    attachFileService.process(attach, AttachFileModule.NOTE, note.getNoteSeq());
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(note));
  }

  // ============== 수정 ==============

  /**
   * @param note 항목
   * @return 할일 정보
   */
  @PostMapping("item-edit")
  public ResponseEntity<String> editItem(NoteVo note, @RequestParam("attachList") MultipartFile[] attach) {
    NoteVo saveData = noteRepository.findById(note.getNoteSeq()).get();
    saveData.setContent(note.getContent());
    saveData.setTitle(note.getTitle());
    saveData.setEditDate(new Date());
    noteRepository.save(note);

    attachFileService.process(attach, AttachFileModule.NOTE, note.getNoteSeq());
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(note));
  }

  /**
   * @param category 카테고리
   * @return no content
   */
  @PutMapping("category")
  public ResponseEntity<Void> applyCategory(@RequestBody NoteCategoryVo category) {
    noteService.updateCategory(category);
    return ResponseEntity.noContent().build();
  }

  // ============== 삭제 ==============

  /**
   * @param noteSeq 일련번호
   * @return 성공여부
   */
  @DeleteMapping(value = "item/{id}")
  public ResponseEntity<Void> deleteItem(@PathVariable("id") int noteSeq) {
    NoteVo saveData = noteRepository.getOne(noteSeq);
    saveData.setDeleteF(true);
    noteRepository.save(saveData);
    return ResponseEntity.noContent().build();
  }
}
