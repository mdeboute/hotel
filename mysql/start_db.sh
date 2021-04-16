#!/bin/bash
chmod u+x stop_db.sh
chmod u+x show_vm.sh
if ! docker info >/dev/null 2>&1; then
  echo "Docker does not seem to be running, run it first and retry"
  exit 1
fi
container='hotel-sql'
if [ "$(docker ps -q -f name=$container)" ]; then
  if [ "$(docker ps -aq -f status=exited -f name=$container)" ]; then
    docker rm $container
  fi
  if [ "$(docker container inspect -f '{{.State.Running}}' $container)" == "true" ]; then
    docker stop $container
    docker rm $container
  fi
fi
docker pull mdeboute/hotel-sql:latest
docker run -d -p 3306:3306 --name hotel-sql -e MYSQL_ROOT_PASSWORD=root mdeboute/hotel-sql:latest
exit 0