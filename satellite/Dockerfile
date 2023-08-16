FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/satellite-0.0.1-SNAPSHOT.jar satellite.jar

EXPOSE 8082

CMD ["java", "-jar", "satellite.jar"]
