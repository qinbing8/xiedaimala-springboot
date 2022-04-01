FROM openjdk:8u212-alpine

RUN mkdir /app

WORKDIR /app

COPY target/spring-boot-0.0.1-SNAPSHOT.jar /app

EXPOSE 8081

CMD [ "java", "-jar", "spring-boot-0.0.1-SNAPSHOT.jar" ]