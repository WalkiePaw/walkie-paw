# FROM openjdk:21
# ARG JAR_FILE=build/libs/*.jar
# COPY ${JAR_FILE} app.jar
# ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod, spring-data-jpa","/app.jar"]\

# 기본 이미지로 OpenJDK 21을 사용
FROM openjdk:21
# Redis 설치를 위한 필요한 패키지 설치
RUN apt-get update && apt-get install -y redis-server
# 작업 디렉토리 설정
WORKDIR /app
# JAR 파일 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
# Redis 서버 시작 스크립트 생성
RUN echo '#!/bin/bash\nservice redis-server start\nexec java -jar -Dspring.profiles.active=prod,spring-data-jpa /app/app.jar' > /app/start.sh \
    && chmod +x /app/start.sh
# 컨테이너 실행 시 start.sh 스크립트 실행
ENTRYPOINT ["/app/start.sh"]