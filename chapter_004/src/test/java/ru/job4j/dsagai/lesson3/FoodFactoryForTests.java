package ru.job4j.dsagai.lesson3;

import ru.job4j.dsagai.lesson3.food.Food;
import ru.job4j.dsagai.lesson3.food.Vegetable;

import java.util.Date;

/**
 * Utility class for test units
 * @author dsagai
 * @version 1.02
 * @since 13.01.2017
 */
public class FoodFactoryForTests {
    /**
     * method returns Food item with expire progress less than limit defined by input param
     * @param name String.
     * @param createDate Date.
     * @param currentDate Date.
     * @param limit double.
     * @return Food item
     */
    public static Food getFoodUnderFreshLimit(String name, Date createDate, Date currentDate, double limit) {
        return getFoodUnderFreshLimit(name, createDate, currentDate, limit, Vegetable.class);
    }

    /**
     * method returns Food item with expire progress less than limit defined by input param
     * @param name String.
     * @param createDate Date.
     * @param currentDate Date.
     * @param limit double.
     * @param clazz Class of food item.
     * @return Food item
     */
    public static Food getFoodUnderFreshLimit(String name, Date createDate, Date currentDate, double limit, Class clazz) {

        long expTime = (long) ((currentDate.getTime() - createDate.getTime()) / (limit - 0.01) + createDate.getTime());
        Date expireDate = new Date(expTime);
        Food result = null;
        try {
            result = (Food) clazz.getConstructor(String.class, Date.class, Date.class, double.class)
                    .newInstance(name, createDate, expireDate, 0d);
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
