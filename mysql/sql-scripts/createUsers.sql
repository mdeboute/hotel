CREATE TABLE `hotel`.`users` (
  `u_name` VARCHAR(30) NOT NULL,
  `u_password` VARCHAR(30) NULL,
  `u_is_admin` TINYINT NULL,
  PRIMARY KEY (`u_name`));

INSERT INTO `hotel`.`users`
VALUES (admin, root, 1);
