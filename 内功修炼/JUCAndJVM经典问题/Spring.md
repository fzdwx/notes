# SpringMVC请求怎么拦截的

1.  DispartcherServlet拦截到用户请求
2.  doService();
    1.  给request请求设置一些参数
3.  doDispatch();
    1.  在handlerMapping中找到能处理这个请求的handler
    2.  根据这个handler获取hanlerAdapter，就是这个handler的执行器
    3.  handlerAdapter调用handler方法
4.  hanler()会返回一个modleAndView对象
    1.  会执行目标方法，调用invokeHandlerMehod()方法
5.  invokeHandlerMehod()
    1.  进行参数解析
    2.  会进行数据绑定DataBinder
    3.  封装相应的对象
    4.  invokeAndHandle：调用方法
    5.  调用getModleAndView()返回ModelAndView对象
6.  processDispatchResult()处理hanler返回的mv对象
    1.  如果需要会调用render方法渲染页面







# SpringIOC容器初始化

1.  创建Bean工厂，如果存在就销毁已经存在的，并加载bean的定义信息

    ==ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();==

2.  给bean工厂设置一些属性和classloader，后置处理器

    ==prepareBeanFactory(beanFactory);==

3.  调用bean工厂的后置处理器

    ==invokeBeanFactoryPostProcessors(beanFactory);==

4.  注册bean的后置处理器

    ==registerBeanPostProcessors(beanFactory);==

5.  注册监听器

    ==registerListeners();==

6.  完成bean工厂的初始化并创建bean，==finishBeanFactoryInitialization==

    1.  getBean

    2.  doGetBean

    3.  CreateBean

    4.  doCreateBean

        1.  实例化bean：获取实例，获取实例的类
        2.  实例化bean
            1.  设置属性
            2.  调用前置处理器
            3.  调用init method
            4.  调用后置处理器
        3.  返回实例化化的对象

7.  完成容器的刷新