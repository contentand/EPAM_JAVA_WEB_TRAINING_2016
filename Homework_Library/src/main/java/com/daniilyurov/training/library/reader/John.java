package com.daniilyurov.training.library.reader;

public class John extends Reader {
    public John() {
        setName("John");
    }

    @Override
    public void run() {
        try {
            boolean took = false;
            while (!took) {
                Thread.sleep(400);
                took = tryToGetBook("Effective Java", READ_AT_HOME);
            }
            Thread.sleep(1000);
            returnBook("Effective Java");

            took = false;
            while (!took) {
                Thread.sleep(4);
                took = tryToGetBook("War and Peace", READ_IN_THE_LIBRARY);
            }
            Thread.sleep(50);
            returnBook("War and Peace");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
