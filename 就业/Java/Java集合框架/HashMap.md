# HashMap

## hash方法原理

### hash(Object key)

```java
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```

这段代码就是计算存进来的元素的hash值的函数

```java
public V put(K key, V value) {
    return putVal(hash(key), key, value, false, true);
}
```

这个函数将key Object的hashcode异或hashcode逻辑右移16得到最终的hash值

如果要通过这个hash值计算出来桶的位置, 则通过`(n - 1) & hash`, 这样就是hash值对n取余后的结果, 因为在数组长度是2的n次方的时候, 取余运算和与运算的结果是一样的(因为在长度是2的n次方的时候, length - 1后会除了第n位全是1, 就能达到取余的效果)

### HashMap中会调用取模运算的地方



### putVal

```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
    // 数组
    HashMap.Node<K,V>[] tab;
    // 元素
    HashMap.Node<K,V> p;

    // n 为数组的长度 i 为下标
    int n, i;
    // 数组为空的时候
    if ((tab = table) == null || (n = tab.length) == 0)
        // 第一次扩容后的数组长度
        n = (tab = resize()).length;
    // 计算节点的插入位置，如果该位置为空，则新建一个节点插入
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
}
```

### getNode

```java
final Node<K,V> getNode(int hash, Object key) {
    // 获取当前的数组和长度，以及当前节点链表的第一个节点（根据索引直接从数组中找）
    Node<K,V>[] tab;
    Node<K,V> first, e;
    int n;
    K k;
    if ((tab = table) != null && (n = tab.length) > 0 &&
            (first = tab[(n - 1) & hash]) != null) {
        // 如果第一个节点就是要查找的节点，则直接返回
        if (first.hash == hash && ((k = first.key) == key || (key != null && key.equals(k))))
            return first;
        // 如果第一个节点不是要查找的节点，则遍历节点链表查找
        if ((e = first.next) != null) {
            do {
                if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
                    return e;
            } while ((e = e.next) != null);
        }
    }
    // 如果节点链表中没有找到对应的节点，则返回 null
    return null;
}
```

`if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))`是HashMap的经典判断e元素和key对应的元素是不是同一个元素的语句

- 先比较两个key的hash值, 如果相等
- 进一步比较两个key是不是值相等,
  - key == null 的时候用==判断
  - key != null的时候用equals判断

### 扰动函数

`hash ^ (hash >>> 16)`

用来混合原来哈希值的高位和低位, 让低位的随机性变大了, 参杂了部分高位的特征, 增大了低位的随机性

> 为什么使用异或呢

因为异或是位级别的均匀分布, 如果是与运算就会导致最后的计算结果中0偏多, 如果是或运算, 就会导致1增多

## HashMap的扩容机制

### resize方法

JDK7中的源码

```java
// newCapacity为新的容量
void resize(int newCapacity) {
    // 小数组，临时过度下
    Entry[] oldTable = table;
    // 扩容前的容量
    int oldCapacity = oldTable.length;
    // MAXIMUM_CAPACITY 为最大容量，2 的 30 次方 = 1<<30
    if (oldCapacity == MAXIMUM_CAPACITY) {
        // 容量调整为 Integer 的最大值 0x7fffffff（十六进制）=2 的 31 次方-1
        threshold = Integer.MAX_VALUE;
        return;
    }

    // 初始化一个新的数组（大容量）
    Entry[] newTable = new Entry[newCapacity];
    // 把小数组的元素转移到大数组中
    transfer(newTable, initHashSeedAsNeeded(newCapacity));
    // 引用新的大数组
    table = newTable;
    // 重新计算阈值
    threshold = (int)Math.min(newCapacity * loadFactor, MAXIMUM_CAPACITY + 1);
}
```

1. 如果原来的容量就是MAXIMUM_CAPACITY, 这个时候容量就是Integer.MAX, 返回, 因为Integer.MAX = 2 \\* MAXIMUN_CAPACITY - 1, 这样扩容就刚好就是扩容了一倍
2. 用新的容量初始化一个新的Entry数组
3. 将小的数组也就是桶数组迁移到新的数组中
4. 引用新的大数组
5. 重新计算最大容量 = min ( newCapacity \* loadFactor, MAXIMUN_CAPACITY + 1)

### newCapacity是怎么计算的

```java
int newCapacity = oldCapacity * 2;
if (newCapacity < 0 || newCapacity >= MAXIMUM_CAPACITY) {
    newCapacity = MAXIMUM_CAPACITY;
} else if (newCapacity < DEFAULT_INITIAL_CAPACITY) {
    newCapacity = DEFAULT_INITIAL_CAPACITY;
}
```





