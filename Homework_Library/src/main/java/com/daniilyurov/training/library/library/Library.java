package com.daniilyurov.training.library.library;

import java.util.HashMap;
import java.util.Map;

public class Library {

    private Map<BookTitle, Book> entities;

    private static final Library instance = new Library();

    private Library() {
        entities = new HashMap<>();
        entities.put(BookTitle.WAR_AND_PEACE, new Book(BookTitle.WAR_AND_PEACE.name));
        entities.put(BookTitle.EFFECTIVE_JAVA, new Book(BookTitle.EFFECTIVE_JAVA.name));
        entities.put(BookTitle.CONCURRENCY_IN_PRACTICE, new Book(BookTitle.CONCURRENCY_IN_PRACTICE.name));
        entities.put(BookTitle.ANNA_KARENINA, new Book(BookTitle.ANNA_KARENINA.name));
    }

    public enum BookTitle {
        WAR_AND_PEACE("War and Peace"),
        EFFECTIVE_JAVA("Effective Java"),
        CONCURRENCY_IN_PRACTICE("Concurrency in Practice"),
        ANNA_KARENINA("Anna Karenina");

        public String name;
        private BookTitle(String name) {
            this.name = name;
        }
    }

    public static Library getInstance() {
        return instance;
    }

    public Book get(BookTitle name, boolean home) throws BookAlreadyTakenException, InterruptedException {
        Book book = entities.get(name);
        if (book == null) {
                throw new IllegalArgumentException("No such book exists");
        }
        if (home) book.takeHome();
        else book.takeToRead();
        return book;
    }

    public void bringBack(Book book) {
        book.bringBack();
    }

}
