package com.example;

import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {

    }
}

class MyBlockingQueue<T> {
    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    public MyBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void put(T item) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }

        // если очередь полная — ждать, пока не освободится место
        // положить элемент
        // разбудить тех, кто ждёт в take()
    }

    public synchronized T take() throws InterruptedException {
        // если очередь пустая — ждать, пока не появится элемент
        // забрать элемент
        // разбудить тех, кто ждёт в put()
        return null; // заглушка
    }

    public synchronized int size() {
        return queue.size();
    }
}