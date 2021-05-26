FROM openjdk:8-jdk-alpine

EXPOSE 8080

ADD target/AuthorizationService-0.0.1-SNAPSHOT.jar MyApp.jar

ENTRYPOINT ["java", "-jar", "/MyApp.jar"]