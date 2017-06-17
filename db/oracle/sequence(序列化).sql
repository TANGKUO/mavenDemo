----主键和唯一键要求列中的每一个值都必须是唯一的（实现方法:数据库对象sequence实现或者 程序员自己写代码）
----nextval（伪例）
----lpad(number,long,char)表示number的长度为long，当长度不够时在其前面补char值...rpad就是在其后面补char值
----格式:
create sequence y
start with 1303001
increment by 1;

select seq.nextval from dual;

create table hhb
(id  char(20));
drop table hhb purge;
insert into hhb values('jsd'||y.nextval);

select * from hhb;