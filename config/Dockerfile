FROM openjdk:17-alpine3.14

WORKDIR /app

COPY ./build/libs/config-0.0.1-SNAPSHOT.jar .

EXPOSE 8081

ENTRYPOINT [ "java", "-jar", "config-0.0.1-SNAPSHOT.jar" ]