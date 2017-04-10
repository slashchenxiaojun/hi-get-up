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
    int a = 1;
    
    int b = 1;
    float f = 1.0f;
    double d = 2.0;
    long l = 1;
    assertEquals(a == b, true);
    assertEquals(1 == new Integer(1), true);
    assertEquals(1000 == new Integer(1000), true);
  }
}
