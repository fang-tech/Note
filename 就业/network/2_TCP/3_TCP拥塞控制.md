# TCP重传, 滑动窗口, 流量控制, 拥塞控制

## 常见的重传机制

TCP会在以下两种情况触发重传机制

- 数据包丢失, 通过序列号判断出来消息丢失了
- 确认应答丢失, 通过没有收到ACK来判断消息丢失了

### 超时重传

RTT (Round-Trip Time往返时延)

![RTT](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost2/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C/TCP-%E5%8F%AF%E9%9D%A0%E7%89%B9%E6%80%A7/6.jpg?)

RTT**是数据发送时刻到接受到确认的时刻的差值**

超时重传时间用RTO(Retransmission Timeout 超时重传时间) 表示

如果超时时间设置得过大或者过小

- RTO过大, 重发就慢, 丢了很久才重发, 没有效率
- RTP过小, 会导致可能没有丢失, 只是ACK晚了一点到, 就触发了重发机制, 增加网络拥塞, 导致更多的超时, 导致更多的重发, 最后直接堵死了

在Linux中RTO的计算

- 需要采样RTT的时间, 然后进行加权平均, 算出来一个平滑RTT值, 而且这个值还是动态变化的, 因为网络状况也是在动态地变化
- 除了采用RTT, 还需要采样RTT的波动范围, 避免RTT有一个大的波动的话很难被发现的情况

![RFC6289 建议的 RTO 计算 ](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost2/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C/TCP-%E5%8F%AF%E9%9D%A0%E7%89%B9%E6%80%A7/9.jpg?image_process=watermark,text_5YWs5LyX5Y-377ya5bCP5p6XY29kaW5n,type_ZnpsdHpoaw,x_10,y_10,g_se,size_20,color_0000CD,t_70,fill_0)

**根据这个公式, 越早之前的SRTT, DevRTT, RTT对RTO的影响呈指数级变化(每次变为原来的alpha/ (1-belta) / muim倍),**

**SRTT是平滑RTT, DevRTT是平滑RTT和当前RTT之间的差距, 两者能让RTO的计算考虑到历史的网络情况, 同时越近的RTT对RTO的影响越大, DevRTT能保证RTO对最近的大的波动RTT的敏感**  

Linux**α = 0.125，β = 0.25， μ = 1，∂ = 4**

如果超时重发的数据, 再次超时的时候, 下次超时重发的时间是上一次的两倍

### 快速重传

![快速重传机制](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost2/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C/TCP-%E5%8F%AF%E9%9D%A0%E7%89%B9%E6%80%A7/10.jpg?image_process=watermark,text_5YWs5LyX5Y-377ya5bCP5p6XY29kaW5n,type_ZnpsdHpoaw,x_10,y_10,g_se,size_20,color_0000CD,t_70,fill_0)

快速重传的工作方式就是, 如果收到了三个相同的ACK, 会在定时器过期前, 重传丢失的报文的报文段

解决了超时时间相对较长的问题

但是没有解决, 重传的时候, 是重传一个, 还是重传所有的问题

- 重传一个, 重传的效率很低, 如果有批量丢失的情况, 就需要每个丢失的数据都发送三个ACK才能重发
- 重发丢失报文及之后的所有报文, 就很容易有重复报文, 浪费网络资源

于是就有了`SACK`方法

### SACK

Selective Acknowledgment, ACK **选择性确认**

需要在TCP的选项字段里面加一个SACK, 它可以将已收到的数据的信息发送给 \[发送方], 这样发送方就可以知道哪些数据收到了, 哪些数据没有收到, 这样就只需要重新传丢失的数据就行

![选择性确认](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost2/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C/TCP-%E5%8F%AF%E9%9D%A0%E7%89%B9%E6%80%A7/11.jpg?image_process=watermark,text_5YWs5LyX5Y-377ya5bCP5p6XY29kaW5n,type_ZnpsdHpoaw,x_10,y_10,g_se,size_20,color_0000CD,t_70,fill_0)

如果要支持SACK, 必须要通信双方都支持, 在Linux下, 可以哦通过`net-IP v.tcp_sack`参数来打开这个功能 (Linux 2.4后自动打开)

### D-SACK

Duplicate SACK 又称D-SACK, **使用SACK来进一步告诉\[发送方]有哪些数据被重复接收了**

能够帮助发送方区分

- 数据包真正丢失
- 数据包被网络延迟或者重排序
- 确认包ACK丢失导致的重传

从而更精确地识别网络状况, 优化重传策略, 改进拥塞控制

##### ACK丢包时

![ACK 丢包](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost2/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C/TCP-%E5%8F%AF%E9%9D%A0%E7%89%B9%E6%80%A7/12.jpg?image_process=watermark,text_5YWs5LyX5Y-377ya5bCP5p6XY29kaW5n,type_ZnpsdHpoaw,x_10,y_10,g_se,size_20,color_0000CD,t_70,fill_0)

接收方一直**没有收到ACK**, 后面直接收到返回的ACK 4000, 而这个时候接收到的数据通过SACK可以知道是3000~3500, 这个发送方就知道发送数据没有丢失, 只是应答报文丢失了

##### 网络延迟

![网络延时](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost2/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C/TCP-%E5%8F%AF%E9%9D%A0%E7%89%B9%E6%80%A7/13.jpg?image_process=watermark,text_5YWs5LyX5Y-377ya5bCP5p6XY29kaW5n,type_ZnpsdHpoaw,x_10,y_10,g_se,size_20,color_0000CD,t_70,fill_0)

发送的报文延迟以后, 触发了快速重传, 在重传以后, 被延迟的数据包到达了接收方, 这个时候接受方返回ACK 3000 SACK 1000~1500, 说明1000~1500这段被重复接收了, 而这一段之前重传过, 说明这段不是丢失了, 只是延迟了

> 如果是真的丢失会怎么样

如果是真的丢失, SACK中就不会是已经发送过的数据, 因为一直丢失, 应该只有最后一份数据是成功到达了接收端的

> 怎么区分是网络延时还是ACK丢包

如果是网络延迟,  那么D-SACK在重传之后的某个时间发送给\[发送端], 而不是紧随重传而至, 这就是关键区别

ACK丢包的情况

- D-SACK几乎紧随重传后到达(时间差远小于RTT)
- 只有重传包被延迟接收
- 这种情况下不会错误地认为网络拥塞, 避免不必要地缩减拥塞窗口

网络延迟的情况

- 原始数据包和重传包之间到达的时间差较大
- D-SACK到达时间表明原始包和重传包之间有明显时间间隔
- 调整RTT估算值和重传超时计算, 适应较高的网络延迟

## 滑动窗口

TCP协议中为了保证可靠性, 每发送一条数据报, 都需要收到一个ACK来确认收到了这条数据报, 但是如果每次都是发送一条数据段, 然后必须等待这条数据段收到ACK才能发送下一条数据段, 效率很低, 网络条件越差, 效率越低

解决方式就是 : 设立缓冲区窗口, 我们能够连续发送数据, 而不需要等待确认应答, 通过窗口大小**来控制我们最多无需等待确认应答, 而可以继续发送数据的数量**

窗口大小由接收端的Window字段决定

如果窗口大小为3, 发送方就能连续发送3个TCP段, 并且中途如果有ACK丢失, 也可以通过下一个确认应答来确认, 比如ACK600丢失了, 但是接收到了ACK700, 这个时候就通过了下一个确认应答来确认了TCP段已经被收到到了, 这个就叫**累计确认**

发送方的窗口示例, 分成四个部分

- #1 : 已发送并接收到ACK确认数据 (窗口前)
- #2 : 已发送但是没有接受到ACK确认的数据 (窗口中已经发送过的数据)
- #3 : 没有发送, 但是在接收端处理范围内的数据 (窗口中还没有发送但是能发送的数据)
- #4 : 没有发送, 并且不在接收端处理范围内的数据 (窗口后)

![](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost2/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C/TCP-%E5%8F%AF%E9%9D%A0%E7%89%B9%E6%80%A7/16.jpg?)

![可用窗口耗尽](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost2/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C/TCP-%E5%8F%AF%E9%9D%A0%E7%89%B9%E6%80%A7/17.jpg?)

![32 ~ 36 字节已确认](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost2/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C/TCP-%E5%8F%AF%E9%9D%A0%E7%89%B9%E6%80%A7/18.jpg)

>  程序如何表示发送方的四个部分

![SND.WND, SND.UN, SND.NXT](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost2/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C/TCP-%E5%8F%AF%E9%9D%A0%E7%89%B9%E6%80%A7/19.jpg?image_process=watermark,text_5YWs5LyX5Y-377ya5bCP5p6XY29kaW5n,type_ZnpsdHpoaw,x_10,y_10,g_se,size_20,color_0000CD,t_70,fill_0)

- SUN.WND : 窗口的大小
- SUN.UNA (Send Unacknowledged): 指向发送窗口中的已发送但是未收到确认的第一个字节的序列号, 也就是#2部分的第一个字节
- SUN.NXT : 指向未发送但总大小未在接收方处理范围内的第一个字节的序列号, 也就是#3的第一个字节
- 指向#4的第一个字节通过SND.UNA + SUN.WND来计算出来

可用窗口的大小 = SND.WND - (SUN.UNA - SUN.NXT)

> 接收方的滑动窗口

![接收窗口](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost2/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C/TCP-%E5%8F%AF%E9%9D%A0%E7%89%B9%E6%80%A7/20.jpg)

分成三个部分

- #1 : 已经成功接收并且确认的数据 (窗口前)
- #2 : 未收到数据但可以接收的数据(窗口)
- #3 : 未收到的数据并且不在窗口范围内的数据 (窗口后)

也是通过指针来划分

- RCV.WND : 接收窗口的大小
- RCV.NXT : 期望对方送来的第一个字节的序列号
- 指向#4的第一个字节 = RCV.NXT + RCV.WND

> 发送窗口和接收窗口总是相等的吗

接收窗口大的大小**约等于**发送窗口的大小

滑动窗口的大小不是一成不变的, 会**随着网络条件动态变化**, 新的接收窗口的大小需要通过TCP头部中的Window字段来告诉对方, 这个过程有**时延**, 所以接收窗口和发送窗口之间的大小关系是约等于

## 流量控制

在TCP中发送方会根据接收方的接收能力来调整发送数据的速度, **发送方根据接收方提供的窗口大小调整数据发送的速度**

### 操作系统缓冲区和滑动窗口之间的关系

发送窗口和接收窗口中所存放的字节数都是放在操作系统的缓冲区中的, 而操作系统中的缓冲区会**受到操作系统调整**

> 例子一

- 窗口的初始大小是360
- 服务端非常地繁忙, 应用层不能及时读取数据

![](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost2/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C/TCP-%E5%8F%AF%E9%9D%A0%E7%89%B9%E6%80%A7/22.jpg?image_process=watermark,text_5YWs5LyX5Y-377ya5bCP5p6XY29kaW5n,type_ZnpsdHpoaw,x_10,y_10,g_se,size_20,color_0000CD,t_70,fill_0)

- 客户端一次性向服务端发送了140字节的数据
- 服务端接收了以后将数据放在了缓冲区, 但是应用程序只读取了40字节的数据, 这个时候就有100字节的数据残留在了缓冲区, 接收窗口的大小就变成了**260** (360 - 100), 会在返回的ACK中告知Window = 260来调整发送窗口的大小

- 服务端不断积累缓冲区中的数据, 最后导致返回的ACK中的Window = 0, 这个时候发送端就无法再发送数据, 也就是发生了窗口关闭

> 第二个例子 : 操作系统直接减少了缓冲区的大小

系统资源很紧张, 操作系统直接减少了缓冲区的大小

![](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost2/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C/TCP-%E5%8F%AF%E9%9D%A0%E7%89%B9%E6%80%A7/23.jpg?image_process=watermark,text_5YWs5LyX5Y-377ya5bCP5p6XY29kaW5n,type_ZnpsdHpoaw,x_10,y_10,g_se,size_20,color_0000CD,t_70,fill_0)

- 客户端发送了140字节的数据, 发送窗口可用大小变成了220, 接收端接收到了140字节数据以后应用程序一个字节都没有读取, 这个时候操作系统直接减小了缓冲区的大小, 这个时候接收端的窗口大小变成了100字节 (360 - 140 - 120)
- 服务端返回ACK并告知Window = 100
- 但是在ACK到来之前, 这个时候发送窗口可发送的最大字节数是220, 这个时候客户端发送180字节
- 这180字节到达接收端以后造成了丢包, 因为接收窗口只有100字节
- 而Window = 100的消息到达了发送端以后, 发送端的窗口大小变成100, Usable变成了负数 -80 (100 - 180)

**为了防止这种错误情况, TCP规定是不允许同时减少缓存又收缩窗口的, 而是采用先收缩窗口, 过段时间再减少缓存, 这样就能避免丢包的情况**

### 窗口关闭

**在接收端返回的Window = 0的时候就发生了窗口关闭, 会阻止发送方给接收当传递数据, 直到窗口的大小是非0**

发生了窗口关闭以后, 接收端会在处理完数据以后, 发送一个窗口大小为非0的ACK报文, 如果这个报文在网络中丢失了, 就会发生死锁

- 发送端的发送窗口是0, 等待一个窗口大小为非0的ACK报文
- 接收端的接收窗口不是0 ,但是以为自己的ACK报文发过去了, 等待发送端发送TCP段

> TCP是怎么解决死锁问题?

TCP为每个连接设置一个持续定时器, 只要TCP连接一方收到了对方的0窗口通知, 就启动持续计数器

如果持续计数器超时, 就会发送窗口探测报文, 对方在确认窗口探测报文的时候, 就会给出自己的接收窗口的大小

- 如果接收窗口仍为0, 重置持续定时器
- 如果接收窗口不是0, 死锁的局面就被打破了

窗口探测的次数一般是3次, 每次间隔大约30~60S, 如果3次过后接收窗口还是0的话, 有的TCP的实现会直接发送RST报文来中断连接

### 糊涂窗口综合征

如果接收方的应用程序来不及处理缓冲区中的数据, 就会导致接收端的窗口大小越来越小, 最后接收端和发送端的窗口大小都只有几个字节

最后每次TCP段里面就只有几个字节的数据在传递, 最后发送的数据还没有`TCP + IP`40个字节大

> 例子

![糊涂窗口综合症](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost2/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C/TCP-%E5%8F%AF%E9%9D%A0%E7%89%B9%E6%80%A7/26.png?image_process=watermark,text_5YWs5LyX5Y-377ya5bCP5p6XY29kaW5n,type_ZnpsdHpoaw,x_10,y_10,g_se,size_20,color_0000CD,t_70,fill_0)

这个问题之所以会发生是因为

- 接收方能通告一个小的窗口
- 发送方能发送小数据

> 怎么让接收方不通告小的窗口呢

窗口大小 < min(MSS, 缓存空间 / 2), 通知窗口大小 = 0, 阻止对方再发送数据过来

> 怎么让发送方不能发送小的数据呢

Nagle算法, 只有满足下面两个条件之一的时候, 才可以发送数据

- 要等到可用窗口大小 >= MSS, 并且数据的大小>= MSS
- 收到之前发送数据的ACK回包

如果接收方还是会通知小的窗口, 即使使用Nagle算法也无法避免糊涂窗口综合征, 因为如果接收端回复ACK的速度很快, Nagle算法就不会拼接太多的数据包

可以在Socket设置`TCP_NODELAY`选项来关闭这个算法

```c
setsockopt(sock_fd, IPPROTO_TCP, TCP_NODELAY, (char *)&value, sizeof(int));
```

## 拥塞控制

流量控制只能保证发送方会根据接收方的接收能力来调整发送数据的速度

但是网络是共享的, 可能会因为其他主机导致网络拥堵, 发生拥塞

有了拥塞控制, 就是为了避免\[发送方]的数据填满整个网络, 发生了拥塞, TCP会自我牺牲, 降低发送的数据量

拥塞窗口 cwnd是发送方维护的一个**状态变量**, 会根据网路的拥塞程度动态变化

引入了拥塞窗口以后发送窗口swnd = min(cwnd, rwnd)

- 只要网络没有出现拥塞, cwnd增大
- 出现了拥塞, 减小

> 怎么知道当前的网络出现了拥塞?

发送方**没有在规定的时间内接收到ACK应答报文, 就可以认为网络出现了拥塞**

> 拥塞控制算法有哪些?

- 慢启动
- 拥塞避免
- 拥塞发生
- 快速恢复

### 慢启动

TCP刚建立的时候, 会一点一点提高发送数据包的数量

慢启动的规则就是 : **每收到一个ACK, cwnd就会 + 1**

假定cwnd和swnd相等(cwnd的单位一般是MSS, swnd和rwnd的单位一般是字节)

- 连接建立完成以后, 一开始初始化cwnd = 1, 表示可以传一个MSS的数据
- 收到一个ACK的应答确认以后, cwnd = 2 (1+1)
- 收到两个ACK的应答确认以后, cwnd = 4(2+2)
- 收到四个ACK的应答确认以后, cwnd = 8(4+4)
- ....

可以看出来拥塞窗口在**慢启动阶段是指数级增长的**

> 什么时候会增长到头呢

有慢启动门限 `ssthresh `(slow start threshold)状态变量

- 当cwd < ssthresh时, 使用慢启动算法
- cwd >= ssthresh时, 使用拥塞避免算法

### 拥塞避免

一般来说ssthresh的大小是`65535`字节

当进入到拥塞避免算法以后, **每收到一个ACK, cwnd会增长1/cwnd, 也就是收到cwnd个ACK以后, cwnd才会增长1**

- 加入ssthresh = 8, 当8个ACK到来的时候cwnd才会从8 -> 9
- 接下来需要收到9个ACK, 才会从9 -> 10

拥塞避免算法将cwnd的增长从指数级增长变成了线性增长

这个时候拥塞窗口还是在增长的, 只是变得更慢了, 这么一直增长以后, 网络就会慢慢进入到拥塞状态, 这个时候就会触发重传机制, 就会使用到拥塞发生算法

### 拥塞发生

有两种重传机制, 快速重传和超时重传, 不同的重传方式, 会有不同的拥塞发生算法

> 发生超时重传的拥塞发生算法

这种情况TCP认为发生了严重的拥塞, 大部分数据都丢失了才会出现超时重传, 不然就会进入到快速重传

- `ssthresh` = `cwnd / 2`
- `cwnd `= 1
- 然后重新进入慢启动阶段

这种方式会导致网络突然出现卡顿

> 发生快速重传的拥塞控制算法

这种情况TCP认为拥塞情况不严重, 大部分数据里面, 只有小部分丢失了

- `ssthresh `= `cwnd / 2`
- 进入快速恢复算法

### 快速恢复

- 拥塞窗口 cwnd = ssthresh + 3
- 重传丢失的数据包
- 如果再收到重复的ACK, cwnd + 1
- 如果收到新数据的ACK, cwnd = ssthresh , 恢复到之前的状态, 即再次进入拥塞避免状态



![快速重传和快速恢复](https://cdn.xiaolincoding.com/gh/xiaolincoder/ImageHost4@main/网络/拥塞发生-快速重传.drawio.png?image_process=watermark,text_5YWs5LyX5Y-377ya5bCP5p6XY29kaW5n,type_ZnpsdHpoaw,x_10,y_10,g_se,size_20,color_0000CD,t_70,fill_0)
