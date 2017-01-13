package ru.job4j.dsagai.lesson3.controller;

import ru.job4j.dsagai.lesson3.exceptions.StorageLimitExcess;
import ru.job4j.dsagai.lesson3.food.Food;
import ru.job4j.dsagai.lesson3.storage.Storage;
import ru.job4j.dsagai.lesson3.util.ConfigReader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Controls food items quality.
 * Relocates items between storages
 * depending on the items expire process.
 * @author dsagai
 * @version 1.01
 * @since 12.01.2017
 */
public class ControlQuality {
    private final static String FRESH_DISCOUNT_BORDER_KEY = "borderFresh.medium";
    private final static String DEFAULT_DISCOUNT_KEY = "default.discount";

    private final List<Storage> storages;
    //value of default discount, which setups to the new food item.
    private final double defaultDiscount;
    //minimum expire progress value, from which discount has to be setup.
    private final double freshBorderForDiscount;

    //calculation of expire process depends on currentDate property value.
    private Date currentDate;

    /**
     * Default constructor.
     * @param currentDate Date.
     */
    public ControlQuality(Date currentDate) {
        this.storages = new CopyOnWriteArrayList<>();
        this.defaultDiscount = Double.parseDouble(ConfigReader.getInstance().getProperty(DEFAULT_DISCOUNT_KEY, "0"));
        this.freshBorderForDiscount = Double.parseDouble(ConfigReader.getInstance().getProperty(FRESH_DISCOUNT_BORDER_KEY, "0"));
        this.currentDate = currentDate;
    }

    /**
     * Specialized counstructor.
     * @param currentDate Date.
     * @param storages List<Storage>.
     */
    public ControlQuality(Date currentDate, List<Storage> storages) {
        this.storages = storages;
        this.defaultDiscount = Double.parseDouble(ConfigReader.getInstance().getProperty(DEFAULT_DISCOUNT_KEY, "0"));
        this.freshBorderForDiscount = Double.parseDouble(ConfigReader.getInstance().getProperty(FRESH_DISCOUNT_BORDER_KEY, "0"));
        this.currentDate = currentDate;
    }

    /**
     * adds new Storage item to the controller
     * @param storage Storage.
     */
    public void addStorage(Storage storage) {
        this.storages.add(storage);
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
     * Places food item into one of the storages.
     * @param food Food.
     * @throws StorageLimitExcess
     */
    public void placeFood(Food food) throws StorageLimitExcess {
        if (food.getExpireProgress(this.currentDate) >= this.freshBorderForDiscount) {
            food.setDiscount(this.defaultDiscount);
        }

        for (Storage storage : this.storages) {
            if (storage.isSuitable(food, this.currentDate)){
                storage.add(food);
                break;
            }
        }
    }

    /**
     * replaces stored items between storages in dependency of expire process.
     * @throws StorageLimitExcess
     */
    public void replaceFoods() throws Exception{
        List<Food> foods = new ArrayList<>();
        for (Storage storage : this.storages){
            foods.addAll(storage.poolFoods());
        }
        for (Food food : foods){
            placeFood(food);
        }
    }
}
