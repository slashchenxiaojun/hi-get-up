package org.hacker.diary.java.base.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierAndCountDownLatch {
  public void cyclicBarrierDemo() throws InterruptedException, BrokenBarrierException {
    final CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
      public void run() {
        System.out.println("all thread run.");
      }
    });
    
    Thread thread01 = new Thread(new Runnable() {
      public void run() {
        try {
          System.out.println("thread01 wait.");
          cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
          e.printStackTrace();
        }
      }
    });
    
    Thread thread02 = new Thread(new Runnable() {
      public void run() {
        try {
          System.out.println("thread02 wait.");
          cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
          e.printStackTrace();
        }
      }
    });
    
    thread01.start();
    thread02.start();
  }
  
  public void CountDownLatchDemo(CountDownLatch countDownLatch) throws InterruptedException {
    Thread thread01 = new Thread(new Runnable() {
      public void run() {
        System.out.println("thread01 wait.");
        countDownLatch.countDown();
      }
    });
    
    Thread thread02 = new Thread(new Runnable() {
      public void run() {
        System.out.println("thread02 wait.");
        countDownLatch.countDown();
      }
    });
    
    thread01.start();
    thread02.start();
    countDownLatch.await();
    System.out.println("all thread finish.");
  }
  
  public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
    CyclicBarrierAndCountDownLatch test = new CyclicBarrierAndCountDownLatch();
    CountDownLatch countDownLatch = new CountDownLatch(2);
    test.cyclicBarrierDemo();
    test.CountDownLatchDemo(countDownLatch);
    test.CountDownLatchDemo(countDownLatch);
  }
}
