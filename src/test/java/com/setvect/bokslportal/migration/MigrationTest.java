package com.setvect.bokslportal.migration;

import com.setvect.bokslportal.MainTestBase;
import com.setvect.bokslportal.board.repository.BoardArticleRepository;
import com.setvect.bokslportal.board.repository.BoardManagerRepository;
import com.setvect.bokslportal.board.vo.BoardArticleVo;
import com.setvect.bokslportal.board.vo.BoardManagerVo;
import com.setvect.bokslportal.user.repository.UserRepository;
import com.setvect.bokslportal.user.vo.UserVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MigrationTest extends MainTestBase {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BoardManagerRepository boardManagerRepository;

  @Autowired
  private BoardArticleRepository boardArticleRepository;


  @Test
  public void migration() throws SQLException, ClassNotFoundException {
    migrationBoard();
    return;
  }

  private void migrationBoard() throws ClassNotFoundException, SQLException {
    boardArticleRepository.deleteAll();
    boardManagerRepository.deleteAll();

    Connection conn = connection();
    PreparedStatement ps = conn.prepareStatement("SELECT * FROM TBBA_BOARD_MANAGER");
    ResultSet rs = ps.executeQuery();

    UserVo user = userRepository.findById("boksl").get();

    while (rs.next()) {
      BoardManagerVo board = new BoardManagerVo();
      board.setName(rs.getString("NAME"));
      board.setBoardCode(rs.getString("BOARD_CODE"));
      board.setAttachF(rs.getBoolean("ATTACH_F"));
      board.setCommentF(rs.getBoolean("COMMENT_F"));
      board.setDeleteF(rs.getBoolean("DELETE_F"));
      board.setEncryptF(rs.getBoolean("ENCODE_F"));
      board.setReplyF(rs.getBoolean("REPLY_F"));
      boardManagerRepository.save(board);


      PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM TBBB_BOARD_ARTICLE WHERE BOARD_CODE = ? ORDER BY ARTICLE_SEQ ASC");
      ps1.setString(1, board.getBoardCode());
      ResultSet rs1 = ps1.executeQuery();
      System.out.println("BoardCode: " + board.getBoardCode());
      int count = 0;
      while (rs1.next()) {
        count++;
        BoardArticleVo article = new BoardArticleVo();
        article.setBoardArticleSeq(rs1.getInt("ARTICLE_SEQ"));
        article.setBoardManager(board);
        article.setContent(rs1.getString("CONTENT"));
        article.setIp(rs1.getString("IP"));
        article.setEncryptF(rs1.getBoolean("ENCODE_F"));
        article.setDeleteF(rs1.getBoolean("DELETE_F"));
        article.setHitCount(rs1.getInt("HIT_COUNT"));
        article.setRegDate(rs1.getDate("REG_DATE"));
        article.setTitle(rs1.getString("TITLE"));
        article.setUser(user);

        if (count % 100 == 0) {
          System.out.println("BoardCode: " + board.getBoardCode() + ", count: " + count);
        }
        System.out.println("BoardCode: " + board.getBoardCode() + " 끝.");
      }
      rs.close();
      ps1.close();

    }
    rs.close();
    ps.close();
    conn.close();
    System.out.println(" 게시판 마이그레이션 끝 ");
  }


  public Connection connection() throws ClassNotFoundException, SQLException {
    Class.forName("org.h2.Driver");
    String url = "jdbc:h2:file:D:/intellij-project/BokslPortal/temp/nowhappy/literatureboy";
    Connection conn = DriverManager.getConnection(url, "sa", "");
    return conn;
  }
}
