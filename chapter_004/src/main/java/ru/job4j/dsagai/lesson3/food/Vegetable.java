package ru.job4j.dsagai.lesson3.food;

import java.util.Date;

/**
 * Vegetable entity
 * @author dsagai
 * @version 1.02
 * @since 13.01.2017
 */
public class Vegetable extends Food implements Reproducible {
    private static final int MIN_TEMP = 5;
    private static final int MAX_TEMP = 10;

    private static final String REPRODUCED_PRODUCT_NAME = "Uncle Bens";
    private static int REPRODUCED_LIFECYCLE_DAYS = 200;
    private static double REPRODUCED_PRICE = 50d;

    /**
     * Default constructor.
     *
     * @param name       String.
     * @param createDate Date.
     * @param expireDate Date.
     * @param price      double.
     */
    public Vegetable(String name, Date createDate, Date expireDate, double price) {
        super(name, createDate, expireDate, price, MIN_TEMP, MAX_TEMP);
    }

    /**
     * Method returns new food item, which was reproduced from the old one.
     * @param currentDate Date date of reproduction.
     * @return Food new Food item, which was reproduced from the old one.
     */
    @Override
    public Food getReproducedFood(Date currentDate) {
        Date expireDate = new Date(currentDate.getTime() + REPRODUCED_LIFECYCLE_DAYS * 24 * 60 * 60 * 1000);
        return new Catsup(REPRODUCED_PRODUCT_NAME, currentDate, expireDate, REPRODUCED_PRICE);
    }
}
