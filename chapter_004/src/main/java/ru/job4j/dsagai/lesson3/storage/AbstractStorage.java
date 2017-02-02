package ru.job4j.dsagai.lesson3.storage;

import ru.job4j.dsagai.lesson3.exceptions.StorageLimitExcess;
import ru.job4j.dsagai.lesson3.food.Food;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Abstract AbstractStorage
 * @author dsagai
 * @version 1.02
 * @since 02.01.2017
 */
public abstract class AbstractStorage implements Storage {
    private final List<Food> foodList;
    private final int maxCapacity;
    private final double upperBorderFresh;
    private final double bottomBorderFresh;

    /**
     * Default constructor.
     * @param maxCapacity int sets up storage capacity.
     * @param upperBorderFresh double.
     */
    public AbstractStorage(int maxCapacity, double upperBorderFresh, double bottomBorderFresh ) {
        this.maxCapacity = maxCapacity;
        this.upperBorderFresh = upperBorderFresh;
        this.bottomBorderFresh = bottomBorderFresh;
        this.foodList = new CopyOnWriteArrayList<>();
    }

    /**
     * add new food item into storage
     * @param food Food.
     * @throws StorageLimitExcess
     */
    @Override
    public void add(Food food) throws StorageLimitExcess {
        if (this.maxCapacity <= this.foodList.size()) {
            throw new StorageLimitExcess(String.format("You can't exceed %s units limit!",this.maxCapacity));
        }
        this.foodList.add(food);
    }

    /**
     * @param food Food.
     * @param currentDate Date.
     * @return true when food item is appropriate to store at the storage and storage is not full.
     */
    @Override
    public boolean isSuitable(Food food, Date currentDate) {
        boolean result = false;
        double expireProgress = food.getExpireProgress(currentDate);
        if (expireProgress < this.upperBorderFresh && expireProgress >= this.bottomBorderFresh && !isFull()){
            result = true;
        }
        return result;
    }

    /**
     * method removes food item from the storage.
     * @param food Food.
     * @return true if food item is present at the storage and remove operation is successful,
     * otherwise return false.
     */
    @Override
    public boolean remove(Food food) {
        return this.foodList.remove(food);
    }

    /**
     * @return true when you excess storage limit.
     */
    @Override
    public boolean isFull() {
        return (this.foodList.size() >= this.maxCapacity);
    }

    /**
     * removes all items from the storage.
     */
    @Override
    public void clear() {
        this.foodList.clear();
    }

    /**
     * Retrieves, but does not remove list of stored food items.
     * @return List<Food>.
     */
    @Override
    public List<Food> getFoods() {
        return Collections.unmodifiableList(this.foodList);
    }

    /**
     * Retrieves and removes list of stored food items.
     * @return List<Food>.
     */
    @Override
    public List<Food> poolFoods() {
        List<Food> result = new ArrayList<>();
        for (int i = this.foodList.size() - 1; i >= 0; i--){
            result.add(this.foodList.remove(i));
        }
        return result;
    }

    /**
     * Retrieves and removes list of stored food items, which expire progress has exceeded limit for
     * this type jf storage.
      * @param currentDate Date to determine food expire progress
     * @return List<Food>.
     */
    public List<Food> getExpiredFoods(Date currentDate){
        List<Food> result = new ArrayList<>();
        for (int i = this.foodList.size() - 1; i >= 0; i--){
            if (!isSuitable(this.foodList.get(i), currentDate)) {
                result.add(this.foodList.remove(i));
            }
        }
        return result;
    }
}
