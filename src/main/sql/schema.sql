CREATE DATABSE seckill;

CREATE TABLE seckill(
  `seckill_id` BIGINT NOT NUll AUTO_INCREMENT COMMENT 'id',
  `name` VARCHAR (120) NOT NULL COMMENT'commodity name',
  `number` int NOT NULL COMMENT 'stock number',
  `start_time` TIMESTAMP NOT NULL COMMENT 'kill start time',
  `end_time`   TIMESTAMP   NULL COMMENT 'kill end time',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
 PRIMARY KEY (seckill_id),
  key idx_start_time(start_time),
  key idx_end_time(end_time),
  key idx_create_time(create_time)
)ENGINE=INNODB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='kill table';

insert into seckill(name,number,start_time,end_time)
values
('1000 kill iphone6',100,'2015-11-01 00:00:00', '2017-11-02 00:00:00'),
('1000 kill ipad2',200,'2015-11-01 00:00:00', '2015-11-02 00:00:00'),
('1000 kill mini 4',300,'2015-11-01 00:00:00', '2015-11-02 00:00:00'),
('1000 kill mini note',400,'2015-11-01 00:00:00', '2015-11-02 00:00:00');

create table success_killed(
  `seckill_id` bigint NOT  null COMMENT 'kill commodity id',
  `user_phone` bigint NOT  null COMMENT 'user phone number',
  `state` tinyint NOT NULL DEFAULT -1 COMMENT 'state:-1 nullsense, 0:success, 1:paid',
  `create_time` TIMESTAMP not null COMMENT 'CREATE TIME',
  PRIMARY  KEY (seckill_id,user_phone), /*COMBINE KEY*/
  key idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT  CHARSET=utf8 COMMENT='kill success detail';

--connect database console