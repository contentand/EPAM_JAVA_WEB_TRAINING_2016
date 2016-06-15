package com.daniilyurov.training.library.reader;

import com.daniilyurov.training.library.reader.Reader;

public class Matt extends Reader {
    public Matt() {
        setName("Matt");
    }

    @Override
    public void run() {
        try {
            boolean took = false;
            while (!took) {
                Thread.sleep(400);
                took = tryToGetBook("War and Peace", READ_AT_HOME);
            }
            Thread.sleep(1000);
            returnBook("War and Peace");

            took = false;
            while (!took) {
                Thread.sleep(4);
                took = tryToGetBook("Effective Java", READ_IN_THE_LIBRARY);
            }
            Thread.sleep(50);
            returnBook("Effective Java");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
