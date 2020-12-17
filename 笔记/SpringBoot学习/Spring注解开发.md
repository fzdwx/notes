## @bean

给ioc容器中注入实例对象



## @scope

bean实例的作用域

-   singleton：单例，默认，==在ioc创建的时候就会创建==
-   prototype：原型，每次都是新的，==只有获取的时候才会创建==
-   request：一个请求中都是同一个实例
-   session：同一个session都是一个实例
-   globel-session：全局session





## @lazy

单例bean，默认在ioc启动的时候就创建，添加在单例作用域的bean上，做到懒加载的效果







## @conditional 

**SpringBoot的大量使用：**

在自动配置类中==使用@conditional 注解==判断==是否有相关的类来决定是否配置相关内容==

**作用**

按照一定条件进行判断，满足条件就给容器中注册bean





## 给ioc中注册bean

1.  包扫描+组件标注注解(@controller,@service,@repository,@component)

2.  @bean

3.  @import

    1.  @import:需要导入到容器中的组件，容器就会自动注册这个组件，id默认是全类名
    2.  实现：importSelector，返回需要导入组件的全类名
    3.  实现：importBeanDefinitionRegistrar,手动注册bean到容器中

4.  实现FactoryBean(bean工厂)

    1.  获取工厂bean本身可以加一个&

        ![image-20200921100230359](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200921100237.png)







## bean的生命周期

### @bean

bean的创建->初始化->销毁的过程

1.  Spring中xml：可以指定，init-method，destory-method属性
2.  @bean注解也可以指定init-method，destory-method属性
3.  实现接口
    1.  实现 initializingBean接口，定义初始化逻辑
    2.  实现disposableBean接口，定义销毁逻辑
4.  使用jsr250规范提供的注解
    1.  @postConstruct：在bean创建完成并且属性赋值完成，来执行初始化方法
    2.  @preDestroy：在容器销毁bea之前进行通知我们进行清理工作
5.  实现BeanPostProcessor接口：bean的后置处理器
    1.  PostProcessorBeforeInitialization：在初始化之前
    2.  PostProcessorAfterInitialization：在初始化之后

生命周期：

单实例(单例)：

1.  bean对象创建
2.  bean对象初始化
3.  容器创建
4.  bean对象销毁
5.  容器关闭

多实例(原型)：

1.  在获取bean的时候才有创建并且初始化
2.  销毁：容器不会管理这个bean：不会调用销毁，需要手动





1.  bean的构造器																		实例化
2.  ​                                                                                                属性赋值
3.  PostProcessorBeforeInitialization：后置处理器              
4.  @postConstruct，init ：初始化方法                                 初始化
5.  PostProcessorAfterInitialization：后置处理器
6.  销毁



## 自动装配

Spirng利用依赖注入DI，完成ioc容器中各个组件的依赖关系的赋值







# SpringAop

在程序运行的时候动态的将某段代码切入到指定方法的指定位置进行的编程方式

@pointcut：抽取公共的切入点表达式

​	1、本类引用

​	2、其他的切面引用

@before：在目标方法执行之前

@after：在目标方法执行之后

@afterRetuin：目标方法返回之后

@afterThrowing：目标方法抛出异常之后

@aRound：环绕通知



给切面类加入@Aspect注解

在启动类中@EnableAspectJAutoProxy()

## @EnableAspectJAutoProxy

1.  引入了一个==AspectJAutoProxyRegistrar.class==给容器中注册==AnnotationAwareAspectJAutoProxyCreator==

2.  AnnotationAwareAspectJAutoProxyCreator：

    ![image-20200922131734876](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20200922131742.png)

3.  注册配置类，调用refresh，刷新容器
4.  registerBeanPostProcessors（beanFactory），注册bean的后置处理器来方便拦截bean的创建
    1.  先获取ioc容器已经定义了的需要创建对象的所有beanPostProcessor
    2.  给容器添加别的beanPostProcessor
    3.  优先注册实现了PriorityOrdered接口的beanPostProcessor
    4.  在给容器中注册实现了ordered接口的beanPostProcessor
    5.  注册实现优先级接口的beanPostProcessor
    6.  注册beanPostProcessor，实际上就是创建beanPostProcessor对象保存在容器中