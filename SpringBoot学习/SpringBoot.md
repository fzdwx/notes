[toc]

# 一、Spirng Boot 入门

## 1.springBoot：

>   1.简化spring应用开发的框架
>
>   2.是spring技术栈的一个整合
>
>   3.j2ee开发的一站式解决方案



## 2.微服务：

>   架构风格，服务微型化





## 3.依赖



~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.3.2.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.lk</groupId>
	<artifactId>SpringBoot</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>SpringBoot</name>
	<description>Demo project for Spring Boot</description>

	<properties>
		<java.version>1.8</java.version>
	</properties>
	<!--web依赖，tomcat，dispatcherServlet，xml-->
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
	<!--spring的单元测试-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
			<exclusions>
				<exclusion>
					<groupId>org.junit.vintage</groupId>
					<artifactId>junit-vintage-engine</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
	</dependencies>
	<!--打包插件-->
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
~~~

## 4.第一个程序：



~~~java
public class HelloworldApplication {
	public static void main(String[] args) {
        //启动程序
		SpringApplication.run(HelloworldApplication.class, args);
	}
}
~~~



~~~java
@RestController
public class HellController {
    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
}
~~~



# 二、原理初识

## 1.pom.xml

### 	a.父项目

~~~xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <version>2.3.2.RELEASE</version>
    <artifactId>spring-boot-starter-parent</artifactId>
</parent>
~~~

==他的父项目是：==

~~~xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-dependencies</artifactId>
    <version>2.3.2.RELEASE</version>
</parent>
~~~

>   他才是真正管理spring boot 应用里面的依赖版本
>
>   只要写了父项目，就不用写版本，除了不在父项目中的



### 	b.导入的依赖

~~~xml
<dependency><!--web项目-->
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
~~~

**spring-boot-starter**-`web`:

​			spring-boot-starter:springboot场景启动器，帮我们导入了web模块正常启动所需要的依赖



SpringBoot将所有的功能场景抽取出来，做出了一个个的staters（启动器），在需要的项目中引用启动器，starter就会将相关的jar包导入





## 2.主程序类，应用的入口：

~~~java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
~~~

### a.@SpringBootApplication：

>   ==说明这个类是springBoot的主配置类，springboot通过运行这个类的main方法运行springboot应用==



~~~java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
~~~



#### a.1 @SpringBootConfiguration：

>   ==标注在类上，表示是springboot的配置类==



~~~java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
public @interface SpringBootConfiguration {

~~~

​	



##### 		a.1.1 @Configuration：

>   ==在配置类上标注这个注解==

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Configuration {
```

>   有==@Component==，表示配置类也是spring容器中的一个组件





#### a.2、@EnableAutoConfiguration：

>   ==开启自动配置功能==，以前我们需要配置的东西，springboot帮助我们自动配置
>
>   ### 1.利用@Import(==AutoConfigurationImportSelector.class==)给容器中导入一些组件
>
>   
>
>   
>
>   ### 2.==selectImports==(AnnotationMetadata annotationMetadata)：选择引用的内容
>
>   ~~~java
>   public String[] selectImports(AnnotationMetadata annotationMetadata) {
>   		if (!isEnabled(annotationMetadata)) {
>   			return NO_IMPORTS;
>   		}
>       
>   //获取自动配置条目
>   		AutoConfigurationEntry autoConfigurationEntry = getAutoConfigurationEntry(annotationMetadata);
>       
>   		return StringUtils.toStringArray(autoConfigurationEntry.getConfigurations());
>   	}
>   ~~~
>
>   
>
>   
>
>   ### 3.==getAutoConfigurationEntry==(annotationMetadata);：获取自动配置条目
>
>   ~~~java
>   protected AutoConfigurationEntry getAutoConfigurationEntry(AnnotationMetadata annotationMetadata) {
>   		if (!isEnabled(annotationMetadata)) {
>   			return EMPTY_ENTRY;
>   		}
>   		AnnotationAttributes attributes = getAttributes(annotationMetadata);
>   //获取候选的配置		
>       List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);
>   		configurations = removeDuplicates(configurations);
>   		Set<String> exclusions = getExclusions(annotationMetadata, attributes);
>   		checkExcludedClasses(configurations, exclusions);
>   		configurations.removeAll(exclusions);
>   		configurations = getConfigurationClassFilter().filter(configurations);
>   		fireAutoConfigurationImportEvents(configurations, exclusions);
>   		return new AutoConfigurationEntry(configurations, exclusions);
>   	}
>   ~~~
>
>   
>
>   
>
>   ### 4.List<String> configurations = ==getCandidateConfigurations==(annotationMetadata, attributes);
>
>   获取候选的配置	
>
>   ~~~java
>   protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
>       
>   		List<String> configurations = SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(),
>   				getBeanClassLoader());
>       
>   		Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you "
>   				+ "are using a custom packaging, make sure that file is correct.");
>   		return configurations;
>   	}
>   ~~~
>
>   
>
>   
>
>   ### 5.List<String> configurations = SpringFactoriesLoader.
>
>   ### loadFactoryNames(getSpring
>
>   FactoriesLoaderFactoryClass(),getBeanClassLoader());
>
>   ```java
>   //返回标注了@EnableAutoConfiguration注解的类
>   protected Class<?> getSpringFactoriesLoaderFactoryClass() {
>      return EnableAutoConfiguration.class;
>   }
>   
>   ```
>
>    		获取META-INF/spring.factories中的所有自动配置类到容器中
>
>   
>
>   ### 6.==loadFactoryNames(getSpringFactoriesLoaderFactoryClass(),getBeanClassLoader());==
>
>   会返回一个properties，里面是生效的自动配置类
>
>   ~~~java
>   private static Map<String, List<String>> loadSpringFactories(@Nullable ClassLoader classLoader) {
>   		MultiValueMap<String, String> result = cache.get(classLoader);
>   		if (result != null) {
>   			return result;
>   		}
>   
>   		try {
>   			Enumeration<URL> urls = (classLoader != null ?
>   					classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
>   					ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION));
>   			result = new LinkedMultiValueMap<>();
>   			while (urls.hasMoreElements()) {
>   				URL url = urls.nextElement();
>   				UrlResource resource = new UrlResource(url);
>   				Properties properties = PropertiesLoaderUtils.loadProperties(resource);
>   				for (Map.Entry<?, ?> entry : properties.entrySet()) {
>   					String factoryTypeName = ((String) entry.getKey()).trim();
>   					for (String factoryImplementationName : StringUtils.commaDelimitedListToStringArray((String) entry.getValue())) {
>   						result.add(factoryTypeName, factoryImplementationName.trim());
>   					}
>   				}
>   			}
>   			cache.put(classLoader, result);
>   			return result;
>   		}
>   		catch (IOException ex) {
>   			throw new IllegalArgumentException("Unable to load factories from location [" +
>   					FACTORIES_RESOURCE_LOCATION + "]", ex);
>   		}
>   	}
>   ~~~
>
>   
>
>   ### 7.以一个配置类为模型解释自动配置的原因
>
>   ~~~java
>   //表示这是一个配置类
>   @Configuration(proxyBeanMethods = false)
>   
>   //按照不同的条件如果满足条件整个配置类的文件就会生效，判断当前应用是否是web应用
>   @ConditionalOnWebApplication(type = Type.SERVLET)
>   //判断当前项目是否有这些类
>   @ConditionalOnClass({ Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class })
>   //容器中没有这个组件
>   @ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
>   @AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
>   @AutoConfigureAfter({ DispatcherServletAutoConfiguration.class, TaskExecutionAutoConfiguration.class,
>   		ValidationAutoConfiguration.class })
>   public class WebMvcAutoConfiguration {
>       
>   ~~~
>
>   如果都符合了这个配置类就生效
>
>   ==debug=true==，可以打印哪些自动配置生效了



~~~java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import(AutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {
~~~



##### a.2.1@AutoConfigurationPackage：

~~~java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(AutoConfigurationPackages.Registrar.class)
public @interface AutoConfigurationPackage {
~~~



>   ==自动配置包==
>
>   **@Import(AutoConfigurationPackages.Registrar.class)**：
>
>   给容器中导入一个组件，导入的组件由AutoConfigurationPackages.Registrar.class
>
>   ==将主配置类（@SpringBootApplication标注的类）以及所在的包下面的所有包里面的所有组件都扫描进入spring容器==



~~~java
static class Registrar implements ImportBeanDefinitionRegistrar, DeterminableImports {
		@Override
		public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
			register(registry, new PackageImports(metadata).getPackageNames().toArray(new String[0]));
		}
		@Override
		public Set<Object> determineImports(AnnotationMetadata metadata) {
			return Collections.singleton(new PackageImports(metadata));
		}
	}
~~~

new PackageImports(metadata).getPackageNames().toArray(new String[0])):

​	==主配置类所在的包==



##### a.2.2 @Import(AutoConfigurationImportSelector.class)：

给容器中导入组件AutoConfigurationImportSelector：

导入哪些组件的选择器

将所有需要导入的组件以全类名的方式返回，这些组件就会被添加到容器中

会给容器导入非常多的自动配置类xxxautoconfiguration

~~~java
@Override
public String[] selectImports(AnnotationMetadata annotationMetadata) {
    if (!isEnabled(annotationMetadata)) {
        return NO_IMPORTS;
    }
    
    
    AutoConfigurationEntry autoConfigurationEntry = getAutoConfigurationEntry(annotationMetadata);
    
    
    return StringUtils.toStringArray(autoConfigurationEntry.getConfigurations());
}
~~~



getAutoConfigurationEntry(annotationMetadata);

>   ==获得自动配置实体==

~~~java
protected AutoConfigurationEntry getAutoConfigurationEntry(AnnotationMetadata annotationMetadata) {
		if (!isEnabled(annotationMetadata)) {
			return EMPTY_ENTRY;
		}
		AnnotationAttributes attributes = getAttributes(annotationMetadata);
    
    
		List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);
    
    
		configurations = removeDuplicates(configurations);
		Set<String> exclusions = getExclusions(annotationMetadata, attributes);
		checkExcludedClasses(configurations, exclusions);
		configurations.removeAll(exclusions);
		configurations = getConfigurationClassFilter().filter(configurations);
		fireAutoConfigurationImportEvents(configurations, exclusions);
		return new AutoConfigurationEntry(configurations, exclusions);
	}
~~~



List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);

>   ==获取所有候选的配置==



~~~java
protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
		List<String> configurations = 
            
            
            
            SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(),
				getBeanClassLoader());
    
    
		Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you "
				+ "are using a custom packaging, make sure that file is correct.");
		return configurations;
	}
//获取标注了@EnableAutoConfiguration的类
protected Class<?> getSpringFactoriesLoaderFactoryClass() {
		return EnableAutoConfiguration.class;
	}
~~~





==loadFactoryNames()==:

获取所有的加载配置

>   ![image-20200804135924288](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200829130720.png)



>   项目资源：classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
>
>   系统资源：ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION))
>
>   FACTORIES_RESOURCE_LOCATION：  "META-INF/spring.factories"

==从这些资源中遍历了URL url = urls.nextElement();最后封装成了一个Properties 供我们使用==

```
Properties properties = PropertiesLoaderUtils.loadProperties(resource);
```



META-INF/spring.factories.自动配置的核心文件：

>   在这个配置文件中的需要自动导入的都有一个注解

@ConditionalOnClass：判断条件是否满足，决定导入



```java
@ConditionalOnClass({ JdbcTemplate.class, PlatformTransactionManager.class })
```



![image-20200804135305616](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200829130729.png)





### b.总结：

springboot的所有自动配置都是在启动的时候扫描并加载，==META-INF/spring.factories.==所有需要自动配置的文件都放在这个文件里面，但是不是都生效，需要判断条件是否成立：通过	**==@ConditionalOnClass==**这个注解判断，满足就会生效

1.springboot在启动的时候在类路径下的==META-INF/spring.factories==中获取配置文件

2.将这些自动配置的类导入容器，就会生效，springboot就会进行自动配置

3.所有和自动配置的东西放在了spring-boot-autoconfigure 这个包里面

![](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200829130747.png)



4.文件中存在非常多的AutoConfigure（@Bean）  ，就是这些类给容器中导入了需要的所有组件，让我们不用手动配置bean，里面会自动配置bean

![image-20200804142759845](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200829130751.png)

![image-20200804142838568](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200829130759.png)















# 三、通过yml配置文件把pojo中的实体类注入到spring容器中

## 1.导入依赖：

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-configuration-processor</artifactId>
</dependency>
```



## 2.pojo

~~~java
@Component
@ConfigurationProperties(prefix = "user")
@Data
public class User {
   private String myName;
   private int age;

}
~~~



## 3.yml配置文件

~~~yaml
user:
  my-name: like
  age: 18
~~~





## 4.测试

~~~java
@RunWith (SpringRunner.class)
@SpringBootTest
public class UserTest {
    @Resource
    User user;
    @Test
    public void name() {
        System.out.println(user);
    }
}


~~~

![image-20200804153253206](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200829130813.png)









# 四、给springboot添加组件：

通过配置类



~~~java
@Configuration
public class BeansConfig {
    @Bean//将方法的返回值添加到容器中，容器中这个组件的名字就是方法名
    public UserService userService() {
        return new UserService();
    }
}
~~~





测试：

~~~java
@RunWith (SpringRunner.class)
@SpringBootTest
public class ApplicationTest {
     @Resource
    UserService userService;
       @Test
    public void getUserService() {
        System.out.println(userService);
    }
~~~





# 五、profile

## 1.激活指定配置文件

properties

![image-20200804163511627](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200829130900.png)

>   有两个配置文件在第一个中指定启动哪个：
>
>   ==spring.profiles.active=dev==





yml

![image-20200804163714523](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200829130904.png)





# 六、SpringBoot的自动配置@EnableAutoConfiguration

@EnableAutoConfiguration：

==开启自动配置功能==，以前我们需要配置的东西，springboot帮助我们自动配置

## 1.利用@Import(==AutoConfigurationImportSelector.class==)给容器中导入一些组件



## 2.==selectImports==(AnnotationMetadata annotationMetadata)：选择引用的内容

~~~java
public String[] selectImports(AnnotationMetadata annotationMetadata) {
		if (!isEnabled(annotationMetadata)) {
			return NO_IMPORTS;
		}
    
//获取自动配置条目
		AutoConfigurationEntry autoConfigurationEntry = getAutoConfigurationEntry(annotationMetadata);
    
		return StringUtils.toStringArray(autoConfigurationEntry.getConfigurations());
	}
~~~



## 3.==getAutoConfigurationEntry==(annotationMetadata);：获取自动配置条目

~~~java
protected AutoConfigurationEntry getAutoConfigurationEntry(AnnotationMetadata annotationMetadata) {
		if (!isEnabled(annotationMetadata)) {
			return EMPTY_ENTRY;
		}
		AnnotationAttributes attributes = getAttributes(annotationMetadata);
//获取候选的配置		
    List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);
		configurations = removeDuplicates(configurations);
		Set<String> exclusions = getExclusions(annotationMetadata, attributes);
		checkExcludedClasses(configurations, exclusions);
		configurations.removeAll(exclusions);
		configurations = getConfigurationClassFilter().filter(configurations);
		fireAutoConfigurationImportEvents(configurations, exclusions);
		return new AutoConfigurationEntry(configurations, exclusions);
	}
~~~



## 4.List<String> configurations = ==getCandidateConfigurations==(annotationMetadata, attributes);

获取候选的配置	

~~~java
protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
    
		List<String> configurations = SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(),
				getBeanClassLoader());
    
		Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you "
				+ "are using a custom packaging, make sure that file is correct.");
		return configurations;
	}

~~~





## 5.List<String> configurations = SpringFactoriesLoader.loadFactoryNames(getSpring



FactoriesLoaderFactoryClass(),getBeanClassLoader());

```java
//返回标注了@EnableAutoConfiguration注解的类
protected Class<?> getSpringFactoriesLoaderFactoryClass() {
   return EnableAutoConfiguration.class;
}

```

 		获取META-INF/spring.factories中的所有自动配置类到容器中



## 6.==loadFactoryNames(getSpringFactoriesLoaderFactoryClass(),getBeanClassLoader());==

会返回一个properties，里面是生效的自动配置类

~~~java
private static Map<String, List<String>> loadSpringFactories(@Nullable ClassLoader classLoader) {
		MultiValueMap<String, String> result = cache.get(classLoader);
		if (result != null) {
			return result;
		}

		try {
			Enumeration<URL> urls = (classLoader != null ?
					classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
					ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION));
			result = new LinkedMultiValueMap<>();
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				UrlResource resource = new UrlResource(url);
				Properties properties = PropertiesLoaderUtils.loadProperties(resource);
				for (Map.Entry<?, ?> entry : properties.entrySet()) {
					String factoryTypeName = ((String) entry.getKey()).trim();
					for (String factoryImplementationName : StringUtils.commaDelimitedListToStringArray((String) entry.getValue())) {
						result.add(factoryTypeName, factoryImplementationName.trim());
					}
				}
			}
			cache.put(classLoader, result);
			return result;
		}
		catch (IOException ex) {
			throw new IllegalArgumentException("Unable to load factories from location [" +
					FACTORIES_RESOURCE_LOCATION + "]", ex);
		}
	}
~~~

## 7.以一个配置类为模型解释自动配置的原因

~~~java
//表示这是一个配置类
@Configuration(proxyBeanMethods = false)

//按照不同的条件如果满足条件整个配置类的文件就会生效，判断当前应用是否是web应用
@ConditionalOnWebApplication(type = Type.SERVLET)
//判断当前项目是否有这些类
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class })
//容器中没有这个组件
@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
@AutoConfigureAfter({ DispatcherServletAutoConfiguration.class, TaskExecutionAutoConfiguration.class,
		ValidationAutoConfiguration.class })
public class WebMvcAutoConfiguration {
    
~~~

如果都符合了这个配置类就生效

==debug=true==，可以打印哪些自动配置生效了





# 七、SpringBoot的日志

日志门面：slf4j

日志实现：logback



![image-20200804185418231](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200829130908.png)



实现：

~~~java
 Logger log = LoggerFactory.getLogger(getClass());
    @Test
    public void log() {
        log.trace("这是trace日志");
        log.debug("这是debug");
        log.warn("出现错误");
        System.out.println(1);
    }	
~~~





# 八、SpringBoot的web开发



## 1.静态资源的引入：

以jquery为例：

导入依赖：

~~~xml
<dependency>
    <groupId>org.webjars.bower</groupId>
    <artifactId>jquery</artifactId>
    <version>3.5.1</version>
</dependency>
~~~

![image-20200805143053815](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200829130910.png)

http://localhost:8888/webjars/jquery/3.5.1/dist/jquery.js

![image-20200805145424381](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200829130913.png)

favicon.ico是显示在网页上的图标

## 2.模板引擎：thymeleaf

### 	a.依赖

```xml
<dependency><!--thymeleaf-->
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

### 	b.使用：

```java
@ConfigurationProperties(prefix = "spring.thymeleaf")
public class ThymeleafProperties {

   private static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;

   public static final String DEFAULT_PREFIX = "classpath:/templates/";

   public static final String DEFAULT_SUFFIX = ".html";
```

>   放入classpath:/templates/中，结尾是.html就能自动渲染



![image-20200805150556915](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200829130916.png)



![image-20200805150613623](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200829130918.png)



# 九、thymeleaf语法：

##### eg.取出服务器传的值：

网页

```html
<div th:text="${hello}">我草你的</div>
```

controller

```java
@RequestMapping ("/success")
public String success(Model model) {
    model.addAttribute("hello","hello world");
    return "success";
}
```



## 1.语法规则：

>   1.  `th`:==任意html属性==，可以用服务器传入的值替换
>   2.  th:text:替换文本内容



![image-20200805152353591](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200829130922)

## 2.表达式：

>   1.==${}==：获取对象的属性，调用方法，使用内置的基本对象，可以时候一些工具类：string,arrays…
>
>   2.==*{}==:配合${}使用
>
>   3.==#{}==:国际化
>
>   4.==@{}==:url使用
>
>   





# 十、拓展springMvc内容

写一个类实现==WebMvcConfigurer== 类并用==@Configuration==注解表示是一个配置类



## 1.实现视图解析器功能：隐射index页面

~~~java
    /**
     * 添加视图解析器
     *
     * @param registry ViewControllerRegistry
     */
    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
    }
~~~



## 2.添加资源处理器

保证静态资源能够使用



~~~java
  /**
     * 使用 WebMvcConfigurationSupport 而静态文件不显示CSS样式的，这是因为替换之后之前的静态资源文件 会被拦截，导致无法可用。
     * 解决办法：重写 addResourceHandlers（）方法，加入静态文件路径即可
     *
     * @param registry ResourceHandlerRegistry
     */
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    /*  registry.addResourceHandler("/**")//"/webjars/**"
                .addResourceLocations("classpath:/META-INF/resources/webjars/");*/
    registry.addResourceHandler("/file/**")
        .addResourceLocations("file:\\");
}
~~~



## 3.添加国际化功能

### a.编写资源文件

![image-20200805173324547](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200829130926.png)







### b.国际化自动配置类：

~~~java
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(name = AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME, search = SearchStrategy.CURRENT)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@Conditional(ResourceBundleCondition.class)
@EnableConfigurationProperties
public class MessageSourceAutoConfiguration {
    @Bean
	@ConfigurationProperties(prefix = "spring.messages")
	public MessageSourceProperties messageSourceProperties() {
		return new MessageSourceProperties();
	}

	@Bean
	public MessageSource messageSource(MessageSourceProperties properties) {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		if (StringUtils.hasText(properties.getBasename())) {
            //设置国际化资源文件的基础名字（去掉国家代码）
			messageSource.setBasenames(StringUtils		.commaDelimitedListToStringArray(StringUtils.trimAllWhitespace(properties.getBasename())));
		}
		if (properties.getEncoding() != null) {
			messageSource.setDefaultEncoding(properties.getEncoding().name());
		}
		messageSource.setFallbackToSystemLocale(properties.isFallbackToSystemLocale());
		Duration cacheDuration = properties.getCacheDuration();
		if (cacheDuration != null) {
			messageSource.setCacheMillis(cacheDuration.toMillis());
		}
		messageSource.setAlwaysUseMessageFormat(properties.isAlwaysUseMessageFormat());
		messageSource.setUseCodeAsDefaultMessage(properties.isUseCodeAsDefaultMessage());
		return messageSource;
	}
    
~~~



### c.在页面获取国际化的值

![image-20200805191337655](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200829130928.png)



### d.实现点击按钮变化国际化语言

原理：

​		在webmvc自动配置类里面：

~~~java
@Bean
@ConditionalOnMissingBean
@ConditionalOnProperty(prefix = "spring.mvc", name = "locale")
public LocaleResolver localeResolver() {
    if (this.mvcProperties.getLocaleResolver() == WebMvcProperties.LocaleResolver.FIXED) {
        return new FixedLocaleResolver(this.mvcProperties.getLocale());
    }

    AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
    localeResolver.setDefaultLocale(this.mvcProperties.getLocale());
    return localeResolver;
}
~~~

 ==new AcceptHeaderLocaleResolver()==;

~~~java
@Override
	public Locale resolveLocale(HttpServletRequest request) {
		Locale defaultLocale = getDefaultLocale();
		if (defaultLocale != null && request.getHeader("Accept-Language") == null) {
			return defaultLocale;
		}
		Locale requestLocale = request.getLocale();
		List<Locale> supportedLocales = getSupportedLocales();
		if (supportedLocales.isEmpty() || supportedLocales.contains(requestLocale)) {
			return requestLocale;
		}
		Locale supportedLocale = findSupportedLocale(request, supportedLocales);
		if (supportedLocale != null) {
			return supportedLocale;
		}
		return (defaultLocale != null ? defaultLocale : requestLocale);
	}
~~~

默认的就是根据请求头里卖的assept-language决定国际化



### e.自定义一个国际化解析器

>   按照请求中的值来返回国际化的语言
>
>   ```html
>   <a class="btn btn-sm" th:href="@{/index(l='zn_CH')}">中文</a>
>   <a class="btn btn-sm" th:href="@{/index(l='en_us')}" >English</a>
>   ```
>
>   

```java
@Component
public class MyLocaleResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String l = request.getParameter("l");
        Locale locale = Locale.getDefault();
        if (!StringUtils.isEmpty(l)) {
            String[] split = l.split("_");
            locale = new Locale(split[0], split[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
```

注册在自己的配置类中：

~~~java
/**
* 添加自定义国际化内容
*
*/
@Bean
public LocaleResolver localeResolver() {
    return new MyLocaleResolver();
}

~~~





## 4.添加拦截器功能

注意事项：

*   禁用缓存

    ~~~properties
    spring.thymeleaf.cache=false
    ~~~

*   重新编译页面：ctrl+f9



### a.一个实现了拦截器的类

>   implements ==HandlerInterceptor==
>
>   通过判断session中是否存在username

~~~java
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object username = session.getAttribute("username");
        //已经登录
        if (username != null) {
            return true;
        }
        //没有登录
        request.setAttribute("errorMsg","请登录后操作");
        request.getRequestDispatcher("/").forward(request, response);
        return false;
    }
~~~

注册在session中：

~~~java
@Override
public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoginHandlerInterceptor())
        //拦截的请求
        .addPathPatterns("/**")
        //放行的请求
        .excludePathPatterns("/", "/index.html", "/user/login");
}
~~~



### b.和用户相关的controller



>   判断请求中是否有用户

```java
@Controller
@RequestMapping ("/user")
public class UserController {
    @PostMapping ("/login")
    public String login(@RequestParam ("username") String username,
                        @RequestParam ("pwd") String pwd, Model m, HttpSession session) {
        if (Objects.equals(username, "root") && Objects.equals(pwd, "1")) {
           session.setAttribute("username",username);
            //登录成功重定向到主页面
            return "redirect:/main.html";
        } else {
            m.addAttribute("errorMsg", "用户名或密码错误");
            //登录识别到登录页面
            return "index";
        }
    }
}
```

在自己的视图解析器中添加main.html

~~~java
@Override
public void addViewControllers(ViewControllerRegistry registry) {
    //首页映射
    registry.addViewController("/").setViewName("index");
    registry.addViewController("/index.html").setViewName("index");
    //进入登录后的页面
    registry.addViewController("/main.html").setViewName("dashboard");
}
~~~

### c.错误消息回显：

~~~html
<p 
    th:text="${errorMsg == null?'请输入用户名和密码':errorMsg }" 	
    th:style="${errorMsg!= null?'color: red':''}">
</p>
~~~



# 十一、restful的crud

## 1.实验规范

![image-20200806145010661](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200829130934.png)

![image-20200806145720755](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200829130935.png)



## 2.显示列表：

>   获取get请求
>
>   uri为/emps

~~~java
@GetMapping("/emps")
public String list(Model model) {
    Collection<Employee> list = employeeDao.getAll();
    model.addAttribute("list", list);
    return "emp/list";
}
~~~



在页面遍历输出

>   格式化日期： ==th:text="${#dates.format(emp.birth,'yyyy/MM/dd HH:mm')}"==

~~~html
<tbody>
    <tr th:each="emp:${list}">
        <td th:text="${emp.id}"></td>
        <td th:text="${emp.lastName}"></td>
        <td th:text="${emp.email}"></td>
        <td th:text="${emp.gender}==0?'女':'男'"></td>
        <td th:text="${emp.department.departmentName}"></td>
        <td th:text="${#dates.format(emp.birth,'yyyy/MM/dd HH:mm')}"></td>
        <td>
            <button class="btn-sm btn-primary">编辑</button>
            <button class="btn-sm btn-danger">删除</button>
        </td>
    </tr>
</tbody>
~~~





## 3.添加功能：

### 	a.在list页面添加按钮

```html
<!--员工添加按钮-->
<h2><a th:href="@{/emp}" class="btn  btn-sm btn-success">员工添加</a></h2>
```

![image-20200806190842293](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200829130938.png)



### 	b.添加controller方法



```java
//去添加页面
@GetMapping ("")
public String toAddPage(Model model) {
    Collection<Department> list = department.getDepartments();
    model.addAttribute("depts", list);
    return "emp/add";
}

//添加
@PostMapping ("")
public String addEmp(Employee employee) {
    System.out.println(employee);
    employeeDao.save(employee);
    return "redirect:/emp/emps";
}
```



c.add.html

~~~html
<form th:action="@{/emp}" method="post">
          <!--姓名-->
    <input type="text" class="form-control" id="lastName" name="lastName"
           placeholder="like">
          <!--邮箱-->
    <input type="email" class="form-control" id="exampleFormControlInput1" name="email"
           placeholder="name@example.com">
         <!--性别-->
    <select class="form-control" id="exampleFormControlSelect1" name="gender">
        <option>0</option>
        <option>1</option>
    </select>
       <!--部门编号-->
    <select multiple class="form-control" id="department" name="department.id">
        <option th:value="${dept.id}" th:each="dept:${depts}" th:text="${dept.departmentName}"></option>
    </select>
         <!--生日-->
    <input class="form-control" type="text" id="birth" name="birth" placeholder="yyyy/MM/dd">
    <input type="submit" value="添加" class="btn btn-primary">
</form>
~~~





## 4.修改功能：

### 	a.添加controller方法



~~~java
//去修改页面
@GetMapping ("/{id}")
public String toEditPage(@PathVariable ("id") Integer id, Model model) {
    //获取员工信息
    Employee employee = employeeDao.get(id);
    model.addAttribute("emp", employee);
    //获取员工部门信息
    Collection<Department> list = department.getDepartments();
    model.addAttribute("list", list);
    return "emp/edit";
}
//修改员工，重定向到获取员工列表请求
@PutMapping ("")
public String editEmp(Employee employee) {
    System.out.println("正在修改:"+employee);
    employeeDao.save(employee);
    return "redirect:/emp/emps";
}
~~~



### 	b.修改页面

```html
<main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
    <!--修改表单-->
    <form th:action="@{/emp}" th:method="put">
      <!--  <input type="hidden" value="put" name="_method">-->
        <!--id-->
        <input type="hidden" name="id" th:value="${emp.id}">
        <!--姓名-->
        <div class="form-group">
            <label for="lastName">LastName</label>
            <input type="text" class="form-control" id="lastName" name="lastName" th:value="${emp.lastName}"
                   placeholder="like">
        </div>
        <!--邮箱-->
        <div class="form-group">
            <label for="exampleFormControlInput1">Email</label>
            <input type="email" class="form-control" id="exampleFormControlInput1" name="email" th:value="${emp.email}"
                   placeholder="name@example.com">
        </div>
        <!--性别-->
        <div class="form-group">
            <label>Gender</label>
            <div class="form-check form-check-inline">
                <input type="radio" class="form-check-input" name="gender" value="1" th:checked="${emp.gender ==1}">
                <label class="form-check-label">男</label>
            </div>
            <div class="form-check form-check-inline">
                <input type="radio" class="form-check-input" name="gender" value="1" th:checked="${emp.gender ==0}">
                <label class="form-check-label">女</label>
            </div>
        </div>
        <!--部门编号-->
        <div class="form-group">
            <label for="department">DepartmentID</label>
            <select multiple class="form-control" id="department" name="department.id">
                <option th:value="${dept.id}" th:each="dept:${list}" th:text="${dept.departmentName}"
                        th:selected="${emp.department.id}==${dept.id}"></option>
            </select>
        </div>
        <!--生日-->
        <div class="form-group">
            <label for="birth">Birth</label>
            <input class="form-control" type="text" id="birth" name="birth" placeholder="yyyy/MM/dd"
                   th:value="${#dates.format(emp.birth,'yyyy/MM/dd HH:mm')}">
        </div>
        <input type="submit" value="修改" class="btn btn-primary">
    </form>
</main>
```





## 5.删除功能：

### 	a.添加controller方法

```java
//删除员工，重定向到获取员工列表请求
@DeleteMapping ("/{id}")
public String delEmp(@PathVariable Integer id) {
    employeeDao.delete(id);
    return "redirect:/emp/emps";
}
```



### 	b.给按钮绑定删除事件

```html
<button th:attr="uri=@{/emp/}+${emp.id}" type="submit" class="btn-sm btn-danger" id="delBtn">删除</button>

<!--删除-->
<script>
    $("#delBtn").click(function () {
        $("#delForm").attr("action", $(this).attr("uri")).submit();
        return false;
    });
</script>
```



# 十二、thymeleaf抽取公共的页面

## 1.insert：

>   将公共的部分整个插入到指定元素
>
>   插入到声明的insert属性的标签中

在需要抽取的部分加入属性  ==th:fragment="nav"==

~~~html
<nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0" 
     th:fragment="nav">
			<a class="navbar-brand col-sm-3 col-md-2 mr-0" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#"th:text="${session.username}">Company name</a>
			<input class="form-control form-control-dark w-100" type="text" placeholder="Search" aria-label="Search">
			<ul class="navbar-nav px-3">
				<li class="nav-item text-nowrap">
					<a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">Sign out</a>
				</li>
			</ul>
		</nav>
~~~

在需要加入的地方加入属性 ==th:insert="dashboard::nav"==

​		网页的名字（不带后缀）+设置的名字

~~~html
<div th:insert="dashboard::nav"></div>
~~~



## 2.replace：

>   把声明的片段替换为公共的片段
>
>   连声明replace属性的标签也替换







## 3.include

>   引入公共片段的标签的里面的内容到声明include属性的标签













## 4.抽取公共部分:

### a.bar

~~~html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>bar</title>
</head>
<body>
<!--顶栏-->
<nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0" th:fragment="topbar">
    <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#"
       th:text="${session.username}">Company name</a>
    <input class="form-control form-control-dark w-100" type="text" placeholder="Search" aria-label="Search">
    <ul class="navbar-nav px-3">
        <li class="nav-item text-nowrap">
            <a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">Sign out</a>
        </li>
    </ul>
</nav>
<!--侧边栏-->
<nav class="col-md-2 d-none d-md-block bg-light sidebar" th:fragment="sidebar">
    <div class="sidebar-sticky">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a  th:href="@{/main.html}"
                    th:class="${activeTrue=='main'?'nav-link active':'nav-link '}">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                         stroke-linejoin="round" class="feather feather-home">
                        <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
                        <polyline points="9 22 9 12 15 12 15 22"></polyline>
                    </svg>
                    仪表板 <span class="sr-only">(current)</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                         stroke-linejoin="round" class="feather feather-file">
                        <path d="M13 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V9z"></path>
                        <polyline points="13 2 13 9 20 9"></polyline>
                    </svg>
                    Orders
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                         stroke-linejoin="round" class="feather feather-shopping-cart">
                        <circle cx="9" cy="21" r="1"></circle>
                        <circle cx="20" cy="21" r="1"></circle>
                        <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path>
                    </svg>
                    Products
                </a>
            </li>
            <li class="nav-item">
                <a th:class="${activeTrue=='emps'?'nav-link active':'nav-link '}" href="/emp/emps">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                         stroke-linejoin="round" class="feather feather-users">
                        <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                        <circle cx="9" cy="7" r="4"></circle>
                        <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
                        <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
                    </svg>
                    员工管理
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                         stroke-linejoin="round" class="feather feather-bar-chart-2">
                        <line x1="18" y1="20" x2="18" y2="10"></line>
                        <line x1="12" y1="20" x2="12" y2="4"></line>
                        <line x1="6" y1="20" x2="6" y2="14"></line>
                    </svg>
                    Reports
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                         stroke-linejoin="round" class="feather feather-layers">
                        <polygon points="12 2 2 7 12 12 22 7 12 2"></polygon>
                        <polyline points="2 17 12 22 22 17"></polyline>
                        <polyline points="2 12 12 17 22 12"></polyline>
                    </svg>
                    Integrations
                </a>
            </li>
        </ul>

        <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
            <span>Saved reports</span>
            <a class="d-flex align-items-center text-muted"
               href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                     class="feather feather-plus-circle">
                    <circle cx="12" cy="12" r="10"></circle>
                    <line x1="12" y1="8" x2="12" y2="16"></line>
                    <line x1="8" y1="12" x2="16" y2="12"></line>
                </svg>
            </a>
        </h6>
        <ul class="nav flex-column mb-2">
            <li class="nav-item">
                <a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                         stroke-linejoin="round" class="feather feather-file-text">
                        <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                        <polyline points="14 2 14 8 20 8"></polyline>
                        <line x1="16" y1="13" x2="8" y2="13"></line>
                        <line x1="16" y1="17" x2="8" y2="17"></line>
                        <polyline points="10 9 9 9 8 9"></polyline>
                    </svg>
                    Current month
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                         stroke-linejoin="round" class="feather feather-file-text">
                        <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                        <polyline points="14 2 14 8 20 8"></polyline>
                        <line x1="16" y1="13" x2="8" y2="13"></line>
                        <line x1="16" y1="17" x2="8" y2="17"></line>
                        <polyline points="10 9 9 9 8 9"></polyline>
                    </svg>
                    Last quarter
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                         stroke-linejoin="round" class="feather feather-file-text">
                        <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                        <polyline points="14 2 14 8 20 8"></polyline>
                        <line x1="16" y1="13" x2="8" y2="13"></line>
                        <line x1="16" y1="17" x2="8" y2="17"></line>
                        <polyline points="10 9 9 9 8 9"></polyline>
                    </svg>
                    Social engagement
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                         stroke-linejoin="round" class="feather feather-file-text">
                        <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                        <polyline points="14 2 14 8 20 8"></polyline>
                        <line x1="16" y1="13" x2="8" y2="13"></line>
                        <line x1="16" y1="17" x2="8" y2="17"></line>
                        <polyline points="10 9 9 9 8 9"></polyline>
                    </svg>
                    Year-end sale
                </a>
            </li>
        </ul>
    </div>
</nav>
</body>
</html>
~~~



### b.通过引用公共部分传值，实现按钮active

>   判断 activeTrue的值来绝对是不是active

~~~html
<a  th:href="@{/main.html}"
      th:class="${activeTrue=='main'?'nav-link active':'nav-link '}">
~~~



~~~html
<div th:replace="commons/bar::sidebar(activeTrue='main')"></div>
~~~









# 十三、错误处理机制

## 1.SpringBoot默认的错误处理机制

​	a.浏览器：访问一个错误页面

![image-20200807133821026](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200829130949.png)

​	b.客户端：会返回一个json数据

```js
{    
    "timestamp":"2020-08-07T05:41:14.123+00:00",
    "status":404,
    "error":"Not Found",
    "message":"No message available","path":"/s"
}
```

原因：

>   请求头不一样
>
>   浏览器是：text/html
>
>   其他客户端是：*/*

## 2.ErrorMvcAutoConfiguration的错误处理机制

给容器中添加了以下组件



1.  DefaultErrorAttributes

    在页面共享信息

    ```java
    @Override
    @Deprecated
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
       Map<String, Object> errorAttributes = new LinkedHashMap<>();
       errorAttributes.put("timestamp", new Date());
       addStatus(errorAttributes, webRequest);
       addErrorDetails(errorAttributes, webRequest, includeStackTrace);
       addPath(errorAttributes, webRequest);
       return errorAttributes;
    }
    ```

    

2.  BasicErrorController：处理/error请求

    ```java
    @RequestMapping("${server.error.path:${error.path:/error}}")
    public class BasicErrorController extends AbstractErrorController {
        ···
            //处理html请求
        @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)//"text/html"
        public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
            HttpStatus status = getStatus(request);
            Map<String, Object> model = Collections
                .unmodifiableMap(getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.TEXT_HTML)));
            response.setStatus(status.value());
            //去哪个页面，包含页面地址和页面内容
            ModelAndView modelAndView = resolveErrorView(request, response, status, model);
            return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);
        }
        
        //处理其他请求，产生json数据
        @RequestMapping
        public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
            HttpStatus status = getStatus(request);
            if (status == HttpStatus.NO_CONTENT) {
                return new ResponseEntity<>(status);
            }
            Map<String, Object> body = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL));
            return new ResponseEntity<>(body, status);
        }
    ```

    

3.  ErrorPageCustomizer：返回错误页面

    >   系统出现错误以后来到error请求进行处理；（web.xml注册的错误页面规则）

    ```java
    public class ErrorProperties {
       @Value("${error.path:/error}")
       private String path = "/error";
    ```

    

4.  DefaultErrorViewResolver

    ```java
    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
       ModelAndView modelAndView = resolve(String.valueOf(status.value()), model);
       if (modelAndView == null && SERIES_VIEWS.containsKey(status.series())) {
          modelAndView = resolve(SERIES_VIEWS.get(status.series()), model);
       }
       return modelAndView;
    }
    
    private ModelAndView resolve(String viewName, Map<String, Object> model) {
       //默认springboot可以去找一个页面   error/404
        String errorViewName = "error/" + viewName;
        
        //如果模板引擎可以解析这个页面就解析
       TemplateAvailabilityProvider provider = this.templateAvailabilityProviders.getProvider(errorViewName,
             this.applicationContext);
       if (provider != null) {
           //返回errorviewName指定的视图地址
          return new ModelAndView(errorViewName, model);
       }
        //如果不可用在静态资源里面找error/xxx（status）.html   
    	//"classpath:/META-INF/resources/",
        //"classpath:/resources/", "classpath:/static/", "classpath:/public/" };
       return resolveResource(errorViewName, model);
    }
    ```

    





## 3.定制错误消息：

### 	a.定制错误页面

​			**1）、有模板引擎的情况下；error/状态码;** 【将错误页面命名为  错误状态码.html 放在模板引擎文件夹里面的 error文件夹下】，发生此状态码的错误就会来到  对应的页面；

​			我们可以使用4xx和5xx作为错误页面的文件名来匹配这种类型的所有错误，精确优先（优先寻找精确的状态码.html）；		

​			页面能获取的信息；

​				timestamp：时间戳

​				status：状态码

​				error：错误提示

​				exception：异常对象

​				message：异常消息

​				errors：JSR303数据校验的错误都在这里

​			2）、没有模板引擎（模板引擎找不到这个错误页面），静态资源文件夹下找；

​			3）、以上都没有错误页面，就是默认来到SpringBoot默认的错误提示页面；

![image-20200807143037494](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200829130957.png)

```html
<h1>status:[[${status}]]</h1>
<h2>timestamp:[[${timestamp}]]</h2>
<h2>type:[[${error}]]</h2>
<h2>message:[[${message}]]</h2>
```



### 	b.定制json数据

出现错误以后，会来到/error请求，会被BasicErrorController处理，响应出去可以获取的数据是由getErrorAttributes得到的（是AbstractErrorController（ErrorController）规定的方法）；

​	1、完全来编写一个ErrorController的实现类【或者是编写AbstractErrorController的子类】，放在容器中；

​	2、页面上能用的数据，或者是json返回能用的数据都是通过errorAttributes.getErrorAttributes得到；

​			容器中DefaultErrorAttributes.getErrorAttributes()；默认进行数据处理的；

自定义ErrorAttributes

```java
//给容器中加入我们自己定义的ErrorAttributes
@Component
public class MyErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(requestAttributes, includeStackTrace);
        map.put("company","atguigu");
        return map;
    }
}
```

最终的效果：响应是自适应的，可以通过定制ErrorAttributes改变需要返回的内容，

![](D:/下载/Compressed/SpringBoot笔记/源码、资料、课件/文档/Spring Boot 笔记/images/搜狗截图20180228135513.png)



### 注册Servlet三大组件【Servlet、Filter、Listener】

由于SpringBoot默认是以jar包的方式启动嵌入式的Servlet容器来启动SpringBoot的web应用，没有web.xml文件。

注册三大组件用以下方式

ServletRegistrationBean

```java
//注册三大组件
@Bean
public ServletRegistrationBean myServlet(){
    ServletRegistrationBean registrationBean = new ServletRegistrationBean(new MyServlet(),"/myServlet");
    return registrationBean;
}

```

FilterRegistrationBean

```java
@Bean
public FilterRegistrationBean myFilter(){
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    registrationBean.setFilter(new MyFilter());
    registrationBean.setUrlPatterns(Arrays.asList("/hello","/myServlet"));
    return registrationBean;
}
```

ServletListenerRegistrationBean

```java
@Bean
public ServletListenerRegistrationBean myListener(){
    ServletListenerRegistrationBean<MyListener> registrationBean = new ServletListenerRegistrationBean<>(new MyListener());
    return registrationBean;
}
```





# 十四、Spring Data

## 1.jdbc



```xml
<dependency><!--jdbc-->
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency><!--mysql-connector-->
   <groupId>mysql</groupId>
   <artifactId>mysql-connector-java</artifactId>
</dependency>
```



配置文件：

连接docker虚拟机上面的

```properties
#配置数据库
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.url=jdbc:mysql://192.168.76.10:3306/jdbc
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```



## 2.自动配置原理

### 	a.数据源：

DataSourceConfiguration.class：

>   ​	默认支持tomcat、hikari、dbcp2，==springboot默认使用的是HikariDataSource==

可以自定义数据源

>   利用反射创建响应type的数据源，并且绑定相关属性

```java
/**
 * Generic DataSource configuration.
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(DataSource.class)
@ConditionalOnProperty(name = "spring.datasource.type")
static class Generic {
   @Bean
   DataSource dataSource(DataSourceProperties properties) {
      return properties.initializeDataSourceBuilder().build();
   }
}



@SuppressWarnings("unchecked")
public T build() {
    Class<? extends DataSource> type = getType();
    DataSource result = BeanUtils.instantiateClass(type);
    maybeGetDriverClassName();
    bind(result);
    return (T) result;
}
```



### 	b.自动运行.sql文件

DataSourceInitializerInvoker.afterPropertiesSet()



```java
@Override
public void afterPropertiesSet() {
   DataSourceInitializer initializer = getDataSourceInitializer();
   if (initializer != null) {
       //运行sql文件
      boolean schemaCreated = this.dataSourceInitializer.createSchema();
      if (schemaCreated) {
         initialize(initializer);
      }
   }
}
```

DataSourceInitializer.createSchema()

>   获取 fallbackResources.add("classpath*:" + fallback + "-" + platform + ".sql");
>      			 fallbackResources.add("classpath*:" + fallback + ".sql");
>
>   下的sql文件并运行

```java
boolean createSchema() {
    //获取sql文件
    List<Resource> scripts = getScripts("spring.datasource.schema", this.properties.getSchema(), "schema");
    if (!scripts.isEmpty()) {
        if (!isEnabled()) {
            logger.debug("Initialization disabled (not running DDL scripts)");
            return false;
        }
        String username = this.properties.getSchemaUsername();
        String password = this.properties.getSchemaPassword();
        //运行sql文件
        runScripts(scripts, username, password);
    }
    return !scripts.isEmpty();
}

private List<Resource> getScripts(String propertyName, List<String> resources, String fallback) {
    if (resources != null) {
        return getResources(propertyName, resources, true);
    }
    String platform = this.properties.getPlatform();
    List<String> fallbackResources = new ArrayList<>();
    fallbackResources.add("classpath*:" + fallback + "-" + platform + ".sql");
    fallbackResources.add("classpath*:" + fallback + ".sql");
    return getResources(propertyName, fallbackResources, false);
}


private void runScripts(List<Resource> resources, String username, String password) {
   if (resources.isEmpty()) {
      return;
   }
   ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
   populator.setContinueOnError(this.properties.isContinueOnError());
   populator.setSeparator(this.properties.getSeparator());
   if (this.properties.getSqlScriptEncoding() != null) {
      populator.setSqlScriptEncoding(this.properties.getSqlScriptEncoding().name());
   }
   for (Resource resource : resources) {
      populator.addScript(resource);
   }
   DataSource dataSource = this.dataSource;
   if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
      dataSource = DataSourceBuilder.create(this.properties.getClassLoader())
            .driverClassName(this.properties.determineDriverClassName()).url(this.properties.determineUrl())
            .username(username).password(password).build();
   }
   DatabasePopulatorUtils.execute(populator, dataSource);
}
```



## 3.整合Druid

### 	a.依赖：

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.23</version>
</dependency>
```



### 	b.配置

```java
@Configuration
public class DruidConfig {

    //返回德鲁伊数据库连接池
    @ConfigurationProperties (prefix = "spring.datasource")
    @Bean
    public DataSource druid() {
        return new DruidDataSource();
    }

    //配置druid的监控，
    //管理后台的servlet
    @Bean
    public ServletRegistrationBean statViewServlet() {
        //处理那些网页的请求
        ServletRegistrationBean bean =
                new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        Map<String, String> initParams = new HashMap<>();
        //初始化参数
        //设置用户名和密码
        initParams.put("loginUsername", "root");
        initParams.put("loginPassword", "1");
        initParams.put("allow", "");//允许访问，默认所有
        initParams.put("deny", "192.168.88.88");//拒绝访问
        bean.setInitParameters(initParams);
        return bean;
    }

    //过滤请求的filter
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<>();
        initParams.put("exclusions","*.js,*.css,/druid/**");//排除
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));//拦截所有请求
        return bean;
    }
}
```





## 4.整合mybatis

### 	a.依赖

```xml
<dependency>  <!--mybatis-->
   <groupId>org.mybatis.spring.boot</groupId>
   <artifactId>mybatis-spring-boot-starter</artifactId>
   <version>1.3.1</version>
</dependency>
```



### 	b.mapper.interface

```java
@Mapper
@Repository
public interface DepartmentMapper {
    public Department getDepById(Integer id);
    public int delDepById(Integer id);
    public boolean insertDep(Department department);
    public int updateDep(Department department);
}
```



### 	c.mapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace = "lk.dao.DepartmentMapper">
    <update id = "updateDep"> update department set departmentName = #{departmentName} where id = #{id} </update>
    
    <select id = "getDepById" resultType = "lk.pojo.Department">select * from department where id = #{id};</select>
    
    <delete id = "delDepById">delete from department where id = #{id}</delete>

    <insert id = "insertDep"> insert into department(departmentName)  values(#{departmentName})</insert>
</mapper>
```





### 	d.application.properties

```properties
#mybatis相关配置
mybatis.configuration.map-underscore-to-camel-case=true
mybatis.mapper-locations=classpath:mybatis/mapperXml/*.xml
```









### 5.整合springData JPA







# 十五、springboot运行流程

## 1.SpringApplication 

```java
public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
   return new SpringApplication(primarySources).run(args);
}
```



### 		a. new SpringApplication ()

```java
#构造函数，判断是普通项目还是web项目
public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
    //保存主配置类
    this.resourceLoader = resourceLoader;
    Assert.notNull(primarySources, "PrimarySources must not be null");
    this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
    //判断是不是web应用
    //private static final String WEBMVC_INDICATOR_CLASS = "org.springframework.web.servlet.DispatcherServlet";
    this.webApplicationType = WebApplicationType.deduceFromClasspath();
    //找到meta-info/spring.factories 下的所有ApplicationContextInitializer并保存
    setInitializers((Collection) getSpringFactoriesInstances(ApplicationContextInitializer.class));
    //找到所有的listener 监听器
    setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
    //决定哪个class 是主程序
    this.mainApplicationClass = deduceMainApplicationClass();
}
```



### b.   ConfigurableApplicationContext  run()方法的运行

```java
public ConfigurableApplicationContext run(String... args) {
   StopWatch stopWatch = new StopWatch();
   stopWatch.start();
   ConfigurableApplicationContext context = null;
   Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
   configureHeadlessProperty();
    
    //获取SpringApplicationRunListeners spring.factroies
   SpringApplicationRunListeners listeners = getRunListeners(args);
    //调用所有listeners.starting()方法
   listeners.starting();
   try {
       	//封装命令行参数
      ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
       //准备环境,完成后会调用listener.environmentPrepared()表示完成
      ConfigurableEnvironment environment = prepareEnvironment(listeners, applicationArguments);
       
      configureIgnoreBeanInfo(environment);
       //打印banner
      Banner printedBanner = printBanner(environment);
       //创建ioc容器
      context = createApplicationContext();
      exceptionReporters = getSpringFactoriesInstances(SpringBootExceptionReporter.class,
            new Class[] { ConfigurableApplicationContext.class }, context);
       //准备上下文环境，将environment保存到ioc中，并且调用applyinitlizers(),里面会调用initlizers()
       //还会调用listener的contextPrepared();方法
       //完全运行后又会回调所有的listener.contextLoaded()方法
      prepareContext(context, environment, listeners, applicationArguments, printedBanner);
       //刷新容器，ioc初始化完成
      refreshContext(context);
      //从容器中调用ApplicationRunner，CommandLineRunner
      afterRefresh(context, applicationArguments);
      stopWatch.stop();
      if (this.logStartupInfo) {
         new StartupInfoLogger(this.mainApplicationClass).logStarted(getApplicationLog(), stopWatch);
      }
      listeners.started(context);
      callRunners(context, applicationArguments);
   }
   catch (Throwable ex) {
      handleRunFailure(context, ex, exceptionReporters, listeners);
      throw new IllegalStateException(ex);
   }

   try {
      listeners.running(context);
   }
   catch (Throwable ex) {
      handleRunFailure(context, ex, exceptionReporters, null);
      throw new IllegalStateException(ex);
   }
    //返回ioc容器
   return context;
}
```



## 2.事件监听机制





# 十六、自定义stater

stater：

1.  这个场景需要的依赖是什么
2.  如何编写

```java
@Configuration    //指定是一个配置类
@ConditionalOnXXX  //在指定条件成立的情况下自动配置生效
@AutoConfigureAfter   //指定自动配置类的顺序

@Bean //给容器添加组件
@ConditionalOnProperty(prefix = "spring.mvc.hiddenmethod.filter", name = "enabled", matchIfMissing = false)//结合XXXproperties给配置类中添加配置
@EnableConfigurationProperties({ xxxProperties.class})//使XXX生效加入到容器中

将自动配置类配置在
    META-INF/spring.factories
```

3.  模式：

    启动器只用来做依赖导入，专门写一个自动配置模块，启动器依赖自动配置模块

    XXX-spring-boot-stater





## eg：

## 1.my-spring-boot-stater

```xml
<dependencies>
    <dependency>
        <!--引入自动配置模块-->
        <groupId>com.lk</groupId>
        <artifactId>my-spring-boot-stater-autoconfig</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency	>
</dependencies>
```



## 2.my-spring-boot-stater-autoconfig

```xml
<parent>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-parent</artifactId>
   <version>2.3.2.RELEASE</version>
   <relativePath /> <!-- lookup parent from repository -->
</parent>
<groupId>com.lk</groupId>
<artifactId>my-spring-boot-stater-autoconfig</artifactId>
<version>0.0.1-SNAPSHOT</version>
<name>my-spring-boot-stater-autoconfig</name>
<description>Demo project for Spring Boot</description>

<properties>
   <java.version>1.8</java.version>
</properties>

<dependencies>
   <!--引入spring-boot-starter，所有stater的基本配置-->
   <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter</artifactId>
   </dependency>
</dependencies>
```

![image-20200811163708621](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200829131009.png)

myserivce

```java
public class myService {
    myProperties myProperties;
    public com.lk.stater.myProperties getMyProperties() {
        return myProperties;
    }
    public void setMyProperties(com.lk.stater.myProperties myProperties) {
        this.myProperties = myProperties;
    }

    public String hello(String name) {
        return myProperties.getPrefix() + "-" + name + myProperties.getSuffix();
    }
}
```

myproperties

```java
@ConfigurationProperties(prefix = "lk.my")
public class myProperties {
   private String prefix;
   private String suffix;
    public String getPrefix() {
        return prefix;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public String getSuffix() {
        return suffix;
    }
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
```

aotoconfig

```java
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties (myProperties.class)
public class myServiceAutoConfiguration {
    @Autowired
    public myProperties myProperties;


    @Bean
    public myService myService() {
        myService service = new myService();
        service.setMyProperties(myProperties);
        return service;
    }
}
```

spring.factories

```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.lk.stater.myServiceAutoConfiguration
```







## 3.使用

引入依赖

```xml
<dependency>
    <groupId>com.lk</groupId>
    <artifactId>my-spring-boot-stater</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

controller

```java
@RestController
public class controller {
    @Autowired
    myService service;

    @GetMapping ("/hello")
    public String hello() {
       service.setMyProperties(service.getMyProperties());
        return service.hello("like");
    }
}
```

properties

```properties
lk.my.prefix=欢迎
lk.my.suffix=进入
```