package ru.job4j.dsagai.lesson3.storage;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.dsagai.lesson3.food.Food;
import ru.job4j.dsagai.lesson3.food.Vegetable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Tests for StorageImpl class
 * @author dsagai
 * @since 29.12.2016
 */
public class StorageImplTest {
    private Storage storage;

    @Before
    public void init() {
        storage = new StorageImpl(10);
    }

    @Test
    public void whenAddThenThenContainsAdded() throws Exception {
        this.storage.clear();
        Vegetable vegetable = new Vegetable("tomato",new Date(),new Date(), 0d);
        this.storage.add(vegetable);
        List<Food> list = this.storage.getFoods();
        assertThat(list.contains(vegetable), is(true));
    }

    @Test
    public void whenAddAllThenThenContainsAllAdded() throws Exception {
        this.storage.clear();
        Vegetable vegetable1 = new Vegetable("tomato",new Date(),new Date(), 0d);
        Vegetable vegetable2 = new Vegetable("potato",new Date(),new Date(), 0d);
        this.storage.addAll(Arrays.asList(vegetable1, vegetable2));
        List<Food> list = this.storage.getFoods();
        assertThat(list.contains(vegetable1), is(true));
        assertThat(list.contains(vegetable2), is(true));
    }

    @Test
    public void whenRemoveLastThenStorageIsEmpty() throws Exception {
        this.storage.clear();
        Vegetable vegetable = new Vegetable("tomato",new Date(),new Date(), 0d);
        this.storage.add(vegetable);
        this.storage.remove(vegetable);
        List<Food> list = this.storage.getFoods();
        assertThat(list.size(), is(0));
    }

    @Test
    public void getFoods() throws Exception {
        initFill();
        List<Food> foods = this.storage.getFoods();
        assertThat(foods.size(), is(2));
        foods = this.storage.getFoods();
        assertThat(foods.size(), is(2));
    }

    @Test
    public void whenPoolThenStorageIsEmpty() throws Exception {
        initFill();
        List<Food> foods = this.storage.poolFoods();
        assertThat(foods.size(), is(2));
        foods = this.storage.getFoods();
        assertThat(foods.size(), is(0));
    }

    @Test
    public void clear() throws Exception {
        Storage newStrorage = new StorageImpl(10);
        newStrorage.add(new Vegetable("name",new Date(), new Date(),0.0));
        newStrorage.add(new Vegetable("name",new Date(), new Date(),0.0));
        newStrorage.clear();
        assertThat(newStrorage.getFoods().size(), is(0));
    }

    private void initFill() {
        try {
            this.storage.add(new Vegetable("name1", new Date(), new Date(), 0.0));
            this.storage.add(new Vegetable("name1", new Date(), new Date(), 0.0));
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}