package ru.job4j.dsagai.lesson3.storage;

import ru.job4j.dsagai.lesson3.exceptions.StorageLimitExcess;
import ru.job4j.dsagai.lesson3.food.Food;

import java.util.Date;
import java.util.List;

/**
 * Interface Storage provides base operations
 * for food-storage.
 * @author dsagai
 * @version 1.02
 * @since 02.02.2017
 */
public interface Storage {
    /**
     * add new food item into storage
     * @param food Food.
     * @throws StorageLimitExcess
     */
    void add(Food food) throws StorageLimitExcess;

    /**
     * @param food Food.
     * @param currentDate Date.
     * @return true when food item is appropriate to store at the storage and storage is not full.
     */
    boolean isSuitable(Food food, Date currentDate);

    /**
     * method removes food item from the storage.
     * @param food Food.
     * @return true if food item is present at the storage and remove operation is successful,
     * otherwise return false.
     */
    boolean remove(Food food);

    /**
     * @return true when you excess storage limit.
     */
    boolean isFull();

    /**
     * removes all items from the storage.
     */
    void clear();

    /**
     * Retrieves, but does not remove list of stored food items.
     * @return List<Food>.
     */
    List<Food> getFoods();

    /**
     * Retrieves and removes list of stored food items.
     * @return List<Food>.
     */
    List<Food> poolFoods();

    /**
     * Retrieves and removes list of stored food items, which expire progress has exceeded limit for
     * this type jf storage.
     * @param currentDate Date to determine food expire progress
     * @return List<Food>.
     */
    List<Food> getExpiredFoods(Date currentDate);
}
