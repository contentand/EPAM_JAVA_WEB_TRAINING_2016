package com.daniilyurov.training.concurrency.integral;

public class DefinedIntegralCalculator implements Threadable {

    private Function function;
    private double begin;
    private double end;
    private int precision;

    public DefinedIntegralCalculator(Function function, double begin, double end, int precision) {
        this.function = function;
        this.begin = begin;
        this.end = end;
        this.precision = precision;
    }

    @Override
    public void execute(int threads) {
        try {
            IntegralCalculator.calculate(function, begin, end, precision, threads);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getMaxThreads() {
        return precision;
    }
}
