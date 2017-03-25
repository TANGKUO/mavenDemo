----索引的创建 create index....on...(列名),其是相对于表中的列来创建的  
----如:create index serviec_account_id_index on service(account_id)
----索引知道rowid（index entry）其特点:有序的
----适合建索引的列:经常出现在where子句的列  经常用于表连接的列  表中数据基数很大  表很大结果集却很小  
----主键列，唯一键列，外键列  经常需要排序和分组的列  该列包含了很多null（索引里面不计空值）
----索引类型:唯一性索引（等价于唯一约束）, 非唯一性索引（用于提高查询性能）

create index bs
ON service_xudong(unix_host,cost_id);

create index hb
on service_wz(account_id);







