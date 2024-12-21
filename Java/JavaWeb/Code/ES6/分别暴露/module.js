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