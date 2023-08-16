FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/mcs-0.0.1-SNAPSHOT.jar mcs.jar

EXPOSE 8081

CMD ["java", "-jar", "mcs.jar"]
