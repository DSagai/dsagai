package ru.job4j.dsagai.lesson3.controller;

import ru.job4j.dsagai.lesson3.exceptions.StorageLimitExcess;
import ru.job4j.dsagai.lesson3.food.Food;
import ru.job4j.dsagai.lesson3.storage.Storage;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;


/**
 * Controls food items quality.
 * Relocates items between three storages
 * depending on the items expire process.
 */
public class ControlQuality {
    //path to the property file, which defines quality borders
    private final static String PROP_PATH = "lesson3/foodStorApp.properties";

    private final Storage warehouse;
    private final Storage shop;
    private final Storage trash;

    //value of default discount, which setups to the new food item.
    private double defaultDiscount;
    //calculation of expire process depends on currentDate property value.
    private Date currentDate;


    /**
     * default constructor.
     * @param warehouse Storage.
     * @param shop Storage.
     * @param trash Storage.
     * @param currentDate Date.
     */
    public ControlQuality(Storage warehouse, Storage shop, Storage trash, Date currentDate) {
        this.warehouse = warehouse;
        this.shop = shop;
        this.trash = trash;
        this.currentDate = currentDate;
        init();
    }


    /**
     * inits limit property for Limits enum.
     */
    private void init() {
        Properties properties = new Properties();
        ClassLoader loader = ControlQuality.class.getClassLoader();
        try {
            properties.load(loader.getResourceAsStream(PROP_PATH));
            Limits.Fresh.setLimit(Double.parseDouble(properties.getProperty("borderFresh.fresh","0")));
            Limits.Medium.setLimit(Double.parseDouble(properties.getProperty("borderFresh.medium","0")));
            Limits.Old.setLimit(Double.parseDouble(properties.getProperty("borderFresh.expire","0")));

            this.defaultDiscount = Double.parseDouble(properties.getProperty("default.discount","0"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Places food item into one of thee storages: warehouse, shop or trash.
     * @param food Food.
     * @throws StorageLimitExcess
     */
    public void placeFood(Food food) throws StorageLimitExcess {
        double expireProgress = food.getExpireProgress(this.currentDate);

        if (expireProgress < Limits.Fresh.getLimit()) {
            this.warehouse.add(food);
        } else if (expireProgress < Limits.Medium.getLimit()){
            this.shop.add(food);
        } else if (expireProgress < Limits.Old.getLimit()) {
            food.setDiscount(this.defaultDiscount);
            this.shop.add(food);
        } else {
            this.trash.add(food);
        }
    }


    /**
     * replaces stored items between storages in dependency of expire process.
     * @throws StorageLimitExcess
     */
    public void replaceFoods() throws StorageLimitExcess{
        List<Food> foods = new ArrayList<>(this.warehouse.poolFoods());
        foods.addAll(this.shop.poolFoods());
        for (Food food : foods){
            placeFood(food);
        }
    }

    /**
     * getter currentDate.
     * @return Date.
     */
    public Date getCurrentDate() {
        return currentDate;
    }

    /**
     * setter currentDate.
     * @param currentDate Date.
     */
    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    /**
     * enum derives three standard borders for product quality.
     */
    private enum Limits {
        Fresh,
        Medium,
        Old;

        private double limit;

        public double getLimit() {
            return limit;
        }

        public void setLimit(double limit) {
            this.limit = limit;
        }
    }

    /**
     * getter warehouse.
     * @return Storage.
     */
    public Storage getWarehouse() {
        return warehouse;
    }

    /**
     * getter shop
     * @return Storage.
     */
    public Storage getShop() {
        return shop;
    }

    /**
     * getter trash
     * @return Storage.
     */
    public Storage getTrash() {
        return trash;
    }


}
