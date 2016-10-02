#!/bin/bash
cd "$(dirname $0)"
./build.sh
./deployJar.sh
./deployProps.sh
