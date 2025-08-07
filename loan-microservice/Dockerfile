FROM openjdk:17-jdk-slim
LABEL authors="saul_ramirez"
ARG JAR_FILE=target/loan-microservice-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_loan_microservice.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app_loan_microservice.jar"]