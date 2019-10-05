package com.setvect.bokslportal.board.controller;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.board.repository.BoardManagerRepository;
import com.setvect.bokslportal.board.service.BoardManagerSearch;
import com.setvect.bokslportal.board.vo.BoardManagerVo;
import com.setvect.bokslportal.common.GenericPage;
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

import java.util.Optional;

@RestController
@RequestMapping(value = "/board-manager/")
@Log4j2
public class BoardManagerController {
  @Autowired
  private BoardManagerRepository boardRepository;


  // ============== 조회 ==============

  /**
   * @param param 검색 조건
   * @return 할일 목록
   */
  @GetMapping("page")
  public ResponseEntity<String> page(BoardManagerSearch param) {
    GenericPage<BoardManagerVo> page = boardRepository.getPagingList(param);

    String json = ApplicationUtil.toJsonWtihRemoveHibernate(page);
    return ResponseEntity.ok().body(json);
  }

  /**
   * @param boardCode 게시판 코드
   * @return 할일 목록
   */
  @GetMapping("item/{id}")
  public ResponseEntity<String> getItem(@PathVariable("id") String boardCode) {
    BoardManagerVo item = boardRepository.findById(boardCode).get();
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(item, "**,attach[-savePath,-saveName]"));
  }

  // ============== 등록 ==============

  /**
   * @param board 항목
   * @return 등록된 항목
   */
  @PostMapping("item")
  public ResponseEntity<String> addItem(BoardManagerVo board) {
    Optional<BoardManagerVo> b = boardRepository.findById(board.getBoardCode());
    if (b.isPresent()) {
      throw new RuntimeException("이미 등록된 게시판이다.");
    }

    boardRepository.save(board);
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(board));
  }

  // ============== 수정 ==============

  /**
   * @param board 게시판
   * @return 수정된 항목
   */
  @PutMapping("item")
  public ResponseEntity<String> editItem(BoardManagerVo board) {

    boardRepository.save(board);
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(board));
  }


  // ============== 삭제 ==============

  /**
   * @param boardCode 일련번호
   * @return 성공여부
   */
  @DeleteMapping(value = "item/{id}")
  public ResponseEntity<Void> deleteItem(@PathVariable("id") String boardCode) {
    BoardManagerVo saveData = boardRepository.getOne(boardCode);
    saveData.setDeleteF(true);
    boardRepository.save(saveData);
    return ResponseEntity.noContent().build();
  }
}
