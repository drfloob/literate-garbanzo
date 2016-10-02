#!/bin/bash
cd "$(dirname $0)"
python setup.py sdist && \
    peg scp to-rem literate-garbanzo 4 dist/ui.server-1.0.tar.gz /home/ubuntu/flasky/ && \
    peg sshcmd-node literate-garbanzo 4 "cd flasky; source bin/activate; easy_install ui.server-1.0.tar.gz"
