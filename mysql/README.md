### Build image
docker build -t hotel-sql .

### run the image
docker run -d -p 3306:3306 --name hotel-sql -e MYSQL_ROOT_PASSWORD=root hotel-sql

### list all docker containers
docker ps -a 
docker container ls -a

### to start container
docker start [container_id]

### if you need to inspect the machine
docker exec -it hotel-sql bash

### check around the vm
mysql -uroot -proot
show databases;

### to stop a container
docker container stop [container_id]

### remove a stopped container
docker container rm [container_id]

### see list of docker images
docker images

### remove a docker image
docker image rm [image_id]

 any conflicts with container name existing after rebuild
docker rm hotel-sql
