---
tags:
  - frontend
  - JavaWeb
  - JS
  - ES6
  - Java
---
# ES6
## 1. ES6的介绍
> ECMAScript 6，简称ES6，是**JavaScript**语言的一次重大更新。它于**2015**年发布，是原来的ECMAScript标准的第六个版本。ES6带来了大量的新特性，包括箭头函数、模板字符串、let和const关键字、解构、默认参数值、模块系统等等，大大提升了JavaScript的开发体验。`由于VUE3中大量使用了ES6的语法,所以ES6成为了学习VUE3的门槛之一` ES6对JavaScript的改进在以下几个方面：

1. 更加简洁：ES6引入了一些新的语法，如箭头函数、类和模板字符串等，使代码更加简洁易懂。
    
2. 更强大的功能：ES6引入了一些新的API、解构语法和迭代器等功能，从而使得JavaScript更加强大。
    
3. 更好的适用性：ES6引入的模块化功能为JavaScript代码的组织和管理提供了更好的方式，不仅提高了程序的可维护性，还让JavaScript更方便地应用于大型的应用程序。
    

> 总的来说，ES6在提高JavaScript的核心语言特性和功能方面取得了很大的进展。由于ES6已经成为了JavaScript的标准，它的大多数新特性都已被现在浏览器所支持，因此现在可以放心地使用ES6来开发前端应用程序。
## 2. ES6的变量和模板字符

> ES6新增了let和const, 用于声明变量

- let和var的区别
    1. let有作用域, 在不是函数的花括号内
    2. 不能重复声明
    3. 不存在变量提升, 不能先使用后定义
    4. 不会变成window属性
    5. 循环中更推荐使用
```html
    <script>
    {
        let a = 1
        let b = 2
        console.log(a)
        console.log(b)
    }
    // 有作用域
    // console.log(a)
    // console.log(b)

    // 不能重复赋值
    // let name = 1
    // let name = 2 

    // 变量提升
    console.log(test)
    var test = 'test'
    // console.log(test1)
    // let test1 = 'test1'

    // 不会成为window属性
    var a  = 100
    console.log(window.a) // 100
    let b = 122
    console.log(window.b) // undefine

    // 循环中推荐使用
    for (let i=0;i < 10; i++){
        console.log(i)
    }
    console.log(i)
    </script>
```

- const和var的差异
    1. 和let类似, 是不能修改的let
    2. 对于对象和数组, const只能保证地址不变, 不保证对象内部的值不变
```html
    <script>
        const PI = 3.1415926

        const TEAM = [1,2,3,4,5]
        TEAM.push(6)
        // TEAM = []
        console.log(TEAM)
    </script>
```

> 模板字符串

- 使用\`\`括起
- 允许多行字符串
- 支持行内拼接

```html
<script>
        let ulstr = 
            '<ul>'+
            '<li>JAVA</li>'+
            '<li>html</li>'+
            '<li>VUE</li>'+
            '</ul>'
        console.log(ulstr)

        let ulstr2 = `
        <ul>
            <li>JAVA</li>
            <li>html</li>
            <li>VUE</li>
        </ul>`
        console.log(ulstr2)

        // 普通字符串拼接
        let name = "小明"
        let infoStr = name+'sb'
        console.log(name+infoStr)

        // 模板字符串拼接
        let infoStr2 = `${name}被品味sb`
        console.log(infoStr2)
    </script>
```

## 3. ES6的解构表达式

```html
    <script>
    // 数组解构
        let [a,b,c] = [1,2,"33"]
        console.log(a)
        console.log(b)
        console.log(c)

        // 默认值
        let [a1,b1,c1, d = 4] = [1,2,3,5]
        console.log(d)

    // 对象解构
        let {o1, o2} = {o1:1, o2:2}
        //新增变量名必须和属性名相同, 本质是初始化变量的值为对象中同名属性的值
        // 等价于 let o1 = Object.o1, let o2 = Object.o2
        console.log(o1) // 1
        console.log(o2) // 2

        // 该语句将对象 {a: 1, b: 2} 中的 a 属性值赋值给 a 变量，b 属性值赋值给 b 变量。 可以为标识符分配不同的变量名称，使用 : 操作符指定新的变量名
        let {a2:x, b2:y} = {a2:2, b2:12421}
        console.log(x) // 2
        console.log(y) // 12421

    // 函数参数解构
    function sum([x,y]) {
        return x + y
    }
    sum([1,2]); // 3
    </script>
```

## 4. ES6的箭头函数

### 4.1 声明和特点

> 语法类似于Java中的Lambda表达式

```html
    <script>
        let fn1 = function(){}
        let fn2 = x => {}
        let fn3 = () => {}
        let fn4 = x => console.log(x) // 只有一行函数体可以省略{}
        let fn5 = x => x + 1
        //2. 使用特点 箭头函数this关键字
        // 在 JavaScript 中，this 关键字通常用来引用函数所在的对象，
        // 或者在函数本身作为构造函数时，来引用新对象的实例。
        // 但是在箭头函数中，this 的含义与常规函数定义中的含义不同，
        // 并且是由箭头函数定义时的上下文来决定的，而不是由函数调用时的上下文来决定的。
        // 箭头函数没有自己的this，this指向的是外层上下文环境的this

        let person ={
            name:"张三",
            showName: function(){
                console.log(this) // 指向的person对象
                console.log(this.name)
            },
            viewName: () =>{
                console.log(this) // 指向window
                console.log(this.name)
            }
        }

        person.showName()
        person.viewName()

        // 对于这样依赖于上下文的this的应用
        function Counter() {
            this.count = 0;
            setInterval(()=>{
                // 这里的this是上一层作用域中的this, 即Counter实例化对象
                this.count++;
                console.log(this.count)
            }, 1000)
        }

        let counter = new Counter();
    </script>
```

- 可以利用这个this的特性返回上一层作用域

### 4.2 实践和应用场景

```html
    <div id="xdd"></div>
    <script>
        let xdd = document.getElementById("xdd")
        xdd.onclick = function(){
            console.log(this)
            let _this = this; // xdd
            // 开启定时器
            setTimeout(function(){
                console.log(this)
                // 变成粉色
                _this.style.backgroundColor = 'black'
            }, 2000)
        }

        xdd.onclick = function(){
            console.log(this)
            setTimeout(()=>{
                console.log(this)
                this.style.backgroundColor = 'black'
            }, 2000)
        }
    </script>
```

- 可以充当一个../, 向上一层索引的功能

### 4.3 rest和spread

> rest参数, 在形参上使用和Java中的可变参数一样

```html
    <script>
        // res参数
        let fun1 = function(...args){ 
            console.log(args)
        }

        let fun2 = (...args) => console.log(args)
        fun1(1,2,3,4)
        fun2(1,2,3,4,5,6,6)
        
        // 只能是最后一个参数, 并且只能有一个
        // let fun3 = (...args, ...args2) => {} // 报错
    </script>
```

> spread参数, 在实参上使用rest参数

```html
    <script>
        // spread 语法可以将[1,2,3] => 1,2,3 , 像是一种拆包
        let arr = [1,2,3]
        // let arrSpread = ...arr // 错误, 不能直接拆包, 只能在函数调用的时候作为参数传递
        let fn1 = (a,b,c) => console.log(a,b,c)
        fn1(...arr) // 1 2 3
        fn1(arr) // [1,2,3] undefine undefine

        // 应用场景1 合并数组
        let arr2 = [2,3,4]
        console.log(...arr, ...arr2)
        // 应用场景2 合并对象属性
        let p1 = {name:"张三"}
        let p2 = {age:10}
        let p3 = {gender:"boy"}
        let person = {...p1, ...p2, ...p3}
        console.log(person)
    </script>
```

## 5. es6的对象创建和拷贝

### 5.1 对象创建上的语法糖

```html
    <script>
        class Person{
            // 属性名
            #n; // 私有成员
            age;
            
            // getter and setter
            get name() {
                console.log("getter")
                return this.#n
            }
            set name(n) {
                console.log("setter")
                this.#n = n;
            }

            // 实例方法
            eat(food) {
                console.log(`${this.age}的${this.#n}正在吃${food}`)
            }

            // 构造方法
            constructor(name, age){
                this.#n = name
                this.age = age
            }
        }

        let p = new Person();
        p.name = "zhangsan";
        p.age = 123;
        // let p = new Person(" zhangsan ", 123)
        p.eat("答辩")
        // p.#n; 私有属性不能直接访问

        // 继承
        class Student extends Person{
            score;
            constructor(name, age, score) {
                super(name,age)
                this.score = score
            }

            study(){
                console.log(`${this.age}岁的${this.name}考了${this.score}正在装死`)
            }
            // 静态方法 
            static sum(a,b){
                return a+b
            }
        }

        let s = new Student("fun",1213,234)
        s.study()
        console.log(Student.sum(1,23))
    </script>
```

### 5.2 对象的深拷贝和浅拷贝

```html
    <script>
        // 浅拷贝
        let arr = [1,2,3,4]
        let person = {
            name:"zhangsn",
            number : arr
        }

        let person2 = person
        person2.name = "sss231"
        console.log(person)
        console.log(person2)

        // 深拷贝
        // 实质上是通过JSON和字符串的转化生成一个新的对象
        let person3 = JSON.parse(JSON.stringify(person))
        person3.name = "ssdevice-width"
        console.log(person)
        console.log(person3)

    </script>script>


/script>
```

## 6. ES6的模块化处理
### 6.1 模块化的介绍
> 模块化是一种组织和管理前端代码的方式，将代码拆分成小的模块单元，使得代码更易于维护、扩展和复用。它包括了定义、导出、导入以及管理模块的方法和规范。前端模块化的主要优势如下：

1. 提高代码可维护性：通过将代码拆分为小的模块单元，使得代码结构更为清晰，可读性更高，便于开发者阅读和维护。
    
2. 提高代码可复用性：通过将重复使用的代码变成可复用的模块，减少代码重复率，降低开发成本。
    
3. 提高代码可扩展性：通过模块化来实现代码的松耦合，便于更改和替换模块，从而方便地扩展功能。
    

> 目前，前端模块化有多种规范和实现，包括 CommonJS、AMD 和 ES6 模块化。ES6 模块化是 JavaScript 语言的模块标准，使用 import 和 export 关键字来实现模块的导入和导出。现在，大部分浏览器都已经原生支持 ES6 模块化，因此它成为了最为广泛使用的前端模块化标准. `

---

> 简单来说就是可以复用其它文件中的代码

- ES6模块化的几种暴露和导入方式
    1. 分别导出
    2. 统一导出
    3. 默认导出
- ES6中无论以何种方式导出,导出的都是一个对象,导出的内容都可以理解为是向这个对象中添加属性或者方法
### 6.2 分别导出

- app.js
```js
// 导入module.js中的所有成员

import * as m1 from './module.js'

console.log(m1.PI)

let result = m1.sum(1,2)
console.log(result)

let person = new m1.Person("张三", 123123)
person.toString();
```

- module.js
```js
// 1. 分别暴露
// 将需要暴露的模块添加export关键字

export const PI = 3.1415926

export function sum(a,b){
    return a+b
}

export class Person{
    name
    age
    constructor(name, age){
        this.name = name
        this.age = age
    }

    toString(){
        console.log(`${this.age}岁的${this.name}`)
    }
}
```

- index.html (后面的演示代码中index.html都是这个样子的, 所以后面不再写了)
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="./app.js" type="module"></script>
    <title>Document</title>
</head>
<body>
    
</body>
</html>
```

### 6.3 统一导出

- app.js
```js
// import {PI, sum, Person} from './module.js'
// import {PI as pi, sum as add, Person as People} from './module.js'
import {PI, Person, sum, PI as pi, sum as add, Person as People} from './module.js'

console.log(PI)
console.log(pi)
let result1 = sum(1,223)
let result2 = add(12,3213)
console.log(result1)
console.log(result2)

let person = new Person("萨汗", 1312)
let person2 = new People("sad", 1321)
person.sayHello()
person2.sayHello()
```

- module.js
```js
// 统一暴露
// 整个模块对外导出

const PI = 3.14
// 定义一个函数
function sum(a, b) {
    return a + b;
}
// 定义一个类
class Person {
constructor(name, age) {
    this.name = name;
    this.age = age;
}
sayHello() {
    console.log(`Hello, my name is ${this.name}, I'm ${this.age} years old.`);
}
}
export {
    PI,
    sum,
    Person,
}

```

### 6.4 默认导出

- app.js
```js
import * as m1 from './module.js'

console.log(m1.default(12,1234))

// import {default as add} from './module.js'
import add from './module.js'

console.log(add(1,24))

```

- module.js
```js
const PI = 3.1415926

let sum = function(a,b){
    return a+b
}

// 只能有一个
export default sum
// export default PI
```