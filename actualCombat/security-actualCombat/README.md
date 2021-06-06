# 简介

查看相关比较 -  如何一步步搭建认证服务器：https://www.yuque.com/ekoc/nb/nhl87h

~~~bash
oauth-server              # 认证服务器
resource-server           # 资源服务器
  --- config              # config 关于 认证服务器的配置
  ---------- support      
  ------------------ custom  # 自定义 认证服务器 一些处理机制
  ------------------ runner  # 加载权限信息到redis 
security-common           # 公共的一些类
  --- config              # config 目录下就是关于资源服务器的一些主要配置
  ---------- support
  ------------------ accesscontrol # 关于资源服务器鉴权的配置(在认证服务器启动的时候，将database中各个路径相关的权限，加载到redis中)
                                   https://www.yuque.com/ekoc/nb/lvlkgp
~~~

spring security 还是太麻烦了。最近发现了一个satoken，准备试试。http://sa-token.dev33.cn/