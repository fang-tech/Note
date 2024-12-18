---
tags:
  - JavaWeb
  - CSS
  - frontend
---
# CSS

## css的三种引入方式

- 行内式
  - 直接将style放在对应的标签内部

```html
<input type="button" value="按钮" 
       style="
              display: block;
              width: auto;
              height: auto;
              font-family: 隶书;
              background-color: bisque;
              border-radius: 5px;
              border: 3px solid goldenrod;
              color: brown;
              font-size: 22px;
              line-height: 30px;
              "
       />
```

- 内嵌式
  - 将行内式的style提取出来, 放在head里, 用于规范所有的对应标签

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <style>
        input {
            display: block;
            width: auto;
            height: auto;
            font-family: 隶书;
            background-color: bisque;
            border-radius: 5px;
            border: 3px solid goldenrod;
            color: brown;
            font-size: 22px;
            line-height: 30px;
        }
    </style>
</head>
<body>
    <input type="button" value="按钮" />
</body>
</html>
```

-  外部引用式
  - 将css样式提取出来, 放在别的文件中, 然后在head中引用

```html
```

## 引用样式之元素选择法

- id法
  - 创造以id命名的样式, 然后标签的id设置为对应样式的id, 就能获取到对应的样式

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <style>
        #btn1 {
            display: block;
            width: auto;
            height: auto;
            font-family: 隶书;
            background-color: bisque;
            border-radius: 5px;
            border: 3px solid goldenrod;
            color: brown;
            font-size: 22px;
            line-height: 30px;
        }
    </style>
</head>
<body>
    <!-- id法 -->
    <input type="button" id="btn1" value="按钮"/>
    <input type="button" id="btn2" value="按钮"/>
    <input type="button" id="btn3" value="按钮"/>
    <input type="button" id="btn4" value="按钮"/>
</body>
</html>
```

- class法
  - 通过 .ClassName{}的形式设置样式, 每个标签再去选择使用哪些样式

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <style>
        .shapeClass {
            display: block;
            width: auto;
            height: auto;
            border-radius: 5px;
        }
        .colorClass {
            background-color: bisque;
            border: 3px solid goldenrod;
        }
        .fontClass{
            font-family: 隶书;
            color: brown;
            font-size: 22px;
            line-height: 30px;
        }
        #btn1 {
            display: block;
            width: auto;
            height: auto;
            font-family: 隶书;
            background-color: bisque;
            border-radius: 5px;
            border: 3px solid goldenrod;
            color: brown;
            font-size: 22px;
            line-height: 30px;
        }
    </style>
</head>
<body>
    <!-- id法 -->
    <input type="button" id="btn1" value="按钮"/>
    <input type="button" id="btn2" value="按钮"/>
    <input type="button" id="btn3" value="按钮"/>
    <input type="button" id="btn4" value="按钮"/>
    <!-- class法 -->
    <input class="fontClass colorClass shapeClass" type="button" value="按钮"/>
    <input class="fontClass colorClass" type="button" value="按钮"/>
    <input class="shapeClass" type="button" value="按钮"/>
    <input type="button" value="按钮"/>
</body>
</html>
```

## 块的浮动

- 最开始的设计初衷是为了解决图片环绕文字的问题 : float属性
  - 发生浮动以后, 块就脱离了文档流, 相当于图层变得更上层了, 会直接覆盖原来的文档流内容, 如果发生了重叠现象的话
  - 浮动有向左和向右, 如果有多个元素, 则会叠起来

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <style>
        .innerDiv {
            width: 60px;
            height: 60px;
            background-color: aqua;
            
            color: black;
            border: 1px black solid;
            float: left;
        }
        .outerDiv {
            width: 60px;
            height: 60px;
            background-color: red;
            color: black;
            border: 1px rgb(48, 144, 19) solid;
            float: right;
        }
    </style>
</head>
<body>
    <div class="innerDiv">框1</div>
    <div class="outerDiv">框2</div>
    <div class="innerDiv">框3</div>
</body>
</html>
```

## 定位

| position | 功能                                               |
| -------- | -------------------------------------------------- |
| absolute | 绝对定位, 相对static定位以外的第一个父元素进行定位 |
| fixed    | 生成绝对定位, 相对于浏览器窗口进行定位             |
| relative | 生成相对定位, 相对于其原本的位置进行定位           |
| static   | 默认的情况, 没有定位, 定位元素不会生效             |

- static => 默认情况, 块元素垂直排列, 行内元素水平排列
- absolute
  - 很像float, 移动后会让出自己原来的位置

```html
<head>
    <meta charset="UTF-8">
    <style>
        .innerDiv{
                width: 100px;
                height: 100px;
        }
        .d1{
            background-color: rgb(166, 247, 46);
            position: absolute;
            left: 120px;
            top: 50px;
        }
        .d2{
            background-color: rgb(79, 230, 124);
            position: absolute;
            left: 220px;
            top: 50px;
        }
        .d3{
            background-color: rgb(26, 165, 208);
            position: absolute;
            left: 320px;
            top: 50px;
        }
    </style>
</head>
<body>
        <div class="innerDiv d1">框1</div>
        <div class="innerDiv d2">框2</div>
        <div class="innerDiv d3">框3</div>
</body>
```

- relative, 不用赘述了, 相对于其原来的位置有left, right, top, bottom四个属性
  - 会保留原来的位置, 其他元素并不会移动到位置
- fixed : 相对于浏览器窗口定位
  - 定位后会让出原来的位置, 其他元素可以占用

## CSS盒子模型

<img src="1681262535006.png" alt="1681262535006" style="zoom:67%;" />

- 说明
  - Margin : 清除边框外的区域, 外边距是透明的
  - Border : 围绕在内边距和内容上的边框
  - Padding : 内容和边框之间的距离
  - Content : 内容

```html
<head>
    <meta charset="UTF-8">
    <style>
        .outerDiv{
            width: 800px;
            height: 400px;
            border: 1px solid green;
            background-color: aliceblue;
            margin: 0px auto;
            padding: 20px;
            position: relative;
        }
        .innerDiv{
                width: 100px;
                height: 100px;
        }
        .d1{
            background-color: rgb(166, 247, 46);
            position: absolute;
            right: 30px;
            top: 30px;
        }
        .d2{
            background-color: rgb(79, 230, 124);
        }
        .d3{
            background-color: rgb(26, 165, 208);
        }
    </style>
</head>
<body>
    <div class="outerDiv">
        <div class="innerDiv d1">框1</div>
        <div class="innerDiv d2">框2</div>
        <div class="innerDiv d3">框3</div>
    </div>
</body>
```

