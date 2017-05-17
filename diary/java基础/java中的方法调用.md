java是符合面向对象的编程语言，所以它满足面向对象的封装、继承、多态特性

方法调用并不等于方法执行，方法调用阶段唯一的任务就是确定被调用方法的版本(即调用哪一个方法)
在程序运行时，进行方法调用是最普遍、最频繁的操作，但是Class文件的编译过程不包括传统编译中的连接步骤，一切方法调用在Class文件里面存储的都只是符号引用，而不是方法在实际运行时内存布局中的入口地址。这个特性给Java带来了更强大的动态扩展能力，但也使得Java方法调用过程变得相对复杂起来，需要在类加载期间，甚至到运行期间才能确定目标方法的直接引用。

所有方法调用中的目标方法在Class文件里面都是一个常量池中的引用，在类加载的解析阶段，会将其中的一部分符号引用转化为直接引用。这种解析能成立的前提是：方法在程序真正执行之前就有一个可确定的调用版本，并且这个方法的调用版本在运行期是不可改变的。换句话说，调用目标在程序代码写好、编译器进行编译时就必须确定下来，这类方法的调用称为解析。

在Java语言中符合"编译期可知，运行期不可变"这个要求的方法，主要包括静态方法和私有方法两大类，前者与类型直接关联，后者在外部不可被访问，这两种方法各自的特点决定了他们不可能通过继承或别的方式重写其他版本，因此他们适合在类加载阶段进行解析。
静态方法、私有方法、实例构造器、父类方法。这些方法称为非虚方法，它们在类加载的时候就会把符号引用解析为该方法的直接引用。与之相反，其他方法称为虚方法(除去final方法)。

##静态分派(方法的重载Overloading)
```java
public class StaticDispatch {
  static abstract class Human {}
  
  static class Man extends Human {}
  
  static class Woman extends Human {}
  
  public static void sayHello(Human guy) {
    System.out.println("hello,guy!");
  }
  
  public static void sayHello(Man guy) {
    System.out.println("hello,gentlemen!");
  }
  
  public static void sayHello(Woman guy) {
    System.out.println("hello,lady!");
  }
  
  public static void main(String[] args) {
    Human man = new Man();
    Human woman = new Woman();
    sayHello(man);
    sayHello(woman);
  }
}
```
Human man = new Man();
我们把"Human"称为变量的`静态类型`，后面的"Man"称为变量的`实际类型`，静态类型和实际类型在程序中都可以发生一些变化，区别是静态类型的变化仅仅在使用时发生，变量本身的静态类型不会被改变，并且最终的静态类型在编译器可知；而实际类型变化的结果在运行期才确定，编译器在编译期并不知道一个对象的实际类型是什么。

编译器在重载时是通过参数的`静态类型`而不是`实际类型`作为判定的依据。并且静态类型在编译期可知，因此，编译阶段，Javac编译器会根据参数的静态类型决定使用哪个重载版本。

所有依赖静态类型来定位方法执行版本的分派动作称为静态分派。静态分派的典型应用就是方法重载。

##动态分派(方法的重写Overriding)

```java
public class DynamicDispatch {
  static abstract class Human {
    protected abstract void sayHello();
  }

  static class Man extends Human {
    @Override
    protected void sayHello() {
      System.out.println("man say hello!");
    }
  }

  static class Woman extends Human {
    @Override
    protected void sayHello() {
      System.out.println("woman say hello!");
    }
  }

  public static void main(String[] args) {
    Human man = new Man();
    Human woman = new Woman();
    man.sayHello(); // man say hello!
    woman.sayHello(); // woman say hello!
  }
}
```
###invokevirtual
1、找到操作数栈顶的第一个元素所指向的对象的实际类型，记作C。
2、如果在类型C中找到与常量中的描述符和简单名称相符合的方法，然后进行访问权限验证，如果验证通过则返回这个方法的直接引用，查找过程结束；如果验证不通过，则抛出java.lang.IllegalAccessError异常。
3、否则未找到，就按照继承关系从下往上依次对类型C的各个父类进行第2步的搜索和验证过程。
4、如果始终没有找到合适的方法，则跑出java.lang.AbstractMethodError异常。

由于invokevirtual指令执行的第一步就是在运行期确定接收者的实际类型，所以两次调用中的invokevirtual指令把常量池中的类方法符号引用解析到了不同的直接引用上，这个过程就是Java语言方法重写的本质。我们把这种在运行期根据实际类型确定方法执行版本的分派过程称为动态分派。