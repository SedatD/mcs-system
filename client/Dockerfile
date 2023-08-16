FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/client-0.0.1-SNAPSHOT.jar client.jar

EXPOSE 8080

CMD ["java", "-jar", "client.jar"]
