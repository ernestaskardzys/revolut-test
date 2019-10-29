FROM openjdk:11.0.4-jre-slim
COPY build/libs/revolut-test-all.jar /app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app.jar"]