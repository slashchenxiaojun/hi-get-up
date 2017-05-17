package org.hacker.diary.java.base;

public class JavaOperator {
  public static void main(String[] args) {
    System.out.println( toBinaryString(1829164700) );
    System.out.println( toBinaryString(1829164700 >>> 16) );
    System.out.println( toBinaryString(1829164700 ^ (1829164700 >>> 16)) );
    System.out.println( toBinaryString(-Integer.MAX_VALUE) );
    System.out.println( toBinaryString(-Integer.MAX_VALUE >> 16) );
    System.out.println( toBinaryString(Integer.MAX_VALUE) );
    System.out.println( toBinaryString(Integer.MAX_VALUE >> 16) );
  }
  
  public static String toBinaryString(int i) {
    String binary = Integer.toBinaryString(i);
    if (binary.length() < 32) {
      while (32 - binary.length() != 0)
        binary = "0" + binary;
    }
    char[] newChar = new char[32 + 3];
    int n = 0;
    for (int j = 0; j < 32; j++) {
      if (j != 0 && j % 8 == 0) newChar[n++] = ' ';
      newChar[n++] = binary.charAt(j);
    }
    return new String(newChar);
  }
}
