--连接:内连接 外连接
--集合:交集intersect 并集union/unionAll 差minus
/*方法1*/
SELECT real_name,recommender_id FROM account
WHERE recommender_id IS NOT NULL
UNION ALL
SELECT real_name,recommender_id FROM account
WHERE recommender_id IS NULL;
/*方法2*/
SELECT a1.real_name,a2.real_name FROM ACCOUNT a1 JOIN ACCOUNT a2
ON a1.ID=a2.recommender_id
UNION ALL
SELECT real_name,'No recommender' FROM ACCOUNT
where recommender_id is null;

SELECT * FROM COST;
SELECT * FROM host;
SELECT * FROM service;

/*交集*/
SELECT COST_id FROM service s JOIN host h
ON s.unix_host=h.ID AND h.NAME='sun280'
INTERSECT
SELECT COST_id FROM service s1 JOIN host h1
ON s1.unix_host=h1.ID AND h1.NAME='sun-server';

/*差*/
SELECT ID FROM host
MINUS
select unix_host from service;

