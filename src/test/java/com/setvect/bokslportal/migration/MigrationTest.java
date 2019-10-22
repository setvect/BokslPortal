package com.setvect.bokslportal.migration;

import com.setvect.bokslportal.MainTestBase;
import com.setvect.bokslportal.board.repository.BoardManagerRepository;
import com.setvect.bokslportal.board.vo.BoardManagerVo;
import com.setvect.bokslportal.user.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MigrationTest extends MainTestBase {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BoardManagerRepository boardManagerRepository;

  @Test
  public void migration() throws SQLException, ClassNotFoundException {
//    List<BoardManagerVo> list = boardManagerRepository.findAll();
//    list.forEach(b -> System.out.println("#######################" + b.getName()));

    Connection conn = connection();
    PreparedStatement ps = conn.prepareStatement("select * from TBBA_BOARD_MANAGER");
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
      System.out.println(rs.getString("NAME"));
    }

    conn.close();
  }


  public Connection connection() throws ClassNotFoundException, SQLException {
    Class.forName("org.h2.Driver");
    String url = "jdbc:h2:file:D:/intellij-project/BokslPortal/temp/nowhappy/literatureboy";
    Connection conn = DriverManager.getConnection(url, "sa", "");
    return conn;
  }
}
