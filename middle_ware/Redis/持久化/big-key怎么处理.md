# Big-Key对持久化的影响

## 对AOF日志写入的影响

Big-Key对AOF日志写入的影响是在执行fsync()函数的时候, big-key会导致长时间的阻塞

这里得分三种情况讨论, 也就是AOF选用的三种策略

- Always : 这个时候每写入一条命令就会立刻同步执行fsync(), big-key会造成阻塞
- Everysec : 因为fsync()是异步执行的, 所以不会造成阻塞, 没有影响
- No : 同样不会有影响

## 对AOF重写和RDB的影响

对这两个过程的影响主要是在两个过程中

- 创建子进程的时候需要复制页表, big-key会导致页表的增大, 最后延长了fork的时间
- 在执行修改big-key的时候, 写时复制会导致的长时间的阻塞复制

## 除了持久化方面的影响, 其他方面的影响

- 客户端阻塞超时, 阻塞工作线程 : 操作big-key的时候会比较耗时
- 引发网络阻塞 : 每次获取big-key的时候产生的网络流量较大
- 内存分布不均匀: 在slot分片均匀情况下, 会出现数据和查询倾斜的情况, 部分big-key的Redis节点占用内存多, QPS也较大

## 如何避免big-key呢

- 在设计阶段将big-key切分成一个个小的key
- 定期检查Redis是否存在big-key, 如果存在, 使用unlink进行异步删除, 使用del会阻塞主线程