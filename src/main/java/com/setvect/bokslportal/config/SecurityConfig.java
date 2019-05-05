package com.setvect.bokslportal.config;

import com.setvect.bokslportal.BokslPortalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;


/**
 * 보안 관련(spring security) 설정.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  /**
   * 사용자 정보 관리.
   */
  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  private UserDetailsService userDetailsService;

  @Override
  @Autowired
  public void configure(final AuthenticationManagerBuilder auth) throws Exception {
    PasswordEncoder passwordEncoder = passwordEncoder();
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  @Override
  public void configure(final WebSecurity web) throws Exception {
    web.ignoring()
      .antMatchers("/assets/**")
      .antMatchers("/index.html")
      .antMatchers("/html/**")
      .antMatchers("/user/login.do")
      .antMatchers("/user/logout.do")
      .antMatchers("/user/info.json")  // TODO 삭제
      .antMatchers("/static/**")
      .antMatchers("/h2-console/**")
      .antMatchers("/error/**");
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http
      .csrf().disable()
      .authorizeRequests()
      .antMatchers("/user/login").permitAll()
      .antMatchers("/user").hasAuthority("USER")
      .antMatchers("/admin").hasAuthority("ADMIN")
      .anyRequest().authenticated()
      .and()
      .logout()
    ;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  /**
   * @return rememberMe 토근 생성 방식
   */
  @Bean
  public TokenBasedRememberMeServices tokenBasedRememberMeServices() {
    TokenBasedRememberMeServices tokenBasedRememberMeServices = new TokenBasedRememberMeServices(
      BokslPortalConstant.Login.REMEMBER_ME_KEY, userDetailsService);
    tokenBasedRememberMeServices.setCookieName(BokslPortalConstant.Login.REMEMBER_COOKIE_NAME);
    return tokenBasedRememberMeServices;
  }

  /**
   * @return 패스워드 암호화
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder;
  }
}