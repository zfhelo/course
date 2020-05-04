### 将此目录下的application.properties文件和course.jar文件放在同个目录下在使用以下指令运行
> 先添加此表
```sql
CREATE TABLE course_tag (
	id INT PRIMARY KEY AUTO_INCREMENT,
	`name` VARCHAR(30) NOT NULL,
	course_id INT NOT NULL,
FOREIGN KEY ( course_id ) REFERENCES course ( id ) ON DELETE CASCADE ON UPDATE CASCADE
)
```
**java -jar course.jar --spring.profiles.active=prod**