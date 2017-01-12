package ru.job4j.dsagai.lesson3.storage;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.dsagai.lesson3.food.Food;
import ru.job4j.dsagai.lesson3.food.Vegetable;

import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Test for Trash class
 * @author dsagai
 * @version 1.00
 * @since 10.01.2017
 */
public class TrashTest {
    private Storage storage;

    @Before
    public void init() {
        storage = new Trash();
    }

    @Test
    public void whenRemoveThenNothingHappens() throws Exception {
        initFill();

        Food food = this.storage.getFoods().get(0);

        this.storage.remove(food);
        assertThat(this.storage.getFoods().contains(food), is(true));
    }

    @Test
    public void whenPoolThenReceiveEmptyList() throws Exception {
        initFill();
        List<Food> list = this.storage.poolFoods();
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