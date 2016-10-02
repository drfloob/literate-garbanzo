#!/bin/bash

BASE="$(dirname $0)/.."
$BASE/flinkCC/deployProps.sh

peg scp to-rem literate-garbanzo 2 $BASE/venturi/src/main/resources/kafka.properties /home/ubuntu
peg scp to-rem literate-garbanzo 2 $BASE/venturi/src/main/resources/venturi.properties /home/ubuntu

peg scp to-rem literate-garbanzo 3 $BASE/mockFirehose/src/main/resources/hose.properties /home/ubuntu
peg scp to-rem literate-garbanzo 3 $BASE/mockFirehose/src/main/resources/kafka.properties /home/ubuntu
peg scp to-rem literate-garbanzo 3 $BASE/mockFirehose/src/main/resources/s3files.txt /home/ubuntu

