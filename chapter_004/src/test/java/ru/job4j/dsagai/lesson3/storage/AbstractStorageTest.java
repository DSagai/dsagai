package ru.job4j.dsagai.lesson3.storage;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.dsagai.lesson3.food.Food;
import ru.job4j.dsagai.lesson3.food.Vegetable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Test for AbstractStorage class
 * @author dsagai
 * @version 1.01
 * @since 12.01.2017
 */
public class AbstractStorageTest {
    private Storage storage;
    private final int MAX_CAPACITY = 3;
    private final double UPPER_FRESH_BORDER = 0.5;
    private final double BOTTOM_FRESH_BORDER = 0.2;

    private SimpleDateFormat format;
    private Date createDate;
    private Date currentDate;

    @Before
    public void init() {
        this.storage = new AbstractStorage(this.MAX_CAPACITY, this.UPPER_FRESH_BORDER, this.BOTTOM_FRESH_BORDER) {
        };
        this.format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.createDate = this.format.parse("01/12/2016");
            this.currentDate = this.format.parse("01/01/2017");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void add() throws Exception {
        Food apple = getFoodUnderFreshLimit("apple", this.createDate, this.currentDate, 0d);
        Food tomato = getFoodUnderFreshLimit("tomato", this.createDate, this.currentDate, 0d);
        this.storage.add(apple);

        List<Food> foods = this.storage.getFoods();

        assertThat(foods.contains(apple), is(true));
        assertThat(foods.contains(tomato), is(false));
    }

    @Test
    public void whenSuitableThenReturnTrueElseFalse() throws Exception {

        Food subSuitable = getFoodUnderFreshLimit("suitable", this.createDate, this.currentDate, 0d);
        Food suitable = getFoodUnderFreshLimit("suitable", this.createDate, this.currentDate, 0.25);
        Food notSuitable = getFoodUnderFreshLimit("notSuitable", this.createDate, this.currentDate, 0.75);

        assertThat(this.storage.isSuitable(subSuitable, this.currentDate), is(false));
        assertThat(this.storage.isSuitable(suitable, this.currentDate), is(true));
        assertThat(this.storage.isSuitable(notSuitable, this.currentDate), is(false));
    }

    @Test
    public void remove() throws Exception {
        Food apple = getFoodUnderFreshLimit("apple", this.createDate, this.currentDate, 0d);
        Food tomato = getFoodUnderFreshLimit("tomato", this.createDate, this.currentDate, 0d);
        this.storage.add(apple);
        this.storage.add(tomato);

        this.storage.remove(tomato);

        List<Food> foods = this.storage.getFoods();

        assertThat(foods.contains(apple), is(true));
        assertThat(foods.contains(tomato), is(false));

    }

    @Test
    public void isFull() throws Exception {
        Food food = getFoodUnderFreshLimit("food", this.createDate, this.currentDate, 0d);

        for (int i = 0; i < this.MAX_CAPACITY; i++) {
            assertThat(this.storage.isFull(),is(false));
            this.storage.add(food);
        }
        assertThat(this.storage.isFull(), is(true));
    }

    @Test
    public void whenClearThenStorageIsEmpty() throws Exception {
        Food apple = getFoodUnderFreshLimit("apple", this.createDate, this.currentDate, 0d);
        Food tomato = getFoodUnderFreshLimit("tomato", this.createDate, this.currentDate, 0d);
        this.storage.add(apple);
        this.storage.add(tomato);

        this.storage.clear();

        List<Food> foods = this.storage.getFoods();

        assertThat(foods.isEmpty(), is(true));
    }

    @Test
    public void whenGetThenStorageIsNotEmpty() throws Exception {
        Food apple = getFoodUnderFreshLimit("apple", this.createDate, this.currentDate, 0d);
        Food tomato = getFoodUnderFreshLimit("tomato", this.createDate, this.currentDate, 0d);
        this.storage.add(apple);
        this.storage.add(tomato);

        List<Food> foods = this.storage.getFoods();

        assertThat(foods.contains(apple), is(true));
        assertThat(foods.contains(tomato), is(true));
        assertThat(foods.isEmpty(), is(false));
    }

    @Test
    public void whenPoolThenStorageIsEmpty() throws Exception {
        Food apple = getFoodUnderFreshLimit("apple", this.createDate, this.currentDate, 0d);
        Food tomato = getFoodUnderFreshLimit("tomato", this.createDate, this.currentDate, 0d);
        this.storage.add(apple);
        this.storage.add(tomato);

        List<Food> foods = this.storage.poolFoods();

        assertThat(foods.contains(apple), is(true));
        assertThat(foods.contains(tomato), is(true));

        List<Food> foodsAfter = this.storage.poolFoods();
        assertThat(foodsAfter.isEmpty(), is(true));
    }

    /**
     * method returns Food item with expire progress less than limit defined by input param
     * @param name String.
     * @param createDate Date.
     * @param currentDate Date.
     * @param limit double.
     * @return Food item
     */
    private Food getFoodUnderFreshLimit(String name, Date createDate, Date currentDate, double limit) {
        long expTime = (long) ((currentDate.getTime() - createDate.getTime()) / (limit - 0.01) + createDate.getTime());
        Date expireDate = new Date(expTime);
        return new Vegetable(name, createDate, expireDate, 0d);
    }

}