# JVM调优

## 1. 新生代中Eden区域和Survivor比例调优

默认的比例是8:1:1

### 需要调整的情况

1. 过早的晋升现象频繁发生, 说明Survivor空间不足
2. Minor GC频繁异常: 说明Eden区域太小了, 频繁填满Eden区域, 较少但耗时长的Minor GC说明Eden区域太大了
3. 老年代频繁Full GC: 可能是因为对象过早晋升导致老年代填充过快
4. 应用特性不匹配: 应用程序主要产生大量短生命周期对象, 就需要更大的Eden, 中等生命周期对象, 需要更大的Survivor

### 参数

通过`-XX:SurvivorRatio`来设置, 比如`-XX:SurvivorRatio=6`设置Eden:S1:S2 = 6:2:2

通过监控GC频率变化情况和对象晋升率变化