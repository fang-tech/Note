# ArrayList

## private static int hugeCapacity(int minCapacity) : 

```java
if (minCapacity < 0) // 错误的索引, 一定会导致OOM
    throw new OOMError();

if (minCapacity > MAX_ARRAY_SIZE) // MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8
    return (minCapacity > MAX_ARRAY_SIZE) ?
        Integer.MAX_VALUE :
        MAX_ARRAY_SIZE;
```

> 这个方法用于处理容量非常大的时候, 处理计算的时候可能会导致的整数溢出的情况, 这个时候就会给出整数溢出的错误
> 同时如果需要的数组大于安全的MAX_ARRAY_SIZE的时候, 能给出能给出的最大的大小, Integer.MAX_VALUE, 但是这个大小是不安全的, 因为JVM需要一定的头字节

- 为什么最大的索引是INT_MAX ? 
  - 因为Java中使用Integer作为索引, 最大的索引就是INT_MAX


## private static final inte MAX_ARRAY_SIZE = Integer.MAx_VALUE - 8 :

这个值是JVM能给出的最大的安全数组大小, 一些JVM实现需要在数组对象中保存额外的元数据, 头信息

## private void grow(int minCapacity) :

> 这个方法用于将数组扩容 : 创建新的大小的数组, 并将这个旧数组的数据移向新的数组 (这个行为的开销很大)

```java
    // 可能会导致整数溢出的代码
    int oldCapacity = elementData.length;
    int newCapacity = oldCapacity + (oldCapacity >> 1); // 这里的可能会导致整数溢出
    if (newCapacity < minCapacity)
        newCapacity = minCapacity; // minCapacity也可能溢出的整数
    if (newCapacity > MAX_ARRAY_SIZE) 
        newCapacity = hugeCapacity(newCapacity);
    elementData = Arrays.copy(newCapacity, elementData);
```

> 数组的容量会扩充为原先的1.5倍, 这个扩充是在时空上的平衡选择, 如果我们扩充的太大, 会占用额外的内存, 如果扩充得太小, 会频繁地扩充, 开销很大

## private void ensureExplicitCApacity(int minCapacity) :

```java
    modCount++;  // 不需要特别注意, 不是核心逻辑, 是修改计数器, 用于迭代器的一致性保证
    // 这部分代码会导致OOM, 执行扩容
    if (minCapcity > elementData.length)
        grow(minCapacity)
```

> 如果容量不足, 执行扩充

## private void ensureCapacityInternal(int minCapacity) :

```java
    // 最小容量是DEFAULT_CAPACITY
    if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
        minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity)

    ensureExplicityCacity(minCapacity)
```

> 这个方法是执行扩容的内部方法, 保证数组有足够的容量插入新的元素

## public void ensureCapacity(int minCapacity)

```java
    int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
        ? 0
        : DEAULT_CAPACITY
    if (minCapacity > minExpand)
        exsureCapasityInternal(minCapacity);
```

> - 向外暴露的修改数组容量的方法, 保证了外界输入的容量的有效性, 
> - 如果数组不为空, 说明里面已经有元素了, 这个时候就将minCapacity传递下去, 如果这个minCapacity < length会在ensureExplicitCApacity中在grow前被阻断
> - 如果数组为空, 这个时候小于DEFAULT_CAPACITY的容量都是无效的, 被阻断, 因为第一次初始化, 最小都会有DEFAULT_CAPACITY大小

# Stack & Queue

## Queue

Queue是个接口,  继承自Collection, 除了Collection的接口方法外, 额外提供了

|         | throws Exception | Returns special value |
| ------- | ---------------- | --------------------- |
| Insert  | add(e)           | offer(e)              |
| Remove  | remove()         | poll()                |
| Examine | element()        | peek()                |

## Deque

和Queue的接口基本一样, 只不过提供了两头的方法, 也就是能操作两端, 原本add只是在队尾插入元素, 在Deque中就有addFirst和addLast两套方法, 其他的方法也是一样, 提供了两套方法

## 方法剖析

这里只针对ArrayDeque, 底层使用数组实现, head指向头元素所在位置, tail指向尾端第一个可以插入的空位,  这个数组中不允许存放null

### addFirst(E e)

- 通过head == tail来判断队列满

```java
if (e == null) 
    throw new NPE();
elements[(head-1 + head) % (elements.length - 1)] = e; // 实际上的代码并不是这个, 但是实现的功能是这个
if (head == tail)
    dequeCapacity(); // 将数组的容量扩大成原来的两倍
```

- 真正的避免数组越界的方法 : elements[head = (head - 1) & (elements.length - 1)] = e
  - 我们要处理的实际上就只是head - 1以后 == -1 的情况
  - -1在计算机中存储的实际上是它的补码全11111....
  - 而elements.length - 1(我们这里deque的长度只会是2的倍数)这个时候的length - 1也是全是1, 保证了在这里取模运算的正确性
- 如果deque的长度不是2的倍数会发生什么? 我们假设长度是10 (1010)
  - head == 0的时候, 这个时候head - 1 == (1111_1111)_2 & (1010) = 1010, 这个时候的结果是正确的
  - head != 0 的时候, 假设head - 1 == 1, 这个时候我们预期取模后的结果是1(0001),但是 0001 & 1010 = 0000 (0)_10 出现了错误
  - \-1在计算机中存储的是全1保证了在head - 1 == -1 的时候运算出来的值是length - 1, elements.length 是 2 的幂次, length - 1的二进制是全1, 保证了head - 1 != -1 的时候计算出来的结果是head - 1

### doubleCapacity()



会将deque以head为分界线将数组内容划分为两部分, head的后一部分包含head, 这个部分在新的数组中的前一半,  前一部分划分到后一部分, **空间问题是在插入元素之后进行的**

![](https://pdai.tech/images/collection/ArrayDeque_doubleCapacity.png)

```java
assert head == tail;
int n = elements.length;
int p = head; // head所在索引前面的元素数量
int r = n - p; // head(包括head)后面的元素数量
int newCapacity = n << 1; // 将数组的大小扩充为原来的两倍
if (newCapacity < 0) // 整数溢出
    throw new IllegalStateException("deque too big");
Object a[] = new Object[newCapacity];
System.arraycopy(elements, p, a, 0, r); // 复制右半部分
System.arraycopy(elements, 0, a, r, p); // 复制左半部分
elements = (E[]) a;
head = 0;
tail = n;
```

# PriorityQueue

Java中的堆是 **小顶堆**, 元素的顺序可以通过构造时传入的比较器实现(Comparator)

![](https://pdai.tech/images/collection/PriorityQueue_base.png)

- leftNo = parentNo * 2 + 1
- rightNo = parentNo * 2 + 2
- parentNo = (nodeNo-1) / 2

## 方法剖析



### siftup(int k, E x)

![](https://pdai.tech/images/collection/PriorityQueue_offer.png)

我们在数组尾部新增了一个元素以后, 因为这个新增的元素是任意的, 我们为了维护循环不变式, 我们需要重新调整数组中元素, 我们将这个从数组的尾部, 将元素逐步向上直到找到合适的位置称作上浮

- 这个函数的作用是 **从k位置开始, 以x为参照,  插入一个元素 (k位置之后的元素不会受到影响)**, 向堆中插入一个元素

- 将最后一个元素和parent进行对比, 如果比parent小则上浮, else break

```java

private void siftup(int k, E x) {
    while (k > 0){ // 等效于k != 0, 没有parent了
        int parent = (k - 1) >>> 1; // parentNo = (childNo - 1) / 2
        Object e = queue[parent];
        if (comparator.compare(x, (E) e)) > 0) // x > e, 也就是child < parent的时候
            break; // found right place
        queue[k] = (E) queue[parent];
        k = parent;
    }
    queue[k] = x;
}

```

 

### siftdown(int k, E x)

![](https://pdai.tech/images/collection/PriorityQueue_poll.png)

删除了顶部的最小的元素以后, 这个时候堆的形式也遭到了破坏, 我们将最后一个元素上提到顶元素, 这个时候再将这个元素下沉到合适的位置

- 这个方法的作用是 : **从指定的位置开始, 将x逐层向下与当前节点的左右孩子中的较小的那个交换位置, 知道x 小于等于左右孩子中的任何一个为止**

```java
private void siftDown(int k, E x) {
    int half = size >>> 1;
    while (k < half){ // if k >= half 说明这个节点不再有child节点了
        int child = (k << 1) + 1; // leftChildNo = parentNo * 2 + 1
        int right = child + 1;
        Object c = queue[child];
        if (comparator.compare((E) c, (E) queue[right]) > 0) left > right
            c = queue[child = right];
        if (comparator.compare(x, (E) c) <= 0) // x <= child
            break;
        queue[k] = queue[child];
        k = child;
    }
    queue[k] = x; // 没有child了, 或者已经找到合适的位置了
}
```

### offer(E e)

```java
private boolean offer(E e) {
    if (e == null)
        throw new NPE();
    int i = size;
    if (i > queue.length)
        grow(i+1); // 扩容
   	size = i + 1;
    if (i == 0) // 队列为空的时候
        queue[0] = e;
    else 
        shif(i, e);
    return true;
}
```

### poll()

```java
public E poll(){
    if (size == 0) 
        return null;
    int s = --size;
    modCount++;
    E result = (E) queue[0];
	E x = (E) queue[s];
    queue[s] = null;
    if (s != 0) 
        siftDown(0, x);
    return result;
}
```



### remove(Object o)

> 这个方法更加特殊一点, 需要分成删除的元素是不是最后一个元素考虑, 是最后一个元素, 则直接删除, 如果不是最后一个元素, 就要执行siftDown(从删除点开始, 最后一个元素为参照)

# HashMap

## 方法详解

### hash

```java
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```

- 最后的异或操作保证了hashcode的高16位也能在计算桶的索引的时候也能发挥作用
- 所以通过异或操作将hashcode的高16位和低16位混合

### put(K key, V value)

这个方法直接调用的putVal内部函数, 将hash(key), key, value传进去

### putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict)



