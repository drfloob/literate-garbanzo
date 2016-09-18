#!/bin/bash

for i in $(seq 1 100); do
  echo $i $(expr $i + 2) >> example.txt
done
