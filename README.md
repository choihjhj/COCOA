# 📖 < COCOA WEBTOON > 서비스 소개
> Spring + MyBatis 기반 웹툰 서비스   
> 단순 CRUD를 넘어 동시성 제어, 쿼리 최적화, 커넥션 튜닝을 통해    
> 실제 서비스 환경의 문제 해결을 경험한 백엔드 프로젝트     

   
---
## 🎯 프로젝트 목적    

단순 기능 구현이 아닌, 실제 서비스에서 발생할 수 있는 문제를 해결하는 것을 목표로 개발했습니다.    
- DB 제약조건 기반 동시성 제어 설계
- 쿼리 성능 개선을 위한 인덱스 설계
- 커넥션 풀 튜닝을 통한 API 응답 속도 개선
- MyBatis 기반 SQL 최적화 경험 확보
    
👉 “문제 발생 → 원인 분석 → 구조적 해결” 경험에 집중한 프로젝트

---  
## 🙂 프로젝트 개요
- **프로젝트명** : COCOA
- **개발 기간** : 2023.07 ~ 2023.10    
- **프로젝트 구성 인원 및 담당** : (Front 1명, Back 2명) , Back-end 담당
- **서비스 설명** : 웹툰 조회, 댓글, 좋아요, 결제 기능을 제공하는 웹툰 서비스

---     
## 🚀 핵심 기능
 - 요일별 웹툰 목록 조회
 - 웹툰 에피소드 이미지 조회  
 - 댓글 CRUD + 좋아요 기능
 - 웹툰 유료 회차 구매 (포인트 기반) 
 - 보관함 (최근 본 / 구매 목록)

---      
## 💻 개발 환경 및 기술 스택
- **Backend Framework** : Spring (5.0.7.RELEASE)
- **Java Version** : 11
- **Build Tool** : Maven
- **Database** : ojdbc8 (버전 19.3.0.0)
- **Server** : TomCat v9.0
- **Server Port Number** : 8081
- **Editor** : STS 3 
- **Library** :
  - MyBatis 
  - Lombok 
  - JSP 



---
## ⚡ 기술적 문제 해결
### 1️⃣ 구매 동시성 제어 및 데이터 정합성 확보
**문제**      
- 동일 유저의 회차 중복 구매 발생 가능
- 동시 요청 시 포인트 차감과 구매 데이터 불일치

**해결**    
- DB 복합 UNIQUE KEY 적용 (userid, epid) 적용
- 포인트 차감 → 구매 insert 트랜잭션 처리로 원자성 보장
- DuplicateKeyException 기반 롤백 처리

    
**결과**    
- 멀티스레드 테스트 (동시 10건 요청) → 1건만 성공
- 데이터 정합성 확보 및 중복 구매 방지

---

### 2️⃣ 좋아요 기능 동시성 안정성 확보
**문제**      
- 좋아요 중복 삽입 및 likeCnt 불일치 발생 가능
- likecnt + 1 방식에서 race condition 발생

**해결**    
- DB 복합 UNIQUE KEY 적용 (userId, commentId) 적용
- 아요 수를 증가 방식이 아닌 COUNT 기반 재계산 방식으로 변경
- INSERT + COUNT UPDATE를 트랜잭션으로 처리

    
**결과**    
- 동시 요청 환경에서도 좋아요 수 정합성 유지
- 데이터 불일치 문제 제거

---
### 3️⃣ SQL 인덱스 최적화
**문제**      
- 댓글 / 웹툰 목록 조회 시 Full Scan 발생

**해결**    
- 쿼리 패턴 기반 복합 인덱스 설계
  - epcomment(epId, likeCnt DESC) → 베스트 댓글    
  - epcomment(epId, writeDate DESC) → 최신 댓글    
  - likecomment(userId, commentId) → 좋아요 여부 조회    

    
**결과**    
- 베스트 / 최신 댓글 조회 쿼리 성능 개선
- 불필요한 Full Scan 제거

---

### 4️⃣ HikariCP 커넥션 풀 성능 튜닝
**문제**      
- 메인 페이지 API 응답 시간 약 900ms 발생

**해결**    
- HikariCP 풀 설정 튜닝
  - minimumIdle 증가    
  - maximumPoolSize 조정    
  - connectionTestQuery 설정    
- DB 커넥션 초기화 최적화
    
**결과**    
- 932ms → 293ms (약 68% 성능 개선)

---
## 🧠 핵심 성과
- 동시성 문제 (구매 / 좋아요) DB 레벨에서 해결
- API 응답 속도 3배 이상 개선 (HikariCP 튜닝)
- SQL 인덱스 설계 경험 확보

---
## 🔮 향후 개선 방향
- Redis 캐싱 적용 (댓글 / 인기 웹툰)
- 이미지 CDN 분리 (정적 리소스 최적화)
- Spring Boot 마이그레이션
   <br>
   <br>
       
## 🧩 ERD 
![image](https://github.com/user-attachments/assets/25d70032-9e05-46cf-a90a-0ca92b2c81a3)

<br>
<br>
<br>

## ✨ 기능 별 페이지 구성 (메뉴 트리)
![image](https://github.com/user-attachments/assets/1de230e1-f158-4bc6-b007-c4e56d9c5f72)




## ✔️ Endpoints 
      
**HOME**    
|HTTP|URI|설명|   
|:------:|:---:|:---:|   
|GET|/|홈페이지 요청|    
|GET|/layout|특정 요일에 맞는 웹툰 목록 조회|       
|GET|/errorPage|에러 페이지 요청|       
      
**WEBTOON**    
|HTTP|URI|설명|   
|:------:|:---:|:---:|   
|GET|/toondetail|웹툰 상세 정보 및 에피소드 목록 조회| 
      
      
**PURCHASE**    
|HTTP|URI|설명|   
|:------:|:---:|:---:|   
|GET|/purchase|유료 에피소드 구매 전 에피소드 가격 및 사용자 포인트 잔액 조회|   
|POST|/purchase|유료 에피소드 구매| 

      
      
**CHARGE**    
|HTTP|URI|설명|   
|:------:|:---:|:---:|   
|GET|/charge|포인트 충전 페이지 요청|   
|POST|/charge|포인트 충전|    
      
      
**EPISODE**    
|HTTP|URI|설명|   
|:------:|:---:|:---:|   
|GET|/episode|에피소드 내용 및 댓글 조회|   
      
      
**EPCOMMENT**    
|HTTP|URI|설명|   
|:------:|:---:|:---:|   
|GET|/lastestComment|최신 댓글 목록 조회|  
|GET|/bestComment|베스트 댓글 목록 조회|  
|POST|/like/{commentId}|좋아요 추가|  
|DELETE|/removeLike/{commentId}|좋아요 삭제|  
|POST|/newcomment |댓글 추가|  
|DELETE|/removecomment |댓글 삭제|  
|PUT|/modifycomment |댓글 수정|   

      
      
**SEARCH**    
|HTTP|URI|설명|   
|:------:|:---:|:---:|   
|GET|/search|검색 페이지 요청|  
|GET|/search/ajax|검색어를 기반으로 웹툰 목록 조회|       
      
**TOONUSER**    
|HTTP|URI|설명|   
|:------:|:---:|:---:|   
|GET|/login|로그인 페이지 요청|  
|POST|/login|로그인|  
|POST|/signup|회원가입|  
|GET|/myinfo|마이 페이지 요청|  
|POST|/louout|로그아웃|  
|DELETE|/remove|회원탈퇴|  
|GET|/cocoahistory|포인트 사용 내역 목록 조회|       
|GET|/mystorage|사용자가 구매한 에피소드 목록 조회|     
<br>
<br>    

## 💥 COCOA WEBTOON 서비스 화면 
**메인 화면**     
- 요일에 해당하는 웹툰 목록 조회    
![image](https://github.com/user-attachments/assets/5300a1f0-8f1f-4172-a699-93ea440ee611)     

**웹툰 페이지 화면**      
- 해당 웹툰의 유/무료 회차 전체 목록을 확인할 수 있습니다.     
- 무료 회차는 전체 관람 가능하지만, 유료 회차는 로그인 후 포인트로 구매해야 관람 가능합니다.       
![image](https://github.com/user-attachments/assets/751adec4-1ddc-43af-b26f-cae14bf431bd)      
   
**회차 페이지 화면**      
- 웹툰의 회차를 클릭해서 접속할 시 웹툰을 감상할 수 있으며 댓글 작성, 수정, 삭제 및 베스트/최신순 댓글을 조회할 수 있습니다.
- 또한, 댓글 좋아요 기능도 이용할 수 있습니다. 
![image](https://github.com/user-attachments/assets/a888a59d-633a-42c5-b030-4f13962e4599)    
![image](https://github.com/user-attachments/assets/c396aac4-5483-42b6-815e-f23eb15a701f)    

**유료 회차 구매 페이지 화면**      
- 유료 회차를 구매하기 위한 페이지 입니다. 로그인이 되어 있는 사용자는 유료회차를 구매할 수 있습니다.
- 잔액 포인트 조회가 되며 만약 포인트 부족시에는 포인트 충전을 하고 온 뒤 구매 가능 하도록 하였습니다.
![image](https://github.com/user-attachments/assets/be314a22-53fe-421f-99d9-870aaaf56e3f)   

**포인트 충전 페이지 화면**  
- 포인트 충전은 가상으로 신용카드 번호와 비밀번호를 입력하도록 하여 충전이 가능하도록 구현하였습니다.
![image](https://github.com/user-attachments/assets/44294bd7-b121-4236-a0ad-f8ab8391ca47)    

**검색 페이지 화면**  
- 웹툰 검색을 할 수 있는 검색 페이지 입니다. 검색어에 해당하는 작가 및 작품이 전체 조회 되도록 구현하였습니다.
- 클릭 시 웹툰 페이지로 이동합니다. 
![image](https://github.com/user-attachments/assets/c62b8d49-234b-4d41-a051-a63a736e0595)     

**보관함 페이지 화면**  
- 사용자는 최근 감상한 웹툰들과 구매한 웹툰 목록을 보관함을 통해 조회할 수 있습니다.   
![image](https://github.com/user-attachments/assets/1b9c699f-1593-4e8c-b96e-5c58660dbcce)    
![image](https://github.com/user-attachments/assets/9fe52160-eade-4c9d-b26c-28ed1d157b93)    

 
**회원가입 및 로그인 화면**         
![image](https://github.com/user-attachments/assets/e253597a-95b1-4978-bd1d-7b326f77dc8d)     
![image](https://github.com/user-attachments/assets/e39ea55c-781a-4440-b448-d93ba2efdbb9)      

**마이페이지 화면**     
- 로그인 후 마이페이지 탭으로 이동하면 사용자 아이디와 포인트 잔액이 보여지고 포인트 충전, 포인트 사용 내역, 로그아웃, 회원탈퇴를 할 수 있습니다.      
![image](https://github.com/user-attachments/assets/703d663d-d814-4718-94db-bc8cdef20c31)

**포인트 사용 내역 페이지 화면**     
- 사용자는 포인트에 관한 충전 및 사용 내역을 최신순으로 조회할 수 있습니다.      
![image](https://github.com/user-attachments/assets/411da994-3fb0-4886-927b-d02e9f6b591b)     

<!--
## 👉 개선 사항
- 코코아 충전 기능을 카카오 결제 API를 연동하여 QR 단건 결제로 개선해 볼 것
-->