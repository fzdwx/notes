# 微服务

springCloud是分布式微服务架构的一站式解决方案，是多种微服务架构落地技术的集合体

![image-20200828211949059](C:%5CUsers%5Cpdd20%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20200828211949059.png)



# 版本选型

![image-20200828213110160](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200828213110.png)

# 一、父项目创建

## 1.约定 > 配置 > 编码

*   定义jdk 1.8

*   定义文件类型 uft8

*   注解生效

    ![image-20200828214514233](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200828214514.png)

## 2.父项目Pom依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.like.SpringCloud</groupId>
    <artifactId>parent</artifactId>
    <version>1.0-SNAPSHOT</version>
    <!--设置-->
    <packaging>pom</packaging>
    <!--统一管理jar包版本-->
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>12</maven.compiler.source>
        <maven.compiler.target>12</maven.compiler.target>
        <junit.version>4.9</junit.version>
        <lombok.version>1.18.10</lombok.version>
        <log4j.version>1.2.9</log4j.version>
        <mysql.version>5.1.47</mysql.version>
        <druid.version>1.1.16</druid.version>
        <mybatis.spring.boot.version>2.1.1</mybatis.spring.boot.version>
    </properties>
    <!--声明依赖，并不引入，等待子项目引入-->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-project-info-reports-plugin</artifactId>
                <version>3.0.0</version>
            </dependency>
            <!--spring boot 2.2.2-->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.2.2.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!--spring cloud Hoxton.SR1-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Hoxton.SR1</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!--alibaba.cloud-->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>2.1.0.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!-- druid-->
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid</artifactId>
                <version>${druid.version}</version>
            </dependency>
            <!--mybatis-->
            <dependency>
                <groupId>org.mybatis.spring.boot</groupId>
                <artifactId>mybatis-spring-boot-starter</artifactId>
                <version>${mybatis.spring.boot.version}</version>
            </dependency>
            <!--mysql-->
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>${mysql.version}</version>
                <scope>runtime</scope>
            </dependency>
            <!--junit-->
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>${junit.version}</version>
            </dependency>
            <!--log4j-->
            <dependency>
                <groupId>log4j</groupId>
                <artifactId>log4j</artifactId>
                <version>${log4j.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
    <repositories>
        <repository>
            <id>aliyun</id>
            <name>aliyun</name>
            <layout>default</layout>
            <url>https://maven.aliyun.com/repository/public/</url>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
            <releases>
                <enabled>true</enabled>
            </releases>
        </repository>
    </repositories>
</project>
```

# 二、payment8001项目创建

## ==构建思路==：

支付项目

功能：

1.  根据id查询payment
2.  提交payment到数据库

项目结构：

![image-20200830113920637](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200830113927.png)

## 1.依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>SpringCloud</artifactId>
        <groupId>com.like</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-provider-payment8001</artifactId>

    <dependencies>
      <!--  &lt;!&ndash;包含了sleuth+zipkin&ndash;&gt;
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zipkin</artifactId>
        </dependency>
        &lt;!&ndash;eureka-client&ndash;&gt;
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>&lt;!&ndash; 引入自己定义的api通用包，可以使用Payment支付Entity &ndash;&gt;
            <groupId>com.atguigu.springcloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>-->
        <!--web-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--actuator-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <!--mybatis-plus-boot-starter-->
      <dependency>
         <groupId>com.baomidou</groupId>
         <artifactId>mybatis-plus-boot-starter</artifactId>
      </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.10</version>
        </dependency>
        <!--mysql-connector-java-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!--jdbc-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <!--热部署-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <!--lombok-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <!--test-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>

```



## 2.资源文件配置.yml

```yaml
server:
  port: 8001

spring:
  application:
    name: cloud-payment-service
  sleuth:
    sampler:
    #采样率值介于 0 到 1 之间，1 则表示全部采集
    probability: 1
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource            # 当前数据源操作类型
    driver-class-name: com.mysql.jdbc.Driver          # mysql驱动包
    url: jdbc:mysql://localhost:3306/springcloud?serverTimezone=UTC
    username: root
    password: rootroot



```



## 3.启动类

```java
package com.lk.sc;

@SpringBootApplication
@ComponentScan("com.lk.sc.*")
public class CloudPayment8001 {
    public static void main(String[] args) {
        SpringApplication.run(CloudPayment8001.class,args);
    }
}
```





## 4.实体类

### 	a.payment

```java
package com.lk.sc.entity;
@Data
public class Payment implements Serializable {
    private Long id;
    private String serial;
}
```





### 	b.定义一个统一的返回结果集类



```java
package com.lk.sc.entity;

@Data
public class Result {
    private Boolean success;
    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<String, Object>();
    public Result() {
    }
    public static Result ok() {
        Result result = new Result();
        result.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return result;
    }
    public static Result error() {
        Result result = new Result();
        result.setSuccess(ResultCodeEnum.UNKNOWN_REASON.getSuccess());
        result.setCode(ResultCodeEnum.UNKNOWN_REASON.getCode());
        result.setMessage(ResultCodeEnum.UNKNOWN_REASON.getMessage());
        return result;
    }
    public Result success(Boolean success) {
        this.setSuccess(success);
        return this;
    }
    public Result message(String message) {
        this.setMessage(message);
        return this;
    }
    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }
    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
    public Result data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
    @Getter
    @ToString
    enum ResultCodeEnum {
        /**
         * 成功
         */
        SUCCESS(true, 20000, "成功"),
        /**
         * 失败
         */
        UNKNOWN_REASON(false, 20001, "未知错误");
        private Boolean success;
        private Integer code;
        private String message;
        ResultCodeEnum(Boolean success, Integer code, String message) {
            this.success = success;
            this.code = code;
            this.message = message;
        }
    }
}
```



## 5.dao层

### 	a.接口

```java
package com.lk.sc.mapper;

@Mapper
@Repository
public interface PaymentMapper  extends BaseMapper<Payment> {

}
```



### 	b.配置文件

```xml
package com.lk.sc.mapper.xml;

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.lk.sc.mapper.PaymentMapper">

</mapper>
```



## 6.service层

### 	a.接口

继承IService类

```java
package com.lk.sc.service;
public interface PaymentService  extends IService<Payment> {

}
```

​	

### 	b.实现

继承ServiceImpl类，实现 PaymentService

```java
package com.lk.sc.service.impl;
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment> implements PaymentService {

}
```



## 7.controller层



```java
package com.lk.sc.controller;

@RequestMapping("/payment")
@RestController
public class PaymentController {

    @Resource
    PaymentService paymentService;

    /**
     * 根据id获取payment
     *
     * @param id 需要查询的payment对象的id
     * @return Result.data
     */
    @GetMapping("/get/{id}")
    public Result getById(@PathVariable String id) {
        Payment paymentServiceById = paymentService.getById(id);
        if (paymentServiceById != null) {
            return Result.ok().data("payment", paymentServiceById).message("查询成功");
        }
        return Result.error().message("查询失败，查询id:"+id);
    }

    /**
     * 添加payment到数据库
     *
     * @param payment 需要添加的payment
     * @return Result
     */
    @PostMapping("/save")
    public Result save(Payment payment) {
        boolean save = paymentService.save(payment);
        if (save) {
            return Result.ok().success(save).message("保存成功");
        }
        return Result.error().message("保存失败，数据库中可能已经存在这个对象,保存对象:"+payment);
    }
}
```



## 8.config

```java
package com.lk.sc.config;

/**
 * @author likeLove
 * @time 2020-08-30  11:23
 */
@Configuration
public class MybatisPlusConfig {
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        //可以通过环境变量获取你的mapper路径,这样mapper扫描可以通过配置文件配置了
        scannerConfigurer.setBasePackage("com.lk.*.mapper");
        return scannerConfigurer;
    }
}
```



## 9.建表sql

~~~sql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for payment
-- ----------------------------
DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `serial` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付流水号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '支付表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of payment
-- ----------------------------
INSERT INTO `payment` VALUES (31, '尚硅谷111');
INSERT INTO `payment` VALUES (32, 'atguigu002');
INSERT INTO `payment` VALUES (34, 'atguigu002');
INSERT INTO `payment` VALUES (35, 'atguigu002');

SET FOREIGN_KEY_CHECKS = 1;
~~~



## 10.==小结==

### 1.在运行的时候出现 `Invalid bound statement (not found)` 异常了

~~~
Invalid bound statement (not found): 
	com.lk.sc.service.PaymentService.getBaseMapper
org.apache.ibatis.binding.BindingException: 
	Invalid bound statement (not found): 
		com.lk.sc.service.PaymentService.getBaseMapper
~~~

这是因为没有配置mapper扫描的结果，解决：添加mybatisconfig类，

*   方法一：在 `Configuration` 类上使用注解 `MapperScan`

*   方法二：在`Configuration`类中添加mapper扫描bean

    ```java
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        //可以通过环境变量获取你的mapper路径,这样mapper扫描可以通过配置文件配置了
        scannerConfigurer.setBasePackage("com.yourpackage.*.mapper");
        return scannerConfigurer;
    }
    ```



总之：查看官网你一定会找到你想要的



# 三、api-common项目创建

## 目的：

*   抽取一些公共的方法和资源



## 1.pom依赖

```xml
<dependencies>
    <!--热部署-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <!--lombok-->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
    <!--hutool工具-->
    <dependency>
        <groupId>cn.hutool</groupId>
        <artifactId>hutool-all</artifactId>
        <version>5.1.0</version>
    </dependency>
    <!--mybatis-->
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
    </dependency>
    <!--test-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```



## 2.引入实体类

![image-20200831122957244](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831122957.png)



## 3.在其他的项目中引入这个公共的api项目

```xml
<!--引入自己定义的api-->
<dependency>
    <groupId>com.like</groupId>
    <artifactId>cloud-api-common</artifactId>
    <version>${project.version}</version>
</dependency>
```







# 四、order80项目创建

## 1.目的：

模拟用户操作，远程调用payment8001中的创建和查询方法



![](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831123619.png)

## 2.实现方法：

通过RestTemplate远程调用

## 3.pom依赖

```xml
<dependencies>
    <!--引入自己定义的api-->
    <dependency>
        <groupId>com.like</groupId>
        <artifactId>cloud-api-common</artifactId>
        <version>${project.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
</dependencies>
```

## 4.启动类

```java
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CloudOrder80 {
    public static void main(String[] args) {
        SpringApplication.run(CloudOrder80.class,args);
    }
}
```



## 5.配置文件.yml

```yml
server:
  port: 80
```



## 6.定义resttemplate配置类

返回resttemplate对象

```java
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
```



## 7.定义ordercontroller

```java
@RestController
@RequestMapping("/consumer")
public class OrderController {
    @Autowired
    RestTemplate restTemplate;

    public static final String PAYMENT_URL = "http://localhost:8001";

    @PostMapping("/payment/save")
    public Result savePayment( Payment payment) {
        System.out.println(payment);
        return restTemplate.postForObject(PAYMENT_URL + "/payment/save", payment, Result.class);
    }

    @GetMapping("/payment/get/{id}")
    public Result getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, Result.class);
    }
}
```



## 8.==注意==

1.在==远程调用save方法的时候可能会出现payment对象为null==的情况，但是直接调用paymentcontroller里面的save方法payment不会为null

在paymentcontroller中的save方法中为payment加上`@requestboot注解`,但是加了这个注解后，直接调用paymentcontroller中的save又会出现payment为null的情况



2.因为在api-common中==引入了mybatis-plus包，==所以要在启动类中添加排除类，排除==数据源自动配置类==

```java
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
```