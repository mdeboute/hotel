#!/bin/bash
echo -e "Make sure you have launched start_db.sh before that.\nOnce you're on the mysql shell please type 'use hotel;' and then 'show tables;' to see the database.\nIf u want to exit just type 'exit'\n"
sleep 4
docker exec -it hotel-sql bash -c "mysql -uroot -proot"
exit
exit 0