package com.bigprime.classical;

import java.math.BigInteger;
import java.util.Random;
import java.util.TreeSet;

public class SingleThreadedBigPrimes {

    public static void main(String[] args) {

        final long start = System.currentTimeMillis();

        final TreeSet<Object> primes = new TreeSet<>();

        while (primes.size() < 20) {

            BigInteger bigInteger = new BigInteger(2500, new Random());
            primes.add(bigInteger.nextProbablePrime());
        }

        final long end = System.currentTimeMillis();

        System.out.println(primes);

        System.out.println("The time taken was " + (end - start) / 1000 + " secs");
    }
}
