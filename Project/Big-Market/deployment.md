# docker

## docker engine部署 - Ubuntu

[see Docker docs - Ubuntu for more details](https://docs.docker.com/engine/install/ubuntu/#install-using-the-repository)

1. 设置Docker apt仓库

```bash
# Add Docker's official GPG key:
sudo apt-get update
sudo apt-get install ca-certificates curl
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc

# Add the repository to Apt sources:
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "${UBUNTU_CODENAME:-$VERSION_CODENAME}") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update
```

2. 安装docker 其中只有docker engine, 不包含docker-compose

```bash
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```

3. 测试安装

```bash
sudo docker run hello-world
```

## docker-compose部署-Ubuntu

需要完成上面的前置步骤

[see Docker-compose docs for more details](https://docs.docker.com/compose/install/standalone/)

1. 下载和安装Docker Compose standalone

```bash
curl -SL https://github.com/docker/compose/releases/download/v2.35.0/docker-compose-linux-x86_64 -o /usr/local/bin/docker-compose
```

2. 添加可执行权限给docker-compose文件

```bash
chmod +x /usr/local/bin/docker-compose
```

3. 添加软链接

```bash
sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
```

## docker网络代理配置

只给bash配置了网络代理是不够的, docker有自己的网络设置, 不会自动使用系统或bash配置中的代理

1. 创建或编辑Docker配置文件 (我喜欢用vim)

```bash
sudo vim /etc/docker/daemon.json
```

2. 添加或修改配置

```bash
{
  "proxies": {
    "http-proxy": "http://proxy:port",
    "https-proxy": "http://proxy:port",
    "no-proxy": "localhost,127.0.0.1"
  }
}
```

3. 重启docker服务

```bash
sudo systemctl restart docker
```

## docker的一些简单的配置, 更方便的使用

[see linux-postinstall docs for more details](https://docs.docker.com/engine/install/linux-postinstall/)

### 如何避免每次都使用sudo

在用户中运行docker需要root权限, 需要sudo才能执行

1. 创建docker组

```bash
sudo groupadd docker
```

2. 添加当前用户到docker group中

```bash
sudo usermod -aG docker $USER
```

3. 激活对于组的变动

```bash
newgrp docker
```

4. 验证现在运行docker不需要sudo了

```bash
docker run hello-world
```

### 开机自启动

1. 开启docker自启动和container自启动

```bash
sudo systemctl enable docker.service
sudo systemctl enable containerd.service
```

2. 关闭自启动

```bash
sudo systemctl disable docker.service
sudo systemctl disable containerd.service
```

## 查看docke的配置信息

1. 总的信息

```bash
docker info
```

2. 代理信息

```bash
docker info | grep -i proxy
```





## potainer

一个docker的GUI管理界面

下载配置好docker engine以后

1. ​	拉取

```bash
docker pull portainer/portainer
```

2. 安装和启动 ( 阿里云的服务器还需要先执行 `docker volume crete portainer_data`)

```bash
docker run -d --restart=always --name portainer -p 9000:9000 -v /var/run/docker.sock:/var/run/docker.sock portainer/portainer
```

接下来就能通过服务器IP:9000访问portainer了 

# 文件权限

## 查看文件的权限

1. 查看当前文件夹下的所有文件的权限

```bash
ls -la
```

2. 查看某个文件的详细信息

```bash
stat <file-name>
```

> 文件权限信息解读

```bash
drwxr-xr-x 4 simon simon 4096 Apr 11 21:04 .
drwxr-xr-x 4 simon simon 4096 Apr 11 21:04 ..
-rw-r--r-- 1 simon simon 1460 Apr 11 21:27 docker-compose-app.yml
```

开头的第一个字符表明这是个目录文件还是个文件文件(d : 目录, - : 文件)

后面每三个字符为一组, 每组r : 读权限, w : 写权限, x : 执行权限, - : 说明这个权限没有

三组权限分别是 : 用户权限, 组权限, 其他用户的权限

第一个名称simon是文件所属的用户名称, 第二个是这个文件所属的组名称

## 修改文件的权限

不使用root用户登录的时候, 文件的权限问题还是挺让人恼火的

1. 修改文件的所有权

```bash
sudo chown user:group <file-name>
```

2. 更改文件的权限

```bash
sudo chmod 666
# 这个数字是权限的缩写, 第一个数字是user的权限
# 第二个数字就是group的权限, 第三个数字是others的权限
# 每组权限中
# r 表示读权限(read)，数值为4
# w 表示写权限(write)，数值为2
# x 表示执行权限(execute)，数值为1
# - 表示没有对应的权限
# 所以666代表的权限就是 rw-rw-rw
# 再比如 644 就是权限 rw-r--r--
```

3. 递归修改文件的权限

```bash
sudo chmod -R ...
```

# 网络配置

## 代理配置

1. 为当前bash配置代理

```bash
export http_proxy="http://IP:port"
export https_proxy="https://IP:port"
```

2. 将配置添加到配置文件中, 添加到~/.bashrc中即可
3. 检查配置是否生效

```bash
curl -v www.google.com
```

# Java运行环境配置

## Java JDK

1. 查看所有的apt缓存中的JDK版本

```bash
sudo apt-cache search jdk
```

2. 选个自己要的版本下载

```bash
sudo apt install openjdk-8-jdk -y
```

3. 配置下JAVA_HOME等信息

```bash
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
```

4. 检测安装

```bash
java --version
```



## maven

因为看了下apt中的maven倒也还挺新的, 就直接用的apt下载

1. 安装

```bash
sudo apt install maven -y
```

2. 检测安装

```bash
mvn --version
```







