package com.daniilyurov.training.concurrency.integral;

/**
 * Created by Daniel Yurov on 30.06.2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println(OptimalThreadCountFounder.getOptimalThreadCount(new DefinedIntegralCalculator(x -> x, -5, 5, 10)));
    }
}
