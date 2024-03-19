FROM ubuntu:latest

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk

EXPOSE 8080

COPY --from=build ./app/target/*.jar ./reserva.jar

ENTRYPOINT ["java", "-jar", "reserva.jar"]