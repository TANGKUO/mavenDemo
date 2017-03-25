select * from (select rownum rn,a.* from (select * from cost_wz order by  base_cost) a) where rn between 1 and 3;

alter session set nls_date_format ='yyyy-mm-dd hh:mi:ss';
--order by后跟位置时 其数字为select后面所跟列名的位置
select unix_host,os_username,round(sysdate-create_date) days from service
order by 3 desc;
select * from cost_wz;
select add_months(sysdate,6) from dual;

select last_day(sysdate) from dual;

select add_months(sysdate, 1) from dual;

select add_months(sysdate,-1) from dual;

select next_day(sysdate,'monday') from dual;
--case when...then......else....end....from...;when....then....可以多个;
select case when base_duration=20
            then unit_cost+0.05
            when base_duration=40
            then unit_cost+0.03
       else unit_cost
       end new_unit_cost,
       unit_cost,
       base_duration
       from cost;
--decode(values,if1,then,if2,then,...,else)      
select decode(base_duration,20,unit_cost+0.05,40,unit_cost+0.03,unit_cost)
        new_unit_cost,base_duration,unit_cost
        from cost;
        
select count(unit_cost),avg(unit_cost),sum(unit_cost),
            max(unit_cost),min(unit_cost) from cost;
            
select count(unit_cost),avg(unit_cost),sum(unit_cost),
            max(unit_cost),min(unit_cost) from cost
            where unit_cost is null;
--group by（组函数）和 having(组函数的过滤器）          
select unix_host,min(create_date) from service group by unix_host having count(*)>2;

select unix_host,to_char(create_date,'yyyy-mm-dd') 
      from service group by unix_host,to_char(create_date,'yyyy-mm-dd') having count(*)>1;


--子查询
select create_Date,unix_host,os_username from service where create_date
                        =(select min(create_date)
                        from service);

select unix_host,os_username,round(sysdate-create_date) 
                                from service where (unix_host,round(sysdate-create_date)) in
                                (select unix_host,round(avg(sysdate-create_date))
                                      from service
                                      group by unix_host);                        
                                      
select unix_host,os_username,create_date from service where(unix_host,create_date)
                                      in(select unix_host,min(create_date)
                                      from service
                                      group by unix_host);
                                      
select unix_host,to_char(create_date,'yyyy-mm-dd'),count(os_username) 
                from service 
                group by unix_host,to_char(create_date,'yyyy-mm-dd') 
                having count(os_username)>1;
                
select unix_host,os_username,create_date
                from service 
                where create_date=(select min(create_Date)
                from service); 
                
select unix_host,create_Date,os_username from service where create_date>any(
select create_Date from service where os_username='huangr' and  unix_host='192.168.0.26');     


select * from account;

select id,real_name from account o where o.recommender_id is not null and  id in(
select recommender_id from account i 
where  o.id= i.recommender_id);
                