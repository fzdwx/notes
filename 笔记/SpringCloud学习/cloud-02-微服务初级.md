















![image-20200901204250401](C:%5CUsers%5Cpdd20%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20200901204250789.png)一、微服务注册中心-eureka

**服务治理**

spring cloud 封装的 Netflix 公司开发的 eureka 模块实现 ==服务治理==

在传统的RPC远程调用框架中，管理每个服务和服务之间的依赖关系比较复杂，所以需要服务治理



**服务注册**

![image-20200831125443965](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831125444.png)



## 服务中心

![image-20200831125635774](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831125635.png)



## 注册中心

![image-20200831125645361](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831125645.png)



## 1.Eureka7001服务器项目创建

![image-20200831133138291](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831133138.png)

### a.pom依赖

```xml
<dependencies>
    <!--自己定义的通用api-->
    <dependency>
        <groupId>com.like</groupId>
        <artifactId>cloud-api-common</artifactId>
        <version>${project.version}</version>
    </dependency>
    <!--eureka-server-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
    <!--boot web actuator-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
</dependencies>
```



### b.配置文件

```yml
server:
  port: 7001

spring:
  application:
    name: cloud-eureka-server

eureka:
  instance:
    hostname: eureka7001.com #eureka服务端的实例名称
  client:
    register-with-eureka: false     #false表示不向注册中心注册自己。
    fetch-registry: false     #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    service-url:
      #集群指向其它eureka
      #defaultZone: http://eureka7002.com:7002/eureka/
      #单机就是7001自己
      defaultZone: http://eureka7001.com:7001/eureka/
    #server:
    #关闭自我保护机制，保证不可用服务被及时踢除
    #enable-self-preservation: false
    #eviction-interval-timer-in-ms: 2000
```



### c.启动类

```java
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaServer
public class CloudEureka7001 {
    public static void main(String[] args) {
        SpringApplication.run(CloudEureka7001.class,args);
    }
}
```



## 2.将payment和order注册到eureka服务器上去

### 1.引入erueka.client依赖

```xml
<!--eureka-client-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

### 2.添加配置文件

```yml
eureka:
  client:
    #表示是否将自己注册进EurekaServer默认为true。
    register-with-eureka: true
    #是否从EurekaServer抓取已有的注册信息，默认为true。单节点无所谓，集群必须设置为true才能配合ribbon使用负载均衡
    fetchRegistry: true
    service-url:
      #单机版
      defaultZone: http://localhost:7001/eureka
      # 集群版
      #defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
  instance:
    instance-id: payment8001
    #访问路径可以显示IP地址
    prefer-ip-address: true
    #Eureka客户端向服务端发送心跳的时间间隔，单位为秒(默认是30秒)
    #lease-renewal-interval-in-seconds: 1
    #Eureka服务端在收到最后一次心跳后等待时间上限，单位为秒(默认是90秒)，超时将剔除服务
    #lease-expiration-duration-in-seconds: 2
```

### 3.在启动类上添加注解

```java
@EnableEurekaClient
```



### 4.order同上



### 5.测试

![image-20200831133639206](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831133639.png)





## 3.搭建eureka集群

![image-20200831140609159](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831140609.png)

![image-20200831140749443](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831140749.png)



### a.创建Eruekaserver7002项目

#### 	1.pom依赖和7001一样

#### 	2.配置文件

```yml
server:
  port: 7002

spring:
  application:
    name: cloud-eureka-server

eureka:
  instance:
    hostname: eureka7002.com #eureka服务端的实例名称
  client:
    register-with-eureka: false     #false表示不向注册中心注册自己。
    fetch-registry: false     #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    service-url:
      #集群指向其它eureka
      #defaultZone: http://eureka7002.com:7002/eureka/
      #单机就是7001自己
      defaultZone: http://eureka7001.com:7001/eureka/
    #server:
    #关闭自我保护机制，保证不可用服务被及时踢除
    #enable-self-preservation: false
    #eviction-interval-timer-in-ms: 2000
```

#### 3.启动类

同7001



#### 4.修改hosts文件

![image-20200831143303035](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831143303.png)

#### 5.测试

![image-20200831143319553](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831143319.png)![image-20200831143326450](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831143326.png)



### b.把payment和order注册到eureka上面去

把appliation.yml中的eureka.client.service.url.defaultZone修改为

```yml
defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
```



测试：

![image-20200831143551642](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831143551.png)

![image-20200831143559843](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831143559.png)





![image-20200831143610824](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831143610.png)



## 4.搭建payment集群

### 	创建payment8002项目

*   复制payment8001，修改端口号为8002

*   在order项目中RestTemplateConfig的getRestTemplate方法上加注解

    ```java
    @Configuration
    public class RestTemplateConfig {
    
        @Bean
        @LoadBalanced
        public RestTemplate getRestTemplate() {
            return new RestTemplate();
        }
    }
    ```

*   在payment8001和payment8002项目的controller中加入serverPort字段

    完成请求时，一起返回这个字段

    ```java
    @Value("${server.port}")
    String serverPort;
    ```

*   把OrderController中的payment_url修改为微服务的名字

    ```java
    @RestController
    @RequestMapping("/consumer")
    public class OrderController {
        @Autowired
        RestTemplate restTemplate;
    
        //public static final String PAYMENT_URL = "http://localhost:8001";
        public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
        
        ···
    ```



### 测试：

![image-20200831150527847](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831150527.png)

![image-20200831150541175](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831150541.png)





### ==总结==

不加 @LoadBalanced这个注解在RestTemplate会报错，加了就会轮询访问- ==ribbon的负载均衡==

ribbon和eureka整合后，consumer可以直接调用服务，而不用关心地址和端口号，并实现了负载均衡的功能





## 5.服务发现 Discovery	

对于注册eureka里面的微服务，可以通过服务发现来获得该服务的信息



1.  在启动类上添加==@EnableDiscoveryClient==注解

2.  在paymentController中注入

    ```java
    @Resource
    DiscoveryClient discoveryClient;
    ```

3.  添加方法

    ```java
    @GetMapping("/discovery")
    public Object getDiscoveryInfo() {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("*****element: " + element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }
        return this.discoveryClient;
    }
    ```

![](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831160306.png)





## 6.eureka的自我保护

某时刻一个微服务不能用了，eureka不会立刻清理，依旧会对该微服务的信息进行保存，属于cap里面的ap分支

![image-20200831160346958](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831160347.png)



![image-20200831160818482](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831160818.png)

![image-20200831160902693](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831160902.png)



### 关闭自我保护



eureka7001

```yml
eureka:
  instance:
    hostname: eureka7001.com #eureka服务端的实例名称
  client:
    register-with-eureka: false     #false表示不向注册中心注册自己。
    fetch-registry: false     #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    service-url:
      #集群指向其它eureka
      defaultZone: http://eureka7002.com:7002/eureka/
      #单机就是7001自己
      #defaultZone: http://eureka7001.com:7001/eureka/
    #server:
    #关闭自我保护机制，保证不可用服务被及时踢除
    #enable-self-preservation: false
    #eviction-interval-timer-in-ms: 2000
```



payment8001

```yml
eureka:
  client:
    #表示是否将自己注册进EurekaServer默认为true。
    register-with-eureka: true
    #是否从EurekaServer抓取已有的注册信息，默认为true。单节点无所谓，集群必须设置为true才能配合ribbon使用负载均衡
    fetchRegistry: true
    service-url:
      #单机版
      #defaultZone: http://localhost:7001/eureka
      # 集群版
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
  instance:
    instance-id: payment8001
    #访问路径可以显示IP地址
    prefer-ip-address: true
    #Eureka客户端向服务端发送心跳的时间间隔，单位为秒(默认是30秒)
    #lease-renewal-interval-in-seconds: 1
    #Eureka服务端在收到最后一次心跳后等待时间上限，单位为秒(默认是90秒)，超时将剔除服务
    #lease-expiration-duration-in-seconds: 2
```



## 7. zookeeper

zookeeper是一个分布式协调工具，可以实现注册中心功能

1.payment8004项目创建

### 1.pom依赖

~~~xml
<dependencies>
        <!-- SpringBoot整合Web组件 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency><!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
            <groupId>com.atguigu.springcloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
        <!-- SpringBoot整合zookeeper客户端 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
            <!--先排除自带的zookeeper3.5.3-->
            <exclusions>
                <exclusion>
                    <groupId>org.apache.zookeeper</groupId>
                    <artifactId>zookeeper</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <!--添加zookeeper3.4.9版本-->
        <dependency>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
            <version>3.4.9</version>
        </dependency>
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
~~~



### 2.配置文件.yml

![image-20200831164341271](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831164341.png)



### 3.启动类

### 4.controller

![image-20200831164626891](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831164627.png)





# 二、服务调用-Ribbon

**概述：**

spring cloud ribbon 是一套实现负载均衡的工具，主要功能提供==客户端软件负载均衡算法和服务调用==，ribbon客户端组件提供一系列完善的配置项如连接超时，重试。简单来说就是在配置文件中列出==load banlancer(LB)==后面所有的机器，==ribbon会自动帮助你基于某种规则(简单轮询，随机连接)==去连接这些机器，	

## **1.作用：**

负载均衡+RestTemplate调用

![image-20200831171141144](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831171141.png)



## **2.工作方式：**

*   先选择EurekaServer，优先选择在同一个区域内负载较少的server
*   根据用户指定的策略，从sercer取到的服务注册列表中选择一个地址
*   轮询，随机，根据响应时间加权





## 3.irule

==各种规则：==

![image-20200831182916054](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831182916.png)



![image-20200831191528654](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831191528.png)





### a.切换访问规则

![image-20200831185733643](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831185733.png)

```java
@Configuration
public class MyRule {
    /**
     * 定义为随机规则  new RandomRule()
     * @return
     */
    @Bean
    public IRule getIRule() {
        return new RandomRule();
    }
}
```



>   在启动类上添加注解

​	@RibbonClient(name = "CLOUD-PAYMENT-SERVICE",configuration = MyRule.class)

![image-20200831185813444](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831185813.png)



==注意：==

自定义访问规则类不能和启动类在同一个包里面





## 4.负载均衡算法

### a.轮询算法原理

rest接口第几次请求数%服务器集群总数量 = 实际调用服务器位置下表，每次服务重启后rest接口计数从1开始

核心：取余算法

![image-20200831191103550](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831191103.png)



### b.手写一个轮询算法

#### 1.在restTemplate中注释注解

![image-20200831202537885](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200831202537.png)



#### 2.定义接口

```java
public interface LoadBalancer {

    ServiceInstance instances(List<ServiceInstance> serviceInstances);
}
```



#### 3.实现接口

>   ==int getAndIncrement() ：==
>
>   目的next +1 
>
>   ==ServiceInstance instances(List<ServiceInstance> serviceInstances);==
>
>   取余算法，取出要执行的service实例

```java
@Component
public class MyLoadBalancer implements LoadBalancer {

    private  AtomicInteger atomicInteger = new AtomicInteger(0);
    public final int getAndIncrement() {
        int curr;
        int next;
        do {
            curr = this.atomicInteger.get();
            next = curr >= Integer.MAX_VALUE ? 0 : curr + 1;
        } while (!this.atomicInteger.compareAndSet(curr, next));
        System.out.println("第几次访问，次数：" + next);
        return next;
    }

    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        int index = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}
```



4.controller实现方法

```java
@GetMapping("/payment/lb")
public String getPaymentLb() {
    List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
    //检查是否为空
    if (instances == null || instances.size() == 0) {
        return null;
    }
    ServiceInstance serviceInstance = loadBalancer.instances(instances);
    URI uri = serviceInstance.getUri();
    return restTemplate.getForObject(uri  + "/payment/lb", String.class);
}
```



# 三、服务调用-OpenFeign

是一个声明式的WebService客户端，使用Feign能让编写webservice客户端更简单，使用方法是：==定义一个服务接口然后在上面添加注解==。也支持可插拔式的编码器和解码器。springcloud对feign进行了封装，使其支持了springmvc标准注解库和HttpMessageConverters。可以和Eureka和Ribbon组合使用以支持负载均衡

![image-20200901173035128](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200901173042.png)

​	

## 1.order引入OpenFeign

### 项目结构

![image-20200901193113332](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200901193113.png)

### 1.pom依赖

在原来的order80基础上加入

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```



### 2.yml配置文件

```yaml
server:
  port: 80
spring:
  application:
    name: CLOUD-ORDER-SERVICE

eureka:
  instance:
    instance-id: orderfeign80
  client:
    #表示是否将自己注册进EurekaServer默认为true。
    #register-with-eureka: true
    #是否从EurekaServer抓取已有的注册信息，默认为true。单节点无所谓，集群必须设置为true才能配合ribbon使用负载均衡
    #fetchRegistry: true
    service-url:
      #单机版
      #defaultZone: http://localhost:7001/eureka
      # 集群版
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
```



### 3.启动类

```java
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients
public class CloudOpenFeignOrder80 {
    public static void main(String[] args) {
        SpringApplication.run(CloudOpenFeignOrder80.class, args);
    }
}
```



### 4.openfeign层

```java
@Component
@FeignClient("CLOUD-PAYMENT-SERVICE")
@RequestMapping("/payment")
public interface PaymentFeignService {
    /**
     * 远程调用payment服务的getById方法
     * @param id 需要查询的payment对象
     * @return Result
     */
    @GetMapping("/get/{id}")
    public Result getById(@PathVariable String id);

    /**
     * 远程调用payment服务的save方法
     * @param payment 需要保存的payment对象
     * @return Result
     */
    @PostMapping("/save")
    public Result save(@RequestBody Payment payment);
}
```



### 5.controller层

```java
@RequestMapping("/consumer")
@RestController
@Slf4j
public class OrderController {
    @Autowired
    private PaymentFeignService paymentFeignService;

    @GetMapping("/get/{id}")
    public Result getById(@PathVariable String id){
        return paymentFeignService.getById(id);
    }

    /**
     * 远程调用payment服务的save方法
     *
     * @param payment 需要保存的payment对象
     * @return Result
     */
    @PostMapping("/save")
    public Result save(@RequestBody Payment payment){
        return paymentFeignService.save(payment);
    }
}
```



## 2.openfeign的超时控制

## 	背景

Feign客户端默认等待一秒钟，但是如果服务端处理需要超过一秒钟，导致Feign不想等了就会直接返回报错，为了避免这种情况，所以我们需要设置Feign客户端的超时控制

在yml中添加配置

```
ribbon:
  ConnectTimeout: 500
  ReadTimeout: 5000
```



## 3.日志打印功能

![image-20200901194240933](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200901194241.png)



### 级别

![image-20200901194307025](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200901194307.png)



### 配置

在yml文件中添加

```yaml
logging:
  level:
    #fegin监控的哪个端口，什么级别
    com.lk.sc.fegin.PaymentFeignService: debug
```



添加配置类



```java
package com.lk.sc.config;
@Configuration
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
```





# 三、服务降级-hystrix

**原因**

多个微服务之间调用的时候，假设A调用B和C，B和C有调用 其他的微服务，这就是所谓额“==扇出==”。如果扇出的链路上某个微服务的调用响应时间过长或者不可用，对微服务A的调用就会占用越来越多的系统资源，进而引起系统崩溃，这就是“==雪崩效应==”

对于高流量的应用，单一个后端依赖可能会导致所有服务器上的所有资源都在几秒钟内饱和，这可能导致服务之间的延迟增加，备份队列，线程和其他系统资源紧张，导致整个系统发生更多的级联故障

**解决**

hystrix是一个用于处理分布式系统的==延迟==和==容错==的开源库，在分布式系统里，许多依赖不可避免的会调用失败，比如超时，异常。hystrix==能保证在一个依赖出问题的情况下，不会导致整体服务器失败，避免级联故障，以提高分布式系统的高可用==

==断路器==本身是一种开关响应，当某个服务单元故障后，通过==断路器的故障监控==，==向调用方返回一个符合预期的，可处理的备选响应(fallback)，而不是长时间的等待或者抛出异常==





## 服务降级

==向调用方返回一个符合预期的，可处理的备选响应(fallback)，而不是长时间的等待或者抛出异常==

**触发情况**

*   程序运行异常
*   超时
*   服务熔断触发服务降级
*   线程池/信号量 打满导致服务降级





## 服务熔断

==保险师达到最大服务访问后，直接拒绝访问，拉闸限电，然后调用服务降级的方法返回友好提示==  — ==保险丝跳闸==



## 服务限流

==秒杀高并发等操作，严禁一窝蜂的过来拥挤，大家排队，一秒钟N个,有序进行==



## 1.hystrix-payment8001项目构建

![image-20200902163423819](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200902163423.png)

**pom依赖**

在payment8001的基础上加入

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```



**application.yml**

和payment8001相同



**service**

```java
@Service
public class PaymentService {

    public String ok(int id) {
        return "线程池:  " + Thread.currentThread().getName() + "ok  id:  " + id + "\t" + "O(∩_∩)O哈哈~";
    }

    public String timeOut(int id) {
        //int age = 10/0;
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池:  " + Thread.currentThread().getName() + " timeOut  id:  " + id + "\t" + "O(∩_∩)O哈哈~" + "  耗时(秒): ";
    }
}
```



**controller**

```java
@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;
    @GetMapping("/payment/hystrix/ok/{id}")
    public String ok(@PathVariable("id") Integer id) {
        String result = paymentService.ok(id);
        log.info("*****result: " + result);
        return result;
    }
    @GetMapping("/payment/hystrix/timeout/{id}")
    public String timeOut(@PathVariable("id") Integer id) {
        String result = paymentService.timeOut(id);
        log.info("*****result: " + result);
        return result;
    }
}
```



**启动类**

```java
@SpringBootApplication
@EnableEurekaClient
public class CloudPaymentHsytrix8001 {
    public static void main(String[] args) {
        SpringApplication.run(CloudPaymentHsytrix8001.class, args);
    }
}
```

## 2.openfegin-hsytrix-order80项目构建

![image-20200902164425687](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200902164425.png)

pom依赖

加入

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

application.yml

```yaml
server:
  port: 80

eureka:
  client:
    register-with-eureka: false
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/

feign:
  hystrix:
    enabled: false
ribbon:
  ConnectTimeout: 500
  ReadTimeout: 5000
```



启动类

```java
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients
public class CloudOpenFeignHsytrixOrder80 {
    public static void main(String[] args) {
        SpringApplication.run(CloudOpenFeignHsytrixOrder80.class, args);
    }
}
```



feign

```java
@Component
@FeignClient(value = "CLOUD-PAYMENT-HYSTRIX-SERVICE")
public interface PaymentOpenFeign {
    @GetMapping("/payment/hystrix/ok/{id}")
    public String ok(@PathVariable("id") Integer id);
    @GetMapping("/payment/hystrix/timeout/{id}")
    public String timeOut(@PathVariable("id") Integer id);
}
```



congtroller

```java
@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Resource
    private PaymentOpenFeign paymentOpenFeign;
    @GetMapping("/payment/hystrix/ok/{id}")
    public String ok(@PathVariable("id") Integer id) {
        return paymentOpenFeign.ok(id);
    }
    @GetMapping("/payment/hystrix/timeout/{id}")
    public String timeOut(@PathVariable("id") Integer id) {
        return paymentOpenFeign.timeOut(id);
    }
}
```







## 3.解决要求

*   超时导致服务器访问变慢
    *   超时不用等待
*   出错(宕机或程序运行出错)
    *   出错兜底
*   解决
    *   对方8001超时了，调用者80不能一直卡死等待，必须有服务降级
    *   对方8001down机了，调用者80不能一直卡斯等待，必须有服务降级
    *   对方8001ok，调用者80自己出了故障或有自我要求（自己的等待时间小于服务器提供者），自己降级



## 4.服务降级配置

### cloud-provider-hystrix-payment8001模块

1.在启动类上添加注解

```java
@EnableCircuitBreaker
```



2.在需要服务降级的方法上

```java
@HystrixCommand(fallbackMethod = "timeOutHandle",commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
})
public String timeOut(int id) {
    //超时时间
    int time = 5;
    //int age = 10/0;
    try {
        TimeUnit.SECONDS.sleep(time);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    return "线程池:  " + Thread.currentThread().getName() + " timeOut  id:  " + id + "\t" + "O(∩_∩)O哈哈~" + "  耗时(秒): "+time;
}

public String timeOutHandle(int id) {
    return "线程池:  " + Thread.currentThread().getName() + " timeOutHandle  系统繁忙 id:  " + id + "\t" + "handle方法";
}
```







### cloud-consumer-openfegin-hsytrix-order80模块

配置fallback，通常放在客户端



1.在启动类上添加注释

```java
@EnableHystrix
```

2.配置文件

![image-20200902172413213](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200902172413.png)

3.controller

```java
@GetMapping("/payment/hystrix/timeout/{id}")
@HystrixCommand(fallbackMethod = "timeOutHandle", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
})
public String timeOut(@PathVariable("id") Integer id) {
    int age = 10 / 0;
    return paymentOpenFeign.timeOut(id);
}

public String timeOutHandle(Integer id) {
    return "我是消费者80,对方支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己,o(╥﹏╥)o";
}
```





### 1.当前问题

*   每个方法都需要有一个兜底方法，代码膨胀
*   统一和自定义的分开



### 2.解决

在feign接口上添加注解==@DefaultProperties(defualtFallback = “”)==

*   除了个别重要的核心业务专属，其他普通的可以通过这个注解统一跳转到统一处理结果



1.在类上添加注解

```java
@DefaultProperties(defaultFallback = "globalFallback")
```

![image-20200902173231350](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200902173231.png)



2.在需要服务降级的方法上添加注解

```java
@HystrixCommand
```

![image-20200902173307026](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200902173307.png)



3.全局服务降级方法	

仅对当前类有效

```java
//全局fallback方法
public String globalFallback() {
    return "Global异常处理信息，请稍后再试，/(ㄒoㄒ)/~~";
}
```







## 5.服务降级feign

![image-20200902173815222](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200902173815.png)

![image-20200902173927089](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200902173927.png)



### 1.写一个类实现PaymentOpenFeign

```java
@Component
public class PaymentOpenFeignFallback  implements PaymentOpenFeign {

    @Override
    public String ok(Integer id) {
        return "ok 的fallback";
    }

    @Override
    public String timeOut(Integer id) {
        return "timeOut 的fallback";
    }
}
```



### 2.修改PaymentOpenFeign上的注解

添加fallback字段

![image-20200902175734259](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200902175734.png)





### 3.修改yml文件

![image-20200902175807282](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200902175807.png)





### 4.注释掉controller里面关于服务降级的注解

![image-20200902175829205](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200902175829.png)





### 5.测试

![image-20200902175849564](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200902175849.png)









## 6.服务熔断

==服务降级->熔断->恢复调用链路==

![image-20200902180747523](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200902180747.png)
			服务熔断：当下游的服务因为某种原因突然**变得不可用**或**响应过慢**，上游服务为了保证自己整体服务的可用性，不再继续调用目标服务，直接返回，快速释放资源。如果目标服务情况好转则恢复调用。
需要说明的是熔断其实是一个框架级的处理，那么这套熔断机制的设计，基本上业内用的是`断路器模式`，如`Martin Fowler`提供的状态转换图如下所示



![image-20200902181223454](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200902181223.png)



-   最开始处于`closed`状态，一旦检测到错误到达一定阈值，便转为`open`状态；
-   这时候会有个 reset timeout，到了这个时间了，会转移到`half open`状态；
-   尝试放行一部分请求到后端，一旦检测成功便回归到`closed`状态，即恢复服务；

在Hystrix中，对应配置如下

```java
//滑动窗口的大小，默认为20
circuitBreaker.requestVolumeThreshold 
//过多长时间，熔断器再次检测是否开启，默认为5000，即5s钟
circuitBreaker.sleepWindowInMilliseconds 
//错误率，默认50%
circuitBreaker.errorThresholdPercentage
```

每当20个请求中，有50%失败时，熔断器就会打开，此时再调用此服务，将会直接返回失败，不再调远程服务。直到5s钟之后，重新检测该触发条件，判断是否把熔断器关闭，或者继续打开。

这些属于框架层级的实现，我们只要实现对应接口就好！







### 为cloud-provider-hystrix-payment8001添加服务熔断

#### 1.在paymentservice中添加方法



```java
//=====服务熔断
@HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
        @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),// 是否开启断路器
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),// 请求次数
        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), // 时间窗口期
        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),// 失败率达到多少后跳闸
})
public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
    if (id < 0) {
        throw new RuntimeException("******id 不能负数");
    }
    String serialNumber = IdUtil.simpleUUID();

    return Thread.currentThread().getName() + "\t" + "调用成功，流水号: " + serialNumber;
}

public String paymentCircuitBreaker_fallback(Integer id) {
    return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " + id;
}
```



#### 2.在controller中添加方法



```java
//====服务熔断
@GetMapping("/payment/circuit/{id}")
public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
    String result = paymentService.paymentCircuitBreaker(id);
    log.info("****result: " + result);
    return result;
}
```



#### 3.测试：

连续多次访问错误的地址

http://localhost:8001/payment/circuit/-1

![image-20200902191750070](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200902191750.png)



访问正确的

刚开始访问也会出现错误的

![image-20200902191816093](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200902191816.png)

过几秒后

![image-20200902191832699](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200902191832.png)





#### 4.结论

![image-20200902191950029](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200902191950.png)



# 四、服务网关-gateway

==zuul -> gateway==

提供一种简单而有效的方式对API进行路由，以及提供一些强大的过滤器功能，例如：熔断，限流，重试等。==Spring Cloud Gateway是基于WebFlux框架实现的，而WebFlux框架底层使用了高性能的Reactor通信框架netty==；

提供统一的路由方式且基于==Filter==链的方式提供了网关基本的功能；例如：安全，监控/指标，限流	



![image-20200903175954585](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200903175954.png)



## 1.特性

-   动态路由：能匹配任何请求属性
-   可以对路由指定==Predicate(断言)==和==Filter(过滤器)==
-   集成了Hystrix的断路器功能
-   集成了SpringCloud服务发现功能 discover
-   易于编写的Predicate(断言)和Filter(过滤器)
-   请求限流功能

-   支持路径重写







## 2.三大核心概念

>   route 路由

路由是构建网关的基本模块，由==ID==， ==目标ur==i 一系列的断言和过滤器组成，如果断言为true则匹配该路由



>   predicate 断言

参考的是java.util.funciton.Predicate

开发人员可以匹配http请求中的所有内容（比如请求头和请求参数）==如果请求和断言相匹配则进行路由==



>   filter 过滤器

指的是==gatewayFilter==的实例，==使用过滤器可以在请求被路由前或者后对请求修改==





>   总结

一个web请求，通过一些匹配条件，定位到真正的服务节点，并在这个转发过程的前后，进行一些精细化控制，==predicate就是我们的匹配条件==，而==filter，可以理解为一个无所不能的拦截器==。有了这两个元素，在加上目标uri，就可以实现一个具体的路由







## 3.创建gateway9527项目

a.依赖

```xml
<!--gateway-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
<!--eureka-client-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
<dependency>
    <groupId>com.like</groupId>
    <artifactId>cloud-api-common</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
<!--一般基础配置类-->
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
```





b.application.yml配置

```yaml
server:
  port: 9527

spring:
  application:
    name: cloud-gateway
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true #开启从注册中心动态创建路由的功能，利用微服务名进行路由
      routes:
        - id: payment_routh #payment_route    #路由的ID，没有固定规则但要求唯一，建议配合服务名
          uri: http://localhost:8001          #匹配后提供服务的路由地址
          #uri: lb://cloud-payment-service #匹配后提供服务的路由地址
          predicates:
            - Path=/payment/get/**         # 断言，路径相匹配的进行路由

        - id: payment_routh2 #payment_route    #路由的ID，没有固定规则但要求唯一，建议配合服务名
          uri: http://localhost:8001          #匹配后提供服务的路由地址
          #uri: lb://cloud-payment-service #匹配后提供服务的路由地址
          predicates:
            - Path=/payment/lb/**         # 断言，路径相匹配的进行路由
            #- After=2020-02-21T15:51:37.485+08:00[Asia/Shanghai]
            #- Cookie=username,zzyy
            #- Header=X-Request-Id, \d+  # 请求头要有X-Request-Id属性并且值为整数的正则表达式

eureka:
  instance:
    hostname: cloud-gateway-service
  client: #服务提供者provider注册进eureka服务列表内
    service-url:
      register-with-eureka: true
      fetch-registry: true
      defaultZone: http://eureka7001.com:7001/eureka
```



c.启动类

```java
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
public class CloudGateway9527 {
    public static void main(String[] args) {
        SpringApplication.run(CloudGateway9527.class, args);
    }
}
```





d.测试

![image-20200903184317152](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200903184317.png)



![image-20200903184330818](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200903184330.png)





## 4.gateway配置路由的2种方式

使用spring boot 配置类的方式：

```java
@Configuration
public class GateWayConfig {

    /**
     *  路由映射
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();

        routes.route("path_route_atguigu",
                     r -> r.path("/guonei")
                     .uri("http://news.baidu.com/guonei")).build();

        return routes.build();
    }

    @Bean
    public RouteLocator customRouteLocator2(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("baidu_news_mil",  
                     s -> s.path("/mil")
                     .uri("http://news.baidu.com/mil")).build();
        return routes.build();
    }
}
```







## 5.配置动态路由

默认情况下gateway会根据注册中心注册的服务列表，以注册中心上的微服务名为路径的创建==动态路由进行转发，从而实现动态路由的功能==



修改application.yml文件

```yaml
spring:
  application:
    name: cloud-gateway
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true #开启从注册中心动态创建路由的功能，利用微服务名进行路由
      routes:
        - id: payment_routh #payment_route    #路由的ID，没有固定规则但要求唯一，建议配合服务名
          uri: lb://CLOUD-PAYMENT-SERVICE #匹配后提供服务的路由地址
          predicates:
            - Path=/payment/get/**         # 断言，路径相匹配的进行路由

        - id: payment_routh2 #payment_route    #路由的ID，没有固定规则但要求唯一，建议配合服务名
          uri: lb://CLOUD-PAYMENT-SERVICE #匹配后提供服务的路由地址
          predicates:
            - Path=/payment/lb/**         # 断言，路径相匹配的进行路由
```





测试

![image-20200903191113505](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200903191113.png)

![image-20200903191105886](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200903191105.png)

**结果**

8001和8002会来回调用





## 6.predicate

gateway创建Route对象时，使用routePredicateaFctroy 创建 predicate 对象，predicate 对象可以赋值给Route。gateway内置了许多routePredicateaFctroy

![image-20200903191259698](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200903191259.png)



```yaml
predicates:
  - Path=/payment/lb/**         # 断言，路径相匹配的进行路由
  #- After=2020-02-21T15:51:37.485+08:00[Asia/Shanghai]
  #- Cookie=username,zzyy
  #- Header=X-Request-Id, \d+  # 请求头要有X-Request-Id属性并且值为整数的正则表达式
```



## 7.filter

用法：

token鉴权

>   自定义filter

```java
@Configuration
@Slf4j
public class GatewayFilter  implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String name = exchange.getRequest().getQueryParams().getFirst("name");
        if (name == null) {
            log.info("用户名为空");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
```



>   测试

失败：

![image-20200903193826417](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200903193826.png)

![image-20200903193831196](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200903193831.png)





正确

![image-20200903193903147](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200903193903.png)







# 五、服务配置-config

## 1、简介

**分布式配置中心**

微服务就是将很多业务拆分成一个个子服务，而每一个服务都需要有一个application.yml(pro)的配置文件，所以一个集中式的，动态的配置管理设施是必不可少的

![image-20200916183315737](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200916183315.png)



**服务端**

分布式配置中心，是一个独立的微服务应用，用来连接配置服务器并为客户端提供获取配置信息，加密/解密信息的访问接口

**客户端**

通过指定的配置中心来管理应用资源，以及业务相关的配置内容，并在启动的似乎还从配置中心获取和加载配置信息，配置服务器默认采用git存储配置信息







## 2、作用

1.  集中管理配置文件
2.  不同环境不同配置
3.  动态化的配置更新，分环境部署，比如dev、test、prod、bate、release
4.  运行期间动态调整配置，不需要在每个服部署的机器上编写配置文件，服务会像配置中心统一拉去自己的信息
5.  当配置发生变化时，服务不需要重启
6.  将配置信息以rest接口的信息暴露



## 3、config服务端配置

新建项目cloud-config-center-3344

### 依赖：

```xml
<dependencies>
    <!--添加消息总线RabbitMQ支持-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-bus-amqp</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-config-server</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
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

```yml
server:
  port: 3344

spring:
  application:
    name:  cloud-config-center #注册进Eureka服务器的微服务名
  cloud:
    config:
      server:
        git:
          uri: git@github.com:likedeke/SpringCloud.git #GitHub上面的git仓库名字
          ####搜索目录
          search-paths:
            - SpringCloud
      ####读取分支
      label: master
```



### 启动类

```java
@SpringBootApplication
@EnableConfigServer
public class CloudConfigCenter3344 {
    public static void main(String[] args) {
        SpringApplication.run(CloudConfigCenter3344.class, args);
    }
}
```



### 修改hosts映射

127.0.0.1  config-3344.com







### 测试

在https://github.com/likedeke/SpringCloud中新建一个文件叫做config-dev.yml

报错：reject HostKey: github.com

修改配置文件

![image-20200916190947155](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200916190947.png)

访问成功

![image-20200916191554693](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200916191554.png)

命名规范

![image-20200916191301905](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200916191302.png)





## 4、客户端访问ConfigCenter

新建项目：cloud-config-client-3355



### 依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

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
```



### 配置文件

使用bootstrap.yml

**原因**

bootstrap.yml是系统级的，优先级更高，springcloud会创建一个 ==boorstrap context==，作为`application context`的==父上下文==。初始化的时候 ==boorstrap context==负责从外部源加载配置属性并解析配置，这两个上下文共享一个从外部获取的==environment==

![image-20200916192202767](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200916192202.png)

```
server:
  port: 3355

spring:
  application:
    name: config-client
  cloud:
    #Config客户端配置
    config:
      label: master #分支名称
      name: config #配置文件名称
      profile: dev #读取后缀名称   上述3个综合：master分支上config-dev.yml的配置文件被读取http://config-3344.com:3344/master/config-dev.yml
      uri: http://localhost:3344 #配置中心地址k

  #rabbitmq相关配置 15672是Web管理界面的端口；5672是MQ访问的端口
  #rabbitmq:
    #host: localhost
    #port: 5672
    #username: guest
    #password: guest

#服务注册到eureka地址
eureka:
  client:
    service-url:
      defaultZone: http://localhost:7001/eureka
# 暴露监控端点
#management:
  #endpoints:
    #web:
      #exposure:
        #include: "*"
```





### 启动类

```java
@SpringBootApplication
@EnableEurekaClient
public class CloudConfigClient3355 {
    public static void main(String[] args) {
        SpringApplication.run(CloudConfigClient3355.class, args);
    }
}
```





### 控制层 

测试controller

```java
@RestController
public class HelloController {
    @Value("${config.info}")
    private String configInfo;
    @GetMapping("/configinfo")
    public String getConfigInfo() {
        return  configInfo;
    }
}
```





### 测试

成功读取到了配置中心的配置文件

![image-20200916193410784](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200916193410.png)







## 5、动态更新配置文件中的内容

修改配置文件中的内容，客户端马上就可以访问到

### 引入actuator监控

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```



### 修改配置文件

```yml
management:
  endpoints:
    web:
      exposure:
        include: "*"
```





### 在HelloController中添加注解

```
@RefreshScope
```





### 测试

重启3355

![image-20200916194741980](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200916194742.png)

修改为3

![image-20200916194814145](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200916194814.png)

3344上

![image-20200916194908320](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200916194908.png)

3355

![image-20200916194921448](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200916194921.png)

并没有修改



### 解决

使用post请求动态刷新

![image-20200916195153104](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200916195153.png)

再次访问

![image-20200916195206895](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200916195206.png)





# 六、消息总线-bus

bus配合config可以实现配置的动态刷新,一个分布式执行器，用于广播状态更改，事件推送的，也可以当做微服务间的通信通道

## 1、什么是总线

在微服务架构的系统中，通常会使用轻量级的==消息代理来构建一个公用的公共的消息主题，并让系统中所有微服务实例都连接上来==。由于该主题中==产生的消息会被所有实例监听和消费==，所以称他为消息总线。在总线上的各个实例都可以方便的广播一些需要让其他连接在这个上面的实例都知道的消息

**基本原理**

configClient实例都监听MQ中一个topic（默认是springCloudBus）。当一个服务刷新数据的时候，他会把这个信息放入topic中，这样其他监听同一个topic的服务就能得到通知，然后去更新自身的配置



![](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200916200408.png)

![](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200916195917.png)



## 2、docker安装rabbitmq

>   安装

docker pull rabbitmq:3.7.7-management

>   启动

docker run -d --hostname localhost --name myrabbit -p 15672:15672 -p 5672:5672 rabbitmq:3.7.7-management

```bash
docker exec -it 63ac2bb1bda8 bash
rabbitmqctl add_user like like
rabbitmqctl set_permissions -p / like ".*" ".*" ".*"
rabbitmqctl set_user_tags like administrator
```

>   访问：

ip地址:15672





## 3、bus设计思想

1.  利用消息总线触发一个客户端/bus/refresh,而刷新所有客户端的配置

2.  利用消息总线触发一个服务端ConfigServer的/bus/refresh端点，而刷新所有客户端的配置

    使用第二种

![image-20200916205240513](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200916205240.png)



## 4、添加依赖

3344 55 66

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
```

## 5、修改配置文件

3344 55 66

![image-20200916210749458](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200916210749.png)





## 6、问题

不知道为什么报错，在网上找了很久没有找到问题

Error creating bean with name 'rabbitMessageChannelBinder'















# 七、消息驱动-stream

消息中间件有很多，比如activemq，rabbitmq，rocketmq，kafka等。消息驱动就能屏蔽底层消息中间件的差异，降低切换成本，统一消息的编程模型

![image-20200917104836585](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917104843.png)

==通过定义绑定器，完美的实现了应用程序和消息中间件细节之间的隔离==，通过向应用程序暴露统一的Channel通道，使得应用程序不需要在考虑各种不同的消息中间件的实现

![image-20200917110618278](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917110618.png)





## 1、常用Api和注解

![image-20200917111227104](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917111227.png)





## 2、消息提供者-8801











# stream和bus因为是rabbitmq相关现在完成不了学完在来



# 八、链路监控-sleuth

![image-20200917114433952](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200917114434.png)







# 