FROM openjdk:11
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=Pogoda_savtraBot
ENV BOT_TOKEN=5643534380:AAEETZ3BUT8cilHlhguAN-vhjyjthN_5DJM
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-jar", "/app.jar"]