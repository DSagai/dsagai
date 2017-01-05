package ru.job4j.dsagai.lesson3.storage;

import ru.job4j.dsagai.lesson3.exceptions.StorageLimitExcess;
import ru.job4j.dsagai.lesson3.food.Food;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Base implementation of Storage interface.
 * @author dsagai
 * @since 29.12.2016
 */
public class StorageImpl implements Storage {
    //keeps food collection
    private final List<Food> foods;
    //defines max capacity of the storage
    private final int maxCapacity;

    /**
     * default constructor.
     * @param capacity int.
     */
    public StorageImpl(int capacity) {
        this.maxCapacity = capacity;
        this.foods = new CopyOnWriteArrayList<Food>();
    }

    @Override
    /**
     * method adds new food item to the storage.
     * @param food Food.
     * @return boolean.
     * @throws StorageLimitExcess
     */
    public boolean add(Food food)  throws StorageLimitExcess {

        checkLimitExcess(this.foods.size() + 1);
        return this.foods.add(food);
    }

    /**
     * checks is storage could be grown up to new capacity?
     * @param n int new capacity.
     * @throws StorageLimitExcess
     */
    private void checkLimitExcess(int n) throws StorageLimitExcess{
        if (n > this.maxCapacity) {
            throw new StorageLimitExcess(String.format("You can't exceed %s units limit!",this.maxCapacity));
        }
    }

    @Override
    /**
     * method provides group entry for food items.
     * @param foods Collection<Food>.
     * @return boolean.
     * @throws StorageLimitExcess
     */
    public boolean addAll(Collection<Food> foods)  throws StorageLimitExcess {
        checkLimitExcess(foods.size() + this.foods.size());
        return this.foods.addAll(foods);
    }

    @Override
    /**
     * removes food item from the storage.
     * @param food Food.
     * @return boolean.
     */
    public boolean remove(Food food) {
        return this.foods.remove(food);
    }

    @Override
    /**
     * Retrieves, but does not remove list of stored food items.
     * @return List<Food>.
     */
    public List<Food> getFoods() {
        return Collections.unmodifiableList(this.foods);
    }

    @Override
    /**
     * Retrieves and removes list of stored food items.
     * @return List<Food>.
     */
    public List<Food> poolFoods() {
        List<Food> list = new ArrayList<>();
        for (int i = this.foods.size() - 1; i >= 0; i--){
            list.add(this.foods.remove(i));
        }
        return list;
    }

    @Override
    /**
     * removes all items from the storage.
     */
    public void clear() {
        this.foods.clear();
    }

    @Override
    /**
     * @return true when you excess storage limit
     */
    public boolean isFull() {
        return this.foods.size() == this.maxCapacity;
    }
}
