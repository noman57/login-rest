
# Our base image, it’s something like extend keyword in Java. Here we want to base on the image with Java 8 installed
#FROM java:8
#FROM java:8-alpine
FROM frolvlad/alpine-oraclejdk8:slim
#FROM isuper/java-oracle:jdk_8
#FROM jeanblanchard/java:8

MAINTAINER Md. Moin Uddin <moensam@gmail.com>

# install some useful tools (e.g., complete implementations of grep, sed, awk, bc, head, tail, and etc).
#RUN apk add --no-cache bash gawk sed grep bc coreutils
#RUN apk add --update bash && rm -rf /var/cache/apk/*

# We added a VOLUME pointing to "/tmp" because that is where a Spring Boot application creates working directories for
# Tomcat by default. The effect is to create a temporary file on your host under "/var/lib/docker" and link it to the
# container under "/tmp". This step is optional for the simple app that we wrote here, but can be necessary for other
# Spring Boot applications if they need to actually write in the filesystem.
VOLUME /tmp

# Port number our app will be started, it will be available from the outside of the Docker container
EXPOSE 8080

# helper variable with a user name we will use to run our application
ENV USER_NAME ufril
# next helper variable with directory where our app will be located
ENV APP_HOME /home/$USER_NAME/app
# create user with a name defined in $USER_NAME
#RUN useradd -ms /bin/bash $USER_NAME
RUN addgroup -g 999 $USER_NAME && adduser -D  -G $USER_NAME -s /bin/false -u 999 $USER_NAME
# create app directory
RUN mkdir $APP_HOME

# add fat-jar to our image and put it in $APP_HOME
ADD ufril-rest-api.jar $APP_HOME/app.jar

# all previous commands were executed as ROOT so we need to change owner of our jar file to $USER_NAME.
# Generally using ROOT in Docker is considered as wrong approach because of the security.
RUN chown $USER_NAME $APP_HOME/app.jar

# change user and working directory to ones we want to use to run our application
USER $USER_NAME
WORKDIR $APP_HOME

# You can use a RUN command to "touch" the jar file so that it has a file modification time (Docker creates all
# container files in an "unmodified" state by default). This actually isn’t important for the simple app that we wrote,
# but any static content (e.g. "index.html") would require the file to have a modification time.
RUN sh -c 'touch app.jar'

# To reduce Tomcat startup time we added a system property pointing to "/dev/urandom" as a source of entropy.
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]