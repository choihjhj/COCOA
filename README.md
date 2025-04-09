# 📖 < COCOA WEBTOON > 서비스 소개
 이 프로젝트는 **Spring framework**와 **Mybatis**를 활용해 보고자 만든 프로젝트입니다.     
 회원가입, 로그인, 결제, 충전, 댓글, 좋아요 등의 간단한 **CRUD** 기능을 포함하고 싶었고, 이 기능들을 포함한 **웹툰 사이트**가 가장 적합한 주제라고 생각하여 웹툰 사이트로 주제를 설정하였습니다.       
 따라서 **COCOA WEBTOON** 사이트를 통해 사용자는 요일별 연재되는 유/무료 웹툰을 코코아 가상 충전/결제를 통해 즐길 수 있고,     
 이와 더불어 댓글과 좋아요, 보관함 기능을 통해
다양하게 즐길 수 있도록 구현하였습니다.   
<!--템플릿 엔진으로 JSP를 선택한 이유는 원래는 spring framework에 Thymeleaf를 dependencies추가 했었는데 spring에서는 타임리프가 잘 호환이 되지 않는 문제가 생겼고
타임리프는 스프링부트와 잘 맞는 템플릿 엔진이라는 것을 깨닫고 JSP로 다시 변경하게 되었습니다.-->
    
<br>
<br>
      
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

   <br>
   <br>    
      
## 🙂 프로젝트 기간 및 구성 인원
- **총 프로젝트 기간** : 2023.07 ~ 2023.10    
- **프로젝트 구성 인원 및 담당** : (Front 1명, Back 1명) 총 2명, Back-end [PL] 담당
 

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