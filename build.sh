#!/bin/bash -ex
sbt clean assembly
cp target/scala-2.12/novice-assembly-*.jar target/novice.jar
docker-compose build
