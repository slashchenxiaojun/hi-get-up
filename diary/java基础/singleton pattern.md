>单例模式(singleton pattern)是一种非常常见的设计模式，它允许类只拥有唯一一个实例，优点是避免了每次都需要创建对象的系统开销，缺点的话大概就是不能被CG回收吧，但在实际开发中单例模式有种被滥用的趋势，曾经有朋友跟我讨论过关于单例的使用，他认为只要存在频繁调用的类都应该使用单例从而避免创建对象的开销，咋一听觉得没什么问题，但是创建一个类真的需要如此多的系统开销吗？java不是拥有垃圾收集器吗？单例模式的滥用不就否定了垃圾回收这一个概念了(有种画蛇添足的含义在里面)

##类的创建

类的创建生命周期有2个部分:
第一个部分是类加载，它涉及到了class字节码的加载、验证、准备、解析。加载过程暂时不在本文的讨论之内
第二个部分是类的初始化，这个过程就是普通开发者能感知到的真正开始执行java代码的地方，比如调用构造方法等。我们讨论的类的创建就是这个部分(当然实际情况是类的解析可能在初始化之后)

类的初始化过程为
1. 初始化静态变量
2. 执行化静态语句块
----静态的语句只会执行一次
3. 执行语句块
4. 执行构造方法
```java
class Son {
  static int a;
  static int b = 1;
  static {
    System.out.println("Son static");
  }
  {
    System.out.println("Son");
  }
  public Son() {
    System.out.println("init son");
  }
  public Son(int a) {
    System.out.println("init son - args");
  }
  public static void main(String[] args) {
    new Son();
    System.out.println("---");
    new Son(2);
  }
}
```
输出
```java
Son static // 因为是静态代码块所以只会执行一次
Son // 普通的代码块，每次构造一个新的对象(new、反射)都会执行一遍，这个写法相对来说比较少见
init son // 执行构造方法
---
Son
init son - args
```
这个一个类初始化的过程，如果一个类继承了一个父类，那么在上诉的3，4步会优先执行父类步骤，静态代码块和变量还是执行一次
```java
class Grandfather {
  static {
    System.out.println("Grandfather static");
  }
  {
    System.out.println("Grandfather");
  }
  public Grandfather() {
    System.out.println("init Grandfather");
  }
  public Grandfather(int a) {
    System.out.println("init Grandfather - args");
  }
}
class Father extends Grandfather {
  static {
    System.out.println("Father static");
  }
  {
    System.out.println("Father");
  }
  public Father() {
    System.out.println("init Grandfather");
  }
  public Father(int a) {
    System.out.println("init Father - args");
  }
}
class Son extends Father {
  static int a;
  static int b = 1;
  static {
    System.out.println("Son static");
  }
  {
    System.out.println("Son");
  }
  public Son() {
    System.out.println("init son");
  }
  public Son(int a) {
    System.out.println("init son - args");
  }
  public static void main(String[] args) {
    new Son();
    System.out.println("---");
    new Son(2);
  }
}
```
现在我们让Son继承Father而Father继承Grandfather
```java
Grandfather static
Father static
Son static // 静态代码块执行一次
Grandfather // 代码块优先构造方法执行
init Grandfather // 父类的构造方法
Father // 父类代码块
init Grandfather // 父类的构造方法
Son
init son
---
Grandfather
init Grandfather // 执行的父类无参构造方法
Father
init Grandfather
Son
init son - args
```
这里值得注意的是如果子类中没有显示的调用super(a);执行的是父类默认的无参构造方法，通过字节码观察得出如果子类没有显示的调用super()方法，会默认添加
```java
public Son() {
  System.out.println("init son");
}
```
同等于
```java
public Son() {
  super();
  System.out.println("init son");
}
```
###类初始化的大小
在类加载检查过后，接下来为对象在JVM堆中分配内存，并且对象所需要分配的多大内存在类加载过程中就已经确定了

对象在内存的存储布局中包括：对象头、实例数据、对齐填充

对象头(Header)
1. 存储对象自身的运行时数据，比如哈希码、GC分代年龄等；
2. 类型指针：通过这个指针确定这个对象属于哪个类。

实例数据(Instance Data)
存储代码中定义的各种类型的字段内容。

对齐填充(Padding)这部分信息没有任何意义，仅仅是为了使得对象占的内存大小为8字节的整数倍。

所有相对来说一个对象的头所占用的数据是很小的

##原型or单例
通过类的初始化和创建类的大小，我们分别从时间，和空间上了解了一个类创建一个对象需要的代价，开发者可以根据实际情况来判断是否需要使用单例设计模式。
对于一个类如果他的所有的父类和自己(递归寻找所有的父类)代码块，构造方法耗时比较长，可以考虑使用单例
对于一个类会被频繁的调用，可以考虑使用单例
除非你想让JVM的heap中永久存放对象，不然实在是想不出任何一个理由在去使用单例模式了