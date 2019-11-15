package com.setvect.bokslportal.board.controller;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.attach.service.AttachFileModule;
import com.setvect.bokslportal.attach.service.AttachFileService;
import com.setvect.bokslportal.attach.vo.AttachFileVo;
import com.setvect.bokslportal.board.repository.BoardArticleRepository;
import com.setvect.bokslportal.board.repository.BoardManagerRepository;
import com.setvect.bokslportal.board.service.BoardArticleSearch;
import com.setvect.bokslportal.board.service.BoardService;
import com.setvect.bokslportal.board.vo.BoardArticleVo;
import com.setvect.bokslportal.board.vo.BoardManagerVo;
import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.util.StringEncrypt;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/board-article/")
@Log4j2
public class BoardArticleController {
  @Autowired
  private BoardArticleRepository boardArticleRepository;

  @Autowired
  private BoardManagerRepository boardManagerRepository;

  @Autowired
  private AttachFileService attachFileService;

  @Autowired
  private BoardService boardService;

  // ============== 조회 ==============

  /**
   * @param param 검색 조건
   * @return 목록
   */
  @GetMapping("page")
  public ResponseEntity<String> page(BoardArticleSearch param) {
    GenericPage<BoardArticleVo> page = boardArticleRepository.getPagingList(param);
    page.getList().forEach(board -> {
      List<AttachFileVo> attach = attachFileService.listAttachFile(AttachFileModule.BOARD, board.getBoardArticleSeq());
      board.setAttach(attach);
    });
    String json = ApplicationUtil.toJson(page, "**,list[**,boardManager[boardCode,name],attach[-savePath,-saveName],user[name,userId]]");
    return ResponseEntity.ok().body(json);
  }

  /**
   * @param boardArticleSeq 일련번호
   * @return 게시물
   */
  @GetMapping("item/{id}")
  public ResponseEntity<String> getItem(@PathVariable("id") int boardArticleSeq) {
    BoardArticleVo item = boardArticleRepository.findById(boardArticleSeq).get();
    List<AttachFileVo> attach = attachFileService.listAttachFile(AttachFileModule.BOARD, boardArticleSeq);
    item.setAttach(attach);

    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(item, "**,boardManager[boardCode,name],attach[-savePath,-saveName],user[name,userId]"));
  }

  // ============== 등록 ==============

  /**
   * @param boardArticleSeq 일련번호
   * @param encrypt         암호화 문자열
   * @return 본문 디코딩 결과
   */
  @PostMapping("/decode/{id}")
  public ResponseEntity<String> getItem(@PathVariable("id") int boardArticleSeq, @RequestParam(value = "encrypt", required = false) String encrypt) {
    BoardArticleVo item = boardArticleRepository.findById(boardArticleSeq).get();
    String c = StringEncrypt.decodeJ(item.getContent(), encrypt);
    return ResponseEntity.ok().body(c);
  }

  /**
   * @param boardArticleVo 게시물
   * @param attach         첨부파일
   * @param boardCode      게시판 코드
   * @param encrypt        암호화 문자열
   * @param request
   * @return 등록된 게시물
   */
  @PostMapping("item")
  public ResponseEntity<String> addItem(BoardArticleVo boardArticleVo, @RequestParam("attachList") MultipartFile[] attach, @RequestParam("boardCode") String boardCode, @RequestParam(value = "encrypt", required = false) String encrypt, HttpServletRequest request) {
    BoardManagerVo board = boardManagerRepository.findById(boardCode).get();
    boardArticleVo.setBoardManager(board);
    boardArticleVo.setUser(ApplicationUtil.getLoginUser());
    boardArticleVo.setRegDate(new Date());
    boardArticleVo.setIp(request.getRemoteAddr());

    boardService.processEncrypt(boardArticleVo, encrypt);

    boardArticleRepository.saveAndFlush(boardArticleVo);
    attachFileService.process(attach, AttachFileModule.BOARD, boardArticleVo.getBoardArticleSeq());
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(boardArticleVo));
  }

  // ============== 수정 ==============

  /**
   * @param boardArticleVo      게시물
   * @param deleteAttachFileSeq 삭제 첨부파일 번호
   * @param attach              업로드 첨부파일 정보
   * @param encrypt             암호화 문자열
   * @return 수정된 항목
   */
  @PostMapping("item-edit")
  public ResponseEntity<String> editItem(BoardArticleVo boardArticleVo, @RequestParam(name = "deleteAttachFileSeq", required = false) Set<Integer> deleteAttachFileSeq, @RequestParam("attachList") MultipartFile[] attach, @RequestParam(value = "encrypt", required = false) String encrypt) {
    BoardArticleVo saveData = boardArticleRepository.findById(boardArticleVo.getBoardArticleSeq()).get();
    saveData.setContent(boardArticleVo.getContent());
    saveData.setTitle(boardArticleVo.getTitle());
    boardService.processEncrypt(saveData, encrypt);

    boardArticleRepository.save(saveData);

    attachFileService.process(attach, AttachFileModule.BOARD, boardArticleVo.getBoardArticleSeq());
    if (deleteAttachFileSeq != null) {
      attachFileService.deleteFile(deleteAttachFileSeq);
    }
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(boardArticleVo));
  }

  // ============== 삭제 ==============

  /**
   * @param boardArticleSeq 일련번호
   * @return void
   */
  @DeleteMapping(value = "item/{id}")
  public ResponseEntity<Void> deleteItem(@PathVariable("id") int boardArticleSeq) {
    BoardArticleVo saveData = boardArticleRepository.getOne(boardArticleSeq);
    saveData.setDeleteF(true);
    boardArticleRepository.save(saveData);
    return ResponseEntity.noContent().build();
  }

}
