FROM azul/zulu-openjdk:17.0.2 AS build-env
WORKDIR /app
COPY . .
RUN ./mvnw clean package

FROM azul/zulu-openjdk:17.0.2 as discovery
COPY --from=build-env /app/discovery/target/*.jar /usr/local/app/app.jar
WORKDIR /usr/local/app/
EXPOSE 8761
CMD ["sh", "-c", "java -jar ./app.jar"]

FROM azul/zulu-openjdk:17.0.2 as gateway
COPY --from=build-env /app/gateway/target/*.jar /usr/local/app/app.jar
WORKDIR /usr/local/app/
EXPOSE 8080
CMD ["sh", "-c", "java -jar ./app.jar"]

FROM azul/zulu-openjdk:17.0.2 as shopping-list-write
COPY --from=build-env /app/shopping-list-write/target/*.jar /usr/local/app/app.jar
WORKDIR /usr/local/app/
EXPOSE 8082
CMD ["sh", "-c", "java -jar ./app.jar"]

FROM azul/zulu-openjdk:17.0.2 as shopping-list-read
COPY --from=build-env /app/shopping-list-read/target/*.jar /usr/local/app/app.jar
WORKDIR /usr/local/app/
EXPOSE 8081
CMD ["sh", "-c", "java -jar ./app.jar"]
