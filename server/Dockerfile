FROM openjdk:8-jdk-alpine
MAINTAINER Yuicon <910722178@qq.com>
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]