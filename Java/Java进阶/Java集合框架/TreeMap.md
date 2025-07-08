# TreeMap

## TreeMap实现了元素根据key进行排序

```java
public V put(K key, V value) {
    Entry<K,V> t = root; // 将根节点赋值给变量t
    if (t == null) { // 如果根节点为null，说明TreeMap为空
        compare(key, key); // type (and possibly null) check，检查key的类型是否合法
        root = new Entry<>(key, value, null); // 创建一个新节点作为根节点
        size = 1; // size设置为1
        return null; // 返回null，表示插入成功
    }
    int cmp;
    Entry<K,V> parent;
    // split comparator and comparable paths，根据使用的比较方法进行查找
    Comparator<? super K> cpr = comparator; // 获取比较器
    if (cpr != null) { // 如果使用了Comparator
        do {
            parent = t; // 将当前节点赋值给parent
            cmp = cpr.compare(key, t.key); // 使用Comparator比较key和t的键的大小
            if (cmp < 0) // 如果key小于t的键
                t = t.left; // 在t的左子树中查找
            else if (cmp > 0) // 如果key大于t的键
                t = t.right; // 在t的右子树中查找
            else // 如果key等于t的键
                return t.setValue(value); // 直接更新t的值
        } while (t != null);
    }
    else { // 如果没有使用Comparator
        if (key == null) // 如果key为null
            throw new NullPointerException(); // 抛出NullPointerException异常
            Comparable<? super K> k = (Comparable<? super K>) key; // 将key强制转换为Comparable类型
        do {
            parent = t; // 将当前节点赋值给parent
            cmp = k.compareTo(t.key); // 使用Comparable比较key和t的键的大小
            if (cmp < 0) // 如果key小于t的键
                t = t.left; // 在t的左子树中查找
            else if (cmp > 0) // 如果key大于t的键
                t = t.right; // 在t的右子树中查找
            else // 如果key等于t的键
                return t.setValue(value); // 直接更新t的值
        } while (t != null);
    }
    // 如果没有找到相同的键，需要创建一个新节点插入到TreeMap中
    Entry<K,V> e = new Entry<>(key, value, parent); // 创建一个新节点
    if (cmp < 0) // 如果key小于parent的键
        parent.left = e; // 将e作为parent的左子节点
    else
        parent.right = e; // 将e作为parent的右子节点
    fixAfterInsertion(e); // 插入节点后需要进行平衡操作
    size++; // size加1
    return null; // 返回null，表示插入成功
}
```

1. 检查根节点是不是空
   1. 如果根节点是空, 检查key的类型是否合法`(compare(key, key))`, 然后创建一个新的节点, root = newEntry
   2. size = 1, return null表示插入成功
2. 根节点不是空, 就要找到插入的位置
3. 尝试获取比较器, 如果有比较器, 在比较的部分通过传进来的比较器进行比较, 如果没有比较器, 说明key必须实现了Comparable接口, 将key强转成Comparator类型, 然后进行比较
   1. 比较这个key和当前节点的key(t.key)之间的大小
   2. 如果key更大, 说明接下来要向右查找, t = t.right
   3. 如果key更小, 说明接下来要向左查找, t = t.left
   4. 如果key == t.key, 直接更新t的值
4. 没有找到相同的键, 创建一个新的节点, 让parent(t的父节点, 因为最后出循环的条件是t == null)的孩子为新的节点
5. 最后调用`fixAfterInsertion(e)`, 插入节点后进行平衡操作

