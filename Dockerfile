FROM openjdk:21
EXPOSE 8080
COPY target/online-shop-api-0.0.1-SNAPSHOT.jar online-shop-api.jar
ENTRYPOINT ["java","-jar","/online-shop-api.jar"]
