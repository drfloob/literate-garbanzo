#!/bin/bash

peg service literate-garbanzo flink stop &
sleep 2
peg service literate-garbanzo kafka stop &
sleep 5
peg service literate-garbanzo zookeeper stop &
