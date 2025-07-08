# Socket编程

## 针对TCP如何进行socket编程

![基于 TCP 协议的客户端和服务端工作](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9jZG4uanNkZWxpdnIubmV0L2doL3hpYW9saW5jb2Rlci9JbWFnZUhvc3QyLyVFOCVBRSVBMSVFNyVBRSU5NyVFNiU5QyVCQSVFNyVCRCU5MSVFNyVCQiU5Qy9UQ1AtJUU0JUI4JTg5JUU2JUFDJUExJUU2JThGJUExJUU2JTg5JThCJUU1JTkyJThDJUU1JTlCJTlCJUU2JUFDJUExJUU2JThDJUE1JUU2JTg5JThCLzM0LmpwZw?x-oss-process=image/format,png)

- 服务端和客户端初始化`socket`, 得到文件描述符
- 服务端调用`bind`将`socket`绑定监听IP和端口, 调用`listen`监听该端口
- 服务端调用`accept`等待客户端连接
- 客户端调用`connect`, 向服务端的地址和端口发起连接请求
- 服务端`accept`返回用于传输的`socket`的文件描述符
- 客户端调用`write`写入数据, 服务端调用`read`读取数据
- 客户端断开连接, 调用`close`, 服务端`read`数据的时候, 就会读取到`EOF`, 处理完数据以后, 服务端就会调用`close`

服务端调用accept时, 返回的时一个已经完成连接的socket, 后续用来传输数据

所以用于监听端口的socket和真正用来传输数据的socket是两个socket, 一个叫做监听socket, 一个叫做已完成连接socket

## listen的时候参数backlog参数的意义

```c
int listen(int socketfd, int backlog)
```

在早期的linux内核中, backlog是SYN队列的大小

在Linux内核2.2以后, **backlog变成accept队列, 也就是已经完成连接的队列长度**

但是上限值是由内核参数somaxconn的大小决定, 也就是accept = min(somaxconn, backlog)

## accept发生在三次握手的哪一步

![socket 三次握手](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost4/网络/socket三次握手.drawio.png)

**accept发生在三次握手的第三次握手成功之后, 会成功返回, connect会在第二次握手成功以后成功返回**

## 客户端调用close了, 连接是断开的流程是什么

- 客户端调用close, 表示没有要发送的数据了, 这个时候客户端发送FIN报文给服务端
- 服务端接收到了这个FIN报文, TCP协议栈会为这个报文插入一个文件描述符EOF到接收缓冲区, 应用程序可以调用read来感知到这个FIN包, 这个EOF会被放在已经排队等候的其他已经接收的数据之后, 服务端需要处理这种额外的异常情况, 因为EOF表示之后不会再接收任何数据了, 服务端进入到CLOSE_WAIT状态
- 当处理完数据以后接收到EOF就会调用close关闭socket, 这会使服务端发送一个FIN报文给客户端, 然后进入到LAST_ACK状态
- 然后就是第四次握手

## 没有accept, 能建立TCP连接吗

**可以**建立, accept系统调用不参与TCP的三次握手过程, 只是负责从TCP全连接队列中取出来一个已经建立好连接的socket, 用户层能通过这个socket来进行读写操作

## 没有listen也能建立TCP连接

TCP连接可以是自己连自己的自连接, 也可以是两个客户端同时向对方发出请求, 没有服务端