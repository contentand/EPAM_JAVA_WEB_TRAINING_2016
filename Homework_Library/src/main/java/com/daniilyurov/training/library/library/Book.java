package com.daniilyurov.training.library.library;

import com.daniilyurov.training.library.reader.Reader;

// Class is thread-safe because all modifiable fields can only be modified by thread-safe Library.
public class Book {

    private final String bookName;
    private Reader holder;
    private boolean isOutsideTheBuilding;

    protected Book(String bookName) {
        this.bookName = bookName;
    }

    protected boolean isAway() {
        return isOutsideTheBuilding;
    }

    protected String getHolderName() {
        if (holder == null) {
            return null;
        }
        return holder.getName();
    }

    public String getName() {
        return bookName;
    }

    protected void takeHome() {
        this.holder = (Reader) Thread.currentThread();
        this.isOutsideTheBuilding = true;
    }

    protected void takeToRead() {
        this.holder = (Reader) Thread.currentThread();
    }

    protected void bringBack() {
        this.holder = null;
        this.isOutsideTheBuilding = false;
    }
}
