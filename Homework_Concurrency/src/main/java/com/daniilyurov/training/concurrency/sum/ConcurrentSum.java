package com.daniilyurov.training.concurrency.sum;

import java.util.Arrays;

public class ConcurrentSum {
    public static int sum(int threads, int ... values) {
        Result result = new Result();
        if (threads > values.length) threads = values.length;
        int load = values.length / threads;

        if (values.length % threads == 1) {
            result.add(values[values.length - 1]);
        }

        for (int i = 0; i < threads; i++) {
            addInAThread(result, Arrays.copyOfRange(values, load * i, load * (i + 1)));
        }

        return result.get();

    }

    private static void addInAThread(final Result result, int ... values) {

        Thread th = new Thread(() -> {
            for (int value : values) {
                result.add(value);
            }
        });

        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static class Result{
        int result;

        int get() {
            return result;
        }

        public synchronized void add(int value) {
            this.result += value;
        }
    }
}
