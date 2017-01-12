package ru.job4j.dsagai.lesson3.food;

import java.util.Date;

/**
 * Meat entity
 * @author dsagai
 * @version 1.01
 * @since 12.01.2017
 */
public class Meat extends Food {
    private static final int MIN_TEMP = -20;
    private static final int MAX_TEMP = -5;

    private final MeatTypes type;

    /**
     * Default constructor.
     * @param name String.
     * @param createDate Date.
     * @param expireDate Date.
     * @param price double.
     * @param type MeatTypes.
     */
    public Meat(String name, Date createDate, Date expireDate, double price, MeatTypes type) {
        super(name, createDate, expireDate, price, MIN_TEMP, MAX_TEMP);
        this.type = type;
    }



    /**
     * getter meat.
     * @return MeatTypes.
     */
    public MeatTypes getType() {
        return type;
    }
}