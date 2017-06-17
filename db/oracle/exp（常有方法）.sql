alter session set nls_language = 'SIMPLIFIED CHINESE';
alter session set nls_language='american';
select to_char(base_cost,'L99.99') from cost;
alter session set nls_territory='china';
select to_char(base_cost,'L99.99') from cost;

select upper(os_username) from service;
select lower(os_username) from service where os_username='huangr';
select base_cost from cost where base_cost not in(1.1,5.8,5.9,10.5);
select base_cost from cost where base_cost is not null;
select base_cost from cost;
select base_cost from cost where base_cost=any(5.9,10.5);
select base_cost from cost where base_cost between 1.1 and 20;

alter session set nls_date_format='yyyy-mm-dd hh24:mi:ss';
select sysdate from dual;

select * from service;

select to_date(create_date,'yyyy-mm-dd hh24:mi:ss') from service;

select to_char(sysdate) from dual;

select to_number('ab','xx') from dual;

create table xD(name char(10),sex varchar2(10));

insert into xD values('徐东','女');

select * from xd;

update xd set name='xx' where name='徐东';

insert into xd values ('tom','男');

select * from xd;



