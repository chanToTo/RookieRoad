### 一、说一下HashMap1.7和1.8
> - 1.7：数组+链表，插入元素使用头插法，rehash的时候容易造成死循环
> - 1.8：数组+链表+红黑树，插入元素使用尾插法
### 二、为什么HashMap1.8要加入红黑树`
> - 防止链表过长检索耗费时间，链表的时间复杂度为O(N)，红黑树的时间复杂度为O(log n)
### 三、HashMap的寻址公式
```shell script
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}

# h >>> 16 是 h 右移 16 位，因为 int 是 4 字节，32 位，所以右移 16 位后变成：左边 16 个 0 + 右边原 h 的高 16 位；
# 最后把这两个进行异或返回
# 异或：二进制位运算。如果一样返回 0，不一样则返回 1。
```
> - table.length - 1 & hash值
> - 哈希表长度 length 为 2 的整次幂时， hash & (length - 1) 的计算结果跟 hash % length 一样，而且效率还更好。
### 四、HashMap为什么不直接用 hashCode() 而是用它的高 16 位进行异或计算新 hash 值
> int 类型占 32 位，可以表示 2^32 种数（范围：-2^31 到 2^31-1），而哈希表长度一般不大，在 HashMap 中哈希表的初始化长度是 16（HashMap 中的 DEFAULT_INITIAL_CAPACITY），
> 如果直接用 hashCode 来寻址，那么相当于只有低 4 位有效，其他高位不会有影响。这样假如几个 hashCode 分别是 2^10、2^20、2^30，那么寻址结果 index 就会一样而发生冲突，所以哈希表就不均匀分布了
> 为了减少这种冲突，HashMap 中让 hashCode 的高位也参与了寻址计算（进行扰动），即把 hashCode 高 16 位与 hashCode 进行异或算出 hash，然后根据 hash 来做寻址。
### 五、单例模式用的哪种，使用volatile的原因，如何防止指令重排
```shell script
public class DoubleCheckSingleton {
    private volatile static DoubleCheckSingleton doubleCheckSingleton;

    private DoubleCheckSingleton() {
    }
    public static DoubleCheckSingleton getInstance(){
        if(doubleCheckSingleton == null){
            synchronized (DoubleCheckSingleton.class){
                if(doubleCheckSingleton == null){
                    doubleCheckSingleton = new DoubleCheckSingleton();
                }
            }
        }
        return doubleCheckSingleton;
    }
}
```
> - 双重检查、防止指令重排、内存读写屏障

内存读写屏障：
> - LoadLoad屏障：对于这样的语句Load1; LoadLoad; Load2， 在Load2及后续读取操作要读取的数据被访问前，保证Load1要读取的数据被读取完毕。
> - StoreStore屏障：对于这样的语句Store1; StoreStore; Store2， 在Store2及后续写入操作执行前，保证Store1的写入操作对其它处理器可见。
> - LoadStore屏障：对于这样的语句Load1; LoadStore; Store2， 在Store2及后续写入操作被刷出前，保证Load1要读取的数据被读取完毕。
> - StoreLoad屏障：对于这样的语句Store1; StoreLoad; Load2， 在Load2及后续所有读取操作执行前，保证Store1的写入对所有处理器可见。
### 六、还了解哪些设计模式（代理、工厂、装饰器、观察者）
### 七、spring中BeanFactory和ApplicationContext的区别（BeanFactory获取bean使用的是懒加载模式，App功能较多，国际化...）
> - BeanFactory在启动的时候不会去实例化Bean，中有从容器中拿Bean的时候才会去实例化。ApplicationContext在启动的时候就把所有的Bean全部实例化了。它还可以为Bean配置lazy-init=true来让Bean延迟实例化
> - 应用上下文，继承BeanFactory接口，它是Spring的一各更高级的容器，提供了更多的有用的功能，eg.  国际化（MessageSource）、访问资源，如URL和文件（ResourceLoader）、载入多个（有继承关系）上下文 ，使得每一个上下文都专注于一个特定的层次，比如应用的web层、消息发送、响应机制（ApplicationEventPublisher）、AOP（拦截器）
### 八、Object有哪些方法（toString、wait、notify、notifyAll、getClass、clone、hashcode、equals）
### 九、在什么情况下使用wait和notify，需要注意什么，一定要加锁吗（加锁synchronized使用，wait和notify配套使用，这两个方法都属于object的，参考https://zhuanlan.zhihu.com/p/76625784）
### 十、说说JVM的内存分布（方法区、堆、java栈、本地方法栈、程序计数器）
### 十一、方法区中除了存对象的一些常量还存了什么（string,对象的常量）
### 十二、说说以上内存结构中的线程公有和线程私有的理解（对象内的全局变量线程公有，方法里的局部变量线程私有）
### 十三、说说动态代理是怎么实现的
> - 在运行过程中通过改变类的字节码动态的生成新的代理对象
### 十四、zookeeper的选举机制
> 每个节点自己有一个timeout，在竞选leader的时候，timeout先结束的节点就将自己设置candidate状态，然后成为Leader

> Leader选举是保证分布式数据一致性的关键所在。当Zookeeper集群中的一台服务器出现以下两种情况之一时，需要进入Leader选举
>> - 服务器初始化启动
>> - 服务器运行期间无法和Leader保持连接

1.服务器初始化启动
> 若进行Leader选举，则至少需要两台机器，这里选取3台机器组成的服务器集群为例。在集群初始化阶段，当有一台服务器Server1启动时，其单独无法进行和完成Leader选举，当第二台服务器Server2启动时，此时两台机器可以相互通信，每台机器都试图找到Leader，于是进入Leader选举过程，选举过程如下：
> - 每个Server发出一个投票。由于是初始情况，Server1和Server2都会将自己作为Leader服务器来进行投票，每次投票会包含所推举的服务器的myid和ZXID，使用(myid, ZXID)来表示，此时Server1的投票为(1, 0)，Server2的投票为(2, 0)，然后各自将这个投票发给集群中其他机器
> - 接受来自各个服务器的投票。集群的每个服务器收到投票后，首先判断该投票的有效性，如检查是否是本轮投票、是否来自LOOKING状态的服务器
> - 处理投票。针对每一个投票，服务器都需要将别人的投票和自己的投票进行PK，PK规则如下
>> - 优先检查ZXID。ZXID比较大的服务器优先作为Leader
>> - 如果ZXID相同，那么就比较myid。myid较大的服务器作为Leader服务器
> - 统计投票。每次投票后，服务器都会统计投票信息，判断是否已经有过半机器接受到相同的投票信息，对于Server1、Server2而言，都统计出集群中已经有两台机器接受了(2, 0)的投票信息，此时便认为已经选出了Leader
> - 改变服务器状态。一旦确定了Leader，每个服务器就会更新自己的状态，如果是Follower，那么就变更为FOLLOWING，如果是Leader，就变更为LEADING

2.服务器运行时期的Leader选举
> 在Zookeeper运行期间，Leader与非Leader服务器各司其职，即便当有非Leader服务器宕机或新加入，此时也不会影响Leader，但是一旦Leader服务器挂了，那么整个集群将暂停对外服务，进入新一轮Leader选举，其过程和启动时期的Leader选举过程基本一致。假设正在运行的有Server1、Server2、Server3三台服务器，当前Leader是Server2，若某一时刻Leader挂了，此时便开始Leader选举。选举过程如下:
> - 变更状态。Leader挂后，余下的非Observer服务器都会讲自己的服务器状态变更为LOOKING，然后开始进入Leader选举过程
> - 每个Server会发出一个投票。在运行期间，每个服务器上的ZXID可能不同，此时假定Server1的ZXID为123，Server3的ZXID为122；在第一轮投票中，Server1和Server3都会投自己，产生投票(1, 123)，(3, 122)，然后各自将投票发送给集群中所有机器
> - 接收来自各个服务器的投票。与启动时过程相同
> - 处理投票。与启动时过程相同，此时，Server1将会成为Leader
> - 统计投票。与启动时过程相同
> - 改变服务器的状态。与启动时过程相同

### 十五、git fetch和git pull命令之间的区别？
> git fetch branch是把名为branch的远程分支拉取到本地，git pull = fetch + merge (合并并拉取)
### 十六、git merge和git rebase的区别
> - git merge branch会把branch分支的差异内容pull到本地，然后与本地分支的内容一并形成一个committer对象提交到主分支上，合并后的分支与主分支一致
> - git rebase branch会把branch分支优先合并到主分支，然后把本地分支的commit放到主分支后面，合并后的分支就好像从合并后主分支又拉了一个分支一样，本地分支本身不会保留提交历史
### 十七、eureka和zookeeper注册中心有哪些区别（AP CP  zookeeper选举）
> CAP理论：C（一致性）、A（可用性）、P（分区容错性）

Zookeeper保证CP：
> - 当向注册中心查询服务列表时，我们可以容忍注册中心返回的是几分钟以前的注册信息，但不能接受服务直接down掉不可用。也就是说，服务注册功能对可用性的要求要高于一致性。但是zk会出现这样一种情况，当master节点因为网络故障与其他节点失去联系时，剩余节点会重新进行leader选举。问题在于，选举leader的时间太长，30 ~ 120s, 且选举期间整个zk集群都是不可用的，这就导致在选举期间注册服务瘫痪。在云部署的环境下，因网络问题使得zk集群失去master节点是较大概率会发生的事，虽然服务能够最终恢复，但是漫长的选举时间导致的注册长期不可用是不能容忍的。

Eureka保证AP
> - Eureka在设计时就优先保证可用性。Eureka各个节点都是平等的，几个节点挂掉不会影响正常节点的工作，剩余的节点依然可以提供注册和查询服务。而Eureka的客户端在向某个Eureka注册或时如果发现连接失败，则会自动切换至其它节点，只要有一台Eureka还在，就能保证注册服务可用(保证可用性)，只不过查到的信息可能不是最新的(不保证强一致性)。除此之外，Eureka还有一种自我保护机制，如果在15分钟内超过85%的节点都没有正常的心跳，那么Eureka就认为客户端与注册中心出现了网络故障，此时会出现以下几种情况：
>> - Eureka不再从注册列表中移除因为长时间没收到心跳而应该过期的服务
>> -  Eureka仍然能够接受新服务的注册和查询请求，但是不会被同步到其它节点上(即保证当前节点依然可用)
>> -  当网络稳定时，当前实例新的注册信息会被同步到其它节点中

总结：
> 因此， Eureka可以很好的应对因网络故障导致部分节点失去联系的情况，而不会像zookeeper那样使整个注册服务瘫痪。

### 十八、HTTP与TCP的区别和联系

参考：https://www.cnblogs.com/baizhanshi/p/8482612.html

TCP三次握手：
> - 第一次握手：客户端发送syn包(syn=j)到服务器，并进入SYN_SEND状态，等待服务器确认
> - 第二次握手：服务器收到syn包，必须确认客户的SYN（ack=j+1），同时自己也发送一个SYN包（syn=k），即SYN+ACK包，此时服务器进入SYN_RECV状态
> - 第三次握手：客户端收到服务器的SYN＋ACK包，向服务器发送确认包ACK(ack=k+1)，此包发送完毕，客户端和服务器进入ESTABLISHED状态，完成三次握手

TCP四次挥手：
> - 第一次挥手：A数据传输完毕需要断开连接，A的应用进程向其TCP发出连接释放报文段（FIN = 1,序号seq = u）,并停止再发送数据，主动关闭TCP连接，进入FIN-WAIT-1状态，等待B的确认
> - 第二次挥手：B收到连接释放报文段后即发出确认报文段（ACK=1，确认号ack=u+1,序号seq=v）,B进入CLOSE-WAIT关闭等待状态,此时的TCP处于半关闭状态，A到B的连接释放。而A收到B的确认后，进入FIN-WAIT-2状态，等待B发出的连接释放报文段
> - 第三次挥手：当B数据传输完毕后，B发出连接释放报文段（FIN = 1，ACK = 1，序号seq = w,确认号ack=u+1）,B进入LAST-ACK（最后确认）状态，等待A 的最后确认
> - 第四次挥手：A收到B的连接释放报文段后，对此发出确认报文段（ACK = 1，seq=u+1，ack=w+1）,A进入TIME-WAIT（时间等待）状态。此时TCP未释放掉，需要经过时间等待计时器设置的时间2MSL后，A才进入CLOSE状态

HTTP连接：
> HTTP协议是建立在TCP协议之上的一种应用， HTTP连接最显著的特点是客户端发送的每次请求都需要服务器回送响应，在请求结束后，会主动释放连接。从建立连接到关闭连接的过程称为“一次连接”
> - 在HTTP 1.0中，客户端的每次请求都要求建立一次单独的连接，在处理完本次请求后，就自动释放连接
> - 在HTTP 1.1中则可以在一次连接中处理多个请求，并且多个请求可以重叠进行，不需要等待一个请求结束后再发送下一个请求。

HTTP和TCP的区别：
> - TCP位于传输层，HTTP位于应用层，HTTP是基于TCP基础的，TCP就是单纯建立连接，不涉及任何请求的实际数据，只是简单的传输，HTTP是用来收发数据的

### 十九、了解saas的多租户模式吗
### 二十、synchronized和reentrantLock的区别

> - synchronized是java中的关键字，实现是JVM层面的，reentrantLock实现是api层面的
> - synchronized是非公平锁，reentrantLock可公平、可非公平
> - synchronized不可中断，除非遇到异常，reentrantLock可中断，使用tryLock

### 二十一、reentrantLock的底层实现原理、是怎么样判断拿不拿锁的、公平和非公平锁的体现（状态，AQS队列）

### 二十二、了解juc下的哪些类
> - Atomic、concurrentHashMap、ReentrantLock
### 二十三、Atomic是怎么实现的
### 二十四、concurrentHashMap1.7和1.8的区别，为什么要cas+synchronized，目的在哪（cas预估值为空）
### 二十五、http协议的线程模型
### 二十六、了解tomcat哪些内容
### 二十七、spring bean的生命周期
### 二十八、如果我需要对bean做一些自定义操作应该如何实现
### 二十九、spring加注解是如何实现加了注解就有用的
### 三十、aop有哪些动态代理，如何实现？你要看他怎么实现的如何看（字节码）
### 三十一、G1、cms垃圾收集器的实现以及区别，适用场景（内存大使用G1）
### 三十二、如何处理分布式事务
### 三十三、导致系统出现死锁的情况

死锁的出现需要同时满足以下四个条件：
> - 互斥（Mutual Exclusion）：一次只能有一个进程使用资源。如果另一个进程请求该资源，则必须延迟请求进程，直到释放该资源为止
> - 保持并等待（Hold and wait）：必须存在一个进程，该进程至少持有一个资源，并且正在等待获取其他进程当前所持有的资源
> - 无抢占（No Preemption）：资源不能被抢占，也就是说，在进程完成其他任务之后，只能由拥有他的进程自动释放资源
> - 循环等待（Circular Wait）：必须存在一组{p0，p1...pn}的等待进程，使p0等待p1持有的资源，p1等待由p2持有的资源，pn-1正在等待由pn持有的资源，而pn正在等待由p0持有的资源


