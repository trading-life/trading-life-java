FROM tradingview:java

WORKDIR /app
ADD target/trading.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar","--spring.profiles.active=pro"]
EXPOSE 10008