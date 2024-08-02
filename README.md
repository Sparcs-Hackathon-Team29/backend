# API 문서

## 엔드 포인트

### 사용자 인증
**1. 회원가입**
   - url : `/join`
   - method : `POST`
   - 설명 : 회원가입
   - request
     ```
      "username": "user",
      "password": "a123456789"
     ```

**2. 로그인**
   - url : `/login`
   - method : `POST`
   - 설명 : 로그인
   - request
     ```
      "username": "user",
      "password": "a123456789"
     ```

**3. 로그아웃**
   - url : `/logout`
   - method : `POST`
   - 설명 : 로그아웃

### AI 여행 추천

**4. 클로바 X 여행 생성**
   - url : `/api/completion`
   - method : `POST`
   - 설명 : 키워드를 통해서 클로바 X를 이용한 여행 생성
   - request
     ```
      location: sixthfeedback,  // 지역
      time: seventhfeedback,    // 시간
      keywords: keywords,       // 키워드 배열
     ```
**5. 클로바 X 여행 삭제**
   - url : `/api/recommend/delete`
   - method : `DELETE`
   - 설명 : 클로바 X를 이용한 여행 삭제

### 데이터 조회
**6. 여행 조회**
   - url : `/api/recommend/{username}
   - method : `GET`
   - 설명 : 사용자가 생성한 여행 조회
   - response
     ```
      location: sixthfeedback,  // 지역
      time: seventhfeedback,    // 시간
      keywords: keywords,       // 키워드 배열
     ```

**6. 랜덤 여행 추천**
   - url : `/api/random/
   - method : `GET`
   - 설명 : 사용자가 랜덤으로 생성한 여행 조회
   - request

     
