create table member (
	member_id		varchar(20)		not null 	primary key,
	member_pw		varchar(20)		not null,
	member_name 	varchar(20)		not null,
	member_phone	varchar(30),
	member_email 	varchar(50)
	);
drop table member ;
CREATE TABLE board (
	b_num INT PRIMARY KEY AUTO_INCREMENT,
	b_category VARCHAR(20) NOT NULL,
	b_title VARCHAR(20) NOT NULL,
	b_writer VARCHAR(20) NOT NULL, 
	b_contents VARCHAR(10000) NOT NULL,
	b_reg CHAR(17) NOT NULL,
	b_hit INT,
	b_id VARCHAR(20),
 	FOREIGN KEY (b_id)
    REFERENCES member(member_id) ON UPDATE CASCADE
);  
 