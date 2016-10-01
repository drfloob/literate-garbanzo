#!/bin/bash
mvn clean package -Pbuild-jar
peg scp to-rem literate-garbanzo 1 flinkCC/target/flinkCC-0.0.1.jar /home/ubuntu
