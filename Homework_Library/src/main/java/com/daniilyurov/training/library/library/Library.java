package com.daniilyurov.training.library.library;

import java.util.HashMap;
import java.util.Map;

public class Library {

    private Map<String, Book> entitiesAvailable;
    private Map<String, Book> entitiesTaken;

    private static final Library instance = new Library();

    private Library() {
        entitiesTaken = new HashMap<>();
        entitiesAvailable = new HashMap<>();
        entitiesAvailable.put("War and Peace", new Book("War and Peace"));
        entitiesAvailable.put("Effective Java", new Book("Effective Java"));
        entitiesAvailable.put("Concurrency in Practice", new Book("Concurrency in Practice"));
        entitiesAvailable.put("Anna Karenina", new Book("Anna Karenina"));
    }

    public static Library getInstance() {
        return instance;
    }

    public synchronized Book get(String name, boolean home) throws BookAlreadyTakenException {
        Book book = entitiesAvailable.remove(name);
        if (book == null) {
            Book takenEntity = entitiesTaken.get(name);
            if (takenEntity == null) {
                throw new IllegalArgumentException("No such book exists");
            } else {
                String holder = takenEntity.getHolderName();
                String location = (takenEntity.isAway()) ? "at home" : "in the library";
                throw new BookAlreadyTakenException(holder + " took " + name + " to read it " + location);
            }
        }
        if (home) book.takeHome();
        else book.takeToRead();
        entitiesTaken.put(book.getName(), book);
        return book;
    }

    public synchronized void bringBack(Book book) {
        book.bringBack();
        entitiesAvailable.put(book.getName(), book);
        entitiesTaken.remove(book.getName());
    }

}
