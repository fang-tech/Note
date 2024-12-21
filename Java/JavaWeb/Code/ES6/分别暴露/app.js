// 导入module.js中的所有成员

import * as m1 from './module.js'

console.log(m1.PI)

let result = m1.sum(1,2)
console.log(result)

let person = new m1.Person("张三", 123123)
person.toString();