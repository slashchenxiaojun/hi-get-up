在java中有"静态分派"和  "动态分派"
所谓的分派既是JVM如何确定调用哪个方法，分派是面向对象-多态的一种实现
多态是面向对象编程的一种特性，同一操作作用于不同的对象，可以有不同的解释，产生不同的执行结果
```java
abstract class Animal {
  public abstract void noise();
}
class Cat extends Animal {
  public void noise() {
    System.out.println("喵");
  }
}
class Dog extends Animal {
  public void noise() {
    System.out.println("汪");
  }
}
main() {
  Animal cat = new Cat();
  Animal dog = new Dog();
  cat.noise(); // 喵
  dog.noise(); // 汪
}
```
上诉代码属于动态分派-也是重写的实现