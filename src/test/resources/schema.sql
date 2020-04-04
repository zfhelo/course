DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
	`id` INT PRIMARY KEY AUTO_INCREMENT,
	`username` VARCHAR ( 12 ) UNIQUE NOT NULL,
	`nickname` VARCHAR ( 30 ),
	`password` VARCHAR ( 255 ) NOT NULL,
	`photo` VARCHAR ( 255 ),
	`email` VARCHAR ( 30 ),
	`phone` VARCHAR ( 11 ),
	`time` TIMESTAMP DEFAULT NOW( )
);
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
	`id` INT PRIMARY KEY AUTO_INCREMENT,
	`username` VARCHAR ( 12 ) UNIQUE NOT NULL,
	`nickname` VARCHAR ( 30 ),
	`password` VARCHAR ( 255 ) NOT NULL,
	`photo` VARCHAR ( 255 ),
	`email` VARCHAR ( 30 ),
	`phone` VARCHAR ( 11 ),
`time` TIMESTAMP DEFAULT NOW( )
);