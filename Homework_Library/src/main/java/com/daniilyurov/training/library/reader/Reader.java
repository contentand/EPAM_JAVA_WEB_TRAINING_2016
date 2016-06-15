package com.daniilyurov.training.library.reader;

import com.daniilyurov.training.library.library.Book;
import com.daniilyurov.training.library.library.BookAlreadyTakenException;
import com.daniilyurov.training.library.library.Library;

import java.util.HashMap;
import java.util.Map;

public abstract class Reader extends Thread {

    protected final boolean READ_AT_HOME = true;
    protected final boolean READ_IN_THE_LIBRARY = false;

    protected Library library = Library.getInstance();
    protected Map<String, Book> booksTaken = new HashMap<>();

    protected boolean tryToGetBook(String name, boolean home) {
        try {
            Book book = library.get(name, home);
            booksTaken.put(name, book);
            System.out.println(getName() + " asked for " + name + " and the librarian gave him " + book.getName());
            return true;
        } catch (BookAlreadyTakenException e) {
            System.out.println(getName() + " asked for a book but " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(getName() + " asked for a book that does not exist.");
        }
        return false;
    }

    protected void returnBook(String name) {
        Book book = booksTaken.remove(name);
        if (book == null) {
            System.out.println(getName() + " tried to return " + name + " but he never took it.");
        } else {
            library.bringBack(book);
            System.out.println(getName() + " returned " + name);
        }
    }

}
