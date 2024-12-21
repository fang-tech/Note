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