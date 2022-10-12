# PythonKafkaSpringJpaMongoDBDocker

## Before everything

```
curl https://github.com/tchiotludo/akhq/releases/download/0.22.0/akhq-0.22.0-all.jar -o akhq.jar
mv akhq.jar ./kafka_akhq/copy/
```

## 10000 foot view

### Containers

| container number | content      |
| ---------------- | ------------ |
| 1                | producers    |
| 2                | kafka + akhq |
| 3                | consumers    |
| 4                | mongodb      |
| 5                | backend      |

## Start

To start everything use the launch.bat, or these commands:

```bash
mvn -f consumers/pom.xml install &&
mvn -f backend/pom.xml install &&
docker compose up
```

I like to build on host without use docker stage.

## TODO

[ ] backend dockerfile
[ ] backend mongodb connection using default docker network

## Remember

### utils commands

```bash
docker pull openjdk:18-jdk-alpine3.14
ln -s <original file> <link to file>
docker build -t consumers .
docker run -d -p 9092:9092 -p 8081:8080 <image name>
docker run -ti consumers /bin/bash   # cool
docker exec -ti cf600fc6f7c409419284069050a736b75010044141375dcd560826a7170d20dc /bin/bash

# debug network utils
nmap -p9092 172.17.0.0/24
ping kafka_akhq
```

### utils tool

```
https://sed.js.org/
```

# Questions

- Should You Run Your Database in Docker? Not in production. Could be useful in dev.
