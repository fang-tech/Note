---
tags:
  - frontend
  - JavaWeb
---
# JavaScript

## 1. JS的特性

- JS是一门解释性的语言
- JS是弱类型的语言
- 句尾;可加可不加
- JS并不算是面对对象的语言

## 2. 数据类型和运算符

### 数据类型

1. 数字类型 Number
2. 字符串类型 String
3. 布尔类型 boolean
4. 引用类型 Object
5. 函数类型 function
6. 未命名的类型 var
7. 可以通过 typeof判断数据类型

### 运算符

1. == 与 ===

   前者在进行判断的时候, 如果两边的变量类型不一致, 会进行对齐转换处理, 后在进行判断

   而===, 如果类型不一致, 直接回返回false

### 特性

> 变量具有的特性

- var类型的变量可以再次声明
- 所有类型的变量可以被多次赋予不同类型的值
- 因为是弱类型, 所以如果变量声明但是未赋值是undefine类型
- 如果给变量赋值null, 数据类型是Object
- 声明变量的时候, **不能指定类型**

###  总结

> 声明变量的时候, **不能指定类型**

## 3. JS的流程控制和函数

### 3.1 分支结构

和Java一样

### 3.2 循环结构

> for循环

- 和Java一致

> foreach循环

- 返回的是数组的索引
- 使用in而不是:

```js
var arr = [1,2,3,4,5];
document.write("<ul>");

for (var index in arr) {
document.write("<li>"+arr[index]+"</li>");
}
document.write("</ul>")
```

### 3.3 函数声明

- 函数声明的时候需要function关键字
- 参数列表不需要表明参数类型
- 直接return就行, 没有返回限制
- 调用函数的时候实参数量和形参数量可以不一致, 但是多出来的部分不生效

```js
// 第一种声明方式
function sum (a, b) {
return a + b;
}

var s = sum(1,2);
console.log(s);

// 第二种声明方式
var add = function (a, b) {
return a + b;
}

console.log(add(1,23));
```

## 4. JS的对象和JSON

### 4.1 对象

> 声明对象的两种方式

```js

// 第一种方式
var person = new Object();

person.name = "...";
person.age = 20;
person.arr = [1,2,3,4];
person.func = function(){
console.log(this.age + " years, " + this.name);
for (var index in this.arr) {
console.log(this.arr[index]);
}
}

person.func();

// 第二种方式
var person = {
"name" : "funfs",
"foods" : [1,2,3,4],
}

console.log(person.foods);
```

- 第二种方式更像是JSON风格的

### 4.2 JSON格式

> 全程 JavaScript Object Notation, JS对象简谱, 因为容易转换为对象, 所以常被用于数据传输

- JSON.parse : 将JSON格式字符串转化为Object对象
- JSON.stringify : 将Object对象转化为JSON格式字符串

```js
var personStr = '{"name":"...", "age":123, "girlFriend":{"name":"ghg", "age":12}, "foods":[1,2,3,4]}';

console.log(personStr);
console.log(typeof personStr);

var person = JSON.parse(personStr);
console.log(typeof person);
console.log(person.name);
console.log(person);


var person = {
"name":"ssb",
"foods":[1,2,3,4],
}

var personStr = JSON.stringify(person);
console.log(personStr); // {"name":"ssb","foods":[1,2,3,4]}
```

### 

### 4.3 JS常见对象

#### 4.3.1 数组

> 创建数组的四种方式

+ new Array()                                                   创建空数组
+ new Array(5)                                                 创建数组时给定长度
+ new Array(ele1,ele2,ele3,... ... ,elen);          创建数组时指定元素值
+ [ele1,ele2,ele3,... ... ,elen];                           相当于第三种语法的简写

> 数组的常见API

+ 在JS中,数组属于Object类型,其长度是可以变化的,更像JAVA中的集合

| 方法                                                                         | 描述                                               |
| :------------------------------------------------------------------------- | :----------------------------------------------- |
| [concat()](https://www.runoob.com/jsref/jsref-concat-array.html)           | 连接两个或更多的数组，并返回结果。                                |
| [copyWithin()](https://www.runoob.com/jsref/jsref-copywithin.html)         | 从数组的指定位置拷贝元素到数组的另一个指定位置中。                        |
| [entries()](https://www.runoob.com/jsref/jsref-entries.html)               | 返回数组的可迭代对象。                                      |
| [every()](https://www.runoob.com/jsref/jsref-every.html)                   | 检测数值元素的每个元素是否都符合条件。                              |
| [fill()](https://www.runoob.com/jsref/jsref-fill.html)                     | 使用一个固定值来填充数组。                                    |
| [filter()](https://www.runoob.com/jsref/jsref-filter.html)                 | 检测数值元素，并返回符合条件所有元素的数组。                           |
| [find()](https://www.runoob.com/jsref/jsref-find.html)                     | 返回符合传入测试（函数）条件的数组元素。                             |
| [findIndex()](https://www.runoob.com/jsref/jsref-findindex.html)           | 返回符合传入测试（函数）条件的数组元素索引。                           |
| [forEach()](https://www.runoob.com/jsref/jsref-foreach.html)               | 数组每个元素都执行一次回调函数。                                 |
| [from()](https://www.runoob.com/jsref/jsref-from.html)                     | 通过给定的对象中创建一个数组。                                  |
| [includes()](https://www.runoob.com/jsref/jsref-includes.html)             | 判断一个数组是否包含一个指定的值。                                |
| [indexOf()](https://www.runoob.com/jsref/jsref-indexof-array.html)         | 搜索数组中的元素，并返回它所在的位置。                              |
| [isArray()](https://www.runoob.com/jsref/jsref-isarray.html)               | 判断对象是否为数组。                                       |
| [join()](https://www.runoob.com/jsref/jsref-join.html)                     | 把数组的所有元素放入一个字符串。                                 |
| [keys()](https://www.runoob.com/jsref/jsref-keys.html)                     | 返回数组的可迭代对象，包含原始数组的键(key)。                        |
| [lastIndexOf()](https://www.runoob.com/jsref/jsref-lastindexof-array.html) | 搜索数组中的元素，并返回它最后出现的位置。                            |
| [map()](https://www.runoob.com/jsref/jsref-map.html)                       | 通过指定函数处理数组的每个元素，并返回处理后的数组。                       |
| [pop()](https://www.runoob.com/jsref/jsref-pop.html)                       | 删除数组的最后一个元素并返回删除的元素。                             |
| [push()](https://www.runoob.com/jsref/jsref-push.html)                     | 向数组的末尾添加一个或更多元素，并返回新的长度。                         |
| [reduce()](https://www.runoob.com/jsref/jsref-reduce.html)                 | 将数组元素计算为一个值（从左到右）。                               |
| [reduceRight()](https://www.runoob.com/jsref/jsref-reduceright.html)       | 将数组元素计算为一个值（从右到左）。                               |
| [reverse()](https://www.runoob.com/jsref/jsref-reverse.html)               | 反转数组的元素顺序。                                       |
| [shift()](https://www.runoob.com/jsref/jsref-shift.html)                   | 删除并返回数组的第一个元素。                                   |
| [slice()](https://www.runoob.com/jsref/jsref-slice-array.html)             | 选取数组的一部分，并返回一个新数组。                               |
| [some()](https://www.runoob.com/jsref/jsref-some.html)                     | 检测数组元素中是否有元素符合指定条件。                              |
| [sort()](https://www.runoob.com/jsref/jsref-sort.html)                     | 对数组的元素进行排序。                                      |
| [splice()](https://www.runoob.com/jsref/jsref-splice.html)                 | 从数组中添加或删除元素。                                     |
| [toString()](https://www.runoob.com/jsref/jsref-tostring-array.html)       | 把数组转换为字符串，并返回结果。                                 |
| [unshift()](https://www.runoob.com/jsref/jsref-unshift.html)               | 向数组的开头添加一个或更多元素，并返回新的长度。                         |
| [valueOf()](https://www.runoob.com/jsref/jsref-valueof-array.html)         | 返回数组对象的原始值。                                      |
| [Array.of()](https://www.runoob.com/jsref/jsref-of-array.html)             | 将一组值转换为数组。                                       |
| [Array.at()](https://www.runoob.com/jsref/jsref-at-array.html)             | 用于接收一个整数值并返回该索引对应的元素，允许正数和负数。负整数从数组中的最后一个元素开始倒数。 |
| [Array.flat()](https://www.runoob.com/jsref/jsref-flat-array.html)         | 创建一个新数组，这个新数组由原数组中的每个元素都调用一次提供的函数后的返回值组成。        |
| [Array.flatMap()](https://www.runoob.com/jsref/jsref-flatmap-array.html)   | 使用映射函数映射每个元素，然后将结果压缩成一个新数组。                      |

#### 4.3.2 Boolean对象

> boolean对象的方法比较简单

| 方法                                                                     | 描述                 |
| :--------------------------------------------------------------------- | :----------------- |
| [toString()](https://www.runoob.com/jsref/jsref-tostring-boolean.html) | 把布尔值转换为字符串，并返回结果。  |
| [valueOf()](https://www.runoob.com/jsref/jsref-valueof-boolean.html)   | 返回 Boolean 对象的原始值。 |

#### 4.3.3 Date对象

> 和JAVA中的Date类比较类似

| 方法                                                                                 | 描述                                                                                      |
| :--------------------------------------------------------------------------------- | :-------------------------------------------------------------------------------------- |
| [getDate()](https://www.runoob.com/jsref/jsref-getdate.html)                       | 从 Date 对象返回一个月中的某一天 (1 ~ 31)。                                                           |
| [getDay()](https://www.runoob.com/jsref/jsref-getday.html)                         | 从 Date 对象返回一周中的某一天 (0 ~ 6)。                                                             |
| [getFullYear()](https://www.runoob.com/jsref/jsref-getfullyear.html)               | 从 Date 对象以四位数字返回年份。                                                                     |
| [getHours()](https://www.runoob.com/jsref/jsref-gethours.html)                     | 返回 Date 对象的小时 (0 ~ 23)。                                                                 |
| [getMilliseconds()](https://www.runoob.com/jsref/jsref-getmilliseconds.html)       | 返回 Date 对象的毫秒(0 ~ 999)。                                                                 |
| [getMinutes()](https://www.runoob.com/jsref/jsref-getminutes.html)                 | 返回 Date 对象的分钟 (0 ~ 59)。                                                                 |
| [getMonth()](https://www.runoob.com/jsref/jsref-getmonth.html)                     | 从 Date 对象返回月份 (0 ~ 11)。                                                                 |
| [getSeconds()](https://www.runoob.com/jsref/jsref-getseconds.html)                 | 返回 Date 对象的秒数 (0 ~ 59)。                                                                 |
| [getTime()](https://www.runoob.com/jsref/jsref-gettime.html)                       | 返回 1970 年 1 月 1 日至今的毫秒数。                                                                |
| [getTimezoneOffset()](https://www.runoob.com/jsref/jsref-gettimezoneoffset.html)   | 返回本地时间与格林威治标准时间 (GMT) 的分钟差。                                                             |
| [getUTCDate()](https://www.runoob.com/jsref/jsref-getutcdate.html)                 | 根据世界时从 Date 对象返回月中的一天 (1 ~ 31)。                                                         |
| [getUTCDay()](https://www.runoob.com/jsref/jsref-getutcday.html)                   | 根据世界时从 Date 对象返回周中的一天 (0 ~ 6)。                                                          |
| [getUTCFullYear()](https://www.runoob.com/jsref/jsref-getutcfullyear.html)         | 根据世界时从 Date 对象返回四位数的年份。                                                                 |
| [getUTCHours()](https://www.runoob.com/jsref/jsref-getutchours.html)               | 根据世界时返回 Date 对象的小时 (0 ~ 23)。                                                            |
| [getUTCMilliseconds()](https://www.runoob.com/jsref/jsref-getutcmilliseconds.html) | 根据世界时返回 Date 对象的毫秒(0 ~ 999)。                                                            |
| [getUTCMinutes()](https://www.runoob.com/jsref/jsref-getutcminutes.html)           | 根据世界时返回 Date 对象的分钟 (0 ~ 59)。                                                            |
| [getUTCMonth()](https://www.runoob.com/jsref/jsref-getutcmonth.html)               | 根据世界时从 Date 对象返回月份 (0 ~ 11)。                                                            |
| [getUTCSeconds()](https://www.runoob.com/jsref/jsref-getutcseconds.html)           | 根据世界时返回 Date 对象的秒钟 (0 ~ 59)。                                                            |
| getYear()                                                                          | 已废弃。 请使用 getFullYear() 方法代替。                                                            |
| [parse()](https://www.runoob.com/jsref/jsref-parse.html)                           | 返回1970年1月1日午夜到指定日期（字符串）的毫秒数。                                                            |
| [setDate()](https://www.runoob.com/jsref/jsref-setdate.html)                       | 设置 Date 对象中月的某一天 (1 ~ 31)。                                                              |
| [setFullYear()](https://www.runoob.com/jsref/jsref-setfullyear.html)               | 设置 Date 对象中的年份（四位数字）。                                                                   |
| [setHours()](https://www.runoob.com/jsref/jsref-sethours.html)                     | 设置 Date 对象中的小时 (0 ~ 23)。                                                                |
| [setMilliseconds()](https://www.runoob.com/jsref/jsref-setmilliseconds.html)       | 设置 Date 对象中的毫秒 (0 ~ 999)。                                                               |
| [setMinutes()](https://www.runoob.com/jsref/jsref-setminutes.html)                 | 设置 Date 对象中的分钟 (0 ~ 59)。                                                                |
| [setMonth()](https://www.runoob.com/jsref/jsref-setmonth.html)                     | 设置 Date 对象中月份 (0 ~ 11)。                                                                 |
| [setSeconds()](https://www.runoob.com/jsref/jsref-setseconds.html)                 | 设置 Date 对象中的秒钟 (0 ~ 59)。                                                                |
| [setTime()](https://www.runoob.com/jsref/jsref-settime.html)                       | setTime() 方法以毫秒设置 Date 对象。                                                              |
| [setUTCDate()](https://www.runoob.com/jsref/jsref-setutcdate.html)                 | 根据世界时设置 Date 对象中月份的一天 (1 ~ 31)。                                                         |
| [setUTCFullYear()](https://www.runoob.com/jsref/jsref-setutcfullyear.html)         | 根据世界时设置 Date 对象中的年份（四位数字）。                                                              |
| [setUTCHours()](https://www.runoob.com/jsref/jsref-setutchours.html)               | 根据世界时设置 Date 对象中的小时 (0 ~ 23)。                                                           |
| [setUTCMilliseconds()](https://www.runoob.com/jsref/jsref-setutcmilliseconds.html) | 根据世界时设置 Date 对象中的毫秒 (0 ~ 999)。                                                          |
| [setUTCMinutes()](https://www.runoob.com/jsref/jsref-setutcminutes.html)           | 根据世界时设置 Date 对象中的分钟 (0 ~ 59)。                                                           |
| [setUTCMonth()](https://www.runoob.com/jsref/jsref-setutcmonth.html)               | 根据世界时设置 Date 对象中的月份 (0 ~ 11)。                                                           |
| [setUTCSeconds()](https://www.runoob.com/jsref/jsref-setutcseconds.html)           | setUTCSeconds() 方法用于根据世界时 (UTC) 设置指定时间的秒字段。                                             |
| setYear()                                                                          | 已废弃。请使用 setFullYear() 方法代替。                                                             |
| [toDateString()](https://www.runoob.com/jsref/jsref-todatestring.html)             | 把 Date 对象的日期部分转换为字符串。                                                                   |
| toGMTString()                                                                      | 已废弃。请使用 toUTCString() 方法代替。                                                             |
| [toISOString()](https://www.runoob.com/jsref/jsref-toisostring.html)               | 使用 ISO 标准返回字符串的日期格式。                                                                    |
| [toJSON()](https://www.runoob.com/jsref/jsref-tojson.html)                         | 以 JSON 数据格式返回日期字符串。                                                                     |
| [toLocaleDateString()](https://www.runoob.com/jsref/jsref-tolocaledatestring.html) | 根据本地时间格式，把 Date 对象的日期部分转换为字符串。                                                          |
| [toLocaleTimeString()](https://www.runoob.com/jsref/jsref-tolocaletimestring.html) | 根据本地时间格式，把 Date 对象的时间部分转换为字符串。                                                          |
| [toLocaleString()](https://www.runoob.com/jsref/jsref-tolocalestring.html)         | 根据本地时间格式，把 Date 对象转换为字符串。                                                               |
| [toString()](https://www.runoob.com/jsref/jsref-tostring-date.html)                | 把 Date 对象转换为字符串。                                                                        |
| [toTimeString()](https://www.runoob.com/jsref/jsref-totimestring.html)             | 把 Date 对象的时间部分转换为字符串。                                                                   |
| [toUTCString()](https://www.runoob.com/jsref/jsref-toutcstring.html)               | 根据世界时，把 Date 对象转换为字符串。实例：`var today = new Date(); var UTCstring = today.toUTCString();` |
| [UTC()](https://www.runoob.com/jsref/jsref-utc.html)                               | 根据世界时返回 1970 年 1 月 1 日 到指定日期的毫秒数。                                                       |
| [valueOf()](https://www.runoob.com/jsref/jsref-valueof-date.html)                  | 返回 Date 对象的原始值。                                                                         |

#### 4.3.4 Math

>  和JAVA中的Math类比较类似

| 方法                                                              | 描述                                         |
| :-------------------------------------------------------------- | :----------------------------------------- |
| [abs(x)](https://www.runoob.com/jsref/jsref-abs.html)           | 返回 x 的绝对值。                                 |
| [acos(x)](https://www.runoob.com/jsref/jsref-acos.html)         | 返回 x 的反余弦值。                                |
| [asin(x)](https://www.runoob.com/jsref/jsref-asin.html)         | 返回 x 的反正弦值。                                |
| [atan(x)](https://www.runoob.com/jsref/jsref-atan.html)         | 以介于 -PI/2 与 PI/2 弧度之间的数值来返回 x 的反正切值。       |
| [atan2(y,x)](https://www.runoob.com/jsref/jsref-atan2.html)     | 返回从 x 轴到点 (x,y) 的角度（介于 -PI/2 与 PI/2 弧度之间）。 |
| [ceil(x)](https://www.runoob.com/jsref/jsref-ceil.html)         | 对数进行上舍入。                                   |
| [cos(x)](https://www.runoob.com/jsref/jsref-cos.html)           | 返回数的余弦。                                    |
| [exp(x)](https://www.runoob.com/jsref/jsref-exp.html)           | 返回 Ex 的指数。                                 |
| [floor(x)](https://www.runoob.com/jsref/jsref-floor.html)       | 对 x 进行下舍入。                                 |
| [log(x)](https://www.runoob.com/jsref/jsref-log.html)           | 返回数的自然对数（底为e）。                             |
| [max(x,y,z,...,n)](https://www.runoob.com/jsref/jsref-max.html) | 返回 x,y,z,...,n 中的最高值。                      |
| [min(x,y,z,...,n)](https://www.runoob.com/jsref/jsref-min.html) | 返回 x,y,z,...,n中的最低值。                       |
| [pow(x,y)](https://www.runoob.com/jsref/jsref-pow.html)         | 返回 x 的 y 次幂。                               |
| [random()](https://www.runoob.com/jsref/jsref-random.html)      | 返回 0 ~ 1 之间的随机数。                           |
| [round(x)](https://www.runoob.com/jsref/jsref-round.html)       | 四舍五入。                                      |
| [sin(x)](https://www.runoob.com/jsref/jsref-sin.html)           | 返回数的正弦。                                    |
| [sqrt(x)](https://www.runoob.com/jsref/jsref-sqrt.html)         | 返回数的平方根。                                   |
| [tan(x)](https://www.runoob.com/jsref/jsref-tan.html)           | 返回角的正切。                                    |
| [tanh(x)](https://www.runoob.com/jsref/jsref-tanh.html)         | 返回一个数的双曲正切函数值。                             |
| [trunc(x)](https://www.runoob.com/jsref/jsref-trunc.html)       | 将数字的小数部分去掉，只保留整数部分。                        |

#### 4.3.5 Number

> Number中准备了一些基础的数据处理函数

| 方法                                                                                                | 描述                         |
| :------------------------------------------------------------------------------------------------ | :------------------------- |
| [isFinite](https://www.runoob.com/jsref/jsref-isfinite-number.html)                               | 检测指定参数是否为无穷大。              |
| [isInteger](https://www.runoob.com/jsref/jsref-isinteger-number.html)                             | 检测指定参数是否为整数。               |
| [isNaN](https://www.runoob.com/jsref/jsref-isnan-number.html)                                     | 检测指定参数是否为 NaN。             |
| [isSafeInteger](https://www.runoob.com/jsref/jsref-issafeInteger-number.html)                     | 检测指定参数是否为安全整数。             |
| [toExponential(x)](https://www.runoob.com/jsref/jsref-toexponential.html)                         | 把对象的值转换为指数计数法。             |
| [toFixed(x)](https://www.runoob.com/jsref/jsref-tofixed.html)                                     | 把数字转换为字符串，结果的小数点后有指定位数的数字。 |
| [toLocaleString(locales, options)](https://www.runoob.com/jsref/jsref-tolocalestring-number.html) | 返回数字在特定语言环境下的表示字符串。        |
| [toPrecision(x)](https://www.runoob.com/jsref/jsref-toprecision.html)                             | 把数字格式化为指定的长度。              |
| [toString()](https://www.runoob.com/jsref/jsref-tostring-number.html)                             | 把数字转换为字符串，使用指定的基数。         |
| [valueOf()](https://www.runoob.com/jsref/jsref-valueof-number.html)                               | 返回一个 Number 对象的基本数字值。      |

#### 4.3.6 String

> 和JAVA中的String类似

| 方法                                                                               | 描述                                   |
| :------------------------------------------------------------------------------- | :----------------------------------- |
| [charAt()](https://www.runoob.com/jsref/jsref-charat.html)                       | 返回在指定位置的字符。                          |
| [charCodeAt()](https://www.runoob.com/jsref/jsref-charcodeat.html)               | 返回在指定的位置的字符的 Unicode 编码。             |
| [concat()](https://www.runoob.com/jsref/jsref-concat-string.html)                | 连接两个或更多字符串，并返回新的字符串。                 |
| [endsWith()](https://www.runoob.com/jsref/jsref-endswith.html)                   | 判断当前字符串是否是以指定的子字符串结尾的（区分大小写）。        |
| [fromCharCode()](https://www.runoob.com/jsref/jsref-fromcharcode.html)           | 将 Unicode 编码转为字符。                    |
| [indexOf()](https://www.runoob.com/jsref/jsref-indexof.html)                     | 返回某个指定的字符串值在字符串中首次出现的位置。             |
| [includes()](https://www.runoob.com/jsref/jsref-string-includes.html)            | 查找字符串中是否包含指定的子字符串。                   |
| [lastIndexOf()](https://www.runoob.com/jsref/jsref-lastindexof.html)             | 从后向前搜索字符串，并从起始位置（0）开始计算返回字符串最后出现的位置。 |
| [match()](https://www.runoob.com/jsref/jsref-match.html)                         | 查找找到一个或多个正则表达式的匹配。                   |
| [repeat()](https://www.runoob.com/jsref/jsref-repeat.html)                       | 复制字符串指定次数，并将它们连接在一起返回。               |
| [replace()](https://www.runoob.com/jsref/jsref-replace.html)                     | 在字符串中查找匹配的子串，并替换与正则表达式匹配的子串。         |
| [replaceAll()](https://www.runoob.com/jsref/jsref-replaceall.html)               | 在字符串中查找匹配的子串，并替换与正则表达式匹配的所有子串。       |
| [search()](https://www.runoob.com/jsref/jsref-search.html)                       | 查找与正则表达式相匹配的值。                       |
| [slice()](https://www.runoob.com/jsref/jsref-slice-string.html)                  | 提取字符串的片断，并在新的字符串中返回被提取的部分。           |
| [split()](https://www.runoob.com/jsref/jsref-split.html)                         | 把字符串分割为字符串数组。                        |
| [startsWith()](https://www.runoob.com/jsref/jsref-startswith.html)               | 查看字符串是否以指定的子字符串开头。                   |
| [substr()](https://www.runoob.com/jsref/jsref-substr.html)                       | 从起始索引号提取字符串中指定数目的字符。                 |
| [substring()](https://www.runoob.com/jsref/jsref-substring.html)                 | 提取字符串中两个指定的索引号之间的字符。                 |
| [toLowerCase()](https://www.runoob.com/jsref/jsref-tolowercase.html)             | 把字符串转换为小写。                           |
| [toUpperCase()](https://www.runoob.com/jsref/jsref-touppercase.html)             | 把字符串转换为大写。                           |
| [trim()](https://www.runoob.com/jsref/jsref-trim.html)                           | 去除字符串两边的空白。                          |
| [toLocaleLowerCase()](https://www.runoob.com/jsref/jsref-tolocalelowercase.html) | 根据本地主机的语言环境把字符串转换为小写。                |
| [toLocaleUpperCase()](https://www.runoob.com/jsref/jsref-tolocaleuppercase.html) | 根据本地主机的语言环境把字符串转换为大写。                |
| [valueOf()](https://www.runoob.com/jsref/jsref-valueof-string.html)              | 返回某个字符串对象的原始值。                       |
| [toString()](https://www.runoob.com/jsref/jsref-tostring.html)<br>               | 返回一个字符串。                             |

## 5. 事件的绑定
### 5.1 绑定事件的作用
> 用于捕捉用户的操作, 并对之做出响应, 可通过定义函数, 并绑定操作实现
### 5.2 常见事件

> 鼠标事件

| 属性                                                                     | 描述                  |
| :--------------------------------------------------------------------- | :------------------ |
| [onclick](https://www.runoob.com/jsref/event-onclick.html)             | 当用户点击某个对象时调用的事件句柄。  |
| [oncontextmenu](https://www.runoob.com/jsref/event-oncontextmenu.html) | 在用户点击鼠标右键打开上下文菜单时触发 |
| [ondblclick](https://www.runoob.com/jsref/event-ondblclick.html)       | 当用户双击某个对象时调用的事件句柄。  |
| [onmousedown](https://www.runoob.com/jsref/event-onmousedown.html)     | 鼠标按钮被按下。            |
| [onmouseenter](https://www.runoob.com/jsref/event-onmouseenter.html)   | 当鼠标指针移动到元素上时触发。     |
| [onmouseleave](https://www.runoob.com/jsref/event-onmouseleave.html)   | 当鼠标指针移出元素时触发        |
| [onmousemove](https://www.runoob.com/jsref/event-onmousemove.html)     | 鼠标被移动。              |
| [onmouseover](https://www.runoob.com/jsref/event-onmouseover.html)     | 鼠标移到某元素之上。          |
| [onmouseout](https://www.runoob.com/jsref/event-onmouseout.html)       | 鼠标从某元素移开。           |
| [onmouseup](https://www.runoob.com/jsref/event-onmouseup.html)         | 鼠标按键被松开。            |

> 键盘事件

| 属性                                                               | 描述            |
| :--------------------------------------------------------------- | :------------ |
| [onkeydown](https://www.runoob.com/jsref/event-onkeydown.html)   | 某个键盘按键被按下。    |
| [onkeypress](https://www.runoob.com/jsref/event-onkeypress.html) | 某个键盘按键被按下并松开。 |
| [onkeyup](https://www.runoob.com/jsref/event-onkeyup.html)       | 某个键盘按键被松开。    |
|                                                                  |               |

> 表单事件

| 属性                                                               | 描述                                                               |
| :--------------------------------------------------------------- | :--------------------------------------------------------------- |
| [onblur](https://www.runoob.com/jsref/event-onblur.html)         | 元素失去焦点时触发                                                        |
| [onchange](https://www.runoob.com/jsref/event-onchange.html)     | 该事件在表单元素的内容改变时触发( \<input>, \<keygen>, \<select>, 和 \<textarea>) |
| [onfocus](https://www.runoob.com/jsref/event-onfocus.html)       | 元素获取焦点时触发                                                        |
| [onfocusin](https://www.runoob.com/jsref/event-onfocusin.html)   | 元素即将获取焦点时触发                                                      |
| [onfocusout](https://www.runoob.com/jsref/event-onfocusout.html) | 元素即将失去焦点时触发                                                      |
| [oninput](https://www.runoob.com/jsref/event-oninput.html)       | 元素获取用户输入时触发                                                      |
| [onreset](https://www.runoob.com/jsref/event-onreset.html)       | 表单重置时触发                                                          |
| [onsearch](https://www.runoob.com/jsref/event-onsearch.html)     | 用户向搜索域输入文本时触发 ( <input="search">)                                |
| [onselect](https://www.runoob.com/jsref/event-onselect.html)     | 用户选取文本时触发 ( \<input> 和 \<textarea>)                              |
| [onsubmit](https://www.runoob.com/jsref/event-onsubmit.html)     | 表单提交时触发                                                          |
### 5.3 事件的绑定
> 常规的绑定与触发
```js
function testDown1() {
console.log("键盘按键被按下了");
}

function testDown2() {
console.log("键盘按键被按下了2");
}

function downAndPress() {
console.log("某个按键被按下了并松开");
}

function up() {
console.log("按键被松开");
}

function loseFocus() {
console.log("即将失去焦点");
}

function lostFocus() {
console.log("失去焦点");
}

function change(input) {
console.log("表单内容被改变");
console.log(input.value);
}
```

```html
<input type="text" 
           onblur="lostFocus()"
           onfocusout="loseFocus()"
           onkeydown="testDown1(), testDown2()"
           onkeypress="downAndPress()"
           onkeyup="up()"
           onchange="change(this)"
           >
```

> 特点

 -  一个事件能绑定多个函数
 - 一个函数能被多个事件同时绑定
 - 可以传递this对象, 代表当前元素
 - 即将失去焦点和失去焦点的两个事件的区别是前者会有事件冒泡 : 事件从最内层的目标元素向上传播到最外层的父元素
>通过DOM编程绑定

- 通过指定ID找到元素, 再对应事件绑定触发事件
```js
window.onload=function(){
var in1 = document.getElementById("in1");

// DOM编程绑定事件
in1.onchange=change;
}

function change() {
console.log(event.target.value);
console.log("表单内容被改变");
}

```

```html
<input type="text" id="in1"/>
```
### 5.4 事件的触发

> 行为触发 : 就是最开始演示的如何绑定事件的方式, 发生行为时触发

> DOM编程触发
-  通过代码触发, 执行某些行为的时候相当于触发了某些事件
```js
// 页面加载完毕事件, 加载完毕后加载事件
window.onload=function(){
var in1 = document.getElementById("in1");

// DOM编程绑定事件
in1.onchange=change;

var btn1 = document.getElementById("btn1");

btn1.onclick=function() {
console.log("pressed");
in1.onchange();
}
}

function change() {
console.log(event.target.value);
console.log("表单内容被改变");
}
```

## 6. BOM编程
### 6.1 什么是BOM
- BOM是Browser Object Model的简写, 是浏览器对象模型
- 将浏览器抽象为一个对象, 从而可以得到这个对象的行为和属性
- BOM是一系列对象的集合, 是访问, 控制, 修改浏览器的属性和方法
- 简单来说, BOM提供了可以控制和查看浏览器的一系列API
- BOM编程的对象结构
    - window 顶层对象, 代表整个浏览器窗口
        - location对象 window对象的属性之一,代表浏览器的地址栏
    
        - history对象 window对象的属性之一,代表浏览器的访问历史
            
        - screen对象 window对象的属性之一,代表屏幕
            
        - navigator对象 window对象的属性之一,代表浏览器软件本身
            
        - document对象 window对象的属性之一,代表浏览器窗口目前解析的html文档
            
        - console对象 window对象的属性之一,代表浏览器开发者工具的控制台
            
        - localStorage对象 window对象的属性之一,代表浏览器的本地数据持久化存储
            
        - sessionStorage对象 window对象的属性之一,代表浏览器的本地数据会话级存储
### 6.2 window对象的常见属性
|属性|描述|
|---|---|
|[closed](https://www.runoob.com/jsref/prop-win-closed.html)|返回窗口是否已被关闭。|
|[defaultStatus](https://www.runoob.com/jsref/prop-win-defaultstatus.html)|设置或返回窗口状态栏中的默认文本。|
|[document](https://www.runoob.com/jsref/dom-obj-document.html)|对 Document 对象的只读引用。(请参阅[对象](https://www.runoob.com/jsref/dom-obj-document.html))|
|[frames](https://www.runoob.com/jsref/prop-win-frames.html)|返回窗口中所有命名的框架。该集合是 Window 对象的数组，每个 Window 对象在窗口中含有一个框架。|
|[history](https://www.runoob.com/jsref/obj-history.html)|对 History 对象的只读引用。请参数 [History 对象](https://www.runoob.com/jsref/obj-history.html)。|
|[innerHeight](https://www.runoob.com/jsref/prop-win-innerheight.html)|返回窗口的文档显示区的高度。|
|[innerWidth](https://www.runoob.com/jsref/prop-win-innerheight.html)|返回窗口的文档显示区的宽度。|
|[localStorage](https://www.runoob.com/jsref/prop-win-localstorage.html)|在浏览器中存储 key/value 对。没有过期时间。|
|[length](https://www.runoob.com/jsref/prop-win-length.html)|设置或返回窗口中的框架数量。|
|[location](https://www.runoob.com/jsref/obj-location.html)|用于窗口或框架的 Location 对象。请参阅 [Location 对象](https://www.runoob.com/jsref/obj-location.html)。|
|[name](https://www.runoob.com/jsref/prop-win-name.html)|设置或返回窗口的名称。|
|[navigator](https://www.runoob.com/jsref/obj-navigator.html)|对 Navigator 对象的只读引用。请参数 [Navigator 对象](https://www.runoob.com/jsref/obj-navigator.html)。|
|[opener](https://www.runoob.com/jsref/prop-win-opener.html)|返回对创建此窗口的窗口的引用。|
|[outerHeight](https://www.runoob.com/jsref/prop-win-outerheight.html)|返回窗口的外部高度，包含工具条与滚动条。|
|[outerWidth](https://www.runoob.com/jsref/prop-win-outerheight.html)|返回窗口的外部宽度，包含工具条与滚动条。|
|[pageXOffset](https://www.runoob.com/jsref/prop-win-pagexoffset.html)|设置或返回当前页面相对于窗口显示区左上角的 X 位置。|
|[pageYOffset](https://www.runoob.com/jsref/prop-win-pagexoffset.html)|设置或返回当前页面相对于窗口显示区左上角的 Y 位置。|
|[parent](https://www.runoob.com/jsref/prop-win-parent.html)|返回父窗口。|
|[screen](https://www.runoob.com/jsref/obj-screen.html)|对 Screen 对象的只读引用。请参数 [Screen 对象](https://www.runoob.com/jsref/obj-screen.html)。|
|[screenLeft](https://www.runoob.com/jsref/prop-win-screenleft.html)|返回相对于屏幕窗口的x坐标|
|[screenTop](https://www.runoob.com/jsref/prop-win-screenleft.html)|返回相对于屏幕窗口的y坐标|
|[screenX](https://www.runoob.com/jsref/prop-win-screenx.html)|返回相对于屏幕窗口的x坐标|
|[sessionStorage](https://www.runoob.com/jsref/prop-win-sessionstorage.html)|在浏览器中存储 key/value 对。 在关闭窗口或标签页之后将会删除这些数据。|
|[screenY](https://www.runoob.com/jsref/prop-win-screenx.html)|返回相对于屏幕窗口的y坐标|
|[self](https://www.runoob.com/jsref/prop-win-self.html)|返回对当前窗口的引用。等价于 Window 属性。|
|[status](https://www.runoob.com/jsref/prop-win-status.html)|设置窗口状态栏的文本。|
|[top](https://www.runoob.com/jsref/prop-win-top.html)|返回最顶层的父窗口。|

### 6.3 window对象的常见方法(了解)

| 方法                                                                             | 描述                                                                                    |
| ------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------- |
| [alert()](https://www.runoob.com/jsref/met-win-alert.html)                     | 显示带有一段消息和一个确认按钮的警告框。                                                                  |
| [atob()](https://www.runoob.com/jsref/met-win-atob.html)                       | 解码一个 base-64 编码的字符串。                                                                  |
| [btoa()](https://www.runoob.com/jsref/met-win-btoa.html)                       | 创建一个 base-64 编码的字符串。                                                                  |
| [blur()](https://www.runoob.com/jsref/met-win-blur.html)                       | 把键盘焦点从顶层窗口移开。                                                                         |
| [clearInterval()](https://www.runoob.com/jsref/met-win-clearinterval.html)     | 取消由 setInterval() 设置的 timeout。                                                        |
| [clearTimeout()](https://www.runoob.com/jsref/met-win-cleartimeout.html)       | 取消由 setTimeout() 方法设置的 timeout。                                                       |
| [close()](https://www.runoob.com/jsref/met-win-close.html)                     | 关闭浏览器窗口。                                                                              |
| [confirm()](https://www.runoob.com/jsref/met-win-confirm.html)                 | 显示带有一段消息以及确认按钮和取消按钮的对话框。                                                              |
| [createPopup()](https://www.runoob.com/jsref/met-win-createpopup.html)         | 创建一个 pop-up 窗口。                                                                       |
| [focus()](https://www.runoob.com/jsref/met-win-focus.html)                     | 把键盘焦点给予一个窗口。                                                                          |
| [getSelection](https://www.runoob.com/jsref/met-win-getselection.html)()       | 返回一个 Selection 对象，表示用户选择的文本范围或光标的当前位置。                                                |
| [getComputedStyle()](https://www.runoob.com/jsref/jsref-getcomputedstyle.html) | 获取指定元素的 CSS 样式。                                                                       |
| [matchMedia()](https://www.runoob.com/jsref/met-win-matchmedia.html)           | 该方法用来检查 media query 语句，它返回一个 MediaQueryList对象。                                        |
| [moveBy()](https://www.runoob.com/jsref/met-win-moveby.html)                   | 可相对窗口的当前坐标把它移动指定的像素。                                                                  |
| [moveTo()](https://www.runoob.com/jsref/met-win-moveto.html)                   | 把窗口的左上角移动到一个指定的坐标。                                                                    |
| [open()](https://www.runoob.com/jsref/met-win-open.html)                       | 打开一个新的浏览器窗口或查找一个已命名的窗口。                                                               |
| [print()](https://www.runoob.com/jsref/met-win-print.html)                     | 打印当前窗口的内容。                                                                            |
| [prompt()](https://www.runoob.com/jsref/met-win-prompt.html)                   | 显示可提示用户输入的对话框。                                                                        |
| [resizeBy()](https://www.runoob.com/jsref/met-win-resizeby.html)               | 按照指定的像素调整窗口的大小。                                                                       |
| [resizeTo()](https://www.runoob.com/jsref/met-win-resizeto.html)               | 把窗口的大小调整到指定的宽度和高度。                                                                    |
| scroll()                                                                       | 已废弃。 该方法已经使用了 [scrollTo()](https://www.runoob.com/jsref/met-win-scrollto.html) 方法来替代。 |
| [scrollBy()](https://www.runoob.com/jsref/met-win-scrollby.html)               | 按照指定的像素值来滚动内容。                                                                        |
| [scrollTo()](https://www.runoob.com/jsref/met-win-scrollto.html)               | 把内容滚动到指定的坐标。                                                                          |
| [setInterval()](https://www.runoob.com/jsref/met-win-setinterval.html)         | 按照指定的周期（以毫秒计）来调用函数或计算表达式。                                                             |
| [setTimeout()](https://www.runoob.com/jsref/met-win-settimeout.html)           | 在指定的毫秒数后调用函数或计算表达式。                                                                   |
| [stop()](https://www.runoob.com/jsref/met-win-stop.html)                       | 停止页面载入。                                                                               |
| [postMessage()](https://www.runoob.com/jsref/met-win-postmessage.html)         | 安全地实现跨源通信。                                                                            |
### 6.4 通过BOM编程控制浏览器行为演示
> 三种弹窗方式
```js
function testAlert() {
window.alert("提示信息")
}

function testConfirm() {
// 确认框
var con = confirm("确认要删除吗");
if (con) {
alert("删除成功");
} else {
alert("取消成功");
}
}

function testPrompt() {
// 信息输入对话框
var res = prompt("请输入信息");
alert("你输入的是:" + res);
}
```
> 页面跳转
```html
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<script>
function goAtguigu(){
var flag = confirm("即将跳转到尚硅谷官网, 你会丢失当前页面的信息, 确定吗");
if (flag) {
// 通过BOM编程地址栏URL切换
window.location.href="http://www.atguigu.com";
}
}
</script>
<title>Document</title>
</head>
<body>
<input type="button" id="btn1" value="跳转到尚硅谷官网" onclick="goAtguigu()"/>
</body>
```

### 6.5 通过BOM编程实现会话级和持久级数据存储
- 会话级数据 : 内存型数据, 是浏览器在内存上临时存储的数据, 浏览器关闭后, 就会失去
- 持久级数据 : 磁盘型数据, 会一直保存, 浏览器关闭后, 仍然存在
```html
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<script>
function saveItem(){
window.sessionStorage.setItem("sessionMsg", "sessionValue");
window.localStorage.setItem("localMsg", "localItem");

console.log("saved");
}

function removeItem(){
window.sessionStorage.removeItem("sessionMsg");
window.localStorage.removeItem("localMsg");

console.log("Deleted");
}

function readItem(){
console.log("read");
console.log("session : " + sessionStorage.getItem("sessionMsg"));
console.log("local : " + localStorage.getItem("localMsg"));
}

</script>
<title>Document</title>
</head>
<body>
<button onclick="saveItem()">存储</button> <br>
<button onclick="removeItem()">删除</button> <br>
<button onclick="readItem()">读取</button> <br>
</body>
</html>
```

## 7. DOM编程
### 7.1 DOM编程简介
-  DOM指的就是BOM中的document对象, 通过这个对象, 我们能获取到传进来的html文本中的各个结点
- JS可以根据HTML的结构树, 生成document的节点树, 从而实现对页面中的各个节点的精准操控
### 7.2 获取页面元素的几种方式
#### 7.2.1 在整个文档范围内查找元素结点

| 功能               | API                                     | 返回值           |
| ------------------ | --------------------------------------- | ---------------- |
| 根据id值查询       | document.getElementById(“id值”)         | 一个具体的元素节 |
| 根据标签名查询     | document.getElementsByTagName(“标签名”) | 元素节点数组     |
| 根据name属性值查询 | document.getElementsByName(“name值”)    | 元素节点数组     |
| 根据类名查询       | document.getElementsByClassName("类名") | 元素节点数组     |

#### 7.2.2 在具体元素节点范围内查找子节点

| 功能               | API                       | 返回值         |
| ------------------ | ------------------------- | -------------- |
| 查找子标签         | element.children          | 返回子标签数组 |
| 查找第一个子标签   | element.firstElementChild | 标签对象       |
| 查找最后一个子标签 | element.lastElementChild  | 节点对象       |

#### 7.2.3 查找指定子元素节点的父节点

| 功能                     | API                   | 返回值   |
| ------------------------ | --------------------- | -------- |
| 查找指定元素节点的父标签 | element.parentElement | 标签对象 |

#### 7.2.4  查找指定元素节点的兄弟节点

| 功能               | API                         | 返回值   |
| ------------------ | --------------------------- | -------- |
| 查找前一个兄弟标签 | node.previousElementSibling | 标签对象 |
| 查找后一个兄弟标签 | node.nextElementSibling     | 标签对象 |
```html
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script>

// 获取元素-直接获取
function fun1() {
var ele1 = document.getElementById("username");
console.log(ele1);
}

function fun2(){
var ele1 = document.getElementsByTagName("input")
for (var i in ele1) {
console.log(ele1[i])
}
}

function fun3(){
var ele1 = document.getElementsByName("aaa")
for (var i in ele1) {
console.log(ele1[i])
}
}

function fun4(){
var ele1 = document.getElementsByClassName("b")
for (var i in ele1) {
console.log(ele1[i])
}
}

function fun5(){
// 获取父元素的所有子元素
var div01 = document.getElementById("div01")
var child = div01.children
for (var i in child) {
console.log(child[i])
}

// 获取第一个子元素
console.log(div01.firstElementChild)
// 获取最后一个子元素
console.log(div01.lastElementChild)
}

function fun6(){
// 通过子元素获取父元素
var child = document.getElementById("username")
var f = child.parentElement
console.log(f)
}

function fun7(){
// 获取子元素的兄弟元素
var child = document.getElementById("username")
console.log(child.previousElementSibling)
console.log(child.nextElementSibling)
}

</script>
<title>Document</title>
</head>
<body>
<div id="div01">
<input type="text" class="a" id="username" name="aaa"/>
<input type="text" class="b" id="password" name="aaa"/>
<input type="text" class="a" id="email"/>
<input type="text" class="b" id="address"/>
</div>
<input type="text" class="a"/><br>

<hr>
<input type="button" value="通过父元素获取子元素" onclick="fun5()" id="btn05"/>
<input type="button" value="通过子元素获取父元素" onclick="fun6()" id="btn06"/>
<input type="button" value="通过当前元素获取兄弟元素" onclick="fun7()" id="btn07"/>
<hr>

<input type="button" value="根据id获取指定元素" onclick="fun1()" id="btn01"/>
<input type="button" value="根据标签名获取多个元素" onclick="fun2()" id="btn02"/>
<input type="button" value="根据name属性值获取多个元素" onclick="fun3()" id="btn03"/>
<input type="button" value="根据class属性值获得多个元素" onclick="fun4()" id="btn04"/>
</body>
</html>
```

### 7.3 操作元素属性值
#### 7.3.1 属性操作
- 读取属性值 => 元素对象.属性名
- 修改属性值 => 元素对象.属性名 = 新的属性值
#### 7.3.2 内部文本操作

- 获取或者设置标签体的文本内容 => element.innerText
- 获取或者设置标签体的内容 => element.innerHTML

```html
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script>

function changeAttribute(){
// 操作属性
var in1 = document.getElementById("in1")

console.log(in1.type)
console.log(in1.value)

in1.type = "button"
in1.value = "hi"
}

function changeStyle(){
// 操作元素的样式
var in1 = document.getElementById("in1")
in1.style.color = "green"
in1.style.fontSize = "45px"
}

function changeText(){
// 修改文本
var div1 = document.getElementById("div01")

console.log(div1.innerText)
console.log(div1.innerHTML)

div1.innerHTML = "<h1>hi</h1>"
}

</script>
<title>Document</title>
</head>
<body>
<input id="in1" type="text" value="hello">
<div id="div01">
hello
</div>

<hr>
<button onclick="changeAttribute()">操作属性</button>
<button onclick="changeStyle()">操作样式</button>
<button onclick="changeText()">操作文本</button>
</body>
</html>
```
### 7.4 增删元素
#### 7.4.1 对页面的元素进行增删操作

| API                                      | 功能                                       |
| ---------------------------------------- | ------------------------------------------ |
| document.createElement(“标签名”)         | 创建元素节点并返回，但不会自动添加到文档中 |
| document.createTextNode(“文本值”)        | 创建文本节点并返回，但不会自动添加到文档中 |
| element.appendChild(ele)                 | 将ele添加到element所有子节点后面           |
| parentEle.insertBefore(newEle,targetEle) | 将newEle插入到targetEle前面                |
| parentEle.replaceChild(newEle, oldEle)   | 用新节点替换原有的旧子节点                 |
| element.remove()                         | 删除某个标签                               |
```html
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script>

function addCs(){
// 添加元素
var csli = document.createElement("li")
csli.id = "cs"
csli.innerText = "长沙"

// 将元素添加到父元素末尾
var cityul = document.getElementById("city")
        cityul.appendChild(csli)
    }

    function addCsBeforeSz(){
        // 在某个元素前面追加子元素
        // 添加元素
        var csli = document.createElement("li")
        csli.id = "cs"
        csli.innerText = "长沙"

        // 在指定元素前面添加元素
        var cityul = document.getElementById("city")
        var szli = document.getElementById("sz")
        cityul.insertBefore(csli,szli)
    }

    function replaceSz(){
        // 在某个元素前面追加子元素
        var csli = document.createElement("li")
        csli.id = "cs"
        csli.innerText = "长沙"

        // 替换子元素
        var cityul = document.getElementById("city")
        var szli = document.getElementById("sz")
        cityul.replaceChild(csli,szli)
    }

    function removeSz(){
        // 删除子元素
        var cityul = document.getElementById("city")
        var szli = document.getElementById("sz")
        cityul.removeChild(szli)
    }

    function clearCity(){
        // 清空所有
        var cityul = document.getElementById("city")

        cityul.innerHTML = ""
    }

    </script>
    <title>Document</title>
</head>
<body>
    <ul id="city">
        <li id="bj">北京</li>
        <li id="sh">上海</li>
        <li id="sz">深圳</li>
        <li id="gz">广州</li>
    </ul>

    <hr>
    <!-- 目标1 在城市列表的最后添加一个子标签  <li id="cs">长沙</li>  -->
    <button onclick="addCs()">增加长沙</button>
    <!-- 目标2 在城市列表的深圳前添加一个子标签  <li id="cs">长沙</li>  -->
    <button onclick="addCsBeforeSz()">在深圳前插入长沙</button>
    <!-- 目标3  将城市列表的深圳替换为  <li id="cs">长沙</li>  -->
    <button onclick="replaceSz()">替换深圳</button>
    <!-- 目标4  将城市列表删除深圳  -->
    <button onclick="removeSz()">删除深圳</button>
    <!-- 目标5  清空城市列表  -->
    <button onclick="clearCity()">清空</button>
</body>
</html>
```

## 8. 正则表达式
### 8.1 正则表达式简介

> 用于匹配字符串以执行检索, 验证, 替换等操作, 执行逻辑是匹配定义格式的字符串

+ 语法 

``` javascript
var patt=new RegExp(pattern,modifiers);
或者更简单的方式:
var patt=/pattern/modifiers; 
// pattern :定义的匹配格式, modifiers :定义的修饰符
```

> 修饰符

| 修饰符                                                | 描述                           |
| :------------------------------------------------- | :--------------------------- |
| [i](https://www.runoob.com/js/jsref-regexp-i.html) | 执行对大小写不敏感的匹配。                |
| [g](https://www.runoob.com/js/jsref-regexp-g.html) | 执行全局匹配（查找所有匹配而非在找到第一个匹配后停止）。 |
| m                                                  | 执行多行匹配。                      |

> 方括号

| 表达式                                                       | 描述                               |
| :----------------------------------------------------------- | :--------------------------------- |
| [[abc\]](https://www.runoob.com/jsref/jsref-regexp-charset.html) | 查找方括号之间的任何字符。         |
| [[^abc\]](https://www.runoob.com/jsref/jsref-regexp-charset-not.html) | 查找任何不在方括号之间的字符。     |
| [0-9]                                                        | 查找任何从 0 至 9 的数字。         |
| [a-z]                                                        | 查找任何从小写 a 到小写 z 的字符。 |
| [A-Z]                                                        | 查找任何从大写 A 到大写 Z 的字符。 |
| [A-z]                                                        | 查找任何从大写 A 到小写 z 的字符。 |
| [adgk]                                                       | 查找给定集合内的任何字符。         |
| [^adgk]                                                      | 查找给定集合外的任何字符。         |
| (red\|blue\|green)                                           | 查找任何指定的选项。               |

> 元字符

| 元字符                                                       | 描述                                        |
| :----------------------------------------------------------- | :------------------------------------------ |
| [.](https://www.runoob.com/jsref/jsref-regexp-dot.html)      | 查找单个字符，除了换行和行结束符。          |
| [\w](https://www.runoob.com/jsref/jsref-regexp-wordchar.html) | 查找数字、字母及下划线。                    |
| [\W](https://www.runoob.com/jsref/jsref-regexp-wordchar-non.html) | 查找非单词字符。                            |
| [\d](https://www.runoob.com/jsref/jsref-regexp-digit.html)   | 查找数字。                                  |
| [\D](https://www.runoob.com/jsref/jsref-regexp-digit-non.html) | 查找非数字字符。                            |
| [\s](https://www.runoob.com/jsref/jsref-regexp-whitespace.html) | 查找空白字符。                              |
| [\S](https://www.runoob.com/jsref/jsref-regexp-whitespace-non.html) | 查找非空白字符。                            |
| [\b](https://www.runoob.com/jsref/jsref-regexp-begin.html)   | 匹配单词边界。                              |
| [\B](https://www.runoob.com/jsref/jsref-regexp-begin-not.html) | 匹配非单词边界。                            |
| \0                                                           | 查找 NULL 字符。                            |
| [\n](https://www.runoob.com/jsref/jsref-regexp-newline.html) | 查找换行符。                                |
| \f                                                           | 查找换页符。                                |
| \r                                                           | 查找回车符。                                |
| \t                                                           | 查找制表符。                                |
| \v                                                           | 查找垂直制表符。                            |
| [\xxx](https://www.runoob.com/jsref/jsref-regexp-octal.html) | 查找以八进制数 xxx 规定的字符。             |
| [\xdd](https://www.runoob.com/jsref/jsref-regexp-hex.html)   | 查找以十六进制数 dd 规定的字符。            |
| [\uxxxx](https://www.runoob.com/jsref/jsref-regexp-unicode-hex.html) | 查找以十六进制数 xxxx 规定的 Unicode 字符。 |

> 量词

| 量词                                                         | 描述                                                         |
| :----------------------------------------------------------- | :----------------------------------------------------------- |
| [n+](https://www.runoob.com/jsref/jsref-regexp-onemore.html) | 匹配任何包含至少一个 n 的字符串。例如，/a+/ 匹配 "candy" 中的 "a"，"caaaaaaandy" 中所有的 "a"。 |
| [n*](https://www.runoob.com/jsref/jsref-regexp-zeromore.html) | 匹配任何包含零个或多个 n 的字符串。例如，/bo*/ 匹配 "A ghost booooed" 中的 "boooo"，"A bird warbled" 中的 "b"，但是不匹配 "A goat grunted"。 |
| [n?](https://www.runoob.com/jsref/jsref-regexp-zeroone.html) | 匹配任何包含零个或一个 n 的字符串。例如，/e?le?/ 匹配 "angel" 中的 "el"，"angle" 中的 "le"。 |
| [n{X}](https://www.runoob.com/jsref/jsref-regexp-nx.html)    | 匹配包含 X 个 n 的序列的字符串。例如，/a{2}/ 不匹配 "candy," 中的 "a"，但是匹配 "caandy," 中的两个 "a"，且匹配 "caaandy." 中的前两个 "a"。 |
| [n{X,}](https://www.runoob.com/jsref/jsref-regexp-nxcomma.html) | X 是一个正整数。前面的模式 n 连续出现至少 X 次时匹配。例如，/a{2,}/ 不匹配 "candy" 中的 "a"，但是匹配 "caandy" 和 "caaaaaaandy." 中所有的 "a"。 |
| [n{X,Y}](https://www.runoob.com/jsref/jsref-regexp-nxy.html) | X 和 Y 为正整数。前面的模式 n 连续出现至少 X 次，至多 Y 次时匹配。例如，/a{1,3}/ 不匹配 "cndy"，匹配 "candy," 中的 "a"，"caandy," 中的两个 "a"，匹配 "caaaaaaandy" 中的前面三个 "a"。注意，当匹配 "caaaaaaandy" 时，即使原始字符串拥有更多的 "a"，匹配项也是 "aaa"。 |
| [n$](https://www.runoob.com/jsref/jsref-regexp-ndollar.html) | 匹配任何结尾为 n 的字符串。                                  |
| [^n](https://www.runoob.com/jsref/jsref-regexp-ncaret.html)  | 匹配任何开头为 n 的字符串。                                  |
| [?=n](https://www.runoob.com/jsref/jsref-regexp-nfollow.html) | 匹配任何其后紧接指定字符串 n 的字符串。                      |
| [?!n](https://www.runoob.com/jsref/jsref-regexp-nfollow-not.html) | 匹配任何其后没有紧接指定字符串 n 的字符串。                  |

> RegExp对象方法

| 方法                                                         | 描述                                               |
| :----------------------------------------------------------- | :------------------------------------------------- |
| [compile](https://www.runoob.com/jsref/jsref-regexp-compile.html) | 在 1.5 版本中已废弃。 编译正则表达式。             |
| [exec](https://www.runoob.com/jsref/jsref-exec-regexp.html)  | 检索字符串中指定的值。返回找到的值，并确定其位置。 |
| [test](https://www.runoob.com/jsref/jsref-test-regexp.html)  | 检索字符串中指定的值。返回 true 或 false。         |
| [toString](https://www.runoob.com/jsref/jsref-regexp-tostring.html) | 返回正则表达式的字符串。                           |

> 支持正则的String的方法

| 方法                                                    | 描述                             |
| :------------------------------------------------------ | :------------------------------- |
| [search](https://www.runoob.com/js/jsref-search.html)   | 检索与正则表达式相匹配的值。     |
| [match](https://www.runoob.com/js/jsref-match.html)     | 找到一个或多个正则表达式的匹配。 |
| [replace](https://www.runoob.com/js/jsref-replace.html) | 替换与正则表达式匹配的子串。     |
| [split](https://www.runoob.com/js/jsref-split.html)     | 把字符串分割为字符串数组。       |
### 8.2 正则表带式体验
#### 8.2.1 验证

**注意**：这里是使用**正则表达式对象**来**调用**方法。

```javascript
// 创建一个最简单的正则表达式对象
var reg = /o/;
// 创建一个字符串对象作为目标字符串
var str = 'Hello World!';
// 调用正则表达式对象的test()方法验证目标字符串是否满足我们指定的这个模式，返回结果true
console.log("/o/.test('Hello World!')="+reg.test(str));
```

#### 8.2.2 匹配

```javascript
// 创建一个最简单的正则表达式对象
var reg = /o/;
// 创建一个字符串对象作为目标字符串
var str = 'Hello World!';
// 在目标字符串中查找匹配的字符，返回匹配结果组成的数组
var resultArr = str.match(reg);
// 数组长度为1
console.log("resultArr.length="+resultArr.length);

// 数组内容是o
console.log("resultArr[0]="+resultArr[0]);
```

#### 8.2.3 替换

**注意**：这里是使用**字符串对象**来**调用**方法。

```javascript
// 创建一个最简单的正则表达式对象
var reg = /o/;
// 创建一个字符串对象作为目标字符串
var str = 'Hello World!';
var newStr = str.replace(reg,'@');
// 只有第一个o被替换了，说明我们这个正则表达式只能匹配第一个满足的字符串
console.log("str.replace(reg)="+newStr);//Hell@ World!

// 原字符串并没有变化，只是返回了一个新字符串
console.log("str="+str);//str=Hello World!
```

#### 8.2.4  全文查找

如果不使用g对正则表达式对象进行修饰，则使用正则表达式进行查找时，仅返回第一个匹配；使用g后，返回所有匹配。

```javascript
// 目标字符串
var targetStr = 'Hello World!';

// 没有使用全局匹配的正则表达式
var reg = /[A-Z]/;
// 获取全部匹配
var resultArr = targetStr.match(reg);
// 数组长度为1
console.log("resultArr.length="+resultArr.length);
// 遍历数组，发现只能得到'H'
for(var i = 0; i < resultArr.length; i++){
  console.log("resultArr["+i+"]="+resultArr[i]);
}
```

对比

```javascript
// 目标字符串
var targetStr = 'Hello World!';
// 使用了全局匹配的正则表达式
var reg = /[A-Z]/g;
// 获取全部匹配
var resultArr = targetStr.match(reg);
// 数组长度为2
console.log("resultArr.length="+resultArr.length);
// 遍历数组，发现可以获取到“H”和“W”
for(var i = 0; i < resultArr.length; i++){
  console.log("resultArr["+i+"]="+resultArr[i]);
}
```

####  8.2.5 忽略大小写

```javascript
//目标字符串
var targetStr = 'Hello WORLD!';

//没有使用忽略大小写的正则表达式
var reg = /o/g;
//获取全部匹配
var resultArr = targetStr.match(reg);
//数组长度为1
console.log("resultArr.length="+resultArr.length);
//遍历数组，仅得到'o'
for(var i = 0; i < resultArr.length; i++){
  console.log("resultArr["+i+"]="+resultArr[i]);
}
```

对比

```javascript
//目标字符串
var targetStr = 'Hello WORLD!';
//使用了忽略大小写的正则表达式
var reg = /o/gi;
//获取全部匹配
var resultArr = targetStr.match(reg);
//数组长度为2
console.log("resultArr.length="+resultArr.length);
//遍历数组，得到'o'和'O'
for(var i = 0; i < resultArr.length; i++){
  console.log("resultArr["+i+"]="+resultArr[i]);
}
```

#### 8.2.6 元字符使用

```javascript
var str01 = 'I love Java';
var str02 = 'Java love me';
// 匹配以Java开头
var reg = /^Java/g;
console.log('reg.test(str01)='+reg.test(str01)); // false
console.log("<br />");
console.log('reg.test(str02)='+reg.test(str02)); // true
```

```javascript
var str01 = 'I love Java';
var str02 = 'Java love me';
// 匹配以Java结尾
var reg = /Java$/g;
console.log('reg.test(str01)='+reg.test(str01)); // true
console.log("<br />");
console.log('reg.test(str02)='+reg.test(str02)); // false
```

#### 8.2.7 字符集合的使用

```javascript
//n位数字的正则
var targetStr="123456789";
var reg=/^[0-9]{0,}$/;
//或者 ： var reg=/^\d*$/;
var b = reg.test(targetStr);//true
```

```javascript
//数字+字母+下划线，6-16位
var targetStr="HelloWorld";
var reg=/^[a-z0-9A-Z_]{6,16}$/;
var b = reg.test(targetStr);//true
```

### 8.3  常用正则表达式
| 需求   | 正则表达式                                                      |
| ---- | ---------------------------------------------------------- |
| 用户名  | /^\[a-zA-Z ]\[a-zA-Z-0-9]{5,9}\$/                          |
| 密码   | /^\[a-zA-Z0-9 \_-@#& \*]{6,12}\$/                          |
| 前后空格 | /^\s+\|\s+\$/g                                             |
| 电子邮箱 | /^\[a-zA-Z0-9 \_.-]+@(\[a-zA-Z0-9-]+\[.]{1})+\[a-zA-Z]+\$/ |
- 解释说明
    - 用户名: 首个字符需要是字母, 后面的字符有5-9个, 可以有字母和数字
    - 密码: 字符有字母数字, \_ - @ # & \* , 6-12位的密码
    - 前后空格: 匹配任意数量的空白字符开头或者任意数量的空白字符结尾
    - 电子邮箱, 同上, 不做额外过多解释
