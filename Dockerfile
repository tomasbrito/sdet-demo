FROM --platform=linux/amd64 maven:3.9-eclipse-temurin-17

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

CMD ["mvn", "clean", "test"]