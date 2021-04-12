# mysql official image
FROM mysql

# add a database
ENV MYSQL_DATABASE hotel

# container startup scripts
COPY sql-scripts/ /docker-entrypoint-initdb.d/
