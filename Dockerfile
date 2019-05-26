FROM maven:latest as builder
EXPOSE 8080

ENV MAVEN_OPTS "-Dmaven.repo.local=/cache"
WORKDIR /app

COPY . /app
RUN mvn package

FROM openjdk:8-jre-stretch

COPY --from=builder /app/target/demo-0.0.1-SNAPSHOT.jar /app.jar
CMD java -jar /app.jar


