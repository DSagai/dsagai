package ru.job4j.dsagai.lesson3.food;

import java.util.Date;

/**
 * Meat entity
 * @author dsagai
 * @version 1.02
 * @since 13.01.2017
 */
public class Meat extends Food implements Reproducible {
    private static final int MIN_TEMP = -20;
    private static final int MAX_TEMP = -5;
    private static final MeatTypes DEFAULT_TYPE = MeatTypes.Beef;

    private static final String REPRODUCED_PRODUCT_NAME = "Bavaria Sausage";
    private static int REPRODUCED_LIFECYCLE_DAYS = 30;
    private static double REPRODUCED_PRICE = 100d;

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
     * Default constructor.
     * @param name
     * @param createDate
     * @param expireDate
     * @param price
     */
    public Meat(String name, Date createDate, Date expireDate, double price) {
        super(name, createDate, expireDate, price, MIN_TEMP, MAX_TEMP);
        this.type = DEFAULT_TYPE;
    }

    /**
     * getter meat.
     * @return MeatTypes.
     */
    public MeatTypes getType() {
        return type;
    }

    /**
     * Method returns new food item, which was reproduced from the old one.
     * @param currentDate Date date of reproduction.
     * @return Food new Food item, which was reproduced from the old one.
     */
    @Override
    public Food getReproducedFood(Date currentDate) {
        Date expireDate = new Date(currentDate.getTime() + REPRODUCED_LIFECYCLE_DAYS * 24 * 60 * 60 * 1000);
        return new Sausage(REPRODUCED_PRODUCT_NAME, currentDate, expireDate, REPRODUCED_PRICE);
    }
}