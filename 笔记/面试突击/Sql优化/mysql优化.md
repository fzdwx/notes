

# 一、Sql执行慢的原因

## 1.原因

-   查询语句写的烂
-   索引失效
    -   单值
    -   符合
-   关联查询太多join
-   服务器调优以及各个参数设置



## 2.Sql执行顺序



>   手写

~~~sql
select distinct
	< select_list>
from
	< left_table > < join_type >
join 
	< right_table > 
on 
	< join_condition>
where
	< where_condition>
group by
	< group_by_list>
having
	< having_condition>
order by
	< order_by_condition >
limit < limit_number >
~~~



>   机读

~~~sql
from 
	< left_table >
on 
	< join_condition> < join_type > 
join 
	< right_table >
where
	< where_condition>
group by
	< group_by_list>
having
	< having_condition>
select distinct
	< select_list>
order by
	< order_by_condition >
limit < limit_number >
~~~



![image-20201018104440460](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018104447.png)



# 二、索引优化

>   优势

index是帮助mysql高效获取数据的数据结构，目的在于提高查询效率。==排好序的快速查找的数据结构==。在数据之外，==数据库系统还维护这满足特定查找算法的数据结构==，这些数据结构以某种方式引用数据，这样就可以在这些数据结构上实现高级查找算法。这种数据结构，就是索引。

![image-20201018121612266](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018121612.png)

为了加快col2的查找，可以维护一个右边所示的二叉查找树，每个节点分别包含索引键值和一个指向对应数据记录物理地址的指针，这样就可以运用二叉查找在一定的复杂度内获取到相应数据，从而快速的检索出符合条件的记录。

唯一索引默认都是使用B+树索引，统称索引，除了B+树还有哈希



>   劣势

虽然索引大大提高了查询速度，==同时会降低更新表的速度==，如对表进行insert，update，delete。应为更新表时，mysql不仅要保存数据，还要保存一下索引文件每次更新添加了索引列的字段，都会调整应为更新所带来的键值变化后的索引信息



## 1.索引分类

1.  单值索引

    一个索引只包含单个列，一个表可以有多个单列索引 

2.  唯一索引

    索引列的值必须唯一，允许有空值

3.  复合索引

    一个索引包含多个列



## 2.索引增删改查

~~~sql
# 创建索引
create [unique] index indexName on tableName(colmnName1,colmnName2...);
alter tableName add [unique] index [indexName] on (colmnName1,colmnName2...);

# 删除
drop index [indexName] on tableName;

# 查看
show index from tableName;
~~~



## 3.常用的索引添加方式

~~~sql
# 添加一个主键索引，索引值必须是唯一的，且不能为null
alter table tableName add primary key(colName); 
# 添加唯一索引，值必须是唯一的(除了null)
alter table tableNmae add unique indexName(colNmae_list);
# 添加普通索引，索引值可以出现多次
alter table tableName add index indxName(colNmae_list);
# 添加全文索引 fulltext
alter table tableName add fulltext indexName(colNmae_list);
~~~





## 4.mysql索引优化

1.  主键自动建立唯一索引
2.  ==频繁作为条件查询的字段应该建立索引==
3.  查询中与其他表关联的字段，外键关系建立索引
4.  ==频繁更新的字段不适合创建索引==
5.  where条件用不到的字段不创建索引
6.  单值/组合索引的选择问题 - 在高并发条件下倾向创建组合索引
7.  查询中排序的字段，排序字段若通过索引去访问将大大提高排序速度
8.  查询统计或者分组字段





# 三、Join语句

~~~sql
select distinct
	< select_list>
from
	< left_table > < join_type >
join < right_table > on < join_condition>
where
	< where_condition>
group by
	< group_by_list>
having
	< having_condition>
order by
	< order_by_condition >
limit < limit_number >
~~~



## 1.内连接

找到两张表中相同的部分

~~~sql
select *
from table_A A 
inner JOIN table_B B 
on A.key = B.key
~~~

![image-20201018105000434](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018105000.png)



## 2.左连接

主要是前一个表的数据和两张表公共的数据

~~~sql
select * 
from table_A A
left join table_B B
on A.key = B.key
~~~

![image-20201018105407776](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018105407.png)



## 3.右连接

主要是后一个表的数据和两张表公共的数据

~~~sql
select * 
from table_A A
right join table_B B
on A.key = B.key
~~~

![image-20201018105656189](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018105656.png)





## 4.左连接之A独有-B.key is null

~~~sql
select * 
from table_A A
left join table_B B
on A.key = B.key
where B.key is null
~~~

![](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018110012.png)

## 5.右连接之B独有-A.key is null

~~~sql
select * 
from table_A A
right join table_B B
on A.key = B.key
where A.key is null
~~~

![image-20201018110144871](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018110144.png)



## 6.全连接

~~~sql
select * 
from table_A A
full outer join table_B B 
on A.key = B.key
~~~

![image-20201018110440349](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018110440.png)



## 7.全连接之A，B独有

~~~sql
select * 
from talbe_A A
full outer join table_B B
on A.key = B.key 
where A.key is null
or B.key is null
~~~

![image-20201018110610733](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018110610.png)



## 8.建表语句

~~~sql
CREATE TABLE `tbl_dept` (
`id` INT(11) NOT NULL AUTO_INCREMENT,
`deptName` VARCHAR(30) DEFAULT NULL,
`locAdd` VARCHAR(40) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `tbl_emp` (
`id` INT(11) NOT NULL AUTO_INCREMENT,
`name` VARCHAR(20) DEFAULT NULL,
`deptId` INT(11) DEFAULT NULL, 
PRIMARY KEY (`id`),
KEY `fk_dept_id` (`deptId`)
#CONSTRAINT `fk_dept_jid` FOREIGN KEY (`deptId`) REFERENCES `tbl_dept` (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO tbl_dept(deptName,locAdd) VALUES('RD',11);
INSERT INTO tbl_dept(deptName,locAdd) VALUES('HR',12);
INSERT INTO tbl_dept(deptName,locAdd) VALUES('MK',13);
INSERT INTO tbl_dept(deptName,locAdd) VALUES('MIS',14);
INSERT INTO tbl_dept(deptName,locAdd) VALUES('FD',15);


INSERT INTO tbl_emp(NAME ,deptId) VALUES('z3',1);
INSERT INTO tbl_emp(NAME,deptId) VALUES('z4',1);
INSERT INTO tbl_emp(NAME ,deptId) VALUES('z5',1);
INSERT INTO tbl_emp(NAME ,deptId) VALUES('w5',2);
INSERT INTO tbl_emp(NAME,deptId) VALUES('w6',2);
INSERT INTO tbl_emp(NAME,deptId) VALUES('s7',3);
INSERT INTO tbl_emp(NAME,deptId) VALUES('s8',4);
INSERT INTO tbl_emp(NAME,deptId) VALUES('s9',51 );

~~~



## 测试

![image-20201018111939671](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018111939.png)

### 	a.内连接

​	两张表共有的部分

![image-20201018112308714](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018112308.png)



### b.左连接

以A表为主，B表中没有的补null

![image-20201018112411799](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018112411.png)



### c.右连接

以B表为主，A表中没有的补null

![image-20201018112503544](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018112503.png)

### d.左连接之A独有-B.key is null

![image-20201018112800741](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018112800.png)



### e.右连接之B独有-A.key is null

![image-20201018113025891](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018113025.png)



### f.全连接

使用union把左连接和右连接的结果结合

![image-20201018113638336](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018113638.png)





### g.全连接之A独有+ B独有

![image-20201018114027430](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018114027.png)





# 四、explain

使用==explain==关键字可以模拟优化器执行sql查询语句，从而知道mysql是如果处理你的sql语句的，分析你的查询语句或是表结构的性能瓶颈

作用：

-   表的读取顺序
-   数据读取操作的操作类型
-   哪些索引可以使用
-   哪些索引被实际使用
-   表之间的引用
-   每张表有多少行被优化器查询

![image-20201018131031580](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018131031.png)



## 1.id

-   select 查询的序列号，包含一组数字，表示查询中select子句或操作表的顺序
-   id相同，执行顺序由上至下
-   id不同，越大越优先

## 2.select_type

![image-20201018132710648](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018132710.png)

## 3.table

使用的表

## 4.type

![image-20201018133110267](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018133110.png)

访问类型排序，显示查询使用了何种类型

从好到差依次是：

==system > const >eq_ref > ref > range > index >All==

-   system:表只有一行数据(等于系统表)，这是const类型的特例，平时不会出现，这个也可以忽略不计
-   const:如果通过索引一次就找到了，const用于比较primary key 或者unique索引。因为值匹配一行数据，所以很快。如果将主键置于where语句中，mysql就能将该表查询转换为一个常量
-   eq_ref：唯一性索引扫描，对于每个索引键，表中只有一条记录与之匹配。常见于主键或为一索引扫描 
-   ref:非唯一性索引扫描，返回匹配某个单独值的所有行，本质上也是一种索引访问，他返回所有匹配某个单独值的行，然而他可能会赵铎个符合条件的行，所以他通过属于查找和扫描的混合体
-   range：只检索给定范围的行，使用一个索引来选择行。key列显示使用了哪个索引，一般就是在你的where语句中出现了between，< ,>,in等的查询
-   index:full index scan，index和all区别为index类型值遍历索引树，这通常比all快，因为索引文件通常比数据文件小。也就是说虽然all和index都是读全表，但是index是从索引中读取的，而all是从硬盘中读的。

## 5.possible_keys

显示可能应用在这张表中的索引，一个或多个。查询涉及到的字段上若存在索引，则该索引将被列出。==但不一定被查询使用==

## 6.key

实际使用的索引，如果为null，则没有使用索引，==查询中若使用了覆盖索引，则该索引仅出现在key列表中==

## 7.key_len

表示索引使用的字节数

![image-20201018140628190](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018140628.png)

## 8.ref

显示索引的哪一列被引用了，如果可能的话，是一个常数。哪些列或常量被用于查找索引列上的值

## 9.rows

根据表统计信及索引选用情况，大致估算出找到所需要的记录所需要读取的行数

## 10.extra

包含不适合在其他列中显示但是十分重要的额外信息

-   using filesort    ==危险==

    说明mysql会对数据使用一个外部的索引排序，而不是按照表内的索引顺序进行读取。mysql中无法利用索引完成的排序操作称为“文件排序”

-   using temporary  ==十死无生==

    使用临时表保存中间结果，mysql在对查询结果排序时使用临时表，常见于排序order by 和分组group by

-   using index

    表示select操作中使用了覆盖索引，避免访问了表的数据行，效率不错 

    ==覆盖索引==：就是select的数据列只用从索引中就能取得，不必读取数据行，mysql可以利用索引返回select列表中的字段，而不必根据索引在次读取数据文件，换句话说==查询列要被所见的索引覆盖==







# 五、优化案例：

## 1、两表

~~~sql
CREATE TABLE IF NOT EXISTS `class`(
`id` INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
`card` INT (10) UNSIGNED NOT NULL
);
 
CREATE TABLE IF NOT EXISTS `book`(
`bookid` INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
`card` INT (10) UNSIGNED NOT NULL
);
~~~

explain select * from class left join book on class.card = book.card;

左连接的一个查询，发现type都是all，

![image-20201018162727124](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018162727.png)



>   优化



左连接，为右表的card字段添加索引建了索引type就变成了ref

~~~sql
alter table book add index y(card);
~~~

![image-20201018163023165](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018163023.png)



左连接，为左表的card字段添加索引

~~~sql
alter table class add index y (card);
~~~

![image-20201018163458944](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018163459.png)



>   结论：

左连接为右表添加索引，反着加





## 2、三表

~~~sql
 CREATE TABLE IF NOT EXISTS `phone`(
`phoneid` INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
`card` INT (10) UNSIGNED NOT NULL
)ENGINE = INNODB;
~~~

~~~sql
# 左连接，给右边的添加索引
EXPLAIN SELECT * from class left JOIN book on class.card = book.card LEFT JOIN phone ON book.card = phone.card;
CREATE INDEX y  ON book(card);
CREATE INDEX y  ON phone(card);
CREATE INDEX y on  class(card);
~~~

![image-20201018171224038](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018171224.png)



尽可能减少join语句中的nestedloop的循环总次数：==永远用小结果集驱动大结果集==







# 六、索引优化

~~~
全值匹配我最爱，最左前缀要遵守；
带头大哥不能死，中间兄弟不能断；
索引列上少计算，范围之后全失效；
Like百分写最右，覆盖索引不写星；
不等空值还有or，索引失效要少用；
VAR引号不可丢，SQL高级也不难！
~~~

1.  全值匹配我最爱

2.  ==最佳左前缀法则==

    查询从索引的最前列开始并且==不跳过索引中的列==

    ~~~sql
    ALTER TABLE staffs ADD INDEX index_staffs_nameAgePos(`name`,`age`,`pos`)
    ~~~

    ![image-20201018173013170](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018173013.png)

    ![image-20201018173002675](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018173002.png)

3.  不要在索引列上做任何操作(计算，函数，类型转换)，会导致索引失效而转向全表扫描

4.  存储引擎不能使用索引中范围条件右边的列

5.  尽量使用覆盖索引(只访问索引的查询)，减少select *

6.  mysql在使用！ =  <  > 的时候会导致无法使用索引而导致全表扫描

7.  is null，is not null也无法使用索引

8.  like 以通配符开头(%abc)也会导致索引失效

    ~~~sql
    create index indexName on tableName(需要查询的列，查多少写多少 from)
    select 需要查询的列，查多少写多少 from tableName where name like ‘%xxx%’
    
    ~~~

    

9.  字符串不加单引号会导致索引失效

10.  少用or，用它连接会导致索引失效

11.  

~~~sql
CREATE TABLE staffs(
id INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(24)NOT NULL DEFAULT'' COMMENT'姓名',
`age` INT NOT NULL DEFAULT 0 COMMENT'年龄',
`pos` VARCHAR(20) NOT NULL DEFAULT'' COMMENT'职位',
`add_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'入职时间'
)CHARSET utf8 COMMENT'员工记录表';
INSERT INTO staffs(`name`,`age`,`pos`,`add_time`) VALUES('z3',22,'manager',NOW());
INSERT INTO staffs(`name`,`age`,`pos`,`add_time`) VALUES('July',23,'dev',NOW());
INSERT INTO staffs(`name`,`age`,`pos`,`add_time`) VALUES('2000',23,'dev',NOW());

ALTER TABLE staffs ADD INDEX index_staffs_nameAgePos(`name`,`age`,`pos`)
~~~





# 七、排序优化



## 1.order by 子句

-   尽量使用index方式排序，避免使用fileSort方式排序

-   尽可能在索引列上完成排序操作，遵照索引建的最佳左前缀

-   如果不在索引列上，fileSort有两种算法：

    -   双路排序

        扫描==两次==磁盘，最终得到数据，读取行指针和order by 列，对他们进行排序，然后扫描已经排好序的列表，  按照列表中的值重新从列表中读取对应的数据输出

    -   单路排序

        mysql 4.1以后



![image-20201018222634988](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018222635.png)

![image-20201018223313617](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018223313.png)





## 2.group by

![image-20201018223414434](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201018223414.png)