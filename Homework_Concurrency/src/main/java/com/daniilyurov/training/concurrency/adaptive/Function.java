package com.daniilyurov.training.concurrency.adaptive;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.*;

public class Function implements Runnable {

    private double begin;
    private double end;
    private double epsilon;
    private double result;

    public Function(double begin, double end, double epsilon) {
        this.begin = begin;
        this.end = end;
        this.epsilon = epsilon;
    }

    @Override
    public void run() {
        if (abs(end - begin) > epsilon) {
            double c = begin + (end - begin) / 2;

            if (function(begin) * function(c) < 0) {
                calc(new Function(begin, c, epsilon));
            }
            else {
                calc(new Function(c, end, epsilon));
            }
        }
        else {
            if (abs(function(begin)) < 0.01) {
                result = round(begin, 2);
            }
            else {
                result = Double.NaN;
            }

        }
    }

    private void calc(Function f) {
        Thread t = new Thread(f);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = f.result;
    }

    public static double function(double x) {
        return (-x/2)*(x - 29);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static void main(String[] args) throws InterruptedException {
        Function f = new Function(-1, 5, .001);
        Thread t = new Thread(f);
        t.start();
        t.join();
        System.out.println(f.result);
    }
}
