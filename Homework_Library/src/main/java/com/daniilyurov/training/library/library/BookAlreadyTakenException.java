package com.daniilyurov.training.library.library;

public class BookAlreadyTakenException extends Exception {
    public BookAlreadyTakenException(String s) {
        super(s);
    }
}
