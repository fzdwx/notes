# 简介
查看相关笔记: https://www.yuque.com/ekoc/nb/lettuce#9994aa47

**redis实战**


## redis-demo
~~~java
/*
 * redis中各种数据类型的一些常用案例
 */
package cn.like.code.testCase

/*
 * spring boot 整合lettuce 并使用lettuce原生客户端
 */
package cn.like.code.web
~~~

测试接口

http://localhost:8080/set/hello/world

http://localhost:8080/get/hello

http://localhost:8080/hello


## redis-starter
笔记:https://www.yuque.com/ekoc/nb/tk8v8p

封装一个redis的boot starter 

1.使用lettuce作为客户端

2.使用时直接注入RedisService即可

3.提供单机和集群两种模式

4.具体配置查看LettuceConfigProperties


