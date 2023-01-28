FROM openjdk:17.0
VOLUME /tmp
EXPOSE 8080
ADD target/*.jar app.jar
RUN bash -c 'touch /app.jar'
ENV JAVA_OPTS='-Dspring.profiles.active=prod'
ENTRYPOINT exec java -server -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -jar /app.jar