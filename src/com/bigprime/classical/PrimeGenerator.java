package com.bigprime.classical;

import java.math.BigInteger;
import java.util.Random;

public class PrimeGenerator implements Runnable {

    private Results results;

    public PrimeGenerator(Results results) {
        this.results = results;
    }

    @Override
    public void run() {
        BigInteger bigInteger = new BigInteger(2500, new Random());
        results.addPrime(bigInteger.nextProbablePrime());
    }
}
