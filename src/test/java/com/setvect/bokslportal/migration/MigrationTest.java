package com.setvect.bokslportal.migration;

import com.setvect.bokslportal.MainTestBase;
import com.setvect.bokslportal.user.repository.UserRepository;
import com.setvect.bokslportal.user.vo.UserVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MigrationTest extends MainTestBase{
  @Autowired
  private UserRepository userRepository;

  @Test
  public void migration() {
    List<UserVo> userList = userRepository.findAll();
    System.out.println("####################@@@@@@@@@@@@@@@@@@");
    userList.forEach(u -> System.out.println("############" + u));
  }
}
