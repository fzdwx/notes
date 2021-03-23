# 1.全文检索

==扫描文章的每一个词，对每个词建立一个索引，指明该词在文章中出现的位置和次数。当用户查询时根据建立的索引查找，类似于通过字典的检索字表查字的过程==

检：检查，索：索引

全文检索：（full-text retrieval(检索)）: 易文斌作为检索对象，找出含有指定词汇的文本

~~~
1.只处理文本
2.不处理语义
3.不区分大小写
4.结果列表有相关度排序	
~~~



# 2.安装

下载地址：https://www.elastic.co/cn/downloads/

/opt/app/es/

# 3.启动



~~~bash
# 启动命令
cd /opt/app/es/elasticsearch-7.9.2
su es
./bin/elasticsearch

 cd /opt/app/es/kibana-7.9.2-linux-x86_64
./bin/kibana --allow-root
~~~



>   es：

常见es安装问题：https://blog.csdn.net/qq_20394285/article/details/104355031

​	默认9200端口

![image-20201020133059121](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201020133106.png)



>   kibana：

​	默认5601端口

~~~yml
# 增加配置
server.host: "0.0.0.0"
i18n.locale: "zh-CN"
elasticsearch.hosts: ["http://47.112.150.204:9200"]
~~~



# 4.概述

在es中，一切都是json，面向文档。

elasticsearch在后台把**索引划分成多个分片**，每份分片

~~~bash
#索引（index）:
存储数据，相当于sql中的数据库，索引由名词（全为小写）进行标识。一个es集群中可以创建任意数目的索引。一个es索引由一个或多个lunce索引构成


#类型（type）
类型是索引内部的逻辑分区(category/partition)，一个索引内部可以定义一个或多个类型，相当于sql中的表

#文档（document）
是包含多个字段（field）的容器，每个字段拥有一个名字以及一个或多个值，

#字段（field）
~~~





# 5.倒排索引

**什么是倒排索引:** 

倒排索引也叫反向索引，通俗来讲正向索引是通过key找value，==反向索引则是通过value找key。==





# 6.ik分词器

**分词：**

把一段中文或者别的划分成一个个的关键字，在搜索的时候把自己的信息进行分词，会把数据库中或者索引库中的数据进行分词，然后进行一个匹配的操作，默认的中文分词是将每个字看成一个词，但是这显然是不合要求的，所以有了ik分词器。

ik_smart和ik_max_word

**ik_smart**：最少切分

**ik_max_word**：最细粒度划分



**查看效果**

>   ik_smart

~~~kibana
GET _analyze
{
  "analyzer": "ik_smart",
  "text": "李可真帅啊啊啊"
}
~~~



![image-20201020220350488](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201020220357.png)

>   ik_max_word

~~~
GET _analyze
{
  "analyzer": "ik_max_word",
  "text": "李可真帅啊啊啊"
}
~~~



![image-20201020220433111](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201020220433.png)





## 配置

添加自己的字典：

~~~
cd /opt/app/es/elasticsearch-7.9.2/plugins/ik/config
touch my.dic
vim my dic
	李可真帅
vim 
~~~

![image-20201020221054437](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201020221054.png)



测试：

可以看到==李可真帅==变成了不可分割的词了

![image-20201020221313581](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201020221313.png)





# 7.增删改查



## a.创建一个索引

~~~json 
PUT /索引名/类型名/文档id
{}

# 创建普通索引
PUT /test/user/1
{
	"name": "like",
	"age": 20
}

# 创建索引规则
PUT /test2
{
  "mappings": {
    "properties": {
      "name":{
        "type": "text"
      },
      "age":{
        "type": "long"
      },
      "birthday":{
        "type": "date"
      }
      
    }
  }
}
~~~



## b.查看索引

~~~
GET test2
~~~



## c.修改

~~~
POST /test/_doc/1
{
	"doc":{
	  "name": "qweqwe"
	}
}
~~~



## d.删除

~~~
Delete test
~~~





## e.搜索功能



~~~ json
# 创建
PUT /t/_doc/1
{
    "user" : "李可",
    "age": 18
}
PUT /t/_doc/2
{
    "user" : "李可",
    "age": 20
}

# 搜索
GET /t/_doc/_search
{
    "query": {
        "match": {
            "user": "李可"
        }
    }
}

# 结果过滤
GET /t/_doc/_search
{
    "query": {
        "match": {
            "user": "李可"
        }
    },
    // 指定查询的字段
    "_source": ["age"]  
}
# 排序
GET /t/_doc/_search
{
    "query": {
        "match": {
            "user": "李可"
        }
    },
    "sort": [
        {
            "age": {
                "order": "desc"
            }
        }
    ]
}

# 分页
GET /t/_doc/_search
{
    "query": {
        "match": {
            "user": "李可"
        }
    },
    "from": 0,
    "size": 1
}


# and
GET /t/_doc/_search
{
    "query": {
        "bool": {
            "must": [
                {
                    "match": {
                        "user": "李可"
                    }
                },
                {
                    "match": {
                        "age": 18
                    }
                }
            ]
        }
    }
}

# or
GET /t/_doc/_search
{
    "query": {
        "bool": {
            "should": [
                {
                    "match": {
                        "user": "李可"
                    }
                },
                {
                    "match": {
                        "age": 18
                    }
                }
            ]
        }
    }
}

# !=
GET /t/_doc/_search
{
    "query": {
        "bool": {
            "must_not": [
                {
                    "match": {
                        "user": "1"
                    }
                }
            ]
        }
    }
}


# 数据过滤
GET /t/_doc/_search
{
    "query": {
        "bool": {
            "must": [
                {
                    "match": {
                        "user": "李可"
                    }
                }
            ],
            "filter": {
                "range": {
                    "age": {
                        "gte":18,
                        "lte": 20
                    }
                }
            }
        }
    }
}

# 模糊查询
GET /t/_doc/_search
{
  "query": {
    "match": {
      "user": "李 可"
    }
  } 
}


# 精确查询多个值
GET testznmv/_doc/_search
{
  "query": {
    "bool": {
      "should": [
        {
          "term": {
            "name": "李可 Java name2"
          }
        },
         {
          "term": {
            "name": "李可 Java name3"
          }
        }
      ]
    }
  }
}
~~~



高亮

![image-20201023125111981](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201023125119.png)



![image-20201022130219261](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201022130226.png)









# 8.数值类型

-   字符串类型

    text，keyword

-   数值类型

    long，integer，short，byte，double，float，half，float，scaled

-   日期类型

    date

-   布尔类型

    boolean

-   二进制类型

    binary







# 9.SpringBoot集成ES



1.依赖

~~~xml
<dependency>
    <groupId>org.elasticsearch.client</groupId>
    <artifactId>elasticsearch-rest-client</artifactId>
    <version>7.9.3</version>
</dependency>
~~~



2.创建对象

~~~java
RestClient restClient = RestClient.builder(
    new HttpHost("localhost", 9200, "http"),
    new HttpHost("localhost", 9201, "http")).build();
restClient.close();
~~~



## 1.索引的增删改查

~~~java
@SpringBootTest()
public class EsConfigTest {

    @Resource
    public RestHighLevelClient client;

    private String myIndex = "java_create_index";

    /**
     * @throws IOException
     * 创建
     */
    @Test
    void testCreateIndex() throws IOException {
        // 1.创建索引请求
        CreateIndexRequest req = new CreateIndexRequest(myIndex);
        // 2.客户端执行创建请求
        CreateIndexResponse resp = client.indices()
                                         .create(req, RequestOptions.DEFAULT);

    }

    /**
     * @throws IOException
     * 获取索引
     */
    @Test
    void testGetIndex() throws IOException {
        GetIndexRequest req = new GetIndexRequest(myIndex);
        boolean b = client.indices()
                          .exists(req, RequestOptions.DEFAULT);
        System.out.println("b = " + b);
    }

    /**
     * @throws IOException
     * 删除
     */
    @Test
    void testDelIndex() throws IOException {
        DeleteIndexRequest req = new DeleteIndexRequest(myIndex);
        AcknowledgedResponse delete = client.indices()
                                            .delete(req, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }
}
~~~



## 2.doc的增删改查

```java
/**
* 创建文档
*/
@Test
void testAddDoc() {
    // 1.存储在索中的对象
    User user = new User("李可", 18);
    // 2.选择需要添加内容的索引
    IndexRequest indexReq = new IndexRequest(myIndex);

    // 3.PUT /java_create_index/_doc/1
    indexReq.id("1");
    indexReq.timeout(TimeValue.timeValueSeconds(1));
    // 将数据放入请求中
    indexReq.source(JSON.toJSONString(user),XContentType.JSON);

    // 4.发送请求
    IndexResponse resp = null;
    try {
        resp = client.index(indexReq, RequestOptions.DEFAULT);
    } catch (IOException e) {
        e.printStackTrace();
    }finally {
        System.out.println("resp.toString() = " + resp.toString());
        System.out.println("resp.status() = " + resp.status());
    }
}


/**
* 判断文档是否存在
*/
@Test
void testIsExists() {
    GetRequest getRequest = new GetRequest(myIndex, "1");
    getRequest.fetchSourceContext();
    getRequest.storedFields("_none_");
    boolean exists = false;
    try {
        exists = client.exists(getRequest, RequestOptions.DEFAULT);
    } catch (IOException e) {
        e.printStackTrace();
    }finally {
        System.out.println("exists = " + exists);
    }
}


@Test
void testGetDoc() {
    GetRequest getRequest = new GetRequest(myIndex, "1");
    GetResponse documentFields = null;
    try {
        documentFields = client.get(getRequest, RequestOptions.DEFAULT);
    } catch (IOException e) {
        e.printStackTrace();
    }finally {
        System.out.println("documentFields.getSourceAsString() = " + documentFields.getSourceAsString());
    }
}


/**
* 更新文档
*/
@Test
void testUpdateDoc() {
    UpdateRequest upReq = new UpdateRequest(myIndex, "1");
    upReq.timeout("1s");

    User user = new User("keke", 20);
    upReq.doc(JSON.toJSONString(user),XContentType.JSON);

    try {
        client.update(upReq,RequestOptions.DEFAULT);
    } catch (IOException e) {
        e.printStackTrace();
    }
}


@Test
void testDelDoc() {
    DeleteRequest delReq = new DeleteRequest(myIndex, "1");
    delReq.timeout("1s");

    DeleteResponse delete = null;
    try {
        delete = client.delete(delReq, RequestOptions.DEFAULT);
    } catch (IOException e) {
        e.printStackTrace();
    }finally {
        System.out.println("delete.status() = " + delete.status());
    }
}


// 批量增加 
@Test
void testBatch() {
    BulkRequest bulkRequest = new BulkRequest();
    bulkRequest.timeout("10s");

    ArrayList<User> users = new ArrayList<>();
    users.add(new User("like", 18));
    users.add(new User("like", 19));
    users.add(new User("like", 20));
    users.add(new User("like", 21));
    users.add(new User("like", 22));
    for (int i = 0; i < users.size(); i++) {
        bulkRequest.add(new IndexRequest(myIndex)
                        .id("" + i + 1)
                        .source(JSON.toJSONString(users.get(i)),XContentType.JSON));
        BulkResponse bulk = null;
        try {
            bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("bulk.hasFailures() = " + bulk.hasFailures());
        }
    }
}
```



## 3.搜索

1.搜索请求：new SearchRequest(); 

2.搜索条件构造：new SearchSourceBuilder(); 



```java
@Test
void testQuery() {
    // 1.查找的索引
    SearchRequest search = new SearchRequest(myIndex);

    // 2.搜索条件
    SearchSourceBuilder ssB = new SearchSourceBuilder();
    // 精确查询 termQuery，匹配所有 matchAllQuery
    ssB.query(QueryBuilders.termQuery("name", "like"));
    // 分页
    ssB.from(1);
    ssB.size(10);

    // 3.搜索的源
    search.source(ssB);

    // 4.执行搜索
    SearchResponse sResp = null;
    try {
        sResp = client.search(search, RequestOptions.DEFAULT);
    } catch (IOException e) {
        e.printStackTrace();
    }finally {
        SearchHits hits = sResp.getHits();
        String value = JSON.toJSONString(hits);
        System.out.println("value = " + value);
        System.out.println("============");
        for (SearchHit hit : hits.getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }
}
```



# 10.爬取数据



## 实体类

```java
//  实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JDSearch {
    private String title;
    private String price;
    private String img;
}


// 配置类
@Configuration
public class EsConfig {
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(RestClient.builder(
            new HttpHost("47.112.150.204", 9200, "http")));
    }
}


// 工具类
public class HtmlParseUtil {
    public static void main(String[] args) {
        int page = 1;
        int s = 1;
        for (int i = 0; i < 16; i++) {
            parse("vue", "" + page, "" + s).forEach(System.out::println);
            System.out.println("pgae:" + page + "===s:" + s);
            page += 2;
            s += 50;
        }
        System.out.println("====================");
        parse("你好", "" + page, "" + s).forEach(System.out::println);
    }

    public static List parse(String keyWord, String page, String s) {
        // https://search.jd.com/Search?keyword=java&page=7&s=151&click=1
        String url = "https://search.jd.com/Search?keyword=" + keyWord + "&page=" + page + "&s=" + s + "&click=1";
        ArrayList<JDSearch> jdSearches = new ArrayList<>();
        Document parse = null;
        try {
            // 解析网页
            parse = Jsoup.parse(new URL(url), 30000);
            // 获取id为 J_goodsList 的标签
            Element j_goodsList = parse.getElementById("J_goodsList");
            // 获取li标签
            Elements li = parse.getElementsByTag("li");
            for (Element element : li) {
                String img = element
                    .getElementsByTag("img")
                    .eq(0)
                    .attr("data-lazy-img");
                String price = element
                    .getElementsByClass("p-price")
                    .eq(0)
                    .text();
                String title = element
                    .getElementsByClass("p-name")
                    .eq(0)
                    .text();
                if (StrUtil.isAllNotBlank(img, title, price)) {
                    jdSearches.add(JDSearch
                                   .builder()
                                   .img(img)
                                   .price(price)
                                   .title(title)
                                   .build());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jdSearches;
    }

}

```



## 业务层

~~~java
@Service
public class JDsearchService {
    @Resource
    private RestHighLevelClient client;
    private String myIndex = "java_create_index";

    public boolean parse(String keyword) {
        List parse = HtmlParseUtil.parse(keyword, "1", "51");

        BulkRequest bulkReq = new BulkRequest();
        bulkReq.timeout("30s");
        for (Object o : parse) {
            bulkReq.add(new IndexRequest(myIndex).source(JSON.toJSONString(o), XContentType.JSON));

        }
        BulkResponse bulk = null;
        try {
            bulk = client.bulk(bulkReq, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return ! bulk.hasFailures();
    }

    public List<Map<String, Object>> searchPage(String keyword, int pageNo, int pageSize) {
        if (pageNo <= 1) {
            pageNo = 1;
        }
        // 1.构建搜索条件
        SearchRequest search = new SearchRequest(myIndex);
        SearchSourceBuilder ssb = new SearchSourceBuilder()
            .query(QueryBuilders.termQuery("title", keyword))
            .timeout(new TimeValue(60, TimeUnit.SECONDS))
            .from(pageNo)
            .size(pageSize);
        search.source(ssb);

        // 2.执行搜索
        SearchResponse seaResp = null;
        List<Map<String, Object>> res = new ArrayList<>();
        try {
            seaResp = client.search(search, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 3. 保存数据并返回
            for (SearchHit hit : seaResp
                 .getHits()
                 .getHits()) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                res.add(sourceAsMap);
            }
        }
        return res;
    }
}
~~~





## 接口

~~~java
@RestController
public class MyController {

    @Resource
    JDsearchService jDsearchService;


    // 实际上应该是put
    @GetMapping("/parse/{keyword}")
    public String parse(@PathVariable String keyword) {
        boolean parse = jDsearchService.parse(keyword);
        return "" + parse;
    }


    @GetMapping("/search/{keyword}/{pageNo}/{pagesSze}")
    public List<Map<String, Object>> searchPage(@PathVariable String keyword, @PathVariable int pagesSze,
                                                @PathVariable int pageNo) {
        return jDsearchService.searchPage(keyword, pageNo, pagesSze);
    }
}
~~~

