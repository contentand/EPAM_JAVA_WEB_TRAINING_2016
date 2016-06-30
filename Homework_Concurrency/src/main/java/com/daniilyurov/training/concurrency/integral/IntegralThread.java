package com.daniilyurov.training.concurrency.integral;

public class IntegralThread extends Thread {

    private double begin;
    private double end;
    private int precision;
    private Function function;
    private double result;
    
    public IntegralThread(double begin, double end, int precision, Function function) {
        this.begin = begin;
        this.end = end;
        this.precision = precision;
        this.function = function;
    }

    @Override
    public void run() {
        double delta = getDelta();

        for (int i = 0; i < precision; i++) {
            result += trapezeArea(function, begin + i * delta, begin + (i + 1) * delta, delta);
        }
    }

    private static double trapezeArea(Function f, double x0, double x1, double delta) {
        return delta * (f.function(x0) + f.function(x1)) / 2;
    }

    private double getDelta() {
        return Math.abs(end - begin) / precision;
    }

    public double getResult() {
        return result;
    }
}
