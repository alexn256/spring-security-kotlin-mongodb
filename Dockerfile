FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml -DskipTests=true package

FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
COPY --from=build /home/app/target/kotlin-spring-security-1.0-SNAPSHOT.jar /usr/local/lib/kotlin-spring-security-1.0-SNAPSHOT.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","/usr/local/lib/kotlin-spring-security-1.0-SNAPSHOT.jar"]
