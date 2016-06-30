package com.daniilyurov.training.concurrency.integral;

import static java.lang.Math.*;

public class IntegralCalculator {

    public static double calculate(Function function, double begin, double end, int precision, int threads)
            throws Exception {

        if (function == null) {
            throw new NullPointerException();
        }

        if (begin >= end) {
            throw new IllegalArgumentException("Begin should be smaller than end");
        }

        if (precision < threads) {
            throw new IllegalArgumentException("There should be less threads than precision.");
        }

        IntegralThread[] threadPool = new IntegralThread[threads];
        double result = 0;

        double delta = Math.abs(end - begin) / threads;
        precision = precision / threads;

        for (int i = 0; i < threads; i++) {
            IntegralThread thread = new IntegralThread(begin + delta * i, begin + delta * (i + 1), precision, function);
            threadPool[i] = thread;
            thread.start();
        }

        for (IntegralThread thread : threadPool) {
            try {
                thread.join();
                result += thread.getResult();
            } catch (InterruptedException e) {
                throw new Exception(e);
            }
        }

        return abs(round(result));
    }
}