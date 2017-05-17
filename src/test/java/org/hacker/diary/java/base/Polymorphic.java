package org.hacker.diary.java.base;

public class Polymorphic {
  static abstract class Animal {
    public abstract void noise();
  }
  static class Cat extends Animal {
    public void noise() {
      System.out.println("喵");
    }
  }
  static class Dog extends Animal {
    public void noise() {
      System.out.println("汪");
    }
  }
  public static void main(String[] args) {
    Animal cat = new Cat();
    Animal dog = new Dog();
    cat.noise();
    dog.noise();
  }
}
