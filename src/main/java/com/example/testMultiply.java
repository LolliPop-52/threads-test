package com.example;

import java.util.concurrent.atomic.AtomicInteger;

public class testMultiply {
    public static void test(String[] args) {
        long start = System.nanoTime();
        int countOfThreads = 10;
        Thread[] threads = new Thread[countOfThreads];

        Counter counter = new Counter();

        for (int i = 0; i < countOfThreads; i++) {
            int threadIndex = i;
            threads[threadIndex]  = new Thread(() -> {
                for (int j = 0; j < 100_000; j++) {
                    counter.increment();
                }
                System.out.println("Поток [" + threadIndex + "] -> отработал");
            });
            threads[threadIndex].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Ожидаем: " + (10 * 100_000));
        System.out.println("Получили: " + counter.getCount());

        long end = System.nanoTime();
        System.out.println("Время: " + (end - start) / 1_000_000 + " ms");
    }
}

class Counter{
    private int count = 0;
    public synchronized void increment() {
        count++;
    }
    public int getCount() {
        return count;
    }
}

class Counter1{
    private AtomicInteger count = new AtomicInteger(0);
    public void increment() {
        count.incrementAndGet();
    }
    public int getCount() {
        return count.get();
    }
}

class Counter2 {
    private int count = 0;
    public void increment() {
        synchronized (this) {
            count++;
        }
    }
    public int getCount() {
        return count;
    }
}
