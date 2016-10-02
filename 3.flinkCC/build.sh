#!/bin/bash
cd "$(dirname $0)"
mvn clean package -Pbuild-jar
