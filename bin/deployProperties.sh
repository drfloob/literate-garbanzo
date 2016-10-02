#!/bin/bash

BASE="$(dirname $0)/.."

peg scp to-rem literate-garbanzo 3 $BASE/1.mock-firehose/src/main/resources/hose.properties /home/ubuntu
peg scp to-rem literate-garbanzo 3 $BASE/1.mock-firehose/src/main/resources/kafka.properties /home/ubuntu
peg scp to-rem literate-garbanzo 3 $BASE/1.mock-firehose/src/main/resources/s3files.txt /home/ubuntu

peg scp to-rem literate-garbanzo 2 $BASE/2.venturi/src/main/resources/kafka.properties /home/ubuntu
peg scp to-rem literate-garbanzo 2 $BASE/2.venturi/src/main/resources/venturi.properties /home/ubuntu

$BASE/3.flinkCC/deployProps.sh

