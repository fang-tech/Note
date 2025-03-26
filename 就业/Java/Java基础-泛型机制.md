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

通过`? super Class`来保证下限, 即这个传递进来的?类必须是Class及其父类