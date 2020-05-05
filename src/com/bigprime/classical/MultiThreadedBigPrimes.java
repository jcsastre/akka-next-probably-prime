package com.bigprime.classical;

import java.util.ArrayList;
import java.util.List;

public class MultiThreadedBigPrimes {

    public static void main(String[] args) throws InterruptedException {

        final long start = System.currentTimeMillis();

        Results results = new Results();
        Runnable task = new PrimeGenerator(results);

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            final Thread t = new Thread(task);
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        final long end = System.currentTimeMillis();

        System.out.println("The time taken was " + (end - start) / 1000 + " secs");
    }
}
