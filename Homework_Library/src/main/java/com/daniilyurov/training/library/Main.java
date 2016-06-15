package com.daniilyurov.training.library;

import com.daniilyurov.training.library.reader.John;
import com.daniilyurov.training.library.reader.Matt;
import com.daniilyurov.training.library.reader.Reader;
import com.daniilyurov.training.library.reader.Vanya;

public class Main {
    public static void main(String[] args) {
        Reader vanya = new Vanya();
        Reader matt = new Matt();
        Reader john = new John();

        vanya.start();
        matt.start();
        john.start();
    }
}
