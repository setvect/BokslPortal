package com.setvect.bokslportal.board.service;

import com.setvect.bokslportal.board.vo.BoardArticleVo;
import com.setvect.bokslportal.util.StringEncrypt;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class BoardService {
  /**
   * 암호화 글 처리
   *
   * @param article
   */
  public void processEncrypt(BoardArticleVo article, String encrypt) {
    // 암호화 글
    if (StringUtils.isNotBlank(encrypt)) {
      article.setContent(StringEncrypt.encodeJ(article.getContent(), encrypt));
      article.setEncodeF(true);
    } else {
      article.setEncodeF(false);
    }
  }
}
