#JGET

###本地离线下载服务
>JGET 的作用就是一个离线下载的程序, 当下载一个非常大的文件时候, 而且本地的网络又很慢, 这时候离线下载
>就能发挥作用, 各提供离线下载的服务商的做法是将这个大的文件离线到自己的服务器, 然后在从服务商的服务器进行下载;
>而 JGET 并非如此, 是直接离线到本地.
    
###准备
>需要有一台长开的电脑(例如树莓派), 来部署下载程序, 然后在 JGET 的网页中添加这台机器(事实上也无需在网页中添加,
    在下载程序中配置好机器名, JGET 会自动注册这台机器), 之后就能添加下载的任务了;
>目前添加的任务仅支持 TCP 链接, 也就是通常浏览器中看到的 URL, 后期考虑支持种子以及磁力链接.

###安装
>JGET 是使用 JAVA 语言开发, 因此若要成功安装, 需要先安装 JAVA 运行环境 (JRE), <a href="http://java.com" target="_blank">JAVA 官网</a>
>JAVA 环境安装完毕后就可以进行部署 JGET.
>如果你是一名开发着, JGET 是开放程式, 可以使用 GIT 检出代码编译

```language=base
~$ git clone http://xx
~$ cd jget
~$ mvn clean package
```

>打包后会有 jget-download/target/jget-download*.tar.gz 这个文件, 这个便是下载程式.
>之后解压这个压缩包会有三个目录  bin conf lib
>bin 目录下存放的便是启动程序
>conf 存放的是配置文件
>lib 资料
>启动程序执行 bin/jget.sh start 即可, 可选 stop 停止
    
```
~$ bin/jget.sh start
```

###配置
>下载程序的配置文件在 conf/conf.properties, 详细说明如下:

```
##### 这是 JGET 的相关信息无需关注
jget.version = 1.0
jget.dev = true

### 必须配置, 否则 JGET 无法获取到下载任务
### 配置这个下载机所使用的 WEB 任务管理信息
# 是否 SSL 协议
jget.server.ssl = false
# 服务器域名 不填写默认 localhost
jget.server.host = localhost
# 服务器端口 不填写默认 80
jget.server.port = 8120
# 服务所在的路径, 默认是空表示在根目录
jget.server.basepath =

### 必填 账户配置 这个很重要
# web 服务中注册的账户
jget.account.name = test
# web 服务中账户的密码
jget.account.passwd = 0

#### 下载机名 如果不配置默认将获取机器名称
jget.cli.name = Test

##### 程序执行的相关配置 通常无需更改, 设置的过高会影响效能, 这里看到的值都是默认配置
# 客户机 ip 更新间隔时间 单位 小时
jget.interval.cli_ip = 1
# 账户连接更新间隔时间 单位 天
jget.interval.account_conn = 10
# 客户机心跳间隔时间 单位 秒
jget.interval.cli_heartbeat = 60
# 任务读取间隔 单位 分
jget.interval.job = 2
# 同时下载的最大数量, 不建议设置的过高, 不填写默认是 CPU 数的 2 倍
jget.thread.job.max_num = 8

### 下载文件保存的路径 \${user.home} 表示当前系统登入用户的主目录
jget.download.path = \${user.home}/jget
```

        