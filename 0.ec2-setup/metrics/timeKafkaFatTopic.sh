#!/bin/bash

# peg scp to-rem literate-garbanzo 1 ~/proj/literate-garbanzo/0.ec2-setup/metrics/timeKafkaFatTopic.sh /home/ubuntu
time /usr/local/kafka/bin/kafka-console-consumer.sh --zookeeper localhost:2181 --max-messages 10000 --topic gh_fat_topic > /dev/null
