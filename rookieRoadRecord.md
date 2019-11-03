# _菜鸟学习之路_
***

### _一、什么是值传递，什么是引用传递，为什么说java中只有值传递？_

值传递（pass by value）是指在调用函数时将实际参数复制一份传递到函数中，这样在函数中如果对参数进行修改，将不会影响到实际参数。
引用传递（pass by reference）是指在调用函数时将实际参数的地址直接传递到函数中，那么在函数中对参数所进行的修改，将影响到实际参数。

|     | 值传递              | 引用传递            |
|-----|--------------------|--------------------|
| 区别 | 会创建副本          | 不会创建副本         |
| 结果 | 函数中不会改变原始对象 | 函数中可以改变原始对象 |
按共享传递，是指在调用函数时，传递给函数的是实参的地址的拷贝如果实参在栈中，则直接拷贝该值）。在函数内部对参数进行操作时，需要先拷贝的地址寻找到具体的值，再进行操作。如果该值在栈中，那么因为是直接拷贝的值，所以函数内部对参数进行操作不会对外部变量产生影响。如果原来拷贝的是原值在堆中的地址，那么需要先根据该地址找到堆中对应的位置，再进行操作。因为传递的是地址的拷贝所以函数内对值的操作对外部变量是可见的。
  

### _二、什么是编译，什么是反编译，java的编译与反编译方法？_

1. 编程语言：分为低级语言和高级语言。
* 低级语言：直接用计算机指令编写程序，如机器语言、汇编语言。
* 高级语言：用语句编写程序，语句是计算机程序的抽象表示，如java等。
2. 什么是编译：将高级语言翻译成计算机能识别的低级语言（二级制）。
* 编译分为前端编译和后端编译两个部分：前端编译是将源程序编译成.class文件；后端编译是将JVM的解释执行器执行class文件（将字节码翻译成机器码执行）。
* 前端编译：
* 词法分析：从左到右一个字符一个字符地读入源程序，将字符序列转换为标记（token）序列。这里的标记是一个字符串，是构成源代码的最小单位。在这个过程中，词法分析器还会对标记进行分类。规序列范的标记包含有 ：1.java关键词：package、import、public、class、int等； 2. 自定义单词：包名、类名、变量名、方法名； 3. 符号：=、; 、+、-、*、/、%、{、}等。**此阶段不会检查代码的结构是否正确**。
* 语法分析：在词法分析的基础上将单词序列组合成各类语法短语，如“程序”、“语句”、“表达式”等等。**语法分析程序判断源程序在结构上是否正确，此阶段不会检查代码的上下文逻辑是否正确**。
* 语义分析：**将复杂语法转换为简单语法，对结构上正确的源程序进行上下文有关性质的检查，审查源程序有无语义错误，类型错误等**，为代码生成阶段收集类型信息，还会添加一些代码，如添加默认构造器。
* 中间代码生成：**生成一个明确的低级或类机器语言的中间表示（如class文件）**。

* 后端编译：将源文件（.java文件）编译成字节码文件（.class文件），通过类加载器加载执行。
3. 编译与反编译

> - 编译：java的源文件可通过java本身自带的javac命令将其编译成class文件（javac .java文件）。
> - 反编译：javap -c .class文件（javap反编译出来的是字节码文件），除此之外，使用jad、cfr等工具皆可反编译。

### _三、什么是Java的语法糖，列举你知道的语法糖，如何解语法糖？_

1. 什么是java的语法糖？

摘自维基百科

* 语法糖（Syntactic sugar），也译为糖衣语法，是由英国计算机科学家彼得·兰丁发明的一个术语，指计算机语言中添加的某种语法，这种语法对语言的功能没有影响，但是更方便程序员使用。语法糖让程序更加简洁，有更高的可读性。
2. 语法糖列举：
* switch 支持String与枚举

public static void main(String[] args) {

    String helloWorld = "helloWorld";
    switch (helloWorld){
        case "hello":
            System.out.println("hello");
            break;
        case "world":
            System.out.println("world");
        case "helloWorld":
            System.out.println(helloWorld);
            break;
        default:
            break;

    }

}

> - **补充：switch中的字符串匹配是通过调用hashCode方法去匹配case分支中的数据，如果匹配成功，则需要再调用String对象的equals()方法去进行比较，因为hash值不免有相同的值**

* 自动拆装箱

public static void main(String[] args) {

        int i = 10;
        Integer integer = i;
        System.out.println(integer);
        int n = integer;
        System.out.println(n);

    }

* 泛型

public static void main(String[] args) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "001");
        map.put("name", "orangeC");

    }

> - **补充：泛型语法糖的类型擦除：jvm编译器不认<>里的类型，会在编译程序的时候把它擦除，回归最原始的集合**

* 方法变长参数

public static void print(String...strs) {

    for (int i = 0; i < strs.length; i++) {
        System.out.println(strs[i]);

    }

}

* 枚举

public enum e {

    SPRING,SUMMER;

}

* foreach

public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("001");
        list.add("002");
        for (String str : list) {
            System.out.println(str);
        }

    }

* lambda表达式

public static void main(String[] args) {

        String[] players = {"zhansgan", "lisi", "wangwu", "zhaoliu", "wangmazi"};
        Arrays.sort(players, (String s1, String s2) -> (s1.length() - s2.length()));

    }

.
.
.

3. 解语法糖

反编译后可解语法糖，查看实际语法

### _四、什么是多态，java如何实现多态？_

1. 多态：是面向对象编程的一个重要特征，它是**指在父类中定义的属性和方法被子类继承后，可以具有出不同的数据类型和体现出不同的行为**，这使得一个属性或方法在父类及其各个子类中具有不同的含义。
* 编译时多态：体现在重载
* 运行时多态： 通过动态绑定来实现

public class Human {

    private int age = 19;
    public String name = "haha";

    public void introduce(int age,String name){
        System.out.println("my name is " + name + " age " + age);

    }

}

class Female extends Human{

    private int age = 31;
    public String name = "orangeC";
    @Override
    public void introduce(int age, String name) {
        super.introduce(age,name);
        System.out.println("my name is " + name + " age " + age + " i am a female");

    }

}

class Man extends Human{

    private int age = 28;
    public String name = "charles";
    @Override
    public void introduce(int age, String name) {
        System.out.println("my name is " + name + " age " + age + " i am a man");

    }

}

class Test1{

    public static void main(String[] args) {
        Human human = new Female();
        Human human1 = new Man();
        human.introduce(19,"haha");
        human1.introduce(28,"heiehi");
        System.out.println(human.name);
        System.out.println(human1.name);

    }

}
输出结果：
my name is haha age 19
my name is haha age 19 i am a female
my name is heiehi age 28 i am a man
haha
haha

**结论**：human对象编译时类型是Human，运行时是Female, human1对象编译时类型是Human，运行时是Man，**当运行时调用引用变量的方法时，其方法行为总是表现出子类方法的行为特征，而不是父类方法的行为特征，这就表现出：相同类型的变量调用同一个方法时表现出不同的行为特征，这就是多态**。**对象的实例变量不具备多态性**

2. 多态的实现：基于重载和重写。

### _五、什么是方法重写，什么是方法重载，成员变量可以被重写么？_

1. 方法重写：子类继承父类，重写父类的方法。
2. 方法重载：方法名相同，参数顺序、个数、类型不同的方法（返回值类型变化不属于重载）。
3. 成员变量不会被重写，对象的成员变量不具备多态性（参考四代码案例）。

### _六、接口和抽象类的区别，java8给接口新增加了什么？_

1. 接口和抽象类的区别：

> - 抽象类的方法可以用abstract和public或者protect修饰。而接口默认为public abstract  修饰。
> - 抽象类的方法可以有需要子类实现的抽象方法，也可以有具体的方法。而接口在老版本的jdk中，只能有抽象方法，但是Java8版本的接口中，接口可以带有默认方法。
> - 一个类能实现多个接口，但只能继承一个抽象类。
> - 抽象类的属性可以用各种各样的修饰符修饰。而接口的属性是默认的public static final。
> - 抽象类中可以含有静态代码块和静态方法，而接口不能含有静态方法和静态代码块。
> - 抽象类可以含有构造方法，接口不能含有构造方法。
> - 接口只能继承接口。而抽象类可以继承普通的类，也能继承接口和抽象类。

2. java8给接口新增加内容：**接口可以带有默认方法，有方法体**。

### _七、Java能不能多继承，可不可以多实现？_

1. 单继承类，多实现接口。

### _八、什么是构造函数，什么是默认构造函数？_

1. 构造函数：是一种特殊的方法。主要用来在创建对象时初始化对象， 即为对象成员变量赋初始值，总与new运算符一起使用在创建对象的语句中。特别的一个类可以有多个构造函数 ，可根据其参数个数的不同或参数类型的不同来区分它们 即构造函数的重载。

2. 不带参的构造函数。

### _九、构造方法能不能被重载，构造方法能不能被重写？_

1. 能。不能，构造方法不能被继承，所以不能被重写。

### _十、对于成员变量和方法的作用于，public, protected, private以及不写之间的区别_

|     | 同一个类              | 同一个包            | 子类 | 其他包
|-----|--------------------|--------------------|--------------------|--------------------|
| public | √ | √ | √ | √ |
| protected | √ | √ | √ | |
| 默认 | √ | √ |  |  |
| private | √ |  |  |  |

### _十一、接口能否继承接口，抽象类能否实现接口，抽象类能否继承具体类？_

1. 能，能，能。

### _十二、String类能不能被继承，为什么？这种设计有什么好处？_

1. 不能，被final修饰。

2. 好处：其一是为了效率和安全性的缘故，由于String的高度被使用率，可能会降低程序的性能。其二是字符串池的实现在运行时能节约很多heap空间，因为不同的字符串变量都指向池中的同一字符串，所以只有当字符串是不可变时，字符串池才有可能实现。其三字符串是多线程安全的。

### _十三、什么是Static Nested Class，什么是Inner Class，二者有什么不同？_

1. Nested Class一般是C++的说法，Inner Class一般是Java的说法，非静态内部类中不能定义静态成员。

> - 静态内部类（Nested Class）创建一个static内部类的对象，不需要一个外部类对象。
> - 静态内部类（Nested Class）不能从一个static内部类的一个对象访问一个外部类对象。
> - 内部类（Inner Class）中不能定义静态成员。
> - 内部类（Inner Class）可以直接访问外部类中的成员变量。
> - 静态内部类（Nested Class）可以定义成public、protected、默认的、private等多种类型，而不同内部类（Inner Class）只能定义成public和默认的这两种类型。

### _十四、Java中有几种基本数据类型，如何分类的，String是基本数据类型么？_

1. 四类八种

> - 整型:int、short、long、byte
> - 浮点型:float、double
> - 字符型：char
> - 布尔型：boolean

2. 不是，String是对象。

### _十五、整型的几种中，各个类型的取值范围是多少，如何计算的，超出范围会发生什么？_

| 数据类型 | 最小值 | 最大值 | 占用储存空间 | 
|----|----|----|----|
| byte | -2^7=-128 | 2^7-1=127 | 1字节=8bit |
| short | -2^15=-32768 | 2^15-1=32767 | 2字节 |
| int | -2^31=-2147483648 | 2^31=2147483647 | 4字节 |
| long | -2^63=-9223372036854775808 | 2^63-1=9223372036854775807 | 8字节 |

1. 1个字节=8个bit ， 转换成二进制，eg.int 4个字节=32个bit，第一个为符号位，所以2^31次方，其他类型以此类推。

2. 超出范围会发生数据溢出，eg.最大值（2147483647）+1会变成最小值（-2147483648），解决办法：强制类型转换为long类型，在表达式后加L或变量前加long。

### _十六、什么是浮点型，什么是单精度和双精度，为什么代码中不要用浮点数表示金额，那应该用什么表示金额？_

1. 什么是浮点型：浮点类型是指用于表示小数的数据类型。

2. 单精度浮点型float，用32位存储，1位为符号位, 指数8位, 尾数23位，即：float的精度是23位，能精确表达23位的数，超过就被截取。双精度浮点型double，用64位存储，1位符号位,11位指数,52位尾数，即：double的精度是52位，能精确表达52位的数，超过就被截取。

3. 为什么不用浮点数表示金额：因为float的精度是23位，double精度是63位。在存储或运算过程中，当超出精度时，超出部分会被截掉，由此就会造成误差。因此采用BigDecimal这个类表示金额，可表示任何精度的数字。

### _十七、Java中的char是否可以存储中文字符？_

1. 可以，char占位两个字节，1个字符=2个字节。

### _十八、String s = new String("xc")，定义了几个对象？_

1. 分如下两种情况：

> - 如果在字符串常量池中查找找不到xc对象，首先会在字符串常量池中创建一个xc对象，并且在执行new时会去堆区开辟新的空间，还会创建一个xc字符串，共创建了两个对象。
> - 如果在字符串常量池中查找找到了xc对象，则只会在堆区创建一个xc字符串，共创建了一个对象。

### _十九、String有没有长度限制，为什么，如果有，超过限制会发生什么？_

1. 有限制。

2. 编译期：

> - java中String源码 public String(char value[], int offset, int count) {}，其中 this.value = Arrays.copyOfRange(value, offset, offset+count); value表示String的值，count是int类型的，所以char[] value中最多可以保存Integer. MAX_VALUE个, 即2147483647字符。
> - 当使用字符串字面量直接定义String的时候，是会将字符串在常量池中存储一份的，常量池中的每一项数据项的类型以CONSTANT_Utf8类型表示，CONSTANTUtf8info是一个CONSTANTUtf8类型的常量池数据项，它存储的是一个常量字符串。常量池中的所有字面量几乎都是通过CONSTANTUtf8info描述的。CONSTANTUtf8_info的定义如下，而CONSTANTUtf8info中有u2 length; 表明了该类型存储数据的长度。u2是无符号的16位整数，因此理论上允许的的最大长度是2^16=65536。而java class文件是使用一种变体UTF-8格式来存放字符的，null 值使用两个字节来表示，因此只剩下65536-2 = 65534个字节。**因此，在Java中，所有需要保存在常量池中的数据，长度最大不能超过65535，这当然也包括字符串的定义。**
>> - CONSTANT_Utf8_info {

    u1 tag;
    u2 length;
    u1 bytes[length];

}

> - 编译时期（javac）超过限制会报错（错误: 常量字符串过长）

3. 运行期：

> - 参考编译期中所提到的Integer. MAX_VALUE，这个值约等于4G，在运行期，如果String的长度超过这个范围，就可能会抛出异常。(在jdk 1.9之前），int 是一个 32 位变量类型，取正数部分来算的话，他们最长可以有（**近4G的容量**）：
>> - 2^31-1 =2147483647 个 16-bit Unicodecharacter
>> - 2147483647 * 16 = 34359738352 位
>> - 34359738352 / 8 = 4294967294 (Byte)
>> - 4294967294 / 1024 = 4194303.998046875 (KB)
>> - 4194303.998046875 / 1024 = 4095.9999980926513671875 (MB)
>> - 4095.9999980926513671875 / 1024 = 3.99999999813735485076904296875 (GB)

### _二十、String, StringBuilder, StringBuffer之间的区别与联系？_

1. String：**不可变字符串**，保存格式为：private final char value[]，线程安全

2. StringBuilder：**可变字符串**，保存格式为：char[] value，通过append(),insert()等直接修改字符序列来完成对字符串的操作。**线程不安全**

3. StringBuffer：**可变字符串**，保存格式为：char[] value，通过append(),insert()等直接修改字符序列来完成对字符串的操作。**线程安全**

### _二十一、subString()方法到底做了什么，不同版本的JDK中是否有区别，为什么？_

1. subString源码(jdk8)：

public String substring(int beginIndex, int endIndex) {

        if (beginIndex < 0) {
            throw new StringIndexOutOfBoundsException(beginIndex);
        }
        if (endIndex > value.length) {
            throw new StringIndexOutOfBoundsException(endIndex);
        }
        int subLen = endIndex - beginIndex;
        if (subLen < 0) {
            throw new StringIndexOutOfBoundsException(subLen);
        }
        return ((beginIndex == 0) && (endIndex == value.length)) ? this
                : new String(value, beginIndex, subLen);

    }

String(char value[], int offset, int count) {
...
this.value = Arrays.copyOfRange(value, offset, offset+count); 
}
**将一个原始的数组original，从下标from开始复制，复制到上标to，生成一个新的数组，避免内存泄漏**

2. subString源码(jdk6)

public String substring(int beginIndex, int endIndex) {

    if (beginIndex < 0) {
        throw new StringIndexOutOfBoundsException(beginIndex);

    }

    if (endIndex > count) {
        throw new StringIndexOutOfBoundsException(endIndex);

    }

    if (beginIndex > endIndex) {
        throw new StringIndexOutOfBoundsException(endIndex - beginIndex);

    }

    return ((beginIndex == 0) && (endIndex == count)) ? this :
    new String(offset + beginIndex, endIndex - beginIndex, value);

}

String(int offset, int count, char value[]) {

    this.value = value;
    this.offset = offset;
    this.count = count;

}
**其中传入的value用的还是原来string对象的 value。即这个value的值会被两个string对象共享，规避方式：String sub = new String(s.substring(…)); **

### _二十二、如何理解String的intern方法，不同版本的JDK有何不同，为什么？_

intern()的作用

> 提高程序效率或者减少内存占用的情况。

在JDK1.6及以前，inter()方法是这样描述的：

> 当字符串在常量池存在时，则返回常量池中的字符串；当字符串在常量池不存在时，则在常量池中拷贝一份，然后再返回常量池中的字符串。

JDK1.6以后：

> 当字符串在常量池存在时，则返回常量池中的字符串；当字符串在常量池不存在时，则把堆内存中此对象引用添加到常量池中，然后再返回此引用。

总结：**JDK1.6及以前是将堆内存的对象拷贝一份到常量池中，JDK1.6以后是将此对象的引用放入常量池中**。

首先理解String创建对象的概念：

1. 只在常量池上创建常量:

> String a1 = "AA"; 

2. 只在堆上创建对象

> String a2 = new String("A") + new String("A"); 

3. 在堆上创建对象，在常量池上创建常量

> String a3 = new String("AA"); 

4. 在堆上创建对象，在常量池上创建引用

> String a4 = new String("A") + new String("A"); //只在堆上创建对象AA

 a4.intern(); //将该对象AA的引用保存到常量池上

5. intern()理解

> 判断当前常量是否存在于常量池，if（存在），判断存在内容是引用还是常量，**如果是引用，返回引用地址指向堆空间对象，如果是常量，直接返回常量池常量**。else（不存在），将当前对象引用复制到常量池，**并且返回当前对象的引用**。

6. 代码示例：

> public static void main(String[] args) {

        String a5 = new String("A") + new String("A");
        a5.intern();
        String a6 = "AA";
        System.out.println(a5 == a6);//true

        String a3 = new String("AA");   
        a3.intern(); 
        String a4 = "AA"; 
        System.out.println(a3 == a4);//false

        String a1 = new String("AA"); 
        String a2 = "AA"; 
        System.out.println(a1.intern() == a2); // true

    }

### _二十三、什么是Java中整型的缓存机制？_

1. Integer自动装箱，创建对象之前先从缓存中查找，数值范围在-128-127之间，可用==比较，因为返回的是同一对象，Integer缓存数组中保存了 -128～h(h>=127) 的值的包装类对象，其中h值可以通过JVM参数 -XX:AutoBoxCacheMax=size 修改，超过这个定义的数值，就会新建对象返回

public static void main(String...strings) {

        Integer integer1 = 3; //整数3自动装箱
        Integer integer2 = 3; //整数3自动装箱

        if (integer1 == integer2) //两个自动装箱后对象比较
            System.out.println("integer1 == integer2");
        else
            System.out.println("integer1 != integer2");

        Integer integer3 = 300;//整数300自动装箱
        Integer integer4 = 300;//整数300自动装箱

        if (integer3 == integer4)//两个自动装箱后对象比较
            System.out.println("integer3 == integer4");
        else
            System.out.println("integer3 != integer4");

    }

输出结果：
integer1 == integer2
integer3 != integer4

2. 源码：

public static Integer valueOf(int i) {

        if (i >= IntegerCache.low && i <= IntegerCache.high) 
            return IntegerCache.cache[i + (-IntegerCache.low)]; 
        return new Integer(i); 

    }

private static class IntegerCache {

        static final int low = -128;
        static final int high;
        static final Integer cache[];

        static {
            // high value may be configured by property
            int h = 127;
            String integerCacheHighPropValue =
                sun.misc.VM.getSavedProperty("java.lang.Integer.IntegerCache.high");
            if (integerCacheHighPropValue != null) {
                try {
                    int i = parseInt(integerCacheHighPropValue);
                    i = Math.max(i, 127);
                    // Maximum array size is Integer.MAX_VALUE
                    h = Math.min(i, Integer.MAX_VALUE - (-low) -1);
                } catch( NumberFormatException nfe) {
                    // If the property cannot be parsed into an int, ignore it.
                }
            }
            high = h;

            cache = new Integer[(high - low) + 1];
            int j = low;
            for(int k = 0; k < cache.length; k++)
                cache[k] = new Integer(j++);

            // range [-128, 127] must be interned (JLS7 5.1.7)
            assert IntegerCache.high >= 127;
        }

        private IntegerCache() {}

    }

### _二十四、Collection和Collections有什么区别？_

1. Collection是一个集合接口。它提供了对集合对象进行基本操作的通用接口方法。实现该接口的类主要有List和set，该接口的设计目标是为各种具体的集合提供最大化的统一操作方式。

2. Collections是针对集合类的一个包装类，它提供了一系列的静态方法以实现对各种集合的搜索、排序、线程安全化等操作，其中大多数方法都是用来处理线性表。 Collections类不能实例化，如同一个工具类，服务于Collection。若是在使用Collections类的方法时候，对应的collection的对象为null，则这些方法都会抛出 NullPointerException 。之前我们见到的包装类还有Arrays，它是为数组提供服务的。

Collections常用方法：

public static void main(String[] args) {
        List<String> list = new ArrayList<>(); 
        list.add("zhangsan"); 
        list.add("lisi"); 
        list.add("wangwu"); 
        Collections.shuffle(list);    //1、将list随机排列
        System.out.println(list);    //输出[a2,  a3,  a0,  a1,  a4]
        Collections.reverse(list);   //2、将集合中的内容逆序
        System.out.println(list);     //输出 [a4,  a3,  a2,  a1,  a0]
        Collections.sort(list);   //3、将集合中的内容进行排序
        System.out.println(list);    //输出[a0,  a1,  a2,  a3,  a4]
        Collections.fill(list, "hello world");   //4、填充集合
    }

### _二十五、Set是如何保证元素不重复的？_

1. set添加的元素无序不重复

2. HashSet源码实现：

public class HashSet<E> extends AbstractSet<E> implements Set<E>, Cloneable, java.io. Serializable{

    
    private transient HashMap<E,Object> map;

    // Dummy value to associate with an Object in the backing Map
    private static final Object PRESENT = new Object();

    public HashSet() {
        map = new HashMap<>(); 

    }

    .
    .
    .
    // PRESENT是一个虚拟值，是为了HashMap实现HashSet的一个假设的值
    public boolean add(E e) {
        return map.put(e, PRESENT)==null; 

    }

}
**set往往使用hashSet进行实现，而hashSet中构造函数底层是以hashMap实现的，在add方法中将所添加的元素作为key存入hashMap，保证元素的唯一。hashMap put方法详情参考源码。**

### _二十六、Java中的List有几种实现，各有什么不同？_

1. ArrayList是使用数组方式存储数据，线程不安全，此数组元素数大于实际存储的数据以便增加和插入元素，它们都允许直接按序号索引元素，但是插入元素要涉及数组元素移动等内存操作，所以索引数据快而插入数据慢。

2. LinkedList使用双向链表实现存储，按序号索引数据需要进行前向或后向遍历，但是插入数据时只需要记录本项的前后项即可，所以插入速度较快。 

3. Vector利用数组及扩容实现List，但Vector是一种线程安全的List结构，它的读写效率不如ArrayList，其原因是在该实现类内在方法上加上了同步关键字，Vector在默认情况下是以两倍速度递增。

### _二十七、什么是synchronizedList, 他和Vector有什么区别(待深入了解)？_

1. Vector是java.util包中的一个类。 SynchronizedList是java.util.Collections中的一个静态内部类。在多线程的场景中可以直接使用Vector类，也可以使用Collections.synchronizedList(List list)方法来返回一个线程安全的List。

2. Vector使用同步方法实现，synchronizedList使用同步代码块实现。

3. SynchronizedList有很好的扩展和兼容功能。他可以将所有的List的子类转成线程安全的类。

4. 使用SynchronizedList的时候，进行遍历时要手动进行同步处理。

5. SynchronizedList可以指定锁定的对象。

### _二十八、通过Arrays.asList获得的List有何特点，使用时应该注意什么？_

1. Arrays.asList：将数组转换成集合

> 该方法不适用于基本数据类型（byte, short, int, long, float, double, boolean）
> 该方法将数组与列表链接起来，当更新其中之一时，另一个自动更新
> 不支持add和remove方法

2. 基本数据类型使用此方法打印出来的都是地址值，而String类型的数组输出的是数组中的元素

> public static void main(String[] args) {

        // char
        char[] mChar = { 'a', 'b' }; 
        System.out.println("----->char" + Arrays.asList(mChar)); 
        // int
        int[] mInt = { 1, 23, 4, 5, 56, 77 }; 
        System.out.println("---->int" + Arrays.asList(mInt)); 
        // boolean
        boolean[] mBoolean = { true, false }; 
        System.out.println("---->boolean" + Arrays.asList(mBoolean)); 
        // double
        double[] mDouble = { 1.1, 2.4, 5.6, 7.8 }; 
        System.out.println("---->double" + Arrays.asList(mDouble)); 
        // String
        String mString[] = { "orange", "apple", "lemon" }; 
        List<String> list = Arrays.asList(mString); 
        System.out.println("---->String" + Arrays.asList(mString)); 

    }

输出结果：
char[[C@14ae5a5]
int[[I@7f31245a]
boolean[[Z@6d6f6e28]
double[[D@135fbaa4]
String[orange, apple, lemon]

3. 总结说明：list的长度是根据mString[]获得的，长度已经确定，不能改变，如果往该集合添加或删除元素，都会报java.lang.UnsupportedOperationException异常，这是因为Arrays.asList() 返回java.util.Arrays.ArrayList，而不是java.util.ArrayList。Arrays.ArrayList和util.ArrayList都是继承AbstractList，remove，add等 method在AbstractList中是默认throw UnsupportedOperationException而且不作任何操作。util.ArrayList override这些method来对list进行操作，但是Arrays.ArrayList没有override remove()，add()等，所以throw UnsupportedOperationException。

### _二十九、Java中的Collection如何迭代？_

1. Iterator

public static void display(Iterator<Object> it) {

    while(it.hasNext()) {
        Object obj = it.next(); 
        System.out.println(obj); 

    }

}

2. forEach

public static void display(Collection<Object> co) {

    for (Object obj : co) {
        System.out.print(obj); 

    }

}

3.  for循环

public static void display(Collection<Object> co) {

    Object[] obj = co.toArray(new Object[co.size()]);
    for (int i=0; i<obj.length; i++) {
    System.out.print(s[i]);
    }   

}

### _三十、Enumeration和Iterator接口的区别？_

1. Enumeration速度是Iterator的2倍，同时占用更少的内存。但是，Iterator远远比Enumeration安全，因为其他线程不能够修改正在被iterator遍历的集合里面的对象。同时，Iterator允许调用者删除底层集合里面的元素，这对Enumeration来说是不可能的。

package java.util; 

 public interface Enumeration<E> {

      boolean hasMoreElements();
      E nextElement();

 }
 public interface Iterator<E> {

    boolean hasNext();
     E next();
    void remove();
    void forEachRemaining(Consumer<? super E> action);

 }

 2. Enumeration枚举速度快，占用内存少，但是不是快速失败的，线程不安全。Iterator迭代允许删除底层数据

### _三十一、Iterator和ListIterator之间有什么区别？_

1. Iterator源码方法：

> hasNext()：如果迭代器指向位置后面还有元素，则返回 true，否则返回false
> next()：返回集合中Iterator指向位置后面的元素
> remove()：删除集合中Iterator指向位置后面的元素

 2. ListIterator源码方法：

> add(E e): 将指定的元素插入列表，插入位置为迭代器当前位置之前
> hasNext()：以正向遍历列表时，如果列表迭代器后面还有元素，则返回 true，否则返回false
> hasPrevious(): 如果以逆向遍历列表，列表迭代器前面还有元素，则返回 true，否则返回false
> next()：返回列表中ListIterator指向位置后面的元素
> nextIndex(): 返回列表中ListIterator所需位置后面元素的索引
> previous(): 返回列表中ListIterator指向位置前面的元素
> previousIndex()：返回列表中ListIterator所需位置前面元素的索引
> remove(): 从列表中删除next()或previous()返回的最后一个元素（有点拗口，意思就是对迭代器使用hasNext()方法时，删除ListIterator指向位置后面的元素；当对迭代器使用hasPrevious()方法时，删除ListIterator指向位置前面的元素）
> set(E e)：从列表中将next()或previous()返回的最后一个元素返回的最后一个元素更改为指定元素e

3. 主要区别：

> 两者都是迭代器，但是Iterator可以迭代所有的集合，ListIterator只能迭代List及其子类型的集合。
> ListIterator有add方法，可以向List中添加对象，而Iterator不能。
> ListIterator和Iterator都有hasNext()和next()方法，可以实现顺序向后遍历，但是ListIterator有hasPrevious()和previous()方法，可以实现逆向（顺序向前）遍历。Iterator不可以。
> ListIterator可以定位当前索引的位置，nextIndex()和previousIndex()可以实现。Iterator没有此功能。
> 都可实现删除操作，但是ListIterator可以实现对象的修改，set()方法可以实现。Iterator仅能遍历，不能修改。

### _三十二、什么是fail-fast ，什么是fail-safe ，有什么区别？_

1. fail-fast：快速失败，在用迭代器遍历一个集合对象时，如果遍历过程中对集合对象的内容进行了修改（增加、删除、修改），则会抛出Concurrent Modification Exception。

> 原理：迭代器在遍历时直接访问集合中的内容，并且在遍历过程中使用一个modCount变量。集合在被遍历期间如果内容发生变化（即检测 modCount！=expectedmodCount），就会改变modCount的值。每当迭代器使用hashNext()/next()遍历下一个元素之前，都会检测modCount变量是否为expectedmodCount值，是的话就返回遍历；否则抛出异常，终止遍历。

2. fail-safe：安全失败，采用安全失败机制的集合容器，在遍历时不是直接在集合内容上访问的，而是先复制原有集合内容，在拷贝的集合上进行遍历。

> 原理：由于迭代时是对原集合的拷贝进行遍历，所以在遍历过程中对原集合所作的修改并不能被迭代器检测到，所以不会触发Concurrent Modification Exception。
> 缺点：基于拷贝内容的优点是避免了Concurrent Modification Exception，但同样地，迭代器并不能访问到修改后的内容，即：迭代器遍历的是开始遍历那一刻拿到的集合拷贝，在遍历期间原集合发生的修改迭代器是不知道的。

3. 注意：java.util包下的集合类都是快速失败的，不能在多线程下发生并发修改（迭代过程中被修改）。java.util.concurrent包下的容器都是安全失败，可以在多线程下并发使用，并发修改。

### _三十三、如何在遍历的同时删除ArrayList中的元素？_

1. 使用迭代器Iterator

### _三十四、如何对一组对象进行排序？_

1. 对象需要实现Comparable接口及compareTo方法

2. 代码示例：

@Data
public class Person implements Comparable<Person>{

    private int age;
    private String name;

    public Person(int age, String name){
        this.name = name; 
        this.age = age; 

    }

    @Override
    public int compareTo(Person o) {
        if( this.age < o.age ){
            return -1; 
        } else {
            return this.name.compareTo(o.name); 
        }

    }

    @Override
    public String toString() {
        return "person: " + this.name + " " + this.age; 

    }

}

public static void main(String[] args) {

        Person[] d = new Person[4]; 
        d[0] = new Person(18, "orange"); 
        d[1] = new Person(22, "w"); 
        d[2] = new Person(21, "r"); 
        d[3] = new Person(14, "h"); 
        Arrays.sort(d); 
        for( int i = 0 ; i < d.length ; i ++ ){
            System.out.println(d[i]); 
        }

    }

### _三十五、Comparable和Comparator有何区别？_

1. Comparable定义在java.lang包里,意味着可以被比较的能力,因此某个类想要可以被排序,被比较大小,需要实现int compareTo(T o);这个接口，接口里只定义了这一个方法,代表了:传入一个对象,将对象和元素自身进行比较,如果元素自身大,返回1,相等返回0,元素自身小于参数则返回-1。

2. Comparator定义与java.util包中,代表着一个角色,这个角色的功能是对传入的两个元素进行大小的比较,并且返回结果。主要方法为int compare(T o1, T o2)。

3. 总结：Comparable实现内部自然排序，Comparator实现外部比较。

### _三十六、Java中的集合使用泛型有哪些好处？_

1. 类型安全。提供了编译时类型安全监测机制，该机制允许我们在编译时检测到非法的类型数据结构。

2. 消除强制类型转换。泛型一个附带好处是，消除代码中许多强制类型的转换。减少代码出错率，更好阅读。

### _三十七、当一个集合被作为参数传递给一个函数时，如何才能确保函数不能修改它？_

1. 在作为参数传递之前，我们可以使用Collections.unmodifiableCollection(Collection c)方法创建一个只读集合，这将确保改变集合的任何操作都会抛出UnsupportedOperationException。

### _三十八、通过给定集合得到一个synchronized的集合？_

1. 可使用Collections.synchronizedCollection(Collection c)根据指定集合来获取一个synchronized（线程安全的）集合。

### _三十九、Java中的Map主要有哪几种，之间有什么区别？_

1. 主要有HashMap、HashTable、TreeMap、ConcurrentHashMap、LinkedHashMap、weakHashMap。

2. 区别：

> HashMap： 使用位桶和链表实现（最近的jdk1.8改用红黑树存储而非链表），它是线程不安全的Map，方法上都没有synchronize关键字修饰。
> HashTable： hashTable是线程安全的一个map实现类，它实现线程安全的方法是在各个方法上添加了synchronize关键字。但是现在已经不再推荐使用HashTable了，因为现在有了ConcurrentHashMap这个专门用于多线程场景下的map实现类，其大大优化了多线程下的性能。
> ConcurrentHashMap：（待补充）

### _四十、遍历map的几种方式？_

public static void main(String[] args) {

        Map<String, String> map = new HashMap<String, String>(); 
        map.put("1", "value1"); 
        map.put("2", "value2"); 
        map.put("3", "value3"); 
        //第一种：普遍使用，二次取值
        System.out.println("通过Map.keySet遍历key和value："); 
        for (String key : map.keySet()) {
            System.out.println("key= " + key + " and value= " + map.get(key)); 
        }
        //第二种：推荐，尤其是容量大时
        System.out.println("通过Map.entrySet遍历key和value"); 
        for (Map. Entry<String, String> entry : map.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue()); 
        }
        //第三种
        System.out.println("通过Map.values()遍历所有的value，但不能遍历key"); 
        for (String v : map.values()) {
            System.out.println("value= " + v); 
        }
        //第四种
        System.out.println("通过Map.entrySet使用iterator遍历key和value：");
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }

    }

**建议使用entrySet进行遍历，可把key、value同时取出**

### _四十一、hashmap与hashtable区别？_

1. 区别：

> - hashtable继承自Dictionary类，而HashMap继承自AbstractMap类。但二者都实现了Map接口。
> - hashtable的方法是Synchronize的，线程安全，而hashmap反之。
> - hashmap可以有空的value，而hashtable的key和value都不能为空

### _四十二、HashMap和ConcurrentHashMap的区别？_

1. HashMap：
> - HashMap默认不是线程安全的。
> - HashMap是map接口的实例，是将键映射到值的对象，其中键和值都是对象，并且不能包含重复键，但可以包含重复值。

2. ConcurrentHashMap：
> - 采用CAS+Synchronized保证并发安全性，且synchronized关键字不是用在方法上而是用在了具体的对象上，实现了更小粒度的锁。
> - ConcurrentHashMap采用了分段锁技术，其中Segment继承于ReentrantLock。不会像HashTable那样不管是put还是get操作都需要做同步处理，理论上ConcurrentHashMap支持CurrencyLevel(Segment 数组数量)的线程并发。每当一个线程占用锁访问一个Segment时，不会影响到其他的Segment。

**详情查看源码**

### _四十三、同样是线程安全的MAP, HashTable和ConcurrentHashMap之间有什么区别？_

1. 区别：HashTable实现线程安全采用的方式是对方法都加上sychronized同步锁，get和put方法皆是如此，导致效率低下，而ConcurrentHashMap采用分段锁机制，在每一个segment上面加上ReentrantLock，多个线程不需要去等待同一把锁，提高效率。

### _四十四、hashCode()和equals()方法的作用，二者有什么关系？_

1. 作用：比较两个对象是否一致。

2. 关系：

> 当hashCode判断相同时，equals判断未必相同
> 当equals判断相同时，hashCode判断一定相同

**注意：如果没有重写hashcode方法，使用Object自带的hashCode，无法保证两个对象equals的时候hashCode必须相等的条件，如果把对象放到散列存储结构的集合中，必须要重写。**

### _四十五、HashMap和TreeMap的区别是什么？_

1. 区别：

> - HashMap是通过hashcode()对其内容进行快速查找的；HashMap中的元素是没有顺序的；TreeMap中所有的元素都是有某一固定顺序的，如果需要得到一个有序的结果，就应该使用TreeMap。
> - HashMap和TreeMap都不是线程安全的。
> - HashMap继承AbstractMap类；覆盖了hashcode() 和equals() 方法，以确保两个相等的映射返回相同的哈希值；TreeMap继承SortedMap类；他保持键的有序顺序。
> - HashMap基于hash表实现的；使用HashMap要求添加的键类明确定义了hashcode() 和equals() （可以重写该方法）；为了优化HashMap的空间使用，可以调优初始容量和负载因子；TreeMap基于红黑树实现的；TreeMap就没有调优选项，因为红黑树总是处于平衡的状态。
> - HashMap适用于Map插入，删除，定位元素；TreeMap适用于按自然顺序或自定义顺序遍历键（key）。

### _四十六、所有类都可以作为Map的key么？有什么需要注意的？_

> - 如果要将对象作为map的key，需要实现该对象的equals方法和hashCode方法。Object类的hashCode()方法返回这个对象存储的内存地址的编号。而equals()比较的是内存地址是否相等。

### _四十七、了解Java的并发编程包么，并发集合类是什么，有哪些？_

一. 原子类型：
1. 原子基本类型：
> - AtomicBoolean
> - AtomicInteger
> - AtomicLong

2. 常见方法：
> - int addAndGet(int delta)： 以原子方式将输入的数值与实例中的值（AtomicInteger里的value）相加，并返回结果。
> - boolean compareAndSet(int expect，int update)：如果输入的数值等于预期值，则以原子方式将该值设置为输入的值。
> - int getAndIncrement()：以原子方式将当前值加1，注意，这里返回的是自增前的值。
> - int getAndSet（int newValue）：以原子方式设置为newValue的值，并返回旧值。
> - int getAndSet（int newValue）：以原子方式设置为newValue的值，并返回旧值。

二. 闭锁操作
1. CountDownLatch
> 闭锁操作，在完成某些运算时，只有其他所有线程的运算全部完成时，当前运算才能继续执行，也就是说，可以设置主线程在其他分线程执行完成之后才执行。

2. 常见方法：
> - public void await() //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
> - public boolean await(long timeout, TimeUnit unit) //和await()类似，只不过等待一定的时间后count值还没变为0的话就会继续执行
> - public void countDown() { }; //将count值减1
> - CyclicBarrie(同步屏障)
> - CyclicBarrier和CountDownLatch的区别：CountDownLatch和CyclicBarrier都能够实现线程之间的等待，只不过它们侧重点不同：CountDownLatch一般用于某个线程A等待若干个其他线程执行完任务之后，它才执行；而CyclicBarrier一般用于一组线程互相等待至某个状态，然后这一组线程再同时执行；另外，CountDownLatch是不能够重用的，而CyclicBarrier是可以重用的。

三. 阻塞队列：
1. 概念：阻塞队列（BlockingQueue）是一个支持两个附加操作的队列。这两个附加的操作是：在队列为空时，获取元素的线程会等待队列变为非空。当队列满时，存储元素的线程会等待队列可用。阻塞队列常用于生产者和消费者的场景，生产者是往队列里添加元素的线程，消费者是从队列里拿元素的线程。阻塞队列就是生产者存放元素的容器，而消费者也只从容器里拿元素。

2. 常使用类：
> - ArrayBlockingQueue：一个由数组结构组成的有界阻塞队列
> - LinkedBlockingQueue：一个由链表结构组成的有界阻塞队列
> - PriorityBlockingQueue：一个支持优先级排序的无界阻塞队列
> - DelayQueue：一个使用优先级队列实现的无界阻塞队列
> - SynchronousQueue：一个不存储元素的阻塞队列
> - LinkedTransferQueue：一个由链表结构组成的无界阻塞队列
> - LinkedBlockingDeque：一个由链表结构组成的双向阻塞队列

四. 线程池
1. 概念：线程池提供了一个线程队列，队列中保存着所有等待状态的线程，避免创建与销毁额外开销，提高了响应的速度。

2. 线程池的体系结构：
> java.util.concurrent.Executor：负责线程的使用与调度的根接口
>   ExecutorService子接口：线程池的主要接口
>       ThreadPoolExecutor 线程的实现类
>       ScheduledExecutorService 子接口：负责线程的调度
>           ScheduledThreadPoolExecutor：继承ThreadPoolExecutor，实现ScheduledExecutorService

3. 工具类：Executors
> ExecutorService newFixedThreadPool()：创建固定大小的线程池。
> ExecutorService newCachedThreadPool()：缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量。
> ExecutorService newSingleThreadExecutor()：创建单个线程池，线程池中只有一个线程。
> ScheduledExecutorService newScheduledThreadPool()：创建固定大小的线程，可以延迟或定时的执行任务。

五. Runnable和Callable
1. 说明：Runnable接口和Callable接口的实现类，都可以被ThreadPoolExecutor或ScheduledThreadPoolExecutor执行。它们之间的区别是Runnable不会返回结果，而Callable可以返回结果，callable需要FutureTask类支持，callable实现可用于闭锁。

### _四十八、介绍下CopyOnWriteArrayList, 和普通的ArrayList存在哪些区别，以及，什么是CopyOnWrite？_ 
1. CopyOnWriteArrayList：写入并复制，是一个ArrayList的线程安全的变体，原理是先copy出一个容器(可以简称副本)，再往新的容器里添加这个新的数据，最后把新的容器的引用地址赋值给了之前那个旧的的容器地址，但是在添加这个数据的期间，其他线程如果要去读取数据，仍然是读取到旧的容器里的数据。可避免在操作集合添加的同时读取会报修改异常，适合并发迭代操作，但是添加操作多时，效率低。

2. 区别：
> ArrayList迭代器在设计上是快速失败的，在用迭代器遍历一个集合对象时，如果遍历过程中对集合对象的内容进行了修改（增加、删除、修改），则会抛出Concurrent Modification Exception。
> CopyOnWriteArrayList采用安全失败机制的集合容器，在遍历时不是直接在集合内容上访问的，而是先复制原有集合内容，在拷贝的集合上进行遍历。

3. CopyOnWrite：线程安全，写时复制， 在往集合中添加数据的时候，先拷贝存储的数组，然后添加元素到拷贝好的数组中，然后用现在的数组去替换成员变量的数组（就是get等读取操作读取的数组）。这个机制和读写锁是一样的，但是比读写锁有改进的地方，那就是读取的时候可以写入的 ，这样省去了读写之间的竞争。适用多读取，少添加。

**备注：java.util包下的集合类都是快速失败的，不能在多线程下发生并发修改（迭代过程中被修改）java.util.concurrent包下的容器都是安全失败，可以在多线程下并发使用，并发修改。**

### _四十九、什么是跳表(SkipList)？_ 
1. 概念：使用“空间换时间”的算法，令链表的每个结点不仅记录next结点位置，还可以按照level层级分别记录后继第level个结点。在查找时，首先按照层级查找，比如：当前跳表最高层级为3，即每个结点中不仅记录了next结点（层级1），还记录了next的next（层级2）、next的next的next（层级3）结点。现在查找一个结点，则从头结点开始先按高层级开始查：head->head的next的next的next->。。。直到找到结点或者当前结点q的值大于所查结点，则此时当前查找层级的q的前一节点p开始，在p~q之间进行下一层级（隔1个结点）的查找......直到最终迫近、找到结点。此法使用的就是“先大步查找确定范围，再逐渐缩小迫近”的思想进行的查找。

### _五十、什么是ConcurrentSkipListMap，和ConcurrentHashMap有什么区别？_ 
1. ConcurrentSkipListMap：TreeMap使用红黑树按照key的顺序（自然顺序、自定义顺序）来使得键值对有序存储，但是只能在单线程下安全使用；多线程下想要使键值对按照key的顺序来存储，则需要使用ConcurrentSkipListMap。底层是通过跳表来实现的。跳表是一个链表，但是通过使用“跳跃式”查找的方式使得插入、读取数据时复杂度变成了O（logn）。

2. ConcurrentHashMap：采取了“锁分段”技术来细化锁的粒度：把整个map划分为一系列被成为segment的组成单元，一个segment相当于一个小的hashtable。这样，加锁的对象就从整个map变成了一个更小的范围——一个segment。ConcurrentHashMap线程安全并且提高性能原因就在于：对map中的读是并发的，无需加锁；只有在put、remove操作时才加锁，而加锁仅是对需要操作的segment加锁，不会影响其他segment的读写，由此，不同的segment之间可以并发使用，极大地提高了性能。