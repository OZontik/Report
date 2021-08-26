package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        List<int[]> prod = new ArrayList<>();
        prod.add(positiveArray(20000, 40000, 700000));
        prod.add(positiveArray(8000, 60000, 300000));
        prod.add(positiveArray(5000, 2000, 800000));

        LongAdder revenue = new LongAdder();

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        prod.forEach(ints -> executorService.submit(() -> {
            int sum = Arrays.stream(ints).reduce(0, Integer::sum);
            revenue.add(sum);
        }));
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Выручка: " + revenue);
    }

    public static int[] positiveArray(int size, int min, int max) {
        int[] prod = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            prod[i] = random.nextInt((max - min) + 1) + min;
        }
        return prod;
    }
}
