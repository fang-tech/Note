# 配置镜像站

## 配置VPN

### 1. 配置X-UI

- 安装命令

  ```bash <(curl -Ls https://raw.githubusercontent.com/FranzKafkaYu/x-ui/956bf85bbac978d56c0e319c5fac2d6db7df9564/install.sh) 0.3.4.4```

- 设置账号密码, 端口

  ```账号 : func  密码: 362326122+fty  port: 10800```

- 启动浏览器, 登录面板

### 2.1 搭建站点 (vless+vision)

- 协议选择 vless

- 添加用户 -> flow选择vision

- 将域名2.59.151.254 -> 2-59-151-254.nip.io

- ```bash
  #安装证书工具：
  curl https://get.acme.sh | sh; apt install socat -y || yum install socat -y; ~/.acme.sh/acme.sh --set-default-ca --server letsencrypt
  
  #三种方式任选其中一种，申请失败则更换方式
  #申请证书方式1： 
  ~/.acme.sh/acme.sh  --issue -d 你的域名 --standalone -k ec-256 --force --insecure
  #申请证书方式2： 
  ~/.acme.sh/acme.sh --register-account -m "${RANDOM}@chacuo.net" --server buypass --force --insecure && ~/.acme.sh/acme.sh  --issue -d 你的域名 --standalone -k ec-256 --force --insecure --server buypass
  #申请证书方式3： 
  ~/.acme.sh/acme.sh --register-account -m "${RANDOM}@chacuo.net" --server zerossl --force --insecure && ~/.acme.sh/acme.sh  --issue -d 你的域名 --standalone -k ec-256 --force --insecure --server zerossl
  
  #安装证书：
  ~/.acme.sh/acme.sh --install-cert -d 你的域名 --ecc --key-file /etc/x-ui/server.key --fullchain-file /etc/x-ui/server.crt
  ``

- 复制路径/etc/x-ui/server.key到浏览器中的x-ui的公钥公文中, /etc/x-ui/server.crt到密钥路径中

- ```
  [Mon Jul 29 03:21:17 PM UTC 2024] Installing key to: /etc/x-ui/server.key
  [Mon Jul 29 03:21:17 PM UTC 2024] Installing full chain to: /etc/x-ui/server.crt
  ```

- 配置完成, 复制url,  在v2rayn中导入, 测试延迟, 配置成功

- 莫名出现了问题, 怀疑式v2rayN的问题


## 2.2 搭建站点 (vmess)

- 协议选择vmess
- 添加用户, 将id的第一段复制到路径
- 网络选择ws
- 配置成功

### x-ui访问网址[http://2.59.151.254:10800/ZnUD/x-ui](http://2.59.151.254:10800/ZnUD)



## 配置Nginx反向代理

### 安装Nginx

1. 安装Ngixn

   ```bash
   sudo apt install nginx
   ```

2. 创建新的配置文件
   ```
   sudo nano /etc/nginx/sites-available/your_domain
   ```


3. 反向代理配置失败, 未完全配置完, 其中碰到的问题有
   - Claude对代理有限制
   - 对话式ai需要有额外的配置
   - 未系统学习Nginx