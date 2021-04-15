@echo off

docker stop %(docker ps -a -q)
docker rm %(docker ps -a -q)
exit 0