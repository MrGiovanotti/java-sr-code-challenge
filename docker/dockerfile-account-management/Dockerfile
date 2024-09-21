FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY account-management-0.0.1-SNAPSHOT.jar /app/account-management.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "account-management.jar"]