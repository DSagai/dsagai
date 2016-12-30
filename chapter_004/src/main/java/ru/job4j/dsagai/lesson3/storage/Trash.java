package ru.job4j.dsagai.lesson3.storage;



import ru.job4j.dsagai.lesson3.food.Food;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Trash implementation of Storage interface unlike StorageImpl
 * does not allow pool items from it.
 * Also it has unlimited capacity.
 * @author dsagai
 * @since 29.12.2016
 */
public class Trash implements Storage {
    //keeps food collection
    private final List<Food> foods;

    /**
     * default constructor.
     */
    public Trash() {
        this.foods = new ArrayList<Food>();
    }

    @Override
    /**
     * method adds new food item to the storage.
     * @param food Food.
     * @return boolean.
     */
    public boolean add(Food food) {
        return this.foods.add(food);
    }

    @Override
    /**
     * method provides group entry for food items.
     * @param foods Collection<Food>.
     * @return boolean.
     */
    public boolean addAll(Collection<Food> foods) {
        return this.foods.addAll(foods);
    }

    @Override
    /**
     * does nothing.
     * @param food Food.
     * @return boolean false.
     */
    public boolean remove(Food food) {
        return false;
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
     * does nothing.
     * @return List<Food> empty list.
     */
    public List<Food> poolFoods() {
        return new ArrayList<Food>();
    }

    @Override
    /**
     * removes all items from the storage.
     */
    public void clear() {
        foods.clear();
    }
}
