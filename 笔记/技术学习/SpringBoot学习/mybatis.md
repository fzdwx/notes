# 一、第一个Mybatis程序：



 mybatis-smart 

## 1.导入的依赖：

~~~xml
		<dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.9</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.4.5</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.43</version>
        </dependency>
~~~



## 2.mybatis-config.xml

~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/book"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="lk/dao/UserMapper.xml"/>
    </mappers>
</configuration>
~~~



## 3.构建sqlSessionFactory，获取sqlSession

>   ### 1.SqlSessionFactoryBuilder().build(is)获取sqlSessionFactory
>
>   ## 2.sqlSessionFactory.openSession()获取sqlsession

~~~java
public class MyBatisUtils {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            //1.获取sqlSessionFactory
            String resource = "mybatis-config.xml";
            InputStream is = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //2.利用sqlSessionFactory 获取sqlSession 实例
    //sqlSession包含的面向数据命令执行所需的所有方法
    public static SqlSession getSqlSession (){
        return sqlSessionFactory.openSession();
    }
    @Test
    public void  test (){
        SqlSession sqlSession = getSqlSession();
        System.out.println(sqlSession);
    }
}
~~~



## 4.pojo

~~~java
public class User {
   private int id;
   private String username;
   private String pwd;
   private String email;
   private String balance;
}
~~~



## 5.dao

~~~java
public interface UserDao {
   /**
    * 获取所有用户
    * @return list
    */
   List<User> getAllUser();
}
~~~



mapper.xml

~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="lk.dao.UserDao">
  <select id="getAllUser" resultType="lk.pojo.User">
    select * from b_user
  </select>
</mapper>
~~~



## 6.测试



~~~java
public class UserDaoTest {
    @Test
    public void  test (){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserDao ud = sqlSession.getMapper(UserDao.class);
        List<User> list = ud.getAllUser();
        list.forEach(System.out::println);

        sqlSession.close();
    }
}
~~~





## 7.注意：

### 1.xml文件导入不成功：

在pom.xml中加入：

~~~xml
  <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>
~~~



### 2.每一个Mapper.xml都要在mybatis-config.xml中注册



~~~xml
 	<mappers>
        <mapper resource="lk/dao/UserMapper.xml"/>
    </mappers>
~~~







# 二、写一个crud：

## 1.在userDao接口中定义方法

~~~java
public interface UserDao {
   /**
    * 获取所有用户
    * @return list
    */
   List<User> getAllUser();

   /**
    * 根据输入的id获取用户
    * @param id int
    * @return user
    */
   User getUserById(int id);

   /**
    * 添加一个用户
    * @param user user
    * @return int
    *
    */
   Integer inertUser(User user);

   /**
    * 修改user
    * @param user user
    * @return int
    */
   Integer updateUser(User user);

   /**
    * 根据id删除用户
    * @param id int
    * @return int
    */
   Integer deleteUserById(int id);
}
~~~



## 2.在UserMapper中实现方法



~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="lk.dao.UserDao">
  <!--1获取所有用户-->
  <select id="getAllUser" resultType="lk.pojo.User">
    select * from b_user
  </select>

  <!--2根据输入的id获取用户-->
  <select id="getUserById" resultType="lk.pojo.User" parameterType="int">
        select * from b_user where id = #{id}
  </select>

  <insert id="inertUser" parameterType="lk.pojo.User">
    insert into b_user(username,pwd,email,balance) values (#{username},#{pwd},#{email},#{balance});
  </insert>
  <update id="updateUser" parameterType="lk.pojo.User">
    update b_user set username= #{username},pwd= #{pwd},email=#{email},balance = #{balance} where id=#{id};
</update>
<delete id="deleteUserById" parameterType="int">
    delete from b_user where  id = #{id};
</delete>

</mapper>
~~~



## 3.测试

~~~java
public class UserDaoTest {
    @Test
    public void getAllUser() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserDao ud = sqlSession.getMapper(UserDao.class);
        List<User> list = ud.getAllUser();
        list.forEach(System.out::println);
        sqlSession.close();
    }

    @Test
    public void getUserById() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserDao ud = sqlSession.getMapper(UserDao.class);
        User userById = ud.getUserById(1);
        System.out.println(userById);
    }

    @Test
    public void insert() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserDao ud = sqlSession.getMapper(UserDao.class);
        Integer integer = ud.inertUser(new User(0, "wosilike", "980650920", "980650933320@qq.com", 100));
        System.out.println(integer);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void update() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserDao ud = sqlSession.getMapper(UserDao.class);
        Integer integer = ud.updateUser(new User(16, "wosilike", "980650920", "980650933320@qq.com", 999));
        System.out.println(integer);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void delete() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserDao ud = sqlSession.getMapper(UserDao.class);
        Integer integer = ud.deleteUserById(16);
        System.out.println(integer);
        sqlSession.commit();
        sqlSession.close();
    }
}
~~~



# 三、使用map优化：

>   ### 1.用map代替user
>
>   ### 2.map里面字段的顺序可以不和sql语句相同
>
>   ## 3.只有map：mapper.xml里面的参数类型可以不用写



## 1.Api：

~~~java
  //通过map添加用户
   boolean addUserForMap(Map<String,Object> map);
   // 通过map更新用户
   boolean updateUserForMap(Map<String, Object> map);
~~~



## 2.mapper：



~~~xml
 	<insert id="addUserForMap" parameterType="map">
       insert into b_user(username,pwd) values (#{username},#{pwd});
    </insert>
        
    <update id="updateUserForMap">
    update b_user set username=#{name},pwd=#{password}  where id = #{id};
    </update>
~~~



## 3.测试



~~~java
  @Test
    public void addUserForMap() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserDao ud = sqlSession.getMapper(UserDao.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("username","qweqweqwe22222");
        map.put("pwd","123123123123231");
        boolean b = ud.addUserForMap(map);
        System.out.println(b);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void updateUserForMap() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserDao ud = sqlSession.getMapper(UserDao.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 17);
        map.put("name", "newqweqweqwe");
        map.put("password", "23123123");
        boolean b = ud.updateUserForMap(map);
        System.out.println(b);
        sqlSession.commit();
        sqlSession.close();
    }
~~~



# 四、模糊查询



>   ### 1.在java中加入%%，来实现模糊查询



## 1.API

~~~java
 List<User> getLikeUserName(String value);
~~~

## 2.mapper.xml

~~~xml
<select id="getLikeUserName" resultType="lk.pojo.User">
  	select * from b_user where username like #{value}
</select>
~~~

## 3.测试

~~~java
 @Test
    public void getLikeUserName() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserDao ud = sqlSession.getMapper(UserDao.class);
        List<User> list = ud.getLikeUserName("%like%");
        list.forEach(System.out::println);
        sqlSession.commit();
        sqlSession.close();
    }
~~~





# 五、mybatis环境配置：

## 概览：

>   configuration（配置）
>
>   -   [properties（属性）](https://mybatis.org/mybatis-3/zh/configuration.html#properties)
>   -   [settings（设置）](https://mybatis.org/mybatis-3/zh/configuration.html#settings)
>   -   [typeAliases（类型别名）](https://mybatis.org/mybatis-3/zh/configuration.html#typeAliases)
>   -   [typeHandlers（类型处理器）](https://mybatis.org/mybatis-3/zh/configuration.html#typeHandlers)
>   -   [objectFactory（对象工厂）](https://mybatis.org/mybatis-3/zh/configuration.html#objectFactory)
>   -   [plugins（插件）](https://mybatis.org/mybatis-3/zh/configuration.html#plugins)
>   -    [environments（环境配置）](https://mybatis.org/mybatis-3/zh/configuration.html#environments) 
>       -   environment（环境变量）
>           -   transactionManager（事务管理器）
>           -   dataSource（数据源）
>   -   [databaseIdProvider（数据库厂商标识）](https://mybatis.org/mybatis-3/zh/configuration.html#databaseIdProvider)
>   -   [mappers（映射器）](https://mybatis.org/mybatis-3/zh/configuration.html#mappers)



示例：

~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/book"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="lk/dao/UserMapper.xml"/>
    </mappers>
</configuration>
~~~



## 1.环境配置：environments

-    [environments（环境配置）](https://mybatis.org/mybatis-3/zh/configuration.html#environments) 
    -   environment（环境变量）
    -   transactionManager（事务管理器）
    -   dataSource（数据源）



>   一个mybatis可以配置成多套环境，但是一个sqlSessionFactory只能选择一个运行
>
>   默认的事务管理器是：jdbc
>
>   默认的连接池是：pooled

## 2.属性：properties

[properties（属性）](https://mybatis.org/mybatis-3/zh/configuration.html#properties)

>   可以引入外部文件来配置连接的数据库
>
>   

![1595836397012](C:\Users\pdd20\AppData\Roaming\Typora\typora-user-images\1595836397012.png)



![1595836422374](C:\Users\pdd20\AppData\Roaming\Typora\typora-user-images\1595836422374.png)



## 3.设置：settings

[settings（设置）](https://mybatis.org/mybatis-3/zh/configuration.html#settings)

![1595837152719](C:\Users\pdd20\AppData\Roaming\Typora\typora-user-images\1595837152719.png)

![1595837169690](C:\Users\pdd20\AppData\Roaming\Typora\typora-user-images\1595837169690.png)

![1595837180121](C:\Users\pdd20\AppData\Roaming\Typora\typora-user-images\1595837180121.png)





一个配置完整的 settings 元素的示例如下：

```xml
<settings>
  <setting name="cacheEnabled" value="true"/>
  <setting name="lazyLoadingEnabled" value="true"/>
  <setting name="multipleResultSetsEnabled" value="true"/>
  <setting name="useColumnLabel" value="true"/>
  <setting name="useGeneratedKeys" value="false"/>
  <setting name="autoMappingBehavior" value="PARTIAL"/>
  <setting name="autoMappingUnknownColumnBehavior" value="WARNING"/>
  <setting name="defaultExecutorType" value="SIMPLE"/>
  <setting name="defaultStatementTimeout" value="25"/>
  <setting name="defaultFetchSize" value="100"/>
  <setting name="safeRowBoundsEnabled" value="false"/>
  <setting name="mapUnderscoreToCamelCase" value="false"/>
  <setting name="localCacheScope" value="SESSION"/>
  <setting name="jdbcTypeForNull" value="OTHER"/>
  <setting name="lazyLoadTriggerMethods" value="equals,clone,hashCode,toString"/>
</settings>
```



## 4.别名优化：typeAliases

[typeAliases（类型别名）](https://mybatis.org/mybatis-3/zh/configuration.html#typeAliases)



~~~xml
<typeAliases>
        <!--1.给整个包里面的类起别名，别名是首字母小写-->
        <package name="lk.pojo"/>
        <!--2.给一个类指定别名，可以随意取值-->
        <typeAlias type="lk.pojo.User" alias="user"/>
    </typeAliases>
~~~









# 六、xml映射文件

## XML 映射器

MyBatis 的真正强大在于它的语句映射，这是它的魔力所在。由于它的异常强大，映射器的 XML 文件就显得相对简单。如果拿它跟具有相同功能的 JDBC 代码进行对比，你会立即发现省掉了将近 95% 的代码。MyBatis 致力于减少使用成本，让用户能更专注于 SQL 代码。

SQL 映射文件只有很少的几个顶级元素（按照应被定义的顺序列出）：

-   `cache` – 该命名空间的缓存配置。
-   `cache-ref` – 引用其它命名空间的缓存配置。
-   `resultMap` – 描述如何从数据库结果集中加载对象，是最复杂也是最强大的元素。
-   `parameterMap` – 老式风格的参数映射。此元素已被废弃，并可能在将来被移除！请使用行内参数映射。文档中不会介绍此元素。
-   `sql` – 可被其它语句引用的可重用语句块。
-   `insert` – 映射插入语句。
-   `update` – 映射更新语句。
-   `delete` – 映射删除语句。
-   `select` – 映射查询语句。

下一部分将从语句本身开始来描述每个元素的细节。







## 1.结果映射：resultMap

### 	简单的结果集映射：



~~~xml
<resultMap id="userResulMap" type="lk.pojo.User">
    <id property="userId" column="id"/>
    <result property="password" column="pwd"/>
    <result property="name" column="username"/>
</resultMap>
<!--1获取所有用户-->
<select id="getAllUser" resultMap="userResulMap">
    select id,username,pwd,email,balance from b_user
</select>
~~~







# 七、日志工厂：



![1595915246853](C:\Users\pdd20\AppData\Roaming\Typora\typora-user-images\1595915246853.png)







常用：log4j  stdout_logging





## 	1.使用：stdout_logging

配置：

~~~xml
<settings>
    <setting name="logImpl" value="stdout_logging"/>
</settings>
~~~

测试

~~~java
Logging initialized using 'class org.apache.ibatis.logging.stdout.StdOutImpl' adapter.
PooledDataSource forcefully closed/removed all connections.
PooledDataSource forcefully closed/removed all connections.
PooledDataSource forcefully closed/removed all connections.
PooledDataSource forcefully closed/removed all connections.
Opening JDBC Connection
Tue Jul 28 13:50:46 GMT+08:00 2020 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
Created connection 1863932867.
Setting autocommit to false on JDBC Connection [com.mysql.jdbc.JDBC4Connection@6f195bc3]
==>  Preparing: select id,username,pwd,email,balance from b_user 
==> Parameters: 
<==    Columns: id, username, pwd, email, balance
<==        Row: 1, like, 123, 980650920@qq.com, 99800
<==        Row: 2, root, root, , 100000
<==        Row: 10, like123, 123123123, 9806509201@qq.com, 100000
<==        Row: 12, 123123123, 123123123, 123123123123@qq.com, 100000
<==        Row: 13, aaaaaaa, 123123123, 123@qq.com, 100000
<==        Row: 14, like123456, like123456, like123456@qq.com, 100000
<==        Row: 17, newqweqweqwe, 23123123, null, null
<==        Row: 18, qweqweqwe22222, 123123123123231, null, null
<==      Total: 8
User{id=1, username='like', pwd='123', email='980650920@qq.com', balance='99800'}
User{id=2, username='root', pwd='root', email='', balance='100000'}
User{id=10, username='like123', pwd='123123123', email='9806509201@qq.com', balance='100000'}
User{id=12, username='123123123', pwd='123123123', email='123123123123@qq.com', balance='100000'}
User{id=13, username='aaaaaaa', pwd='123123123', email='123@qq.com', balance='100000'}
User{id=14, username='like123456', pwd='like123456', email='like123456@qq.com', balance='100000'}
User{id=17, username='newqweqweqwe', pwd='23123123', email='null', balance='0'}
User{id=18, username='qweqweqwe22222', pwd='123123123123231', email='null', balance='0'}
Resetting autocommit to true on JDBC Connection [com.mysql.jdbc.JDBC4Connection@6f195bc3]
Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@6f195bc3]
Returned connection 1863932867 to pool.
~~~



## 2.使用log4j：

>   1.log4j是阿帕奇的一个开元项目，可以控制日志信息的输出，目的地是文件，gui组件，控制台
>
>   2.可以控制每一条日志的输出
>
>   3.可以定义每一条日志的级别



### 	a.导入依赖

~~~xml
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
~~~



### 	b.生成资源文件

log4j. properties 

~~~properties
#将等级为DEBUG的日志信息输出到console和file这两个目的地，console和file的定义在下面的代码
log4j.rootLogger=DEBUG,console,file
#控制台输出的相关设置
log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.Target=System.out
log4j.appender.console.Threshold=DEBUG
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=[%p][%-d{y-MM-dd HH:mm:ss}][%c]-%m%n
#文件输出的相关设置
log4j.appender.file=org.apache.log4j.RollingFileAppender
log4j.appender.file.File=./log/log4j.log
log4j.appender.file.MaxFileSize=10mb
log4j.appender.file.Threshold=DEBUG
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=[%-d{yyyy-MM-dd HH:mm:ss}]  [ %t:%r ] - [ %p ]  %m%n
#日志输出级别
log4j.logger.org.mybatis=DEBUG
log4j.logger.java.sql=DEBUG
log4j.logger.java.sql.Statement=DEBUG
log4j.logger.java.sql.ResultSet=DEBUG
log4j.logger.java.sql.PreparedStatement=DEBUG
~~~





### 	c.在mybatis—config.xml配置



~~~xml
<settings>
    <!--<setting name="logImpl" value="stdout_logging"/>-->
    <setting name="logImpl" value="log4j"/>
</settings>
~~~



d.自己实现一个日志方法：

~~~java
  static Logger log4j = Logger.getLogger(UserDaoTest.class);

    @Test
    public void testLog4j() {
        log4j.info("info:测试方法");
    }
~~~



# 八、分页方法：

## 1.API

~~~java
 /**
    * 分页显示数据
    *    String startIndex
    *    String pageSize
    * @param map map
    * @return list
    */
   List<User> getUserByLimit(Map<String, Object> map);
~~~



## 2.mapper.xml

~~~xml
<select id="getUserByLimit"  resultType="lk.pojo.User" parameterType="map">
    select  * from  b_user limit #{startIndex},#{pageSize};
</select>
~~~





## 3.测试

~~~java
 @Test
    // select  * from  b_user limit #{startIndex},#{pageSize};
    public void getUserByLimit() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserDao ud = sqlSession.getMapper(UserDao.class);
        Map<String, Object> map = new HashMap<>();
        map.put("startIndex",0);
        map.put("pageSize",4);
        List<User> limit = ud.getUserByLimit(map);
        limit.forEach(System.out::println);
    }
~~~



## 4.RorBoudns 实现：

​	mapper.xml

~~~xml
<select id="getUerByRowBounds" resultType="lk.pojo.User">
    select * from b_user;
</select>
~~~

api

~~~java

/**
* 通过RowBounds获取分页
* @return
*/
List<User> getUerByRowBounds();
~~~



测试：

~~~java
  @Test
    public void getUerByRowBounds() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        RowBounds rb = new RowBounds(1, 2);
        List<User> l = sqlSession.selectList("lk.dao.UserDao.getUerByRowBounds",null,rb);
        l.forEach(System.out::println);
    }
~~~





## 5.Mybatis-pagehelper 分页插件

### 											[分页插件]( https://pagehelper.github.io/docs/howtouse/ )





# 九、使用注解开发mybatis





# 十、多对一，多个学生对一个老师

## 	1.环境搭建：

老师和学生表

>   学生和老师是多对一关系

~~~sql
CREATE TABLE `teacher` (
`id` INT(10) NOT NULL,
`name` VARCHAR(30) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

INSERT INTO teacher(`id`, `name`) VALUES (1, '秦老师');

CREATE TABLE `student` (
`id` INT(10) NOT NULL,
`name` VARCHAR(30) DEFAULT NULL,
`tid` INT(10) DEFAULT NULL,
PRIMARY KEY (`id`),
KEY `fktid` (`tid`),
CONSTRAINT `fktid` FOREIGN KEY (`tid`) REFERENCES `teacher` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8
INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('1', '小明', '1');
INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('2', '小红', '1');
INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('3', '小张', '1');
INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('4', '小李', '1');
INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('5', '小王', '1');
~~~



![1595999305376](C:\Users\pdd20\AppData\Roaming\Typora\typora-user-images\1595999305376.png)

![1595999321254](C:\Users\pdd20\AppData\Roaming\Typora\typora-user-images\1595999321254.png)



## 2.高级的结果集映射：

>   通过子查询，查询到学生表关联的老师的信息



### 	a.API

~~~java

/**
* 获取所有学生的信息和他们老师的信息
* @return list
*/
List<Student> getAllStudentAndTid();
~~~



### 	b.Mapper.xml

>   按照查询嵌套查询

~~~xml
<select id="getAllStudentAndTid" resultMap="getStudentAndTeacher">
    select  * from student;
</select>
<resultMap id="getStudentAndTeacher" type="lk.pojo.Student">
    <association property="teacher" column="tid" javaType="lk.pojo.Teacher" select="getTeacher"/>
</resultMap>
<select id="getTeacher" resultType="lk.pojo.Teacher">
    select * from teacher where id= #{tid};
</select>
~~~



### c.测试

~~~java
 @Test
    public void getAllStudentAndTid() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        StudentDao sd = sqlSession.getMapper(StudentDao.class);
        List<Student> list = sd.getAllStudentAndTid();
        list.forEach(System.out::println);
    }

Student{id=1, name='小明', teacher=Teacher{id=1, name='秦老师'}}
Student{id=2, name='小红', teacher=Teacher{id=1, name='秦老师'}}
Student{id=3, name='小张', teacher=Teacher{id=1, name='秦老师'}}
Student{id=4, name='小李', teacher=Teacher{id=1, name='秦老师'}}
Student{id=5, name='小王', teacher=Teacher{id=1, name='秦老师'}}
~~~



### d.方法2

>   连表查询，按照结果查询

~~~xml
<select id="getAllStudentAndTid2" resultMap="getStudentAndTeacher2">
    select s.id sid, s.name sname, t.name tname
    from student s,
    teacher t
    where s.tid =t.id;
</select>
<resultMap id="getStudentAndTeacher2" type="lk.pojo.Student">
    <result property="id" column="sid"/>
    <result property="name" column="sname"/>
    <association property="teacher" javaType="lk.pojo.Teacher">
        <result property="name"  column="tname"/>
    </association>
</resultMap>
~~~



# 十一、一对多，一个老师对多个学生





## 1.Api

~~~java
/**
* 获取一个老师的所有学生
* @return
*/
List<Teacher> getAllTeacherAndStudentName();
~~~



## 2.xml



~~~xml
<select id="getAllTeacherAndStudentName" resultMap="mapV1">
    select  t.id tid,t.name tname, s.name sname,s.id sid
    from student s,
    teacher t
    where t.id = s.tid;
</select>
<resultMap id="mapV1" type="lk.pojo.Teacher">
    <result property="id" column="tid"/>
    <result property="name" column="tname"/>
    
    <collection property="student" ofType="lk.pojo.Student" >
        <result property="id" column="sid"/>
        <result property="name" column="sname"/>
        <result property="id" column="tid"/>
        <association property="teacher" javaType="lk.pojo.Teacher" >
            <result property="id" column="tid"/>
        </association>
    </collection>
</resultMap>

~~~



## 3.测试



~~~java
 @Test
    public void getAllTeacherAndStudentName() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        TeacherDao td = sqlSession.getMapper(TeacherDao.class);
        List<Teacher> allTeacher = td.getAllTeacherAndStudentName();
        allTeacher.forEach(System.out::println);
    }


Teacher{id=1, name='秦老师', 
student=[
Student{id=1, name='小明', teacher=Teacher{id=1, name='null', student=null}}, Student{id=1, name='小红', teacher=Teacher{id=1, name='null', student=null}}, Student{id=1, name='小张', teacher=Teacher{id=1, name='null', student=null}}, Student{id=1, name='小李', teacher=Teacher{id=1, name='null', student=null}}, Student{id=1, name='小王', teacher=Teacher{id=1, name='null', student=null}}]}
~~~



# 十二、一对多，多对一小结

>   #### 1.关联：assoncation  多对一
>
>   ##### 2.集合：collection 一对多
>
>   ##### 3.javatype：用来指定实体类中的属性的类型
>
>   ##### 3.oftype：用来指定到集合（list）中的pojo类型



*   保证sql可读性，



# 十三、动态sql

[动态sql]([https://mybatis.org/mybatis-3/zh/dynamic-sql.html)



>   表达式
>
>   -   if
>   -   choose (when, otherwise)
>   -   trim (where, set)
>   -   foreach



## 1.环境搭建

建表

~~~sql
CREATE TABLE `blog`(
`id` VARCHAR(50) NOT NULL COMMENT '博客id',
`title` VARCHAR(100) NOT NULL COMMENT '博客标题',
`author` VARCHAR(30) NOT NULL COMMENT '博客作者',
`create_time` DATETIME NOT NULL COMMENT '创建时间',
`views` INT(30) NOT NULL COMMENT '浏览量'
)ENGINE=INNODB DEFAULT CHARSET=utf8
~~~

实体类

~~~java
public class Blog {
    private int id;
    private String title;
    private String author;
    private Date createTime;
    private int views;
}
~~~



>createTime 和数据的不一致，开启驼峰命名，在myatis-config.xml
>
>```xml
><setting name="mapUnderscoreToCamelCase" value="true"/>
>```

## 2.IF

>   动态sql要做的是根据条件包含where子句的一部分
>
>   通过判断传入的参数，是否拼接sql语句

### 	eg：

~~~xml
/** 查询 */
List<Blog>  queryBlogByIf(Map map);
    
<select id="queryBlogByIf" resultType="lk.pojo.Blog">
    select * from blog where  1=1
    <if test="t != null">
        and title =#{t}
    </if>
    <if test="a != null">
        and author= #{a}
    </if>
</select> 
~~~



~~~java
  @Test
    public void queryBlogByIf() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        BlogDao bd = sqlSession.getMapper(BlogDao.class);
        HashMap<Object, Object> map = new HashMap<>();
        /*map.put("t","Mybatis学习手册");*/
        map.put("a","like");
        List<Blog> blogs = bd.queryBlogByIf(map);
        blogs.forEach(System.out::println);
    }
~~~



### a.使用《where》 改进

~~~xml
<select id="queryBlogByIf" resultType="lk.pojo.Blog">      
    select * from blog  
    <where>
        <if test="t != null">
            and title =#{t}    
        </if>
        <if test="a != null">      
            and author= #{a}
        </if>
    </where>
</select>
~~~









## 3.choose、when、otherwise



>    有时候，我们不想使用所有的条件，而只是想从多个条件中选择一个使用 。
>
>    谁第一个满足条件就使用哪个
>
>   choose：选择一个



~~~xml
<select id="queryBlogByChoose" resultType="lk.pojo.Blog">
    select  * from blog
    <where>
        <choose>
            <when test="t != null ">and title = #{t}</when>
            <when test="a != null ">and author = #{a}</when>
            <when test="v != null "> and views > #{v} </when>
        </choose>
    </where>
</select>
~~~



~~~java
@Test
public void queryBlogByChoose() {
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    BlogDao bd = sqlSession.getMapper(BlogDao.class);
    HashMap<Object, Object> map = new HashMap<>();
    /* map.put("t","Mybatis学习手册");*/
    map.put("a", "like");
    map.put("v", 5000);
    bd.queryBlogByChoose(map).forEach(System.out::println);
}
~~~



## 4.set



~~~xml
boolean updateBlogBySet(Map map);

<update id="updateBlogBySet">
    update blog
    <set>
        <if test="title != null ">title = #{title},</if>
        <if test="author != null ">author = #{author},</if>
        <if  test="views !=null ">views = #{views},</if>
    </set>
    where id = #{id}
</update>
~~~



~~~java
@Test
public void updateBlogBySet() {
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    BlogDao bd = sqlSession.getMapper(BlogDao.class);
    HashMap<Object, Object> map = new HashMap<>();
    map.put("id","1");
    map.put("title","新的title");
    map.put("author","likelove");
    boolean b = bd.updateBlogBySet(map);
    sqlSession.commit();
    System.out.println(b);
}
~~~







## 5.trim

>   prefix：表示在trim标签内sql语句加上前缀xxx
>
>   suffix：表示在trim标签内sql语句加上后缀xxx
>
>   suffixOverrides：表示去除最后一个后缀xxx
>
>   prefixOverrides：表示去除第一个前缀and或者or 
>
>   xxx表示属性引号中的值；

~~~xml
<trim prefix="WHERE" prefixOverrides="AND |OR ">
  ...
</trim>
<trim prefix="SET" suffixOverrides=",">
  ...
</trim>
~~~

>    上述例子会移除所有 *prefixOverrides* 属性中指定的内容，并且插入 *prefix* 属性中指定的内容 





## 6.sql

>   sql语句复用，抽取公共部分
>
>   include 引入



### 	a.sql抽取公共的

~~~xml
<sql id="set-if-title-author-views">
    <if test="title != null ">title = #{title},</if>
    <if test="author != null ">author = #{author},</if>
    <if  test="views !=null ">views = #{views},</if>
</sql>
~~~



### 	b.include引入sql

~~~xml
<update id="updateBlogBySet">
    update blog
    <set>
    <include refid="set-if-title-author-views"></include>
    </set>
    where id = #{id}
</update>
~~~



## 7.foreach



>   #####  动态 SQL 的另一个常见使用场景是对集合进行遍历（尤其是在构建 IN 条件语句的时候） 



### 	a.API

>   ```java
>   List<Blog> queryBlogByForeach(Map map);
>   ```



### 	b.mapper.xml



~~~xml
<select id="queryBlogByForeach" resultType="lk.pojo.Blog">
    select  * from blog
    <where>
        <foreach collection="list"  item="id" open="id in (" close=")" separator=",">
            #{id}
        </foreach>
    </where>
</select>>
~~~





### 	c.test



~~~java
@Test
public void queryBlogByForeach() {
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    BlogDao bd = sqlSession.getMapper(BlogDao.class);
    HashMap<Object, Object> map = new HashMap<>();
    ArrayList<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(2);
    list.add(3);
    map.put("list",list);
    List<Blog> blogs = bd.queryBlogByForeach(map);
    for (Blog blog : blogs) {
        System.out.println(blog);
    }
}

Preparing: select * from blog WHERE id in ( ? , ? , ? ) 
Parameters: 1(Integer), 2(Integer), 3(Integer)
    
    
Blog{
    id=1, title='新的title', author='likelove', createTime=Wed Jul 29 15:22:00 GMT+08:00 2020, views=123123}
Blog{
    id=2, title='我在学习Spring', author='like', createTime=Wed Jul 29 15:23:28 GMT+08:00 2020, views=3996}
Blog{
    id=3, title='mysql从入门到精通', author='like', createTime=Wed Jul 29 15:23:56 GMT+08:00 2020, views=6999}
~~~







# 十四、缓存

查询：

>   ​	1.连接据库，消耗资源
>
>   ​	2.一次查询的结果，可以暂存在一个可以直接取到的地方 ：内存 =>缓存
>
>   ​	3.再次查询的时候走缓存，不用走数据库
>
>   

缓存：

>   1.  存在内存中你的临时数据
>   2.  用户经常查询的数据放入缓存中，用户查询的时候就不用去数据库查询，从缓存中插叙，提高了查询效率，解决了高并发系统的性能问题



## 1.缓存级别

>   1.默认定义了两级缓存：
>
>   ​	一级缓存
>
>   ​	二级缓存
>
>   2.默认只有一级缓存开启（sqlsession级别，本地缓存）
>
>   3.二级缓存需要手动开启和配置，基于namespace 命名空间
>
>   4.可以通过cache接口自定义二级缓存



-   映射语句文件中的所有 select 语句的结果将会被缓存。
-   映射语句文件中的所有 insert、update 和 delete 语句会刷新缓存。
-   缓存会使用最近最少使用算法（LRU, Least Recently Used）算法来清除不需要的缓存。
-   缓存不会定时进行刷新（也就是说，没有刷新间隔）。
-   缓存会保存列表或对象（无论查询方法返回哪种）的 1024 个引用。
-   缓存会被视为读/写缓存，这意味着获取到的对象并不是共享的，可以安全地被调用者修改，而不干扰其他调用者或线程所做的潜在修改。



 可用的清除策略有： 

-   `LRU` – 最近最少使用：移除最长时间不被使用的对象。      默认
-   `FIFO` – 先进先出：按对象进入缓存的顺序来移除它们。
-   `SOFT` – 软引用：基于垃圾回收器状态和软引用规则移除对象。
-   `WEAK` – 弱引用：更积极地基于垃圾收集器状态和弱引用规则移除对象。





### 	a.实例

~~~java
@Test
public void queryBlogByForeach() {
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    BlogDao bd = sqlSession.getMapper(BlogDao.class);
    HashMap<Object, Object> map = new HashMap<>();
    ArrayList<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(2);
    list.add(3);
    map.put("list",list);
    List<Blog> blogs = bd.queryBlogByForeach(map);
    for (Blog blog : blogs) {
        System.out.println(blog);
    }
    System.out.println("****************");
    List<Blog> blogs2 = bd.queryBlogByForeach(map);
    for (Blog blog : blogs2) {
        System.out.println(blog);
    }
}

Opening JDBC Connection
Fri Jul 31 14:17:28 GMT+08:00 2020 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
Created connection 712025048.
Setting autocommit to false on JDBC Connection [com.mysql.jdbc.JDBC4Connection@2a70a3d8]
==>  Preparing: select * from blog WHERE id in ( ? , ? , ? ) 
==> Parameters: 1(Integer), 2(Integer), 3(Integer)
<==    Columns: id, title, author, create_time, views
<==        Row: 1, 新的title, likelove, 2020-07-29 15:22:00.0, 123123
<==        Row: 2, 我在学习Spring, like, 2020-07-29 15:23:28.0, 3996
<==        Row: 3, mysql从入门到精通, like, 2020-07-29 15:23:56.0, 6999
<==      Total: 3
Blog{id=1, title='新的title', author='likelove', createTime=Wed Jul 29 15:22:00 GMT+08:00 2020, views=123123}
Blog{id=2, title='我在学习Spring', author='like', createTime=Wed Jul 29 15:23:28 GMT+08:00 2020, views=3996}
Blog{id=3, title='mysql从入门到精通', author='like', createTime=Wed Jul 29 15:23:56 GMT+08:00 2020, views=6999}
****************
Blog{id=1, title='新的title', author='likelove', createTime=Wed Jul 29 15:22:00 GMT+08:00 2020, views=123123}
Blog{id=2, title='我在学习Spring', author='like', createTime=Wed Jul 29 15:23:28 GMT+08:00 2020, views=3996}
Blog{id=3, title='mysql从入门到精通', author='like', createTime=Wed Jul 29 15:23:56 GMT+08:00 2020, views=6999}
Resetting autocommit to true on JDBC Connection [com.mysql.jdbc.JDBC4Connection@2a70a3d8]
Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@2a70a3d8]
Returned connection 712025048 to pool.
~~~



>   数据库连接只开启了一次



### b.清理缓存

~~~java
sqlSession.clearCache()
~~~

### c.一级缓存：

默认开启







## 2.二级缓存

### 	开启

~~~xml
  <setting name="cacheEnabled" value="true"/>

~~~

### 	在要使用的mapper中开启

~~~xml
<cache
  eviction="FIFO"
  flushInterval="60000"
  size="512"
  readOnly="true"/>

~~~



### 	测试

~~~java
@Test
public void queryBlogByForeach() {
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    BlogDao bd = sqlSession.getMapper(BlogDao.class);
    HashMap<Object, Object> map = new HashMap<>();
    ArrayList<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(2);
    list.add(3);
    map.put("list",list);
    List<Blog> blogs = bd.queryBlogByForeach(map);
    for (Blog blog : blogs) {
        System.out.println(blog);
    }
    System.out.println("****************");
    sqlSession.close();
    SqlSession sqlSession2 = MyBatisUtils.getSqlSession();
    BlogDao bd2 = sqlSession2.getMapper(BlogDao.class);
    List<Blog> blogs2 = bd2.queryBlogByForeach(map);
    for (Blog blog : blogs2) {
        System.out.println(blog);
    }
}

Opening JDBC Connection
Fri Jul 31 14:40:01 GMT+08:00 2020 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
Created connection 428910174.
Setting autocommit to false on JDBC Connection [com.mysql.jdbc.JDBC4Connection@1990a65e]
==>  Preparing: select * from blog WHERE id in ( ? , ? , ? ) 
==> Parameters: 1(Integer), 2(Integer), 3(Integer)
<==    Columns: id, title, author, create_time, views
<==        Row: 1, 新的title, likelove, 2020-07-29 15:22:00.0, 123123
<==        Row: 2, 我在学习Spring, like, 2020-07-29 15:23:28.0, 3996
<==        Row: 3, mysql从入门到精通, like, 2020-07-29 15:23:56.0, 6999
<==      Total: 3
Blog{id=1, title='新的title', author='likelove', createTime=Wed Jul 29 15:22:00 GMT+08:00 2020, views=123123}
Blog{id=2, title='我在学习Spring', author='like', createTime=Wed Jul 29 15:23:28 GMT+08:00 2020, views=3996}
Blog{id=3, title='mysql从入门到精通', author='like', createTime=Wed Jul 29 15:23:56 GMT+08:00 2020, views=6999}
****************
Resetting autocommit to true on JDBC Connection [com.mysql.jdbc.JDBC4Connection@1990a65e]
Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@1990a65e]
Returned connection 428910174 to pool.
Cache Hit Ratio [lk.dao.BlogDao]: 0.5
Blog{id=1, title='新的title', author='likelove', createTime=Wed Jul 29 15:22:00 GMT+08:00 2020, views=123123}
Blog{id=2, title='我在学习Spring', author='like', createTime=Wed Jul 29 15:23:28 GMT+08:00 2020, views=3996}
Blog{id=3, title='mysql从入门到精通', author='like', createTime=Wed Jul 29 15:23:56 GMT+08:00 2020, views=6999}
~~~



# 十五、mybatisplus

