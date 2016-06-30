package com.daniilyurov.training.concurrency.integral;

public interface Threadable {
    void execute(int threads) throws Exception;
    int getMaxThreads();
}
