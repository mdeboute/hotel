#!/bin/bash
docker pull mdeboute/hotel-sql:latest
docker run -d -p 3306:3306 --name hotel-sql -e MYSQL_ROOT_PASSWORD=root mdeboute/hotel-sql
exit 0