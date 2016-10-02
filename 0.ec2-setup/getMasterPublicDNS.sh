#!/bin/bash
peg describe literate-garbanzo | grep Public | head -1 | sed 's/.*Public DNS:\s*//'
