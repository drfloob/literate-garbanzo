#!/bin/bash

peg service literate-garbanzo zookeeper start &
sleep 3
peg service literate-garbanzo kafka start &
sleep 5
peg service literate-garbanzo flink start &
