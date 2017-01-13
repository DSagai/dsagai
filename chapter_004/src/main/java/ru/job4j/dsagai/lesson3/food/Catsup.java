package ru.job4j.dsagai.lesson3.food;

import java.util.Date;

/**
 * Catsup entity
 * @author dsagai
 * @version 1.02
 * @since 13.01.2017
 */
public class Catsup extends Food {
    private static final int MIN_TEMP = 5;
    private static final int MAX_TEMP = 10;
    /**
     * Default constructor.
     *
     * @param name       String.
     * @param createDate Date.
     * @param expireDate Date.
     * @param price      double.
     */
    public Catsup(String name, Date createDate, Date expireDate, double price) {
        super(name, createDate, expireDate, price, MIN_TEMP, MAX_TEMP);
    }
}
