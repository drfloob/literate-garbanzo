#!/bin/bash

# for fault tolerance, I'd beef up the disk storage and increase the
# retention significantly. I'd also potentially sink directly to long
# term storage.

# 30 second retention, GH firehose stream
/usr/local/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 3 \
--partitions 3 --config retention.ms=30000 --topic gh_fat_topic

# 30 second retention, GH skinny stream
/usr/local/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 2 \
--partitions 3 --config retention.ms=30000 --topic gh_skinny_topic


# 30 second retention, GH components
/usr/local/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 2 \
--partitions 3 --config retention.ms=30000 --topic gh_components

