package com.daniilyurov.training.library.reader;

import static com.daniilyurov.training.library.library.Library.BookTitle.*;
public class Matt extends Reader {
    public Matt() {
        setName("Matt");
    }

    @Override
    public void run() {

        getBook(CONCURRENCY_IN_PRACTICE, READ_AT_HOME);
        read(300, CONCURRENCY_IN_PRACTICE);

        getBook(EFFECTIVE_JAVA, READ_IN_THE_LIBRARY);
        read(20, EFFECTIVE_JAVA);
        returnBook(EFFECTIVE_JAVA);

        read(100, CONCURRENCY_IN_PRACTICE);

        returnBook(CONCURRENCY_IN_PRACTICE);

        getBook(WAR_AND_PEACE, READ_IN_THE_LIBRARY);
        read(10, WAR_AND_PEACE);
        returnBook(WAR_AND_PEACE);

    }
}
