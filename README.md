# neighborhood-friend

### 서비스 소개
이웃친구는 가까운 거리에 살고 비슷한 라이프스타일을 공유하는 사람들을 연결하기 위해 고안된 서비스입니다. \
다양한 활동을 통해 이웃친구를 만들 수 있습니다.

---

### 프로젝트 사용 기술 스택 & 라이브러리 사용 이유
<img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=Java&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">
<img src="https://img.shields.io/badge/JPA-green?style=for-the-badge&logo=JPA&logoColor=white">
<img src="https://img.shields.io/badge/mariadb-003545?style=for-the-badge&logo=mariadb&logoColor=white">
<img src="https://img.shields.io/badge/aws_EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white">
<img src="https://img.shields.io/badge/aws_S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=white">
<br>

| **기술 스택** | **사용이유** |
| :--- | :--- |
| GitHub Actions / CodeDeploy / Amazon S3 | 유저의 측면에서 서버가 중단되어 서비스를 이용하지 못하는 상황을 막고자 GitHub Action으로 빌드를 진행하고 빌드한 파일을 S3에 업로드한 후 Codedeploy에서 사전에 작성된 script를 기반으로 자동 배포를 할 수 있도록 구현 |
| JWT | - 권한 부여 :  사용자가 로그인하면 이후의 각 요청에는 JWT가 포함되어 사용자가 해당 토큰으로 허용된 경로, 서비스 및 리소스에 액세스할 수 있음 <br> - 정보 교환 :  JSON 웹 토큰은 당사자 간에 정보를 안전하게 전송하는 좋은 방법 JWT는 보낸 사람이 자신이 누구인지 확인할 수 있음 또한 헤더와 페이로드를 사용하여 서명을 계산하므로 내용이 조작되지 않았는지 확인할 수도 있음 

<br>

---

## Project Sturucture
```bash
src
├── config // 인증/인가
│   └──  EncoderConfig
│   └──  JwtFilter
│   └──  SecurityConfig
├── util // jwt토큰 
├── controller
├── domain
│   └── common
│   └── dto
│   └── form
│   └── model
│   └── repository
│   └── type
├── exception // 커스텀 예외
├── geoLite // 외부api
├── service // 상품 도메인
```
---


### API Document
http://3.34.133.145:8080/swagger-ui/index.html#/

---

### Architecture
<img width="614" alt="image" src="https://github.com/jodonghyeon3/neighborhood-friend/assets/117457834/65bfb1db-fd2e-4c6e-8b4d-43a5df70c791">


---

### 프로그램 주요 기능
- 회원
  - 회원가입을 할 수 있습니다.
  - 주소 설정: 회원은 두 개의 주소를 설정 할 수 있습니다. 주소는 현재 위치기반으로 설정할 수 있습니다.
- 게시물
  - 약속 게시글 등록: 회원은 다른회원들과 함께 하고싶은 활동에 대해 모집게시글을 작성할 수 있습니다.
    - ex) 술 마실 사람, 전시회 갈 사람, 자전거 탈 사람, 데이트 할 사람
  - 약속 게시글 조회 : 자신이 설정한 주소와 같은 동네의 약속 모집 중인 게시글을 최신순으로 조회할 수 있습니다.
  - 댓글 작성 : 약속 게시글에 댓글을 달아 관심을 표현하거나 질문할 수 있습니다.
  - 리뷰 작성 : 약속이 종료된 후 약속의 참여자는 약속에 대한 리뷰를 작성할 수 있습니다.
  - 약속 참여자 조회 : 게시글 작성자는 약속 게시글에 신청한 유저들의 목록을 조회할 수 있습니다.
  - 약속 승인/거부 : 게시글 작성자는 받은 약속 요청을 승인하거나 거부할 수 있습니다.
<hr>

### ERD 설계
<img width="500" alt="image" src="https://github.com/jodonghyeon3/neighborhood-friend/assets/117457834/d5952177-ae9e-4077-84b5-78dd44f38633">



