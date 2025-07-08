# MySQL优化

## 1. MySQL分页优化

### MySQL是怎么完成一次分页查询的, 性能问题在哪里

有两种分页查询语句

```mysql
select * from t_user order by id limit offset, size
```

```mysql
select * from t_user order by id limit size
```

第二种查询语句, 其实相当于offset == 0的情况

每次执行分页查询的时候, **引擎层首先根据where条件筛选出来符合条件的所有记录, 然后对这些已经筛选的记录order by, 最后应用LIMIT, 从已经筛选出来和排序的结果中取出来指定的offset到offset + size范围的记录**

> 性能问题出在哪里

这里的问题就是, 我们不管size是多少, 我们都需要额外取出来offset条冗余的数据, 这就造成了当offset越来越来的时候, 执行分页查询的开销就越大, 也造成了 limit 1000, 10的开销比limit 100, 10的开销更大

从where筛选 -> order by 排序 -> limit分页, 其中需要做对前面的offset条数据大量的行扫描和排序再跳过offset条数据

### 怎么优化

#### 基于主键索引的limit执行优化

也就是我们order by的是主键索引

```mysql
select * from t_user order by id limit offset, size
```

这里其实有两方面的冗余

1. 查出来了最后抛弃的不需要的offset部分的数据
2. 查出来的抛弃的offset部分的数据中的, 我们实际上只需要他们的id列, 别的列的信息是无用的

基于这两个方面

```mysql
select * from page where id >= (select id from page order by id limit 60000, 1) order by id limit 10
```

优化的方面有两个

1. 先执行了子查询, 这个子分页查询从引擎层取数据的时候, 只获取了id列, 减少了复制的开销
2. 对于父查询, 通过id >= 子查询出来的id的形式, 不用再使用有offset的分页查询, 能保证需要返回的列中的每一列我们是查询到了的

性能能大概提升一倍

#### 基于非主键索引的limit执行优化

当offset比较小的时候, 过程就比主键索引多了一个回表的过程, 将回表后的数据返回给server层

但是在offset非常大的时候, 因为server层的**优化器**在看到非主键索引的600w次回表以后, 发现还不如**全表扫描**, 这是**真性能杀手**

这里优化的关键在于**回表操作会导致变成全表扫描, 我们规避掉回表操作就行**

```mysql
select * from page t1, (select id from page order by user_name limit 600000, 100) t2 where t1.id = t2.id;
```

这样就避免了回表操作

### 深度分页

上面叙述的, 在offset变得巨大的时候, 分页查询的性能急剧下降的问题, 就是深度分页问题, 深度分页问题只能缓解, 不能避免, 出现这个问题的时候, 我们需要追溯到为什么我们的代码会产生深度分页问题来规避

> 情景一 : 想取出来全表的数据

假如我们有一张大表, 我们想将所有数据取出来

```mysql
select * from page
```

数据量较大, mysql没办法一次性取出来全部报错, 很容易**超时报错**

这个时候就会有人通过limit offset size的形式来分批获取, 但是在哪天数据表变得奇大无比的时候, 就会出现前面的深度分页问题

这种时候很好解决, 简单来说就是通过where id > 当前获取到的批次的最大id来获取到获取分页数据的起点

```python
start_id = 0
while(1) {
    datas = [select * from page where id > start_id order by id limit 100];
    if(datas.length == 0) break
    add(datas)
    start_id = get_max_id_from(datas)
}
```

> 情景二: 给用户做分页展示

就像搜索引擎下面的翻页功能

解决方式有两种

- 限制翻页的上下限, 谁家好人搜索引擎会翻到100多页
- 或者做成只能上一页或者只能下一页的形式, 这样就能通过上面的start_id分批获取的方式来得到稳定的查询速度





## 星球

慢查询定位总结

1、介绍一下当时产生问题的场景（我们当时的一个接口测试的时候非常的慢，压测的结果大概5秒钟） 

2、我们系统中当时采用了运维工具（ Skywalking ），可以监测出哪个接口，最终因为是sql的问题 

3、在mysql中开启了慢日志查询，我们设置的值就是2秒，一旦sql执行超过2秒就会记录到日志中（调试阶段）

 4、上线前的压力测试，观察相关的指标 tps / qps / rt / io / cpu  

总结： 慢 SQL 处理思路  

发现: 系统监控:基于 arthars 运维工具、promethues 、skywalking  mysql 慢日志: 会影响一部分系统性能 慢 SQL 处理 由宏观到微观 

1、检查 系统相关性能 top / mpstat / pidstat / vmstat 检查 CPU / IO 占比，由进程到线程 

2、检查 MySQL 性能情况，show processlist 查看相关进程 | 基于 MySQL Workbench 进行分析 

3、检查 SQL 语句索引执行情况，explain 关注 type key extra rows 等关键字 

4、检查是否由于 SQL 编写不当造成的不走索引，例如 使用函数、not in、%like%、or 等 

5、其他情况: 深分页、数据字段查询过多、Join 连表不当、业务大事物问题、死锁、索引建立过多 

6、对于热点数据进行前置，降低 MySQL 的压力 redis、mongodb、memory 

7、更改 MySQL 配置 ， 处理线程设置一般是 cpu * 核心数 的 2 倍，日志大小 bin-log 

 8、升级机器性能 or 加机器 

9、分表策略，分库策略 

10、数据归档