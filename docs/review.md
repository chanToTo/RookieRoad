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


