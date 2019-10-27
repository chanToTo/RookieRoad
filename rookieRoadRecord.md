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

### _二十七、什么是synchronizedList,他和Vector有什么区别(待深入了解)？_

1. Vector是java.util包中的一个类。 SynchronizedList是java.util.Collections中的一个静态内部类。在多线程的场景中可以直接使用Vector类，也可以使用Collections.synchronizedList(List list)方法来返回一个线程安全的List。

2. Vector使用同步方法实现，synchronizedList使用同步代码块实现。

3. SynchronizedList有很好的扩展和兼容功能。他可以将所有的List的子类转成线程安全的类。

4. 使用SynchronizedList的时候，进行遍历时要手动进行同步处理。

5. SynchronizedList可以指定锁定的对象。

### _二十八、通过Arrays.asList获得的List有何特点，使用时应该注意什么？_

1. Arrays.asList：将数组转换成集合

### _二十九、Java中的Collection如何迭代？_