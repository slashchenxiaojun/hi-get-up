package org.hacker.diary.java.base;

public class HashCode {
  public static void main(String[] args) {
    Integer a = 10;
    Integer b = 10;
    System.out.println(a.hashCode());
    System.out.println(b.hashCode());
    String s1 = "hello";
    String s2 = "hello";
    System.out.println(s1.hashCode());
    System.out.println(s2.hashCode());
    System.out.println(s1 == s2);
    s2 = new String("hello");
    System.out.println(s2.hashCode());
    System.out.println(s2 == s1);
    System.out.println(s2.equals(s1));
    Animal animal0 = new Animal("Animal0");
    Animal animal1 = new Animal("Animal0");
    System.out.println(animal0 == animal1);
    System.out.println(animal0.equals(animal1));
  }
}
class Animal {
  String name;
  public Animal(String name) {
    super();
    this.name = name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Animal other = (Animal) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
  
}