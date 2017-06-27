--查询导航表信息。

select *  from mallm_navig;

--导航表结构信息。
--
truncate table mallm_navig;

--navig_id,is_home_page,navig_name,navig_url,is_show,is_new_window,local,prio_level
create table mallm_navig
(
   navig_id                   number  not null,
   is_home_page                  varchar(20)  default '',
   navig_name                varchar(150)  default '',
   navig_url                 varchar(2000)  default '',
   is_show                varchar(20)  default '',
   is_new_window              varchar(20)  default '',
   local             varchar(200) default '',
   prio_level        varchar(50)  default '',
   primary key (navig_id)
);

commit;
--针对此表结构进行操作。
INSERT INTO mallm_navig(navig_id, is_home_page, navig_name, navig_url, is_show, is_new_window, local, prio_level) 
VALUES (001,1,'test','test',9,9,9,9);

--查询导航表信息。

select *  from mallm_navig;

--
delete from mallm_navig where navig_id=#{navig_id}
--
update mallm_navig set is_home_page=#{is_home_page},navig_name=#{navig_name},navig_url=#{navig_url},
        is_show=#{is_show},is_new_window=#{is_new_window},local=#{local},prio_level=#{prio_level}   
         where navig_id=#{navig_id}
         
--
 insert into mallm_navig(is_home_page,navig_name,navig_url,is_show,is_new_window,local,prio_level)  
             values(#{is_home_page},#{navig_name},#{navig_url},#{is_show},#{is_new_window},#{local},#{prio_level}) 
             
                      





