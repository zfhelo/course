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
CREATE TABLE course (
	id INT PRIMARY KEY AUTO_INCREMENT,
	tea_id INT NOT NULL,
	FOREIGN KEY ( tea_id ) REFERENCES teacher ( id ) ON DELETE CASCADE ON UPDATE CASCADE,
	`name` VARCHAR ( 30 ) NOT NULL,
	number VARCHAR ( 12 ) UNIQUE NOT NULL,
	cover VARCHAR ( 255 ),
	description VARCHAR ( 50 ),
	`time` TIMESTAMP DEFAULT NOW()
);
CREATE TABLE stu_course_map (
	id INT PRIMARY KEY AUTO_INCREMENT,
	stu_id INT NOT NULL,
	course_id INT NOT NULL,
	regular_grade INT DEFAULT 0,
	grade INT DEFAULT 0,
	`time` TIMESTAMP DEFAULT NOW( ),
	FOREIGN KEY ( stu_id ) REFERENCES student ( id ) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY ( course_id ) REFERENCES course ( id ) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE single_question (
	id INT PRIMARY KEY AUTO_INCREMENT,
	course_id INT NOT NULL,
	FOREIGN KEY ( course_id ) REFERENCES course ( id ) ON DELETE CASCADE ON UPDATE CASCADE,
	title LONGTEXT NOT NULL,
	choose1 VARCHAR ( 500 ) NOT NULL,
	choose2 VARCHAR ( 500 ) NOT NULL,
	choose3 VARCHAR ( 500 ) NOT NULL,
	choose4 VARCHAR ( 500 ) NOT NULL,
	grade INT NOT NULL,
	`time` TIMESTAMP DEFAULT NOW( )
);
CREATE TABLE essay_question (
	id INT PRIMARY KEY AUTO_INCREMENT,
	course_id INT NOT NULL,
	FOREIGN KEY ( course_id ) REFERENCES course ( id ) ON DELETE CASCADE ON UPDATE CASCADE,
	title LONGTEXT NOT NULL,
	grade INT NOT NULL,
	`time` TIMESTAMP DEFAULT NOW( )
);
CREATE TABLE true_or_false_question (
	id INT PRIMARY KEY AUTO_INCREMENT,
	course_id INT NOT NULL,
	FOREIGN KEY ( course_id ) REFERENCES course ( id ) ON DELETE CASCADE ON UPDATE CASCADE,
	title LONGTEXT NOT NULL,
	answer boolean NOT NULL,
	grade BOOLEAN NOT NULL,
	reference INT DEFAULT 0,
	`time` TIMESTAMP DEFAULT NOW( )
);
CREATE TABLE gap_filling_question (
	id INT PRIMARY KEY AUTO_INCREMENT,
	course_id INT NOT NULL,
	FOREIGN KEY ( course_id ) REFERENCES course ( id ) ON DELETE CASCADE ON UPDATE CASCADE,
	title LONGTEXT NOT NULL,
	answer VARCHAR ( 500 ) NOT NULL,
	grade INT NOT NULL,
	`time` TIMESTAMP DEFAULT NOW()
);
CREATE TABLE exam_model (
	id INT PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR ( 30 ) NOT NULL,
	course_id INT NOT NULL,
	`is_hide` BOOLEAN DEFAULT TRUE,
	grade FLOAT DEFAULT 0.0,
	FOREIGN KEY ( course_id ) REFERENCES course ( id ) ON DELETE CASCADE ON UPDATE CASCADE,
	`time` TIMESTAMP DEFAULT NOW( ),
	`start_time` TIMESTAMP DEFAULT NOW( ),
	`end_time` TIMESTAMP DEFAULT NOW( )
);
CREATE TABLE exam_paper (
	id INT PRIMARY KEY AUTO_INCREMENT,
	stu_id INT NOT NULL,
	`model_id` INT NOT NULL,
	grade FLOAT DEFAULT 0.0,
	`status` BOOLEAN DEFAULT FALSE,
	`rule` INT NOT NULL,
	`update_time` TIMESTAMP,
	FOREIGN KEY ( stu_id ) REFERENCES student ( id ) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY ( `model_id` ) REFERENCES exam_model ( id ) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE paper_single_map (
	id INT PRIMARY KEY AUTO_INCREMENT,
	paper_id INT NOT NULL,
	single_id INT NOT NULL,
	user_answer VARCHAR ( 500 ),
	`time` TIMESTAMP DEFAULT NOW( ),
	FOREIGN KEY ( paper_id ) REFERENCES exam_paper ( id ) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY ( single_id ) REFERENCES single_question ( id ) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE paper_gap_map (
	id INT PRIMARY KEY AUTO_INCREMENT,
	paper_id INT NOT NULL,
	gap_id INT NOT NULL,
	user_answer VARCHAR ( 500 ),
	`time` TIMESTAMP DEFAULT NOW( ),
	FOREIGN KEY ( paper_id ) REFERENCES exam_paper ( id ) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY ( gap_id ) REFERENCES gap_filling_question ( id ) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE paper_essay_map (
	id INT PRIMARY KEY AUTO_INCREMENT,
	paper_id INT NOT NULL,
	essay_id INT NOT NULL,
	user_answer LONGTEXT,
	user_grade FLOAT DEFAULT 0,
	`time` TIMESTAMP DEFAULT NOW( ),
	FOREIGN KEY ( paper_id ) REFERENCES exam_paper ( id ) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY ( essay_id ) REFERENCES essay_question ( id ) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE paper_torf_map (
	id INT PRIMARY KEY AUTO_INCREMENT,
	paper_id INT NOT NULL,
	torf_id INT NOT NULL,
	user_answer BOOLEAN,
	`time` TIMESTAMP DEFAULT NOW( ),
	FOREIGN KEY ( paper_id ) REFERENCES exam_paper ( id ) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY ( torf_id ) REFERENCES true_or_false_question ( id ) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE homework (
	id INT PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR ( 30 ) NOT NULL,
	course_id INT NOT NULL,
	content LONGTEXT,
	grade FLOAT DEFAULT 0.0,
	FOREIGN KEY ( course_id ) REFERENCES course ( id ) ON DELETE CASCADE ON UPDATE CASCADE,
	`time` TIMESTAMP DEFAULT NOW( ),
	`start_time` TIMESTAMP DEFAULT NOW( ),
	`end_time` TIMESTAMP DEFAULT NOW( )
);
CREATE TABLE stu_homework_answer (
	id INT PRIMARY KEY AUTO_INCREMENT,
	homework_id INT NOT NULL,
	stu_id INT NOT NULL,
	answer LONGTEXT DEFAULT NULL,
	grade FLOAT DEFAULT 0.0,
	FOREIGN KEY ( homework_id ) REFERENCES homework ( id ) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY ( stu_id ) REFERENCES student ( id ) ON DELETE CASCADE ON UPDATE CASCADE,
	update_time TIMESTAMP,
	`time` TIMESTAMP DEFAULT NOW( )
);
CREATE TABLE student_resources (
	id INT PRIMARY KEY AUTO_INCREMENT,
	course_id INT NOT NULL,
	stu_id INT NOT NULL,
	description VARCHAR ( 1000 ) NOT NULL,
	path VARCHAR ( 1000 ) NOT NULL,
	size LONG NOT NULL,
	`time` TIMESTAMP DEFAULT NOW( ),
	FOREIGN KEY ( stu_id ) REFERENCES student ( id ) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY ( course_id ) REFERENCES course ( id ) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE teacher_resources (
	id INT PRIMARY KEY AUTO_INCREMENT,
	course_id INT NOT NULL,
	tea_id INT NOT NULL,
	description VARCHAR ( 1000 ) NOT NULL,
	path VARCHAR ( 1000 ) NOT NULL,
	size LONG NOT NULL,
	`time` TIMESTAMP DEFAULT NOW( ),
	FOREIGN KEY ( tea_id ) REFERENCES teacher ( id ) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY ( course_id ) REFERENCES course ( id ) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE stu_invitation (
	id INT PRIMARY KEY AUTO_INCREMENT,
	course_id INT NOT NULL,
	stu_id INT NOT NULL,
	title VARCHAR ( 255 ) NOT NULL,
	content LONGTEXT NOT NULL,
	`time` TIMESTAMP DEFAULT NOW( ),
	FOREIGN KEY ( stu_id ) REFERENCES student ( id ) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY ( course_id ) REFERENCES course ( id ) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE stu_comment (
	id INT PRIMARY KEY AUTO_INCREMENT,
	parent_id INT DEFAULT 0,
	user_name VARCHAR ( 30 ) NOT NULL,
	head_img VARCHAR ( 500 ) NOT NULL,
	invitation_id INT NOT NULL,
	content LONGTEXT NOT NULL,
	`time` TIMESTAMP DEFAULT NOW( ),
	FOREIGN KEY ( invitation_id ) REFERENCES stu_invitation ( id ) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE tea_invitation (
	id INT PRIMARY KEY AUTO_INCREMENT,
	course_id INT NOT NULL,
	tea_id INT NOT NULL,
	title VARCHAR ( 255 ) NOT NULL,
	content LONGTEXT NOT NULL,
	`time` TIMESTAMP DEFAULT NOW( ),
	FOREIGN KEY ( tea_id ) REFERENCES teacher ( id ) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY ( course_id ) REFERENCES course ( id ) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE tea_comment (
	id INT PRIMARY KEY AUTO_INCREMENT,
	parent_id INT DEFAULT 0,
	user_name VARCHAR ( 30 ) NOT NULL,
	head_img VARCHAR ( 500 ) NOT NULL,
	invitation_id INT NOT NULL,
	content LONGTEXT NOT NULL,
	`time` TIMESTAMP DEFAULT NOW( ),
	FOREIGN KEY ( invitation_id ) REFERENCES tea_invitation ( id ) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE notice (
	id INT PRIMARY KEY AUTO_INCREMENT,
	course_id INT NOT NULL,
	title VARCHAR ( 255 ) NOT NULL,
	content LONGTEXT NOT NULL,
	`overdue_time` TIMESTAMP,
	`time` TIMESTAMP DEFAULT NOW(),
	FOREIGN KEY ( course_id ) REFERENCES course ( id ) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE course_tag (
	id INT PRIMARY KEY AUTO_INCREMENT,
	`name` VARCHAR(30) NOT NULL,
	course_id INT NOT NULL,
FOREIGN KEY ( course_id ) REFERENCES course ( id ) ON DELETE CASCADE ON UPDATE CASCADE
)