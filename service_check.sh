#!/bin/bash

peg sshcmd-cluster literate-garbanzo "ps -ef | egrep 'flink|kafka|venturi|firehose|zookeeper'"
