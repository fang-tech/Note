# Redis 数据结构

## SDS

SDS的出现主要是为了解决C语言字符串的三个问题

- **需要O(n)的时间复杂度获取字符串的长度**
- **无法动态拓展缓冲区**
- **二进制不安全**(原本的c语言字符串只能存储文本)



### SDS的结构

![](https://img-blog.csdnimg.cn/img_convert/516738c4058cdf9109e40a7812ef4239.png)

- alloc : 分配的空间长度, 通过这个属性 alloc - len能获取到剩余的空间
- flags : sds的类型, sdshdr5、sdshdr8、sdshdr16、sdshdr32 和 sdshdr64用处在节省内存空间那里

### O(1)获取字符串的长度

SDS中会额外记录字符串的长度

### 二进制安全

原本的C语言风格字符串是通过`\0`来标识字符串的结尾, 现在通过len长度来标识字符串的结尾, 这样也就没有二进制不安全的缺陷, 能够**存储二进制文件**

### 动态扩容

和ArrayList之类的集合类一样, 会在缓冲区空间不足的时候, 自动进行扩容

```c
hisds hi_sdsMakeRoomFor(hisds s, size_t addlen)
{
    ... ...
    // s 目前的剩余空间已足够，无需扩展，直接返回
    if (avail >= addlen)
        return s;
    //获取目前 s 的长度
    len = hi_sdslen(s);
    sh = (char *)s - hi_sdsHdrSize(oldtype);
    //扩展之后 s 至少需要的长度
    newlen = (len + addlen);
    //根据新长度，为 s 分配新空间所需要的大小
    if (newlen < HI_SDS_MAX_PREALLOC)
        //新长度<HI_SDS_MAX_PREALLOC 则分配所需空间*2 的空间
        newlen *= 2;
    else
        //否则，分配长度为目前长度 +1MB
        newlen += HI_SDS_MAX_PREALLOC;
       ...
}
```

- **当申请的最小空间 < 1MB的时候, 会申请两倍的最小空间**
- **当申请的最小空间 >= 1MB的时候, 会申请最小空间 + 1MB的内存空间**

在拓展SDS内存空间之前还会检查 \[未使用空间\]的大小, 如果不够SDS分配修改需要的空间, 还会执行分配SDS额外的 \[未使用空间\]

这样做的好处是能**减少内存分配的次数**, 就像建立了一个内存的缓冲池一样

### 节省内存空间

SDS中的flags属性标识了这个flags的类型有五种类型 : sdshdr5、sdshdr8、sdshdr16、sdshdr32 和 sdshdr64

这五种类型之间的区别就是SDS头的**len和alloc属性的数据类型(长度)不一样**

```c
struct __attribute__ ((__packed__)) sdshdr16 {
    uint16_t len;
    uint16_t alloc; 
    unsigned char flags; 
    char buf[];
};


struct __attribute__ ((__packed__)) sdshdr32 {
    uint32_t len;
    uint32_t alloc; 
    unsigned char flags;
    char buf[];
};
```

不同的len和alloc的长度, 限定了这个字符串的长度

这样的设计能够灵活地适应不同长度的字符串, 有效**减少占用的内存空间**

同时`__attribute__ ((__packed__))`还能阻止编译过程的优化对齐导致的结构体中的属性不紧凑, 从而进一步的节省内存空间, 按照实际占用的字节数对齐

## 链表

就是简单的双向链表

```c
typedef struct listNode {
    //前置节点
    struct listNode *prev;
    //后置节点
    struct listNode *next;
    //节点的值
    void *value;
} listNode;
```

```c
typedef struct list {
    //链表头节点
    listNode *head;
    //链表尾节点
    listNode *tail;
    //节点值复制函数
    void *(*dup)(void *ptr);
    //节点值释放函数
    void (*free)(void *ptr);
    //节点值比较函数
    int (*match)(void *ptr, void *key);
    //链表节点数量
    unsigned long len;
} list;
```

![](https://img-blog.csdnimg.cn/img_convert/cadf797496816eb343a19c2451437f1e.png)

### 优势和缺陷

优点

- 双向的链表, 获取某个节点的前置或者后置节点的时间复杂度是O(1)
- 获取头尾节点的时间复杂度是O(1)
- 获取节点数量的时间复杂度是O(1)
- 数据的类型是void*, 所以能够保存不同类型的数据

缺陷

- 申请的内存是不保证连续的, 不能很好地利用CPU缓存 (因此有了压缩列表)
- 前驱后置节点带来了额外的内存开销

## 压缩列表

为了节省内存而开发的, **连续内存块组成的顺序型数据结构**

![](https://img-blog.csdnimg.cn/img_convert/a3b1f6235cf0587115b21312fe60289c.png)

- zlbytes : 整个压缩列表占用的字节数
- zltail : 记录压缩列表尾部的节点距离起始地址的偏移量, 用于快速访问尾部节点
- zllen : 记录压缩列表的节点数量
- zlend : 标志压缩列表的结束点, 固定值 0xFF

- entry : 压缩列表的节点
  - prevlen : 记录前一个节点占用的字节数, 用于从后往前遍历
  - encoding : 记录了数据数据类型和长度
  - data : 记录了当前节点的实际数据

### prevlen和encoding

这两个节点是根据数据的大小和类型动态分配内存空间

>  prevlen 

- 如果前一个节点的**长度(这里包括三个属性) 小于254字节,prevlen属性占用1个字节**
- 如果前一个节点的**长度大于等于254字节, prevlen属性占用5个字节**

> encoding

encoding会根据存储的数据类型和长度动态分配内存空间, 主要分为**字符串**和**整数**两类

- 如果是整数会根据整数的长度存储不同内容, **encoding属性占用一个字节**
- 如果是字符串, 会根据字符串的长度分配内存, **encoding存储字符串的长度**, **长度小于等于63/小于等于2^14-1/小于等于2^32-1**, 分别分配**1字节/2字节/5字节的空间**进行编码

### 连锁更新

压缩列表新增某个元素或者修改某个元素的时候, 如果内存空间不够, 就需要更新节点重新分配内存. 但是当更新的节点较大的时候, 可能会导致后续元素的prevlen都发生变化, 从1字节变化到5字节, 引起连锁更新

![](https://img-blog.csdnimg.cn/img_convert/1f0e5ae7ab749078cadda5ba0ed98eac.png)

### 缺陷

- 访问元素本质上是O(n)的时间复杂度, 如果节点数量多了以后, 性能会下降

- 同时连锁更新的问题会影响写入性能

也是针对这些设计上的缺陷, 后面引入了quicklist和listpack

## 哈希表

Redis中的哈希表采用的是**链式哈希**来解决哈希冲突

### Hash的结构

哈希表数据结构

```c
typedef struct dictht {
    //哈希表数组
    dictEntry **table;
    //哈希表大小
    unsigned long size;  
    //哈希表大小掩码，用于计算索引值
    unsigned long sizemask;
    //该哈希表已有的节点数量
    unsigned long used;
} dictht;
```

哈希表表项数据结构

```c
typedef struct dictEntry {
    //键值对中的键
    void *key;
  
    //键值对中的值
    union {
        void *val;
        uint64_t u64;
        int64_t s64;
        double d;
    } v;
    //指向下一个哈希表节点，形成链表
    struct dictEntry *next;
} dictEntry;
```

![哈希表结构](https://img-blog.csdnimg.cn/img_convert/dc495ffeaa3c3d8cb2e12129b3423118.png)

简单来说dictht中存有table和size属性, table指向dictEntry表, 也就是hash映射表, 每个表项也指向dictEntry也就是真实的数据

dictEntry中的value使用 **联合体**存储, 这样在存储数值型value的时候, 可以直接存在hahs表中, 不需要额外创建一个指针

### 哈希冲突

从key到table中的索引的转换过程 : `hash(key) % size`
如果两个不同的key在经过转换以后得到了相同的索引, 这个时候就发生了哈希冲突
链式哈希就是将, **被分配到同一个哈希桶上的多个节点用单向链表链接起来**

链式哈希有个明显的缺点, 就是**hash表的查找性能会向O(n)退化**
在这个时候, 就要进行**rehash**, 重新建立hash表

### rehash

> Redis是怎么进行的rehash

```c
typedef struct dict {
    …
    //两个 Hash 表，交替使用，用于 rehash 操作
    dictht ht[2]; 
    …
} dict;
```

Redis为一个hash表数据类型会建立两个哈希表(dickht)
![两个哈希](https://img-blog.csdnimg.cn/img_convert/2fedbc9cd4cb7236c302d695686dd478.png)

执行的过程

1. 为第二个hash表分配内存, 一般是原来的两倍
2. 将表1的数据拷贝到表二中
3. 拷贝完成以后, 将表1释放, 将表2设置为表1, 重新创建一个空的表2

> 渐进式rehash

如果像上面一样进行rehash, 一次性将所有节点都重新拷贝到新的hash表中, 那么在hash表的节点数量很多的时候, 拷贝的开销很大, 这个时候就会造成redis主线程的阻塞

所以在Redis中, rehash采用了 **渐进式rehash**, 不再一次性完成, 而是在需要rehash的时候, 进入到rehash时期, 在这个时期内

- 不再对原hash表1进行插入操作, 所有的新增项, 将所有的项插入到表2
- 不是一次性迁移, 而是在执行插入, 更新, 查询, 删除的时候, 将整个hash桶上的key-value迁移到表2上
- 在执行查询操作的时候, 会优先从表1中查询, 如果没有查询到, 会从表2中查询

> 什么时候进行rehash呢?

根据负载因子来看 : 负载因子 = 哈希表中保存的节点数量 / 哈希表的大小

- 如果负载因子 >= 1, 并且没有进行bgsave与bgwriteaof的时候, 进行rehash操作
- 如果负载因子 > 5, 不管是不是在执行AOF重写或者RDB快照, 都会执行rehash操作

## 整数集合

整数集合是Set数据类型在存储的数据类型是整数, 并且元素数量不多的时候, 采用的底层实现, 之所以采用这个实际查询性能是O(n)的数据结构, 是因为其紧凑连续的内存布局, 能减少内存的占用, 同时能有效利用CPU的缓存局部性, 实际的性能并不差

### 数据结构

```c
typedef struct intset {
    //编码方式
    uint32_t encoding;
    //集合包含的元素数量
    uint32_t length;
    //保存元素的数组
    int8_t contents[];
} intset;
```

虽然contents数组的类型是int8_t, 但是实际上contents的类型由encoding来决定

- encoding的值为INT_SET_ENC_INT16的时候contents的类型为int16_t
- encoding的值为INT_SET_ENC_INT32的时候contents的类型为int32_t
- encoding的值为INT_SET_ENC_INT64的时候contents的类型为int64_t

### 升级操作

当存入了数据长度大于encoding中的编码的时候, 会进行升级操作, **修改encoding, 并将数组中所有元素的类型都进行升级**

1. 计算需要的额外的内存空间并进行扩容操作
2. 数据迁移
3. 将新的数据存入

![升级操作](https://img-blog.csdnimg.cn/img_convert/e84b052381e240eeb8cc97d6b729968b.png)

升级操作能有效地节省内存, 同时又能适应存储大数据的需求
**一旦升级, 没有降级操作, 删除了大数据也不会降级**

## 跳表

### 数据结构

zset由 **跳表 + 哈希表**实现, 其中跳表能有效支持范围查询, 而哈希表能提升单点查询的性能, 也是ZSCORE能在O(1)的时间复杂度下查找到一个filed对应的score

```c
typedef struct zset {
    dict *dict;
    zskiplist *zsl;
} zset;
```

```c
typedef struct zskiplistNode {
    //Zset 对象的元素值
    sds ele;
    //元素权重值
    double score;
    //后向指针
    struct zskiplistNode *backward;
  
    //节点的 level 数组，保存每层上的前向指针和跨度
    struct zskiplistLevel {
        struct zskiplistNode *forward;
        unsigned long span;
    } level[];
} zskiplistNode;
```

跳表能在O(logN)的时间复杂度的时间复杂度下进行查询操作, 这里的查询是指基于范围的查找, 比如说我要查找到分数是12-12的节点(基于filed的查找由哈希表实现)
跳表之所以能在logN的时间复杂度下查询主要就是因为跳表中的level层高的设计, 让便利链表不再是需要逐个访问, 而是能够跳跃访问

### 跳表执行查询的过程

跳表执行查询的时候, 会从头节点的最高层开始, 会根据以下两个标准来判断是访问当前层的下一个节点还是访问下一层

- 如果当前层的下一个节点的权重小于需要查询的权重的时候, 跳表会访问当前层的下一个几点
- 如果当前层的下一个节点的权重等于需要查询的权重的时候, 如果下一个节点的SDS值小于需要查询的数据的时候, 会访问下一层的节点

如果都不满足或者当前层指向null, 则访问当前节点的下一层

![](https://cdn.xiaolincoding.com/gh/xiaolincoder/redis/%E6%95%B0%E6%8D%AE%E7%B1%BB%E5%9E%8B/3%E5%B1%82%E8%B7%B3%E8%A1%A8-%E8%B7%A8%E5%BA%A6.drawio.png)

我们现在要访问abcd 4

1. 从头节点的最高层L2出发, 因为当前层的下一个节点的score = 3 \< 4, 访问下一个节点
2. 从该节点的最高层level[2]触发, 访问到null, 访问下一层
3. 下一层的权重等于4, 但是sds元素的大小 > abcd, 不符合, 访问下一层
4. level[0]的下一个节点的权重 == 4, 同时元素值 == abcd, 找到了符合要求的节点

### 跳表是如何实现的logN的查询

在最开始的时候, 看小林coding的图示, 以为跳表只有三层, 那么最快的情况, 需要查询的节点都在最高层, 那么查询时间复杂度也不过是n/3, 并没有做到logN

> 那么怎么设置层高能实现logN的时间复杂度呢?

假设元素的数量是n, 层高为L, 新增一层的概率为p, 我们从执行查询的时候查询路径分析

想象跳表就是一个金字塔, 一共有L层, P(层高 >= n) = p^(n-1)

- 所以P(层高 >= 1) = 1
- P(层高 >= 2) = p
- P(层高 >= 3) = p^2
- ...
- P(层高 >= N) = p^(N-1)

所以节点在第一层的期望是 P(层高>=1) - P(层高>=2)

在第i层的期望是P(层高==i) = P(层高>=i) - P(层高>=i+1) = p^(i-1) - p^i

**第i层的节点个数是$N * (p^{(i-1)} - p^i) = N *(1-p)*p^{(i-1)}$**

设层高为L, 则$\sum_{i=1}^{L} (p^{(i-1)} - p^i) = 1$

由等比数列的求和公式得 $1-p^L = 1$, 但是这个公式只有在**L趋近于inf 的时候才成立**, 实际上的跳表的高度肯定不是无穷, 所以我们只能**使用$N*(1-p)*p^{(L-1)}$这个期望值为1的项来近似1**

所以**最后的公式是$\sum_{i=1}^{L} (p^{(i-1)} - p^i) = N*(1-p)*p^{(L-1)}$**

$1-p^L = N*(p^{L-1} - p^L)$ 得到 **$L = log_pN + C$也就是$**L = O(log_2N)$

从查询路径分析时间复杂度

- 在第i层, 节点出现的概率是$p^{i-1}$
- 在第i层, 相邻两个节点之间的距离是$1/p^{(i-1)}$
- 也就是从第i层出发, 大概每$1/p^{(i-1)}$会遇到一个节点
- **第i+1层的两个节点之间, 第i层平均有1/p个节点**
- 也就是从第i+1层降到第i层的时候, 我们**最多访问1/p个节点**

所以最后的时间复杂度 = O(log_2N) + O(1/p) = O(log_2N)

> Redis中的p是多少

理论上来讲p = 1/2是查询最快速的值, 但是实际上Redis取得是1/4, 这里是内存效率和查询性能的平衡

由一个节点的期望层高 = $\sum_{i=1}^{\inf} P_{L>=i} = \sum_{i=1}^{\inf} p^{(i-1)} = 1*(1-p^{inf} ) / (1-p) = 1/(1-p)$

所以所有节点的期望总层高就是 N \* 1/(1-p), **p越大, 层数越多, 要占用越多的内存**

### 跳表是的层高设置

跳表在**创建节点的时候会生成一个[0,1]随机数, 如果随机数的大小 <= 0.25, 则层数增加一层, 循环直到最后随机数 > 0.25为止**

创建跳表的头节点的时候, **如果层高的最大限制是64, 创建跳表的头节点的时候会直接创建64层高的头节点**

```c
/* Create a new skiplist. */
zskiplist *zslCreate(void) {
    int j;
    zskiplist *zsl;

    zsl = zmalloc(sizeof(*zsl));
    zsl->level = 1;
    zsl->length = 0;
    zsl->header = zslCreateNode(ZSKIPLIST_MAXLEVEL,0,NULL);
    for (j = 0; j < ZSKIPLIST_MAXLEVEL; j++) {
        zsl->header->level[j].forward = NULL;
        zsl->header->level[j].span = 0;
    }
    zsl->header->backward = NULL;
    zsl->tail = NULL;
    return zsl;
}
```

ZSKIPLIST_MAXLEVEL 定义的是最高的层数，Redis 7.0 定义为 32，Redis 5.0 定义为 64，Redis 3.0 定义为 32。

### 为什么使用跳表而不使用平衡树

- 跳表在实现和维护上都是更加简单的
- 内存开销更小, 每个节点的指针数量期望值为1/(1-p) = 4/3而不是平衡树的2
- 更新操作开销更小, BTree在更新节点的时候涉及到复杂的节点分裂合并和平衡操作
- 天然支持范围查询, 只需要沿着底层链表遍历就行
- 空间局部性更好, 更能利用CPU的高速缓存
  - 跳表在Redis中采用了连续分配的内存块来存储节点, 而不是和传统的链表一样完全随机分配的链表节点
  - B树节点内部是内存是连续的, 但是节点之间内存连续性是不保证的, 同时因为频繁的动态调整节点以维护平衡, 内存碎片和不连续会更进一步

## quicklist

这个数据结构的出现主要是为了解决压缩列表的设计缺陷

- 节点数量多了以后的查询性能差
- 节点数量多了以后难以承受的连锁更新

### 数据结构

```c
typedef struct quicklist {
    //quicklist 的链表头
    quicklistNode *head;      //quicklist 的链表头
    //quicklist 的链表尾
    quicklistNode *tail; 
    //所有压缩列表中的总元素个数
    unsigned long count;
    //quicklistNodes 的个数
    unsigned long len;       
    ...
} quicklist;
```

```c
typedef struct quicklistNode {
    //前一个 quicklistNode
    struct quicklistNode *prev;     //前一个 quicklistNode
    //下一个 quicklistNode
    struct quicklistNode *next;     //后一个 quicklistNode
    //quicklistNode 指向的压缩列表
    unsigned char *zl;              
    //压缩列表的的字节大小
    unsigned int sz;                
    //压缩列表的元素个数
    unsigned int count : 16;        //ziplist 中的元素个数 
    ....
} quicklistNode;
```

![](https://img-blog.csdnimg.cn/img_convert/f46cbe347f65ded522f1cc3fd8dba549.png)

quicklist的解决方案就是让压缩列表的节点数量控制在一定数量以内, 在添加一个元素的时候, 会先尝试检查插入位置的压缩列表能不能容纳该元素, 如果不能容纳就会创建一个新的压缩列表, 将新节点添加到新的压缩列表中

## listpack

quicklist并不是解决了压缩列表中的连锁更新的问题, 只是控制了这个问题发生的时候的成本, 而listpack是确实实现了解决连锁更新的问题

解决方式也很简单, 删除了entry中的prevlen属性将entry修改为

```c
<encoding-type><element-data><element-tot-len>
```

从原先的

- pervlen
- encoding
- contents

变为

- encoding
- data
- backlen : encoding + data 的长度, 也就是只记录当前entry的元信息, 就不会导致连锁更新的问题

### 删除prevlen以后listpack是怎么同样实现向前遍历的?



```txt
                                  当前位置
                                     ↓
[Header][E1-type][E1-data][E1-backlen][E2-type][E2-data][E2-backlen][EOF]
```

我们怎么向前遍历E1?

backlen的高位会有特殊的编码标识, 我们从当前位置向前读取字节, 也就是想低位读取字节

1. 如果最高位是**0**（即0xxxxxxx），表示这是一个1字节的backlen，直接表示0-127的长度值
2. 如果高两位是**10**（即10xxxxxx），表示这是一个2字节backlen的第一个字节
3. 如果高三位是**110**（即110xxxxx），表示这是一个3字节backlen的第一个字节
4. 如果高四位是**1110**（即1110xxxx），表示这是一个4字节backlen的第一个字节
5. 如果高五位是**11110**（即11110xxx），表示这是一个5字节backlen的第一个字节

第一次读取到了这样的特殊的编码标识, 就说明我们已经读取完了backlen, 就能完整得到backlen的值 
