-- DO $$ DECLARE
--    r RECORD;
-- BEGIN
--    FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = 'public') LOOP
--       EXECUTE 'DROP TABLE IF EXISTS ' || quote_ident(r.tablename) || ' CASCADE';
--    END LOOP;
-- END $$;






CREATE TABLE users
(
	"id"			serial			PRIMARY KEY,
	email 			varchar(100)	NOT NULL UNIQUE,
	phone			char(10)		NOT NULL UNIQUE,
	"password"		char(60)		NOT NULL,
	full_name		varchar(100)	NOT NULL,
	nick_name		varchar(100)	,
	avatar			varchar(200)	,
	dob				date			NOT NULL,
	created_at		timestamp 		NOT NULL,
	locked			boolean			NOT NULL,
	enable			boolean			NOT NULL,
	is_online		boolean			NOT NULL,
	"role"			varchar			NOT NULL CHECK(role in ('user','admin'))	
);

CREATE TABLE confirmation_token
(
	"id"	serial	PRIMARY KEY,
	"token" varchar(100) NOT NULL,
	created_at timestamp NOT NULL,
	expires_at timestamp NOT NULL,
	confirmed_at timestamp,
	user_id int NOT NULL,
	FOREIGN KEY(user_id) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE blocks
(
	user_id			int		NOT NULL,
	blocked_user	int		NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (blocked_user) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE,
	PRIMARY KEY(user_id, blocked_user)
);

CREATE TABLE friends
(
	user_id		int		NOT NULL,
	friend_id	int		NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (friend_id) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE,
	PRIMARY KEY(user_id, friend_id)
);

CREATE TABLE chats
(
	id				serial			PRIMARY KEY, 
	is_deleted		boolean			NOT NULL DEFAULT FALSE,
	first_user		int				NOT NULL,
	second_user		int				NOT NULL,
	FOREIGN KEY (first_user) REFERENCES users(id),
	FOREIGN KEY (second_user) REFERENCES users(id)
);

CREATE TABLE  group_chats
(
	id				serial		PRIMARY KEY,
	is_deleted		boolean		NOT NULL DEFAULT FALSE, 
	number_member	integer		DEFAULT 0
);

CREATE TABLE attend_gchats
(
	user_id 		int		NOT NULL,
	groupchat_id	int		NOT NULL,
	PRIMARY KEY(user_id, groupchat_id),
	FOREIGN KEY (user_id) REFERENCES users(id) ON UPDATE CASCADE ON DELETE NO ACTION,
	FOREIGN KEY (groupchat_id) REFERENCES group_chats(id) ON UPDATE CASCADE ON DELETE NO ACTION
);

CREATE TABLE messages
(
	id			serial			PRIMARY KEY,
	content		varchar(100)	NOT NULL CHECK (content <> ''),
	is_deleted	boolean			NOT NULL DEFAULT FALSE, --TRUE, IT'S REMOVED
	send_time	timestamp		NOT NULL,
	link_file	varchar(200)	,
	user_id		serial			NOT NULL,
	chat_id		serial			NOT NULL,
	gchat_id	serial			NOT NULL,
	type		varchar			NOT NULL CHECK(type in ('pair','group')),
	FOREIGN KEY (user_id) REFERENCES users(id) ON UPDATE CASCADE ON DELETE NO ACTION,
	FOREIGN KEY (chat_id) REFERENCES chats(id) ON UPDATE CASCADE ON DELETE CASCADE,	
	FOREIGN KEY (gchat_id) REFERENCES group_chats(id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE posts
(
	id			serial			PRIMARY KEY,
	"like" 		integer		    NOT NULL CHECK("like">=0),
	heart 		integer			NOT NULL CHECK(heart>=0),
	haha 		integer			NOT NULL CHECK(haha>=0),
	content		text			NOT NULL CHECK(content <> ''),
	number_comment int			NOT NULL CHECK(number_comment>=0),
	image		varchar(200	)	,
	is_deleted	boolean			NOT NULL DEFAULT FALSE,
	user_id		int				NOT NULL,
	shared_post	int				,
	FOREIGN KEY (user_id) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (shared_post) REFERENCES posts(id) ON UPDATE CASCADE ON DELETE NO ACTION
);


CREATE TABLE "comments"
(
	id					serial			PRIMARY KEY,
	"like" 				integer		    NOT NULL CHECK("like">=0),
	heart 				integer			NOT NULL CHECK(heart>=0),
	haha 				integer			NOT NULL CHECK(haha>=0),
	content				text			NOT NULL CHECK(content <> ''),
	image				varchar(200)	,
	post_id				int				NOT NULL,
	user_id				int				NOT NULL,
	is_deleted			boolean			NOT NULL DEFAULT FALSE,
	number_comment 		int				NOT NULL CHECK(number_comment>=0),
	FOREIGN KEY	(post_id)	REFERENCES	posts(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY	(user_id)	REFERENCES	users(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE reactions
(
	user_id			int			NOT NULL,
	post_id			int			NOT NULL,
	type			char(5)		NOT NULL CHECK(type in ('none','like','heart','haha')),
	FOREIGN KEY (user_id) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (post_id) REFERENCES posts(id),
	PRIMARY KEY (user_id, post_id)
);

--trigger for number of member 
CREATE OR REPLACE FUNCTION
check_number_member()
RETURNS TRIGGER AS $$
BEGIN 
	UPDATE group_chats SET number_member = (SELECT COUNT(*) FROM attend_gchats WHERE groupchat_id = NEW.groupchat_id) WHERE id=NEW.groupchat_id;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_number_member
AFTER INSERT OR UPDATE ON attend_gchats
FOR EACH ROW
EXECUTE FUNCTION check_number_member();

CREATE OR REPLACE FUNCTION
decrease_number_member()
RETURNS TRIGGER AS $$
BEGIN 
	UPDATE group_chats SET number_member = (SELECT COUNT(*) FROM attend_gchats WHERE groupchat_id = OLD.groupchat_id) WHERE id=OLD.groupchat_id;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_decrease_number_member
AFTER DELETE ON attend_gchats
FOR EACH ROW
EXECUTE FUNCTION decrease_number_member();


--trigger kiem tra avatar va fullname

CREATE OR REPLACE FUNCTION
update_number_comment()
RETURNS TRIGGER AS $$
BEGIN 
	UPDATE posts SET number_comment = (SELECT COUNT(*) FROM "comments" WHERE (post_id = NEW.post_id or post_id = OLD.post_id)) WHERE (id=NEW.post_id or id=OLD.post_id);
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trigger_update_number_comment
AFTER DELETE OR INSERT OR UPDATE ON "comments"
FOR EACH ROW
EXECUTE FUNCTION update_number_comment();



-- trigger count like, heart, haha on reactions
CREATE OR REPLACE FUNCTION 
count_reaction()
RETURNS TRIGGER AS $$
DECLARE
	like_count integer;
	heart_count integer;
	haha_count integer;
BEGIN 
	like_count:=(SELECT COUNT(*) FROM reactions where type='like' and post_id=NEW.post_id);
	heart_count:=(SELECT COUNT(*) FROM reactions where type='heart' and post_id=NEW.post_id);
	haha_count:=(SELECT COUNT(*) FROM reactions where type='haha' and post_id=NEW.post_id);
	UPDATE posts SET 
		"like"=like_count,
		heart=heart_count,
		haha=haha_count
	WHERE posts.id=NEW.post_id;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_count_reaction
AFTER INSERT OR UPDATE ON reactions
FOR EACH ROW
EXECUTE FUNCTION count_reaction();


-- trigger update like,heart,haha when row is deleted on reactions
CREATE OR REPLACE FUNCTION 
decrease_reaction()
RETURNS TRIGGER AS $$
DECLARE
	like_count integer;
	heart_count integer;
	haha_count integer;
BEGIN 
	like_count:=(SELECT COUNT(*) FROM reactions where type='like' and post_id=OLD.post_id);
	heart_count:=(SELECT COUNT(*) FROM reactions where type='heart' and post_id=OLD.post_id);
	haha_count:=(SELECT COUNT(*) FROM reactions where type='haha' and post_id=OLD.post_id);
	UPDATE posts SET 
		"like"=like_count,
		heart=heart_count,
		haha=haha_count
	WHERE posts.id=OLD.post_id;
	RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_decrease_reaction
AFTER DELETE ON reactions
FOR EACH ROW
EXECUTE FUNCTION decrease_reaction();





-- insert sample data
insert into users(email,phone,password,full_name,nick_name,avatar,dob,created_at,locked,enable,is_online,role)
values('test123@gmail.com','0913200212','password','Duong Tan Thanh','onion','https://th.bing.com/th/id/OIP.HP0nbNthAdAFIS9rF9IeEAHaHn?pid=ImgDet&rs=1','2002-04-09',now(),FALSE,FALSE,TRUE,'user');
insert into users(email,phone,password,full_name,nick_name,avatar,dob,created_at,locked,enable,is_online,role)
values('test2@gmail.com','0913200213','password','Duong Tan Thanh 2','onion','https://th.bing.com/th/id/OIP.HP0nbNthAdAFIS9rF9IeEAHaHn?pid=ImgDet&rs=1','2002-04-09',now(),FALSE,FALSE,TRUE,'user');
insert into users(email,phone,password,full_name,nick_name,avatar,dob,created_at,locked,enable,is_online,role)
values('test3@gmail.com','0913200214','password','Duong Tan Thanh 3','onion','https://th.bing.com/th/id/OIP.HP0nbNthAdAFIS9rF9IeEAHaHn?pid=ImgDet&rs=1','2002-04-09',now(),FALSE,FALSE,TRUE,'user');
insert into users(email,phone,password,full_name,nick_name,avatar,dob,created_at,locked,enable,is_online,role)
values('test4@gmail.com','0913200215','password','Duong Tan Thanh 4','onion','https://th.bing.com/th/id/OIP.HP0nbNthAdAFIS9rF9IeEAHaHn?pid=ImgDet&rs=1','2002-04-09',now(),FALSE,FALSE,TRUE,'user');
insert into users(email,phone,password,full_name,nick_name,avatar,dob,created_at,locked,enable,is_online,role)
values('test5@gmail.com','0913200216','password','Duong Tan Thanh 4','onion','https://th.bing.com/th/id/OIP.HP0nbNthAdAFIS9rF9IeEAHaHn?pid=ImgDet&rs=1','2002-04-09',now(),FALSE,FALSE,TRUE,'user');
insert into users(email,phone,password,full_name,nick_name,avatar,dob,created_at,locked,enable,is_online,role)
values('test6@gmail.com','0913200217','password','Duong Tan Thanh 4','onion','https://th.bing.com/th/id/OIP.HP0nbNthAdAFIS9rF9IeEAHaHn?pid=ImgDet&rs=1','2002-04-09',now(),FALSE,FALSE,TRUE,'user');
insert into users(email,phone,password,full_name,nick_name,avatar,dob,created_at,locked,enable,is_online,role)
values('test7@gmail.com','0913200218','password','Duong Tan Thanh 4','onion','https://th.bing.com/th/id/OIP.HP0nbNthAdAFIS9rF9IeEAHaHn?pid=ImgDet&rs=1','2002-04-09',now(),FALSE,FALSE,TRUE,'user');


insert into blocks values (1,2);
insert into blocks values (1,3);
insert into blocks values (4,2);


insert into friends values(1,4);
insert into friends values(1,5);
insert into friends values(2,3);

insert into chats(first_user,second_user) values(1,4);
insert into chats(first_user,second_user) values(1,5);
