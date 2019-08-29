# Kafka
Useful informations about Kafka

# Installation (for scripts)
https://kafka.apache.org/downloads

# Use cases
## Start inside docker instance
Start zookeeper and 3 instances (Dev mode)
```bash
docker run -d --net=host --name=zookeeper -e ZOOKEEPER_CLIENT_PORT=32181 -e ZOOKEEPER_TICK_TIME=2000 -e ZOOKEEPER_SYNC_LIMIT=2 confluentinc/cp-zookeeper
docker run -d --net=host --name=kafka-01 -e KAFKA_ZOOKEEPER_CONNECT=localhost:32181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 -e KAFKA_BROKER_ID=1 confluentinc/cp-kafka
docker run -d --net=host --name=kafka-02 -e KAFKA_ZOOKEEPER_CONNECT=localhost:32181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9093 -e KAFKA_BROKER_ID=2 confluentinc/cp-kafka
docker run -d --net=host --name=kafka-03 -e KAFKA_ZOOKEEPER_CONNECT=localhost:32181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9094 -e KAFKA_BROKER_ID=3 confluentinc/cp-kafka
```

## Create topics
```bash
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 2 --partitions 3 --topic TOPIC_NAME
```

## List topics
```bash
bin/kafka-topics.sh --list --zookeeper localhost:32181
```

## Describe topic
```bash
bin/kafka-topics.sh --describe --bootstrap-server localhost:9092 --topic TOPIC_NAME
```

## Consume from begining
```bash
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic TOPIC_NAME --from-beginning
```

## Consume everything
```bash
bin/kafka-consumer-groups --bootstrap-server localhost:9092 --group GROUP_NAME --topic TOPIC_NAME --reset-offsets --to-earliest --execute
```

# Kafka Connect
https://docs.confluent.io/5.2.1/connect/userguide.html#connect-configuring-workers

https://docs.confluent.io/current/connect/kafka-connect-s3/configuration_options.html

## Run
```bash
bin/connect-standalone.sh config/connect-standalone.properties INSTALL_PATH/WORKER.properties
```
