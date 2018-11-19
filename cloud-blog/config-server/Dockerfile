FROM frolvlad/alpine-oraclejdk8:slim
MAINTAINER Yuicon <910722178@qq.com>
VOLUME /tmp
ADD ./target/config-service-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]