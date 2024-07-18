FROM openjdk:21
RUN microdnf install redis
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
CMD redis-server --daemonize yes && java -jar -Dspring.profiles.active=prod,spring-data-jpa /app.jar
