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
* [UI 템플릿 - vue-admin-template](https://github.com/PanJiaChen/vue-admin-template)


### IDE 셋팅 방법
공통
* 그래드 플러그인 설치
* npm 설치

인텔리제이(IDEA 2018.3.2 기준)

1. lombok plugin 설치
1. project settings -> Build, Execution, Deployment -> Compiler에서 Build Project automatically 체크
1. project settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors에서 Enable annontation processing 체크  

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
1. `gradle clean`
2. `gradle bootWar` 이게 잘 안되면 `gradlew wrapper --gradle-version 4.10 bootWar`

### 개발 실행

### 주요 화면