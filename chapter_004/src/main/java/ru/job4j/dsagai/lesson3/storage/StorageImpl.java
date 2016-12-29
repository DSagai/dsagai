package ru.job4j.dsagai.lesson3.storage;

import ru.job4j.dsagai.lesson3.exceptions.StorageLimitExcess;
import ru.job4j.dsagai.lesson3.food.Food;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Created by QQQ on 29.12.2016.
 */
public class StorageImpl implements Storage {
    private final List<Food> foods;
    private final int maxCapacity;

    public StorageImpl(int capacity) {
        this.maxCapacity = capacity;
        this.foods = new CopyOnWriteArrayList<Food>();
    }

    @Override
    public boolean add(Food food)  throws StorageLimitExcess {

        checkLimitExcess(this.foods.size() + 1);
        return this.foods.add(food);
    }

    private void checkLimitExcess(int n) throws StorageLimitExcess{
        if (n > this.maxCapacity) {
            throw new StorageLimitExcess(String.format("You can't exceed %s units limit!",this.maxCapacity));
        }
    }

    @Override
    public boolean addAll(Collection<Food> foods)  throws StorageLimitExcess {
        checkLimitExcess(foods.size() + this.foods.size());
        return this.foods.addAll(foods);
    }

    @Override
    public boolean remove(Food food) {
        return this.foods.remove(food);
    }

    @Override
    public List<Food> getFoods() {
        return Collections.unmodifiableList(this.foods);
    }

    @Override
    public List<Food> poolFoods() {
        List<Food> list = new ArrayList<>();
        for (int i = this.foods.size() - 1; i >= 0; i--){
            list.add(this.foods.remove(i));
        }
        return list;
    }

    @Override
    public void clear() {
        this.foods.clear();
    }
}
