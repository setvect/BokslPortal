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
1. `npm run prod`
1. `gradle clean`
1. war 파일 만들기
    1. `gradle bootWar` 이게 잘 안되면 `gradlew wrapper --gradle-version 4.10 bootWar`
    1. `gradle war` 이게 잘 안되면 `gradlew wrapper --gradle-version 4.10 war`

잘 되면
 - gradle bootWar 경우 _build/libs/bokslportal-x.x.x.war_ 
 - gradle war 경우 _build/libs/ROOT.war_ 
 
 이렇게 만들어짐

### 실행방법
- 일반 war 방식
    - 톰켓에 webapps디렉토리에 war 파일을 복사

- bootWar 방식
```
java -jar bokslportal-x.x.x.war
```

### 개발 실행

### 수정사항
- node_modules/bootstrap/dist/css/bootstrap.css 파일에서 아래 수정
    - .nav 클래스 'display: flex;' 속성 제거
    - 나도 node_modules 파일 직접 수정하면 안되는 거 알고 있다. 어쩔 수 없다. 어떻게 하는지 모르겠다.

### 주요 화면

### 주요 파일
