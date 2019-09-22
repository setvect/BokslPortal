package com.setvect.bokslportal;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 상수 정의.
 */
public final class BokslPortalConstant {

  /**
   * 테스트 실행 여부을 알수 있는 System property 이름
   */
  public static final String TEST_CHECK_PROPERTY_NAME = "test_run";
  ;

  /**
   * not instance.
   */
  private BokslPortalConstant() {
  }

  /**
   * js, css 등 웹 자원 파일 캐싱을 하지위한 값<br>
   * src="${request.contextPath}/boksl.js?<%=com.setvect.bokslmoney.BokslPortalConstant.CACHE_VER%>"
   */
  public static final long CACHE_VER = System.currentTimeMillis();

  /**
   * 로그인 관련 상수.
   */
  public static class Login {
    /**
     * 로그인 아이디(고정)
     */
    public static final String ID = "boksl";
    /**
     * remember 관련.
     */
    public static final String REMEMBER_ME_KEY = "bokslLoginKey";
    /**
     * remember 관련.
     */
    public static final String REMEMBER_COOKIE_NAME = "bokslCookie";

  }

  public static class Attach {
    /**
     * 파일 저장 기본 경로
     */
    public static final File BASE_DIR = new File(EnvirmentProperty.getString("com.setvect.bokslportal.attch.upload"));

    /**
     * 업로드 허용하는 확장자
     */
    public static final Set<String> ALLOW_EXT = new HashSet<>(Arrays.asList(EnvirmentProperty.getString("com.setvect.bokslportal.attch.allow_ext").split(",")));
    /**
     * 이미지 확장자
     */
    public static final Set<String> IMAGE_EXT = new HashSet<>(Arrays.asList(EnvirmentProperty.getString("com.setvect.bokslportal.attch.image_ext").split(",")));
  }

  /**
   * 포토 서비스 관련 상수.
   */
  public static class Photo {
    /**
     * 이미지 저장 기본 경로.
     */
    public static final File BASE_DIR = new File(EnvirmentProperty.getString("com.setvect.bokslportal.photo.upload"));

    /**
     * 썸네일 이미지 저장 기본 경로
     */
    public static final File THUMBNAIL_DIR = new File(
      EnvirmentProperty.getString("com.setvect.bokslportal.photo.thumbnail"));

    /**
     * 이미지 회전 보정 저장 기본 경로
     */
    public static final File ROTATE_DIR = new File(EnvirmentProperty.getString("com.setvect.bokslportal.photo.rotate"));

    /**
     * 허용 이미지 파일 확장자.
     */
    public static final Set<String> ALLOW = new HashSet<>(Arrays.asList("jpg", "png", "gif"));

    /**
     * GPS 좌표 추출 정규식
     */
    public static final String GPS_REGEX = "^(\\d*)\\W*(\\d*)\\W*((?:\\d|\\.)*)";

    /**
     * 메타정보 - 촬영일
     */
    public static final String DATE_TIME_ORIGINAL = "Date/Time Original";

    /**
     * 메타정보 - 위도
     */
    public static final String GPS_LATITUDE = "GPS Latitude";

    /**
     * 메타정보 - 경도
     */
    public static final String GPS_LONGITUDE = "GPS Longitude";
  }

  /**
   * WHERE 절 replace를 하기 위함.
   */
  public static final String SQL_WHERE = "{WHERE}";
}
