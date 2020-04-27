### 一、内连接和外连接

内连接(join on)：取两张表中有交集的数据
> inner join：select * from A join B on A.xx=B.yy

外连接(left join on || right join on)：
> - left join：取左表的所有数据，加上右表中和左表有交集的数据（即满足条件的数）
>> - select city.name,country.name,city.population from city left join country on city.countrycode=country.code and city.population < 100
> - right join：取右表的所有数据，加上左表中和右表有交集的数据

### 二、索引？

参考：https://blog.csdn.net/tongdanping/article/details/79878302

1.创建索引

在创建表的时候添加索引
```xml
CREATE TABLE mytable(  
    ID INT NOT NULL,   
    username VARCHAR(16) NOT NULL,  
    INDEX [indexName] (username(length))  
);
```

在创建表以后添加索引
```xml
ALTER TABLE my_table ADD [UNIQUE] INDEX index_name(column_name);
或者
CREATE INDEX index_name ON my_table(column_name);
```

2.删除索引
```xml
DROP INDEX my_index ON tablename；
或者
ALTER TABLE table_name DROP INDEX index_name;
```

3.查看表中的索引
```xml
SHOW INDEX FROM tablename
```

### 三、索引的优缺点？
> - 优势：可以快速检索，减少I/O次数，加快检索速度；根据索引分组和排序，可以加快分组和排序
> - 劣势：索引本身也是表，因此会占用存储空间，一般来说，索引表占用的空间的数据表的1.5倍；索引表的维护和创建需要时间成本，这个成本随着数据量增大而增大；构建索引会降低数据表的修改操作（删除，添加，修改）的效率，因为在修改数据表的同时还需要修改索引表

### 四、索引分类？
**常见的索引类型有：主键索引、唯一索引、普通索引、全文索引、组合索引**

1.主键索引：即主索引，根据主键pk_clolum（length）建立索引，不允许重复，不允许空值
> ALTER TABLE 'table_name' ADD PRIMARY KEY pk_index('col')

2.唯一索引：用来建立索引的列的值必须是唯一的，允许空值
> ALTER TABLE 'table_name' ADD UNIQUE index_name('col')

3.普通索引：用表中的普通列构建的索引，没有任何限制
> ALTER TABLE 'table_name' ADD INDEX index_name('col')

4.全文索引：用大文本对象的列构建的索引
> ALTER TABLE 'table_name' ADD FULLTEXT INDEX ft_index('col')

5.组合索引：用多个列组合构建的索引，这多个列中的值不允许有空值
> ALTER TABLE 'table_name' ADD INDEX index_name('col1','col2','col3')

**遵循“最左前缀”原则，把最常用作为检索或排序的列放在最左，依次递减，组合索引相当于建立了col1,col1col2,col1col2col3三个索引，而col2或者col3是不能使用索引的**

**在使用组合索引的时候可能因为列名长度过长而导致索引的key太大，导致效率降低，在允许的情况下，可以只取col1和col2的前几个字符作为索引**

使用col1的前4个字符和col2的前3个字符作为索引
> ALTER TABLE 'table_name' ADD INDEX index_name(col1(4),col2（3))

1.什么时候要使用索引？
> - 主键自动建立唯一索引
> - 经常作为查询条件在WHERE或者ORDER BY 语句中出现的列要建立索引
> - 作为排序的列要建立索引
> - 查询中与其他表关联的字段，外键关系建立索引
> - 高并发条件下倾向组合索引
> - 用于聚合函数的列可以建立索引，例如使用了max(column_1)或者count(column_1)时的column_1就需要建立索引

2.什么时候不要使用索引？
> - 经常增删改的列不要建立索引
> - 有大量重复的列不建立索引
> - 表记录太少不要建立索引。只有当数据库里已经有了足够多的测试数据时，它的性能测试结果才有实际参考价值。如果在测试数据库里只有几百条数据记录，它们往往在执行完第一条查询命令之后就被全部加载到内存里，这将使后续的查询命令都执行得非常快--不管有没有使用索引。只有当数据库里的记录超过了1000条、数据总量也超过了MySQL服务器上的内存总量时，数据库的性能测试结果才有意义

3.索引失效的情况
> - 在组合索引中不能有列的值为NULL，如果有，那么这一列对组合索引就是无效的
> - 在一个SELECT语句中，索引只能使用一次，如果在WHERE中使用了，那么在ORDER BY中就不要用了
> - LIKE操作中，'%aaa%'不会使用索引，也就是索引会失效，但是‘aaa%’可以使用索引
> - 在索引的列上使用表达式或者函数会使索引失效，例如：select * from users where YEAR(adddate)<2007，将在每个行上进行运算，这将导致索引失效而进行全表扫描，因此我们可以改成：select * from users where adddate<’2007-01-01′。其它通配符同样，也就是说，在查询条件中使用正则表达式时，只有在搜索模板的第一个字符不是通配符的情况下才能使用索引
> - 在查询条件中使用不等于，包括<符号、>符号和！=会导致索引失效。特别的是如果对主键索引使用！=则不会使索引失效，如果对主键索引或者整数类型的索引使用<符号或者>符号不会使索引失效。（不等于，包括&lt;符号、>符号和！，如果占总记录的比例很小的话，也不会失效）
> - 在查询条件中使用IS NULL或者IS NOT NULL会导致索引失效
> - 字符串不加单引号会导致索引失效。更准确的说是类型不一致会导致失效，比如字段email是字符串类型的，使用WHERE email=99999 则会导致失败，应该改为WHERE email='99999'
> - 在查询条件中使用OR连接多个条件会导致索引失效，除非OR链接的每个条件都加上索引，这时应该改为两次查询，然后用UNION ALL连接起来
> - 如果排序的字段使用了索引，那么select的字段也要是索引字段，否则索引失效。特别的是如果排序的是主键索引则select * 也不会导致索引失效
> - 尽量不要包括多列排序，如果一定要，最好为这队列构建组合索引

### 五、BTree种类

> - B-Tree
> - B+Tree(在叶子节点处加入指向前后节点的指针，减少IO) ---> B*Tree(在支子节点处加入指向前后节点的指针，减少IO)

![image](../assets/mysql/简易BTree.jpg)

### 六、聚簇索引（InnoDB独有）

> 聚簇索引的叶子节点存储索引及具体的数据

构建前提：
> - 建表时，指定了主键列，MySQL InnoDB会讲主键作为聚簇索引列，比如ID not null primary key
> - 没有指定主键，自动选择唯一键（unique）的列，作为聚簇索引
> - 以上都没有，会生成隐藏聚簇索引

作用：
> 有了聚簇索引之后，将来插入的数据行，在同一个区内，都会按照ID值得顺序，有序在磁盘存储数据

聚簇索引结构

![image](../assets/mysql/聚簇索引.jpg)

### 七、辅助索引

> 使用普通列作为条件构建的索引（辅助聚簇索引）

作用：
> 优化非聚簇索引列之外的查询条件的优化

> 辅助索引构建之后，在辅助索引结构查询到具体的id，再拿该id到聚簇索引中重新查找

![image](../assets/mysql/聚簇索引1.jpg)

![image](../assets/mysql/辅助索引.jpg)

