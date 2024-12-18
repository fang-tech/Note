---
tags:
  - frontend
  - JavaWeb
  - HTML
---
# HTML

## 整体框架

```html
<!DOCTYPE html> 				-> HTML5的文档声明
<html lang="en">				-> 根标签, 并说明语言是英语
<head>							-> 	头部标签
    <meta charset="UTF-8">		-> 用于指明字符编码
    <title>Document</title>		-> 网页的标题
</head>							-> 头部标签的结尾
<body>							-> 文档的内容
    
</body>
</html>
```



## HTML的常用标签

### 标题标签 & 段落标签 &换行标签

hr => horizontal rule 水平线, br => line break => 换行

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
</head>
<body>
    <h1>一级标题</h1>
    <h2>二级标题</h2>
    <h3>三级标题</h3>
    <h4>四级标题</h4>
    <h5>五级标题</h5>
    <h6>六级标题</h6>

    <p>第一个段落</p>
    <p>段落之间是有换行的一</p>
    如果没有p包裹,
    是没有换行的
    <br>
    强行换行符号
    <hr> 
    分割线
</body>
</html>
```

### 列表标签

```html
<ol> => 有序列表
    <li></li> => 列表项
</ol>

<ul> => 无序列表
    <li></li>
</ul>
```

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
</head>
<body>
    <ol>
        <li>JJAVASE</li>
        <li>JAVAWeb</li>
        <li>MYSql</li>
    </ol>

    <ul>
        <li>好困</li>
        <li>怎么会这么困</li>
        <li>是什么原因
            <ol>
                <li>运动后肉体上疲惫</li>
                <li>集中精力累的</li>
                <li>差的配合掉的san值</li>
            </ol>
        </li>
    </ul>
</body>
</html>
```

### 链接标签 & 多媒体标签

```html
<a href="资源路径" target="_blank/_self">文字</a>  => 默认的时候target的值是_self, 如果是外部链接需要使用_blank
```

```html
<a href="Demo1.html", target="_blank">相对路径本地资源链接</a> <br>
<a href="Demo2.html", target="_self">绝对路径本地资源链接</a><br>
<a href="http://www.bing.com", target="_blank">外部资源链接</a><br>

<img src="IMG.png" title="鼠标悬停在图像上显示的文字" alt="显示失败的时候显示的内容" />
<audio src="path" autoplay="autoplay" controls="controls" loop="loop" ></audio>
<video src="path" autoplay="autoplay" controls="controls" loop="loop" ></video>
```

### 表格标签

```html
<table> => 声明表格
    <tr> => 一行数据
        <th></th> => 文字在表格中居中加粗
        <td></td> => 正常的表格中的一格
    </tr>
    <tr>
    	<td colspan="a" rowspan="b"></td> => a:跨的列数, b:跨的行数
    </tr>
</table>
```



```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
</head>
<body>
    <table border="1px" style="width: 400px; margin: 0px auto;">
        <tr>
            <th>排名</th>
            <th>name</th>
            <th>score</th>
            <th>备注</th>
        </tr>
        <tr>
            <td>1</td>
            <td>zxm</td>
            <td>100</td>
            <td rowspan="6">前三名升职加薪</td>
        </tr>
        <tr>
            <td>2</td>
            <td>lxh</td>
            <td>99</td>
        </tr>
        <tr>
            <td>3</td>
            <td>wxh</td>
            <td>98</td>
        </tr>
        <tr>
            <td>总人数</td>
            <td colspan="2">2000</td>
        </tr>
        <tr>
            <td>平均分</td>
            <td colspan="2">90</td>
        </tr>
        <tr>
            <td>及格率</td>
            <td colspan="2">80%</td>
        </tr>
    </table>
</body>
</html>	
```

![image-20241126170402772](C:\Users\28191\AppData\Roaming\Typora\typora-user-images\image-20241126170402772.png)

### 表单标签

- 用于向特定服务器提交内容
- 两种method的区别 : 
  - get : 数据会到action中填写的url后面url?name1=value&name2=value...
  - post : 数据会通过请求体发送, 不会后缀在url后

```HTML
<from action="目标服务器的地址" method="提交的方法"> => 父级标签
	<input type="" name="" value=""/> <br> => 表单项标签
</from>
```

```html
<form action="http://atguigu.com" method="get">
    用户名 <input type="text" name = "username"/> <br>
    密&nbsp;&nbsp;&nbsp;码 <input type="password" name="password" /> <br>
    <input type="submit" value="登录" />
    <input type="reset" value="重置" />
</form>
```

### 表单项标签

> 单选框

```html
<input type="radio" name="sex" value="man"/>男
<input type="radio" name="sex" value="woman"/>女
```

- name => 用于将单选框分组, 组内被选是互斥的, value => 提交上去的表单中sex的值

> 复选框

```html
<input type="checkbox" name="team" value="China"/>中国
<input type="checkbox" name="team" value="France" checked/>法国
<input type="checkbox" name="team" value="Italian"/>意大利
```

> 下拉框

```html
<select name="interesting">
    <option value="swimming">游泳</option>
    <option value="running" selected>跑步</option>
    <option value="shooting">射击</option>
</select>
```

> 按钮

```html
<p>按钮</p>
<input type="button" value="普通按钮">
<input type="submit" value="提交按钮">
<input type="reset" value="重置按钮">

<p>通过button实现的按钮</p>
<button type="button">普通按钮</button>
<button type="submit">提交按钮</button>
<button type="reset">重置按钮</button>
```

> 隐藏域

  用于设置固定会提交的内容, 不需要也不希望用户看到的表单内容, 比如用户的uuid等

 ```html
 <input type="hidden" value="123456789" name="userId"/>
 ```

> 多行文本框

- 没有value选项, 可以通过在双标签中间填入内容来设定默认内容

```html
自我介绍:<textarea name="desc">默认信息</textarea>
```

> 文件

```html
<input type="file" name="file"/>
```

### 布局相关的标签

> div => 用于做块的划分
>
> span => 用作对层的划分

```html
<div style="width: 500px; height:400px; background-color: brown;">
    <div style="width: 300px; height: 200px; background-color: beige;"></div>
</div>
<div style="width: 100px; height: 100px; background-color: rebeccapurple;"></div>
<span style="width: auto; height: 12px; background-color: aqua;">span显示的内容</span>
```

常常通过CSS设置大小背景颜色等参数


