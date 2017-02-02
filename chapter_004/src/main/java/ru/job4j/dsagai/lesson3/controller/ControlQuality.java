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
 * @version 1.02
 * @since 02.02.2017
 */
public class ControlQuality {

    private final List<Storage> storages;


    //calculation of expire process depends on currentDate property value.
    private Date currentDate;

    /**
     * Default constructor.
     * @param currentDate Date.
     */
    public ControlQuality(Date currentDate) {
        this.storages = new CopyOnWriteArrayList<>();
        this.currentDate = currentDate;
    }

    /**
     * Specialized counstructor.
     * @param currentDate Date.
     * @param storages List<Storage>.
     */
    public ControlQuality(Date currentDate, List<Storage> storages) {
        this.storages = storages;
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
            foods.addAll(storage.getExpiredFoods(this.currentDate));
        }
        for (Food food : foods){
            placeFood(food);
        }
    }
}
