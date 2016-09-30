#!/bin/bash

# to add an extra storage volume to each ec2 instance, mounted as /tmp:
#  * create N extra magnetic volumes on aws, and
#  * run the following, then 
#  * reboot the server
peg sshcmd-cluster literate-garbanzo 'sudo mkfs.ext4 /dev/xvdf'
peg sshcmd-cluster literate-garbanzo 'sudo sh -c "echo \"/dev/xvdf /tmp auto noatime 0 0\" >> /etc/fstab"'
