#!/bin/bash
# continuous producer, to be run on producer 1 (172.31.0.4)

cd $(dirname $0)
LOOP=true

while $LOOP; do
    ssh 172.31.0.5 "~/runFirehose.sh" &
    ./runFirehose.sh&

    FAIL=0
    
    for job in `jobs -p`; do
	wait $job || let "FAIL+=1"
    done

    if [ "$FAIL" != "0" ]; then
	echo "FAIL! ($FAIL)";
	LOOP=false
    fi
done

