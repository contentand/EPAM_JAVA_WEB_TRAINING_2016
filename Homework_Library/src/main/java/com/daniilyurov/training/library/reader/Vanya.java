package com.daniilyurov.training.library.reader;

import static com.daniilyurov.training.library.library.Library.BookTitle.*;

public class Vanya extends Reader{

    public Vanya() {
        setName("Vanya");
    }

    @Override
    public void run() {
        getBook(CONCURRENCY_IN_PRACTICE, READ_IN_THE_LIBRARY);
        read(40, CONCURRENCY_IN_PRACTICE);
        returnBook(CONCURRENCY_IN_PRACTICE);

        getBook(EFFECTIVE_JAVA, READ_IN_THE_LIBRARY);
        read(20, EFFECTIVE_JAVA);
        returnBook(EFFECTIVE_JAVA);

        getBook(ANNA_KARENINA, READ_IN_THE_LIBRARY);
        getBook(WAR_AND_PEACE, READ_IN_THE_LIBRARY);
        read(30, ANNA_KARENINA, WAR_AND_PEACE);
        returnBook(ANNA_KARENINA);
        returnBook(WAR_AND_PEACE);
    }
}
