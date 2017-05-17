package org.hacker.diary.java.base;

public class ClassInit {
  public static void main(String[] args) throws IllegalAccessException {
    new Son();
    System.out.println("---");
    new Son(2);
    System.out.println(SizeOfObject.fullSizeOf(new Son()));
  }
}

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
  String c = "final in JVM heap > JDK1.7";
  String d;
  String e;
  Object obj;
  static {
    System.out.println("Son static");
  }
  {
    System.out.println("Son");
  }
  public Son() {
    System.out.println("init son");
    System.out.println(d);
    System.out.println(e);
    System.out.println(d == e);
  }
  public Son(int a) {
    System.out.println("init son - args");
  }
  public int m(int a, int b) {
    return a + b;
  }
}