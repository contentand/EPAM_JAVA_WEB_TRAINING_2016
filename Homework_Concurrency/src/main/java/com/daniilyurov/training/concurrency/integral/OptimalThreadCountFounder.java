package com.daniilyurov.training.concurrency.integral;

public class OptimalThreadCountFounder {

    public static int getOptimalThreadCount(Threadable algo) {
        long optimal = Long.MAX_VALUE;
        int threads = -1;

        for(int i = 1; i <= algo.getMaxThreads(); i++) {
            try {
                long start = System.currentTimeMillis();
                algo.execute(i);
                long delta = System.currentTimeMillis() - start;
                if (delta < optimal) {
                    optimal = delta;
                    threads = i;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return threads;
    }
}
