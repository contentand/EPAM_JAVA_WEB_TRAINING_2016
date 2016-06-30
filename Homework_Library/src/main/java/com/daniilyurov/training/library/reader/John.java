package com.daniilyurov.training.library.reader;

import static com.daniilyurov.training.library.library.Library.BookTitle.*;
public class John extends Reader {
    public John() {
        setName("John");
    }

    @Override
    public void run() {
        getBook(EFFECTIVE_JAVA, READ_AT_HOME);
        read(400, EFFECTIVE_JAVA);
        returnBook(EFFECTIVE_JAVA);
        getBook(WAR_AND_PEACE, READ_IN_THE_LIBRARY);
        read(20, WAR_AND_PEACE);
        returnBook(WAR_AND_PEACE);
    }
}
