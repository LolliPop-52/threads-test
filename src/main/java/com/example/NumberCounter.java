package com.example;

import java.util.Arrays;
import java.util.Random;

public class NumberCounter {
    final static int numberOfThreads = 8;

    final static int length = 1_000_000_000;
    final static int[] numbers = getRandomIntArray(length);


    public static void test(String[] args) {
        Thread[] threads = new Thread[numberOfThreads];
        long[] results = new long[numberOfThreads];

        for(int i = 0; i < numberOfThreads; i++) {
            final int threadIndex = i;
            threads[threadIndex] = new Thread(() -> {
                long sum = calculatePartialSum(threadIndex);
                results[threadIndex] = sum;
                System.out.println("Поток [" + (threadIndex + 1) + "] -> закончил расчет (" + sum + ")");
            });

            threads[threadIndex].start();
        }

        for (int i = 0; i < numberOfThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        long totalSum = 0;
        for (long res : results) {
            totalSum += res;
        }

        long curTotalSum = Arrays.stream(numbers)
                .mapToLong(i -> i)
                .sum();
        System.out.println("Правильный результат: " + curTotalSum);
        System.out.println("Результат потоков --> " + totalSum);
    }

    public static int[] getRandomIntArray(int length) {
        return new Random().ints(length, 1, 101).toArray();
    }


    public static long calculatePartialSum(int numberOfPart) {
        int baseSize = length / numberOfThreads;
        int remainder = length % numberOfThreads;

        int min = numberOfPart * baseSize + Math.min(numberOfPart, remainder);
        int currentSize = baseSize + (numberOfPart < remainder ? 1 : 0);
        int max = min + currentSize;

        long sum = 0;
        for (int i = min; i < max; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static int calculateSum(int[] values){
        int result = 0;
        for (int value : values) {
            result += value;
        }
        return result;
    }
}
