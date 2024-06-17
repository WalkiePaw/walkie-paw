# 프로젝트 개요

### 팀명

타임스톤💎

### 팀원

박세진, 권채린, 김현준, 김선종, 손창우

### 프로젝트명

Walkie Paw 🐾

### 프로젝트 기간

2024.06.10 ~ 2024.07.19

### 프로젝트 소개

반려동물은 단순한 가족 구성원 이상의 의미를 지닙니다. 그러나 바쁜 생활 패턴으로 인해 반려견 산책은 종종 소홀히 되기 쉽습니다. WalkiePaw는 반려견 산책 전문 서비스를 제공하여 이 문제를 해결하고자 합니다.

WalkiePaw를 통해 견주들은 신뢰할 수 있는 산책인을 쉽게 찾을 수 있습니다. 위치기반 매칭 시스템을 활용하여 가까운 곳에서 활동 중인 산책인 검색을 검색할 수도 있습니다. 채팅을 통해 산책인과 직접 대화하며 자세한 사항을 조율해 보세요.

또한 산책인 프로필과 평가 시스템을 갖추고 있어, 견주들이 안심하고 산책인을 선택할 수 있습니다. 산책 후에는 간편한 결제 프로세스를 거치면 됩니다.

WalkiePaw를 통해 반려견 산책은 더 이상 부담이 되지 않을 것입니다. 반려견과 가족 모두가 행복해질 수 있는 기회를 만들어보세요!

### 역할 분담

프론트엔드: 박세진, 권채린, 김현준

백엔드: 김선종, 손창우

### 기술 스택

- 협업 도구 : ERDCloud, Figma, Notion, Slack
- 버전 관리 도구 : Git, GitHub
- Backend : Java, SpringBoot, MSA, JPA(ORM)
- Frontend : HTML, CSS, Javascript, React, node.js
- 테스트 도구 : MySQLWorkbench, JUnit

### **구현 계획**

1. **Spring Boot 프로젝트 설정**
    - 필요한 종속성으로 Spring Boot 프로젝트 초기화
    - 데이터베이스 연결 구성
2. **JPA 엔티티 생성**
    - 데이터베이스 스키마에 해당하는 JPA 엔티티 정의
    - JPA 어노테이션을 사용해 엔티티 간 관계 설정
3. **레포지토리 레이어 개발**
    - 엔티티에 대한 CRUD 작업을 위한 레포지토리 생성
4. **서비스 레이어 구현**
    - 비즈니스 로직을 위한 서비스 개발
    - 트랜잭션 무결성 보장 및 예외 처리
5. **REST 컨트롤러 빌드**
    - 프론트엔드 상호작용을 위한 엔드포인트 공개
    - 인증 및 권한 부여 구현
6. **프론트엔드 통합**
    - 프론트엔드와 백엔드 서비스 연결
    - WebSockets 등을 사용한 실시간 업데이트 및 알림 보장
7. **테스트 및 배포**
    - 단위 및 통합 테스트 작성
    - 클라우드 제공자 또는 온프레미스 서버에 애플리케이션 배포

### **주요 기능**

1. **사용자 등록 및 인증:**
    - 반려동물 주인 및 산책사 계정 생성 및 관리
    - 안전한 인증 시스템
2. **프로필 관리:**
    - 사용자 프로필 및 반려동물 정보 관리
    - 산책사 서비스, 가능 시간, 요금 목록
3. **예약 시스템:**
    - 반려동물 산책 예약
    - 일정 관리 통합
4. **결제 시스템:**
    - 안전한 결제 게이트웨이
    - 거래 내역 및 인보이스
5. **리뷰 및 평가:**
    - 산책사에 대한 리뷰 및 평가 제공
    - 서비스 품질 유지 피드백 시스템
    
### **데이터베이스 스키마 (ERD 기반)**
    
1. 회원 테이블 (`member`)
    
    - **회원ID (member_id)**: int, PK
    - **이름 (member_name)**: varchar(10)
    - **이메일 (email)**: varchar(30)
    - **비밀번호 (password)**: varchar(255)
    - **전화번호 (tel)**: varchar(20)
    - **주소 (addr)**: varchar(255)
    - **성별 (gender)**: varchar(2)
    - **가입일 (created_date)**: LOCALDATE
    - **생일 (birth)**: LOCALDATE
    - **자기소개 (profile)**: varchar(50)
    - **프로필 사진 (member_photo)**: varchar(255)
    - **상태 (status)**: enum
    - **탈퇴일 (deleted_date)**: LOCALDATE
    - **신고횟수 (reported_cnt)**: int
    
2. 게시글 테이블 (`board`)
    
    - **게시글ID (board_id)**: int, PK
    - **회원ID (member_id)**: int, FK
    - **제목 (title)**: varchar(50)
    - **내용 (content)**: varchar(255)
    - **금액 (price)**: int
    - **카운트발생 (meeting_time)**: LOCALDATE
    - **작성일 (created_date)**: LOCALDATE
    - **조회수 (view_count)**: int
    - **좋아요수 (like_count)**: int
    - **상태 (status)**: enum
    
3. 게시글 사진 테이블 (`board_photo`)
    
    - **사진ID (board_photo_id)**: int, PK
    - **게시글ID (board_id)**: int, FK
    - **원본사진명 (ori_name)**: varchar(255)
    - **UUID 사진명 (uuid_name)**: varchar(255)
    
4. 게시글 좋아요 테이블 (`board_like`)
    
    - **회원ID (member_id)**: int, PK, FK
    - **게시글ID (board_id)**: int, PK, FK

5. 리뷰 테이블 (`review`)
    
    - **리뷰ID (review_id)**: int, PK
    - **리뷰내용 (review_content)**: varchar(255)
    - **리뷰점수 (point)**: int
    - **작성일 (created_date)**: LOCALDATE
    - **삭제일 (deleted_date)**: LOCALDATE
    - **구매회원ID (member_id2)**: int, FK
    - **판매회원ID (member_id)**: int, FK
    - **구인구직게시글ID (board_id)**: int, FK
    - **평점 (score)**: int
    
6. 채팅방 테이블 (`chatroom`)
    
    - **채팅방ID (chatroom_id)**: int, PK
    - **구매회원ID (member_id)**: int, FK
    - **판매회원ID (member_id2)**: int, FK
    - **상태 (status)**: enum
    - **구인구직게시글ID (board_id)**: int, FK
    
7. 채팅 메시지 테이블 (`chat_message`)
    
    - **채팅방ID (chatroom_id)**: int, FK
    - **읽음여부 (is_read)**: tinyint(2)
    - **보낸시간 (created_date)**: LOCALDATE
    - **내용 (content)**: varchar(255)
    
8. 채팅 사진 테이블 (`chat_photo`)
    
    - **사진ID (chat_photo_id)**: int, PK
    - **원본사진명 (ori_name)**: varchar(255)
    - **UUID 사진명 (uuid_name)**: varchar(255)
    - **채팅방ID (chatroom_id)**: int, FK
    
9. 게시글 신고 테이블 (`board_report`)
    
    - **게시글신고ID (board_report_id)**: int, PK
    - **신고한회원ID (member_id)**: int, FK
    - **신고당한게시글ID (board_id)**: int, FK
    - **제목 (title)**: varchar(100)
    - **내용 (content)**: varchar(255)
    - **신고일 (reported_date)**: LOCALDATE
    - **신고 이유 (reason)**: varchar(10)
    
10. 회원 신고 테이블 (`member_report`)
    
    - **회원신고ID (member_report_id)**: int, PK
    - **신고한회원ID (member_id)**: int, FK
    - **신고당한회원ID (member_id2)**: int, FK
    - **제목 (title)**: varchar(100)
    - **내용 (content)**: varchar(255)
    - **신고일 (reported_date)**: LOCALDATE
    - **신고 이유 (reason)**: varchar(10)
    
11. 문의 테이블 (`qna`)
    
    - **문의ID (qna_id)**: int, PK
    - **회원ID (member_id)**: int, FK
    - **제목 (title)**: varchar(50)
    - **내용 (content)**: varchar(255)
    - **문의일 (qna_date)**: LOCALDATE
    
12. 알림 테이블 (`notification`)
    
    - **알림ID (notification_id)**: int, PK
    - **회원ID (member_id)**: int, FK
    - **알림시간 (notification_time)**: LOCALDATETIME
    - **알림여부 (is_read)**: tinyint(2)
    
13. 지역 설정 테이블 (`region_setting`)
    
    - **회원ID (member_id)**: int, FK
    - **기준 지연ID (reference_area_id)**: int
    - **인증여부 (authenticate)**: int
    - **반경 범위 (distance_meters)**: int
    - **지역 설정 (polygon)**: polygon
