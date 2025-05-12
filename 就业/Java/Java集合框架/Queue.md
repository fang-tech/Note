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



