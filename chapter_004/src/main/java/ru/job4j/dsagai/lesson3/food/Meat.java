package ru.job4j.dsagai.lesson3.food;

import java.util.Date;

/**
 * Meat entity
 * @author dsagai
 * @version 1.00
 * @since 10.01.2017
 */
public class Meat extends Food {
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
        super(name, createDate, expireDate, price);
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