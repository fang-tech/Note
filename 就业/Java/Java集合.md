
- private static int hugeCapacity(int minCapacity) : 

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


- private static final inte MAX_ARRAY_SIZE = Integer.MAx_VALUE - 8 :

这个值是JVM能给出的最大的安全数组大小, 一些JVM实现需要在数组对象中保存额外的元数据, 头信息

- private void grow(int minCapacity) :

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

- private void ensureExplicitCApacity(int minCapacity) :

```java
    modCount++;  // 不需要特别注意, 不是核心逻辑, 是修改计数器, 用于迭代器的一致性保证
    // 这部分代码会导致OOM, 执行扩容
    if (minCapcity > elementData.length)
        grow(minCapacity)
```

> 如果容量不足, 执行扩充

- private void ensureCapacityInternal(int minCapacity) :

```java
    // 最小容量是DEFAULT_CAPACITY
    if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
        minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity)

    ensureExplicityCacity(minCapacity)
```

> 这个方法是执行扩容的内部方法, 保证数组有足够的容量插入新的元素

- public void ensureCapacity(int minCapacity)

```java
    int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
        ? 0
        : DEAULT_CAPACITY
    if (minCapacity > minExpand)
        exsureCapasityInternal(minCapacity);
```

> 向外暴露的修改数组容量的方法, 保证了外界输入的容量的有效性, 
> 如果数组不为空, 说明里面已经有元素了, 这个时候就将minCapacity传递下去, 如果这个minCapacity < length会在ensureExplicitCApacity中在grow前被阻断
> 如果数组为空, 这个时候小于DEFAULT_CAPACITY的容量都是无效的, 被阻断, 因为第一次初始化, 最小都会有DEFAULT_CAPACITY大小

