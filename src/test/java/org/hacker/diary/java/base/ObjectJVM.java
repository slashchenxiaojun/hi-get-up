package org.hacker.diary.java.base;

import java.util.ArrayList;
import java.util.List;

public class ObjectJVM {
  static class OomObj {
    // 4M
    int[] a = new int[1024 * 1024];
  }
  public static void main(String[] args) {
    
    int count = 0;
    List<Object> list = new ArrayList<>();
    try {
      while (true) {
        list.add(new OomObj());
        count++;
      }
    } finally {
      System.out.println(count);
    }
  }
}
