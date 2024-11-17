- 关于cpp中的一些API和语法的记录

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

  