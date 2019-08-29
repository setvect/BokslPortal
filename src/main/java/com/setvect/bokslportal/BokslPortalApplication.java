package com.setvect.bokslportal;

import java.net.URL;

import com.setvect.bokslportal.memo.service.MemoService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import com.setvect.bokslportal.user.service.UserService;
import com.setvect.bokslportal.util.BeanUtils;

@SpringBootApplication
// @ImportResource({ "classpath:/spring/context-transaction.xml" })
@ImportResource({"classpath:/spring/context-transaction.xml", "classpath:/spring/context-beans.xml"})
public class BokslPortalApplication {
  /**
   * 설정 파일 경로.
   */
  private static final String CONFIG_PROPERTIES = "/application.properties";
  /**
   * 테스트 설정 파일 경로
   */
  private static final String CONFIG_PROPERTIES_TEST = "/test.properties";

  public static void main(String[] args) {
    // spring boot에서 클래스가 및 properties 변경되었을 때 restart 안되게 함.
    // 즉 reload 효과
    System.setProperty("spring.devtools.restart.enabled", "false");

    SpringApplication.run(BokslPortalApplication.class, args);
  }

  /**
   * Spring scope가 아닌 곳에서 Spring Bean 객체를 접근하기 위해 사용.
   *
   * @return ApplicationContextProvider
   */
  @Bean
  ApplicationContextProvider getApplicationContext() {
    return new ApplicationContextProvider();
  }

  /**
   * 서비스 시작점 초기화.
   *
   * @return Spring boot 시작 bean
   */
  @Bean
  InitializingBean init() {
    return () -> {
      String testEnv = System.getProperty(BokslPortalConstant.TEST_CHECK_PROPERTY_NAME);
      URL configUrl;
      if (Boolean.parseBoolean(testEnv)) {
        configUrl = BokslPortalApplication.class.getResource(CONFIG_PROPERTIES_TEST);
      } else {
        configUrl = BokslPortalApplication.class.getResource(CONFIG_PROPERTIES);
      }
      EnvirmentProperty.init(configUrl);
      UserService userService = BeanUtils.getBean(UserService.class);
      userService.init();

      MemoService memoService = BeanUtils.getBean(MemoService.class);
      memoService.init();
    };
  }

}
