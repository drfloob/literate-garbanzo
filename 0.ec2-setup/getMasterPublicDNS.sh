#!/bin/bash
cd "$(dirname $0)"
./getPublicDNS.sh | head -1
