# 1.分布式搜索引擎

**Lucene,Solr,Elasticsearch**

- 倒排索引
- Lucene是类库
- solr基于lucene
- es基于solr



## 倒排索引

根据属性的值来查找记录。这种索引表的每一项都包括一个属性值和包含该属性值的各个记录位置。由于不是根据记录来确定属性，而是根据属性来记录位置，所以叫倒排索引。 

![image-20210307152900500](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210307152900.png)

 



## 安装ES

~~~
tar -zxvf elasticsearch-7.9.3-linux-x86_64.tar.gz
mkdir /es
mv elasticsearch-7.9.3 /es/
cd /es/elasticsearch-7.9.3
~~~

配置：

~~~
mkdir data
cd config
vim elasticsearch.yml

cluster.name: like.es-cluster
node.name: es-node-1 
path.data: /es/elasticsearch-7.9.3/data
path.logs: /es/elasticsearch-7.9.3/logs
network.host: 0.0.0.0
cluster.initial_master_nodes: ["es-node-1"]
~~~

服务器环境

~~~
vim jvm.options
-Xms128m
-Xmx128m
~~~

启动服务

~~~
useradd es
mv 
chown -R es:es /es
cd bin
su es

vim /etc/security/limits.conf
* soft nofile 65536
* hard nofile 131072
* soft nproc 4096
* hard nproc 4096

vim /etc/sysctl.conf
vm.max_map_count=262145
sysctl -p


/es/elasticsearch-7.9.3/bin/elasticsearch -d
~~~

![image-20210307173158371](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210307173158.png)



## 安装插件head

使用chrome浏览器安装插件

~~~
https://chrome.google.com/webstore/detail/elasticsearch-head/ffmkiejjmecolpfloofpjologoblkegm?hl=zh-CN
~~~

![image-20210307173959577](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210307173959.png)





## 创建mapping

~~~json
put http://8.131.57.243:9200/index_mapping

{
 "mappings":{
 	"properties":{
 		"realname":{
 			"type":"text",
 			"index":true
 		},
 		"username":{
 			"type":"keyword",
 			"index":false
 		}
 	}
 }
}
~~~

![image-20210307181547869](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210307181548.png)

## analyze分析

测试分词效果

~~~json
post http://8.131.57.243:9200/index_mapping/_analyze

{
	"field":"realname",
	"text":"like is very nice"
}

{
    "tokens": [
        {
            "token": "like",
            "start_offset": 0,
            "end_offset": 4,
            "type": "<ALPHANUM>",
            "position": 0
        },
        {
            "token": "is",
            "start_offset": 5,
            "end_offset": 7,
            "type": "<ALPHANUM>",
            "position": 1
        },
        {
            "token": "very",
            "start_offset": 8,
            "end_offset": 12,
            "type": "<ALPHANUM>",
            "position": 2
        },
        {
            "token": "nice",
            "start_offset": 13,
            "end_offset": 17,
            "type": "<ALPHANUM>",
            "position": 3
        }
    ]
}
~~~

![image-20210307182923886](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210307182924.png)



## 增加mapping

~~~json
post http://8.131.57.243:9200/index_mapping/_mapping

{
 	"properties":{
 		"id":{
 			"type":"long"
 		},
 		"age":{
 			"type":"integer"
 		}
 	}

}
~~~

