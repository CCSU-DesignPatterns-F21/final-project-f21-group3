# syntax=docker/dockerfile:1

# using multistage docker build
# ref: https://docs.docker.com/develop/develop-images/multistage-build/
    
# temp container to build using gradle
FROM gradle:6.8.3-jre15 AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY /lib/build.gradle settings.gradle $APP_HOME
  
COPY gradle $APP_HOME/gradle
COPY --chown=gradle:gradle . /home/gradle/src
USER root
RUN chown -R gradle /home/gradle/src
    
RUN gradle build || return 0
COPY . .

RUN gradle clean build

    
# actual container
FROM openjdk:15-oracle
ENV ARTIFACT_NAME=racingBot-0.1.0-all.jar
ENV APP_HOME=/usr/app/
    
WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .
    
EXPOSE 443
ENTRYPOINT exec java -jar $APP_HOME/build/libs/${ARTIFACT_NAME}
