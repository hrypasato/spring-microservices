FROM openjdk:17-alpine3.14

WORKDIR /app

COPY ./build/libs/registry-0.0.1-SNAPSHOT.jar .

EXPOSE 8099

ENTRYPOINT [ "java", "-jar", "registry-0.0.1-SNAPSHOT.jar" ]