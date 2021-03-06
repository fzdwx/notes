# 1.什么是分布式架构

- 不同的业务(功能模块)分散部署在不同的服务器
- 每个子系统负责一个或多个不同的业务模块
- 服务之间可以相互交互通信



> 缺点：

- 架构复杂
- 部署多个子系统复杂
- 系统之间通信耗时



> 设计原则

- 异步解耦
- 幂等性
- 拆分原则
- 融合分布式中间件



# 2.Redis介绍

## Redis的线程模型

阻塞和非阻塞

![image-20210303153714595](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210303153714.png)



## Redis的发布和订阅

![image-20210305162051568](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305162051.png)



### 实例

![image-20210305162311915](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305162311.png)



~~~bash
订阅频道
	subscribe xiaoxiangge jinghanqing  # 订阅小翔哥、敬汉卿
	SUBSCRIBE jinghanqing shangguigu   # 订阅敬汉卿、尚硅谷
	subscribe j*					   # 订阅以j开头的
发送消息
	publish shangguigu kaixuele 
~~~

![image-20210305163102097](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305163102.png)



## Redis持久化

### **RDB机制**

默认如下配置：

\#表示900 秒内如果至少有 1 个 key 的值变化，则保存save 900 1#表示300 秒内如果至少有 10 个 key 的值变化，则保存save 300 10#表示60 秒内如果至少有 10000 个 key 的值变化，则保存save 60 10000

不需要持久化，那么你可以注释掉所有的 save 行来停用保存功能。

**②stop-writes-on-bgsave-error ：**默认值为yes。当启用了RDB且最后一次后台保存数据失败，Redis是否停止接收数据。这会让用户意识到数据没有正确持久化到磁盘上，否则没有人会注意到灾难（disaster）发生了。如果Redis重启了，那么又可以重新开始接收数据了

**③rdbcompression ；**默认值是yes。对于存储到磁盘中的快照，可以设置是否进行压缩存储。

**④rdbchecksum ：**默认值是yes。在存储快照后，我们还可以让redis使用CRC64算法来进行数据校验，但是这样做会增加大约10%的性能消耗，如果希望获取到最大的性能提升，可以关闭此功能。

**⑤dbfilename ：**设置快照的文件名，默认是 dump.rdb

**⑥dir：**设置快照文件的存放路径，这个配置项一定是个目录，而不能是文件名。

![image-20210305173114009](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305173114.png)

**RDB 的优势和劣势**

①、优势

（1）RDB文件紧凑，全量备份，非常适合用于进行备份和灾难恢复。

（2）生成RDB文件的时候，redis主进程会fork()一个子进程来处理所有保存工作，主进程不需要进行任何磁盘IO操作。

（3）RDB 在恢复大数据集时的速度比 AOF 的恢复速度要快。

②、劣势

RDB快照是一次全量备份，存储的是内存数据的二进制序列化形式，存储上非常紧凑。当进行快照持久化时，会开启一个子进程专门负责快照持久化，子进程会拥有父进程的内存数据，父进程修改内存子进程不会反应出来，所以在快照持久化期间修改的数据不会被保存，可能丢失数据。



### **AOF机制**

**1、持久化原理**

他的原理看下面这张图：

![img](https://pics3.baidu.com/feed/32fa828ba61ea8d3c2502e396b1b3848251f58b0.jpeg?token=394597ccd73bd15778c518b5c5be6998&s=2D62E7169D305F8A847546E20200B036)

每当有一个写命令过来时，就直接保存在我们的AOF文件中。

**2、文件重写原理**

AOF的方式也同时带来了另一个问题。持久化文件会变的越来越大。为了压缩aof的持久化文件。redis提供了bgrewriteaof命令。将内存中的数据以命令的方式保存到临时文件中，同时会fork出一条新进程来将文件重写。

![img](https://pics7.baidu.com/feed/09fa513d269759ee28454d2c4cea4b106c22dfd3.jpeg?token=86eda46b8bcd54a7a0e7d8a37d87bee8&s=EDB2A4579D317B824660D4DF0200E036)

重写aof文件的操作，并没有读取旧的aof文件，而是将整个内存中的数据库内容用命令的方式重写了一个新的aof文件，这点和快照有点类似。

**3、AOF也有三种触发机制**

（1）每修改同步always：同步持久化 每次发生数据变更会被立即记录到磁盘 性能较差但数据完整性比较好

（2）每秒同步everysec：异步操作，每秒记录 如果一秒内宕机，有数据丢失

（3）不同no：从不同步

![img](https://pics5.baidu.com/feed/b17eca8065380cd7df69859ba056a5325982816c.jpeg?token=a060f459d81c409c3d6c7208d2118888&s=AF4AA5574ED85CC841D04BE60300A036)

**4、优点**

（1）AOF可以更好的保护数据不丢失，一般AOF会每隔1秒，通过一个后台线程执行一次fsync操作，最多丢失1秒钟的数据。（2）AOF日志文件没有任何磁盘寻址的开销，写入性能非常高，文件不容易破损。

（3）AOF日志文件即使过大的时候，出现后台重写操作，也不会影响客户端的读写。

（4）AOF日志文件的命令通过非常可读的方式进行记录，这个特性非常适合做灾难性的误删除的紧急恢复。比如某人不小心用flushall命令清空了所有数据，只要这个时候后台rewrite还没有发生，那么就可以立即拷贝AOF文件，将最后一条flushall命令给删了，然后再将该AOF文件放回去，就可以通过恢复机制，自动恢复所有数据

**5、缺点**

（1）对于同一份数据来说，AOF日志文件通常比RDB数据快照文件更大

（2）AOF开启后，支持的写QPS会比RDB支持的写QPS低，因为AOF一般会配置成每秒fsync一次日志文件，当然，每秒一次fsync，性能也还是很高的

（3）以前AOF发生过bug，就是通过AOF记录的日志，进行数据恢复的时候，没有恢复一模一样的数据出来。





![img](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305173226.jpeg)



## Redis主从复制

![image-20210305173521267](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305173521.png)



### 环境搭建

由于我使用的docker环境

![image-20210305180231568](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305180231.png)

~~~bash
1.启动三个redis
2.docker inspect redis |grep ess       # 查看ip地址
	172.17.0.3                         # master
	172.17.0.2						   # s1
	172.17.0.5						   # s2
~~~

![image-20210305180356683](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305180356.png)

~~~bash
3.docker exec -it redis  bash 					# 进入容器内部
  redis-cli
  info replication     	  		                # 查看主从信息
4.修改主从信息
  SLAVEOF 172.17.0.3 6379
  save
~~~





![image-20210305181645036](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305181645.png)



## Redis无磁盘化复制

**磁盘化传输**: Redis主进程创建一个编写RDB的新进程放入磁盘文件。稍后，文件由父进程传输进程以增量的方式传递给从进程

**无磁盘化传输**：就是master会创建一个新的进程生成RDB文件，并且通过`socket`传输给slave节点，不会经过磁盘

~~~bash
repl-diskless-sync yes  
~~~







## Redis缓存过期机制

定期删除(主动)

~~~bash
hz 10    								# default
~~~

惰性删除(被动)

![image-20210305184953319](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305184953.png)





## 哨兵模式

当master节点挂掉之后，重新选一个master节点

















# 3.项目优化：首页轮播图使用redis缓存



~~~
思路：
	1.请求接口的时候先从redis中获取key对应的value
	2.不存在就从db中查询，然后保存到redis中作为缓存
问题：如果轮播图发生修改怎么办？
	1.后台运营系统，一旦发生更改，就可以删除缓存，然后重置缓存
	2.定时重置，清除缓存的时候尽量分开，避免缓存雪崩
~~~



```java
@GetMapping("carousel")
@ApiOperation(value = "获取首页轮播图列表")
public HttpJSONResult carousel() {
    List<Carousel> dbDataList = null;
    // 1.先从redis中获取缓存
    String redisCacheJson = redisUtil.get(REDIS_KEY_CAROUSEL);
    // 2.判断是否有缓存
    if (StringUtils.isBlank(redisCacheJson)) {
        dbDataList = carouselService.queryAllRootLevelCat(YesOrNo.YES.code);
        // 第一次查询，保存一份到redis中作为缓存
        redisUtil.set(REDIS_KEY_CAROUSEL, JsonUtils.objectToJson(dbDataList));
    } else dbDataList = JsonUtils.jsonToList(redisCacheJson, Carousel.class);

    return HttpJSONResult.ok(dbDataList);
}
```



# 4.购物车添加商品功能：将数据保存在redis中

~~~
思路：
	1.先从redis中获取该用户的购物车信息
	2.循环购物车，如果有前端传入的商品信息
	3.如果该商品存在，就添加对应的购买数量
	4.如果不存在，就添加商品
	5.如果购物车都存在，就新建购物车，然后添加商品
~~~



```java
@PostMapping("/add")
@ApiOperation(value = "添加商品到购物车")
public HttpJSONResult add(
    @RequestParam String userId,
    @RequestBody ShopCartBO shopCart) {
    if (StrUtil.isBlank(userId)) return HttpJSONResult.errorMsg("user Id 不能为空");
    log.info("购物车数据:{}", shopCart);
    List<ShopCartBO> shopCartList = null;

    // 前端用户在登录的时候，添加商品上购物车，会同时在后端同步购物车到redis中
    String shopCartRedisCacheJson = redisUtil.get(REDIS_KEY_SHOP_CART_PREFIX + userId);
    if (StringUtils.isNotBlank(shopCartRedisCacheJson)) {
        AtomicBoolean isHaving = new AtomicBoolean(false);
        // 1.判断添加到购物车的商品在购物车中是否存在，如果存在就累加数量
        shopCartList = JsonUtils.jsonToList(shopCartRedisCacheJson, ShopCartBO.class).stream().peek((cart) -> {
            if (cart.getSpecId().equals(shopCart.getSpecId())) {
                cart.setBuyCounts(cart.getBuyCounts() + shopCart.getBuyCounts());
                isHaving.set(true);
            }
        }).collect(Collectors.toList());
        // 2.不存在就添加到购物车中
        if (!isHaving.get()) {
            shopCartList.add(shopCart);
        }
    } else {
        // 3.用户第一次，新建购物车
        shopCartList = new ArrayList<>();
        shopCartList.add(shopCart);
    }
    // 4.覆盖原有购物车
    redisUtil.set(REDIS_KEY_SHOP_CART_PREFIX + userId, JsonUtils.objectToJson(shopCartList));

    return HttpJSONResult.ok();
}
```





# 5.购物车中删除商品

~~~
思路：
	1.从redis获取用户的购物车信息
	2.遍历，如果存在对应的商品就直接删除，然后覆盖
	3.不存在就不处理
~~~



```java
@PostMapping("/del")
@ApiOperation(value = "从购物车中删除商品")
public HttpJSONResult del(
        @RequestParam String userId,
        @RequestParam String itemSpecId) {
    if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId))
        return HttpJSONResult.errorMsg("请求参数不完整");
    log.info("用户id:{}-删除商品规格id:{}", userId, itemSpecId);
    //   如果用户已经登录，则需要同步删除后端购物车中的商品
    List<ShopCartBO> shopCartList = null;
    String shopCartRedisCache = redisUtil.get(REDIS_KEY_SHOP_CART_PREFIX + userId);
    if (StringUtils.isNoneBlank(shopCartRedisCache)) {
        shopCartList = JsonUtils.jsonToList(shopCartRedisCache, ShopCartBO.class).stream().map(shopCart -> {
            if (shopCart.getSpecId().equals(itemSpecId)) {
                return null;   // 存在就删除
            }
            return shopCart;
        }).collect(Collectors.toList());
        // 覆盖原购物车
        redisUtil.set(REDIS_KEY_SHOP_CART_PREFIX + userId, JsonUtils.objectToJson(shopCartList));
    }
    return HttpJSONResult.ok();
}
```



# 6.订单创建的时候从redis中获取购物车中对应的购买数量并移除对应的购物信息

![image-20210304200557597](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210304200604.png)

```java
/**
 * 刷新购物车
 * @param userId
 * @param shopCart 原购物车
 * @param removeShopCart 需要删除的信息
 * @return
 */
private String refreshShopCart(String userId, List<ShopCartBO> shopCart, List<ShopCartBO> removeShopCart) {
    Map<String, ShopCartBO> m = shopCart.stream()
                                        .collect(Collectors.toMap(ShopCartBO::getSpecId, e -> e));
    for (ShopCartBO s : removeShopCart) {
        ShopCartBO shopCartBO = m.get(s.getSpecId());
        shopCartBO.setBuyCounts(shopCartBO.getBuyCounts() - s.getBuyCounts());
        if (shopCartBO.getBuyCounts().equals(0)) {
            m.remove(s.getSpecId());
        } else {
            m.put(s.getSpecId(), shopCartBO);
        }
    }
    Collection<ShopCartBO> values = m.values();
    String s = "";
    if (values.size() == 0) {
        redisUtil.delete(REDIS_KEY_SHOP_CART_PREFIX + userId);
    } else {
        s = JsonUtils.objectToJson(values);
        redisUtil.set(REDIS_KEY_SHOP_CART_PREFIX + userId, s);
    }

    return s;
}
```





# 7.合并购物车(登录前后)



~~~
思路：
	1.reids中购物车为空
		a.cookie也为空->什么都不做
		b.cookie不为空->将cookie中的购物车信息写入到redis
	2.redis中购物车不为空
		a.cookie不为空->存在相同的商品，购买数量以cookie为准，然后合并购物车
		b.cookie为空->将redis中的购物车复制一份到cookie中
~~~



![image-20210305155836415](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305155836.png)



```java
/**
 * 用户注册成功后，同步cookie中的购物车到redis中
 * @param req http请求
 * @param userId 用户id
 */
private void syncShopCart(
        HttpServletRequest req, HttpServletResponse resp,
        String userId) throws UnsupportedEncodingException {
    // 0.获取cookie以及redis中的购物车信息
    String cookieShopCart = CookieUtils.getCookieValue(req, COOKIE_FOODIE_SHOPCART_KEY);
    String redisShopCart = redisUtil.get(REDIS_KEY_SHOP_CART_PREFIX + userId);

    // 1.根据各种情况处理
    if (StringUtils.isBlank(redisShopCart)) {
        if (StringUtils.isNotBlank(cookieShopCart)) {           // redis中为空且cookie不为空就让cookie覆盖
            cookieShopCart = URLDecoder.decode(cookieShopCart, "utf-8");  // 要解码，不然全是乱码  写在这为了减少一次判断
            redisUtil.set(REDIS_KEY_SHOP_CART_PREFIX + userId, cookieShopCart);
        } // 都为空就不操作
    } else if (StringUtils.isNotBlank(redisShopCart)) {
        if (StringUtils.isBlank(cookieShopCart)) {  // redis不为空且cookie为空，给cookie中存入redis中保存的购物车
            CookieUtils.setCookie(req, resp, COOKIE_FOODIE_SHOPCART_KEY, redisShopCart, true);
        } else {        // 都不为空，如果cookie和redis中都存在该购物信息，以cookie中的购物数为准
            List<ShopCartBO> shopCart = JsonUtils.jsonToList(cookieShopCart, ShopCartBO.class);
            Map<String, ShopCartBO> redis = JsonUtils.jsonToList(redisShopCart, ShopCartBO.class).stream()
                                                     .collect(Collectors.toMap(ShopCartBO::getSpecId, e -> e));
            for (ShopCartBO s : shopCart) {    // 存在就覆盖购买数量
                String specId = s.getSpecId();
                if (redis.get(specId) != null) {
                    redis.remove(specId);
                }
            }
            shopCart.addAll(redis.values());

            redisUtil.set(REDIS_KEY_SHOP_CART_PREFIX + userId, JsonUtils.objectToJson(shopCart));
        }
    }
}
```





# 8.分布式会话:登录、注册、登出增加保存token的操作

~~~
思路：
	1.注册和登录的时候生成对应的token，使用redis保存
	2.登出的时候删除保存在redis中的token
~~~



```java
/**
 * 生成返回页面的用户信息,并将当前用户的token保存 到redis中
 * @param createUser 创建用户
 * @return {@link UsersVO}
 */
private UsersVO conventUsersVO(Users createUser) {
    String uniqueToken = UUID.randomUUID().toString().trim();
    UsersVO toWebUser = new UsersVO();
    BeanUtils.copyProperties(createUser, toWebUser);
    toWebUser.setUserUniqueToken(uniqueToken);
    redisUtil.set(REDIS_USER_TOKEN_PREFIX + toWebUser.getId(), toWebUser.getUserUniqueToken());
    return toWebUser;
}
```

![image-20210305212622464](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305212622.png)

![image-20210305212633082](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305212633.png)



## 修改用户信息的时候也修改token

![image-20210305215108316](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305215108.png)

![image-20210305215149850](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20210305215149.png)





# 9.引入spring-session

```xml
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
</dependency>
```

