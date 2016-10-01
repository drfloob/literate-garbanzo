#!/bin/bash

BASE="$(dirname $0)/.."
peg scp to-rem literate-garbanzo 1 $BASE/flinkCC/src/main/resources/flinkCC.properties /home/ubuntu
peg scp to-rem literate-garbanzo 2 $BASE/flinkCC/src/main/resources/flinkCC.properties /home/ubuntu
peg scp to-rem literate-garbanzo 3 $BASE/flinkCC/src/main/resources/flinkCC.properties /home/ubuntu
peg scp to-rem literate-garbanzo 4 $BASE/flinkCC/src/main/resources/flinkCC.properties /home/ubuntu

peg scp to-rem literate-garbanzo 2 $BASE/venturi/src/main/resources/kafka.properties /home/ubuntu
peg scp to-rem literate-garbanzo 2 $BASE/venturi/src/main/resources/venturi.properties /home/ubuntu

peg scp to-rem literate-garbanzo 3 $BASE/mockFirehose/src/main/resources/hose.properties /home/ubuntu
peg scp to-rem literate-garbanzo 3 $BASE/mockFirehose/src/main/resources/kafka.properties /home/ubuntu
peg scp to-rem literate-garbanzo 3 $BASE/mockFirehose/src/main/resources/s3files.txt /home/ubuntu

