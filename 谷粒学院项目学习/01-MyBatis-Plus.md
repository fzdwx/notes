1

# 一、简介

> 润物无声

只做增强不做改变，引入它不会对现有工程产生影响，如丝般顺滑。

> 效率至上

只需简单配置，即可快速进行单表 CRUD 操作，从而节省大量时间。

> 丰富功能

代码生成、物理分页、性能分析等功能一应俱全。

> 依赖

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus</artifactId>
    <version>latest-version</version>
</dependency>
```

## 特性

- **无侵入**：只做增强不做改变，引入它不会对现有工程产生影响，如丝般顺滑
- **损耗小**：启动即会自动注入基本 CURD，性能基本无损耗，直接面向对象操作
- **强大的 CRUD 操作**：内置通用 Mapper、通用 Service，仅仅通过少量配置即可实现单表大部分 CRUD 操作，更有强大的条件构造器，满足各类使用需求
- **支持 Lambda 形式调用**：通过 Lambda 表达式，方便的编写各类查询条件，无需再担心字段写错
- **支持主键自动生成**：支持多达 4 种主键策略（内含分布式唯一 ID 生成器 - Sequence），可自由配置，完美解决主键问题
- **支持 ActiveRecord 模式**：支持 ActiveRecord 形式调用，实体类只需继承 Model 类即可进行强大的 CRUD 操作
- **支持自定义全局通用操作**：支持全局通用方法注入（ Write once, use anywhere ）
- **内置代码生成器**：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 、 Service 、 Controller 层代码，支持模板引擎，更有超多自定义配置等您来使用
- **内置分页插件**：基于 MyBatis 物理分页，开发者无需关心具体操作，配置好插件之后，写分页等同于普通 List 查询
- **分页插件支持多种数据库**：支持 MySQL、MariaDB、Oracle、DB2、H2、HSQL、SQLite、Postgre、SQLServer 等多种数据库
- **内置性能分析插件**：可输出 Sql 语句以及其执行时间，建议开发测试时启用该功能，能快速揪出慢查询
- **内置全局拦截插件**：提供全表 delete 、 update 操作智能分析阻断，也可自定义拦截规则，预防误操作

# 二、创建运行环境

## table

```sql
CREATE TABLE user
(
	id BIGINT(20) NOT NULL COMMENT '主键ID',
	name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
	age INT(11) NULL DEFAULT NULL COMMENT '年龄',
	email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
	PRIMARY KEY (id)
);
```

## data

```sql
INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');
```

## pom.xml

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency><!--lombok-->
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency><!--mybatis-plus-->
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
        <version>3.3.2</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.21</version>
    </dependency>
</dependencies>
```

## entity

```java
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
```

## dao

```java
@Repository
public interface UserDao extends BaseMapper<User> {
}
```

## Application

```java
@SpringBootApplication
@MapperScan
public class LkApplication {
   public static void main(String[] args) {
      SpringApplication.run(SpringBootApplication.class, args);
   }
}
```

## yml

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/academy?serverTimezone=UTC
    username: root
    password: root

mybatis-plus:
  configuration:
    #日志
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

## test

```java
@SpringBootTest
class SpringbootApplicationTests {

    @Autowired
    private UserDao userDao;
    @Test
    void contextLoads() {
        List<User> users = userDao.selectList(null);
        users.forEach(System.out::println);
    }
}
```

# 三、CRUD

```java
@Test
void insert() {
    User user = new User((long) 6, "like", 18, "980650920@qq.com");
    int insert = userDao.insert(user);
    System.out.println(insert);
}

@Test
void del() {
    int i = userDao.deleteById(6);
    System.out.println(i);
}

@Test
void update() {
    User user = userDao.selectById(1296282758598995969l);
    user.setAge(20);
    user.setName("here is like");
    userDao.updateById(user);
}
```

# 四、数据库策略

==按照业务模块将数据库分散到不同的数据库服务器==，比如一个电商网站，包括用户，商品，订单三个业务模块，可以分为用户，商品，订单三个数据库，形成一个数据库集群

**缺点：**

1.  无法使用 join 查询
2.  无法通过事务统一修改
3.  成本增加，如果需要备份，成本翻倍

## 主从复制，读写分离

![image-20200820101600690](https://gitee.com/likeloveC/picture_bed/raw/master/img/20200820101607.png)

## 数据库分表

将不同业务数据存储到不同的数据库服务器，能够支持百万甚至千万的用户规模，但要继续发展，==同一业务的单表数据也会达到单台数据库服务器的处理瓶颈==

单表数据拆分有两种方式：**垂直分表**和**水平分表**

![image-20200820102050714](https://gitee.com/likeloveC/picture_bed/raw/master/img/20200820102050.png)

### 水平分表

![image-20200820102638998](https://gitee.com/likeloveC/picture_bed/raw/master/img/20200820102639.png)

![image-20200820102714658](https://gitee.com/likeloveC/picture_bed/raw/master/img/20200820102714.png)

### 雪花算法

![image-20200820103359926](https://gitee.com/likeloveC/picture_bed/raw/master/img/20200820103400.png)

## mp 的主键策略

默认是雪花算法

```java
@TableId(type = IdType.ASSIGN_ID)
private Long id;
```

可以修改为自增

```java
@TableId(type = IdType.AUTO)
private Long id;
```

可以通过配置文件设置全局主键策略

```
mybatis-plus:
	global-config:
    	db-config:
      		id-type:
```

![image-20200820104442231](https://gitee.com/likeloveC/picture_bed/raw/master/img/20200820104442.png)

# 五、自动填充

**1.添加数据库字段**

根据阿里巴巴代码规范，在 user 表中添加 ==create_time==,==update_time==两个字段，类型是 datetaime

**2.实体类修改**

```java
//插入时填充字段
@TableField(fill = FieldFill.INSERT)
private Date createTime;
//插入和更新时填充字段
@TableField(fill = FieldFill.INSERT_UPDATE)
private Date updateTime;
```

**3.实现元对象处理器接口**

```java
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime",new Date(),metaObject);
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
```

# 六、乐观锁

**意图：**

当要更新一条记录的时候，希望这条记录没有被别人更新

乐观锁实现方式：

- 取出记录时，获取当前 version
- 更新时，带上这个 version
- 执行更新时， set version = newVersion where version = oldVersion
- 如果 version 不对，就更新失败

**table**

```sql
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `name` varchar(30) DEFAULT NULL COMMENT '商品名称',
  `price` int(11) DEFAULT '0' COMMENT '价格',
  `version` int(11) DEFAULT '0' COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## 1.插件配置

```java
@Configuration                  //配置类
@EnableTransactionManagement  //事务处理
public class MpConfig {

    /**
     * 乐观锁插件  OptimisticLockerInterceptor
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
```

## 2.注解实体字段 `@Version` 必须要!

```java
@Data
public class Product {
   private long id;
   private String name;
   private Integer price;
   @Version
   private Integer version;
}
```

# 七、分页

## 1.插件配置

```java
@Bean
public PaginationInterceptor paginationInterceptor() {
    PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
    // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
    // paginationInterceptor.setOverflow(false);
    // 设置最大单页限制数量，默认 500 条，-1 不受限制
    // paginationInterceptor.setLimit(500);
    // 开启 count 的 join 优化,只针对部分 left join
    paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
    return paginationInterceptor;
}
```

## 2.测试

```java
@Test
void selectForPage() {
    Page<User> userPage = new Page<>(1,5);
    Page<User> pageParam = userDao.selectPage(userPage, null);
    List<User> records = pageParam.getRecords();
    records.forEach(System.out::println);
    System.out.println(pageParam.getPages());
    System.out.println(pageParam.getTotal());
    System.out.println(pageParam.getCurrent());
    System.out.println(pageParam.getSize());
    System.out.println(pageParam.hasNext());
    System.out.println(pageParam.hasPrevious());
}
```

### 3.通过 map 指定需要的字段

```java
 @Test
    void selectForPage() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name");

        Page<Map<String,Object>> userPage = new Page<>(1, 5);
        Page<Map<String, Object>> pageParam = userDao.selectMapsPage(userPage, queryWrapper);

        List<Map<String, Object>> records = pageParam.getRecords();
        records.forEach(System.out::println);

    }
```

# 八、逻辑删除

假删除

## 1.在表中加入字段

![image-20200820121722093](https://gitee.com/likeloveC/picture_bed/raw/master/img/20200820121722.png)

## 2.实体类中加入注解

```java
@Data
public class User {
	···
    //逻辑删除
    @TableLogic
    private Integer deleted;
}
```

## 3.删除结果

![image-20200820121916558](https://gitee.com/likeloveC/picture_bed/raw/master/img/20200820121916.png)

# 九、条件构造器-[mapper](https://baomidou.com/guide/wrapper.html#abstractwrapper)

![image-20200820122114312](https://gitee.com/likeloveC/picture_bed/raw/master/img/20200820122114.png)

## [#](https://baomidou.com/guide/wrapper.html#条件构造器) 条件构造器

说明:

- 以下出现的第一个入参`boolean condition`表示该条件**是否**加入最后生成的 sql 中
- 以下代码块内的多个方法均为从上往下补全个别`boolean`类型的入参,默认为`true`
- 以下出现的泛型`Param`均为`Wrapper`的子类实例(均具有`AbstractWrapper`的所有方法)
- 以下方法在入参中出现的`R`为泛型,在普通 wrapper 中是`String`,在 LambdaWrapper 中是**函数**(例:`Entity::getId`,`Entity`为实体类,`getId`为字段`id`的**getMethod**)
- 以下方法入参中的`R column`均表示数据库字段,当`R`具体类型为`String`时则为数据库字段名(**字段名是数据库关键字的自己用转义符包裹!**)!而不是实体类数据字段名!!!,另当`R`具体类型为`SFunction`时项目 runtime 不支持 eclipse 自家的编译器!!!
- 以下举例均为使用普通 wrapper,入参为`Map`和`List`的均以`json`形式表现!
- 使用中如果入参的`Map`或者`List`为**空**,则不会加入最后生成的 sql 中!!!
- 有任何疑问就点开源码看,看不懂**函数**的[点击我学习新知识](https://www.jianshu.com/p/613a6118e2e0)

[ ](https://www.jianshu.com/p/613a6118e2e0)

警告:

不支持以及不赞成在 RPC 调用中把 Wrapper 进行传输

1.  wrapper 很重
2.  传输 wrapper 可以类比为你的 controller 用 map 接收值(开发一时爽,维护火葬场)
3.  正确的 RPC 调用姿势是写一个 DTO 进行传输,被调用方再根据 DTO 执行相应的操作
4.  我们拒绝接受任何关于 RPC 传输 Wrapper 报错相关的 issue 甚至 pr

## [#](https://baomidou.com/guide/wrapper.html#abstractwrapper) AbstractWrapper

说明:

QueryWrapper(LambdaQueryWrapper) 和 UpdateWrapper(LambdaUpdateWrapper) 的父类
用于生成 sql 的 where 条件, entity 属性也用于生成 sql 的 where 条件
注意: entity 生成的 where 条件与 使用各个 api 生成的 where 条件**没有任何关联行为**

### [#](https://baomidou.com/guide/wrapper.html#alleq) allEq

```java
allEq(Map<R, V> params)
allEq(Map<R, V> params, boolean null2IsNull)
allEq(boolean condition, Map<R, V> params, boolean null2IsNull)
```

- 全部[eq](https://baomidou.com/guide/wrapper.html#eq)(或个别[isNull](https://baomidou.com/guide/wrapper.html#isnull))

个别参数说明:

`params` : `key`为数据库字段名,`value`为字段值
`null2IsNull` : 为`true`则在`map`的`value`为`null`时调用 [isNull](https://baomidou.com/guide/wrapper.html#isnull) 方法,为`false`时则忽略`value`为`null`的

- 例 1: `allEq({id:1,name:"老王",age:null})`--->`id = 1 and name = '老王' and age is null`
- 例 2: `allEq({id:1,name:"老王",age:null}, false)`--->`id = 1 and name = '老王'`

```java
allEq(BiPredicate<R, V> filter, Map<R, V> params)
allEq(BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull)
allEq(boolean condition, BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull)
```

个别参数说明:

`filter` : 过滤函数,是否允许字段传入比对条件中
`params` 与 `null2IsNull` : 同上

- 例 1: `allEq((k,v) -> k.indexOf("a") >= 0, {id:1,name:"老王",age:null})`--->`name = '老王' and age is null`
- 例 2: `allEq((k,v) -> k.indexOf("a") >= 0, {id:1,name:"老王",age:null}, false)`--->`name = '老王'`

### [#](https://baomidou.com/guide/wrapper.html#eq) eq

```java
eq(R column, Object val)
eq(boolean condition, R column, Object val)
```

- 等于 =
- 例: `eq("name", "老王")`--->`name = '老王'`

### [#](https://baomidou.com/guide/wrapper.html#ne) ne

```java
ne(R column, Object val)
ne(boolean condition, R column, Object val)
```

- 不等于 <>
- 例: `ne("name", "老王")`--->`name <> '老王'`

### [#](https://baomidou.com/guide/wrapper.html#gt) gt

```java
gt(R column, Object val)
gt(boolean condition, R column, Object val)
```

- 大于 >
- 例: `gt("age", 18)`--->`age > 18`

### [#](https://baomidou.com/guide/wrapper.html#ge) ge

```java
ge(R column, Object val)
ge(boolean condition, R column, Object val)
```

- 大于等于 >=
- 例: `ge("age", 18)`--->`age >= 18`

### [#](https://baomidou.com/guide/wrapper.html#lt) lt

```java
lt(R column, Object val)
lt(boolean condition, R column, Object val)
```

- 小于 <
- 例: `lt("age", 18)`--->`age < 18`

### [#](https://baomidou.com/guide/wrapper.html#le) le

```java
le(R column, Object val)
le(boolean condition, R column, Object val)
```

- 小于等于 <=
- 例: `le("age", 18)`--->`age <= 18`

### [#](https://baomidou.com/guide/wrapper.html#between) between

```java
between(R column, Object val1, Object val2)
between(boolean condition, R column, Object val1, Object val2)
```

- BETWEEN 值 1 AND 值 2
- 例: `between("age", 18, 30)`--->`age between 18 and 30`

### [#](https://baomidou.com/guide/wrapper.html#notbetween) notBetween

```java
notBetween(R column, Object val1, Object val2)
notBetween(boolean condition, R column, Object val1, Object val2)
```

- NOT BETWEEN 值 1 AND 值 2
- 例: `notBetween("age", 18, 30)`--->`age not between 18 and 30`

### [#](https://baomidou.com/guide/wrapper.html#like) like

```java
like(R column, Object val)
like(boolean condition, R column, Object val)
```

- LIKE '%值%'
- 例: `like("name", "王")`--->`name like '%王%'`

### [#](https://baomidou.com/guide/wrapper.html#notlike) notLike

```java
notLike(R column, Object val)
notLike(boolean condition, R column, Object val)
```

- NOT LIKE '%值%'
- 例: `notLike("name", "王")`--->`name not like '%王%'`

### [#](https://baomidou.com/guide/wrapper.html#likeleft) likeLeft

```java
likeLeft(R column, Object val)
likeLeft(boolean condition, R column, Object val)
```

- LIKE '%值'
- 例: `likeLeft("name", "王")`--->`name like '%王'`

### [#](https://baomidou.com/guide/wrapper.html#likeright) likeRight

```java
likeRight(R column, Object val)
likeRight(boolean condition, R column, Object val)
```

- LIKE '值%'
- 例: `likeRight("name", "王")`--->`name like '王%'`

### [#](https://baomidou.com/guide/wrapper.html#isnull) isNull

```java
isNull(R column)
isNull(boolean condition, R column)
```

- 字段 IS NULL
- 例: `isNull("name")`--->`name is null`

### [#](https://baomidou.com/guide/wrapper.html#isnotnull) isNotNull

```java
isNotNull(R column)
isNotNull(boolean condition, R column)
```

- 字段 IS NOT NULL
- 例: `isNotNull("name")`--->`name is not null`

### [#](https://baomidou.com/guide/wrapper.html#in) in

```java
in(R column, Collection<?> value)
in(boolean condition, R column, Collection<?> value)
```

- 字段 IN (value.get(0), value.get(1), ...)
- 例: `in("age",{1,2,3})`--->`age in (1,2,3)`

```java
in(R column, Object... values)
in(boolean condition, R column, Object... values)
```

- 字段 IN (v0, v1, ...)
- 例: `in("age", 1, 2, 3)`--->`age in (1,2,3)`

### [#](https://baomidou.com/guide/wrapper.html#notin) notIn

```java
notIn(R column, Collection<?> value)
notIn(boolean condition, R column, Collection<?> value)
```

- 字段 NOT IN (value.get(0), value.get(1), ...)
- 例: `notIn("age",{1,2,3})`--->`age not in (1,2,3)`

```java
notIn(R column, Object... values)
notIn(boolean condition, R column, Object... values)
```

- 字段 NOT IN (v0, v1, ...)
- 例: `notIn("age", 1, 2, 3)`--->`age not in (1,2,3)`

### [#](https://baomidou.com/guide/wrapper.html#insql) inSql

```java
inSql(R column, String inValue)
inSql(boolean condition, R column, String inValue)
```

- 字段 IN ( sql 语句 )
- 例: `inSql("age", "1,2,3,4,5,6")`--->`age in (1,2,3,4,5,6)`
- 例: `inSql("id", "select id from table where id < 3")`--->`id in (select id from table where id < 3)`

### [#](https://baomidou.com/guide/wrapper.html#notinsql) notInSql

```java
notInSql(R column, String inValue)
notInSql(boolean condition, R column, String inValue)
```

- 字段 NOT IN ( sql 语句 )
- 例: `notInSql("age", "1,2,3,4,5,6")`--->`age not in (1,2,3,4,5,6)`
- 例: `notInSql("id", "select id from table where id < 3")`--->`id not in (select id from table where id < 3)`

### [#](https://baomidou.com/guide/wrapper.html#groupby) groupBy

```java
groupBy(R... columns)
groupBy(boolean condition, R... columns)
```

- 分组：GROUP BY 字段, ...
- 例: `groupBy("id", "name")`--->`group by id,name`

### [#](https://baomidou.com/guide/wrapper.html#orderbyasc) orderByAsc

```java
orderByAsc(R... columns)
orderByAsc(boolean condition, R... columns)
```

- 排序：ORDER BY 字段, ... ASC
- 例: `orderByAsc("id", "name")`--->`order by id ASC,name ASC`

### [#](https://baomidou.com/guide/wrapper.html#orderbydesc) orderByDesc

```java
orderByDesc(R... columns)
orderByDesc(boolean condition, R... columns)
```

- 排序：ORDER BY 字段, ... DESC
- 例: `orderByDesc("id", "name")`--->`order by id DESC,name DESC`

### [#](https://baomidou.com/guide/wrapper.html#orderby) orderBy

```java
orderBy(boolean condition, boolean isAsc, R... columns)
```

- 排序：ORDER BY 字段, ...
- 例: `orderBy(true, true, "id", "name")`--->`order by id ASC,name ASC`

### [#](https://baomidou.com/guide/wrapper.html#having) having

```java
having(String sqlHaving, Object... params)
having(boolean condition, String sqlHaving, Object... params)
```

- HAVING ( sql 语句 )
- 例: `having("sum(age) > 10")`--->`having sum(age) > 10`
- 例: `having("sum(age) > {0}", 11)`--->`having sum(age) > 11`

### [#](https://baomidou.com/guide/wrapper.html#func) func

```java
func(Consumer<Children> consumer)
func(boolean condition, Consumer<Children> consumer)
```

- func 方法(主要方便在出现 if...else 下调用不同方法能不断链)
- 例: `func(i -> if(true) {i.eq("id", 1)} else {i.ne("id", 1)})`

### [#](https://baomidou.com/guide/wrapper.html#or) or

```java
or()
or(boolean condition)
```

- 拼接 OR

注意事项:

主动调用`or`表示紧接着下一个**方法**不是用`and`连接!(不调用`or`则默认为使用`and`连接)

- 例: `eq("id",1).or().eq("name","老王")`--->`id = 1 or name = '老王'`

```java
or(Consumer<Param> consumer)
or(boolean condition, Consumer<Param> consumer)
```

- OR 嵌套
- 例: `or(i -> i.eq("name", "李白").ne("status", "活着"))`--->`or (name = '李白' and status <> '活着')`

### [#](https://baomidou.com/guide/wrapper.html#and) and

```java
and(Consumer<Param> consumer)
and(boolean condition, Consumer<Param> consumer)
```

- AND 嵌套
- 例: `and(i -> i.eq("name", "李白").ne("status", "活着"))`--->`and (name = '李白' and status <> '活着')`

### [#](https://baomidou.com/guide/wrapper.html#nested) nested

```java
nested(Consumer<Param> consumer)
nested(boolean condition, Consumer<Param> consumer)
```

- 正常嵌套 不带 AND 或者 OR
- 例: `nested(i -> i.eq("name", "李白").ne("status", "活着"))`--->`(name = '李白' and status <> '活着')`

### [#](https://baomidou.com/guide/wrapper.html#apply) apply

```java
apply(String applySql, Object... params)
apply(boolean condition, String applySql, Object... params)
```

- 拼接 sql

注意事项:

该方法可用于数据库**函数** 动态入参的`params`对应前面`applySql`内部的`{index}`部分.这样是不会有 sql 注入风险的,反之会有!

- 例: `apply("id = 1")`--->`id = 1`
- 例: `apply("date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")`--->`date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")`
- 例: `apply("date_format(dateColumn,'%Y-%m-%d') = {0}", "2008-08-08")`--->`date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")`

### [#](https://baomidou.com/guide/wrapper.html#last) last

```java
last(String lastSql)
last(boolean condition, String lastSql)
```

- 无视优化规则直接拼接到 sql 的最后

注意事项:

只能调用一次,多次调用以最后一次为准 有 sql 注入的风险,请谨慎使用

- 例: `last("limit 1")`

### [#](https://baomidou.com/guide/wrapper.html#exists) exists

```java
exists(String existsSql)
exists(boolean condition, String existsSql)
```

- 拼接 EXISTS ( sql 语句 )
- 例: `exists("select id from table where age = 1")`--->`exists (select id from table where age = 1)`

### [#](https://baomidou.com/guide/wrapper.html#notexists) notExists

```java
notExists(String notExistsSql)
notExists(boolean condition, String notExistsSql)
```

- 拼接 NOT EXISTS ( sql 语句 )
- 例: `notExists("select id from table where age = 1")`--->`not exists (select id from table where age = 1)`

## [#](https://baomidou.com/guide/wrapper.html#querywrapper) QueryWrapper

说明:

继承自 AbstractWrapper ,自身的内部属性 entity 也用于生成 where 条件
及 LambdaQueryWrapper, 可以通过 new QueryWrapper().lambda() 方法获取

### [#](https://baomidou.com/guide/wrapper.html#select) select

```java
select(String... sqlSelect)
select(Predicate<TableFieldInfo> predicate)
select(Class<T> entityClass, Predicate<TableFieldInfo> predicate)
```

- 设置查询字段

说明:

以上方法分为两类.
第二类方法为:过滤查询字段(主键除外),入参不包含 class 的调用前需要`wrapper`内的`entity`属性有值! 这两类方法重复调用以最后一次为准

- 例: `select("id", "name", "age")`
- 例: `select(i -> i.getProperty().startsWith("test"))`

## [#](https://baomidou.com/guide/wrapper.html#updatewrapper) UpdateWrapper

说明:

继承自 `AbstractWrapper` ,自身的内部属性 `entity` 也用于生成 where 条件
及 `LambdaUpdateWrapper`, 可以通过 `new UpdateWrapper().lambda()` 方法获取!

### [#](https://baomidou.com/guide/wrapper.html#set) set

```java
set(String column, Object val)
set(boolean condition, String column, Object val)
```

- SQL SET 字段
- 例: `set("name", "老李头")`
- 例: `set("name", "")`--->数据库字段值变为**空字符串**
- 例: `set("name", null)`--->数据库字段值变为`null`

### [#](https://baomidou.com/guide/wrapper.html#setsql) setSql

```java
setSql(String sql)
```

- 设置 SET 部分 SQL
- 例: `setSql("name = '老李头'")`

### [#](https://baomidou.com/guide/wrapper.html#lambda) lambda

- 获取 `LambdaWrapper`
  在`QueryWrapper`中是获取`LambdaQueryWrapper`
  在`UpdateWrapper`中是获取`LambdaUpdateWrapper`
