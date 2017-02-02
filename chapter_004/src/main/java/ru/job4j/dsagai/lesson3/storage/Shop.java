package ru.job4j.dsagai.lesson3.storage;


import ru.job4j.dsagai.lesson3.food.Food;
import ru.job4j.dsagai.lesson3.util.ConfigReader;

import java.util.Date;
import java.util.List;

/**
 * Shop AbstractStorage
 *
 * @author dsagai
 * @version 1.01
 * @since 12.01.2017
 */
public class Shop extends AbstractStorage {
    private final static String FRESH_UPPER_BORDER_KEY = "borderFresh.expire";
    private final static String FRESH_BOTTOM_BORDER_KEY = "borderFresh.fresh";
    private final static String DEFAULT_CAPACITY_KEY = "default.warehouseCapacity";
    private final static String FRESH_DISCOUNT_BORDER_KEY = "borderFresh.medium";
    private final static String DEFAULT_DISCOUNT_KEY = "default.discount";


    //value of default discount, which setups to the new food item.
    private final double defaultDiscount;
    //minimum expire progress value, from which discount has to be setup.
    private final double freshBorderForDiscount;


    public Shop() {
        super(Integer.parseInt(ConfigReader.getInstance().getProperty(DEFAULT_CAPACITY_KEY, "0")),
                Double.parseDouble(ConfigReader.getInstance().getProperty(FRESH_UPPER_BORDER_KEY, "0")),
                Double.parseDouble(ConfigReader.getInstance().getProperty(FRESH_BOTTOM_BORDER_KEY, "0")));
        this.defaultDiscount = Double.parseDouble(ConfigReader.getInstance().getProperty(DEFAULT_DISCOUNT_KEY, "0"));
        this.freshBorderForDiscount = Double.parseDouble(ConfigReader.getInstance().getProperty(FRESH_DISCOUNT_BORDER_KEY, "0"));
    }

    public Shop(int maxCapacity) {
        super(maxCapacity,
                Double.parseDouble(ConfigReader.getInstance().getProperty(FRESH_UPPER_BORDER_KEY, "0")),
                Double.parseDouble(ConfigReader.getInstance().getProperty(FRESH_BOTTOM_BORDER_KEY, "0")));
        this.defaultDiscount = Double.parseDouble(ConfigReader.getInstance().getProperty(DEFAULT_DISCOUNT_KEY, "0"));
        this.freshBorderForDiscount = Double.parseDouble(ConfigReader.getInstance().getProperty(FRESH_DISCOUNT_BORDER_KEY, "0"));
    }

    @Override
    /**
     * Retrieves and removes list of stored food items, which expire progress has exceeded limit for
     * this type of storage.
     * Before executing parent method checking for discount condition
     * @param currentDate Date to determine food expire progress
     * @return List<Food>.
     */
    public List<Food> getExpiredFoods(Date currentDate) {
        for (Food food : this.getFoods()) {
            checkDiscountCondition(food, currentDate);
        }
        return super.getExpiredFoods(currentDate);
    }

    /**
     * checks for discount condition.
     * if condition mets, then sets discount
     * @param food
     * @param currentDate
     */
    private void checkDiscountCondition(Food food, Date currentDate) {
        if (food.getExpireProgress(currentDate) >= this.freshBorderForDiscount) {
            food.setDiscount(this.defaultDiscount);
        }
    }

}
