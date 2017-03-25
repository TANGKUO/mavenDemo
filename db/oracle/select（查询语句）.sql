--order by＋ 列名＋排序方式 实现查询 结果集 的排序
select  create_date,os_username FROM service order by create_date desc;
--null值在排序中永远是最大的
select base_cost,base_duration from cost order by base_cost desc;
--order by后面可以跟列名,表达式,别名，位置
select base_cost*12 from cost order by base_cost*12 desc;
--dual单行表
select 2*2*2 from dual;

select unix_host,create_date,os_username from service order by 1,2 desc;

create table firsttest
(c1    number,
 c2    number(6),
 c3    number(4,3),
 c4    number(3,-3)
 );
 desc firsttest
 insert into firsttest values (1.11,1000,1,1);
 insert into firsttest(c2) values (100000);
 insert into firsttest(c3) values (9.999);
 insert into firsttest(c4) values (100999);
 commit;
 
 select * from firsttest;
 
 select round(45.923,2) from dual;
 --round(value,int)对alue进行int值的四舍五入,trunc(value,int)却没有四舍五入
 select round(nvl(base_cost/30,0),3) day_cost,trunc(nvl(base_cost/30,0),3) day_cost1 from cost;
 
 select sysdate from dual;
 
 create table Mr(c1 date);
 
 alter session set nls_date_format='yyyy-mm-dd hh24:mi:ss';
 
 select sysdate from dual;
  
 SELECT * FROM MR;
 
 insert into Mr values (to_date('2013-09-23 15:22:59','yyyy-mm-dd hh24:mi:ss'));
 
 select to_char(c1,'yyyy-mm-dd hh24:mi:ss') from mr;
 
 select create_date,unix_host from service where to_char(create_date,'mm')='03';
 
select to_number('a','xx') from dual;  
--varchar2()对空格很敏感,所以查找的是实际字符集,char（）没其敏感，故空格会被其处理掉，
--同时他们对字符的大小写也敏感
--varchar2()是变长的，必须定义长度,char()是定长的，可以不定义长度
--fm去除前导0和空格,或者用rtrim，ltrim去除右,左空格
create table test001(c1 varchar2(10),c2 char(10));

insert  into test001 values('ab','ab');

select length(c1),c1||'c',length(c2),c2||'c' from test001;

select * from test001;

select nvl(to_char(base_cost),'no base cost') base_cost,nvl(to_char(unit_cost),'no unit cost') unit_cost from cost ;


