FROM openjdk:8u151-jre-alpine
ADD target/backend-0.0.1-SNAPSHOT.jar /app/backend.jar
WORKDIR /app
EXPOSE 9000
CMD ["java","-jar","backend.jar"]
