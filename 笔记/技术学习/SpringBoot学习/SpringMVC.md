# SpringMVC

依赖：

>   ```xnl
>   spring-webmvc
>   servlet-api
>   jstl
>   jsp-api
>   ```

# 一、配置流程（注解）：

>   ## 1.一个前端控制器拦截所有请求，并且智能派发

### web.xml:

~~~xml
 <!--配置DispatchServlet:SpringMVC的核心，请求分发器-->
    <servlet>
        <servlet-name>SpringMVC</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--要绑定SpringMVC的配置文件-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:SpringMVC_Servlet.xml</param-value>
        </init-param>
        <!--启动级别
            值越小，越优先
        -->
       <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>SpringMVC</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

<filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <!--指定解决post请求乱码-->
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <!--解决响应乱码-->
        <init-param>
            <param-name>forceEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>


 <filter>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
~~~

>   ## 2.框架的自身的配置:

### springmvc_servlet.xml

~~~xml
 <!--配置视图解析器:拼接解析地址-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/pages/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
~~~

>   ###  3.Contorller层

### 写一个contorller类

~~~java
@Controller
public class HelloController  {
    //接受哪个请求
    @RequestMapping("/hello")
    public  String  myRequest (){
        System.out.println("已经搜到请求");
        //返回到哪个页面
        return "hello";
    }
}
~~~

## 运行流程：

>   1.客户端点击连接 http://localhost:8080/springMVC/hello
>
>   2.发送到tomcat服务器
>
>   3.SpringMVC的前端控制器 DispatchServlet  接受到请求 /hello
>
>   4.看请求地址和@RequestMapping("/hello")  标注的哪个匹配，来决定调用哪个类的哪个方法
>
>   5.找到目标处理器类和目标方法，直接利用反射执行方法
>
>   6.执行方法会有返回值：springMVC人为这个返回值就是要去的页面
>
>   7.拿到返回值，用视图解析器拼串得到完整的页面地址
>
>   8.前端控制器（DispatchServlet）帮我们转发到这个页面 

# 二、@RequestMapping：

## 标注在类上

==表示访问这个控制器的方法都加了一层基准路径==

>   ~~~java
>   @RequestMapping("/Controller")//标注在类上，表示访问这个控制器的方法都加了一层基准路径
>   @Controller
>   public class HelloController  {
>       //接受哪个请求
>       @RequestMapping("/hello")
>       public  String  myRequest (){
>           System.out.println("已经搜到请求");
>           //返回到哪个页面
>           return "hello";
>       }
>   }
>   //<a href="Controller/hello">去hello页面</a>
>   ~~~

## 属性

>   1.==method==： 限定请求方式
>
>   ​		get，post   默认是全接受
>
>   2.==params==：限定请求参数
>
>   3.==headers==:规定请求头
>
>   4.==consumes==：只接受的内容类型，context-type
>
>   5.==produces==：告诉浏览器返回的类是什么



~~~java
 //接受哪个请求
    @RequestMapping (value = "/hello",
            params = {"username!=qwe", "!pwd"},
            method = RequestMethod.GET,
            //Mozilla/5.0 (Windows NT 10.0; Win64; x64)AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36
            headers = {"user-agent:=Mozilla/5.0 (Windows NT 10.0; Win64; x64)AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36"},
            consumes ="",
            produces = ""
            )
~~~

## ant风格

>   ？： 能替代任意一个字符
>
>   *：能替代任意多个字符，和一层路径 
>
>   **：替代多层路径

## 路径中的占位符@PathVariable

~~~java
@Controller
public class HelloController {
    //接受哪个请求
    @RequestMapping ("/hello/{name}")
    public String myRequest(@PathVariable String name) {
        System.out.println(name);
        System.out.println("已经接受到请求");
        //返回到哪个页面
        return "hello";
    }
}
~~~

## REST风格：

representiational state transfer:==资源表现层状态转化==，互联网软件框架

资源（resources）：一个图片，一首歌，一段文本—

表现层（representation）：把资源具体呈现的一种方式，html格式，xml格式，json格式 ，二进制格式

状态转化（state transfer）：

GET：获取资源

POST:新建资源

PUT:更新资源

DELETE:删除资源

>   ### 1.springMVC有一个filter可以把普通的请求转成 put delete请求

~~~java
 <filter-mapping>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    <filter>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
    </filter>
~~~

>   ### 2.在页面的input 添加一个隐藏域 name=_mthod,value= put,delete_

~~~java
<form action="user/like" method="get">
    <input type="submit" value="获取用户信息">
</form>
<form action="user/like" method="post">
    <input type="submit" value="请求用户信息">
</form>
<form action="user/like" method="post">
    <input type="hidden" name="_method" value="delete"nmbvbnmv>
    <input type="submit" value="删除用户信息">
</form>
<form action="user/like" method="post">
    <input type="hidden" name="_method" value="put">
    <input type="submit" value="更新用户信息">
</form>
~~~

>   ### 3.在控制器方法中写入对应的处理方法

~~~java
@Controller
public class HelloController {
    //接受哪个请求
    @RequestMapping (value = "/user/{name}",method = RequestMethod.GET)
    public String get(@PathVariable String name) {
        System.out.println("已经获取到" + name + "用户的信息");
        //返回到哪个页面
        return "success";
    }

    @RequestMapping (value = "/user/{name}", method = RequestMethod.POST)
    public String post(@PathVariable String name) {
        System.out.println("已经上传到" + name + "用户的信息");
        //返回到哪个页面
        return "success";
    }

    @RequestMapping (value = "/user/{name}", method = RequestMethod.PUT)
    public String put(@PathVariable String name) {
        System.out.println("已经更新" + name + "用户的信息");
        //返回到哪个页面
        return "success";
    }

    @RequestMapping (value = "/user/{name}", method = RequestMethod.DELETE)
    public String delete(@PathVariable String name) {
        System.out.println("已经删除" + name + "用户的信息");
        //返回到哪个页面
        return "success";
    }
}
~~~

## @RequestParam

参数：

>   value：指定获取参数的名字
>
>   required：是否必须
>
>   defaultvalue：如果没有这个值，默认值是什么

获取请求中的参数

~~~java
 @RequestMapping (value = "/user/{name}",method = RequestMethod.GET)
    public String get(@PathVariable String name, @RequestParam(value = "pwd",required = true,defaultValue = "没有密码") String pwd) {
        System.out.println("已经获取到" + name + "用户的信息");
        System.out.println("密码是：" + pwd);
        //返回到哪个页面
        return "success";
    }
~~~

## @RequestHeader

参数：

>   value：指定获取参数的名字
>
>   required：是否必须
>
>   defaultvalue：如果没有这个值，默认值是什么

获取请求头中的参数

~~~java
  @RequestMapping (value = "/user/{name}",method = RequestMethod.GET)
    public String get(@PathVariable String name, @RequestParam(value = "pwd",required = true,defaultValue = "没有密码") String pwd, @RequestHeader("user-agent") String useragent) {
        System.out.println("已经获取到" + name + "用户的信息");
        System.out.println("密码是：" + pwd);
        System.out.println(useragent);
        //返回到哪个页面
        return "success";
    }
~~~

## @CookieValue

获取cookie中的值

~~~java
 @CookieValue(value = "jsessionid") String JD
~~~



## 自动封装对象：

在控制层方法中添加你要封装的对象，springmvc就会自动封装

~~~java
 @RequestMapping (value = "/user/{name}", method = RequestMethod.POST)
    public String post(@PathVariable String name, Person person, HttpSession session) {
        session.setAttribute("hello","hello");
        System.out.println("已经上传到" + name + "用户的信息");
        System.out.println(person);
        //返回到哪个页面
        return "success";
    }
~~~

person

~~~java
public class Person {
    private String name;
    private int age;
}
~~~

html

~~~html
<form action="user/name" method="post">
	<input type="text" name="name">
	<input type="password" name="age">
	<input type="submit" value="添加用户">
</form>
~~~

## 解决乱码

~~~xml
<filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <!--指定解决post请求乱码-->
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <!--解决响应乱码-->
        <init-param>
            <param-name>forceEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
~~~

# 三、前端控制器：dispatcherservlet

httpservlet=>httpservletBean=>==frameworkservlet===>dispatcherservlet

## frameworkservlet：

>   ##### 	1.frameworkserlvet实现了doget、dopost、doput、dodelet,又全都调用了一个方法：
>
>   ###### 				==processRequest(request, response)==;
>
>   #### 	2.processRequest(request, response)又try了一个方法：
>
>   ##### 					==doService(request, response)==;

## dispatcherservlet：

>   ##### 1.dispatcherservlet继承了frameworkservlet，重写了doservice，又try了一个方法：
>
>   ##### 	==doDispatch(request, response)==;
>
>   #### 2.doDispatch有一个方法确定处理器gethandle();
>
>   ##### 	==mappedHandler = getHandler(processedRequest)==;
>
>   #### 3.通过处理器获取到能执行这个方法的适配器 getHandlerAdapter（）
>
>   ##### 	==HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler())==;
>
>   #### 4.doDispatch(request, response)有一个ha.handle，执行方法
>
>   ##### 	==mv = ha.handle(processedRequest, response, mappedHandler.getHandler());==
>
>   ha是适配器，右适配器执行请求的方法，返回一个modleandview	
>
>   #### 5.后面又有个方法根据modleandview，转发到页面页面
>
>   ==processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException)==;

## getHandle：怎么获取的处理器

mappedHandler = getHandler(processedRequest)：

>   1.  有一个map存放了所有handle：==handlerMappings==
>
>       遍历这个mapping，	根据当前请求在这个map中找到请求的映射信息，找到处理器
>
>   2.  这个map什么时候创建的？
>
>       ioc容器启动的时候扫描每个处理器，就保存了这个处理器



## getHandlerAdapter：怎么获取适配器

HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());

>   1.有一个==handlerAdapters==存放了所有的适配器
>
>   ​	根据当前的处理器来获取handleradapter 适配器
>
>   2.根据适配器，执行请求的方法

## handler适配器怎么获取到目标方法：

mv = ha.handle(processedRequest, response, mappedHandler.getHandler())

>   1.调用AbstractHandlerMethodAdapter类里面的 handleInternal方法
>
>   2.最后返回==mav = invokeHandlerMethod(request, response, handlerMethod==);
>
>   3.进入 ModelAndView  invokeHandlerMethod()方法：
>
>   ​	有一个==invocableMethod.invokeAndHandle(webRequest, mavContainer)==；方法 调用并执行方法
>
>   4.调用==MethodParameter[] parameters = getMethodParameters();==方法获取方法的参数
>
>   ​	保存在==Object[] args = new Object[parameters.length];==数组里面
>
>   ​	![1595675199851](C:\Users\pdd20\AppData\Roaming\Typora\typora-user-images\1595675199851.png)
>
>   ​	目的：找到所有注解
>
>   不是注解就解析参数
>
>   ​	解析参数：
>
>   1.  是否是注解 
>
>       注解：保存注解信息，解析得到值
>
>       没标注解：
>
>       ​	是否是原生api
>
>       ​	是否是model或者map
>
>       ​	是否是简单类型
>
>       ​	给arrtName赋值：atrrNmae(如果参数有@modelattribute就是注解的，没有就“” 空串)
>
>       ​		attrname使用参数的类型首字母小写，或者@modelattribute的值
>
>       ​		看隐含对象是否有attrname作为key对应的值
>
>       ​		看是否是@sessionattrbutes中的值
>
>       ​		如果都不是，就用反射创建一个对象
>
>       ​	把所有创建好的对象利用webdatabinder 将请求中的每个数据绑定到这个对象中
>
>   

# 四、srpingmvc的9大组件

~~~java
	/** MultipartResolver used by this servlet. */
	@Nullable
//文件上传解析器
	private MultipartResolver multipartResolver;

	/** LocaleResolver used by this servlet. */
	@Nullable
//和国际化有关
	private LocaleResolver localeResolver;

	/** ThemeResolver used by this servlet. */
	@Nullable
//主题
	private ThemeResolver themeResolver;

	/** List of HandlerMappings used by this servlet. */
	@Nullable
//handler的映射信息
	private List<HandlerMapping> handlerMappings;

	/** List of HandlerAdapters used by this servlet. */
	@Nullable
//handler的适配器
	private List<HandlerAdapter> handlerAdapters;

	/** List of HandlerExceptionResolvers used by this servlet. */
	@Nullable
//异常解析功能
	private List<HandlerExceptionResolver> handlerExceptionResolvers;

	/** RequestToViewNameTranslator used by this servlet. */
	@Nullable
//请求视图转换
	private RequestToViewNameTranslator viewNameTranslator;

	/** FlashMapManager used by this servlet. */
	@Nullable
//FlashMap的管理器，springmvc重定向携带数据功能
	private FlashMapManager flashMapManager;

	/** List of ViewResolvers used by this servlet. */
	@Nullable
//视图解析器
	private List<ViewResolver> viewResolvers;
~~~

什么时候初始化？

>   有一个onrefresh方法调用initStrategies(context);

>   ~~~java
>   protected void initStrategies(ApplicationContext context) {
>   		initMultipartResolver(context);
>   		initLocaleResolver(context);
>   		initThemeResolver(context);
>   		initHandlerMappings(context);
>   		initHandlerAdapters(context);
>   		initHandlerExceptionResolvers(context);
>   		initRequestToViewNameTranslator(context);
>   		initViewResolvers(context);
>   		initFlashMapManager(context);
>   	}
>   ~~~



>   ~~~java
>   private void initHandlerMappings(ApplicationContext context) {
>   		this.handlerMappings = null;
>   
>   		if (this.detectAllHandlerMappings) {
>   			// Find all HandlerMappings in the ApplicationContext, including ancestor contexts.
>   			Map<String, HandlerMapping> matchingBeans =
>   					BeanFactoryUtils.beansOfTypeIncludingAncestors(context, HandlerMapping.class, true, false);
>   			if (!matchingBeans.isEmpty()) {
>   				this.handlerMappings = new ArrayList<>(matchingBeans.values());
>   				// We keep HandlerMappings in sorted order.
>   				AnnotationAwareOrderComparator.sort(this.handlerMappings);
>   			}
>   		}
>   		else {
>   			try {
>   				HandlerMapping hm = context.getBean(HANDLER_MAPPING_BEAN_NAME, HandlerMapping.class);
>   				this.handlerMappings = Collections.singletonList(hm);
>   			}
>   			catch (NoSuchBeanDefinitionException ex) {
>   				// Ignore, we'll add a default HandlerMapping later.
>   			}
>   		}
>   ~~~
>
>   

# 五、视图解析器：ViewResolver

>   ### 1.任何方法的返回值，都被包装成modelandview对象
>
>      ![1595740719379](C:\Users\pdd20\AppData\Roaming\Typora\typora-user-images\1595740719379.png)
>
>   ###  2.视图渲染流程：将域中的数据在页面展示
>
>   1.  ==processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);==
>
>   2.  ###### 进入：==render(mv, request, response)==；渲染页面
>
>   3.  调用==view = resolveViewName(viewName, mv.getModelInternal(), locale, request)==；
>
>       ~~~java
>       protected View resolveViewName(String viewName, @Nullable Map<String, Object> model,
>       			Locale locale, HttpServletRequest request) throws Exception {
>       
>       		if (this.viewResolvers != null) {
>       			for (ViewResolver viewResolver : this.viewResolvers) {
>        //视图解析器根据方法的返回值得到view对象            
>       				View view = viewResolver.resolveViewName(viewName, locale);
>       				if (view != null) {
>       					return view;
>       				}
>       			}
>       		}
>       		return null;
>       	}
>       ~~~
>
>       

# 六、自己写一个视图解析器+视图：

## MyViewResolver：

>   ##### 1.实现：ViewResolver，Ordered 接口
>
>   ##### 2.通过判断开头是不是自己写的开头，来返回自己的视图类

~~~java
public class MyViewResolver implements ViewResolver, Ordered {
    private Integer order=0;

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return order;
    }
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        if (viewName.startsWith("myView:")) {
            return  new MyView();
        } else {
            return null;
        }
    }
}
~~~

## MyView：

>   ##### 1.实现：View 接口



~~~java
public class MyView implements View {
    @Override
    public String getContentType() {
        return "text/html";
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userDownload = (String) model.get("userDownload");
        response.setContentType("text/html");
        response.getWriter().write("<h1>下载成功:"+userDownload+"</h1>");
    }
}
~~~



## 控制器：



~~~java
@Controller ()
public class HelloController {
    //接受哪个请求
    @RequestMapping (value = "/download")
    public String post(Model model, @RequestParam ("userDownload") String userDownload) {
        model.addAttribute("userDownload", userDownload);
        return "myView:/success";
    }
}
~~~



## 注册自己的视图解析器：

~~~xml
 <!--自定义视图解析器-->
    <bean class="view.MyViewResolver">
        <property name="order" value="1"/>
    </bean>
~~~



## 客户页面：

~~~html
<form action="download">
	请输入你要下载的内容：
	<input type="text" name="userDownload">
	<input type="submit" value="点击下载">
</form>
~~~





# 七、写一个rest风格的crud

>   1.  c:create 创建
>   2.  r:retrieve 查询
>   3.  u:update 修改
>   4.  d:delete 删除



支持rest风格的filter

~~~java
 	<filter>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
~~~



# 八、数据转换.数据格式化.数据校验



>   1.  页面提交的数据都是字符串：数据类型的转换问题？ 比如 string =》 integer
>   2.  数据绑定的格式化问题？  比如日期
>   3.  数据校验：
>       1.  前端校验
>       2.  后端校验

## webDataBinder：数据绑定器

​	数据绑定期间的格式化，校验

webdatabingderfactory

>    创建databinder实例对象

conversionService

>   负责数据转换和格式化功能

validate:

>   数据校验
>
>   最终产生数据绑定结果 bindingdata



springmvc抽取bindingresult中的入参对象和校验错误对象

>   赋值给处理方法的响应入参



流程

>   1.  servletrequest 和处理方法的入参对象里面的参数由databinder进行类型转换和格式化
>   2.  格式化和类型转是：conversionService
>   3.  数据校验是：validate
>   4.  返回bindingresult，保存校验结果

## 自定义类型转换器

>   1.写一个类实现converter接口
>
>   2.写的converter要放进conversionService 中
>
>   3.把webdatabinder中的conversionService设置成我们写的converter
>
>   4.配置出自定义的	



~~~xml
<mvc:annotation-driven conversion-service="MyconversionService"></mvc:annotation-driven>
    <bean class="org.springframework.context.support.ConversionServiceFactoryBean">
        <property name="converters">
            <set>
                <bean class="myConversionService"/>
            </set>
        </property>
    </bean>
~~~





# 九、<mvc:annotation-driven> 的强大功能



![1595759273136](C:\Users\pdd20\AppData\Roaming\Typora\typora-user-images\1595759273136.png)



>   ~~~xml
>   <mvc:annotation-driven></mvc:annotation-driven>
>   <mvc:default-servlet-handler></mvc:default-servlet-handler>
>   
>   保证静态资源和动态资源
>   ~~~

# 十、数据校验

校验框架：

>   ```xml
>    <artifactId>hibernate-validator</artifactId>
>    <artifactId>hibernate-validator-annotation-processor</artifactId>
>    
>    <artifactId>classmate</artifactId>
>    <artifactId>jboss-logging</artifactId>
>    <artifactId>validation-api</artifactId>
>   ```



# 十一、json

## 1.Jackson

### 	a.导入依赖

~~~xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.10.0</version>
</dependency>
~~~

### 	b.jackson乱码

~~~xml
<mvc:annotation-driven>
    <mvc:message-converters>
        <bean class="org.springframework.http.converter.StringHttpMessageConverter">
            <constructor-arg value="utf-8"/>
        </bean>
        <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
            <property name="objectMapper">
                <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                    <property name="failOnEmptyBeans" value="false"/>
                </bean>
            </property>
        </bean>
    </mvc:message-converters>
</mvc:annotation-driven>
~~~

### 	c.使用：

~~~java
public class MyController {
   @RequestMapping("/hello")
    public String getHello(Person person, Model mod) throws Exception {
       
       ObjectMapper om = new ObjectMapper();
       String s = om.writeValueAsString(person);
        
       mod.addAttribute("person",s);
       return "hello";
   }
}
~~~

## 2.fastjson

### 	a.导入依赖

~~~xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.73</version>
</dependency>
~~~

### 	b.3个主要的类

jsonobject  代表json对象

>   1.  jsonObject实现了map接口，猜想底层是有map实现的
>   2.  通过get()方法获取json对象中的数据，也可以用size()，isEmpty()判断是否为空，本质是调用map接口中的方法完成的



jsonarray

>   1.  内部是有list接口的方法完成炒作





json代表jsonObject和jsonArray的转化











# 十二、SSM整合



[TOC]



# 依赖：



~~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns = "http://maven.apache.org/POM/4.0.0"
         xmlns:xsi = "http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation = "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>SSMBuild</artifactId>
    <version>1.0-SNAPSHOT</version>
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
    <repositories>
        <repository>
            <id>nexus-aliyun</id>
            <name>Nexus aliyun</name>
            <layout>default</layout>
            <url>https://maven.aliyun.com/repository/public</url>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
            <releases>
                <enabled>true</enabled>
            </releases>
        </repository>
    </repositories>
    <!--依赖
         junit，数据库驱动，数据库连接池，servlet，jsp，mybatis，mybatis-spring，spring，spring mvc
    -->

    <dependencies>
        <!--Junit-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.9</version>
        </dependency>
        <!--数据库驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
        <!-- 数据库连接池 -->
        <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.5.2</version>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.9</version>
        </dependency>

        <!--Servlet - JSP -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
             <version>4.0.1</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.2</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>

        <!--Mybatis-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.5</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.2</version>
        </dependency>

        <!--Spring-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.2.7.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.2.7.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.9.4</version>
        </dependency>
        <dependency>
            <groupId>commons-fileupload</groupId>
            <artifactId>commons-fileupload</artifactId>
            <version>1.3.3</version>
        </dependency>
        <dependency>
            <groupId>org.webjars.bower</groupId>
            <artifactId>jquery</artifactId>
            <version>3.5.1</version>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-io</artifactId>
            <version>1.3.2</version>
        </dependency>
    </dependencies>
    
</project>
~~~~





# mybatis整合



## 1.数据库环境搭建

~~~sql
CREATE DATABASE ssmbuild;
USE ssmbuild;
CREATE TABLE `books`(
    `bookID` INT NOT NULL AUTO_INCREMENT COMMENT '书id',
    `bookName` VARCHAR(100) NOT NULL COMMENT '书名',
    `bookCounts` INT NOT NULL COMMENT '数量',
    `detail` VARCHAR(200) NOT NULL COMMENT '描述',
    KEY `bookID`(`bookID`)
)ENGINE=INNODB DEFAULT CHARSET=utf8

INSERT INTO `books`(`bookID`,`bookName`,`bookCounts`,`detail`)VALUES
(1,'Java',1,'从入门到放弃'),
(2,'MySQL',10,'从删库到跑路'),
(3,'Linux',5,'从进门到进牢')
~~~

## 2.pojo类

Book

~~~java
public class Book {
   private  int bookID;
   private String bookName;
   private int bookCounts;
   private String detail;
}
~~~



## 3.mybatisConfig.xml

~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--配置数据库数据源  在spring中写-->
   <typeAliases>
        <typeAlias type="lk.pojo.Book" alias="book"/>
    </typeAliases>
    <mappers>
        <mapper resource="mapper/BooksMapper.xml"/>
    </mappers>
</configuration>
~~~



## 4.dao层：

### 	BookMapper

~~~java
public interface BookMapper {
    boolean addBook(Books books); 
    boolean delBook(int bookID);
    boolean updateBook(Books books);
    Books queryBook(int id);
    List<Books> queryAllBook();
}
~~~



### 	BookMapper.xml

>   增删改查功能

~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="dao.BookDao">
    <insert id="addBook">  
        insert into books ( bookName, bookCounts, detail) values(#{bookName},#{bookCounts},#{detail});
    </insert>
    <delete id="delBook">    
        delete from books where bookID = #{bookID};
    </delete>
    <update id="updateBook">     
        update books set  bookName= #{bookName}, bookCounts =#{bookCounts}, detail = #{detail} where bookID = #{bookID};
    </update>
    <select id="queryAllBook" resultType="book">        
        select * from books;
    </select>
    <select id="queryBook" resultType="book">       
        select * from books where bookID = #{id}
    </select>
</mapper>
~~~



## 5.service

### 	Bookservice

~~~java
public interface BookService {
    boolean addBook(Books books);
    boolean delBook(int bookID);
    boolean updateBook(Books books);
    Books queryBook(int id);
    List<Books> queryAllBook();
}
~~~



### 	BookServiceImpl

~~~java
public class BookServiceImpl  implements BookService{
    //service 调用dao层
    private BookMapper bookMapper;

    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public boolean addBook(Books books) {
        return bd.addBook(books);
    }

    @Override
    public boolean delBook(int bookID) {
        return bd.delBook(bookID);
    }

    @Override
    public boolean updateBook(Books books) {
        return bd.updateBook(books);
    }

    @Override
    public Books queryBook(int id) {
        return bd.queryBook(id);
    }

    @Override
    public List<Books> queryAl 
~~~



## 2.springService



~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 
service
@author likeLove
@time 2020 - 07 - 31  17:55
-->
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"

       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">
    <!--注解驱动 -->
    <context:annotation-config/>
    <!--1.扫描service-->
    <context:component-scan base-package="lk.service"/>
    <!--2.注入spring-->
    <bean class="lk.service.BookServiceImpl" id="bookServiceImpl">
        <property name="bookMapper" ref="bookMapper"/>
    </bean>
    <!--3.事务-->
    <bean class="org.springframework.jdbc.datasource.DataSourceTransactionManager" id="tx">
        <!--注入数据源-->
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!--4.aop事务支持-->

</beans>
~~~





# springMVC整合



## 1.spirngMvc.xml

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 

@author likeLove
@time 2020 - 07 - 31  18:15
-->
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <!--注解驱动 -->
    <context:annotation-config/>
    <!--1.mvc的注解驱动-->
    <mvc:annotation-driven/>
    <!--2.静态资源过滤-->
    <mvc:default-servlet-handler/>
    <!--3.包扫描-->
    <context:component-scan base-package="controller"/>
    <!--4.视图解析-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/pages/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
~~~





## 2.web.xml

>   1.DispatcherServlet
>
>   2.CharacterEncodingFilter
>
>   3.session-timeout

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--1.dispatchServlet-->
    <servlet>
        <servlet-name>SpirngMvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springMvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>SpirngMvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <!--2.解决乱码-->
    <filter>
        <filter-name>characterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceRequestEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>characterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!--3.session过期时间-->
    <session-config><session-timeout>15</session-timeout></session-config>
</web-app>
~~~





# controller.curd：

>   1.控制层的增删改查功能

~~~java
public class BookController {
    /* *//*@Autowired
    @Qualifier("bookServiceImpl")*/
    @Resource
    private BookService bookServiceImpl;

    /** 查询书籍 */
    @RequestMapping ("/getAllBooks")
    public String getAllBooks(Model mod) {
        List<Book> books = bookServiceImpl.queryAllBook();
        mod.addAttribute("list", books);
        return "getAllBooks";
    }

    /** 去添加书籍页面 */
    @RequestMapping ("/goAddBook")
    public String goAddBook() {
        return "addBook";
    }

    /** 添加书籍 */
    @RequestMapping ("/addBook")
    public String addBook(Book book) {
        boolean b = bookServiceImpl.addBook(book);
        if (b) {
            System.out.println("添加成功");
        }
        return "redirect:/book/getAllBooks";
    }

    /** 去修改书籍页面 */
    @RequestMapping ("/goUpdate/{id}")
    public String goUpdate(@PathVariable int id, Model model) {
        Book book = bookServiceImpl.queryBook(id);
        model.addAttribute("book", book);
        return "updateBook";
    }

    /** 修改书籍 */
    @RequestMapping ("/updateBook")
    public String updateBook(Book book) {
        boolean b = bookServiceImpl.updateBook(book);
        System.out.println(book);
        if (b) {
            System.out.println("修改成功");
        }
        return "redirect:/book/getAllBooks";
    }

    /** 删除书籍 */
    @RequestMapping ("/delBook/{id}")
    public String deleteBook(@PathVariable int id) {
        boolean b = bookServiceImpl.delBook(id);
        if (b) {
            System.out.println("删除");
        }
        return "redirect:/book/getAllBooks";
    }
}
~~~



# pages：

>   1.使用了bootstrap的样式

## 1.index.jsp:

>   1.首页

~~~jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>首页</title>
	<style type="text/css">
		a {
			text-decoration: none;
			color: black;
			font-size: 18px;
		}
		h3 {
			width: 250px;
			height: 50px;
			margin: 200px  auto;
			text-align: center;
			line-height: 50px;
			background: darkcyan;
			border-radius: 25px;
		}
	</style>
</head>
<body>
<h3><a href="${pageContext.request.contextPath}/book/getAllBooks">查询所有图书</a></h3>
</body>
</html>
~~~





## 2.getAllBooks.jsp

>   1.显示书籍列表



~~~jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>查看所有书籍</title>
	
	<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
</head>
<body>
<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div class="page-header">
				<h1>
					<small style="font-size: 38px">书城——显示所有书籍</small>
				</h1>
                //提示消息
				<div style="float: right;"><span style="color: red;font-size: 18px;font-weight: bold">${error}</span></div>
			</div>
			<div class="row">
				<div class="col-md-8 column">
					<a class="btn btn-primary" href="${pageContext.request.contextPath}/book/goAddBook">添加书籍</a>
					<a class="btn btn-primary" href="${pageContext.request.contextPath}/book/getAllBooks">显示所有书籍</a>
				</div>
				
				<div class="col-md-4 column">
					<div style="float: right">
						<form action="${pageContext.request.contextPath}/book/queryBookByQueryString"
						      class="form-inline">
							<input type="text" placeholder="请输入你要查找的书的名字" name="bookName">
							<input type="submit" value="查询" class="btn btn-primary">
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class=" row clearfix">
		<div class="col-md-12 column">
			<table class="table table-hover table-striped">
				<thead>
				<tr>
					<th>书籍编号</th>
					<th>书籍标题</th>
					<th>书籍数量</th>
					<th>书籍详情</th>
					<th>操作</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="book" items="${list}">
					<tr>
						<td>${book.bookID}</td>
						<td>${book.bookName}</td>
						<td>${book.bookCounts}</td>
						<td>${book.detail}</td>
						<td>
							<a href="${pageContext.request.contextPath}/book/goUpdate/${book.bookID}">修改</a>
							&nbsp;|&nbsp;
							<a href="${pageContext.request.contextPath}/book/delBook/${book.bookID}">删除</a>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
</body>
</html>

~~~



## 3.addBooks.jsp

>   1.添加图书页面

~~~jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>添加书籍</title>
	<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
</head>
<body>
<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div class="page-header">
				<h1>
					<small>书城——添加书籍</small>
				</h1>
			</div>
		</div>
	</div>
	<form action="${pageContext.request.contextPath}/book/addBook">
		<div class="form-group">
			<laber for="bookName">书籍标题:</laber>
			<input type="text" class="form-control" name="bookName" required/>
		</div>
		<div class="form-group">
			<laber for="bookCounts">书籍数量:</laber>
			<input type="text" class="form-control" name="bookCounts" required/>
		</div>
		<div class="form-group">
			<laber for="detail">书籍详情:</laber>
			<input type="text" class="form-control" name="detail" required/>
		</div>
		<button type="submit" class="btn btn-default">添加</button>
	</form>
</div>
</body>
</html>

~~~



## 4.updateBook.jsp

>   修改图书页面

~~~jsp

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>添加书籍</title>
	<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
</head>
<body>
<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div class="page-header">
				<h1>
					<small>书城——修改书籍</small>
				</h1>
			</div>
		</div>
	</div>
	<form action="${pageContext.request.contextPath}/book/updateBook">
		<input type="hidden"  name="bookID" value="${book.bookID}">
		<div class="form-group">
			<laber for="bookName">书籍标题:</laber>
			<input type="text" class="form-control" name="bookName" value="${book.bookName}" required/>
		</div>
		<div class="form-group">
			<laber for="bookCounts">书籍数量:</laber>
			<input type="text" class="form-control" name="bookCounts" value="${book.bookCounts}" required/>
		</div>
		<div class="form-group">
			<laber for="detail">书籍详情:</laber>
			<input type="text" class="form-control" name="detail" value="${book.detail}" required/>
		</div>
		<button type="submit" class="btn btn-default">修改</button>
	</form>
</div>
</body>
</html>
~~~





# 增加搜索书籍功能：

## 1.dao：

 

~~~java
  List<Book> queryBookByQueryString(String bookName);
~~~





~~~xml
  <select id="queryBookByQueryString" resultType="lk.pojo.Book" >
        select  * from books
        <where>
        <!--<choose>-->
        <!--<when test="_parameter != null ">-->bookName like #{bookName}<!--</when>-->
      <!--  </choose>-->
        </where>
    </select>
~~~





## 2.service：



~~~java
  List<Book> queryBookByQueryString(String bookName);
~~~





~~~java
@Override
public List<Book> queryBookByQueryString(String bookName) {
    return bookMapper.queryBookByQueryString(bookName);
}
~~~



## 3.controller



~~~java
@RequestMapping ("/queryBookByQueryString")
public String queryBookByQueryString(String bookName, Model model) {
    //判断用户输入是否为空
    if ("".equals(bookName) || bookName == null) {
        model.addAttribute("error", "请输入内容后搜索");
        return "getAllBooks";
    }
    List<Book> books = bookServiceImpl.queryBookByQueryString("%" + bookName + "%");
    //判断获取的list是否为空
    if (books.size() > 0) {
        model.addAttribute("list", books);
    } else {
        model.addAttribute("error", "未找到你需要的书籍");
    }
    return "getAllBooks";
}
~~~





# 增加数据库事务功能：

依赖：

~~~xml
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.4</version>
</dependency>
~~~





spring-service.xml



~~~xml
<!--4.aop事务支持-->
<!--配置事务通知-->
<tx:advice id="txAdvice" transaction-manager="transactionManager">
    <tx:attributes>
        //指定所有方法，策略：没有事务的生成事务
        <tx:method name="*" propagation="REQUIRED"/>
    </tx:attributes>
</tx:advice>
<aop:config>
    //事务切入点
    <aop:pointcut id="bookPoint" expression="execution(* lk.service.*.*(..))*"/>
    <aop:advisor advice-ref="txAdvice" pointcut-ref="bookPoint"/>
</aop:config>
~~~



# 增加拦截器功能：验证用户是否登录



## 1.拦截器实现类



>   1.  验证session中是否存在login这个字段
>   2.  如果不存在就转发到登录页面

~~~java
public class Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String login = (String) request.getSession().getAttribute("login");
        if ("true".equals(login)) {
            return true;
        }
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        return false;
    }
}
~~~



## 2.在springMVC中配置拦截器：

>   拦截和book有关的请求

~~~xml
<!--拦截器-->
<mvc:interceptors>
    <mvc:interceptor>
        <!--book-->
        <mvc:mapping path="/book/*" />
        <!--拦截器所在的类-->
        <bean class="lk.controller.Interceptor"></bean>
    </mvc:interceptor>
</mvc:interceptors>
~~~



## 3.登录控制器



>   处理和登录相关的请求
>
>   1.  判断用户是否输入了用户名和密码
>   2.  输入了就返回书籍列表
>   3.  没有则继续请求登录界面

~~~java
@Controller
@RequestMapping ("/user")
public class LoginController {
    @RequestMapping ("/goLogin")
    public String goLogin() {
        return "login";
    }

    @RequestMapping ("/login")
    public String login(String username, String pwd, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
       if (username != null && pwd != null) {
            HttpSession session = req.getSession();
            session.setAttribute("login", "true");
            session.setAttribute("username", username);
            return "redirect:/book/getAllBooks";
        } else {
            return "redirect:/user/goLogin";
        } 

    }
}
~~~





## 4.login.jsp



~~~jsp
<form action="${pageContext.request.contextPath}/user/login" method="post">
	用户名：
	<input type="text" name="username">
	密码:
	<input type="password" name="pwd">
	<input type="submit" value="登录">
</form>
~~~





# 文件的上传和下载：

## 1.依赖：

~~~xml
<dependency>
    <groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
    <version>1.3.3</version>
</dependency>
<dependency>
    <groupId>org.webjars.bower</groupId>
    <artifactId>jquery</artifactId>
    <version>3.5.1</version>
</dependency>
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-io</artifactId>
    <version>1.3.2</version>
</dependency>
~~~



## 2.springMVC.xml

>   配置文件上传下载的解析器

~~~xml
<!--文件上传和下载-->
<bean class = "org.springframework.web.multipart.commons.CommonsMultipartResolver" id = "multipartResolver">
    <property name = "defaultEncoding" value = "utf-8" />
    <property name = "maxUploadSize" value = "10485760" />
    <property name = "maxInMemorySize" value = "40960" />
</bean>
~~~



## 3.FileController

>   文件上传下载的控制器，页面请求里面的方法

~~~java
@Controller
@RequestMapping ("/file")
public class FileController {
    /* private static final String path = "D:\\Java\\project\\SSMBuild\\web\\upload";*/
    @RequestMapping ("/upload")
    public String fileUpload(@RequestParam ("file") CommonsMultipartFile file, HttpServletRequest req) throws Exception {
        String upLoadFileName = file.getOriginalFilename();
        if (!"".equals(upLoadFileName)) {
            System.out.println("上传了：" + upLoadFileName);
            String path = req.getServletContext().getRealPath("/upload");
            File f = new File(path);
            if (!f.exists()) {
                f.mkdir();
            }
            System.out.println("保存在了:" + f);
            InputStream is = file.getInputStream();
            FileOutputStream os = new FileOutputStream(new File(f, upLoadFileName));
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
                os.flush();
            }
            os.close();
            is.close();
        }
        return "../../index";
    }

    @RequestMapping ("/upload2")
    public String fileUpload2(@RequestParam ("file") CommonsMultipartFile file, HttpServletRequest req) throws Exception {
        String path = req.getServletContext().getRealPath("/upload");
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }
        file.transferTo(new File(f + "/" + file.getOriginalFilename()));
        return "../../index";
    }

    @RequestMapping ("/download")
    public String fileDownload(HttpServletRequest req, HttpServletResponse resp,@RequestParam ("fileName") String fileName) throws Exception {
        String path = req.getServletContext().getRealPath("/upload");
        System.out.println(fileName);
        resp.reset();
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("multipart/form-data");
        resp.setHeader("Content-Disposition",
                       "attachment;fileName=" + URLEncoder.encode(fileName, "utf-8"));
        File file = new File(path, fileName);
        FileInputStream is = new FileInputStream(file);
        ServletOutputStream os = resp.getOutputStream();
        byte[] bys = new byte[1024];
        int len = 0;
        while ((len = is.read(bys)) != -1) {
            os.write(bys, 0, len);
            os.flush();
        }
        os.close();
        is.close();
        return "../../index";
    }
}

~~~



## 4.下载、上传页面：

~~~jsp
<form action="${pageContext.request.contextPath}/file/upload" enctype="multipart/form-data" method="post">
	上传文件
	<input type="file" name="file">
	<input type="submit" value="上传">
</form>
<form action="${pageContext.request.contextPath}/file/download" method="get">
	输入你要下载的文件：
	<input type="text" name="fileName">
	<input type="submit" value="下载">
</form>
~~~



