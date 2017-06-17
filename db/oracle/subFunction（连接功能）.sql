select * from service;
SELECT * FROM account;

--关联查询，可实现主查询与子查询列的相关关系，即子查询调用了主查询的属性（列）
--执行语句：先执行主查询 再执行子查询 然后才返回子查询中的结果给主查询判断，若满足条件
--则传递该结果，再进入下次循环（loop），反之，直接进入下次循环。
select unix_host,os_username,create_date from service o
where round (sysdate-create_date)>
(select round(avg(sysdate-create_date))
from service i
where i.unix_host=o.unix_host);

--exists语句（存在语句跟在where后） 即有返回值则将结果加入结果集，否则pass掉
--（等价于 in--子查询结果少时推荐使用，主查询少时推荐使用，
--in/not in属于非关联子查询，exists/not exists属于关联子查询
--多列子查询：多列同时比较）
--not exists (不存在语句跟在where后)（等价于 not in）
select id,real_name from account o where not exists(
                          select 1 from account i
                          WHERE o.ID=i.recommender_id);

--cross join 交叉连接,产生table1记录条数*table2记录条数
--select ....from...cross join....

--inner join 内连接（驱动表，匹配表，符合on条件语句这拼凑成一条记录）(匹配上的才列出)
--格式:select tablename1.colname,... from tablename inner join tablename2 on condition
SELECT * FROM ACCOUNT;

select * from service;

SELECT a.real_name,a.id,s.os_username ,s.unix_host from service s inner JOIN ACCOUNT A
on a.real_name='huangrong' and s.account_id=a.id;

SELECT a.id,max(a.real_name),count(*) FROM ACCOUNT A JOIN service s 
ON s.account_id=A.ID  GROUP BY A.ID; 
--join后跟子句，现执行s（中间变量），在执行s于account的内连接
SELECT A.real_name,A.ID,s.f FROM ACCOUNT A JOIN 
(SELECT account_id,count(*) f FROM service
      GROUP BY account_id) s
on a.id=s.account_id;

SELECT s1.os_username,s1.create_date,s2.days FROM service s1 JOIN 
(SELECT unix_host,round(avg(SYSDATE-create_date)) days 
  FROM service GROUP BY unix_host) s2
  ON s1.unix_host=s2.unix_host 
  AND round(SYSDATE-s1.create_Date)>s2.days;
--对同一张表进行的内连接知识点，很重要，有点深度
SELECT 
a1.real_name client,decode(a1.id,a2.id,'no recommender',a2.real_name) recommender
FROM ACCOUNT a1 JOIN ACCOUNT a2
on nvl(a1.recommender_id,a1.id)=a2.id;

SELECT distinct a1.real_name FROM ACCOUNT a1 JOIN ACCOUNT a2
on a1.id=a2.recommender_id;

SELECT real_name FROM ACCOUNT a1
where exists
(SELECT 1 FROM ACCOUNT a2
WHERE a1.ID=recommender_id);

--outer join  (匹配问题＋非匹配问题outer join...on..where＋匹配表.非空列is null)外连接(左，右连接) 
--select...from t1 left(right)(full) (outer) join t2 on....
/*t1 left join t2 t1为驱动表； t1 right join t2 t2为驱动表*/
--outer表跟inner表不同 1，如果没匹配到 匹配表会模拟出一条记录（null）和驱动表中的该条记录匹配 2，驱动表固定

SELECT A.ID, MIN(A.real_name),count(s.account_id) c  FROM ACCOUNT A LEFT JOIN service s
--count(a)a为匹配表中的非空列，group by后跟的是驱动表中的列
on a.id=s.account_id group by a.id order by c desc;

--下式效率更高
SELECT a.real_name,nvl(s.cnt,0) c FROM ACCOUNT A LEFT JOIN(
SELECT account_id,count(id) cnt 
FROM service s
GROUP BY account_id) s
ON A.ID=s.account_id
ORDER BY c DESC;
--对驱动表的过滤用where（即对结果集进行过滤）对匹配表的过滤用on.
SELECT   a1.real_name,a2.real_name,a2.id,a2.recommender_id  FROM ACCOUNT a1  LEFT JOIN ACCOUNT a2
ON  a1.ID=a2.recommender_id
WHERE a2.id IS NULL;

select * from host;

SELECT h.ID  FROM service s right JOIN host h
ON s.unix_host=h.ID AND s.os_username='weixb'
where s.os_username is null;

SELECT h.ID FROM host h LEFT JOIN
(SELECT unix_host,os_username FROM service
where os_username='weixb') s
ON s.unix_host=h.ID
where s.os_username is null;

SELECT * FROM age_segment;

SELECT ac.real_name,ac.age,ag.NAME FROM age_segment ag right JOIN (
SELECT real_name,round((SYSDATE-birthdate)/365) age
FROM ACCOUNT) ac
on ac.age between ag.lowage and ag.hiage;

SELECT max(ag.name),count(ag.name) FROM age_segment ag  JOIN (
SELECT real_name,round((SYSDATE-birthdate)/365) age
FROM ACCOUNT) ac
ON ac.age BETWEEN ag.lowage AND ag.hiage
GROUP BY ag.NAME;

SELECT max(ag.name),count(ac.real_name) FROM age_segment ag left JOIN (
SELECT real_name,round((SYSDATE-birthdate)/365) age
FROM ACCOUNT) ac
ON ac.age BETWEEN ag.lowage AND ag.hiage
group by ag.id;

