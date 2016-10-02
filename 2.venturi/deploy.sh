#!/bin/bash
cd "$(dirname $0)"
peg scp to-rem literate-garbanzo 2 target/venturi-0.0.1-jar-with-dependencies.jar /home/ubuntu
peg scp to-rem literate-garbanzo 2 src/main/resources/kafka.properties /home/ubuntu
peg scp to-rem literate-garbanzo 2 src/main/resources/venturi.properties /home/ubuntu
peg scp to-rem literate-garbanzo 2 runVenturi.sh /home/ubuntu
