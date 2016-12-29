package ru.job4j.dsagai.lesson3.controller;

import ru.job4j.dsagai.lesson3.exceptions.StorageLimitExcess;
import ru.job4j.dsagai.lesson3.food.Food;
import ru.job4j.dsagai.lesson3.storage.Storage;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;




/**
 * Created by QQQ on 29.12.2016.
 */
public class ControlQuality {
    private final static String PROP_PATH = "lesson3/foodStorApp.properties";

    private final Storage wearHouse;
    private final Storage shop;
    private final Storage trash;

    private double defaultDiscount;
    private Date currentDate;



    public ControlQuality(Storage wearHouse, Storage shop, Storage trash, Date currentDate) {
        this.wearHouse = wearHouse;
        this.shop = shop;
        this.trash = trash;
        this.currentDate = currentDate;
        init();
    }



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


    public void placeFood(Food food) throws StorageLimitExcess {
        double expireProgress = food.getExpireProgress(this.currentDate);

        if (expireProgress < Limits.Fresh.getLimit()) {
            this.wearHouse.add(food);
        } else if (expireProgress < Limits.Medium.getLimit()){
            this.shop.add(food);
        } else if (expireProgress < Limits.Old.getLimit()) {
            food.setDiscount(this.defaultDiscount);
            this.shop.add(food);
        } else {
            this.trash.add(food);
        }
    }


    public void replaceFoods() throws StorageLimitExcess{
        List<Food> foods = new ArrayList<>(this.wearHouse.poolFoods());
        foods.addAll(this.shop.poolFoods());
        for (Food food : foods){
            placeFood(food);
        }
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

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



    public static void main(String[] args) {
        for (Limits limit : Limits.values()){
            System.out.println(limit.getLimit());
        }

    }
}
