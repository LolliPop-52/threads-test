package com.example;

public class SayPingPong {
    private volatile static int state = 0;
    public final static int N = 100;

    public static void test1(String[] args) {
        Thread thread1 = new Thread(() -> {
            try {
                sayPing();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                sayPong();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread thread3 = new Thread(() -> {
            try {
                sayPeng();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void sayPing() throws InterruptedException{
        for(int i = 0; i < N; i++){
            synchronized (SayPingPong.class){
                while(state != 0){
                    SayPingPong.class.wait();
                }

                System.out.println("PING");
                state = 1;
                SayPingPong.class.notifyAll();
            }
        }
    }

    public static void sayPong() throws InterruptedException{
        for (int i = 0; i < N; i++) {
            synchronized (SayPingPong.class) {
                while (state != 1) {
                    SayPingPong.class.wait();
                }

                System.out.println("PONG");
                state = 2;
                SayPingPong.class.notifyAll();
            }
        }
    }

    public static void sayPeng() throws InterruptedException{
        for (int i = 0; i < N; i++) {
            synchronized (SayPingPong.class) {
                while (state != 2) {
                    SayPingPong.class.wait();
                }

                System.out.println("PENG");
                state = 0;
                SayPingPong.class.notifyAll();
            }
        }
    }
}