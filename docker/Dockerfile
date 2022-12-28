FROM openjdk:17-alpine
ARG JAR_FILE=payment-service.jar
COPY ${JAR_FILE} payment-service.jar
ENTRYPOINT ["java", "-jar", "payment-service.jar"]