FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY customer-management-0.0.1-SNAPSHOT.jar /app/customer-management.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "customer-management.jar"]