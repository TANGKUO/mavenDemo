select 'hello world' from dual;
----PLSQL程序结构块
/*DECLARE.....(声明块);
  BEGIN.......(执行块);
  EXCEPTION...(异常块);
  END;*/
----DDL不能在pl/sql中直接使用（写）而是通过 execute immediate＋'ddl' 本地动态sql来实现 即ddl一字符串的形式实现
----DML/TCL可以直接使用（记住提交操作）静态sql
----调用函数(函数有返回值)
----调用过程（过程本身没有返回值)
/*for i in 1..n loop
  ....;
  end loop;
  commit;
*/

/*while...loop
 ....
 exit when ...
 end loop;
 commit;
 */


begin
    execute immediate 'create table wz_13(c1 number(35))';
end;
 
begin
    for i in 1..1000000 loop
    insert into wz_13 values(i);
    end loop;
    commit;
end;
----------------------------------------------------
declare c1_num number:=1;
begin
    while c1_num<1000 loop
    insert into wz_12 values (c1_num);
    c1_num:=c1_num+1;
    exit when c1_num>1000;
end loop;
commit;
end;

select * from wz_12;
--------------------------------------------------
declare
    v_id number(10):=1011;
    v_realname account.real_name%type;
    v_idcard_no account.idcard_no%type;
begin
    select real_name,idcard_no 
    into v_realname,v_idcard_no 
    from account 
    where v_id=id;
    SYS.dbms_output.put_line(v_realname||','||v_idcard_no);
end;

-------------------------------------------------------
declare
    v_account account%rowtype;
begin
    select id,real_name,idcard_no 
    into v_account.id,v_account.real_name,v_account.idcard_no 
    from account
    where id=1000;
    dbms_output.put_line(v_account.id||' '||v_account.real_name||' '||v_account.idcard_no);
    commit;
exception
    when no_data_found then dbms_output.put_line('the account id is not exists');
end;

----------------------------------------------------------------
declare
    cursor c_account is select * from account;
    v_account c_account%rowtype;
begin
    open c_account;
    loop
    fetch c_account into v_account;
    exit when c_account%notfound;
    sys.dbms_output.put_line(v_account.real_name||' '||v_account.idcard_no);
    end loop;
    close c_account;
end;
-------------------------------------------------------------------
declare
    cursor c_account is select * from account where 1=2;
    v_account c_account%rowtype;
begin
    open c_account;
    fetch c_account into v_account;
    sys.dbms_output.put_line(v_account.real_name||' '||v_account.idcard_no);
    while c_account%found loop
    sys.dbms_output.put_line(v_account.real_name||' '||v_account.idcard_no);
    fetch c_account into v_account;
    end loop;
    close c_account;
end;

----------------------------------------------------------------------
declare
    cursor c_account is select * from account;
begin
    for i in c_account loop
    dbms_output.put_line(i.id||' '||i.real_name||' '||i.idcard_no);
    end loop;
end;
------------------------------------------------------------------------
declare 
      cursor c_account is select * from account where 1=2;
      v_account c_account%rowtype;
begin
    open c_account;
    fetch c_account into v_account;
    if c_account%found then
    close c_account;
      for i in c_account loop
      dbms_output.put_line(i.real_name||' '||i.idcard_no);
      end loop;
    else 
        dbms_output.put_line('the account not exists');
    end if;
end;
------------------------------------------------------- 
set serveroutput on;
declare
      cursor c_account is select * from account where 1=1;
      v_account c_account%rowtype;
      v_cc binary_integer:=0;
begin
      open c_account;
    loop
      fetch c_account into v_account;
      exit when c_account%notfound;
      dbms_output.put_line(v_account.real_name||' '||v_account.idcard_no);
      v_cc:=1;
    end loop;
    close c_account;
    if v_cc=0 then 
    dbms_output.put_line('the account not exists');
    end if;
end;
----------------------------------------------------------
declare
      cursor c_account is select * from account group by id;
begin 
      for i in c_account loop
      dbms_output.put_line(i.real_name||' '
      ||round(to_char(sysdate-i.birthdate)/365)||' '
      ||i.con);
      end loop;
end;
----------------------------------------------------------
declare
  c1 number:=0;
  c2 number:=1;
  c3 binary_integer;
begin
  c3:=c2/c1;
exception  
  when zero_divide then
  dbms_output.put_line('你大');
end;

      
      
    
