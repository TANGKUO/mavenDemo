----create table 后跟子查询 create table newName as select * from table where...group by...having...order by...
----insert into  后跟子查询 insert into newtablename select* from table .....(无as）
----view相当于快捷键一般，实现简单查询（可以执行DML语句对源表进行增删改） ， 权限控制（即读视图的权限而非表的权限）,
----连接视图（基于多个表建立视图）格式和create table子查询一样
----视图维护  维护的是视图后的select语句 如果源表删除 那么所有依赖它的对象（如view）都将失效
----改变视图定义:create or replace view
----改变视图状态:alter view view_name compile；
----删除视图：drop view view_name
----在视图后where...with chcek option constraint_type表示在进行插入时必须满足where条件；
----在试图后where...with read only （缩写o）只读；
select * from emp;

create view t_emp
as
select empno,ename,job from emp
where deptno=20;

update emp set deptno=10
where deptno is null;
SELECT * FROM service;
SELECT * FROM ACCOUNT;
select * from host;
CREATE OR REPLACE VIEW t
AS
SELECT A.real_name,k.NAME,k.host FROM ACCOUNT_11 A LEFT JOIN 
(SELECT s.account_id id,c.NAME name,s.unix_host host FROM service_11  s LEFT JOIN COST_11 c
ON s.cost_id=c.ID) k
on a.id=k.id;
select avg(base_cost) from cost;
select to_number('ab') from dual;
SELECT * FROM cost;                                                                
DROP VIEW u ;
commit;


SELECT A.real_name,c.NAME FROM ACCOUNT A,service s,COST c
WHERE A.ID=s.account_id(+)
AND s.cost_id=c.ID(+);

SELECT h.id,h.name FROM service s,host h
WHERE s.os_username(+)='weixb'
AND s.unix_host(+)=h.ID
and s.id is null;

