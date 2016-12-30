package ru.job4j.dsagai.lesson3.storage;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.dsagai.lesson3.food.Food;
import ru.job4j.dsagai.lesson3.food.Vegetable;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Tests for Trash class
 * @author dsagai
 * @since 29.12.2016
 */
public class TrashTest {
    private Storage storage;

    @Before
    public void init() {
        storage = new Trash();
    }

    @Test
    public void whenAddThenThenContainsAdded() throws Exception {

        Vegetable vegetable = new Vegetable("tomato",new Date(),new Date(), 0d);
        this.storage.add(vegetable);
        List<Food> list = this.storage.getFoods();
        assertThat(list.contains(vegetable), is(true));
    }

    @Test
    public void whenAddAllThenThenContainsAllAdded() throws Exception {
        Vegetable vegetable1 = new Vegetable("apple",new Date(),new Date(), 0d);
        Vegetable vegetable2 = new Vegetable("potato",new Date(),new Date(), 0d);
        this.storage.addAll(Arrays.asList(vegetable1, vegetable2));
        List<Food> list = this.storage.getFoods();
        assertThat(list.contains(vegetable1), is(true));
        assertThat(list.contains(vegetable2), is(true));
    }

    @Test
    public void thenRemoveThenNothingHappens() throws Exception {
        this.storage.clear();
        Vegetable vegetable = new Vegetable("tomato",new Date(),new Date(), 0d);
        this.storage.add(vegetable);
        this.storage.remove(vegetable);
        List<Food> list = this.storage.getFoods();
        assertThat(list.size(), is(1));
    }

    @Test
    public void getFoods() throws Exception {
        initFill();
        List<Food> list = this.storage.getFoods();
        assertThat(list.size(), is(2));
    }

    @Test
    public void whenPoolThenReceiveEmptyList() throws Exception {
        initFill();
        List<Food> list = this.storage.poolFoods();
        assertThat(list.size(), is(0));
    }

    @Test
    public void clear() throws Exception {
        initFill();
        this.storage.clear();
        List<Food> list = this.storage.getFoods();
        assertThat(list.size(), is(0));
    }

    private void initFill() {
        try {
            this.storage.add(new Vegetable("apple", new Date(), new Date(), 0.0));
            this.storage.add(new Vegetable("cherry", new Date(), new Date(), 0.0));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}