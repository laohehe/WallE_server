DROP DATABASE IF EXISTS web_api;
CREATE DATABASE web_api DEFAULT CHARACTER SET utf8 ;
USE web_api;

DROP TABLE IF EXISTS collections;
CREATE TABLE collections (
	`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户收藏表id',
	`openid` VARCHAR(100),
	`type` TINYINT,
	`item_id` SMALLINT,
	`date` date,
	PRIMARY KEY(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS users;
CREATE TABLE users (
	`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户信息表id',
	`nickName` VARCHAR(100) COMMENT '用户昵称',
	`avatarUrl` VARCHAR(500) COMMENT '用户图像地址',
	`openid` VARCHAR(100),
	PRIMARY KEY(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS comments;
CREATE TABLE comments (
	`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户评论表id',
	`openid` VARCHAR(50),
	`type` TINYINT,
	`item_id` SMALLINT,
	`date` date COMMENT '文章发布的日期' ,
	`comment` VARCHAR (2000),
	`starnum` int DEFAULT 0 COMMENT '评论点赞数',
	`comntime` DATETIME COMMENT '评论时间',
	PRIMARY KEY(id) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS userstarcomm;
CREATE TABLE userstarcomn (
	`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户点赞评论表id',
	`openid` VARCHAR(50),
	`comnid` int(11) COMMENT '评论表id',
	PRIMARY KEY(id)   
)ENGINE=InnoDB DEFAULT CHARSET=utf8;