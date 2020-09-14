# 프로젝트 구성
Spring boot

JPA

H2 Database


# 핵심 문제 해결 전략
1. 확장을 고려한 객체지향 설계
2. 메인 컨트롤러: SprinkleController
 - /api/sprinkle/send : 뿌리기 API (Http Method: POST)
 - /api/sprinkle/receive : 받기 API (Http Method: POST)
 - /api/sprinkle/ : 조회 API (Http Method: GET)
3. 애플리케이션 실행시 초기 데이터 적재

 
 
