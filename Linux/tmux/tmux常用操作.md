# tmux

> 一般来说, 每次通过ssh等方式连接服务器并打开一个窗口, 实际上是创建了一个和服务器之间的session, 这样就将添加窗口和创建会话两个功能绑定在了一起, 而tmux则实现了这两个的分离, 有了会话和窗格的概念, 实现了终端的复用

- Tips : 不过一般也只是用来分隔窗口而已, 来实现分屏操作 : )
## 会话

## 窗格

### 查看所有的会话
```bash
tmux ls
```

### 新建会话

```bash
tmux new -s <session-name>(optional)
```

### 接入会话

```bash
tmux attach -t <session-name>(or 编号)
```

### 杀死会话

```bash
tmux kill-session -t <session-name>(编号)
```

### 切换编号

```bash
tmux switch -t <session-name>(编号)
```

### 分离会话

```bash
tmux detach
```

### 快捷键

- `Ctrl+b d`：分离当前会话。
- `Ctrl+b s`：列出所有会话。
- `Ctrl+b $`：重命名当前会话。

### 最简操作流程

1. 新建会话`tmux new -s my_session`
2. 在Tmux窗口运行需要的程序
3. 按下快捷键Ctrl+b d将会话分离
4. 下次使用的时候, 重新连接到会话tmux attach-session -t my_session

## 窗格操作

> 一般通过快捷键实现, 这里直接记快捷键了

- `Ctrl+b %`：划分左右两个窗格。
- `Ctrl+b "`：划分上下两个窗格。
- `Ctrl+b <arrow key>`：光标切换到其他窗格。`<arrow key>`是指向要切换到的窗格的方向键，比如切换到下方窗格，就按方向键`↓`。
- `Ctrl+b ;`：光标切换到上一个窗格。
- `Ctrl+b o`：光标切换到下一个窗格。
- `Ctrl+b {`：当前窗格与上一个窗格交换位置。
- `Ctrl+b }`：当前窗格与下一个窗格交换位置。
- `Ctrl+b Ctrl+o`：所有窗格向前移动一个位置，第一个窗格变成最后一个窗格。
- `Ctrl+b Alt+o`：所有窗格向后移动一个位置，最后一个窗格变成第一个窗格。
- `Ctrl+b x`：关闭当前窗格。
- `Ctrl+b !`：将当前窗格拆分为一个独立窗口。
- `Ctrl+b z`：当前窗格全屏显示，再使用一次会变回原来大小。
- `Ctrl+b Ctrl+<arrow key>`：按箭头方向调整窗格大小。
- `Ctrl+b q`：显示窗格编号
## 参考资料

- [阮总的博客](https://www.ruanyifeng.com/blog/2019/10/tmux.html)
- 
