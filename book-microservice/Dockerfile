FROM openjdk:17-jdk-slim
LABEL authors="saul_ramirez"
ARG JAR_FILE=target/book-microservice-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_book_microservice.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app_book_microservice.jar"]