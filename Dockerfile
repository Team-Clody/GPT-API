FROM amd64/amazoncorretto:21
WORKDIR /app
VOLUME ["/log"]
COPY ./build/libs/GPT-API-0.0.1-SNAPSHOT.jar /app/gpt.jar
CMD ["java","-Dspring.profiles.active=dev", "-jar", "gpt.jar"]

