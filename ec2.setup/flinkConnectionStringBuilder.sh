#!/bin/bash
function join_by { local IFS="$1"; shift; echo "$*"; }
DIR=`dirname $0`
IPS=$(${DIR}/getPrivateIPs.sh | sed 's/$/:9092/')
join_by , $IPS
