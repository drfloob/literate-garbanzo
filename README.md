# [Network Pulse][demo]

## Index

1. [Introduction](README.md#1-introduction)
2. [The Pipeline](README.md#2-the-pipeline)
 * 2.1 [Overview](README.md#21-overview)
 * 2.2 [Mock Firehose](README.md#22-mock-firehose)
 * 2.3 [Venturi](README.md#23-venturi)
 * 2.4 [Flink Connected Components](README.md#24-flink-connected-components)
 * 2.5 [RethinkDB](README.md#25-rethinkdb)
 * 2.6 [UI](README.md#26-ui)
3. [Performance](README.md#3-performance)




## 1. Introduction

Think: Facebook and Linkedin. These are giant networks, growing by the
second, and they play a key role in many of our lives. It's been the
case that if you want to do something interesting on the data that
drives these networks, you have to round up all your Petabytes in one
go and process it all over hours, if not days!

**Network Pulse is a distributed, fault-tolerant big data pipeline
that performs graph analysis over unbounded data streams. Streaming
graph analysis at the pace of change.** Its goal is to find clusters
of people who interact with each other, as they interact.

With this information, we can find who the most influential people are
*as they develop their influence*.

We can recommend new connections *when the connections are most
relevant*.

We can predict who would most easily bridge two separate groups, and
use that information to strengthen a community *when momentum is at its
peak*.

Network Pulse is a powerful prototype; the kind of technology that
will serve as a building block towards the realization of these
goals. I created Network Pulse in 3 weeks as part of the [Insight Data
Engineering Fellowship Program][InsightDE].



## 2. The Pipeline

### 2.1 Overview

### 2.2 Mock Firehose

### 2.3 Venturi

### 2.4 Flink Connected Components

### 2.5 RethinkDB

### 2.6 UI




## 3. Performance

At 20,000 events per second, Network Pulse chugs along without
complaint. There are 9 servers in total:

 * 3x RethinkDB cluster, with 3 partitions and 2 replications each
 * 2x Producers, which operate the mock firehose
 * 4x Kafka / Flink nodes, the workhorses of the pipeline

Originally prototyped 100% multitenant, I opted to test the limits of
this system before separating technologies, and I was quite happy with
the performance. This fairly simple setup can process 8 years worth of
GitHub Event data in about 7 hours, or roughly 2.5 Terabytes in 7
hours. The primary downside to a multitenant setup is the overhead of
a more complicated recovery situation, but for the sake of this
prototype, I felt it was worth acknowledging that drawback and moving
on.

The two most likely bottlenecks (venturi and flinkCC) are horizontally
scalable (via Kafka consumer groups and Flink parallelism,
respectively). I've been very impressed with both technologies, and I
look forward to watching (and helping) Flink mature.



[demo]: https://drfloob.com/pulse
[slides]: https://drfloob.com/pulse/slides
[InsightDE]: http://insightdataengineering.com/