create or replace procedure ttt110
is
  e_notable exception;
  pragma exception_init(e_notable,-942);
begin
  execute immediate'drop table uut purge';
  execute immediate'create table uut(c1 number(10))';
exception
  when e_notable then
  execute immediate'create table uut(c1 number(10))';
end;

alter procedure ttt100 compile;

exec ttt110;
select * from uut;


/*create or replace procedure client_proc
is
  cursor hzh is select id,real_name,idcard_no from account;
  v_hzh hzh%rowtpye;
  
begin
  :id:=1011;
  if :id=v_hzh.id then
  dbms_output.put_line(v_hzh.real_name||' '||v_hzh.idcard_no);
  end if;
exception
 when no_data_found then
 dbms_output.put_line('account not exsists');
end;*/



