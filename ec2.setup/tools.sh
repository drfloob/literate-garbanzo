#!/bin/bash

# install nmon
peg sshcmd-cluster literate-garbanzo "sudo apt-get update"
peg sshcmd-cluster literate-garbanzo "sudo apt-get install nmon"

# install twitter/burrow
peg sshcmd-cluster literate-garbanzo 'sudo apt-get install -y golang'
peg sshcmd-cluster literate-garbanzo 'wget https://raw.githubusercontent.com/pote/gpm/v1.4.0/bin/gpm && chmod +x gpm && sudo mv gpm /usr/local/bin'
peg sshcmd-cluster literate-garbanzo 'mkdir ~/go.work; echo "export GOPATH=$HOME/go.work" >> ~/.bash_profile; . ~/.bash_profile'

peg sshcmd-cluster literate-garbanzo 'echo "alias c=clear" >> .bash_aliases'
