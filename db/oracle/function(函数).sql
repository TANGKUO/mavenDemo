----全局变量（session），局部变量，绑定变量（宿主变量）
create or replace function fucku
(p_in number,p_out out number)
return number
is
begin
 p_out:=2;
 return p_in;
end;

declare
 i number;
begin
 i:=fucku(10,i);
 dbms_output.put_line(i);
end;



-----------------------------------------------------------------------------
create or replace package pak
is 
  type t_rec is record(m1 number,m2 varchar2(10));
  v_rec t_rec;
  procedure proc1;
  function fun1(p_in number)return number;
end;

create or replace package body pak
is

  procedure proc1
  is
  begin
    dbms_output.put_line(v_rec.m1);
  end;
  
  function fun1(p_in number) return number
  is
  begin
    return p_in;
  end;
begin
  v_rec.m1 :=10;--初始化变量
end;

begin 
  pak.v_rec.m1:=pak.fun1(100000);
  pak.proc1;
end;

exec dbms_output.put_line(pak.v_rec.m1);
set serveroutput on;
drop package pak;

-------------------------------------------------------------------
create or replace package ppc
is
    type t_rec is record(id number,real_name varchar2(20),age  binary_integer);
    v_rec t_rec;
    procedure pro(f1 number);
    function foo(f1 number) return t_rec;
end;

create or replace package body ppc
is
    procedure pro(f1 number)
    is
    begin
      dbms_output.put_line(foo(f1).real_name||' '||foo(f1).age);
    end;
    function foo(f1 number) return t_rec
    is
    begin
      select real_name,round((sysdate-birthdate)/365) into v_rec.real_name,v_rec.age 
      from account where id=f1;
      return v_rec;
    end;
end;

begin
    ppc.pro(1011);
end;

select * from service_Wz;

create or replace trigger tr1
after update on service_wz
for each row
declare
begin
    update account_wz set id=:new.id
    where id=:old.id;
end;

update  service_wz  set account_id =1000 where account_id=1011;
select * from account_wz;

  