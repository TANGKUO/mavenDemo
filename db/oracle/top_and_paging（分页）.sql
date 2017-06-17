select t.rn,t.real_name from
delete from account_wz where id=1285;
commit;
(SELECT ROWNUM rn,real_name
FROM ACCOUNT
WHERE ROWNUM<=6) t
where t.rn>=4;

SELECT rn,t.real_name,t.create_date FROM 
(SELECT ROWNUM rn,real_name,create_date
  FROM
      (SELECT ROWNUM,real_name,create_date
      FROM ACCOUNT
      ORDER BY create_date) t1
  WHERE ROWNUM<=6)t
where rn>=4;

SELECT * FROM ACCOUNT;
select * from service;

SELECT aa.real_name,nvl(c.name,'无') FROM COST c RIGHT JOIN 
(SELECT a.real_name,s.cost_id  
FROM ACCOUNT A LEFT JOIN service s
ON s.account_id=A.ID) Aa
on aa.cost_id=c.id;