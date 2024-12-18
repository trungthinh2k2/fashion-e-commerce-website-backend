FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install

FROM openjdk:17-slim
WORKDIR /app
COPY vietnamese-stopwords.txt .
COPY --from=build /app/target/fashion-e-commerce-website-backend-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8080
CMD ["java", "-Duser.timezone=Asia/Ho_Chi_Minh","-jar", "app.jar"]