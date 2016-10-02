#!/bin/bash
cd "$(dirname $0)"
peg scp to-rem literate-garbanzo 3 target/firehose-0.0.1-jar-with-dependencies.jar /home/ubuntu
peg scp to-rem literate-garbanzo 3 src/main/resources/hose.properties /home/ubuntu
peg scp to-rem literate-garbanzo 3 src/main/resources/kafka.properties /home/ubuntu
peg scp to-rem literate-garbanzo 3 src/main/resources/s3files.txt /home/ubuntu
peg scp to-rem literate-garbanzo 3 runFirehose.sh /home/ubuntu
