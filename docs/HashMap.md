### 一、数组优势及劣势
> - 优势：内存是连续的，知道下标就可以快速查找到数据
> - 劣势：数组在开始分配内存时就固定了大小，不可扩容，想要插入元素就需要新建新的数组

### 二、链表优势及劣势
> - 优势：插入数据方便，只许将上一个链表的下一个指针指向新的数据，再链接其后
> - 劣势：查询不便，需要逐一迭代链表并获取值

### 三、补充数组和链表的劣势 -> 散列表（数组+链表）
> 散列表使用hash进行实现，hash也称散列、哈希，基本原理就是把任意长度的输入，通过hash算法变成固定长度的输出，这个映射的规则就是对应的hash算法，而原始数据映射后的二进制串就是哈希值

hash的特点：
> - 从hash值不可以反向推导出原始的数据
> - 输入数据的微小变化会得到完全不同的hash值，相同的数据会得到相同的值
> - 哈希算法的执行效率要高效，长的文本也能快速的计算出哈希值
> - hash算法的冲突概率要小

由于hash的原理是将输入空间的值映射成hash空间内，而hash值得空间远小于输入得空间，根据抽屉原理，一定会存在不同得输入被映射成相同输出得情况。

    抽屉原理：桌上有十个苹果，要把这十个苹果放到九个抽屉里，无论怎样放，我们会发现至少有一个抽屉里面放不少于两个苹果

### 四、HashMap数据结构（数组+链表+红黑树）

> 当链表长度大于8时，并且hash表中所有元素达到64个之后，链表将转换成红黑树

![image](assets/Hash Map Put().jpg)

散列表的长度一定是2的次方数，eg 16 32 64等

### 五、Hash碰撞
> 通过hash算法计算的hash值并不具备唯一性，多个插入数据的hash值恰巧都一样的话，那么大量的数据插入时计算的存储地址都一样，就会造成hash碰撞，那么该链表长度过于大，引发查找效率低，时间复杂度会由原来的O(1)变成O(N)

### 六、HashMap的属性
> - table：哈希表，
> - entrySet：
> - size：当前哈希表中元素个数
> - modCount：当前哈希表结构修改次数
> - threshold：扩容阈值，只能是2的次方数，当哈希表中的元素超过此阈值时触发扩容，扩容增强查找性能，因为桶中的链表有可能链化过于严重
> - loadFactor：负载因子，threshold = capacity（数组长度） * loadFactor