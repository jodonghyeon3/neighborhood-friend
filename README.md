# neighborhood-friend

### 서비스 소개
이웃친구는 가까운 거리에 살고 비슷한 라이프스타일을 공유하는 사람들을 연결하기 위해 고안된 서비스입니다. \
자신이 설정한 동네 내에서 개인 간의 유대관계를 맺는 것을 목표로 합니다.

---

## 프로젝트 사용 기술 스택
<img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=Java&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">
<img src="https://img.shields.io/badge/JPA-green?style=for-the-badge&logo=JPA&logoColor=white">
<img src="https://img.shields.io/badge/mariadb-003545?style=for-the-badge&logo=mariadb&logoColor=white">
<img src="https://img.shields.io/badge/aws_EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white">
<img src="https://img.shields.io/badge/aws_S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=white">
<br>

---

### API Document
http://3.34.133.145:8080/swagger-ui/index.html#/

---

### CI/CD
<img width="783" alt="image" src="https://github.com/jodonghyeon3/neighborhood-friend/assets/117457834/79b93fab-fdeb-404b-9376-186b1f9d7045">

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

### 진행사항
[회원]
- [O] 회원 가입
- [O] 로그인 후 토큰 발급
- [O] GeoLite api를 통해 접속 ip기반으로 현재 좌표 가져오기
- [O] 카카오 api를 통해 좌표를 상세주소로 변환

[게시글]
- [O] 약속 게시글 등록
- [O] 자신이 등록한 주소 기반으로 같은 동네의 게시글 조회
- [O] 게시글 댓글 작성
- [O] 게시글을 통해 약속 신청회원 리스트 조회 후 회원의 상세정보 확인
- [O] 게시글 작성자가 약속 요청한 회원 승인/거절
- [O] 약속 종료 후 약속에 대한 리뷰 / 평점

---
### ERD 설계
<img width="716" alt="image" src="https://github.com/jodonghyeon3/neighborhood-friend/assets/117457834/d5952177-ae9e-4077-84b5-78dd44f38633">






