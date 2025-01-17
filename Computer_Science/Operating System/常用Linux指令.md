You can use Ctrl+U to clear up to the beginning.

You can use Ctrl+W to delete just a word.

You can also use Ctrl+C to cancel.

If you want to keep the history, you can use Alt+Shift+# to make it a comment.
# GCC

## GCC的运行步骤

- 编译的过程最多分为预处理, 编译, 汇编, 链接四个步骤

## 输出文件类型的解释

- .o文件 : 编译中间目标文件, 相当于win上的.obj
- .out文件 : 可执行文件, 相当于win上的.exe
- .i文件 : 不需要预处理的c文件
- .s : 汇编代码
- .S : 必须预处理的汇编代码
## 常见选项使用


- `-static` : 会保存链接的lib的代码
- `--verbose` : 可以查看所有的编译选项
- `-Wl` : 打印链接选项
- `-Wl, --verbose` : 查看所有的编译链接选项

##  编译整个流程

- 编译直接生成.out文件 (可运行文件) : `gcc file.c`
- 编译生成.s汇编代码 : `gcc -S file.c`
- 生成预编译以后的结果 : `gcc -E file.c`
- 生成编译中间文件 (还没有link的文件) : `gcc -c file.c`
- 强行链接.o文件 : `ld file.o -e main`, 最后两个参数指定程序的入口是main
# GDB

- 进入调试环境 : `gdb ./a.out` 
- 运行第一行 starti
- 进入可视化界面 `layout asm`
- 下一步 : si
- 

# grep 

> 用于过滤的用的正则表达

-v :  显示不包含匹配文本的所有行, 反转匹配
-e : 指定字符串作为查找文件内容的样式 ( 正则表达式的内容 )
% : 指定作用的范围为整个文件
%! : 用外部命令处理整个文件
`%!grep -v -e '1'` : 通过这行能只显示成功运行的日志


# objdump


> 用来查看对应的汇编代码

`objdump -d file.out` 查看对应文件的汇编代码
- 加上 `| less` 能用于阅读生成汇编代码
- `|` 表示将前的程序的输出作为|后的程序的输入
# wc : words count

用于统计文本字数的工具

## 选项

- `-l` 统计行数
- 默认 : 统计字数


# 常用linux操作系统命令

## 删除文件/文件夹

`rm file`

## 改名文件

`mv 源文件名 目标文件名`

