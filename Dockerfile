FROM openjdk:8-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod +x mvnw
RUN ./mvnw install -DskipTests

FROM openjdk:8-jdk-alpine as production
VOLUME /tmp

COPY --from=build /workspace/app/target/powerdatabackend-0.0.1-SNAPSHOT.jar app.jar

ENV SHARED_ROOT /data
ENV ALGORITHM_URL http://192.168.1.115:5000

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
