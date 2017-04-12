#`equals && ==`
操作符`==`和方法`equals`是java语言中用于比较变量之间关系的两种方式
通常来说操作符`==`比较的是变量在JVM中的地址(如: 0x00000001)
方法`equals`是一个Object对象中public方式，子类可以覆写它来进行比较，通常来说比较的是变量的值

#基本数据类型与非基本数据类型的比较
java的基本数据类型可以使用操作符`==`直接比较其数值
```java
int a = 1;
int b = 1;
assert a == b;
```

如果变量不是java的基本变量，那么会比较变量的引用(可以理解成内存地址)
```java
ObjectA a = new ObjectA();
ObjectA b = new ObjectA();
assert a != b;
```

在java中基本类型的赋值有点特殊，在jdk1.5后允许使用java.lang下对应的封装类型对基本类型进行赋值，同时这些封装基本类型的类也可以接受基本类型直接赋值
```java
int a0 = new Integer(1);
Integer a1 = 1;
```
这是由于在java中有装箱和拆箱的机制，所有的基本类型都有其对应的类
byte(1 byte)    -> Byte
short(2 byte)   -> Short
int(4 byte)     -> Integer
long(8 byte)    -> Long

float(4 byte)   -> Float
double(8 byte)  -> Double

char(2 byte)    -> Character

boolean(1 byte) -> Boolean

当赋值给基本类型时会发生装箱与拆箱，通过反编译查看字节码得知当发生装箱是调用了封装类型的valueOf()方法而拆箱使用了intValue()方法，代码如下:
```java
int a0 = new Integer(1);
Integer a1 = 1;
```
反编译后的字节码:
```unicode
 0  new java.lang.Integer [17]
 3  dup
 4  iconst_1
 5  invokespecial java.lang.Integer(int) [19]
 8  invokevirtual java.lang.Integer.intValue() : int [22]
11  istore_1 [a0]
12  iconst_1
13  invokestatic java.lang.Integer.valueOf(int) : java.lang.Integer [26]
16  astore_2 [a1]
17  return
```
从字节码可以得出代码`int a0 = new Integer(1);`在JVM中分为2个阶段
1. 新建Integer对象，复制对象并压入栈(dup)，将数值1压入栈，执行Integer(int)构造方法
2. 执行Integer.intValue()方法，将返回值存储到a0变量上
而代码`Integer a1 = 1;`则是调用了Integer.valueOf(int)方法

##一些容易犯的错误
在得知了装箱与拆箱的原理后，我们在返过头来观察一下基本类型与封装类型的比较操作
还是上诉的代码，在最后加入一个比较操作符`==`
```java
int a0 = new Integer(1000);
Integer a1 = 1000;
if (a0 == a1);
```
继续反编译字节码:
```unicode
 0  new java.lang.Integer [17]
 3  dup
 4  sipush 1000
 7  invokespecial java.lang.Integer(int) [19]
10  invokevirtual java.lang.Integer.intValue() : int [22]
13  istore_1 [a0]
14  sipush 1000
17  invokestatic java.lang.Integer.valueOf(int) : java.lang.Integer [26]
20  astore_2 [a1]
21  iload_1 [a0]
22  aload_2 [a1]
23  invokevirtual java.lang.Integer.intValue() : int [22]
26  if_icmpne 29
29  return
```
发现21行-23行(PC计数器)字节码含义为: 
21 iload_1 [a0] // 将a0加载到操作数栈
22 aload_2 [a1] // 将a1加载到操作数栈
23  invokevirtual java.lang.Integer.intValue() : int [22] // 执行a1.intValue()
26  if_icmpne 29 // 对2个整形数值进行比较

结论是当一个变量为基本类型，一个变量为封装类型使用`==`比较时会把封装类型拆箱成基本类型进行比较

再让我们看一下另一种情况
```java
Integer a0 = 128;
Integer a1 = 128;
assert a0 == a1;
```
这个代码会抛出java.lang.AssertionError异常(需要在JVM参数中设置-ea才能生效，不清楚自行google)
但是如果我修改一下
```java
Integer a0 = 127;
Integer a1 = 127;
assert a0 == a1;
```
就不会抛出异常，为什么会这样呢，我们在之前的字节码中得知了当碰到代码`Integer a0 = 127;`会装箱，装箱的valueOf(int)方法
```java
public static Integer valueOf(int i) {
    if (i >= IntegerCache.low && i <= IntegerCache.high)
        return IntegerCache.cache[i + (-IntegerCache.low)];
    return new Integer(i);
}
```
从源代码中可知，当int值在IntegerCache区间中直接返回区间中的数值，否则新建一个Integer对象返回，然后我们在看一下IntegerCache的源代码:
```java
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
```
从源代码中我们可以知道IntegerCache的默认区间是[-128, 127]，这段源代码是我使用JDK1.8查看的，所以在JDK1.8是我们可以通过配置-Djava.lang.Integer.IntegerCache.high=10000来对IntegerCache的最大区间进行改动(这个值越大的就需要更多的JVM内存)

总结一下:
如果对封装类型直接赋值基本类型会调用valueOf()进行装箱，对他们进行比较的时候还是别直接使用`==`操作符，代替的使用`equals`方法会比较保险，而使用基本类型比较时则不会有这个问题

