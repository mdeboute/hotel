# How to launch the database on your computer ?

Run this commands in this folder on your terminal (a bash) :

* Maybe you will need to do a `chmod u+x start_db.sh` before 1. if it doesn't work.

1. `./start_db.sh` (to download/update and start the db)
2. `./show_db.sh` (to obtain a mysql shell and see/communicate with the db)
3. `./stop_db.sh` (to stop the db)

## Testers :

If you want to test the code of the project on the CI prefilled database you can run this commands (make sure that
another container is not running at the same time on the same port) :

1. `docker pull mdeboute/hotel-sql:gitlab-ci`
2. `docker run -d -p 3306:3306 --name hotel-ci -e MYSQL_ROOT_PASSWORD=root mdeboute/hotel-sql:gitlab-ci`

## Docker hub

[Here are the last stable images of the project.](https://hub.docker.com/r/mdeboute/hotel-sql/tags?page=1&ordering=last_updated)
