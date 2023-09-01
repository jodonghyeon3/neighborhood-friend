FROM openjdk:11-jre-slim
ARG JAR_FILE=build/libs/*.jar
COPY build/libs/neighbor-friend.jar neighbor-friend.jar
ENTRYPOINT ["java", "-DSpring.profiles.active=prod", "-jar" ,"neighbor-friend.jar"]