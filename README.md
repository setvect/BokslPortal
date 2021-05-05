복슬포털
=========================
그동안 개발 했던 복슬 프로젝트 모음<br>

### 주요기능

- NowHappy (개인홈피)
- 복슬지식
- 복슬노트
- 복슬메모
- 복슬포토
- 복슬관계

### 실행방법

### 주요 프레임워크 및 라이브러리

* Spring Boot: 2.1.4
* JPA
* H2 Database
* lombok
* [UI 템플릿 - bootstrap](https://bootstrap-vue.js.org/)
* [UI 템플릿 - gentelella](https://github.com/ColorlibHQ/gentelella)

### IDE 셋팅 방법

공통

* 그래드 플러그인 설치
* npm 설치

인텔리제이(IDEA 2018.3.2 기준)

1. lombok plugin 설치
1. project settings -> Build, Execution, Deployment -> Compiler에서 Build Project automatically 체크
1. project settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors에서 Enable annontation processing
   체크

이클립스

- 프로젝트 > 우클릭 > Configure > Add Gradle Nature

### 주요 설정 파일

- src/main/resources/application.properties
- config/index.js

### Webpack

```bash
npm install

# 웹팩 서버 시작 localhost:8081
npm run dev

# 웹팩 빌드. 빌드 경로: /src/main/webapp
npm run build

```

### 빌드 방법

```bash
npm run prod
gradle clean
gradle makeInstallFile
```

build/dest에 실행 파일 만들어짐

| 파일                        | 설명                                             |
| --------------------------- | ------------------------------------------------ |
| bin/BokslPortal.sh          | 윈도우                                           |
| bin/BokslPortal.bat         | linux 실행 스크립트 (실행 퍼미션 권한 부여 필요) |
| lib/BokslPortal-1.0.0.war   | 복슬포털 실행 어플리케이션                       |
| conf/BokslPortal.properties | 각종 설정 파일. DB 연결정보 셋팅                 |
| conf/log4j2.xml             | 로그 설정                                        |

### 개발 실행

### 수정사항

- node_modules/bootstrap/dist/css/bootstrap.css 파일에서 아래 수정
  - .nav 클래스 'display: flex;' 속성 제거
  - 나도 node_modules 파일 직접 수정하면 안되는 거 알고 있다. 어쩔 수 없다. 어떻게 하는지 모르겠다.

### 주요 화면

### 주요 파일
