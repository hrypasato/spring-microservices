FROM openjdk:17-alpine3.14

WORKDIR /app

COPY ./build/libs/primermicro-0.0.1-SNAPSHOT.jar .

EXPOSE 8091

ENTRYPOINT [ "java", "-jar", "primermicro-0.0.1-SNAPSHOT.jar" ]