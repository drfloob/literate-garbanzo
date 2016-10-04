#!/bin/bash

cd "$(dirname $0)"

# install nmon
peg sshcmd-cluster literate-garbanzo "sudo apt-get update"
peg sshcmd-cluster literate-garbanzo "sudo apt-get install nmon"

# install twitter/burrow
peg sshcmd-cluster literate-garbanzo 'sudo apt-get install -y golang-1.6'
peg sshcmd-cluster literate-garbanzo 'wget https://raw.githubusercontent.com/pote/gpm/v1.4.0/bin/gpm && chmod +x gpm && sudo mv gpm /usr/local/bin'
peg sshcmd-cluster literate-garbanzo 'mkdir ~/go.work; echo "export GOPATH=$HOME/go.work" >> ~/.bashrc; . ~/.bashrc'
peg sshcmd-cluster literate-garbanzo 'sudo update-alternatives --install /usr/bin/go go /usr/lib/go-1.6/bin/go 1'

peg sshcmd-cluster literate-garbanzo '. ~/.bash_profile;
go get github.com/linkedin/Burrow &&
cd $GOPATH/src/github.com/linkedin/Burrow &&
gpm install &&
go install'

sed -i -e '/hostname=.*/d' -e '/broker=.*/d' -e '/zookeeper=.*/d' ./config/burrow.cfg
PRIV_IPS=$(./getPrivateIPs.sh)
for ip in $PRIV_IPS; do
     sed -i -e "/\[zookeeper\]/a hostname=$ip" \
	 -e "/\[kafka/a zookeeper=$ip" \
	 config/burrow.cfg
done
PUB_DNS=$(./getPublicDNS.sh)
for dns in $PUB_DNS; do
     sed -i -e "/\[kafka/a broker=$dns" config/burrow.cfg
done;

peg scp to-rem literate-garbanzo 1 config/burrow.cfg /home/ubuntu &
peg scp to-rem literate-garbanzo 2 config/burrow.cfg /home/ubuntu &
peg scp to-rem literate-garbanzo 3 config/burrow.cfg /home/ubuntu &
peg scp to-rem literate-garbanzo 4 config/burrow.cfg /home/ubuntu &



# because 'clear' is 4 letters too long, and I never set up a decent dotfiles repo
peg sshcmd-cluster literate-garbanzo 'echo "alias c=clear" >> .bash_aliases'
