FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
ENV DB_USERNAME=drone
ENV DB_PASSWORD=drone
ENV DB_NAME=postgres
ENV DB_HOST=localhost
ENV DB_PORT=5437
ENV APP_PORT=8081
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.datasource.password=${DB_PASSWORD}", "-Dspring.datasource.username=${DB_USERNAME}", "-Dspring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}", "-Dserver.port=${APP_PORT}", "-jar", "app.jar"]