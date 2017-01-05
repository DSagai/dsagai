package ru.job4j.dsagai.lesson3.food;

import java.util.Date;

/**
 * Pasta entity
 * @author dsagai
 * @since 28.12.2016
 */
public class Pasta extends Food {

    /**
     * Default constructor.
     * @param name String.
     * @param createDate Date.
     * @param expireDate Date
     * @param price double
     */
    public Pasta(String name, Date createDate, Date expireDate, double price) {
        super(name, createDate, expireDate, price);
    }

    /**
     * Extended constructor.
     * @param name String.
     * @param createDate Date.
     * @param expireDate Date.
     * @param price double.
     * @param recyclable boolean.
     * @param storeTemp int.
     */
    public Pasta(String name, Date createDate, Date expireDate, double price, boolean recyclable, int storeTemp) {
        super(name, createDate, expireDate, price, recyclable, storeTemp);
    }
}
