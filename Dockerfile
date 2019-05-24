FROM maven:latest
EXPOSE 8080

ENV MAVEN_OPTS "-Dmaven.repo.local=/cache"
WORKDIR /app

COPY . /app
CMD mvn spring-boot:run
