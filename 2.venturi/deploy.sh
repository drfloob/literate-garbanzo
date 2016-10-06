#!/bin/bash
cd "$(dirname $0)"

for i in {2,}; do
    peg scp to-rem literate-garbanzo $i src/main/resources/kafka.properties /home/ubuntu
    peg scp to-rem literate-garbanzo $i src/main/resources/venturi.properties /home/ubuntu
    peg scp to-rem literate-garbanzo $i runVenturi.sh /home/ubuntu
    peg scp to-rem literate-garbanzo $i target/venturi-0.0.1-jar-with-dependencies.jar /home/ubuntu
done
