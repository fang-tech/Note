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

