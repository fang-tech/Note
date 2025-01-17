## 关于cpp中的一些API和语法的记录

### 数组

- CPP中创建数组是`int a[]`  

### string

- string和int之间的相互转化 : 将string => int : `stoi()` , 将int => string : `to_string()`

### pair

- 在`<utility>`库中

```cpp
// 创建
vector<pair<int,int>> pair_test;

// 构造与传值
pair<int,int> p = make_pair(1,2);
pair<int,int> p(1,2);

// 取出值
p.first;
p.second;
```

### vector

- 构造
  ```cpp
  // 默认构造：空 vector
  std::vector<int> v1;
  
  // 指定大小的 vector，元素默认值初始化
  std::vector<int> v2(5); // 5 个元素，值为 0（int 的默认值）
  
  // 指定大小和初始值
  std::vector<int> v3(5, 10); // 5 个元素，值为 10
  
  // 通过其他容器初始化
  std::vector<int> v4 = {1, 2, 3, 4, 5}; // 使用初始化列表
  std::vector<int> v5(v4.begin(), v4.end()); // 通过迭代器范围
  
  // 使用拷贝构造
  std::vector<int> v6(v3);
  ```
  
### unorder_map\<obj,obj>

> 可以用重复, 就直接当正常的hashmap使用就行

```cpp
unorder_map <int, int> map;

map[index] = val;
```

### unorder_set\<obj> 

>没有重复的

```cpp
unorder_set<int> set;
set.insert(int);
set.count(int); // 返回1说明存在, 返回0说明不存在
```


## 常用操作的实现

### 无穷大和无穷小

```cpp
// 无穷大
const int INF = 0x3f3f3f3f;

// 无穷小
-INF
```

### 通过memset设置最大int和最小int和无穷大

```cpp
// memset是设置通过设置每个字节的值为中间的数字来初始化数组的, 所以一般只用来初始化为0或-1
// 设置为最大, 即每个字节设置为127, 需要考虑到符号位
memset(dp, 127, sizeof(dp));

// 设置为最小, 即每个字节设置为128, 
memset(dp, 128, sizeof(dp));

// 设置为无穷大, 即每个字节设置为0x3f,
memset(dp, 0x3, sizeof(dp));
```

