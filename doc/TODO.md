# 복슬포털 - TODO

### 메모

- 멀티폼 validation: https://codesandbox.io/s/y3504yr0l1?initialpath=%2F%23%2Fscopes&module=%2Fsrc%2Fcomponents%2FScopes.vue

### 진행예정.
- NEW-20190913: DB 마이그레이션
- NEW-20190828: 복슬포토 메타정보 입력(촬영일, 메모)
- NEW-20190825: 복슬 포토 썸내일 이미지 처리 시 인증 토큰 받을 수 있게.
- NEW-20190815: 복슬지식 구현
- NEW-20190815: 복슬노트 구현
- NEW-20190707: webpack 빌드가 잘 안됨. 빌드가 이상하게 되어 빌드된 javascript로 실행이 안됨.(이 문제 나올 것 같았음. 왜 이렇게 힘들까?) - 대충보니 패스만 잘 잡아 주면 될 것 같음.
- NEW-20190626: PhotoRepositoryImpl 사용하지 않는 테이블 정리
- NEW-20190626: PageQueryCondition Builder 형태로 만들어 보는 거 고려
- NEW-20190626: 게시판 CLOB Lazy loadding
- NEW-20190626: 페이징 방식 변경
- NEW-20190622: ckeditor5-vue 높이 조절
- NEW-20190616: quill-editor fullscreen
- NEW-20190616: quill-editor 들어쓰기 단축기 적용
- NEW-20190616: quill-editor 클립보드 이미지 붙이기
- NEW-20190616: quill-editor html
- NEW-20190607: 페이지 접근 시 왼쪽 메뉴 활성화
- NEW-20190607: 로그인 세션 기록하기(remember-me)
- NEW-20190607: eslint 적용

### 완료
- NEW-20190913(20190914): smart_editor2 적용
- NEW-20190913(20190914): npm build 해서 spring boot 자체적으로 실행 가능하도록
- NEW-20190815(20190903): 복슬메모 구현
- NEW-20190815(20190828): 복슬포토 구현
- NEW-20190820(20190823): 로그인 - 비밀번호 입력 폼에서 엔터 누르면 로그인 액션 처리
- NEW-20190820: 대부분의 모듈에서 사용하는 (Swal, jquery, VueUtil)을 전역 선언
- NEW-20190815(20190818): 복슬관계 구현
- NEW-20190807(20190815): CommonUtil.popupError 팝업창 처리
- NEW-20190810(20190813): ajax 통신 시 Wait 구현 - spinner
- NEW-20190813: 메뉴 활성, 비활성 페이지 새로고침 해도 상태 유지
- PBM-20190813: 메뉴 버튼 누르면 레이아웃 깨지는 문제 수정
- NEW-20190720(20190813): TODO 구현
- NEW-20190811: vscode에서 _.vue, _.js 세미콜론 일관성 있게 셋팅. 세미콜로 나오도록 하기
- NEW-20190720: 결정장애 해결 구현
- NEW-20190717: 로또 구현
- NEW-20190606(20190707): 템플릿 만들기
  - 게시판 관리 => OK
  - 게시판 => OK
  - 로그인 => OK
  - 복슬지식 => Ok
  - 복슬노트 => OK
  - 복슬메모 => OK
  - 복슬포토 => OK
  - 복슬관계 => OK
  - 로또 => OK
  - 결정장애 해결 => OK
  - TODO => OK
- NEW-20190626: NoteCategoryVo 계층 구조 변경. 인터페이스 사용안하고 하는 방법 있음.
- NEW-20190626: Hibernate ID generator 방식 변경 -> sequence
- NEW-20190623(20190626): JPA VO 관계 구조 변경. FK 사용
- PBM-20190602: 왼쪽 메뉴 확장 시 블릿 이미지 표시가 메뉴를 덮어 버리는 문제
- PBM-20190602: 왼쪽 메뉴 2단계 확장 시 블릿 이미지 들어 쓰기 오류
