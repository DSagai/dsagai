package ru.job4j.dsagai.lesson3.food;

import java.util.Date;

/**
 * Butter entity
 * @author dsagai
 * @version 1.01
 * @since 12.01.2017
 */
public class Butter extends Food {
    private static final int MIN_TEMP = -15;
    private static final int MAX_TEMP = -5;

    private final double fat;



    /**
     * Default constructor.
     * @param name String.
     * @param createDate Date.
     * @param expireDate Date.
     * @param price double.
     * @param fat double.
     */
    public Butter(String name, Date createDate, Date expireDate, double price, double fat) {
        super(name, createDate, expireDate, price, MIN_TEMP, MAX_TEMP);
        this.fat = fat;
    }


    /**
     * getter fat
     * @return double
     */
    public double getFat() {
        return fat;
    }
}