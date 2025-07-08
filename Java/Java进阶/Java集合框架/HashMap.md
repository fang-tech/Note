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

### JDK7中的resize方法

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
int newCapacity = oldCapacity << 1;
if (newCapacity >= DEFAULT_INITIAL_CAPACITY && oldCapacity >= DEFAULT_INITIAL_CAPACITY) {
    if (newCapacity > MAXIMUM_CAPACITY)
        newCapacity = MAXIMUM_CAPACITY;
} else {
    if (newCapacity < DEFAULT_INITIAL_CAPACITY)
        newCapacity = DEFAULT_INITIAL_CAPACITY;
}
```

在正常情况下新的容量是旧的容量的两倍

后面的代码就是在保证newCapacity的范围是\[DEFAULT_INITIAL_CAPACITY, MAXIMUM_CAPACITY]

### JDK8中的resize()方法

```java
final Node<K,V>[] resize() {
    Node<K,V>[] oldTab = table; // 获取原来的数组 table
    int oldCap = (oldTab == null) ? 0 : oldTab.length; // 获取数组长度 oldCap
    int oldThr = threshold; // 获取阈值 oldThr
    int newCap, newThr = 0;
    if (oldCap > 0) { // 如果原来的数组 table 不为空
        if (oldCap >= MAXIMUM_CAPACITY) { // 超过最大值就不再扩充了，就只好随你碰撞去吧
            threshold = Integer.MAX_VALUE;
            return oldTab;
        }
        else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY && // 没超过最大值，就扩充为原来的2倍
                 oldCap >= DEFAULT_INITIAL_CAPACITY)
            newThr = oldThr << 1; // double threshold
    }
    else if (oldThr > 0) // initial capacity was placed in threshold
        newCap = oldThr;
    else { // zero initial threshold signifies using defaults
        newCap = DEFAULT_INITIAL_CAPACITY;
        newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
    }
    // 计算新的 resize 上限
    if (newThr == 0) {
        float ft = (float)newCap * loadFactor;
        newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                  (int)ft : Integer.MAX_VALUE);
    }
    threshold = newThr; // 将新阈值赋值给成员变量 threshold
    @SuppressWarnings({"rawtypes","unchecked"})
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap]; // 创建新数组 newTab
    table = newTab; // 将新数组 newTab 赋值给成员变量 table
    if (oldTab != null) { // 如果旧数组 oldTab 不为空
        for (int j = 0; j < oldCap; ++j) { // 遍历旧数组的每个元素
            Node<K,V> e;
            if ((e = oldTab[j]) != null) { // 如果该元素不为空
                oldTab[j] = null; // 将旧数组中该位置的元素置为 null，以便垃圾回收
                if (e.next == null) // 如果该元素没有冲突
                    newTab[e.hash & (newCap - 1)] = e; // 直接将该元素放入新数组
                else if (e instanceof TreeNode) // 如果该元素是树节点
                    ((TreeNode<K,V>)e).split(this, newTab, j, oldCap); // 将该树节点分裂成两个链表
                else { // 如果该元素是链表
                    Node<K,V> loHead = null, loTail = null; // 低位链表的头结点和尾结点
                    Node<K,V> hiHead = null, hiTail = null; // 高位链表的头结点和尾结点
                    Node<K,V> next;
                    do { // 遍历该链表
                        next = e.next;
                        if ((e.hash & oldCap) == 0) { // 如果该元素在低位链表中
                            if (loTail == null) // 如果低位链表还没有结点
                                loHead = e; // 将该元素作为低位链表的头结点
                            else
                                loTail.next = e; // 如果低位链表已经有结点，将该元素加入低位链表的尾部
                            loTail = e; // 更新低位链表的尾结点
                        }
                        else { // 如果该元素在高位链表中
                            if (hiTail == null) // 如果高位链表还没有结点
                                hiHead = e; // 将该元素作为高位链表的头结点
                            else
                                hiTail.next = e; // 如果高位链表已经有结点，将该元素加入高位链表的尾部
                            hiTail = e; // 更新高位链表的尾结点
                        }
                    } while ((e = next) != null); //
                    if (loTail != null) { // 如果低位链表不为空
                        loTail.next = null; // 将低位链表的尾结点指向 null，以便垃圾回收
                        newTab[j] = loHead; // 将低位链表作为新数组对应位置的元素
                    }
                    if (hiTail != null) { // 如果高位链表不为空
                        hiTail.next = null; // 将高位链表的尾结点指向 null，以便垃圾回收
                        newTab[j + oldCap] = hiHead; // 将高位链表作为新数组对应位置的元素
                    }
                }
            }
        }
    }
    return newTab; // 返回新数组
}
```

计算新的阈值和新的容量, 主要是分作第一次初始化(无参构造还是在创建对象的时候指定了大小)还是后续扩容的情况来分情况计算

1. 获取原来的数组和数组容量, 以及原来的阈值
2. 如果原来的数组不为空, 说明已经完成了对于数组的初始化
   1. 原来的数组大小已经超过了最大的数组大小了, 这个时候就步扩充了, 将阈值修改为oldTab, 然后直接返回, 随它碰撞去了, 已经扩不动了
   2. 没超过最大值, newCap = oldCap << 1, 并且newCap也没有超过最大值的话, newThr = oldThr << 1
3. 原来的数组为空, 说明是第一次初始化数组
   1. 如果oldThr > 0, 说明用户在创建对象的时候指定数组的容量, 这个时候newCap = oldThr(用户指定的大小)
   2. 如果用户在创建对象的时候使用的是无参构造, 这个时候初始化容量和扩容阈值newCap = DEFAULT_INITIAL_CAPACITY, newThr = (int)(DEFAULT_LOAD_FACTOR \* DEFAULT_INITIAL_CAPACITY)
4. 如果newThr == 0, 说明是第一次初始化的情况, 并且用户指定了数组的容量, 计算出来新的阈值, 并且判断数组的容量和这个阈值是不是超过了最大容量, 如果超过了newThr = Integer.MAX_VALUE

计算出来新的阈值和新的容量以后就要进行扩容操作了

1. 将新的阈值给成员变量threshold, 创建新的数组(大小是newCap)
2. 将旧的元素迁移到新的数组中, 遍历每个table中的桶
   1. 如果桶中的元素不是null
      1. 没有下一个元素, 说明该位置没有发生哈希冲突, 计算这个元素新的index放到新的数组中, 将旧数组的元素置null, 方便GC回收
      2. 如果有下一个元素, 并且下一个元素是树节点, 将该树节点分裂成两个链表
      3. 如果有下一个元素, 并且下一个元素是链表节点, 说明通过了拉链法解决了哈希冲突, 这个时候通过`(e.hash & oldCap) == 0`来判断出来链表中的节点应该放在低位链表(原来的位置的链表)中还是放在高位链表中(新的位置 == oldCap + j该元素的原来的位置), 这里插入元素用的是**尾插法**



> 解释最后高位链表低位链表

现在有一个桶, 它的后面接得是链表, 说明发生了hash冲突, 不同的hash & (oldCao - 1)后的值一样, 我们现在要重新计算其中的节点在新的数组中的位置, 就会有两种情况

- 需要变化index的, 因为这个hash % newCap == newIndex
- 不需要变化index的, 因为hash % newCap == oldIndex

对于不需要变化index的, 说明这个hash值& newCap(n+1位) - 1和& oldCap(n位) - 1的结果是一样的, 也就是这个hash值的第n位是0, 这样才会&0和&1没有区别

对于需要变化index的, 对应下来就是这个hash值的第n位是1, 这样才会&1和&0有区别, 同时这个区别就是index值在原来的基础上第n位也变成1了, 那么这个时候的index值 == oldIndex(第n位等于0) + oldCap(这个数字就是只有第n位 = 1, 其他都是0)

因此我们能够通过e.hash & oldCap == 0, 也就是判断段e.hash的第n位是不是0来直接得出新的index

- 如果==0, 说明index不变
- 如果==1, 说明newIndex = oldIndex + oldCap

## 负载因子为什么是0.75

加载因子 = 填入哈希表中的数据个数 / 哈希表的长度

- 加载因子越小，填满的数据就越少，哈希冲突的几率就减少了，但浪费了空间，而且还会提高扩容的触发几率；
- 加载因子越大，填满的数据就越多，空间利用率就高，但哈希冲突的几率就变大了。

最后的结果就是为了减少哈希冲突发生的概率，当 HashMap 的数组长度达到一个**临界值**的时候，就会触发扩容，扩容后会将之前小数组中的元素转移到大数组中，这是一个相当耗时的操作。

这个临界值 = 初始容量 * 加载因子

这个负载因子 == 0.75是通过二项分布计算出来的

具体的计算过程在[加载因子为什么是0.75](https://javabetter.cn/collection/hashmap.html#_03%E3%80%81%E5%8A%A0%E8%BD%BD%E5%9B%A0%E5%AD%90%E4%B8%BA%E4%BB%80%E4%B9%88%E6%98%AF-0-75)

最后计算出来的值实际上是0.693, 考虑到HashMap的容量必须是2的n次幂, 0.75能保证与容积的乘积为整数

### 树化

1. **体积考虑**：TreeNodes约为普通节点大小的两倍，因此只在必要时使用
2. **使用条件**：只有当桶(bin)中的节点数量达到一定阈值(TREEIFY_THRESHOLD)时才转换成树形结构
3. **退化条件**：当树变得太小(由于移除操作或调整大小)时，会转换回普通的链表结构

## 哈希分布与泊松分布

这段文字指出，在哈希码分布良好的情况下，树结构很少被使用。在理想情况下，随机哈希码条件下，桶中节点的频率分布遵循泊松分布。

### 泊松分布的作用

泊松分布在这里用于:

1. **模型化碰撞概率**：描述各个桶中节点数量的概率分布
2. **理论基础**：为HashMap的性能分析提供数学依据
3. **预测分析**：帮助预测在随机哈希码条件下，桶大小的分布情况

### 参数解释

- 参数λ约为0.5(在默认负载因子0.75的条件下)
- 公式: P(k) = e^(-λ) * λ^k / k!
  - 其中P(k)是桶中恰好有k个节点的概率
  - e是自然对数的底数
  - λ是期望值(平均每个桶中的节点数)
  - k是节点数量
  - k!是k的阶乘

### 概率分布表

文中给出了当λ=0.5时不同k值的概率:

- 空桶(k=0): 约60.65%
- 1个节点(k=1): 约30.33%
- 2个节点(k=2): 约7.58%
- 3个节点(k=3): 约1.26%

这些数字表明:

- 大多数桶是空的或只有少量节点
- 桶中有8个或更多节点的概率非常小(不到千万分之一)

## 线程不安全

- 多线程扩容下会死循环(JDK7扩容的时候使用头插法导致的)
- 多线程下put会导致元素丢失
- put和get并发时会导致get到null值

### 死循环

假设有一个链表 A->B->null，两个线程同时进行扩容：

1. 线程1遍历到节点A，此时被挂起
2. 线程2完成整个扩容过程，链表变成 B->A->null（注意头插法导致顺序反转）
3. 线程1被唤醒，继续执行，但它仍然认为A后面是B，将B插入到新链表头部
4. 结果形成了 B->A->B...的环形链表

### 多线程put会导致元素丢失

多线程同时执行 put 操作时，如果计算出来的索引位置是相同的，那会造成前一个 key 被后一个 key 覆盖，从而导致元素的丢失。

```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
               boolean evict) {
    Node<K,V>[] tab; Node<K,V> p; int n, i;

    // 步骤①：tab为空则创建
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;

    // 步骤②：计算index，并对null做处理
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
    else {
        Node<K,V> e; K k;

        // 步骤③：节点key存在，直接覆盖value
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;

        // 步骤④：判断该链为红黑树
        else if (p instanceof TreeNode)
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);

        // 步骤⑤：该链为链表
        else {
            for (int binCount = 0; ; ++binCount) {
                if ((e = p.next) == null) {
                    p.next = newNode(hash, key, value, null);

                    //链表长度大于8转换为红黑树进行处理
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        treeifyBin(tab, hash);
                    break;
                }

                // key已经存在直接覆盖value
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                p = e;
            }
        }

        // 步骤⑥、直接覆盖
        if (e != null) { // existing mapping for key
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
    }
    ++modCount;

    // 步骤⑦：超过最大容量 就扩容
    if (++size > threshold)
        resize();
    afterNodeInsertion(evict);
    return null;
}
```

问题出现在步骤 ②

```java
if ((p = tab[i = (n - 1) & hash]) == null)
    tab[i] = newNode(hash, key, value, null);
```

### put和get并发时会导致get到null

线程 1 执行 put 时，因为元素个数超出阈值而导致出现扩容，线程 2 此时执行 get，就有可能出现这个问题。

 table = newTab 之后，线程 2 中的 table 此时也发生了变化，此时去 get 的时候当然会 get 到 null 了，因为元素还没有转移。

### 小结

HashMap 是线程不安全的主要是因为它在进行插入、删除和扩容等操作时可能会导致链表的结构发生变化，从而破坏了 HashMap 的不变性。具体来说，如果在一个线程正在遍历 HashMap 的链表时，另外一个线程对该链表进行了修改（比如添加了一个节点），那么就会导致链表的结构发生变化，从而破坏了当前线程正在进行的遍历操作，可能导致遍历失败或者出现死循环等问题。
