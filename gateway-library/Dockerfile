FROM openjdk:17-jdk-slim
LABEL authors="saul_ramirez"
ARG JAR_FILE=target/gateway-library-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_gateway_library.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app_gateway_library.jar"]