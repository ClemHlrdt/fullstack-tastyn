# Pull jre 11 image
FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /app

RUN mkdir -p ./static/avatars

# Move jar file to the file system of the container
ADD target/tastyn-api-1.0.jar app.jar
# run command to start the Spring Boot app
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=prod", "app.jar"]