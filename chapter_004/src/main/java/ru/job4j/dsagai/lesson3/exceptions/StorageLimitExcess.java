package ru.job4j.dsagai.lesson3.exceptions;

/**
 * @author dsagai
 * @version 1.00
 * @since 10.01.2017
 */
public class StorageLimitExcess extends Exception {

    public StorageLimitExcess(String message) {
        super(message);
    }
}
