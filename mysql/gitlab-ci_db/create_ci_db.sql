CREATE DATABASE IF NOT EXISTS hotel;

CREATE TABLE `hotel`.`users` (
  `u_name` VARCHAR(30) NOT NULL,
  `u_password` VARCHAR(50) NULL,
  `u_is_admin` TINYINT NULL,
  PRIMARY KEY (`u_name`));

CREATE TABLE `hotel`.`room_type` (
  `t_name` VARCHAR(30) NOT NULL,
  `beds` SMALLINT NULL,
  `r_size` SMALLINT NULL,
  `has_view` TINYINT NULL,
  `has_kitchen` TINYINT NULL,
  `has_bathroom` TINYINT NULL, 
  `has_workspace` TINYINT NULL,
  `has_tv` TINYINT NULL,
  `has_coffee_maker` TINYINT NULL,
  PRIMARY KEY (`t_name`));

CREATE TABLE `hotel`.`room` (
  `r_num` SMALLINT NOT NULL,
  `r_floor` TINYINT NULL,
  `r_type` VARCHAR(30) NULL,
  `booked` TINYINT NULL,
  PRIMARY KEY (`r_num`),
  FOREIGN KEY (`r_type`) REFERENCES room_type(`t_name`));

CREATE TABLE `hotel`.`booking` (
  `b_id` INT NOT NULL AUTO_INCREMENT,
  `r_num` SMALLINT NULL,
  `paid_by_card` TINYINT NULL,
  `b_from` DATE NOT NULL,
  `b_till` DATE NOT NULL,
  `b_fee` INT NULL,
  `b_is_paid` TINYINT NULL,
  PRIMARY KEY (`b_id`),
  FOREIGN KEY (`r_num`) REFERENCES room(`r_num`));

CREATE TABLE `hotel`.`customer` (
  `c_ss_number` INT NOT NULL,
  `c_address` VARCHAR(500) NULL,
  `c_full_name` VARCHAR(100) NULL,
  `c_phone_num` INT NOT NULL,
  `c_email` VARCHAR(100) NULL,
  PRIMARY KEY (`c_ss_number`));

CREATE TABLE `hotel`.`customer_booking` (
  `customer_ss_number` INT NOT NULL,
  `booking_id` INT NOT NULL,
  PRIMARY KEY (`customer_ss_number`, `booking_id`),
  FOREIGN KEY (`customer_ss_number`) REFERENCES customer(`c_ss_number`),
  FOREIGN KEY (`booking_id`) REFERENCES booking(`b_id`));


INSERT INTO `hotel`.`room_type`(
  t_name, beds, r_size, has_view, has_kitchen,
  has_bathroom, has_workspace, has_tv,
  has_coffee_maker
)
VALUES
  ('Single', 1, 35, 0, 0, 1, 0, 1, 1);

INSERT INTO `hotel`.`room_type`(
  t_name, beds, r_size, has_view, has_kitchen,
  has_bathroom, has_workspace, has_tv,
  has_coffee_maker
)
VALUES
  ('Double', 2, 45, 0, 1, 1, 0, 1, 1);

INSERT INTO `hotel`.`room_type`(
  t_name, beds, r_size, has_view, has_kitchen,
  has_bathroom, has_workspace, has_tv,
  has_coffee_maker
)
VALUES
  ('Triple', 3, 55, 0, 1, 1, 1, 1, 1);

INSERT INTO `hotel`.`room_type`(
  t_name, beds, r_size, has_view, has_kitchen,
  has_bathroom, has_workspace, has_tv,
  has_coffee_maker
)
VALUES
  ('Quad', 4, 70, 1, 1, 1, 1, 1, 1);



INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (1, 1, 'Single', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (2, 1, 'Single', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (3, 1, 'Double', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (4, 1, 'Double', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (5, 2, 'Single', 1);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (6, 1, 'Single', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (7, 1, 'Double', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (8, 1, 'Single', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (9, 1, 'Single', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (10, 1, 'Double', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (11, 1, 'Double', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (12, 1, 'Double', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (13, 1, 'Double', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (14, 1, 'Single', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (15, 1, 'Single', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (16, 1, 'Single', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (17, 1, 'Single', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (18, 1, 'Double', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (19, 1, 'Single', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (20, 1, 'Single', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (21, 2, 'Triple', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (22, 2, 'Triple', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (23, 2, 'Triple', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (24, 2, 'Triple', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (25, 2, 'Triple', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (26, 2, 'Double', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (27, 2, 'Double', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (28, 2, 'Double', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (29, 2, 'Single', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (30, 2, 'Single', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (31, 2, 'Quad', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (32, 2, 'Quad', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (33, 2, 'Quad', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (34, 2, 'Triple', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (35, 2, 'Triple', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (36, 2, 'Double', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (37, 2, 'Double', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (38, 2, 'Single', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (39, 2, 'Single', 0);

INSERT INTO `hotel`.`room`(r_num, r_floor, r_type, booked)
VALUES
  (40, 2, 'Quad', 0);


INSERT INTO `hotel`.`customer`(
  c_ss_number, c_address, c_full_name,
  c_phone_num, c_email
)
VALUES
  (
    46040774, 'Granvagen 10', 'Astrid Karlsson',
    0761233557, 'astrid.karlsson@outlook.com'
  );

INSERT INTO `hotel`.`customer`(
  c_ss_number, c_address, c_full_name,
  c_phone_num, c_email
)
VALUES
  (
    89022742, 'Ormvagen 22', 'Jakob Svensson',
    0732231264, 'jakobsve@yahoo.com'
  );

INSERT INTO `hotel`.`customer`(
  c_ss_number, c_address, c_full_name,
  c_phone_num, c_email
)
VALUES
  (
    89012362, 'Stjarnvagen 68', 'Karl Johann',
    0701537254, 'karl.johann@gmail.com '
  );

INSERT INTO `hotel`.`customer`(
  c_ss_number, c_address, c_full_name,
  c_phone_num, c_email
)
VALUES
  (
    90040526, 'Lillstigen 57', 'Marjatta Soderberg',
    0699541499, 'marsoder@gmail.com'
  );

INSERT INTO `hotel`.`customer`(
  c_ss_number, c_address, c_full_name,
  c_phone_num, c_email
)
VALUES
  (
    82012909, 'Franssonbacken 6', 'Majlis Arvidsson',
    0982355502, 'majlis.arvidsson@hotmail.com'
  );

INSERT INTO `hotel`.`customer`(
  c_ss_number, c_address, c_full_name,
  c_phone_num, c_email
)
VALUES
  (
    84031956, 'Linnebacken 448 ', 'Lennart Ek',
    0808666318, 'lennart.ek.9@msn.com'
  );

INSERT INTO `hotel`.`customer`(
  c_ss_number, c_address, c_full_name,
  c_phone_num, c_email
)
VALUES
  (
    86050868, 'Linnegrand 4700', 'Rolf Sandstrom',
    0738317125, 'rolfgolfr@hotmail.com'
  );

INSERT INTO `hotel`.`customer`(
  c_ss_number, c_address, c_full_name,
  c_phone_num, c_email
)
VALUES
  (
    94080245, 'Kristianliden 335 ', 'Ann-Charlotte Fredriksson',
    0768717802, 'ann.fred@outlook.com'
  );

INSERT INTO `hotel`.`customer`(
  c_ss_number, c_address, c_full_name,
  c_phone_num, c_email
)
VALUES
  (
    98112434, 'Dahlbergbacken 6', 'Maja Hellstrom',
    0734905296, 'maja.hellstrom@yahoo.com'
  );

INSERT INTO `hotel`.`customer`(
  c_ss_number, c_address, c_full_name,
  c_phone_num, c_email
)
VALUES
  (
    72091719, 'Sivliden 71', 'Patrik Wikstrom',
    0761404361, 'patrik.wikstrom@hotmail.com'
  );


INSERT INTO `hotel`.`booking`(
  b_id, r_num, paid_by_card, b_from,
  b_till, b_fee, b_is_paid
)
VALUES
  (
    1, 2, 1, '2021-04-11', '2021-04-12',
    799, 0
  );

INSERT INTO `hotel`.`booking`(
  b_id, r_num, paid_by_card, b_from,
  b_till, b_fee, b_is_paid
)
VALUES
  (
    2, 5, 1, '2021-04-18', '2021-04-20',
    1500, 1
  );

INSERT INTO `hotel`.`booking`(
  b_id, r_num, paid_by_card, b_from,
  b_till, b_fee, b_is_paid
)
VALUES
  (
    3, 4, 0, '2021-05-01', '2021-05-03',
    1500, 0
  );

INSERT INTO `hotel`.`booking`(
  b_id, r_num, paid_by_card, b_from,
  b_till, b_fee, b_is_paid
)
VALUES
  (
    4, 4, 1, '2021-04-22', '2021-04-23',
    500, 1
  );

INSERT INTO `hotel`.`booking`(
  b_id, r_num, paid_by_card, b_from,
  b_till, b_fee, b_is_paid
)
VALUES
  (
    5, 5, 0, '2021-05-10', '2021-05-30',
    10000, 1
  );

INSERT INTO `hotel`.`booking`(
  b_id, r_num, paid_by_card, b_from,
  b_till, b_fee, b_is_paid
)
VALUES
  (
    6, 6, 1, '2021-04-26', '2021-04-30',
    750, 1
  );

INSERT INTO `hotel`.`booking`(
  b_id, r_num, paid_by_card, b_from,
  b_till, b_fee, b_is_paid
)
VALUES
  (
    7, 7, 1, '2021-05-23', '2021-05-29',
    950, 1
  );

INSERT INTO `hotel`.`booking`(
  b_id, r_num, paid_by_card, b_from,
  b_till, b_fee, b_is_paid
)
VALUES
  (
    8, 8, 0, '2021-04-29', '2021-05-01',
    400, 0
  );

INSERT INTO `hotel`.`booking`(
  b_id, r_num, paid_by_card, b_from,
  b_till, b_fee, b_is_paid
)
VALUES
  (
    9, 9, 1, '2021-04-30', '2021-05-05',
    1500, 1
  );

INSERT INTO `hotel`.`booking`(
  b_id, r_num, paid_by_card, b_from,
  b_till, b_fee, b_is_paid
)
VALUES
  (
    10, 10, 1, '2021-05-30', '2021-06-05',
    2000, 1
  );


INSERT INTO `hotel`.`customer_booking`(customer_ss_number, booking_id)
VALUES
  (46040774, 1);

INSERT INTO `hotel`.`customer_booking`(customer_ss_number, booking_id)
VALUES
  (72091719, 2);

INSERT INTO `hotel`.`customer_booking`(customer_ss_number, booking_id)
VALUES
  (82012909, 3);

INSERT INTO `hotel`.`customer_booking`(customer_ss_number, booking_id)
VALUES
  (84031956, 4);

INSERT INTO `hotel`.`customer_booking`(customer_ss_number, booking_id)
VALUES
  (86050868, 5);

INSERT INTO `hotel`.`customer_booking`(customer_ss_number, booking_id)
VALUES
  (89012362, 6);

INSERT INTO `hotel`.`customer_booking`(customer_ss_number, booking_id)
VALUES
  (89022742, 7);

INSERT INTO `hotel`.`customer_booking`(customer_ss_number, booking_id)
VALUES
  (90040526, 8);

INSERT INTO `hotel`.`customer_booking`(customer_ss_number, booking_id)
VALUES
  (94080245, 9);

INSERT INTO `hotel`.`customer_booking`(customer_ss_number, booking_id)
VALUES
  (98112434, 10);


INSERT INTO `hotel`.`users`(u_name, u_password, u_is_admin)
VALUES
  ('admin', '$31$16$xS1nhccd-fN8ItpO-kcRQOIEUOJdjcpO7sB2GoZgITU', 1);

INSERT INTO `hotel`.`users`(u_name, u_password, u_is_admin)
VALUES
  ('rstaff', '$31$16$u3FYsTI1nEDLm3tpswj7PMudnvk5QltpNdly_9QDmFw', 0);
