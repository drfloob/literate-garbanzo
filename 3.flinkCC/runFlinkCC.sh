#!/bin/bash
/usr/local/flink/bin/flink run -C file:///home/ubuntu/ -c com.drfloob.insight.pulse.cc.StreamingJob ~/flinkCC-0.0.1.jar
