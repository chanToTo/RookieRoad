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
### 十八、http和tcp有什么关系，层级结构在哪一层
### 十九、了解saas的租户模式吗
### 二十、synchronized和reentranrlock的区别
### 二十一、reentrantlock的底层实现原理、是怎么样判断拿不拿锁的、公平和非公平锁的体现（状态，AQS队列）
### 二十二、了解juc下的哪些类
### 二十三、atomic是怎么实现的
### 二十四、concurrenthashmap1.7和1.8的区别，为什么要cas+synchronized，目的在哪（cas预估值为空）
### 二十五、http协议的线程模型
### 二十六、了解tomcat哪些内容
### 二十七、spring bean的生命周期
### 二十八、如果我需要对bean做一些自定义操作应该如何实现
### 二十九、spring加注解是如何实现加了注解就有用的
### 三十、aop有哪些动态代理，如何实现？你要看他怎么实现的如何看（字节码）
### 三十一、G1、cms垃圾收集器的实现以及区别，适用场景（内存大使用G1）


