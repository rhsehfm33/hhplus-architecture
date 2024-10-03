# ERD table
![hhplus-architecture](https://github.com/user-attachments/assets/9e87059e-83df-412b-a4f8-1f66362248fe)

# 설계 이유
## lecture_registrations (특강 신청 내역)

- (user_id, lecture_id): 복합키로 설정함으로써 중복된 특강 신청 방지
- created_at : 언제 신청했는지 확인 가능
- (**user_id, lecture_id**)에 따라 row를 삭제하면 손쉽게 특강 신청 해제 가능
- 따로 lectures 테이블에 정보를 두지 않음으로써 데이터 중복 방지

### lectures (특강)
- user_id : 강연자 user_id
- name : 특강 이름
- start_date_time : 특강 신청 시작 시각
- end_date_time : 특강 신청 종료 시각
- user 테이블과 분리시켜 특강 정보만 빠르게 수정할 수 있도록 함
- start_date_time, start_end_time 필드를 통해 신청 가능한 강의를 쉽게 조회 가능
- 단, user_id를 통해 상세한 강연자 정보가 필요할 때 매번 조인이 필요함

### users (사용자)
- email : 사용자 이메일 주소
- password : 사용자 비밀번호
- name : 사용자 이름