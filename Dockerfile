FROM maven:3-eclipse-temurin-17-alpine AS builder
WORKDIR /app
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
COPY pom.xml pom.xml
RUN mvn dependency:go-offline

RUN ls -las
COPY . .
RUN mvn clean install -Dmaven.test.skip=true -Pskip-sonarqube

FROM builder AS base
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar

HEALTHCHECK --interval=30s --timeout=3s CMD curl --fail http://localhost:8080/actuator/health || exit 1
#CMD ["java", "-jar", "app.jar"]
CMD ["/bin/sh", "entrypoint.sh"]