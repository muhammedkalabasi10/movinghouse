FROM maven:3.9.6-amazoncorretto-21-al2023 AS build
WORKDIR /home/app
COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

FROM openjdk:21-jdk-slim
COPY --from=build /home/app/target/*.jar app.jar
COPY .env .env
EXPOSE ${PORT}
ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]