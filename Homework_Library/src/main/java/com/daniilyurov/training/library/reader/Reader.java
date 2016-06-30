package com.daniilyurov.training.library.reader;

import com.daniilyurov.training.library.library.Book;
import com.daniilyurov.training.library.library.BookAlreadyTakenException;
import com.daniilyurov.training.library.library.Library;

import java.beans.IntrospectionException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public abstract class Reader extends Thread {

    protected final boolean READ_AT_HOME = true;
    protected final boolean READ_IN_THE_LIBRARY = false;

    protected Library library = Library.getInstance();
    protected Map<Library.BookTitle, Book> booksTaken = new HashMap<>();

    protected void getBook(Library.BookTitle title, boolean home) {
        try {
            Book book = library.get(title, home);
            booksTaken.put(title, book);
            System.out.println(getTime() + getName() + " asked for " + title.name + " and the librarian gave him " + book.getName());
        } catch (BookAlreadyTakenException e) {
            System.out.println(getTime() + getName() + " asked for a book but " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(getTime() + getName() + " asked for a book that does not exist.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void returnBook(Library.BookTitle title) {
        Book book = booksTaken.remove(title);
        if (book == null) {
            System.out.println(getTime() + getName() + " tried to return " + title.name + " but he never took it.");
        } else {
            library.bringBack(book);
            System.out.println(getTime() + getName() + " returned " + title.name);
        }
    }

    protected void read(long millis, Library.BookTitle... titles) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getTime() {
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ss:n");
        return formatter.format(time) + " ";
    }
}
