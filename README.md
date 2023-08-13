# neighborhood-friend

### 서비스 소개
이웃친구는 가까운 거리에 살고 비슷한 라이프스타일을 공유하는 사람들을 연결하기 위해 고안된 서비스입니다.
자신이 설정한 동네 내에서 개인 간의 유대관계를 맺는 것을 목표로 합니다.

---

### 사용기술
Spring, Jpa, Spring Security, Jwt, Mysql, Docker

---

### 프로그램 주요 기능
- 회원
  - 회원가입을 할 수 있습니다.
  - 주소 설정: 정확한 근접 매칭을 위해 회원의 현재 위치를 기준으로 두가지의 주소를 설정할 수 있습니다.
- 게시물
  - 약속 게시물 등록: 사용자는 특정 서비스 또는 활동을 요청하거나 제공하기 위해 게시물을 작성할 수 있습니다.
  - 댓글 기능: 사용자는 게시물에 댓글을 달아 관심을 표현하거나 질문하거나 추가 정보를 제공할 수 있습니다.
  - 리뷰 기능: 약속이 종료된 후 약속에 대한 리뷰를 작성할 수 있습니다.
  - 약속 : 사용자는 게시물에 대한 응답으로 약속을 요청한 사람들의 목록을 검색할 수 있습니다.
  - 약속 승인/거부: 게시물 작성자는 관심 있는 개인으로부터 받은 약속 요청을 승인하거나 거부할 수 있습니다.
<hr>

### 진행사항
[회원]
- [O] 회원 가입
- [O] 로그인 후 토큰 발급
- [O] 접속 IP 주소를 이용해 GeoLite를 통해 현재 주소의 좌표를 통해 가져오기
- [O] 카카오 api를 이용해 좌표를 상세주소로 변환

[게시글]
- [O] 약속 게시물 등록
- [O] 자신의 설정된 주소 기반으로 게시글 조회
- [O] 게시물 댓글 작성
- [O] 게시글을 통해 약속 신청유저 리스트 조회
- [O] 게시물 작성자가 약속 요청한 사람 승인/거절
- [O] 약속 종료 후 게시글에 대한 리뷰 / 평점

---
### ERD 설계
<img width="713" alt="image" src="https://github.com/jodonghyeon3/neighborhood-friend/assets/117457834/ec0c9211-8bf0-4d36-bef1-69fae5211c29">






