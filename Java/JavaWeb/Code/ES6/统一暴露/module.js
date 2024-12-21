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
