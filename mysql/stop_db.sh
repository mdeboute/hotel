#!/bin/bash
container='hotel-sql'
if [ "$(docker container inspect -f '{{.State.Running}}' $container)" == "true" ]; then
    docker stop $container
    docker rm $container
    exit 0
fi
echo "Please launch the database with './start_db.sh' before stop it"
exit 1