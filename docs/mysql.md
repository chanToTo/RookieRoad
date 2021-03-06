### 一、内连接和外连接

内连接(join on)：取两张表中有交集的数据
> inner join：select * from A join B on A.xx=B.yy

外连接(left join on || right join on)：
> - left join：取左表的所有数据，加上右表中和左表有交集的数据（即满足条件的数）
>> - select city.name,country.name,city.population from city left join country on city.countrycode=country.code and city.population < 100
> - right join：取右表的所有数据，加上左表中和右表有交集的数据

**group by**

> group by配合聚合函数（max(),min(),avg(),count(),sum(),group_concat()使用，碰到group by必然会有聚合函数）
> - max()：最大值
> - min()：最小值
> - avg()：平均值
> - count()：统计个数
> - sum()：求和
> - group_concat()：列转行

### 二、索引？

参考：https://blog.csdn.net/tongdanping/article/details/79878302

1.创建索引

在创建表的时候添加索引
```shell
CREATE TABLE mytable(  
    ID INT NOT NULL,   
    username VARCHAR(16) NOT NULL,  
    INDEX [indexName] (username(length))  
);
```

在创建表以后添加索引
```shell
ALTER TABLE my_table ADD [UNIQUE] INDEX index_name(column_name);
或者
CREATE INDEX index_name ON my_table(column_name);
```

2.删除索引
```shell
DROP INDEX my_index ON tablename；
或者
ALTER TABLE table_name DROP INDEX index_name;
```

3.查看表中的索引
```shell
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

类型：
> - 单列索引
> - 联合索引：最左原则（由多列组合一个索引）
>> - 查询条件必须包含最左的索引作为条件，eg.index(a,b,c)，必须包含a列
>> - 建立联合索引时，一定要选择重复值少的列，作为最左列
> - 前缀索引：针对于所选择的列值长度过长，构建索引时会导致索引树高度增高，会导致索引应用时，需要读取更多的索引数据页，MYSQL建议索引树高度在3-4层，所以前缀索引可以选择大字段的前面部分字符作为索引生成条件

联合索引的全部覆盖（idx(a,b,c)）
> - select * from t where a= and b= and c=
> - select * from t where a in and b in and c=
> - select * from t where b= and c= and a=

联合索引的部分覆盖（idx(a,b,c)）：
> - select * from t where a= and b=
> - select * from t where a= 
> - select * from t where a= and c=
> - select * from t where a= and b > < >= <= like and c= (由于b时取范围值，所以此查询语句只能覆盖到ab，而不能覆盖到c)
> - select xxx from t where a= order by b(走ab索引，order by语句必须要按照索引的顺序，a开始)
> - select xxx from t where a= and b = order by c(走abc索引，order by语句必须要按照索引的顺序，a开始)

不覆盖联合索引（idx(a,b,c)）：


> 辅助索引构建之后，在辅助索引结构查询到具体的id，再拿该id到聚簇索引中重新查找

![image](../assets/mysql/聚簇索引1.jpg)

![image](../assets/mysql/辅助索引.jpg)

B+tree索引树高度影响因素：
> - 索引字段较长：前缀索引
> - 数据行过多：分区表、归档表、分布式架构
> - 数据类型：选择合适的数据类型

回表查询？
> mysql用来存储数据行的逻辑结构，表的数据行最终存储到了很多的page上，innodb存储引擎会按照聚簇索引，有序的组织存储表数据到各个区的连续的页上，这些连续的数据页，成为了聚簇索引的叶子节点，可认为聚簇索引就是原表数据，即 回表 即是 回聚簇索引

辅助索引：
> 将辅助索引列值 + ID主键值，构建辅助索引B树结构，当用户使用辅助索引列作为条件查询时，首先扫描辅助索引的b树
>> - 如果时辅助索引能够完全覆盖查询结果时，就不需要回表
>> - 如果不能完全覆盖到，只能通过得出的ID主键值，回到聚簇索引（回表）扫描，最终得到想要的结果

回表的影响：
> - IO量级变大
> - IOPS（IO次数）会增大
> - 随机IO会增大

如何减少回表：
> - 将查询尽可能用ID主键查询
> - 设计合理的联合索引
> - 更精确的查询条件+联合索引
> - 优化器算法：MRR

> insert、update、delete数据，对于聚簇索引会立即更新，对于辅助索引，不是实时更新的，先落入change buffer。过程如下：

![image](../assets/mysql/更新索引.jpg)

### 八、索引的管理命令

什么时候创建索引？
> 并不是将所有列都建立索引。不是索引越多越好，按照业务语句的需求创建合适的索引，将索引建立在，经常 where、group by、order by、join on...的条件

乱建索引的坏处？
> - 如果冗余索引过多，表的数据变化的时候，很有可能会导致索引频繁更新，会阻塞很多正常的业务更新的请求
> - 索引过多，会导致优化器选择出现偏差

管理命令：
> - desc city 或 show index from city; 查询表的索引情况，key列：PRI聚簇索引、MUL辅助索引、UNI唯一索引
> - alter table city add index idx_na(name); 创建索引
> - alter table city drop index idx_na; 删除索引
> - alter table city add index idx_na(name,country); 创建联合索引 
> - alter table city add index idx_na(district(5)); 创建前缀索引，取前五个字符
> -  

压测命令：
> mysqlslap --defaults-file=/etc/my.cnf \ --concurrency=100 --iterations=1 --create-schema='test' \ --query="select * from test.t100w where k2='780p'" engine=innodb \ -- number-of-queries=200 -uroot -p123 -verbose

### 九、执行计划

> 使用explain 或 desc，后面跟随具体的sql查看即可

执行计划显示结果的分析：
> - table：此次查询设计到的表
> - type：查询类型（全表扫描ALL 或 索引扫描index < range < ref < eq_ref < const(system)）
> - possible_keys：可能用到的索引
> - key：最后选择的索引
> - key_len：索引的覆盖长度（主要用于评估联合索引）
> - rows：此次查询需要扫描的行数
> - Extra：额外的信息

type索引扫描index < range < ref < eq_ref < const(system)
> - index：全索引扫描，需要扫描整颗索引树，eg. desc select countrycode from world.city
> - range：索引范围查询，> < >= <= like in or between and（like例外说明匹配最左前缀索引，不可'%ch%,可'ch%'），eg. desc select * from city where id < 10 或 desc select * from city where countrycode like 'CH%'
> - ref：辅助索引的等值查询，eg. desc select * from city where countrycode='CHN'
> - eq_ref：针对多表连接中，非驱动表连接条件是主键或唯一键，eg. desc select country.name,city.name from city join country on city.countrycode = country.code where city.population='CHN'（country为非驱动表，country.code中的code为country表中的唯一键）
> - const(或system)：聚簇索引等值查询，eg. desc select * from city where id=10 (id为主键)

key_len索引覆盖长度如下：

数字key_len：

|  | not null | 没有not null |
| ---- | ---- | ---- |
| tinyint | 1 | 1 + 1 |
| int | 4 | 4 + 1 |
| bigint | 8 | 8 + 1 |

字符key_len（utf8 -> 一个字符最大占3个字节，utf8mb4 -> 一个字符占4个字节）：

|  | not null | 没有not null |
| ---- | ---- | ---- |
| char(10) | 3 * 10 | 3 * 10 + 1 |
| varchar(10) | 3 * 10 + 2  | 3 * 10 + 2 + 1 |
 
extra：
> - using filesort：标识此次查询使用到了文件排序而没有使用索引去做排序，说明在查询中的排序操作：order by || group by || distinct..

### 十、索引应用规范

1.建索引原则
> - 必须要有主键，如果没有可以作为主键条件的列，就创建无关列
> - 经常作为where条件列，order by、group by、join on、distinct的业务列作为索引
> - 最好使用唯一值多的列作为索引，如果索引列重复值较多，可以考虑使用联合索引
> - 列值长度较长的索引列，建议使用前缀索引
> - 降低索引条目，一方面不要创建没有用的索引，不常使用的索引清理，percona toolkit工具检验无用索引
> - 索引维护要避免业务繁忙期

2.导致索引失效的原因
> - 未加索引条件，导致全表扫描，eg. select * from table
> - 查询结果集是原表中的大部分数据，占比为15%-30%，达到这个区间，优化器会觉得没有必要走索引。
>> - 优化器自行评估，与数据库的预读能力有关，以及一些参数有关
> - 索引和表有自我维护的能力，对于表内容变化比较频繁的情况，统计信息（innodb_index_stats、innodb_table_stats两张表中存储一些表或者索引的统计信息，使用optimize table city进行刷新表的统计信息）不准确，过旧，有可能会出现索引失效
>> - 一般采用删除索引并重建
> - 查询条件使用函数在索引列上，或者对索引列进行运算，运算包括（+、-、*、/、!等）
> - 隐式转换导致索引失效
> - <> 、not in不走索引（辅助索引），对于主键索引的<>和not in还是会走主键索引的
> - or、in可改成union，但不是一定，可使用不同的条件，分别测试效果
> - like "%_" 百分号在前面进行模糊查询不走索引

### 十一、优化器针对索引的算法

自优化能力：

1.mysql索引的自优化-AHI（Adaptive Hash Index 自适应hash索引，InnoDB独有）
> - mysql的InnoDB引擎，能够创建只有BTree
> - AHI作用：会自动统计索引页的使用情况，会自动评估热门的索引页生成hash表类型的索引，帮助InnoDB快速读取索引页，加快索引读取的速度，相当于索引的索引

![image](../assets/mysql/innodb%20architecture.jpg)

2.mysql索引的自优化-Change buffer -> 参考七处

优化器算法：

查看mysql优化器有哪些算法已开启：
> select @@optimizer_switch

修改算法是否开启？
> - 修改my.cnf
> - set global optimizer_switch='index_condition_pushdown=off,batched_key_access=off'; （单引号中为具体的算法名，以逗号分隔）
> - hints

1.ICP（index_condition_pushdown索引下推）

> 作用：解决了联合索引只能部分应用的情况，为了减少没必要的数据页被扫描，将不走索引的条件，在engine层取数据之前做二次过滤，一些无关数据就会被提前过滤掉，减少IO次数

![image](../assets/mysql/icp有无对比.jpg)

2.MRR（multi range read多路读）

> - 减少一部分随机IO
> - 减少回表操作

**未开启MRR之前**，辅助索引可会导致多次回表操作，以及多次IO和随机读问题

![image](../assets/mysql/no_mrr.jpg)

**开启MRR之后**，中间设置一个缓冲区，将因辅助索引获取出来的结果集（id集）放入缓冲区中根据主键进行排序，可减少回表操作及随机读

![image](../assets/mysql/mrr.jpg)

可能会使用到MRR的场景：
> - 索引类型为ref、eq_ref、range的时候
> - 使用BKA算法的时候

![image](../assets/mysql/use_mrr.jpg)

MRR总结：
> 辅助索引 -> 每次回表 -> 聚簇索引  转换成 辅助索引 -> 对id排序 -> 将排序完成id一次性取进行回表，减少回表操作 -> 聚簇索引

3.SNLJ（simple nested-loop join）

eg.
> A join B on A.xx=B.yy where ..
```shell script
for eanch row in A matching range {
  for each row in B {
    A.xx = B.yy send to client
  }
}

循环每次匹配到A的值就会拿该匹配值进B循环中做匹配
```

![image](../assets/mysql/SNLJ.jpg)

参考：https://dev.mysql.com/doc/refman/5.7/en/nested-loop-joins.html

4.BNLJ（Block Nested-Loop join）
> 在t1和t2关联条件匹配时，不再一次一次进行循环，而是采用一次性将驱动表的关联值放入join buffer中去和非驱动表匹配，减少循环的次数。5.7版本默认BNLJ算法自动开启，BNLJ主要优化了SNLJ的CPU消耗，减少了IO次数

劣势：
> 在一个数据页上可能会重复读取多次

![image](../assets/mysql/BNLJ.jpg)

5.BKA（Batched Nested Loops Join）

> 在BNLJ的基础上，依赖MRR，会将join buffer中的条件进行排序，排序好的数据就可以去顺序读取非驱动表中连续的数据页

### 十二、存储引擎

1.概念：
> 相当于MySQL内置的文件系统。与linux中的文件系统打交道的层次结构

2.MYSQL存储引擎种类：
> - InnoDB（MySQL5.5版本以后默认的存储引擎，99%以上的业务表是InnoDB）
> - MyISAM
> - CSV
> - MEMORY

3.InnoDB核心特性（即与MyISAM的区别）：

| 功能 | 支持 |
| ---- | ---- |
| MVCC多版本并发控制 | 是 |
| clustered index群集索引（聚簇索引） | 是  |
| 查询高速缓存 | 是  |
| 事务 | 是  |
| 锁定粒度 | 行  |
| 多个缓冲区池 | 是  |
| change buffer更改缓冲 | 是  |
| AHI自适应散列索引 | 是  |
| 更多的复制特性 | 是  |
| 更新数据字典 | 是  |
| 备份恢复（热备） | 是  |
| 自动故障恢复 | 是  |

4.存储引擎的管理命令
> - select @@default_storage_engine 查看默认存储引擎
> - set default_storage_engine=myisam 会话级别的修改存储引擎，不会影响其他的连接会话，重启之后失效
> - set global default_storage_engine=myisam 针对全局的修改存储引擎（打开新会话），重启之后失效，弱需要永久生效，则需要修改etc/my.cnf文件
> - show create table xx 确认每个表的引擎，或information_schema （select table_schema,table_name,engine from information_schema.tables where table_schema not in ('sys','mysql','information_schema','performance_schema')）
> - alter table t1 engine=innodb 修改一个表的搜索引擎（在业务不繁忙的情况去做）

5.MySQL存储引擎体系结构

MyISAM宏观结构：
> - myt.frm：数据字典信息（列的定义和属性）
> - myt.MYD：数据行
> - myt.MYI：索引

InnoDB宏观结构：
> - city.ibd（独立表空间）：数据行，索引
> - city.frm：数据字典信息
> - ibdata1（共享表空间）：数据字典信息，UNDO、double write磁盘区域、change buffer磁盘区域（不同版本ibdata1中存储的数据不一样，如下）
>> - 5.5版本的ibdata1中还会存储临时表数据+用户数据(数据行+索引)
>> - 5.6版本的ibdata1中会存储临时表数据
>> - 8.0版本的ibdata1取消存储数据字典信息，undo独立了
> - ib_logfile0~ib_logfileN：InnoDB事务重做日志（redo log）
> - ibtmp1：临时表空间（排序、分组、多表连接、子查询、逻辑备份等情况会使用临时表空间）
> - ib_buffer_pool：正常关库的时候，存储缓冲区的热数据。好处在于它可以顺序io加载到内存中

![image](../assets/mysql/InnoDB宏观结构.jpg)

总结：
> 针对InnoDB的表来说，仅仅是拷贝ibd和frm文件到新的数据库中，是无法正常使用的

InnoDB微观结构：

1.磁盘：

表空间：
> 表空间概念是引入Oracle数据库，起初是为了解决存储空间扩展的问题，MySQL5.5版本引入了共享表空间模式

MySQL表中间类型：
> - 共享表空间：在5.5版本引入了共享表空间（ibdata1），作为默认存储方式，用来存储：系统数据、日志、undo、临时表、用户数据和索引
> - 独立表空间：在5.6版本默认独立表空间模式，单表单表空间，eg. city.ibd/country.ibd，文件系统仍旧会打
> - 普通表空间：完全和Oracle一致的表空间管理模式
> - undo表空间：存储undo logs（回滚日志）
> - 临时表空间：存储临时表

表空间管理：
> - 用户数据默认的存储方式，都是独立表空间。独立表空间和共享表空间是可以互相切换的
> - select @@innodb_file_per_table; 查看默认表空间模式，1代表独立表空间，0代表共享表空间
> - set global innodb_file_per_table=0 切换表空间模式，需重新开启新会话，修改完成之后之影响新创建的表，重启mysql之后，此设置失效，若想永久改变，需要修改配置文件etc/my.cnf
> - select @@innodb_data_file_path 扩展共享表空间的大小和个数，方法如下：
>> - 方法1：初始化之前，需要在my.cnf加入以下配置：innodb_data_file_path=ibdata1:1G;ibdata2:1G:autoextend
>> - 方法2：在已运行的数据库上扩展多个ibdata文件

事务日志：
> - redo log重做日志：用来存储，MySQL在走insert、update、delete（DML）操作时的数据页变化过程及版本号（LSN），属于物理日志，默认两个文件存储redo，是循环覆盖使用的
> - undo logs回滚日志：用来存储回滚日志，可以理解为记录了每次操作的反操作，数据逻辑日志，提供功能：1.使用快照功能，提供InnoDB多版本并发读写、2.通过记录的反操作，提供回滚功能

redo log文件位置：
> /data/ib_logfile0~ib_logfileN

redo log控制参数
> - innodb_log_file_size=50331648 设置文件大小
> - innodb_log_files_in_group=2 设置文件个数
> - innodb_log_group_home_dir=./ 设置存储位置

undo logs（5.7默认位置）
> ibtmp1文件、ibdataN文件

undo logs控制参数
> - innodb_rollback_segments=128 设置回滚段的个数

2.内存：

数据内存区域：
> - 数据内存区域：
>> - 共享内存缓冲区域
>>> - buffer_pool(缓冲区池)
>>> - select @@InnoDB_buffer_pool_size
>>> - 功能：缓冲数据页+索引页
>> - 会话内存缓冲区域
>>> - 参数：join_buffer_size、key_buffer_size、read_buffer_size、read_rnd_buffer_size、sort_buffer_size

日志：
> - innodb_log_buffer_size=16777216 负责redo日志的缓冲

### 十三、事务

1.事务的标准特性
> - Atomicity原子性：原子是物质的最小构成单元，具备不可再分的特性，在一个事务的工作单元中，所有标准事务语句（DML），要么全成功，要么全回滚
> - Consistency一致性：事务发生前、中、后都应该保证数据是始终一致的状态，MySQL的各项功能的设计，都是最终要保证一致性
> - Isolation隔离性：MySQL可以支持多事务并发工作的系统，A事务工作的时候，不能受到其他事务写操作的影响，查询不影响
> - Durability持久性：当事务提交成功后，此次事务操作的所有数据 落盘，都要永久保存下去，不会因为数据实例发生故障，导致数据失效

2.事务生命周期管理

标准事务控制语句：
> - begin/start transaction 开启事务
> - commit 提交事务
> - rollback 回滚事务

标准的事务语句：
> - insert/update/select/delete

导致提交的非事务语句：
> - DDL语句（ALTER、CREATE和DROP）
> - DCL语句（GRANT、REMOVE和SET PASSWORD）
> - 锁定语句（LOCK TABLES 和 UNLOCK TABLES）

导致隐式提交的语句实例：
> - TRUNCATE TABLE
> - LOAD DATA INFILE
> - SELECT FOR UPDATE

隐式回滚：
> - 会话关闭
> - 数据库宕机
> - 事务语句执行失败

3.InnoDB事务的ACID如何保证

相关名词：
> - 重做日志:
>> - redo log：ib_logfile0~N 48M，轮询使用，记录的是数据页的变化
>> - redo log buffer：redo内存区域 
> - 数据页存储位置
>> - ibd：存储数据行和索引
>> - buffer pool：缓冲区池，数据页和索引页的缓冲
> - LSN日志序列号：MySQL每次数据库启动，都会比较磁盘数据页和redo log的LSN，必须要求两者LSN一致数据库才能正常启动
> - WAL：write ahead log日志优先数据页，写（异步）的方式实现持久化，先写日志，再写磁盘
> - 脏页：内存脏页，内存中发生了修改，没回写入到磁盘之前，我们把内存页称之为脏页
> - CKPT：Checkpoint，检查点，就是将脏页刷写到磁盘的动作
> - TXID：事务号，InnoDB会为每一个事务生成一个事务号，伴随着整个事务生命周期
> - UNDO：ibdata1，存储了事务工作过程中的回滚信息

相关管理命令：
> - select @@autocommit 查看事务是否开启提交
> - set global autocommit=0 临时设置不开启自动提交，重新开启会话生效
> - vim /etc/my.cnf -> autocommit=0 修改配置文件永久设置事务不开启自动提交，重启数据库生效
> - innodb_flush_log_at_trx_commit = 0/1/2 默认1
>> - 1：在每次事务提交时，会立即刷新redo到磁盘，commit才能成功
>> - 0：每秒刷新日志到os cache，再fsync异步到磁盘，异常宕机时，会有可能导致丢失1s内的事务
>> - 2：每次事务提交，都立即刷新redo buffer到os cache，再每秒fsync()磁盘，异常宕机时，会有可能导致丢失1s内的事务

undo：
> 提供快照技术，保存事务修改之前的数据状态，保证MVCC，隔离性，mysqldump的热备功能

CSR实现：
> 先redo前滚，再undo回滚（redo+undo）

一致性快照：
> 每个事务开启时（begin），都会通过undo生成一个一致性的快照

### 十四、事务的隔离级别

作用：
> 主要时提供I的特性，另外对于C的特性也有保证

相关管理命令:
> - select @@transaction_isolation 查看事务默认配置
> - set global transaction_isolation='read-uncommitted' 临时修改 重启会话生效
> - vi /etc/my.cnf transaction_isolation='read-uncommitted' 重启数据库永久生效

transaction_isolation事务隔离性（以下读不是指SQL层的数据行的select，而是指存储引擎的读，是page的读取）：

| 隔离级别 | 说明 |
| ------ | ------ |
| RU（Read Uncommitted 读未提交） | 允许另一个线程（事务）读取当前线程未提交的事务，会导致脏读、幻读和不可重复读 |
| RC（Read Committed 读已提交） | 保证另一个线程所读到的数据为当前线程已提交的，可避免脏读，但不可避免幻读和不可重复读 |
| RR（Repeatable Read 可重复读 mysql的默认级别） | 对同一字段的多次读取结果都是一致的，除非数据是被本身事务自己所修改，可以阻止脏读和不可重复读，但幻读仍有可能发生，可通过其他手段防止幻读出现，eg. GAP锁、next-lock（两种锁都要基于辅助索引） |
| SR（Serializable 可串行化） | 服从ACID的隔离级别，所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，该级别可以防止脏读、不可重复读以及幻读。但是这将严重影响程序的性能。通常情况下也不会用到该级别 |

问题说明：
> - 脏读：脏读就是指当一个事务正在访问数据，并且对数据进行了修改，而这种修改还没有提交到数据库中，这时，另外一个事务也访问这个数据，然后使用了这个数据
> - 幻读：是指当事务不是独立执行时发生的一种现象，例如第一个事务对一个表中的数据进行了修改，这种修改涉及到表中的全部数据行。同时，第二个事务也修改这个表中的数据，这种修改是向表中插入一行新数据。那么，以后就会发生操作第一个事务的用户发现表中还有没有修改的数据行，就好象发生了幻觉一样
> - 不可重复读：是指在一个事务内，多次读同一数据。在这个事务还没有结束时，另外一个事务也访问该同一数据。那么，在第一个事务中的两次读数据之间，由于第二个事务的修改，那么第一个事务两次读到的的数据可能是不一样的。这样就发生了在一个事务内两次读到的数据是不一样的，因此称为是不可重复读

### 十五、InnoDB锁

1.作用：
> 保证事务之间的隔离性，也保证了数据的一致性，保证资源不会争抢，锁事属于资源的，不是某个事务的特性。每个事务需要资源的时候，需要申请持有资源的锁

2.锁类型：
> - 内存锁：mutex、latch，保证内存数据页资源不被争抢，不被置换
> - 对象锁
>> - MDL（元数据锁）：修改元数据时，DDL操作 -> alter、备份
>> - Table_lock：表锁，DDL操作、备份（FTWRL全局表锁）、lock tables t1 read都会使用表锁
>> - record（row） lock：行锁，索引锁，锁定聚簇索引
>> - GAP：间隙锁，RR级别，铍铜辅助索引间隙锁
>> - Next-lock：下一建锁，GAP锁+record lock，普通辅助索引的范围锁

3.功能分类：
> - IS：意向共享锁，表级别
> - S：共享锁（读锁），行级别
> - IX：一项排他锁，表级别
> - X：排他锁（写锁），行级别 

| | 共享锁（S） | 排他锁（X） | 意向共享锁（IS） | 意向排他锁（IX） |
| ---- | ---- | ---- | ---- | ---- |
| 共享锁（S） | 兼容 | 冲突 | 兼容 | 冲突 |
| 排他锁（X） | 冲突 | 冲突 | 冲突 | 冲突 |
| 意向共享锁（IS） | 兼容 | 冲突 | 兼容 | 兼容 |
| 意向排他锁（IX） | 冲突 | 冲突 | 兼容 | 兼容 |

4.事务的一致性ACID的C特性：
> - A：原子性，Undo，Redo
> - D：持久性，commit的数据，通过redo（WAL）将数据异步写到磁盘
> - I：隔离性，隔离级别，锁机制，MVCC（undo）
> - C：一致性，保证工作前、中、后数据的状态都是完整的，一致的，所以C的特性是以上所有特性都是用来保证一致性的
>> - 写一致性：undo、redo、lock
>> - 读一致性：isolation level，mvcc(undo)
>> - 数据页的一致性：double write



参考资料：https://www.jianshu.com/p/7e3e2f814d22