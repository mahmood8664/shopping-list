FROM azul/zulu-openjdk:17.0.2
COPY ./target/*.jar ./app.jar
EXPOSE 8761
CMD ["sh", "-c", "java -jar ./app.jar"]
