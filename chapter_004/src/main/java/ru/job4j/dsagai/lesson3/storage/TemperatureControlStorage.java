package ru.job4j.dsagai.lesson3.storage;

import ru.job4j.dsagai.lesson3.exceptions.StorageLimitExcess;
import ru.job4j.dsagai.lesson3.food.Food;

import java.util.Date;
import java.util.List;

/**
 * Decorator for Warehouse class.
 * Extends isSuitable method by temperature control.
 * @author dsagai
 * @version 1.01
 * @since 12.01.2017
 */
public class TemperatureControlStorage implements Storage {
    private final Warehouse storage;
    private final int storTemperature;

    /**
     * Default constructor.
     * @param storage Warehouse.
     * @param storTemperature int.
     */
    public TemperatureControlStorage(Warehouse storage, int storTemperature) {
        this.storage = storage;
        this.storTemperature = storTemperature;
    }

    /**
     * add new food item into storage
     * @param food Food.
     * @throws StorageLimitExcess
     */
    @Override
    public void add(Food food) throws StorageLimitExcess {
        this.storage.add(food);
    }

    /**
     * @param food Food.
     * @param currentDate Date.
     * @return true when food item is appropriate to store at the storage and storage is not full.
     */
    @Override
    public boolean isSuitable(Food food, Date currentDate) {
        boolean result;
        if (food.isAppropriateTemperature(this.storTemperature)) {
            result = this.storage.isSuitable(food, currentDate);
        } else {
            result = false;
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
        return this.storage.remove(food);
    }

    /**
     * @return true when you excess storage limit.
     */
    @Override
    public boolean isFull() {
        return this.storage.isFull();
    }

    /**
     * removes all items from the storage.
     */
    @Override
    public void clear() {
        this.storage.clear();
    }

    /**
     * Retrieves, but does not remove list of stored food items.
     * @return List<Food>.
     */
    @Override
    public List<Food> getFoods() {
        return this.storage.getFoods();
    }

    /**
     * Retrieves and removes list of stored food items.
     * @return List<Food>.
     */
    @Override
    public List<Food> poolFoods() {
        return this.storage.getFoods();
    }

    @Override
    /**
     * Retrieves and removes list of stored food items, which expire progress has exceeded limit for
     * this type jf storage.
     * @param currentDate Date to determine food expire progress
     * @return List<Food>.
     */
    public List<Food> getExpiredFoods(Date currentDate) {
        return this.storage.getExpiredFoods(currentDate);
    }
}
