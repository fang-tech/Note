# 泛型的基本使用

## 泛型类

在类名后面用\<>括起来泛型变量就行了, 在类中的就将这个泛型当作普通的类型使用就行
```java
class class Point<T>{
    T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

public class GenericsDemo01 {
    public static void main(String[] args) {
        Point<String> point = new Point<>();
        point.setData("阿达");
//        point.setData(1231231);
        //   泛型会提供类型检查, 保证类型的安全, 错误的类型会给出报错 
        System.out.println(point.getData());
    }
}
```

## 泛型方法

- 和泛型类不一样, 泛型方法我们需要传入的一个必须的参数是`Class<T>`, 通过这个我们才能确定传入方法的参数的类型, 然后再通过反射在方法中构建出来的实例
- 使用泛型方法的时候我们也需要传入某个类的class, 来指明类, 通过Class.forName或者通过类的.class属性都可以

```java
class Generic{

    public static <T> T getObject(Class<T> c) throws InstantiationException, IllegalAccessException {
        // 创建泛型对象
        T t = c.newInstance();
        return t;
    }
}

public class GenericsDemo01 {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        System.out.println(Generic.getObject(String.class).getClass());
        System.out.println(Generic.getObject(Class.forName("java.lang.String")).getClass());
    }
}

```

> 为什么需要使用泛型方法呢? 泛型类在使用的时候需要实例化, 不够灵活, 想换一个类型都需要重新new一次, 使用泛型方法只需要在调用的时候指明类型就可以了, 更加灵活

## 泛型的上下限

> 为了解决泛型隐式转换类型的操作导致的类型错误等问题, 我们引入泛型的上下限机制保证限定传入的参数的类型

### 上限

通过`T extends Class`来保证上限, 即这个传递进来的T必须是Class的子类

### 下限

通过`? super Class`来保证下限, 即这个传递进来的?类必须是Class及其父类, 一般在方法中使用

```java
class
```

## 泛型的擦除

### 怎么进行的擦除

- 消除类型参数声明, 将所有的 `<>` 及其包裹的内容内容擦除
- 将根据类型参数的上下界推断并替换所有的类型参数为原生态类型
  - 如果参数没有上下界, 则将类型替换成Object
  - 如果参数有上下界, 则会将类型替换成最左限定类型 (即最祖宗的那个父类)
- 为了保证类型安全, 有时候会加上强制转型
- 自动产生桥接方法以保证擦除类型以后的代码仍具有多态性

### 如何理解java中的泛型是伪泛型, 泛型中的类型擦除 ?

Java中的泛型的特性是1.5之后才加入的特性, 为了向前兼容, Java中的泛型是 "伪泛型", 在语法上支持泛型, 但是编译以后会进行 **类型擦除**, 将所有的泛型根据上下界替换成对应的类, 最后编译以后运行的代码实际上是只有原生态类型的代码

### 如何证明泛型类型的被擦除呢

- 证明泛型类型别擦除, 只需要证明包含泛型的类中, 类中的泛型元素不论我们传入什么, 最后的class都只是Object, 或者是相同的

- 原始类型相等

```java
List<Integer> list1 = new ArrayList<Integer>();
List<String> list2 = new ArrayList<String>();

list1.add(123);
list2.add("heloo");

list1.getClass() == list2.getClass();  // true
```

- 能通过反射添加别的类型的元素

```java
List<Integer> list = new ArrayList<Integer>();
list.add(123);
list.getClass().getMethod("add", Object.class).invoke(list, "ard");

print list; // 123, ard
```

### 如何理解泛型被擦除以后的原始类型

所有的泛型类型都会被擦除并替换成原始类型, 在最后字节码中的类型变量真正的类型就是原始类型

- 调用泛泛型方法的时候可以指定泛型也可以不指定泛型
  - 如果没有过指定了泛型, 最后的原始类型就是, 这个泛型方法声明的所有泛型的公共父类, 不是最父的, 是最父的话就全是Object
  - 如果指定了泛型, 最后的原始类型就是该泛型的的实例的类型或者其父类

### 如何理解泛型的编译期检查

泛型在Java中存在的意义可以说就是类型检查, 编译器会先执行类型检查, 再执行泛型擦除, 最后进行编译

检查什么, 这里的检查是怎么进行的?
**类型检查是针对引用的, 谁是一个引用, 这个引用调用了泛型方法, 就会触发类型检查, 所以检查的类型是属于引用的**

```java
ArrayList<String> list = new ArrayList();
ArrayList list = new ArrayList<String>();
```

- 这里的第二种实际上是无效的的, 会成为类型检查的漏网之鱼, 因为这个引用是没有类型的, 所以通过这个引用调用泛型方法的时候, 无法触发类型检查
- new ArrayList()实质上只是在内存开辟一片空间, 可以存储任何对象, 真正涉及类型检查的是它的引用

### 如何理解泛型的多态, 泛型的桥方法

- 我们拿set方法举例
如果对泛型类使用继承, 并且在子类中用子类或者实现填充泛型, 这个时候, 我们分析预期生成的字节码, 我们就会发现父类中的函数和子类中的重写的函数, 只有传入的参数是不一样的, 这个时候就不是重写方法, 而是重载, 因为两个方法的参数类型并不一样

- JVM通过桥方法维护了泛型的多态: 通过桥方法, 实现了泛型重写
  - JVM会为通过桥方法重写原来的父方法, 会自己创建一个和原来的父方法一样的参数列表的桥方法, 然后在桥方法中调用我们自己写的方法, 通过这样的形式完成了重写

```java
// 自动生成的桥方法
public void set(Object item) {
    set((Car)item);  // 调用我们手写的方法
}
```

- 可以通过反射API来获取到桥方法



