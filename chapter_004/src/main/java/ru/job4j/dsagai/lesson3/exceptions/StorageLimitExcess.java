package ru.job4j.dsagai.lesson3.exceptions;

/**
 * @author dsagai
 * @since 29.12.2016
 */
public class StorageLimitExcess extends Exception {
    /**
     * default constructor
     * @param message String
     */
    public StorageLimitExcess(String message) {
        super(message);
    }
}
