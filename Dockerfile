FROM maven:3.9.6-eclipse-temurin-17 as maven
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn dependency:go-offline -B
RUN mvn package

FROM openjdk:17
COPY --from=maven target/demoA-*.jar ./demoA.jar
CMD ["java", "-jar", "./demoA.jar"]