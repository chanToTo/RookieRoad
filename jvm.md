### 一、体系结构

![image](assets/jvm架构图.jpg)

> 类装载器ClassLoader：负责加载class文件，class文件在**文件开头有特定的文件标识（cafe babe...）**，将class文件字节码内容加载到内存中，并将这些内容转换成方法区中的运行时数据结构并且classLoder只负责class文件的加载，值与它是否可以运行，则由Execution Engine决定

![image](assets/classLoader.jpg)

类装载器种类：

虚拟机自带的加载器(系统自带)：
> 启动类加载器（Bootstrap）C++ （Object对象获取加载器为null，因为Bootstrap对应的C++为最高级）
> 扩展类加载器（Extension）JAVA
> 应用程序类加载器（AppClassLoader）java也叫系统类加载器，加载当前应用的classPath的所有类（自创建的对象eg MyObject获取加载器为AppClassLoader）

用户自定义加载器:
> java.lang.ClassLoader的子类，用户可以定制类的加载方式

![image](assets/classLoader2.jpg)

ClassLoader的双亲委派机制
> 当一个类收到了类加载请求，它首先不会尝试自己去加载这个类，而是把这个请求委派给父类去完成，每一个层次类加载器都是如此，因此所有的加载请求都应该传送到启动类加载其中，只有当父类加载器反馈自己无法完成这个请求的时候（在它的加载路径下没有找到所需加载的class），子类加载器才会去尝试自己加载。采用双亲委派的一个好处就是比如加载位于rt.jar包中的类java.lang.Object，不管是哪个加载器加载这个类，最终都是委托给顶层的启动类加载器进行加载，这样就保证了使用不同的类加载器最终得到的都是同样一个Object对象

Execution Engine执行引擎负责解释命令，提交操作系统执行