package ru.job4j.dsagai.lesson3.controller;

import ru.job4j.dsagai.lesson3.exceptions.StorageLimitExcess;
import ru.job4j.dsagai.lesson3.food.Food;
import ru.job4j.dsagai.lesson3.storage.Storage;
import ru.job4j.dsagai.lesson3.util.ConfigReader;


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
        this.defaultDiscount = Double.parseDouble(ConfigReader.getInstance().getProperty("default.discount", "0"));
    }



    /**
     * Places food item into one of thee storages: warehouse, shop or trash.
     * @param food Food.
     * @throws StorageLimitExcess
     */
    public void placeFood(Food food) throws Exception {
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
    public void replaceFoods() throws Exception{
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
        Fresh(Double.parseDouble(ConfigReader.getInstance().getProperty("borderFresh.fresh", "0"))),
        Medium(Double.parseDouble(ConfigReader.getInstance().getProperty("borderFresh.medium", "0"))),
        Old(Double.parseDouble(ConfigReader.getInstance().getProperty("borderFresh.expire", "0")));

        private Limits(double limit) {
            this.limit = limit;
        }

        private double limit;

        public double getLimit() {
            return limit;
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
