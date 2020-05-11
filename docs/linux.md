### 一、生产环境服务器变慢，诊断思路和性能评估谈谈？

1.整机：top 查看系统的整机性能，cpu 内存 pid等信息  load average代表系统的负载均衡 ，三个值代表1分钟、5分钟、15分钟系统的平均负载值，三个值相加/100%>60%，说明系统负载中除top外，uptime查看整机性能的精简版

2.CPU：vmstat   vmstat -n 2 3

| 命令  | 说明                                |
| -----  | ------------------------------------|
|procs | r：运行和等待cpu时间片的进程数 b：等待资源的进程数 |
|Cpu | us+sy超过80%说明cpu负载过高，id代表cpu空闲率，越高越好 |

3.内存：free

| 命令  | 说明                                |
| -----  | ------------------------------------|
| free  | 查看内存，字节数  |
| free -g  | 四舍五入吞了一些内存数      |
| free -m   | 推荐使用，准确查看内存数   |
| pidstat -p 进程号 -r 2   | 查看额外   |

4.硬盘：df

> df -h	查看磁盘剩余空间数

5.磁盘IO：iostat

| 命令  | 说明                                |
| -----  | ------------------------------------|
|iostat -xdk 2 3 | 查看磁盘IO占用情况 |
|pidstat -d 2 -p 进程号 | 查看额外 |

![image](../assets/linux/磁盘IO.jpg)

6.网络IO：ifstat
> ifstat	查看网络IO，网络负载情况

### 二、假如生产环境出现cpu占用过高，谈谈分析思路和定位？

> - 先用top命令找出cpu占比最高的，记录pid
> - ps -ef | grep pid 或jps -l找出java的进程号
> - 定位到具体线程或代码
>> - Ps -mp 进程号 -o THREAD,tid,time：哪个线程占用了多少时间
>>> - -m显示所有的线程
>>> - -p pid进程使用cpu的时间
>>> - -o 该参数后是用户自定义格式
> - 将需要的线程ID转换为16进制格式（英文小写格式）： printf "%x\n" 有问题的pid对应的16进制的线程号
> - jstack pid | grep 16进制线程号 -A90

查看主机名
> hostname

修改主机名
> hostnamectl set-hostname test-131

修改host文件
> vi /etc/hosts

新增用户
> useradd orange -d /app

删除
> userdel orange

修改密码
> passwd orange

查看所有用户
> cat /etc/passwd

生成本地密钥
> ssh-keygen -t rsa -P ''

免密：
> for i in {131..134}; do ssh-copy-id -i ~/.ssh/id_rsa.pub hadoop@10.0.0.$i;done

jdk环境变量
> JAVA_HOME=/usr/java/jdk1.8.0_161
PATH=$JAVA_HOME/bin:$PATH
CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export JAVA_HOME
export PATH
export CLASSPATH

生效
> source /etc/profile

查看日志关键字
> cat stout.log | grep "开始推送"

修改文件权限
> chown orange file.txt

修改目录权限
> chown -R orange boot

查看文件所在位置：
> which virtualenvwrapper.sh

查看文件夹所在位置
> whereis zookeeper

排查磁盘不足问题
> 查看占用最大的目录,登录104，切换root用户,使用du -sh /* 查看哪个文件夹占用过大,发现/app 31G, cd /app, du -sh *

启动nginx：
> - start nginx
> - tasklist /fi "imagename eq nginx.exe"
> - taskkill /f /t /im nginx.exe

服务器之间互相拷贝文件：
> scp wommobile root@10.0.0.xx:/app/bigdata

拷贝文件夹
> scp -r wommobile root@10.0.0.xx:/app/bigdata

在Linux下拷贝文件夹的时候出现cp:omitting directory xxx的错误 
解决办法：使用递归拷贝，在cp命令后面加上 -r 参数。 -r 表示递归的意思。
> eg. cp -r wommobile /app/bigdata
























