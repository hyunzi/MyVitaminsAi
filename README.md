# My Vitamin AI

### 요약
나만의 비타민 AI 솔루션 서비스

### 기능 
#### `1. 오늘의 추천` <br>
사용자가 이전에 조회한 영양제 또는 오늘의 추천 영양제 정보를 상단에 제공합니다.

#### `2. 영양제를 물어봐요`
사용자가 영양제에 대한 질문을 할 때, 증상 및 현재 복용 중인 영양제 정보를 기반으로 컨설팅 서비스를 제공합니다.

### 실행방법
1. frontend 실행
```npm
# front 경로로 이동
cd front

# frontend 실행
npm start
```
2. backend 실행
```
# api 경로로 이동
cd api

# Maven clean package
.\mvnw clean package

# Application 실행
mvnw spring-boot:run
```

3. 웹에서 확인
 > http://localhost:3000

### API 설명
* /api/consult
> 요청: POST 요청으로 JSON 형식으로 질문 내용을 전송합니다.

> 응답: JSON 형식으로 컨설팅 내용, 권장 영양제, 관련 링크, 그리고 사용자에게 추천할 수 있는 영양제 목록을 제공합니다.

* /api/recommend
> 요청: GET 요청으로 JSON 형식으로 사용자의 현재 상태 정보를 전송합니다.

> 응답: JSON 형식으로 사용자에게 추천할 수 있는 영양제 목록, 그리고 각 영양제에 대한 간략한 설명, 효능, 그리고 관련 링크를 제공합니다.
