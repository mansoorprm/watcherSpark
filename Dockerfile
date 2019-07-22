FROM openjdk:8-jdk-slim
COPY target/metricServer-jar-with-dependencies.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]