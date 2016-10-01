

## Getting Started

```bash
peg up ec2.setup/peg/quad/master.yml&
peg up ec2.setup/peg/quad/workers.yml&
```

Wait a few minutes for these to spin up, then install a bunch of great pipeline tools!

```bash
eval `ssh-agent -s`
peg fetch literate-garbanzo
peg install literate-garbanzo ssh
peg install literate-garbanzo aws
peg install literate-garbanzo zookeeper
peg install literate-garbanzo kafka
peg install literate-garbanzo hadoop
peg install literate-garbanzo flink
```

To configure flink:

```bash
MDNS=$(./ec2.setup/getMasterPublicDNS.sh)
sed -i "s/jobmanager\.rpc\.address.*/jobmanager.rpc.address: $MDNS/" ec2.setup/config/flink-conf.yaml

peg scp to-rem literate-garbanzo 1 ec2.setup/config/flink-conf.yaml /usr/local/flink/conf/
peg scp to-rem literate-garbanzo 2 ec2.setup/config/flink-conf.yaml /usr/local/flink/conf/
peg scp to-rem literate-garbanzo 3 ec2.setup/config/flink-conf.yaml /usr/local/flink/conf/
peg scp to-rem literate-garbanzo 4 ec2.setup/config/flink-conf.yaml /usr/local/flink/conf/

```

To configure kafka:

```bash
ZC=$(ec2.setup/kafkaZookeeperConnectStringBuilder.sh)
sed -i "s/zookeeper.connect=.*/zookeeper.connect=$ZC/" ec2.setup/config/server.properties

peg scp to-rem literate-garbanzo 1 ec2.setup/config/server.properties /usr/local/kafka/config/
peg sshcmd-node literate-garbanzo 1 "sed -i 's/broker.id=.*/broker.id=0/' /usr/local/kafka/config/server.properties"

peg scp to-rem literate-garbanzo 2 ec2.setup/config/server.properties /usr/local/kafka/config/
peg sshcmd-node literate-garbanzo 2 "sed -i 's/broker.id=.*/broker.id=1/' /usr/local/kafka/config/server.properties"

peg scp to-rem literate-garbanzo 3 ec2.setup/config/server.properties /usr/local/kafka/config/
peg sshcmd-node literate-garbanzo 3 "sed -i 's/broker.id=.*/broker.id=2/' /usr/local/kafka/config/server.properties"

peg scp to-rem literate-garbanzo 4 ec2.setup/config/server.properties /usr/local/kafka/config/
peg sshcmd-node literate-garbanzo 4 "sed -i 's/broker.id=.*/broker.id=3/' /usr/local/kafka/config/server.properties"
```

Time to git'er runnin

```bash
peg service literate-garbanzo zookeeper start
peg service literate-garbanzo kafka start &
sleep 10
peg service literate-garbanzo flink start
```

A few more monitoring and library details before the code can run

```bash
peg sshcmd-cluster literate-garbanzo "sudo apt-get update; sudo apt-get install -y nmon openjdk-8-jdk"
```






## Loading up Network Pulse code

First, you must teach each component about each other

```bash
FLINK_CONNECT=$(./ec2.setup/flinkConnectionStringBuilder.sh)
sed -i "s/bootstrap\.servers=.*/bootstrap.servers=$FLINK_CONNECT/" venturi/src/main/resources/kafka.properties
sed -i "s/bootstrap\.servers=.*/bootstrap.servers=$FLINK_CONNECT/" mockFirehose/src/main/resources/kafka.properties
sed -i "s/bootstrap\.servers=.*/bootstrap.servers=$FLINK_CONNECT/" flinkCC/src/main/resources/flink.properties

ZC=$(ec2.setup/kafkaZookeeperConnectStringBuilder.sh)
sed -i "s/zookeeper\.connect=.*/zookeeper.connect=$ZC/" venturi/src/main/resources/kafka.properties
sed -i "s/zookeeper\.connect=.*/zookeeper.connect=$ZC/" flinkCC/src/main/resources/flink.properties

```

Then build

```bash
cd mockFirehose; mvn clean compile package; cd ..
cd venturi; mvn clean compile package; cd ..
cd flinkCC; mvn clean package -Pbuild-jar; cd ..
```

Presuming each sub-project is built, the following should get the files in place:

```bash
peg scp to-rem literate-garbanzo 1 flinkCC/target/flinkCC-0.0.1.jar /home/ubuntu
peg scp to-rem literate-garbanzo 1 flinkCC/src/main/resources/flink.properties /home/ubuntu
peg scp to-rem literate-garbanzo 1 flinkCC/runFlinkCC.sh /home/ubuntu

peg scp to-rem literate-garbanzo 2 venturi/target/venturi-0.0.1-jar-with-dependencies.jar /home/ubuntu
peg scp to-rem literate-garbanzo 2 venturi/src/main/resources/kafka.properties /home/ubuntu
peg scp to-rem literate-garbanzo 2 venturi/src/main/resources/venturi.properties /home/ubuntu
peg scp to-rem literate-garbanzo 2 venturi/runVenturi.sh /home/ubuntu

peg scp to-rem literate-garbanzo 3 mockFirehose/target/firehose-0.0.1-jar-with-dependencies.jar /home/ubuntu
peg scp to-rem literate-garbanzo 3 mockFirehose/src/main/resources/hose.properties /home/ubuntu
peg scp to-rem literate-garbanzo 3 mockFirehose/src/main/resources/kafka.properties /home/ubuntu
peg scp to-rem literate-garbanzo 3 mockFirehose/src/main/resources/s3files.txt /home/ubuntu
peg scp to-rem literate-garbanzo 3 mockFirehose/runFirehose.sh /home/ubuntu

peg sshcmd-node literate-garbanzo 4 "sudo pip install virtualenv; mkdir ~/flasky; cd flasky; virtualenv ."
peg scp to-rem literate-garbanzo 4 ui.server/run.py /home/ubuntu/flasky
cd ui.server
./buildAndDeploy.sh
```

Create kafka topics with appropriate settings for this application

```bash
peg scp to-rem literate-garbanzo 1 ec2.setup/kafka-create-pulse-topics.sh /home/ubuntu/
peg sshcmd-node literate-garbanzo 1 "~/kafka-create-pulse-topics.sh"

```

Start services (this could take a minute):

```bash
./bin/service_start.sh
```

In four shells, connected to each node, run the following in screen sessions (or via nohup)
```
# on node 1
./runFlinkCC.sh

# on node 2
./runVenturi.sh

# on node 3
./runFirehose.sh

# on node 4
cd flasky
. bin/activate
./run.py
```