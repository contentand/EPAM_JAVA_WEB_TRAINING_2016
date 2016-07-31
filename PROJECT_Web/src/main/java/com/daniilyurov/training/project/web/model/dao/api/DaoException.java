package com.daniilyurov.training.project.web.model.dao.api;

/**
 * Signals that there has been something wrong when trying
 * perform operations with data source.
 *
 * @author Daniil Yurov
 */

public class DaoException extends Exception {

    /**
     * Constructs a DaoException with no detail message.
     * A detail message is a String that describes this particular exception.
     */
    public DaoException() {
    }

    /**
     * Constructs a DaoException with the specified detail
     * message.  A detail message is a String that describes this particular
     * exception.
     *
     * @param message the String that contains a detailed message
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * @param  message the detail message.
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A <tt>null</tt> value
     *         is permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
