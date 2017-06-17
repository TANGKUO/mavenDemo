SELECT * FROM ACCOUNT;
select * from service;
select * from admin_info;

create table admin_info_wz as select * from admin_info;

select * from admin_info_wz;

SELECT a.real_name,s.id  FROM SERVICE S, ACCOUNT A WHERe s.id=a.recommender_id;

DROP TABLE account_wz cascade constraints  PURGE;
--帐务信息表
CREATE TABLE account_wz
(id number(9) constraint account_wz_id_pk primary key,
 recommender_id NUMBER(9) CONSTRAINT account_wz_reid_fk REFERENCES account_wz(ID) on delete cascade,
 login_name VARCHAR2(30) NOT NULL CONSTRAINT account_login_uk_nn UNIQUE,
 login_passwd VARCHAR2(28)  NOT NULL,
 status CHAR(1)  CONSTRAINT account_sta_ck CHECK(status IN (0,1,2)),
 create_date DATE DEFAULT SYSDATE,
 pause_Date DATE,
 close_date DATE,
 real_name VARCHAR2(20) NOT NULL,
 idcard_no CHAR(18) NOT NULL CONSTRAINT account_wz_card_uk UNIQUE,
 birthdate DATE,
 gender CHAR(1) CONSTRAINT account_wz_gender_ck CHECK(gender IN (0,1)),
 occupatuion VARCHAR2(50),
 telephone VARCHAR2(15) constraint account_wz_tel_uk unique,
 email VARCHAR2(50),
 mailaddress VARCHAR2(50),
 zipcode CHAR(6),
 qq VARCHAR2(15),
 last_login_time DATE,
 last_login_ip varchar2(15)
 );
 
ALTER SESSION SET NLS_DATE_FORMAT='yyyy mm dd';
INSERT INTO ACCOUNT_wz(ID,RECOMMENDER_ID,LOGIN_NAME,LOGIN_PASSWD,STATUS,CREATE_DATE,
     REAL_NAME,BIRTHDATE,IDCARD_NO,gender,TELEPHONE)
VALUES(1005,NULL,'taiji001','256528',1,'2008 03 15','zhangsanfeng','19430225','410381194302256528',1,13669351234);

INSERT INTO ACCOUNT_Wz(ID,RECOMMENDER_ID,LOGIN_NAME,LOGIN_PASSWD,STATUS,CREATE_DATE,
REAL_NAME,BIRTHDATE,IDCARD_NO,gender,TELEPHONE)
VALUES(1010,NULL,'xl18z60','190613',1,'2009 01 10','guojing','19690319','330682196903190613',1,13338924567);

INSERT INTO ACCOUNT_Wz(ID,RECOMMENDER_ID,LOGIN_NAME,LOGIN_PASSWD,STATUS,CREATE_DATE,
REAL_NAME,BIRTHDATE,IDCARD_NO,gender,TELEPHONE)
VALUES(1011,1010,'dgbf70','270429',1,'2009 03 01','huangrong','19710827','330902197108270429',1,13637811357);

INSERT INTO ACCOUNT_wz(ID,RECOMMENDER_ID,LOGIN_NAME,LOGIN_PASSWD,STATUS,CREATE_DATE,
REAL_NAME,BIRTHDATE,IDCARD_NO,gender,TELEPHONE)
VALUES(1015,1005,'mjjzh64','041115',1,'2010 03 12','zhangwuji','19890604','610121198906041115',1,13572952468);

INSERT INTO ACCOUNT_wz(ID,RECOMMENDER_ID,LOGIN_NAME,LOGIN_PASSWD,STATUS,CREATE_DATE,
REAL_NAME,BIRTHDATE,IDCARD_NO,gender,TELEPHONE)
VALUES(1018,1011,'jmdxj00','010322',1,'2011 01 1','guofurong','19961022','350581200201010322',1,18617832562);

INSERT INTO ACCOUNT_Wz(ID,RECOMMENDER_ID,LOGIN_NAME,LOGIN_PASSWD,STATUS,CREATE_DATE,
REAL_NAME,BIRTHDATE,IDCARD_NO,gender,TELEPHONE)
VALUES(1019,1011,'ljxj90','310346',1,'2012 02 01','luwushuang','19930731','320211199307310346',1,13186454984);

INSERT INTO ACCOUNT_wz(ID,RECOMMENDER_ID,LOGIN_NAME,LOGIN_PASSWD,STATUS,CREATE_DATE,
REAL_NAME,BIRTHDATE,IDCARD_NO,gender,TELEPHONE)
VALUES(1020,NULL,'kxhxd20','012115',1,'2012 02 20','weixiaobao','20001001','321022200010012115',1,13953410078);
commit;
select * from account_wz;

DROP TABLE cost_wz CASCADE CONSTRAINT PURGE ;
--资费表
 CREATE TABLE cost_wz
 (ID NUMBER(4) CONSTRAINT cost_wz_id_pk PRIMARY KEY,
  NAME VARCHAR2(50) NOT NULL,
  base_duration NUMBER(11),
  base_cost NUMBER(7,2),
  unit_cost NUMBER(7,4),
  status CHAR(1) CONSTRAINT cost_wz_status_ck CHECK(status IN (0,1)),
  descr VARCHAR2(100),
  creatime DATE DEFAULT SYSDATE,
  startime DATE,
  cost_type varchar2(10)
  );
  create sequence cost_wz_seq increment by 1 ;
insert into cost_wz(id,name,base_duration,base_cost,unit_cost,status,descr,creatime,startime,cost_type) VALUES (1,'5.9元套餐',20,5.9,0.4,0,'5.9元20小时/月,超出部分0.4元/时',DEFAULT,NULL,'套餐');
insert into cost_wz(id,name,base_duration,base_cost,unit_cost,status,descr,creatime,startime,cost_type) VALUES (2,'6.9元套餐',40,6.9,0.3,0,'6.9元40小时/月,超出部分0.3元/时',DEFAULT,NULL,'套餐');
insert into cost_wz(id,name,base_duration,base_cost,unit_cost,status,descr,creatime,startime,cost_type) VALUES (3,'8.5元套餐',100,8.5,0.2,0,'8.5元100小时/月,超出部分0.2元/时',DEFAULT,NULL,'套餐');
insert into cost_wz(id,name,base_duration,base_cost,unit_cost,status,descr,creatime,startime,cost_type) VALUES (4,'10.5元套餐',200,10.5,0.1,0,'10.5元200小时/月,超出部分0.1元/时',DEFAULT,NULL,'套餐');
insert into cost_wz(id,name,base_duration,base_cost,unit_cost,status,descr,creatime,startime,cost_type) VALUES (5,'计时收费',0,0.0,0.5,0,'0.5元/时,不使用不收费',DEFAULT,NULL,'计费');
insert into cost_wz(id,name,base_duration,base_cost,unit_cost,status,descr,creatime,startime,cost_type) VALUES (6,'包月',0,20,0.0,0,'每月20元,不限制使用时间',DEFAULT,NULL,'包月');
commit;
select * from cost_wz;

create sequence cost_seq_wz increment by 5 start with 1000;
 
DROP TABLE host_Wz cascade constraints PURGE;
--服务器表
CREATE TABLE HOST_wz
(ID 		VARCHAR2(15) CONSTRAINT HOST_wz_ID_PK PRIMARY KEY,
NAME 		VARCHAR2(20), 
LOCATION	VARCHAR2(30)
);
INSERT INTO HOST_wz VALUES ('172.16.1.230','sunv210','sz');
INSERT INTO HOST_wz VALUES('172.16.1.220','sun-server','sz');
INSERT INTO HOST_wz VALUES ('172.16.1.210','sun280','sz');
INSERT INTO HOST_Wz VALUES ('172.16.1.200','ultra10','sz');
COMMIT;
SELECT * FROM host_wz;

DROP TABLE service_wz CASCADE constraint  PURGE;
drop sequence ser_sqe;
CREATE SEQUENCE SER_SQE INCREMENT BY 1 START WITH 2000;
select ser_sqe.nextval() from dual;
--业务表
CREATE TABLE service_wz
(ID NUMBER(10) CONSTRAINT service_wz_id_pk PRIMARY KEY,
 account_id NUMBER(9) CONSTRAINT service_wz_account_id_fk REFERENCES account_wz(ID),
 unix_host VARCHAR2(15) NOT NULL constraint service_wz_host_fk references host_wz(id),
 os_username VARCHAR2(8) not null,
 login_passwd VARCHAR2(8),
 status CHAR(1) CONSTRAINT service_wz_status_ck CHECK(status IN (0,1,2)),
 create_date DATE DEFAULT SYSDATE,
 pause_date DATE,
 close_date DATE,
 cost_id NUMBER(4) CONSTRAINT service_wz_cost_id_fk REFERENCES cost_wz(ID),
 CONSTRAINT ser_wz_host_name_uk UNIQUE(unix_host,os_username)
 );

DESC SERVICE_WZ;
create table service_wz as select * from service;
ALTER SESSION SET NLS_DATE_FORMAT='yyyy mm dd hh24:mi:ss';
INSERT INTO SERVICE_WZ (ID,ACCOUNT_ID,UNIX_HOST,OS_USERNAME,LOGIN_PASSWD,STATUS,CREATE_DATE,PAUSE_DATE,CLOSE_DATE,COST_ID) VALUES (SER_SQE.NEXTVAL,1010,'172.16.1.230','guojing','guo1234',0,'2009 03 10 10:00:00',NULL,NULL,1);
INSERT INTO SERVICE_WZ (ID,ACCOUNT_ID,UNIX_HOST,OS_USERNAME,LOGIN_PASSWD,STATUS,CREATE_DATE,PAUSE_DATE,CLOSE_DATE,COST_ID) VALUES (SER_SQE.NEXTVAL,1011,'172.16.1.220','huangr','huang234',0,'2009 03 01 15:30:05',NULL,NULL,1);
INSERT INTO SERVICE_WZ (ID,ACCOUNT_ID,UNIX_HOST,OS_USERNAME,LOGIN_PASSWD,STATUS,CREATE_DATE,PAUSE_DATE,CLOSE_DATE,COST_ID) VALUES (SER_SQE.NEXTVAL,1011,'172.16.1.230','huangr','huang234',0,'2009 03 01 15:30:10',NULL,NULL,3);
INSERT INTO SERVICE_WZ (ID,ACCOUNT_ID,UNIX_HOST,OS_USERNAME,LOGIN_PASSWD,STATUS,CREATE_DATE,PAUSE_DATE,CLOSE_DATE,COST_ID) VALUES (SER_SQE.NEXTVAL,1011,'172.16.1.210','huangr','huang234',0,'2009 03 01 15:30:15',NULL,NULL,6);
INSERT INTO SERVICE_WZ (ID,ACCOUNT_ID,UNIX_HOST,OS_USERNAME,LOGIN_PASSWD,STATUS,CREATE_DATE,PAUSE_DATE,CLOSE_DATE,COST_ID) VALUES (SER_SQE.NEXTVAL,1019,'172.16.1.200','luwsh','luwu2345',0,'2012 02 10 23 :50:55',NULL,NULL,4);
INSERT INTO SERVICE_WZ (ID,ACCOUNT_ID,UNIX_HOST,OS_USERNAME,LOGIN_PASSWD,STATUS,CREATE_DATE,PAUSE_DATE,CLOSE_DATE,COST_ID) VALUES (SER_SQE.NEXTVAL,1019,'172.16.1.220','luwsh','luwu2345',0,'2012 02 10 00 :00:00',NULL,NULL,5);
INSERT INTO SERVICE_WZ (ID,ACCOUNT_ID,UNIX_HOST,OS_USERNAME,LOGIN_PASSWD,STATUS,CREATE_DATE,PAUSE_DATE,CLOSE_DATE,COST_ID) VALUES (SER_SQE.NEXTVAL,1020,'172.16.1.230','weixb','wei12345',0,'2012 02 10 11:05:20',NULL,NULL,6);
INSERT INTO SERVICE_Wz (id,account_id,unix_host,os_username,login_passwd,status,create_date,pause_date,close_date,cost_id) VALUES (ser_sqe.nextval,1010,'172.16.1.200','guojing','guo09876',0,'2012 02 11 12:05:21',NULL,NULL,6);
COMMIT;
select * from service_wz;

DROP TABLE service_detail_wz CASCADE CONSTRAINT PURGE;
--业务详单表
CREATE TABLE service_detail_wz
(ID NUMBER(11) CONSTRAINT detarl_id PRIMARY KEY,
 service_id NUMBER(10) NOT NULL CONSTRAINT detail_ser_id REFERENCES service_wz(ID),
 client_host VARCHAR2(15) ,
 os_username VARCHAR2(8),
 pid NUMBER(11),
 login_time DATE,
 logout_time DATE,
 duration NUMBER(20,9),
 COST NUMBER(20,9)
 );

alter session set nls_date_format='yyyy mm dd hh24:mi:ss';

INSERT INTO service_detail_wz(ID,service_id,login_time,logout_time,duration,COST) VALUES(0001,2001,'2013 1 1 00:00:00','2013 1 2 05:00:00',18000,NULL);
INSERT INTO service_detail_wz(ID,service_id,login_time,logout_time,duration,COST) VALUES(0002,2002,'2013 1 2 00:00:00','2013 1 3 05:00:00',18000,null); 
--INSERT INTO service_detail_wz(ID,service_id,login_time,logout_time,duration,COST) VALUES(0003,2003); 
--INSERT INTO service_detail_wz(ID,service_id,login_time,logout_time,duration,COST) VALUES(0004,2004);
--INSERT INTO service_detail_wz(ID,service_id,login_time,logout_time,duration,COST) VALUES(0005,2005); 
--INSERT INTO service_detail_wz(ID,service_id,login_time,logout_time,duration,COST) VALUES(0006,2006); 
--INSERT INTO service_detail_wz(ID,service_id,login_time,logout_time,duration,COST) VALUES(0007,2007);
--INSERT INTO service_detail_wz(ID,service_id,login_time,logout_time,duration,cost) VALUES(0008,2008);
commit;



select * from account_wz;
select * from service_wz;
commit;