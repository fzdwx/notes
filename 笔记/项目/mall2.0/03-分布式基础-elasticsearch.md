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

su es
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







## 添加数据

~~~json
post http://8.131.57.243:9200/idx_demo/_doc/1
{
	"id":1,
	"name":"like",
	"age":18
}


{
    "_index": "idx_demo",
    "_type": "_doc",
    "_id": "1",
    "_version": 1,
    "result": "created",
    "_shards": {
        "total": 2,
        "successful": 1,
        "failed": 0
    },
    "_seq_no": 0,
    "_primary_term": 1
}
~~~

![image-20210308140443946](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210308140444.png)



## 修改数据

~~~json
post http://8.131.57.243:9200/idx_demo/_doc/5/_update

{
	"doc":{
			"name":"oqwe"
	}
}
~~~





## ES乐观锁

![image-20210308145614921](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210308145614.png)

~~~
post http://8.131.57.243:9200/idx_demo/_doc/5?if_seq_no=8&if_primary_term=1
{
	"doc":{
			"name":"ooooo"
	}
}
~~~

![image-20210308145911841](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210308145911.png)



## 安装IK分词器

![image-20210308152349589](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210308152349.png)

~~~
ik_max_word
ik_smart
~~~



~~~json
post http://8.131.57.243:9200/_analyze
{
	"analyzer":"ik_max_word",
	"text":"你知道我是谁吗"
}
~~~



~~~json
{
    "tokens": [
        {
            "token": "你",
            "start_offset": 0,
            "end_offset": 1,
            "type": "CN_CHAR",
            "position": 0
        },
        {
            "token": "知道",
            "start_offset": 1,
            "end_offset": 3,
            "type": "CN_WORD",
            "position": 1
        },
        {
            "token": "我",
            "start_offset": 3,
            "end_offset": 4,
            "type": "CN_CHAR",
            "position": 2
        },
        {
            "token": "是",
            "start_offset": 4,
            "end_offset": 5,
            "type": "CN_CHAR",
            "position": 3
        },
        {
            "token": "谁",
            "start_offset": 5,
            "end_offset": 6,
            "type": "CN_CHAR",
            "position": 4
        },
        {
            "token": "吗",
            "start_offset": 6,
            "end_offset": 7,
            "type": "CN_CHAR",
            "position": 5
        }
    ]
}
~~~





# 2.logstash

~~~
安装：
cd /es/logstash-7.9.3
mkdir sync
vim logstash-db-sync.conf
cp /es/mysql-connector-java-5.1.41.jar  .
~~~



## logstash-db-sync.conf

~~~json
vim logstash-db-sync.conf

input {
    jdbc {
        # 设置 MySql/MariaDB 数据库url以及数据库名称
        jdbc_connection_string => "jdbc:mysql://8.131.57.243:3306/mall2?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true"
        # 用户名和密码
        jdbc_user => "root"
        jdbc_password => "root"
        # 数据库驱动所在位置，可以是绝对路径或者相对路径
        jdbc_driver_library => "/es/logstash-7.9.3/sync/mysql-connector-java-8.0.22.jar"
        # 驱动类名
        jdbc_driver_class => "com.mysql.jdbc.Driver"
        # 开启分页
        jdbc_paging_enabled => "true"
        # 分页每页数量，可以自定义
        jdbc_page_size => "1000"
        # 执行的sql文件路径
        statement_filepath => "/usr/local/logstash-6.4.3/sync/mall-items.sql"
        # 设置定时任务间隔  含义：分、时、天、月、年，全部为*默认含义为每分钟跑一次任务
        schedule => "* * * * *"
        # 索引类型
        type => "_doc"
        # 是否开启记录上次追踪的结果，也就是上次更新的时间，这个会记录到 last_run_metadata_path 的文件
        use_column_value => true
        # 记录上一次追踪的结果值
        last_run_metadata_path => "/es/logstash-7.9.3/sync/track_time"
        # 如果 use_column_value 为true， 配置本参数，追踪的 column 名，可以是自增id或者时间
        tracking_column => "updated_time"
        # tracking_column 对应字段的类型
        tracking_column_type => "timestamp"
        # 是否清除 last_run_metadata_path 的记录，true则每次都从头开始查询所有的数据库记录
        clean_run => false
        # 数据库字段名称大写转小写
        lowercase_column_names => false
    }
}
output {
    elasticsearch {
        # es地址
        hosts => ["8.131.57.243:9200"]
        # 同步的索引名
        index => "mall-items"
        # 设置_docID和数据相同
        #document_id => "%{id}"
        document_id => "%{itemId}"
		template => "/es/logstash-7.9.3/sync/logstash-ik.json"
        template_name => "mall-ik-template"
        template_overwrite => true
        manage_template => false
    }
    # 日志输出
    stdout {
        codec => json_lines
    }
}
~~~

## mall-items.sql

~~~sql
vim mall-items.sql

SELECT
        i.id id,
        i.item_name itemName,
        i.sell_counts sellCounts,
        ii.url imgUrl,
        tempSpec.price_discount price,
        i.updated_time as updateTime 
        FROM items i
        LEFT join items_img ii on i.id = ii.item_id
        left join (
            select item_id, min(price_discount) as price_discount
            from items_spec
            group by item_id
        ) tempSpec on i.id = tempSpec.item_id
        where ii.is_main = 1
         and i.updated_time >= :sql_last_value
~~~

![image-20210308212258434](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210308212503.png)



## logstash-ik.json

~~~
{
    "order": 0,
    "version": 1,
    "index_patterns": ["*"],
    "settings": {
        "index": {
            "refresh_interval": "5s"
        }
    },
    "mappings": {
        "_default_": {
            "dynamic_templates": [
                {
                    "message_field": {
                        "path_match": "message",
                        "match_mapping_type": "string",
                        "mapping": {
                            "type": "text",
                            "norms": false
                        }
                    }
                },
                {
                    "string_fields": {
                        "match": "*",
                        "match_mapping_type": "string",
                        "mapping": {
                            "type": "text",
                            "norms": false,
                            "analyzer": "ik_smart",
                            "fields": {
                                "keyword": {
                                    "type": "keyword",
                                    "ignore_above": 256
                                }
                            }
                        }
                    }
                }
            ],
            "properties": {
                "@timestamp": {
                    "type": "date"
                },
                "@version": {
                    "type": "keyword"
                },
                "geoip": {
                    "dynamic": true,
                    "properties": {
                        "ip": {
                            "type": "ip"
                        },
                        "location": {
                            "type": "geo_point"
                        },
                        "latitude": {
                            "type": "half_float"
                        },
                        "longitude": {
                            "type": "half_float"
                        }
                    }
                }
            }
        }
    },
    "aliases": {}
}

~~~



~~~~
su es
/es/elasticsearch-7.9.3/bin/elasticsearch -d

启动
/es/logstash-7.9.3/bin/logstash -f /es/logstash-7.9.3/sync/logstash-db-sync.conf

cd /es/logstash-7.9.3/config
vim jvm.op
-Xms100m
-Xmx100m

~~~~





## 测试

![image-20210308221848200](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210308221848.png)





# 3.搜索商品接口

## controller

```java
@RestController
public class SearchController {

    @Autowired
    private ItemsSearchService ItemsSearchService;

    @GetMapping("/hello")
    public String hello() {
        return "You Know, for Search!";
    }

    @GetMapping("/es/search/items")
    public HttpJSONResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        if (StringUtils.isBlank(keywords)) {
            return HttpJSONResult.errorMsg("关键字为空");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = ES.PAGE_SIZE;
        }

        PagedGridResult pageGrid = ItemsSearchService.searchItems(keywords, sort, page, pageSize);

        return HttpJSONResult.ok(pageGrid);
    }
}
```

## service

```java
@Service
public class ItemsSearchServiceImpl implements ItemsSearchService {

    @Autowired
    private ESUtils esUtils;

    @Override
    public PagedGridResult searchItems(
            String keywords, String sort, Integer page, Integer pageSize) {
        // 1.构建查询条件
        SearchSourceBuilder sb = new SearchSourceBuilder();
//        sb.size(pageSize).from((page - 1) * ES.PAGE_SIZE);
        sb.query(QueryBuilders.matchQuery(Items.itemNameCol, keywords))
          .highlighter(new HighlightBuilder().field(Items.itemNameCol)
                                             .preTags(ES.preTag)
                                             .postTags(ES.postTag));
        // 2.查询
        ESRes esData = esUtils.queryDataBig(ES.ES_INDEX, sb);
        SearchHit[] searchHits = esData.getHits();

        // 3.封装返回条件
        List<Items> rows = Arrays.stream(searchHits).map(hit -> {
            HighlightField highInfo = (HighlightField) hit.getHighlightFields().values().toArray()[0];
            Map<String, Object> source = hit.getSourceAsMap();
            Items items = new Items();

            items.setItemId((String) source.get(Items.itemIdCol));
            items.setItemName(Arrays.toString(highInfo.getFragments()));
            items.setImgUrl((String) source.get(Items.imgUrlCol));
            items.setPrice((Integer) source.get(Items.priceCol));
            items.setSellCounts((Integer) source.get(Items.sellCountsCol));

            return items;
        }).collect(Collectors.toList());

        // 4.分页
        PagedGridResult res = new PagedGridResult();
        res.setRows(rows);
        res.setRecords(esData.getRecords());
        res.setTotal(esData.getTotal());
        res.setPage(page);
        return res;
    }
}
```