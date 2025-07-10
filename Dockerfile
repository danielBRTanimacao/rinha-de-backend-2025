FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM eclipse-temurin:21

WORKDIR /payment

COPY --from=builder target/PaymentProcessor-0.0.1-SNAPSHOT.jar /payment.jar

ENTRYPOINT ["java","-jar","/payment.jar"]
