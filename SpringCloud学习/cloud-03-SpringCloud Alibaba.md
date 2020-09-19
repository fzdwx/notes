# 	nacos

Dynamic *Na*ming and *Co*nfiguration *S*ervice

eureka + config + bus

**作用**

替代eureka作为服务注册中心，替代config作为服务配置中心



# 一、部署nacos

github：https://github.com/alibaba/nacos下载nacos v1.3.1

访问：http://localhost:8848/nacos/index.html#/login

![image-20200917124137899](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917124138.png)







# 二、建项目9001

cloudalibaba-provider-payment9001

## 依赖：

父

```xml
<!--spring cloud alibaba 2.1.0.RELEASE-->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-alibaba-dependencies</artifactId>
    <version>2.1.0.RELEASE</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>
```

自己

```xml
<dependencies>
    <!--SpringCloud ailibaba nacos -->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
    <!-- SpringBoot整合Web组件 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <!--日常通用jar包配置-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```



## 配置文件

```yml
server:
  port: 9001

spring:
  application:
    name: nacos-payment-provider
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848 #配置Nacos地址

management:
  endpoints:
    web:
      exposure:
        include: '*'
```



## 启动类

```java
@SpringBootApplication
@EnableDiscoveryClient
public class CloudAlibabaNacos9001 {
    public static void main(String[] args) {
        SpringApplication.run(CloudAlibabaNacos9001.class, args);
    }
}
```



## 控制层

```java
@RestController
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/nacos/{id}")
    public String getPayment(@PathVariable String id) {
        return "nacos registry,serverPort:"+serverPort+"\t id"+id ;
    }
}
```



## nacos服务列表

![image-20200917130620775](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917130620.png)





## 复制9001到9002

![image-20200917131011329](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917131011.png)

![image-20200917131018686](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917131018.png)





# 三、建项目83

![image-20200917132822827](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917132822.png)

## pom

```xml
<dependencies>
    <!--SpringCloud ailibaba nacos -->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
    <!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
    <dependency>
        <groupId>com.like</groupId>
        <artifactId>cloud-api-common</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
    <!-- SpringBoot整合Web组件 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <!--日常通用jar包配置-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```



## 配置文件

```yml
server:
  port: 83


spring:
  application:
    name: nacos-order-consumer
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848


#消费者将要去访问的微服务名称(注册成功进nacos的微服务提供者)
service-url:
  nacos-user-service: http://nacos-payment-provider
```



## 启动类

```java
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CloudOrder83 {
    public static void main(String[] args) {
        SpringApplication.run(CloudOrder83.class, args);
    }
}
```



## controller

```java
@RestController
@Slf4j
public class OrderNacosController {
    @Resource
    private RestTemplate restTemplate;
    @Value("${service-url.nacos-user-service}")
    private String serverURL;
    @GetMapping(value = "/consumer/payment/nacos/{id}")
    public String paymentInfo(@PathVariable("id") Long id) {
        return restTemplate.getForObject(serverURL + "/payment/nacos/" + id, String.class);
    }
}
```



## config

```java
@Configuration
public class ApplicationContextConfig {
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
```



## 测试

实现的轮询访问

![image-20200917132750739](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917132750.png)

![image-20200917132758787](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917132758.png)

![image-20200917132836316](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917132836.png)





# 四、C A P

c：是所有节点在同一时间看到的数据是一致的

a：是所有的请求是都会收到响应

**怎么选用：**

如果不需要存储服务级别的信息且服务实例是通过nacos-client注册，并且能够保持心跳上报，那么就可以选择 Ap，ap模式只支持注册临时实例

如果需要在服务级别编辑或者存储配置信息，那么cp是必须的，k8s和dns都适用于cp模式，cp支持注册持久化实例，此时则是以raft协议为集群模式运行，该模式下注册实例必须先注册服务







# 五、nacos的配置中心

## 1、cloudalibaba-config-client-3377项目创建

### pom依赖

```xml
<dependencies>
    <!--nacos-config-->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    </dependency>
    <!--nacos-discovery-->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
    <!--web + actuator-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <!--一般基础配置-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```



### 配置文件

#### application

```yml
spring:
  profiles:
    active: dev # 表示开发环境
    #active: test # 表示测试环境
    #active: info
```

#### boorstrap

```yml
# nacos配置
server:
  port: 3377

spring:
  application:
    name: nacos-config-client
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848 #Nacos服务注册中心地址
      config:
        server-addr: localhost:8848 #Nacos作为配置中心地址
        file-extension: yaml #指定yaml格式的配置
        #group: DEV_GROUP
        #namespace: 7d8f0f5a-6a53-4785-9686-dd460158e5d4
# ${spring.application.name}-${spring.profile.active}.${spring.cloud.nacos.config.file-extension}
# nacos-config-client-dev.yaml

# nacos-config-client-test.yaml   ----> config.info
```



### 启动类

```java
@EnableDiscoveryClient
@SpringBootApplication
public class CloudConfig3377 {
    public static void main(String[] args) {
        SpringApplication.run(CloudConfig3377.class, args);
    }
}
```



### controller接口

```java
@RestController
@RefreshScope //支持Nacos的动态刷新功能。
public class ConfigClientController
{
    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/config/info")
    public String getConfigInfo() {
        return configInfo;
    }
}
```







## 2、在nacos上新建配置文件

遵循bootsrap.yml中的配置文件

~~~
${spring.application.name}-${spring.profile.active}.${spring.cloud.nacos.config.file-extension}
nacos-config-client-dev.yaml
~~~

![image-20200917135117675](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917135117.png)



## 3、访问3377/config/info

![image-20200917140242937](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917140243.png)

## 4、动态刷新在nacos上修改为3：

![image-20200917140318813](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917140318.png)

成功读取



## 5、多环境项目管理-dataID方案

namespace=public，group=default_group;默认Cluster是Default

![image-20200917151145494](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917151145.png)

### a.新建测试环境

![image-20200917152146850](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917152146.png)



### b.读取多环境

修改3377中的application.yml

![image-20200917152334418](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917152334.png)



测试：

由于有热部署，会自动更新，正确

![image-20200917152404574](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917152404.png)



## 6、group方案

### a.新建nacos-config-client-info.yaml配置文件，设置组为dev_group

![image-20200917152722190](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917152722.png)

### b.新建一个配置文件，标记为TEST_group

![image-20200917152850233](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917152850.png)

![image-20200917152918857](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917152918.png)



### c.修改3377的配置文件

增加active：info

![image-20200917153106018](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917153106.png)

增加group: TEST_GROUP

![image-20200917153125351](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917153125.png)



### d.测试结果

![image-20200917153341111](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917153341.png)

![image-20200917153428976](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917153429.png)





## 7、命名空间nameSpace

新建命名空间dev和test

![image-20200917153710302](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917153710.png)



### a.增加配置到dev命名空间

![image-20200917153952026](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917153952.png)



### b.增加配置到test命名空间

![image-20200917154051946](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917154052.png)



### c.修改配置文件

![image-20200917154311390](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917154311.png)

![image-20200917154303919](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917154303.png)





### d.测试

![image-20200917154246899](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917154246.png)

![image-20200917154349888](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917154349.png)



# 六、nacos集群与持久化

默认Nacos使用嵌入式数据库实现数据的存储，所以，如果启动多个默认配置下的Nacos节点，数据存储是存在一致性问题的，为了解决这个问题，Nacos采用了==集中式存储的方式来支持集群化部署，目前只支持mysql的存储==

![image-20200917155307547](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917155307.png)

nacos自带阿帕奇的数据库derby(好像是这个)

## 1、持久化

修改nacos\conf\application.properties

~~~properties
spring.datasource.platform=mysql
### Count of DB:
db.num=1
### Connect URL of DB:
db.url.0=jdbc:mysql://127.0.0.1:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
db.user=root
db.password=123123
~~~

运行sql文件

![image-20200917160520508](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917160520.png)

添加一个配置文件：

![image-20200917160536864](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917160536.png)

![image-20200917160541685](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917160541.png)

持久化成功





# 七、nacos在linux下的集群

条件：一个nginx、三个nacos、一个mysql



## 1、修改nacos\conf\cluster.conf

![image-20200917163902253](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917163902.png)

## 2、修改为mysql，持久化





## 3.修改startup。sh

![image-20200917164849063](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917164849.png)

![image-20200917165001367](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917165001.png)



## 4、nginx启动

/usr/local/webserver/nginx是安装nginx的地址

修改配置文件

![image-20200917174650917](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917174651.png)



./nginx -c /usr/local/webserver/nginx/conf/nginx.conf



## 5、启动3台nacos

./startup.sh -z 3333 4444 5555

![image-20200917174940145](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917174940.png)







## 6.添加

![image-20200917180455549](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917180455.png)

![image-20200917180601983](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917180602.png)

![image-20200917180615220](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917180615.png)



## 7、修改9001的配置文件

![image-20200917180902184](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917180902.png)

成功注册

![image-20200917181015556](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917181015.png)



# 八、sentinel-服务熔断和限流

Sentinel: 分布式系统的流量防卫兵

https://github.com/alibaba/Sentinel/wiki/%E4%BB%8B%E7%BB%8D

**hystrix**

1.  需要我们自己手动搭建监控平台
2.  没有web界面给我们进行更加细粒度化的配置流量控制，速率控制，服务熔断，服务降级



**sentinel**

1.  是一个单独的组件
2.  直接界面化的细粒度配置
    1.  约定>配置>编码
    2.  使用配置和注解的方式，尽量少些代码





## 1、下载并运行

下载地址：https://github.com/alibaba/Sentinel

![image-20200919085637082](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919085644.png)





## 2、新建项目cloudalibaba-sentinel-service8401

### pom依赖

```xml
<dependencies>
    <!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
    <dependency>
        <groupId>com.like</groupId>
        <artifactId>cloud-api-common</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
    <!--SpringCloud ailibaba nacos -->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
    <!--SpringCloud ailibaba sentinel-datasource-nacos 后续做持久化用到-->
    <dependency>
        <groupId>com.alibaba.csp</groupId>
        <artifactId>sentinel-datasource-nacos</artifactId>
    </dependency>
    <!--SpringCloud ailibaba sentinel -->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
    </dependency>
    <!--openfeign-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
    <!-- SpringBoot整合Web组件+actuator -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <!--日常通用jar包配置-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>cn.hutool</groupId>
        <artifactId>hutool-all</artifactId>
        <version>4.6.3</version>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```



### 配置文件

```yml
server:
  port: 8401
spring:
  application:
    name: cloudalibaba-sentinel-service
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848 #Nacos服务注册中心地址
    sentinel:
      transport:
        dashboard: localhost:8080 #配置Sentinel dashboard地址
        port: 8719
      datasource:
        ds1:
          nacos:
            server-addr: localhost:8848
            dataId: cloudalibaba-sentinel-service
            groupId: DEFAULT_GROUP
            data-type: json
            rule-type: flow
management:
  endpoints:
    web:
      exposure:
        include: '*'
feign:
  sentinel:
    enabled: true # 激活Sentinel对Feign的支持
```



### 启动类

```java
@EnableDiscoveryClient
@SpringBootApplication
public class CloudAlibabaSentinel8401 {
    public static void main(String[] args) {
        SpringApplication.run(CloudAlibabaSentinel8401.class, args);
    }
}
```



### 控制器

```java
@RestController
public class FlowLimitController {
    @GetMapping("testa")
    public String testA() {
        return "testA";
    }
    @GetMapping("testb")
    public String testb() {
        return "testb";
    }
}
```







## 3、启动8401

==注意==：要调用一次8401的接口才能显示到8080上

![image-20200919091424563](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919091424.png)

![image-20200919091624471](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919091624.png)











# 九、流控规则

![image-20200919093018254](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919093018.png)

## 1、默认-qps超标直接失败

给/testa添加流控，每秒一次，超过直接失败

![image-20200919092300154](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919092300.png)

被限流

![image-20200919092213048](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919092213.png)





## 2、线程数

![image-20200919093008907](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919093008.png)





## 3、流控模式-关联

![image-20200919093308405](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919093308.png)



使用postman循环访问/testb,访问testa会失败

![image-20200919093825344](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919093825.png)













# 十、流控-预热

默认coldFactor为3，就是qps从（threshold/3）开始，经过多少预热时间逐渐升至设定的qps阈值

阈值为10，预热时间为5。

系统初始化的阈值为10/3=3，即阈值刚开始为3，经过5秒后阈值慢慢升到10

解释：刚开始只能接受每秒3次点击，经过5秒后，可以接受到每秒10次

![image-20200919121348383](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919121348.png)



# 十一、流控-排队等待

![image-20200919122128160](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919122128.png)







# 十二、降级

![image-20200919122833032](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919122833.png)

![image-20200919122621266](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919122621.png)

![image-20200919122713550](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919122713.png)









![image-20200919124538466](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919124538.png)



![image-20200919124231354](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919124231.png)







# 十三、热点规则

```java
@GetMapping("/testhot")
@SentinelResource(value = "testhot",blockHandler = "bk_testhot")
public String testhot(String a1) {

    return "testhot";
}

public String bk_testhot(String a1,BlockException e) {
    return "bk_testhot";
}
```

如果带了第一个参数请求超过了每秒一次就触发bk_testhot

![image-20200919130606356](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919130606.png)

![image-20200919130652745](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919130652.png)

![image-20200919130658344](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919130658.png)





参数例外项：如果参数值为5，那么他的阈值为每秒10次

![image-20200919131601619](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919131601.png)

![image-20200919131527185](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919131527.png)

![image-20200919131628878](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919131628.png)



# 十四、系统自适应限流

![image-20200919132502005](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919132502.png)

![image-20200919132656581](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919132656.png)



# 十五、@sentinelResouce

```java
@GetMapping("/byresource")
@SentinelResource(value = "byresource",blockHandler = "handleException")
public Result byResource() {
    return  Result.ok().message("按资源名称限流测试ok").data("payment",new Payment(1L,"like"));
}

public Result handleException(BlockException e) {
    return Result.error().message(e.getClass().getCanonicalName()+"\t 服务不可用");
}
```

![image-20200919133434592](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919133434.png)

![image-20200919133458692](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919133458.png)





## 1、自定义限流处理

自定义一个类专门处理，一定要static，

```java
public class CustomerBlockHandler {
    
    public static Result byResource(BlockException e) {
        return Result.error().message(e.getClass().getCanonicalName() + "全局限流处理1");
    }
    public static Result byResource2(BlockException e) {
        return Result.error().message(e.getClass().getCanonicalName() + "全局限流处理2");
    }
}
```

controller

```java
@GetMapping("/byresource")
@SentinelResource(value = "byresource",blockHandlerClass = CustomerBlockHandler.class,blockHandler = "byResource")
public Result byResource() {
    return  Result.ok().message("按资源名称限流测试ok").data("payment",new Payment(1L,"like"));
}
```

配置规则

![image-20200919182004837](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919182004.png)

结果

![image-20200919182101079](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919182101.png)



## 2、三个核心API

![image-20200919182655648](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919182655.png)







# 十六、修改9001和83

## 9001

![image-20200919184830100](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919184830.png)

![image-20200919184841276](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919184841.png)



## 83

```xml
<!--SpringCloud ailibaba sentinel -->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
</dependency>
```

![image-20200919184853164](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919184853.png)

![image-20200919184903679](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919184903.png)



## 测试：83调用9001和9002

![image-20200919185403866](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919185403.png)

![image-20200919185409486](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919185409.png)



输入4以后会报错，这样不友好

![image-20200919190325959](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919190326.png)





## 1、增加fallback方法

在order83中，fallback管理java运行异常

![image-20200919190836234](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919190836.png)

![image-20200919190854668](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919190854.png)



## 2、修改为blockhandle

```java
@SentinelResource(value = "fallback",blockHandler = "handlerBlock")//什么都没有配置
@GetMapping(value = "/consumer/fallback/{id}")
public Result fallback(@PathVariable long id) {
    Result res = restTemplate.getForObject(SERVICE_URL + "/" + id, Result.class, id);
    if (id == 4) {
        throw new IllegalArgumentException("非法参数异常");
    }
    return res;
}

//blockHandel sentinel服务降级
public Result handlerBlock(@PathVariable long id, Throwable throwable) {
    return Result.error().message("没有您需要查找的id,block" + throwable.getMessage());
}
```

配置服务降级规则

![image-20200919192049157](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919192049.png)

测试结果：

![image-20200919192101365](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919192101.png)



blockhandle和fallback都进行了配置，则被限流降级而抛出blockexp时会进入blockhandle处理逻辑



忽略异常

![image-20200919193901303](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200919193901.png)



# 十七、OpenFeign

83

```java
package com.lk.sc.openfeign;

@FeignClient(value = "nacos-payment-provider",fallback = HandlerFeign.class)
public interface PaymentFeign {
    @GetMapping("/{id}")
    public Result getDataById(@PathVariable String id);
}

```

```java
package com.lk.sc.openfeign;
@Component
public class HandlerFeign implements PaymentFeign {

    @Override
    public Result getDataById(String id) {
        return Result.error().message("HandlerFeign兜底");
    }
}
```

在controller中增加方法

```java
@Resource
private PaymentFeign paymentFeign;

@GetMapping("/consumer/{id}")
public Result getDataById(@PathVariable String id){
    return paymentFeign.getDataById(id);
}
```

