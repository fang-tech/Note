# Redis中的数据结构

## String

### 数据类型详解

String类型使用的核心数据结构是SDS(简单动态字符串), 这种存储字符串的方式相比于c原生字符串的优点是

- 能动态拓展缓冲区, 保证不会在拓展字符串的时候不会缓冲区溢出
- 能以O(1)的时间复杂度获取到字符串的长度 : SDS数据结构会记录字符串的长度
- 能存储二进制数据 : 不再通过'\0'作为字符串的结尾标识符, 而是通过len来标记字符串该在什么时候结束, 内部的数据都是以二进制的形式存储, 所以不光能存储文本, 还能存储视频, 图片等数据

这个数据类型的最大存储容量是`512M`

这个数据类型内部编码有三种 : 用于存储数字的`INT`, 存储非数字的`embstr`, `raw`

- **INT** : 在string类型中的数据是数字, 并且数字能使用long类型存储, 这个时候就会采用INT内部编码, 会将整数值保存在字符换对象结构里的ptr属性值里
![int](https://cdn.xiaolincoding.com/gh/xiaolincoder/redis/%E6%95%B0%E6%8D%AE%E7%B1%BB%E5%9E%8B/int.png)
- **embstr** : 在字符串的长度 \<= 32**字节(不是字符)** (redis2.x) 的时候, 会将对象的编码设置成embstr
![embstr](https://cdn.xiaolincoding.com/gh/xiaolincoder/redis/%E6%95%B0%E6%8D%AE%E7%B1%BB%E5%9E%8B/embstr.png)
- **raw** : 在字符串的长度 > 32字节的时候(redis 2.x), redis会将字符串的编码设置成raw
![raw](https://cdn.xiaolincoding.com/gh/xiaolincoder/redis/%E6%95%B0%E6%8D%AE%E7%B1%BB%E5%9E%8B/raw.png)

> 为什么要有raw和embstr两种SDS的编码设置, 两者的区别是什么?

embstr相比于raw编码方式, 是一种更加更加快速的存储方式, 但是可变性更差, 是一种对于存储小字符串的优化方案

- 通过raw编码的时候, redis会调用两次malloc, 分别为redisObject和SDS分配内存, 这两块内存是不保证连续的
- 通过embstr编码, redis只会调用一次malloc, 为redisObject和SDS分配一次内存, 存储在一个连续的空间中, 这个时候CPU Cache的命中率更高, 更符合空间局部性原理
- 从embstr的定义和目的也能看出来, embstr实际上是个 **不可变** 的编码方式, 每当以embstr方式编码的对象发生**append**实际上会先将字符串类型的编码形式**改为raw**

> 不同版本的Redis, embstr和raw的边界是不同的

- redis 2.x : 32字节
- redis 3.0-4.0 : 39字节
- redis 5.0以后 : 44字节

### 常用指令

```redis
SET key value
# OK

GET key
# value

STRLEN key
# 5

EXISTS key
# 1

DEL key
# 1
```

- 批量设置

```redis
MSET key1 value1 key2 value2 ....
# OK

MGET key1 key2 ...
# 1) value1
# 2) value2
```

- 计数器

```redis
SET cnt 1
# OK

INCR cnt
# (integer) 2

DECR cnt 
# (integer) 1

INCRBY cnt 2
# (integer) 3

DECRBY cnt 3
# (integer) 0
```

- 过期时间

```redis
EXPIRE key 60 (60s后过期)

TTL key
# 51

SET key value EX 60

SETEX key 60 value
```

- 不存在就插入

```redis
SETNX key value
# (integer) 1
```

### 应用场景

#### 缓存对象

- 直接缓存对象的JSON串

#### 常规计数

- redis内部因为是单线程的, 能保证对其中数字的加减是**原子操作**

#### 共享Session

一般Session存储在用户登陆的服务器上, 并以此让用户相近的登录不需要再重新登陆, 记录一定量的用户信息, 但是在分布式的语境下, 这个方案将不再适用, 因为用户会被随机分配到不同的服务器上, 那么很可能用户再第二次登陆的时候, 被分配到与第一次登陆的时候不同的服务器, 这个时候就会丢失Session信息

- 通过设置一个专门的Session Redis实例, 所有的服务器都将Session信息存储在这个实例中, 也通过这个实例获取Session

![Session](https://cdn.xiaolincoding.com/gh/xiaolincoder/redis/%E6%95%B0%E6%8D%AE%E7%B1%BB%E5%9E%8B/Session2.png)

#### 分布式锁

通过`SET`方法的EX参数, 我们能实现这个值有才插入的操作, 通过这个方式, 我们能实现一个分布式的互斥锁
`SET lock_key unique_value EX PX 10000`

- unique_value是用于分辨这个锁是由哪个服务器创建的, 只有符合要求的服务器, 也就是携带了unique_value的服务器, 才能正确解锁
- EX : 如果存在则插入
- PX 10000 : 过期时间为10s, 确保不会因为客户端出现网络问题的时候, 锁得不到释放
- 当SETNX失败的时候, 说明这个锁已经被别人获取了, 成功SETNX的服务器成功获取到了锁, 只有等待SETNX的服务器删除这个锁, 锁才得到了释放

因为比对unique_value, 如果相同则删除的操作是两个操作, 我们需要通过lua脚本保证两个操作的原子性

```lua
// 释放锁时，先比较 unique_value 是否相等，避免锁的误释放
if redis.call("get",KEYS[1]) == ARGV[1] then
    return redis.call("del",KEYS[1])
else
    return 0
end
```

## List

### List数据结构详解

List内部的存储方式有**双向链表**和**压缩链表**两种, 最多能存储2^32 -1 个元素, 存储的元素的数据类型是String

- 如果List的元素数量 \< 512(可通过`list-max-ziplist-value`配置), 并且每个节点的长度 \< 64, 就会以压缩链表的形式存储
- 如果上面两个条件不是都满足, 就会选用双向链表作为内部存储结构

但是**在Redis3.2版本以后, List数据类型的底层数据结构就只由 quicklist来实现了**

### 常用命令

```redis
LPUSH key value [value...]
RPUSH key value [value...]

# 移除并返回
RPOP key
LPOP key

# 返回范围内的元素, 从strat到end, 从0计数
LRANGE key start end

# timeout是超时的时间
BLPOP key [key...] timeout
```

### 应用场景

#### 消息队列

实现消息队列需保证 **消息保序, 处理重复的消息和保证消息的可靠性**

- **消息保序** : List本身就是有序存储的, 我们可以通过生产端 `LPUSH/RPUSH`, 消费端 `RPOP/LPOP`的方式来实现消息队列
  - 但是生产端产生消息的时候, 消费端并不知道, 如果要实现消费端监听消费队列, 使用RPOP就需要使用循环RPOP获取消费队列中的消息. 这样会大幅浪费CPU的性能, 解决方案就是List提供了BRPOP, 阻塞式获取, 如果消息队列中没有消息, 消费端就会阻塞直到获取到消息(可以通过设置timeout来决定阻塞的时间)
- **处理重复的消息** : 只能通过外部传入全局唯一ID来唯一标识信息, 消费端记录消费过的信息, 在消费信息之前检查当前消息的ID是不是存在于已经消费set中
- **保证消息的可靠性** : 如果消费端在获取了消息以后出现了错误, 比如断电等, 就会导致消息的丢失, 为了留存消息, List提供了`BRPOPLPUSH`操作, 在**阻塞获取消息以后,还会留存一份备份**, 这样如果消费者在阅读了消息但是没有正确处理消息的时候, 能重新从备份List中获取消息

> List作为消费队列的缺陷

List实现的消费队列无法实现消费组的功能 : 多个消费者消费同一个信息, 但是Stream可以

## Hash

Hash类型实现的是Hash集合, 其value是field : value的集合, 形如field : value=[{field1:value1}, {field2:value2}, ... , {fieldN:valueN}], 因此Hash特别适合存储对象

> 什么时候使用String类型存储对象, 什么时候是哦那个Hash存储?

String类型实际上是使用JSON串存储的对象, 所以变动是很不方便的
一般对象使用String + JSON存储, 需要频繁变动的部分使用Hash存储

### 内部实现

- 在元素的数量 \< 512个(可以通过`hash-max-ziplist-entries`来配置), 所有值大小 < 64字节的时候, Hash类型使用**压缩列表**实现
- 在不满足上述情况的条件下, 使用**哈希表**作为数据结构

**在Redis7.0以后, 压缩列表已经弃用了, 被替换成了listpack**

### 常用命令

```redis
HSET key field value

HGET key field

HMSET key field value [field value ...]

HMGET key field [field]

HLEN key

HGETALL key

HINCRBY key field 10
```

### 应用场景

#### 购物车

对于一个购物车场景, 就是一个很适用的非存储对象的场景
![购物车](https://cdn.xiaolincoding.com/gh/xiaolincoder/redis/%E6%95%B0%E6%8D%AE%E7%B1%BB%E5%9E%8B/%E8%B4%AD%E7%89%A9%E8%BD%A6.png)

- field : 用户ID
- value : (商品ID : 商品购买数量)

## Set

无序集合, 形如 field : [member1, member2, ..., memberN]

### 内部实现

- 如果元素的数量小于512(默认, 由`set-maxintentries`配置), 使用**整数集合**实现
- 如果元素的数量大于等于512的时候, 使用**哈希表**实现

### 常用命令

```redis
SADD key member [member...]

SREM key member

# 获取集合中的全部元素
SMEMBERS key member

# 集合中的元素数量
SCARD key

# member在不在key集合中
SISMEMBER key member

# 从key集合中随机取出count个元素, 不删除
SRANDMEMBER key [count]

# 从key集合中取出并删除count个元素
SPOP key [count]
```

- 运算操作

```redis
# 交集操作
SINER key1 key2 [keyn...]

SINERSTORE destkey key1 [keyn...]

# 并集操作
SUNION key1 key2 [keyn...]

SUNIONSTORE destkey key1 key2 [keyn...]

# 差集操作
SDIFF key1 key2

SDIFF STORE destkey key1 key2 [keyn...]
```

### 应用场景

应用场景主要围绕, **无序, 不重复, 支持交集并集等集合操作**来构建
**不过Redis中集合的运算的时间复杂度较高, 在主从集群的场景下, 可能会造成Redis实例的阻塞, 所以一般在从库完成聚合统计**

#### 点赞

这个功能就是应用不重复, 使用set记录点赞用户的ID, SCARD来获取到这个文章的所有点赞人数, 通过SET的增删来增删点赞用户的信息

#### 共同关注

这个功能属于应用集合的运算操作, 可以通过交集运算出来两个用户的共同好友等    

#### 抽奖活动

这个功能也是为了应用了不重复的性质, 并且用到了Redis中`SRANDOMMEMBER field cnt`随机不去除获取cnt个集合中的value, `SPOP`随机去除获取cnt个集合中的value

## ZSET

相比于SET就是多了个排序的功能, 是一个有序的集合, 所以一个value现在有两部分(value, score), 通过score来进行排序

### 内部实现

- 如果元素个数小于128, 并且每个每个元素的大小都小于64字节, 会采用**压缩列表**实现
- 如果不满足上面的情况, 就会使用**跳表**实现

**在Redis7.0以后, 压缩列表已经弃用了, 被替换成了listpack**

### 常用命令

```redis
ZADD key score member [[score member]...]

ZREM key member [member...]

ZSCORE key member


```

### 应用场景

对于有权重值的表, 我们都能使用ZSET来实现, 相较于SET, ZSET不支持差集集合运算

## BitMap

### BitMap数据结构详解

内部使用String类型存储二进制字节数组

### 常用命令

- bitmap基本操作

```redis
# 设置偏移量offset位置的值为value(0/1)
SETBIT key offset value

# 获取偏移量offset位置的值
GETBIT key offset

# 统计从start位置到end位置之间的1的数量
# 这里的start和end是以字节为单位的
BITCOUNT key start end
```

- bitmap运算操作

```redis
# BitMap之间的运算, 也就是二进制数之间的运算, 
# opreation 执行的运算操作
    # AND 与运算
    # NOT 非运算, 非运算是唯一一个单目的运算, 后面的key只有一个
    # XOR 异或
    # OR 或运算
# destkey 存储结果的key
# key [keys...] 参与运算的key
# 参与运算的时候, 较短的key中的空位会被视作0
BITOP [operation] [destkey] key [keys...]

# key中第一次出现value(0/1)的位置
BITPOS key value
```

### 应用场景

#### 签到统计

现在我们需要记录用户在4月的登录情况

基本操作

- 用户登录

```redis
SETBIT sign uid:sign:100:202504 1
```

- 用户退出

```redis
SETBIT sign uid:sign:100:202504 0
```

- 统计用户在4月的登录情况

```redis
BITCOUNT uid:sign:100:202504
```

> 获取4月的第一次登陆的日期 ?

```redis
BITPOS uid:sign:100:202504
```
因为返回的数字是从0开始计数的, 所以最后计算的结果是value + 1

> 统计连续签到7天的用户总数

使用日期作为key, 用户ID作为offset, 然后将这些key做AND运算, 再统计destkey中的1的个数就能得出最新的七天的用户总数

```redis
BITOP AND destkey day:1 day:2 day:3 ... day:7
BITCOUNT destkey
```

## HyperLogLog

### 介绍

适用于超大规模的基数统计, 也就是我们需要统计一个集合中有多少不同的元素. 不论统计的数据量有多少, HyperLogLog数据结构都能使用固定的12KB大小的内存完成统计, 但是最后的出来的数量值并不是精确的, 标准误差率是0.81%

### 内部实现

内部实现主要是数学原理, 以后再说

### 常用命令

就当作只能添加, 合并, 统计集合中的元素个数的集合操作就行

```redis
# 向HyperLogLog中添加元素
PFADD key element [elements...]

# 统计HyperLogLog中的元素个数
PFCOUNT key

# 合并多个HyperLogLog集合
PFMERGE destkey sourcekey [sourcekey...]
```

### 应用场景 : 百万级网站UV计数

UV : Unique Visitor, 独立访客
统计一个网站某一段时间的独立访客数量

- 添加访客 : `PFADD uv:202504 user1 user2`
- 统计访客数量 : `PFCOUNT uv:202504`

## Stream

这个数据类型实在Redis5.0版本引入的, 就是为专门解决使用Redis作为消息队列的情景, 这个数据类型能自动生成全局唯一ID, 而不再用生产者自己实现

### 应用场景 : 消息队列

- 生产者添加消息

```redis
# 向mymq消息队列插入一条消息
# * 表示由redis自己生成全局唯一ID
# 生产的消息的key是name, value是xiaolin
XADD mymq * name xiaolin
"1654254953808-0" # result
```

添加消息成功以后返回生成的全局ID, ID由两部分组成

- 1654254953808 : 数据插入的时候, 以毫秒为单位服务器的时间戳
- 0 : 在这1ms中的第几个消息

- 消费者消费消息

```redis
XREAD STREAMS mymq 1654254953808-0
1) 1) "mymq"
   2) 1) 1) "1654254953808-0"
         2) 1) "name"
            2) "xiaolin"
```

如果想实现**阻塞读**, 加上BLOCK \[time\]选项

```redis
# $表示读取最新的一条消息
XREAD BLOCK 10000 STREAMS mymq $
(nil)
(10.00s)
```

- STREAM支持通过消费组来消费消息, 同一个组内不能消费同一条消息, 但是不同组之间能重复消费同一条消息

- 创建消费组

```redis
# 创建消费组group1
# 0-0表示从第一条消息开始读
XGROUP CREATE mymq group1 0-0

XGROUP CREATE mymq group2 0-0
```

- 通过消费组从队列中读取消息

```redis
# 消费组group1中的消费者consumer1从mymq中消费一条消息
XREADGROUP GROUP group1 consumer1 COUNT 1 STREAMS mymq

XREADGROUP GROUP group1 consumer2 COUNT 1 STREAMS mymq

XREADGROUP GROUP group1 consumer3 COUNT 1 STREAMS mymq
```

> 使用消费组的原因

消费组是组件本身提供的一种可靠的消息分发机制, 通过将一个流中的消息分发给不同组中的消费者, 实现负载均衡, 水平拓展了系统的能力

> 基于STREAM实现的消费队列, 如何保证消费者在宕机或者故障的时候, 消息不丢失

Redis内部会维护一个Pending list队列, 用于留存消费者阅读的消息, 只有在消费阅读后, 并成功消费发送了ACK给Redis以后, Pending list中留存的消息才会被删除

可以使用`XPENDING`命令来查看**已经被读取, 但是还没有被确认**的消息

```redis
XPENDING mymq group2
```

如果想查看组内的某个具体的消费者消费了什么数据

```redis
XPENDING mymq group2 - + 10 consumer2
```

一旦消息已经被consumer2处理了, consumer2就能通过XACK来确认消息, 这个时候内部队列中消息就会被删除掉

```redis
XACK mymq group2 1654256265584-0
```

> Redis实现的消息队列和专业的消息队列组件之间的差别

一个专业的消费队列必须实现 : **消息可靠**, **消息可堆积**两个功能

- 消息可靠性 : 从**生产者生产消息**, **队列中间件**, **消费者消费消息两个维度考虑消息的可靠性**
  - 生产消息的可靠性 : 也就是生产者生产的消息会不会出现丢失的情况, 生产者会不会丢失消息, 取决于消费者对于异常情况的处理, 如果有合适且充足的异常重发机制, 如果没有接收队列中间件的ACK, 就重发消息, 能实现消息可靠
  - 消费端消息会不会丢失 ? Redis通过内部队列保障了只要消费端没有主动ACK消息, 就仍然能重新消费信息
  - 队列中间件会不会丢失消息 : **会**
    - AOF文件的写盘是以秒为单位的, 但是这个操作是异步的, 如果在写盘前redis宕机了, 重启后仍然会出现消息丢失
    - 主从复制也是异步的, **主从切换的时候也会出现数据的丢失**

- 消息堆积

Redis的消息是保存在内存里的, 如果发生了消息堆积, 会有OOM的风险
也是为了避免这种风险, Redis可以设置队列的最大长度, 如果队列的长度超过了最大长度, 就会丢失旧的消息, 可见如果设置了队列的最大长度又会带来消息丢失的风险

但是专业的消息队列组件将消息保存在磁盘中, 就不会有OOM的风险