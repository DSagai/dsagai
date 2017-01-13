package ru.job4j.dsagai.lesson3.storage;

import ru.job4j.dsagai.lesson3.controller.ControlQuality;
import ru.job4j.dsagai.lesson3.exceptions.StorageLimitExcess;
import ru.job4j.dsagai.lesson3.food.Food;
import ru.job4j.dsagai.lesson3.food.Reproducible;

import java.util.Date;
import java.util.List;

/**
 * Decorator for Trash class.
 * Extends add methods behavior by support of Reproducible interface.
 * @author dsagai
 * @version 1.03
 * @since 13.01.2017
 */
public class SmartTrash implements Storage {
    private final Trash trash;
    private final ControlQuality controller;

    /**
     * Default constructor
     * @param trash Trash.
     * @param controller ControlQuality.
     */
    public SmartTrash(Trash trash, ControlQuality controller) {
        this.trash = trash;
        this.controller = controller;
    }

    @Override
    /**
     * if food is instance of Reproducible, then it'll be reproduced
     * and placed into controller as new food item.
     * in other case it will be placed into trash.
     * @param food Food.
     */
    public void add(Food food) throws StorageLimitExcess {
        if (food instanceof Reproducible) {
            this.controller.placeFood(((Reproducible)food).getReproducedFood(controller.getCurrentDate()));
        } else {
            this.trash.add(food);
        }
    }

    @Override
    /**
     * method removes food item from the storage.
     * @param food Food.
     * @return true if food item is present at the storage and remove operation is successful,
     * otherwise return false.
     */
    public boolean isSuitable(Food food, Date currentDate) {
        return this.trash.isSuitable(food, currentDate);
    }

    @Override
    /**
     * does nothing.
     * @param food Food.
     * @return boolean false.
     */
    public boolean remove(Food food) {
        return this.trash.remove(food);
    }

    @Override
    /**
     * @return true when you excess storage limit.
     */
    public boolean isFull() {
        return this.trash.isFull();
    }

    @Override
    /**
     * removes all items from the storage.
     */
    public void clear() {
        this.trash.clear();
    }

    @Override
    /**
     * Retrieves, but does not remove list of stored food items.
     * @return List<Food>.
     */
    public List<Food> getFoods() {
        return this.trash.getFoods();
    }

    @Override
    /**
     * does nothing.
     * @return List<Food> empty list.
     */
    public List<Food> poolFoods() {
        return this.trash.poolFoods();
    }
}
