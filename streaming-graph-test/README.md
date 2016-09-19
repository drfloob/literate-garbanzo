To run this example code:

## Install flink

[flink.apache.org](https://flink.apache.org). You can also build it, if you're feeling adventurous, or need to exercise your machines.

## Build gelly-streaming and Install to maven cache


```bash
git clone https://github.com/vasia/gelly-streaming.git
cd gelly-streaming

mvn package

mvn install:install-file
-Dfile=./target/flink-gelly-streaming-0.1.0.jar
-DgroupId=org.apache.flink -DartifactId=flink-gelly-streaming
-Dversion=0.1.0 -Dpackaging=jar -DgeneratePom=true
```

## Build this example code

`mvn clean package -Pbuild_jar`


## Start Flink

`</path/to/flink>/bin/start-local.sh`


## Watch the Flink output in a separate window

`tail -F </path/to/flink>/flink-*-jobmanager-*.out`


## Run the example

```bash
</path/to/flink>/bin/flink run -c com.drfloob.insight.pulse.ConnectedComponentsExample
target/streaming-graph-test-0.1.jar input/example.txt 1000 1000
```

11 sets of clustered components should appear in the window output, odds and evens separated into two groups spanning roughly 10 numbers per window.