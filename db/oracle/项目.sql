create sequence account_wz_seq increment by 5 start with 10; 
select account_wz_seq.nextval from dual;

select * from account_wz;

desc service_wz;
select * from service_wz;
commit;
rollback;

select account_wz_seq.nextval from dual;
desc account_wz;
desc account_wz;

desc service_wz;
select * from cost_wz;
create sequence service_wz_seq increment by 1 start with 10;
select service_wz_seq.nextval  from dual;


delete from account_wz where id=1011;