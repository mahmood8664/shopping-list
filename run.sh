#!/bin/sh
./mvnw clean package
docker-compose -f ./fast-build/docker-compose.yml up --build