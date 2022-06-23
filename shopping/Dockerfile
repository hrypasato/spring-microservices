FROM openjdk:17-alpine3.14

WORKDIR /app

COPY ./build/libs/shopping-0.0.1-SNAPSHOT.jar .

EXPOSE 8093

ENTRYPOINT [ "java", "-jar", "shopping-0.0.1-SNAPSHOT.jar" ]