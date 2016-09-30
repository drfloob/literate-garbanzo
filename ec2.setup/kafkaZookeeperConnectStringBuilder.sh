#!/bin/bash
function join_by { local IFS="$1"; shift; echo "$*"; }
IPS=$(./getPrivateIPs.sh | sed 's/$/:2181/')
join_by , $IPS
