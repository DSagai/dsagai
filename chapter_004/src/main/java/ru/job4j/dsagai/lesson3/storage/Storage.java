package ru.job4j.dsagai.lesson3.storage;

import ru.job4j.dsagai.lesson3.exceptions.StorageLimitExcess;
import ru.job4j.dsagai.lesson3.food.Food;

import java.util.Collection;
import java.util.List;

/**
 * Created by QQQ on 29.12.2016.
 */
public interface Storage {

    boolean add(Food food) throws StorageLimitExcess;

    boolean addAll(Collection<Food> foods) throws StorageLimitExcess;

    boolean remove(Food food);

    List<Food> getFoods();

    List<Food> poolFoods();

    void clear();
}
