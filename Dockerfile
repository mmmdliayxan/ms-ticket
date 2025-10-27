FROM eclipse-temurin:19-jdk

WORKDIR /app

COPY build/libs/ms-ticket-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]