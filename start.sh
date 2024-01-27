#!/bin/bash

#Pull new changes
git pull

#Prepare Jar
mvn clean
mvn package -DskipTests

# Ensure, that docker-compose stopped
docker-compose stop

#Add environment variables
export SPRING_DATASOURCE_USERNAME=$1
export SPRING_DATASOURCE_PASSWORD=$2

#Start new deployment
docker-compose up --build -d