#!/bin/bash
peg describe literate-garbanzo | grep Hostname | sed -e 's/\s*Hostname:\s*ip-//' -e 's/-/\./g'
