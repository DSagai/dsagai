package ru.job4j.dsagai.lesson5.exceptions;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 03.02.2017
 */

public class KeyException extends Exception {
    /**
     * default constructor
     */
    public KeyException() {
        super();
    }

    /**
     * constructor with specified message
     * @param message String.
     */
    public KeyException(String message) {
        super(message);
    }
}
