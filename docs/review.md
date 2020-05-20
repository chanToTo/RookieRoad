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

参考：https://www.cnblogs.com/weiqihome/p/9665718.html

AQS：
> - AbstarctQueuedSynchronizer简称AQS，是一个用于构建锁和同步容器的框架。事实上concurrent包内许多类都是基于AQS构建的，例如ReentrantLock，Semphore，CountDownLatch，ReentrantReadWriteLock，FutureTask等。AQS解决了在实现同步容器时大量的细节问题
> - AQS使用一个FIFO队列表示排队等待锁的线程，队列头结点称作“哨兵节点”或者“哑结点”，它不与任何线程关联。其他的节点与等待线程关联，每个阶段维护一个等待状态waitStatus
> - AQS中还有一个表示状态的字段state，例如ReentrantLock用它来表示线程重入锁的次数，Semphore用它表示剩余的许可数量，FutureTask用它表示任务的状态。对state变量值的更新都采用CAS操作保证更新操作的原子性
> - AbstractQueuedSynchronized继承了AbstractOwnableSynchronized，这个类只有一个变量：exclusiveOwnerThread，表示当前占用该锁的线程，并且提供了相应的get，set方法

ReentrantLock：

非公平锁：
```shell script
final void lock() {
    if (compareAndSetState(0, 1))
        setExclusiveOwnerThread(Thread.currentThread());
    else
        acquire(1);
}
# CAS操作，判断如果state为0，则表示当前锁没有被占有，如果是0则把它置为1，并且设置当前线程为该锁的独占线程，表示获取锁成功。
# “非公平”即体现在这里，如果占用锁的线程刚释放锁，state置为0，而排队等待锁的线程还未唤醒时，新来的线程就直接抢占了该锁，那么就“插队”了。
```

公平锁：
```shell script
final void lock() {
    acquire(1);
}
# 公平锁，直接入队列
```

### 二十二、了解juc下的哪些类
> Atomic、ConcurrentHashMap、ReentrantLock
> - Atomic包下的实现使用CAS
> - ConcurrentHashMap使用CAS+Synchronized
> - ReentrantLock实现（公平锁/非公平锁、state、cas）
### 二十三、ConcurrentHashMap1.7和1.8的区别，为什么要cas+synchronized，目的在哪（cas预估值为空）

ConcurrentHashMap 1.7：
> 使用分段锁机制，采用Segment + HashEntry的方式进行实现，分为16个segment并加上ReentrantLock

ConcurrentHashMap 1.8
> 放弃了Segment臃肿的设计，取而代之的是采用Node + CAS + Synchronized来保证并发安全进行实现
> - 使用cas传入预估值为null，如果节点为null则证明当前位置还没有被占有，就设置值
> - 在cas失败后，cas升级为synchronized

> 如果在put元素的时候，当前put的node一直都是null的情况下，就会一直使用cas去做put操作，cas不会升级为synchronized重量级锁的情况下又能实现并发安全的添加数据，性能消耗低

ConcurrentHashMap1.8 put过程：
> - 如果没有初始化就先调用initTable（）方法来进行初始化过程
> - 如果没有hash冲突就直接CAS插入
> - 如果还在进行扩容操作就先进行扩容
> - 如果存在hash冲突，就加锁来保证线程安全，这里有两种情况，一种是链表形式就直接遍历到尾端插入，一种是红黑树就按照红黑树结构插入
> - 最后一个如果该链表的数量大于阈值8并且整个table的元素个数达到64个，就要先转换成黑红树的结构，break再一次进入循环(阿里面试官问题，默认的链表大小，超过了这个值就会转换为红黑树)
> - 如果添加成功就调用addCount（）方法统计size，并且检查是否需要扩容

### 二十四、http协议的线程模型
### 二十五、了解tomcat哪些内容

Tomcat Server处理一个HTTP请求的过程：
> - 用户点击网页内容，请求被发送到本机端口8080，被在那里监听的Coyote HTTP/1.1 Connector获得
> - Connector把该请求交给它所在的Service的Engine来处理，并等待Engine的回应
> - Engine获得请求localhost/test/index.jsp，匹配所有的虚拟主机Host
> - Engine匹配到名为localhost的Host（即使匹配不到也把请求交给该Host处理，因为该Host被定义为该Engine的默认主机），名为localhost的Host获得请求/test/index.jsp，匹配它所拥有的所有的Context。Host匹配到路径为/test的Context（如果匹配不到就把该请求交给路径名为“ ”的Context去处理）
> - path=“/test”的Context获得请求/index.jsp，在它的mapping table中寻找出对应的Servlet。Context匹配到URL PATTERN为*.jsp的Servlet,对应于JspServlet类
> - 构造HttpServletRequest对象和HttpServletResponse对象，作为参数调用JspServlet的doGet（）或doPost（）.执行业务逻辑、数据存储等程序
> - Context把执行完之后的HttpServletResponse对象返回给Host
> - Host把HttpServletResponse对象返回给Engine
> - Engine把HttpServletResponse对象返回Connector
> - Connector把HttpServletResponse对象返回给客户Browser

### 二十六、spring bean的生命周期
> - 实例化bean：对于BeanFactory容器，当客户向容器请求一个尚未初始化的bean时，或初始化bean的时候需要注入另一个尚未初始化的依赖时，容器就会调用createBean进行实例化。对于ApplicationContext容器，当容器启动结束后，通过获取BeanDefinition对象中的信息，实例化所有的bean
> - 设置对象属性（依赖注入）：实例化后的对象被封装在BeanWrapper对象中，紧接着，Spring根据BeanDefinition中的信息 以及 通过BeanWrapper提供的设置属性的接口完成依赖注入。
> - 处理Aware接口：Spring会检测该对象是否实现了xxxAware接口，并将相关的xxxAware实例注入给Bean：
>> - 如果这个Bean已经实现了BeanNameAware接口，会调用它实现的setBeanName(String beanId)方法，此处传递的就是Spring配置文件中Bean的id值
>> - 如果这个Bean已经实现了BeanFactoryAware接口，会调用它实现的setBeanFactory()方法，传递的是Spring工厂自身
>> - 如果这个Bean已经实现了ApplicationContextAware接口，会调用setApplicationContext(ApplicationContext)方法，传入Spring上下文
> - BeanPostProcessor：如果想对Bean进行一些自定义的处理，那么可以让Bean实现了BeanPostProcessor接口，那将会调用postProcessBeforeInitialization(Object obj, String s)方法
> - InitializingBean 与 init-method：如果Bean在Spring配置文件中配置了 init-method 属性，则会自动调用其配置的初始化方法
> - 如果这个Bean实现了BeanPostProcessor接口，将会调用postProcessAfterInitialization(Object obj, String s)方法；由于这个方法是在Bean初始化结束时调用的，所以可以被应用于内存或缓存技术，以上步骤完成后，Bean就已经被正确创建了，之后就可以使用这个Bean了
> - DisposableBean：当Bean不再需要时，会经过清理阶段，如果Bean实现了DisposableBean这个接口，会调用其实现的destroy()方法
> - destroy-method：最后，如果这个Bean的Spring配置中配置了destroy-method属性，会自动调用其配置的销毁方法
### 二十七、如果我需要对bean做一些自定义操作应该如何实现
### 二十八、spring加注解是如何实现加了注解就有用的
### 二十九、aop有哪些动态代理，如何实现？你要看他怎么实现的如何看（字节码）
> - JDK动态代理，基于接口InvocationHandler的invoke方法，使用反射原理
> - cglib动态代理，动态的生成一个使用类的子类作为代理对象
### 三十、G1、cms垃圾收集器的实现以及区别，适用场景（内存大使用G1）

G1：
> 划分多个region区，做分代垃圾回收（含新生代和老年代）。更适用于内存大的机器

CMS：
> 老年代的垃圾收集器，适用于内存相对小的机器（和G1相比较）

### 三十一、如何处理分布式事务
### 三十二、导致系统出现死锁的情况

死锁的出现需要同时满足以下四个条件：
> - 互斥（Mutual Exclusion）：一次只能有一个进程使用资源。如果另一个进程请求该资源，则必须延迟请求进程，直到释放该资源为止
> - 保持并等待（Hold and wait）：必须存在一个进程，该进程至少持有一个资源，并且正在等待获取其他进程当前所持有的资源
> - 无抢占（No Preemption）：资源不能被抢占，也就是说，在进程完成其他任务之后，只能由拥有他的进程自动释放资源
> - 循环等待（Circular Wait）：必须存在一组{p0，p1...pn}的等待进程，使p0等待p1持有的资源，p1等待由p2持有的资源，pn-1正在等待由pn持有的资源，而pn正在等待由p0持有的资源

### 三十三、threadLocal怎么导致内存泄露
> threadLocal里面使用了一个存在弱引用的map(ThreadLocalMap),当释放掉threadLocal的强引用以后,map里面的value却没有被回收.而这块value永远不会被访问到了. 所以存在着内存泄露. 最好的做法是将调用threadLocal的remove方法

> 每个thread中都存在一个map, map的类型是ThreadLocal.ThreadLocalMap. Map中的key为一个threadlocal实例. 这个Map的确使用了弱引用,不过弱引用只是针对key. 每个key都弱引用指向threadlocal. 当把threadlocal实例置为null以后,没有任何强引用指向threadlocal实例,所以threadlocal将会被gc回收. 但是,我们的value却不能回收,因为存在一条从current thread连接过来的强引用. 只有当前thread结束以后, current thread就不会存在栈中,强引用断开, Current Thread, Map, value将全部被GC回收

总结：
> 只要这个线程对象被gc回收，那就不会发生内存泄漏，但在threadLocal设为null和线程结束这段时间不会被回收的，就发生了我们认为的内存泄露，如果线程对象不被回收的情况下，这就发生了真正意义上的内存泄露，比如使用线程池的时候，线程结束是不会销毁的，会再次使用的。就可能出现内存泄露
### 三十四、面向对象设计的原则
> - 单一职责原则：对一个类而言，有且仅有一个引起它变化的原因。否则的话就应该把这个类进行拆分。在设计时让一个类只负责一种类型的责任（**单一职责原则的核心就是控制类的粒度大小、将对象解耦、提高内聚性**）
> - 开放-封闭原则：对修改关闭，对扩展开放（**开闭原则是面向对象程序设计的终极目标，它使软件实体拥有一定的适应性和灵活性的同时具备稳定性和延续性**）
> - 里氏替换原则：子类型必须能够替换掉他们的基类型。即，在任何父类可以出现的地方，都可以用子类的实例来赋值给父类型的引用。当一个子类的实例应该能够替换任何其超类的实例时，它们之间才具有是一个（is-a）关系（**子类可以扩展父类的功能，但不能改变父类原有的功能**）
> - 依赖倒置原则：抽象不应该依赖于细节，而细节应该依赖于抽象。即高层模块不应该依赖于底层模块，二者都应该依赖于抽象（**要面向接口编程，不要面向实现编程**）
> - 接口分离原则：依赖于抽象而不依赖于具体，同时在抽象级别不应该有对于细节的依赖(**不应该强迫客户依赖于他们不用的方法。接口属于客户，不属于它所在的类层次结构**）
> - 最少知识原则：一个实体应当尽量少的与其他实体之间发生相互作用，使得系统功能模块相对独立（**低耦合、高内聚**）
### java面向对象类与类/类与对象之间有哪些关系
> - 继承：指的是一个类（称为子类、子接口）继承另外的一个类（称为父类、父接口）的功能，并可以增加它自己的新功能的能力，继承是类与类或者接口与接口之间最常见的关系
> - 实现：指的是一个class类实现interface接口（可以是多个）的功能；实现是类与接口之间最常见的关系
> - 依赖：一个类A使用到了另一个类B，而这种使用关系是具有偶然性的、、临时性的、非常弱的，但是B类的变化会影响到A
> - 关联：体现的是两个类、或者类与接口之间语义级别的一种强依赖关系，比如我和我的朋友；这种关系比依赖更强、不存在依赖关系的偶然性、关系也不是临时性的，一般是长期性的，而且双方的关系一般是平等的、关联可以是单向、双向的；表现在代码层面，为被关联类B以类属性的形式出现在关联类A中，也可能是关联类A引用了一个类型为被关联类B的全局变量
> - 聚合：聚合是关联关系的一种特例，他体现的是整体与部分、拥有的关系，即has-a的关系，此时整体与部分之间是可分离的，他们可以具有各自的生命周期，部分可以属于多个整体对象，也可以为多个整体对象共享
> - 组合：组合也是关联关系的一种特例，他体现的是一种contains-a的关系，这种关系比聚合更强，也称为强聚合；他同样体现整体与部分间的关系，但此时整体与部分是不可分的，整体的生命周期结束也就意味着部分的生命周期结束
### redis的事务如何实现
> MULTI开启、EXEC执行、DISCARD中断、WATCH监控key
### mysql有哪些索引
> - 普通索引：最基本的索引，它没有任何限制
> - 唯一索引：与前面的普通索引类似，不同的就是：索引列的值必须唯一，但允许有空值。如果是组合索引，则列值的组合必须唯一
> - 主键索引：是一种特殊的唯一索引，一个表只能有一个主键，不允许有空值
> - 组合索引：指多个字段上创建的索引，只有在查询条件中使用了创建索引时的第一个字段，索引才会被使用。使用组合索引时遵循最左前缀集合
> - 全文索引：主要用来查找文本中的关键字，而不是直接与索引中的值相比较。fulltext索引跟其它索引大不相同，它更像是一个搜索引擎，而不是简单的where语句的参数匹配
### 查看执行计划时，有哪些字段是重要，需要重点关注的
执行计划显示结果的分析：
> - table：此次查询设计到的表
> - type：查询类型（全表扫描ALL 或 索引扫描index < range < ref < eq_ref < const(system)）（重）
> - possible_keys：可能用到的索引 （重）
> - key：最后选择的索引 （重）
> - key_len：索引的覆盖长度（主要用于评估联合索引）
> - rows：此次查询需要扫描的行数（重，如果在数据量很大的情况下，不管有没有索引，都会导致查询效率变慢，那么评估一个索引高效与否则是看扫描出来了多少行）
> - Extra：额外的信息

type索引扫描index < range < ref < eq_ref < const(system)
> - index：全索引扫描，需要扫描整颗索引树，eg. desc select countrycode from world.city
> - range：索引范围查询，> < >= <= like in or between and（like例外说明匹配最左前缀索引，不可'%ch%,可'ch%'），eg. desc select * from city where id < 10 或 desc select * from city where countrycode like 'CH%'
> - ref：辅助索引的等值查询，eg. desc select * from city where countrycode='CHN'
> - eq_ref：针对多表连接中，非驱动表连接条件是主键或唯一键，eg. desc select country.name,city.name from city join country on city.countrycode = country.code where city.population='CHN'（country为非驱动表，country.code中的code为country表中的唯一键）
> - const(或system)：聚簇索引等值查询，eg. desc select * from city where id=10 (id为主键)

### 查询第k大的值 算法
### dubbo的使用原理
### 如果使用dubbo的情况下，有很多服务，消费者是怎么样辨别我要调用哪台服务的（节点）
### 一个完整的http请求到拿到数据会经过哪些过程

参考：https://blog.csdn.net/u014600626/article/details/78720763

> Http 的header会给我们的请求包装，比如AF中经常设置的可接受的Accept（text/html） --》域名解析，根据域名找到服务器的IP --> 发起TCP的3次握手 --> 建立TCP连接后发起http请求 --> 服务器响应http请求，浏览器得到html代码 --> 浏览器解析html代码，并请求html代码中的资源（如js、css、图片等） --> 浏览器对页面进行渲染呈现给用户

> 每次都请求都会经过  客户端的应用层（http协议）-->  客户端的传输层（tcp或udp协议） -->客户端的网络层（ip协议） --> 客户端的链路层（网卡，路由器等） -->  ------------------经过dns解析，穿越多个isp（互联网服务提供商，移动，联通，电信等），各种数据交换，找到了服务器------------------- 服务器的链路层  -->服务器的网络层  -->服务器的传输层  -->服务器的应用层。 这个请求完成了
### 说一说有哪些锁（锁的分类，具体含义搞清楚）
> - 公平锁/非公平锁
> - 可重入锁
> - 独占锁/共享锁
> - 互斥锁/读写锁
> - 乐观锁/悲观锁
> - 偏向锁/轻量级锁/重量级锁
> - 自旋锁
### 偏向锁是什么
> 在不存在多个线程争抢锁的情况下，优先偏向第一个获取锁的线程
### spring是如何解决循环依赖的（源码）
> 参考：https://www.cnblogs.com/tiger-fu/p/8961361.html
### mysql的查询sql中有联合索引和唯一索引，联合索引中有个索引失效，还会不会执行后面的唯一索引
### concurrentHashMap什么情况下才会有cas升级会synchronized
> cas比较失败
### concurrentHashMap什么时候会将链表转换为红黑树
> 链表长度大于8，桶中元素个数达到64
### 说一说jvm的内存分布（五大块及各块中存了什么）
> - java栈
> - 本地方法栈
> - 程序计数器
> - 方法区
> - 堆
### 常量池中只存对象的常量吗？还存了什么
> - String类型的字符串值或者定义为final类型的常量的值
> - 符号引用:
>> - 类或接口的全限定名（包括他的父类和所实现的接口）
>> - 变量或方法的名称
>> - 变量或方法的描述信息
>> - this
### 说一说CMS垃圾收集器的实现以及优缺点
> - CMS是一种预处理垃圾回收器，它不能等到old内存用尽时回收，需要在内存用尽前，完成回收操作，否则会导致并发回收失败；所以CMS垃圾回收器开始执行回收操作，有一个触发阈值，默认是老年代或永久带达到92%
> - CMS是基于标记-清除算法的，只会将标记为为存活的对象删除，并不会移动对象整理内存空间，会造成内存碎片
### kafka为什么能这么快（零拷贝 顺序写磁盘）
### 3个分区，2个消费者，会如何消费kafka的数据
### 生产者发送消息的时候，是如何落入分区的（有哪些情况，比如不指定partition但是指定了key，或者都不指定）
### MySQL如何排查慢查询并对其做优化
### 生产环境遇到过什么问题，如何排查
### git fetch和git pull的区别
### git merge和git rebase的区别
### git如何根据提交描述查找历史提交过的版本
### redis的数据结构
### 微信朋友圈的信息（发布的朋友圈，点赞数，评论数等）用redis的什么数据类型存储
