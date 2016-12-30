package ru.job4j.dsagai.lesson3.storage;

import ru.job4j.dsagai.lesson3.exceptions.StorageLimitExcess;
import ru.job4j.dsagai.lesson3.food.Food;

import java.util.Collection;
import java.util.List;

/**
 * Interface Storage provides base operations
 * for food-storage.
 * @author dsagai
 * @since 29.12.2016
 */
public interface Storage {

    /**
     * method adds new food item to the storage.
     * @param food Food.
     * @return boolean.
     * @throws StorageLimitExcess
     */
    boolean add(Food food) throws StorageLimitExcess;

    /**
     * method provides group entry for food items.
     * @param foods Collection<Food>.
     * @return boolean.
     * @throws StorageLimitExcess
     */
    boolean addAll(Collection<Food> foods) throws StorageLimitExcess;

    /**
     * removes food item from the storage.
     * @param food Food.
     * @return boolean.
     */
    boolean remove(Food food);

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
     * removes all items from the storage.
     */
    void clear();
}
