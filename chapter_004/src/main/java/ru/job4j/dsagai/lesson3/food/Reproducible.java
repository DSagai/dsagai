package ru.job4j.dsagai.lesson3.food;

import java.util.Date;

/**
 * Interface for food items, which could be reproduced.
 * @author dsagai
 * @version 1.02
 * @since 13.01.2017
 */
public interface Reproducible {

    /**
     * Method returns new food item, which was reproduced from the old one.
     * @param currentDate Date date of reproduction.
     * @return Food new Food item, which was reproduced from the old one.
     */
    Food getReproducedFood(Date currentDate);
}
