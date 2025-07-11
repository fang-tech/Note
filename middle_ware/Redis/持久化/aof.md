# AOF持久化

## 什么是AOF持久化

AOF持久化是Redis持久化数据的一种方式, 通过每进行写命令, 将这条命令写入到AOF appendonly.aof文件中, 这样在Redis重启的时候, 就能通过重新执行aof文件中的写命令恢复数据

 AOF持久化需要在redis.conf主动开启

```conf
//redis.conf
appendonly yes // 默认关闭
appendfilename "appendonly.aof" // AOF持久化文件名
```

写入到AOF文件中的命令会经过再一次的编码

## Redis执行写AOF日志的时机



![AOF](https://img-blog.csdnimg.cn/img_convert/337021a153944fd0f964ca834e34d0f2.png)

执行写日志的时机是在**Redis执行客户端发送过来的命令之后**, 这样做有两大好处

- **不会阻塞当前命令的执行, 但是实际上会阻塞下一条命令的执行**
- 如果先执行写入日志, 在命令错误以后, 就需要将命令从AOF文件中删除, 带来了额外的开销, 如果在写日志中进行检查, 就会带来额外的检查开销, 而**先执行命令, 就能在命令错误的时候不再写入到AOF日志中**

但是这个时机也带来了问题

- 执行写操作和记录日志是两个过程, 也就是会出现AOF磁盘文件和缓存中Redis的数据出现不一致的时候, 在这个时候服务器宕机了, 就会**有数据丢失的风险**
- 不会阻塞当前写命令的执行, 但是**会阻塞下一条命令的执行**, 因为执行写入日志的操作是在**主线程**进行的, 如果此磁盘压力大, 写盘速度慢, 就会造成阻塞

## 三种写回策略

![](https://img-blog.csdnimg.cn/img_convert/4eeef4dd1bedd2ffe0b84d4eaa0dbdea.png)

前面的两个问题 : 数据丢失和阻塞下一条命令的执行, 两个风险的共性就是和**写入磁盘的时机**高度相关, 磁盘I/O才是写入日志最为耗时的部分

如果写入磁盘的时机越及时, 那么数据不一致的时间就越短, 但是相对应的越容易造成阻塞

如果写入磁盘的时机越晚, 那么数据不一致的时间就越长, 但是就越不容易造成对于下一条命令的阻塞(造成阻塞的是fsync()的调度)

Redis有三种写回策略, 在redis.conf文件中的appendfsync中可以进行选择

- **Always** : 总是在执行完写命令以后, 同步将数据写入到硬盘中
- **Everysec** : 每次执行完写操作以后, 就会先将命令写入到AOF文件的内核缓冲区中, 然后每隔一秒将缓冲区中的内容写入到硬盘中
- **No** : Redis不再自己控制将缓冲区中的内容写入到硬盘中, 而是交给操作系统决定, 每次执行完写命令以后, 就只是将命令写入到AOF文件的内核缓冲区中



## 这三种策略是怎么实现的

这三种策略本质上都是在通过控制`fsync()`函数的使用时机

当应用程序向文件中写入数据的时候, 内核通常会先将数据复制到内核缓冲区中, 然后排入队列, 然后由内核决定什么时候写入硬盘

![](https://img-blog.csdnimg.cn/img_convert/f64829ffc2e9e006b090f9aae51035ee.png)

但是如果应用程序向文件中写入数据以后, 希望能立马将数据写入到硬盘中, 就能调用`fsync()`函数, 这样内核就会立马将数据写入到硬盘中

- Always策略就是每次写入AOF文件数据以后, 就立马执行fsync()函数
- Everysec策略会创建一个异步的任务, 每秒执行一次fsync()函数
- No策略不执行fsync函数

## AOF 重写机制

在不断执行写命令的过程中, 会不断向AOF文件中追加内容, 时间久了以后, AOF文件的内容会越来越庞大, 这个时候使用AOF文件恢复会耗时很久

Redis为了避免AOF文件越写越大,  会在AOF文件的大小超过了设定的阈值以后, 执行AOF文件重写机制

在重写时, Redis读取当前数据库中的所有键值对, 每一个键值对用一条命令记录到新的AOF文件中, 记录完了以后, 就用新的AOF文件替换掉旧的AOF文件

> 为什么不复用旧的AOF文件, 而是创建一个新的AOF文件, 然后覆盖

因为如果重写失败, 就会对旧的AOF文件造成污染, 导致数据无法恢复

## AOF 后台重写

触发AOF重写的时候, 需要读取数据库中的所有的键值对, 这个过程是很耗时的, 不能放在主线程执行

Redis的重写AOF过程是由**后台子进程 bgrewriteaof (backround rewrite aof)来完成**

- 避免了阻塞主进程
- 使用了子进程而不是子线程, 不需要使用锁机制来保证数据安全, 而是通过父子进程的**写时复制**保证数据安全, 隔离性更好的同时, 避免复杂的锁机制(redis的主进程是单线程的, 引入锁机制会带来极大的破坏), 虽然创建子进程的内存开销实际上是比创建子线程更大的, 但是相比于安全性和引入锁机制导致的诸多问题, 这点牺牲是完全值得的

> 什么是写时复制

在执行`fork()`函数以后, 创建子进程, 理论上来说, 只有子线程之间是共享内存的, 但是实际上, 创建子进程的时候, 操作系统会把页表也复制过去, 父子进程共享相同的物理内存, 这样能**减少物理内存的开销和创建子进程的开销**, 同时这些内存的权限都会变成**只读**

当两个进程中其中一个进程发起写操作以后, CPU就会发生**缺页中断**, 由于违反权限导致, 操作系统会在缺页异常处理函数中进行物理内存的复制(复制一个内存页的内存), 重新设置内存的映射关系, 将父子进程的内存读写权限设置为可读写, 最后进行写操作

![](https://img-blog.csdnimg.cn/img_convert/d4cfac545377b54dd035c775603b4936.png)

所以bgrewriteaof进程会在两个阶段阻塞父进程

- 创建子进程的途中, 要复制父进程的页表等数据结构
- 创建完子进程以后, 两个进程有修改共享数据, 发生写时复制, 这个时候也会阻塞主进程

从Redis的角度来说

- 在子进程执行重写的过程中, 主进程依然可以正常处理命令
- 但是**如果主进程修改了已经存在的key-value, 就会发生写时复制**, 如果这是一个big key就会有阻塞主进程的风险

> 如果主进程修改了已经存在的key-value, 此时这个key-value在主进程和子进程中数据不一致了怎么办

Redis设置了一个AOF重写缓冲区

在重写AOF启动以后, 当Redis执行完一个写命令以后, 会同时将这个写命令写入到**AOF缓冲区**和**AOF重写缓冲区**

当子进程完成了AOF重写工作, 会向主进程发送一条信号

主进程收到信号以后, 调用信号处理函数

- 将AOF重写缓冲区中的所有内容追加到新的AOF文件中
- 新的AOF文件的进行改名, 覆盖现在的AOF文件