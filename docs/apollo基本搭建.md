## _APOLLO分布式配置中心项目搭建_

一. 环境：

1. JAVA

> Apollo 服务端：1.8+
> Apollo 客户端：1.7+

2. MYSQL

> 5.6.5+

二. 执行sql脚本

1. 将scripts/docker-quick-start/sql目录下的apolloconfigdb.sql、apolloportaldb.sql脚本分别到在mysql中执行。

sql下载地址：
> https://github.com/ctripcorp/apollo/tree/master/scripts/docker-quick-start/sql

2. 执行成功验证语句：

> - apolloconfigdb: select `Id` , `Key` , `Value` , `Comment` from `ApolloConfigDB` . `ServerConfig` ; 
> - apolloportaldb: select `Id` , `Key` , `Value` , `Comment` from `ApolloPortalDB` . `ServerConfig` ; 

查询出表数据即成功

三. 部署方式

**注1：apollo-configservice和apollo-admin工程服务注册地址，如若未配置，则默认apollo自定义的eureka服务中心，需要自定义自己的eureka服务地址需修改ApolloConfigDB. ServerConfig中的eureka.service.url属性，修改为自定义的eureka服务地址即可**

**注3：只在数据库添加环境是不起作用的，还需要为apollo-portal和apollo-client添加新增环境对应的meta server地址，若对应不上，会有获取环境的错误**

1. 可通过直接下载\*\*-git.zip 安装包部署到服务器上，下载地址：https://github.com/ctripcorp/apollo/releases ， 主要安装包有三个：

> - apollo-adminservice-1.6.0-SNAPSHOT-github.zip , 解压并进入其 config 目录下修改 application-github.properties 文件，修改成具体的数据源配置
> - apollo-configservice-1.6.0-SNAPSHOT-github.zip (关键服务)，同上
> - apollo-portal-1.6.0-SNAPSHOT-github.zip (主要是 web 用户界面)，同上，且修改 apollo-env.properties 文件定义各个环境的 meta 地址(此地址为已注册在 eureka 上的 apollo-configservice 地址，端口号为 8080)

**注2：一套Portal可以管理多个环境，但是每个环境都需要独立部署一套Config Service、Admin Service和ApolloConfigDB**

2. 也可通过源码构建，下载地址：https://github.com/ctripcorp/apollo

> 本地构建方式：修改scripts目录下的build.sh文件

config的数据库配置：

> - apollo_config_db_url=jdbc:mysql://x.x.x.x:3306/ApolloConfigDB?characterEncoding=utf8
> - apollo_config_db_username=root
> - apollo_config_db_password=123456

admin的数据库配置

> - apollo_portal_db_url=jdbc:mysql://x.x.x.x:3306/ApolloPortalDB?characterEncoding=utf8
> - apollo_portal_db_username=root
> - apollo_portal_db_password=123456

> - 此meta地址为已注册到eureka上的apollo-configservice服务的地址
> - dev_meta=http://http://192.168.1.*:8090
> - fat_meta=http://http://192.168.1.*:8091
> - uat_meta=http://http://192.168.1.*:8091
> - pro_meta=http://http://192.168.1.*:8090

执行build.sh脚本至打包成功，到相关项目的各自target目录下找到apollo-configservice-1.6.0-SNAPSHOT-github.zip、apollo-adminservice-1.6.0-SNAPSHOT-github.zip、apollo-portal-1.6.0-SNAPSHOT-github.zip部署到相关服务器上依次启动即可。

**如果启动成功可登录http://192.168.x.x:8070/链接地址(即你所部署项目的ip:8070)登录apollo的web服务，账户密码为apollo/admin，添加配置详情参考：https://github.com/ctripcorp/apollo/wiki/Apollo%E9%85%8D%E7%BD%AE%E4%B8%AD%E5%BF%83%E4%BB%8B%E7%BB%8D**

四. APOLLO如需自定义化，可下载源码本地启动

1. 本地启动注意：找到上文所提到的三个关键的工程，yml文件分别添加数据源配置 (由于项目使用了数据源但却未定义，所以项目无法启动，必须先配置)
```xml
spring:
    datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/ApolloConfigDB?useSSL=false
    username: root
    password: 123456
```
2. 添加环境：修改ApolloPortalDB.ServerConfig中的apollo.portal.envs属性，重启即可在页面刷新看到

五、 相关服务使用apollo

1. 添加maven依赖
```xml
<dependency>
    <groupId>com.ctrip.framework.apollo</groupId>
    <artifactId>apollo-client</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>com.ctrip.framework.apollo</groupId>
    <artifactId>apollo-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

2. yml文件中配置：
```xml
app:
  id: bl-service
apollo:
  Meta: http://localhost:8080
  cacheDir: /opt/data/some-cache-dir
```
> app.id为apollo提供的用户中心所配置的项目信息，根据此app.id作为查询配置的标志
> apollo. Meta为注册在eureka服务的apollo-configService的地址(可根据环境切换不同的地址以获取不同环境的配置)

3. 启动类添加@EnableApolloConfig注解，通过@Value("${spring.datasource.url:jdbc:mysql://192.168.20.101:3306/ApolloConfigDB?characterEncoding=utf8}")获取相关配置值，: 后面的为默认值，用于在某些未设置属性的时候使用，若在apollo中设置了属性，在获取时会更新此默认值

### _六. 官网链接：https://github.com/ctripcorp/apollo/wiki (具体详情请参考官网)_

