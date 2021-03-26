# 1.数据类型



## boolean类型

~~~javascript
${flag?c}
${flag?string}
${flag?string('喜欢','不喜欢')}
~~~





## 日期类型

~~~javascript
年月日  ?date
时分秒  ?time
年月日时分秒 ?datetime
指定格式 ?string("自定义格式") y 年 M 月 d 日 H 时 m 分 s 秒


${now?date}
${now?time}
${now?datetime}
${now?string("yyyy年MM月dd日HH时mm分ss秒")}
~~~





## 数值类型

~~~
${num?c} 转换成字符串类型
${num?string.currency} 将数值转换成货币类型字符串
${num?sting.perccent} 将数值转换成百分比类型的字符串
${num?sting["0.##"]} 保留两位小数 。
~~~



## 空值

~~~
不存在的值 保存
值为null 报错
空字符串 没问题

通过！指定缺失的值
${str!} 如果为空 则是空字符串
${str!"str的数据不存在"} 不存在就显示默认值
${(str??)string} 使用?? 判断字符串是否为空，返回boolean 如果想要输出转换成string
~~~





## list

![image-20210325200123034](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210325200130.png)

![image-20210325200744555](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210325200744.png)

![image-20210325201059599](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210325201059.png)





## map

![image-20210325201454134](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210325201454.png)





# 2.常用指令



## assign

![image-20210325203602683](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210325203602.png)



## if

![image-20210325204736550](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210325204736.png)





## 自定义指令 macro

![image-20210325205338339](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210325205338.png)





![image-20210325205448200](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210325205448.png)

![image-20210325210456624](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210325210456.png)

![image-20210325210632291](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210325210632.png)





## import

![image-20210325211630287](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210325211630.png)





# 3.运算

![image-20210325212818999](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210325212819.png)

![image-20210325212850854](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210325212850.png)

![image-20210325212856605](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210325212856.png)