#equals && ==
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
由于在java中有装箱和拆箱的机制，所有的基本类型都有其对应的类
byte(1 byte) -> Byte
short(2 byte) -> Short
int(4 byte) -> Integer
long(8 byte) -> Long

float(4 byte) -> Float
double(8 byte) -> Double

char(2 byte) -> NaN

boolean(1 byte) -> Boolean

java中有8种基本数据类型