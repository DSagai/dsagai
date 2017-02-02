package ru.job4j.dsagai.lesson3.storage;

import ru.job4j.dsagai.lesson3.food.Food;
import ru.job4j.dsagai.lesson3.util.ConfigReader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Trash AbstractStorage
 * @author dsagai
 * @version 1.01
 * @since 12.01.2017
 */
public class Trash extends AbstractStorage {
    private final static String FRESH_BOTTOM_BORDER_KEY = "borderFresh.expire";

    /**
     * Default constructor.
     */
    public Trash() {
        super(Integer.MAX_VALUE, Double.POSITIVE_INFINITY,
                Double.parseDouble(ConfigReader.getInstance().getProperty(FRESH_BOTTOM_BORDER_KEY, "0")));
    }

    @Override
    /**
     * does nothing.
     * @param food Food.
     * @return boolean false.
     */
    public boolean remove(Food food) {
        return false;
    }

    @Override
    /**
     * does nothing.
     * @return List<Food> empty list.
     */
    public List<Food> poolFoods() {
        return new ArrayList<Food>();
    }

    @Override
    /**
     * does nothing.
     * @return List<Food> empty list.
     */
    public List<Food> getExpiredFoods(Date currentDate) {
        return new ArrayList<Food>();
    }
}
