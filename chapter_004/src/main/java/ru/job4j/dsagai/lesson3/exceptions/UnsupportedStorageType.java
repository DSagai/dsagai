package ru.job4j.dsagai.lesson3.exceptions;

/**
 * @author dsagai
 * @since 01.01.2017
 */
public class UnsupportedStorageType extends Exception {

    /**
     * default constructor
     * @param message String
     */
    public UnsupportedStorageType(String message){
        super(message);
    }
}
