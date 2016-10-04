#!/bin/bash
peg describe literate-garbanzo | grep Public | sed 's/.*Public DNS:\s*//'
