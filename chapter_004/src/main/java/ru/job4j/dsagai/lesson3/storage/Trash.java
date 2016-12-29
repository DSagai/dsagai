package ru.job4j.dsagai.lesson3.storage;

import ru.job4j.dsagai.lesson3.food.Food;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by QQQ on 29.12.2016.
 */
public class Trash implements Storage {
    private final List<Food> foods;

    public Trash() {
        this.foods = new ArrayList<Food>();
    }

    @Override
    public boolean add(Food food) {
        return this.foods.add(food);
    }

    @Override
    public boolean addAll(Collection<Food> foods) {
        return this.foods.addAll(foods);
    }

    @Override
    public boolean remove(Food food) {
        return false;
    }

    @Override
    public List<Food> getFoods() {
        return Collections.unmodifiableList(this.foods);
    }

    @Override
    public List<Food> poolFoods() {
        return new ArrayList<Food>();
    }

    @Override
    public void clear() {
        foods.clear();
    }
}
