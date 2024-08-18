
FROM openjdk:17-jdk-slim


WORKDIR /app


COPY target/credit-0.0.1-SNAPSHOT.jar app.jar


RUN apt-get update && \
    apt-get install -y postgresql-client


ENTRYPOINT ["java", "-jar", "app.jar"]