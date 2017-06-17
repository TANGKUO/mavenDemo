----第二范式:每个非主属性必须完全依赖于主属性
----第三范式:每个非主属性不能依赖于另一个非主属性

SELECT * FROM ACCOUNT;
select * from service;
----定义主键 （primary key） 确定数据的唯一性（即表中不允许有重复的记录） 和 不能为null
----主键约束有列级约束和表级约束（主要解决联合主键问题）
----（对于联合约束其中的联合成员可以重复，但他们的联合不能重复）
SELECT real_name FROM jsd1303.ACCOUNT;
CREATE TABLE stu_con1
(SID NUMBER(5),
cid NUMBER(5),
CONSTRAINT stu_con1_sidcid_pk PRIMARY KEY(SID,cid),
score number(5,2) constraint stu_con1_score_uk unique
);

drop table stu_con1 PURGE;

INSERT INTO stu_con1 VALUES (00001,00001,100);
select * from stu_con1;

----非空约束 not null 只有列约束


----唯一约束 unique （key）唯一但这个列可以为空且可有多条记录为空


----定义检查约束  check语句 constraint ...check(条件语句condition)


----外键约束:列名＋数据类型＋约束名＋references+父表（列名）
----被引用表为父表:parent;定义外键的表为子表child
----在子表中定义外键，则外键列（fk）要引用父表中的列，要求父表的该列唯一（pk,uk）
----fk..on deleted cascade;级联删除（其低沉也遵循现删除子表再删除父表的内容）
----fk..on deleted set null；
CREATE TABLE c1(k1 NUMBER(5,2) CONSTRAINT c1_k1_fk REFERENCES stu_con1(score));
INSERT INTO c1 VALUES (99.99);
drop table c1 purge;
select * from c1;


CREATE TABLE jsd1303
(ID NUMBER(3) CONSTRAINT jsd1303_id_pk PRIMARY KEY,
 NAME CHAR(10) NOT NULL
);

insert into jsd1303 values(002,'欧阳正三');

CREATE TABLE cont
(ID NUMBER(4) CONSTRAINT cont_id_pk PRIMARY KEY,
 NAME CHAR(10)  NOT NULL
);

CREATE TABLE goal
(SID NUMBER(3) CONSTRAINT goal_sid_fk REFERENCES jsd1303(ID),
 cid NUMBER(4) CONSTRAINT goal_cid_fk REFERENCES cont(ID),
 score NUMBER(4,1) NOT NULL,
 CONSTRAINT goal_sidcid_pk PRIMARY KEY(SID,cid)
 );
 select * from goal;
 
