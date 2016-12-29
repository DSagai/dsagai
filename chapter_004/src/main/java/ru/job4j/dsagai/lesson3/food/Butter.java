package ru.job4j.dsagai.lesson3.food;

import java.util.Date;

/**
 * Butter entity
 * @author dsagai
 * @since 28.12.2016
 */
public class Butter extends Food {
    private final double fat;



    /**
     * Default constructor.
     * @param name String.
     * @param createDate Date.
     * @param expireDate Date
     * @param price double
     * @param fat double
     */
    public Butter(String name, Date createDate, Date expireDate, double price, double fat) {
        super(name, createDate, expireDate, price);
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