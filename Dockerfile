FROM openjdk:11-jre-slim
COPY build/libs/neighbor-friend.jar neighbor-friend.jar
ENTRYPOINT ["java", "-DSpring.profiles.active=prod", "-jar" ,"neighbor-friend.jar"]