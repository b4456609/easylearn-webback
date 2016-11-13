#!bin/bash
./gradlew bootRepackage
docker build -t b4456609/easylearn-webback:latest -t b4456609/easylearn-webback:${1} .
