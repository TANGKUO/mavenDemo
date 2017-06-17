/*非预定义异常: declare d_name exception pragma exception_init(e_name,xxxx异常编号)
  自定义异常: declare...(e_name exception)...begin...(raise exception)...exception (when ...then..)   end;*/
set serveroutput on;
declare
      type t_wz is table of varchar2(20)
      index by binary_integer;
      cursor c_wz is select * from account where 1=2;
      n number:=1;
      v_wz t_wz;
begin
      for i in c_wz loop
      n:=i.id;
      v_wz(n):=i.real_name;
      end loop;
      if v_wz.first is not null then 
      for i in v_wz.first..v_wz.last loop
      dbms_output.put_line(i||' '||v_wz(i));
      end loop;
      end if;
      commit;
end;
----------------------------------------------------------
declare
      type t_zw is table of varchar2(20)
      index by binary_integer;
      cursor c_zw is select * from account;
      v_zw c_zw%rowtype;
      v_zwz t_zw;
      n binary_integer;
begin
      open c_zw;
      fetch c_zw into v_zw;
      while c_zw%found loop
      v_zwz(v_zw.id):=v_zw.real_name;
      fetch c_zw into v_zw;
      end loop;
      close c_zw;
      
      n :=v_zwz.first;
      while n<v_zwz.last loop
      dbms_output.put_line(n||' '||v_zwz(n));
      n:=v_zwz.next(n);
      end loop;
end;

------------------------------------------------------------------------
declare
      type t_recomd1 is record (real_name account.real_name%type,
      birthdate account.birthdate%type);
      type t_wwz is table of t_recomd1 index by binary_integer;
      v_wwz t_wwz;
      n binary_integer;
      k binary_integer;
begin
      select real_name,birthdate
      bulk collect into v_wwz
      from account;
      n:=v_wwz.first;
      k:=0;
      while n<v_wwz.last loop
      k:=k+round((sysdate-v_wwz(n).birthdate)/365);
      dbms_output.put_line(v_wwz(n).real_name||' '||round((sysdate-v_wwz(n).birthdate)/365)||'岁 共'||k||'岁');
      n:=v_wwz.next(n);
      end loop;
end;
      
-------------------------------------------------------------------------  

create or replace procedure account_xdx
as
v_cnt binary_integer;
begin
 select count(id) into v_cnt from account;
 dbms_output.put_line('account number is:'||v_cnt);
 end;
drop procedure account_xd;
exec account_xdx;

--------------------------------------------------------------------------
create or replace procedure wz_t
(p_c1 varchar2,p_c2 out varchar2,p_c3 in out varchar2)
as
  p_p varchar2(10);
begin 
  p_p:=p_c1||'k';
  p_c2:=p_c2||'u';
  p_c3:=p_c3||'k';
  SYS.dbms_output.put_line(p_p||' '||p_c2||' '||p_c3);
end;

declare
  t1 varchar2(10):='fuc';
  t2 varchar2(10):='fuc';
  t3 varchar2(10):='fuc';
begin
   wz_t(t1,t2,t3);
  sys.dbms_output.put_line(t1||' '||t2||' '||t3);
end;
--------------------------------------------------------------------------
show user;
set timing off;
desc account;
show all;
set autocommit off;

create or replace procedure wz
--(id number,real_name varchar2,age number)
as
begin
  execute immediate'create table mike1(id number(10),real_name varchar2(20))';
end;

begin
  wz;
end;

drop table mike;

select * from mike1;

variable p_c2 varchar2(20)
variable p_c3 varchar2(20)

exec wz_t(1001,:v_realname,:v_age)
print v_realname
print v_Age

--select 'select '''||tname||''',count(*) from '||tname||';' from tab where tabtype='TABLE';
--select 'grant select on '||table_name||' to jsd1303;' from user_tables;
--SELECT 'GRANT SELECT ON '||TNAME||' TO 用户名;' FROM TAB; 