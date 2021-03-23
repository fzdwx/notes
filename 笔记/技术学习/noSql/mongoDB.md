

https://www.runoob.com/mongodb/mongodb-tutorial.html

# 1.快速安装

~~~
docker pull mongo:latest
docker run -itd --name mongo -p 27017:27017 mongo

docker exec  -it mongo  bash
mongo
~~~





# 2.数据库操作

~~~bash
> use test                     			# 创建或切换数据库
switched to db test	
> show dbs					   			# 显示所有数据库
admin   0.000GB
config  0.000GB
local   0.000GB
> db.test.insert({"name":"like"})		# 在当前数据库中插入数据，集合是test(表)
WriteResult({ "nInserted" : 1 })
> show dbs
admin   0.000GB
config  0.000GB
local   0.000GB
test    0.000GB
> db.dropDatabase()						# 删除当前数据库
{ "dropped" : "test", "ok" : 1 }
~~~





# 3.集合操作

~~~bash
创建集合命令
db.createCollection(name,options)
~~~

![image-20210323191100741](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210323191107.png)



~~~
查看所有表
show tables
~~~



~~~
查看表结构
db.getCollection('users').stats()
db.users.stats()
~~~



~~~
删除表
db.users.drop()
~~~





# 4.文档操作

~~~
插入
db.user.save({"name":"like"})
db.user.insert(xxx)

uer1= {
    "name":"like",
    "age":18,
    "addr":{
        "country":"中国",
        "city":"湖北武汉",
        }
    }
 db.users.insert(uer1)
~~~





~~~json
更新
db.users.update(query,updateObj,options)

uer1= {
    "name":"keke",
    "age":18,
    "addr":{
        "country":"中国",
        "city":"湖北武汉",
    }
}
query={"name":"like"}
db.users.update(query,{"$set":uer1},false,true)


query={"name":"keke"}
db.users.update(query,{"$inc":{"age":5}})
~~~

<img src="https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210323192845.png" alt="image-20210323192845782" style="zoom:80%;" />

![image-20210323193505109](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210323193511.png)





# 5.查询

~~~bash
查询所有
db.users.find()
查询所有格式化
db.users.find().pretty()
去重
db.users.distinct("name")
范围
db.users.find({"age":23})  					# 等于
db.users.find({"age":{"$ne":23}})			# 不等于
db.users.find({"age":{"$gt":23}})			# 大于
db.users.find({"age":{"$gte":23}})          # 大于等于
db.users.find({"age":{"$lt":23}})           # 小于
db.users.find({"age":{"$lte":23}})          # 小于等于

db.users.find({"age":{"$gt":24,"$lt":18}})  # 18>x,x>24

~~~

![image-20210323195210168](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210323195210.png)