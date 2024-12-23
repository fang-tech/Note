## 2. IoC注入
### 2.1 set注入

- 在代码中, 创建setXxx的方法
- 在需要创建依赖的Bean里创建Property属性, 添加name和ref
    - name填入xxx(set方法后面的单词的首字母小写结果)
    - ref 填入需要注入的实际的bean的id, 这里的注入, 可以理解为一种向set方法里传参