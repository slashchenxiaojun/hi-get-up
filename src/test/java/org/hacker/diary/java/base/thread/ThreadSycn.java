package org.hacker.diary.java.base.thread;

public class ThreadSycn {
  int i;
  Object LOCK;
  public void add() {
    synchronized (this) {
      i++;
    }
  }
  public void addByLock() {
    synchronized (LOCK) {
      i++;
    }
  }
}
