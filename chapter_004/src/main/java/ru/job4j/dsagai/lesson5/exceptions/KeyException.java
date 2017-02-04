package ru.job4j.dsagai.lesson5.exceptions;

/**
 * Exception for violating string generator rules
 *
 * @author dsagai
 * @version 1.01
 * @since 03.02.2017
 */

public class KeyException extends Exception {

    /**
     * constructor with specified message
     * @param message String.
     */
    public KeyException(String message) {
        super(message);
    }
}
