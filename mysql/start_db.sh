#!/bin/bash
chmod u+x stop_db.sh
chmod u+x show_db.sh
if ! docker info >/dev/null 2>&1; then
  echo "Docker does not seem to be running, run it first and retry"
  exit 1
fi
CONTAINER_NAME='hotel-sql'
if ! [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
  if [ "$(docker ps -aq -f status=exited -f name=$CONTAINER_NAME)" ]; then
    docker rm $CONTAINER_NAME
  fi
  if [ "$(docker container inspect -f '{{.State.Running}}' $CONTAINER_NAME)" == "true" ]; then
    docker stop $CONTAINER_NAME
    docker rm $CONTAINER_NAME
  fi
  docker pull mdeboute/hotel-sql:latest
  docker run -d -p 3306:3306 --name hotel-sql -e MYSQL_ROOT_PASSWORD=root mdeboute/hotel-sql:latest
fi
docker pull mdeboute/hotel-sql:latest
echo "Done !"
exit 0