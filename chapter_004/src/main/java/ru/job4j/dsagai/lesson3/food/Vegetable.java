package ru.job4j.dsagai.lesson3.food;

import java.util.Date;

/**
 * Vegetable entity
 * @author dsagai
 * @since 28.12.2016
 */
public class Vegetable extends Food {

    /**
     * Default constructor.
     * @param name String.
     * @param createDate Date.
     * @param expireDate Date
     * @param price double
     */
    public Vegetable(String name, Date createDate, Date expireDate, double price) {
        super(name, createDate, expireDate, price);
    }
}
