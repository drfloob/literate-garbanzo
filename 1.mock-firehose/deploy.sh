#!/bin/bash
cd "$(dirname $0)"

if [ -f firehose.tar.gz ]; then
    rm firehose.tar.gz
fi

DIR=$(mktemp -d)

cp target/firehose-0.0.1-jar-with-dependencies.jar $DIR/
cp src/main/resources/hose.properties $DIR/
cp src/main/resources/kafka.properties $DIR/
cp src/main/resources/s3files.txt $DIR/
cp runFirehose.sh $DIR/
cp masterRunner.remote.sh $DIR/

tar -cvzf firehose.tar.gz -C $DIR .

rm -r $DIR

peg scp to-rem literate-garbanzo-producers 1 firehose.tar.gz /home/ubuntu/
peg sshcmd-node literate-garbanzo-producers 1 "scp firehose.tar.gz 172.31.0.5:"
peg sshcmd-cluster literate-garbanzo-producers "tar -xvzf firehose.tar.gz"
