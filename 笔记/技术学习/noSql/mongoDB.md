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

