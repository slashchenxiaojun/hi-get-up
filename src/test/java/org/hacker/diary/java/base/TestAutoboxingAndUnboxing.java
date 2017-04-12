package org.hacker.diary.java.base;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * jdk1.8-自动装箱、拆箱测试
 * @author Mr.J
 *
 */
public class TestAutoboxingAndUnboxing {
  @Test
  public void integerTest() {
    int i0 = 1000;
    Integer i1 = 1000;
    assertEquals(i0 == i1, true);
    int i2 = new Integer(1000);
    Integer i3 = 1000;
    assertEquals(i2 == i3, true);
    
    Integer a0 = 128;
    Integer a1 = 128;
    assertEquals(a0 == a1, false);
    assertEquals(a0.equals(a1), true);
    Integer a2 = 127;
    Integer a3 = 127;
    assertEquals(a2 == a3, true);
    Integer a4 = new Integer(127);
    Integer a5 = new Integer(127);
    assertEquals(a4 == a5, false);
  }
  
  @Test
  public void floatDouble() {
    Double a = Math.sqrt(-1.0);  
    Double b = 0.0d / 0.0d;  
    Double c = a + 200.0d;  
    Double d = b + 1.0d;  
    System.out.println(a.equals(b));  
    System.out.println(b.equals(c));  
    System.out.println(c.equals(d));  
    assertEquals(0.0 == -0.01, true);
  }
}
