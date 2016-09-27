#!/bin/bash

peg sshcmd-cluster literate-garbanzo "sudo apt-get update"
peg sshcmd-cluster literate-garbanzo "sudo apt-get install nmon"

peg sshcmd-cluster literate-garbanzo 'echo "log.cleanup.interval.mins=1" >> /usr/local/kafka/config/server.properties'
peg sshcmd-cluster literate-garbanzo 'echo "log.cleaner.enable=true" >> /usr/local/kafka/config/server.properties'

peg sshcmd-cluster literate-garbanzo 'sudo apt-get install -y golang'
peg sshcmd-cluster literate-garbanzo 'wget https://raw.githubusercontent.com/pote/gpm/v1.4.0/bin/gpm && chmod +x gpm && sudo mv gpm /usr/local/bin'
peg sshcmd-cluster literate-garbanzo 'mkdir ~/go.work; echo "export GOPATH=$HOME/go.work" >> ~/.profile; . ~/.profile'
peg sshcmd-cluster literate-garbanzo 'sed -i "s/GOPATH/export GOPATH/" ~/.profile'

peg sshcmd-cluster literate-garbanzo 'echo "alias c=clear" >> .profile'
