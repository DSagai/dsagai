package ru.job4j.dsagai.lesson3.storage;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.dsagai.lesson3.food.Food;
import ru.job4j.dsagai.lesson3.food.Vegetable;
import ru.job4j.dsagai.lesson3.util.ConfigReader;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Tests for WarehouseManager class
 * @author dsagai
 * @since 07.01.2017
 */
public class WarehouseManagerTest {
    private Storage warehouseManager;
    private Storage[] refrigeratorStorages;
    private Storage[] coldStorages;
    private Storage[] normalStorages;

    private int frostTemp;
    private int coldTemp;
    private int normalTemp;

    final int SINGLE_STORAGE_CAPACITY = 2;
    final int STORAGE_GROUP_CAPACITY = 2;

    @Before
    public void init() {
        ConfigReader configReader = ConfigReader.getInstance();
        this.frostTemp = Integer.parseInt(configReader.getProperty("borderTemp.refrigerator"));
        this.coldTemp = Integer.parseInt(configReader.getProperty("borderTemp.cold"));
        this.normalTemp = Integer.parseInt(configReader.getProperty("borderTemp.normal"));

        this.refrigeratorStorages = new Storage[STORAGE_GROUP_CAPACITY];
        this.coldStorages = new Storage[STORAGE_GROUP_CAPACITY];
        this.normalStorages = new Storage[STORAGE_GROUP_CAPACITY];

        initStor(this.refrigeratorStorages);
        initStor(this.coldStorages);
        initStor(this.normalStorages);

        this.warehouseManager = new WarehouseManager(this.refrigeratorStorages, this.coldStorages, this.normalStorages);
    }

    private void initStor(Storage[] storages) {



        for (int i = 0; i < storages.length; i++) {
            storages[i] = new StorageImpl(SINGLE_STORAGE_CAPACITY);
        }
    }


    @Test
    public void add() throws Exception {
        Food fish1 = getFood("fish1", this.frostTemp);
        Food fish2 = getFood("fish2", this.frostTemp);
        Food fish3 = getFood("fish3", this.frostTemp);
        Food butter1 = getFood("butter1", this.coldTemp);
        Food tomato = getFood("tomato", this.normalTemp);

        this.warehouseManager.add(fish1);
        this.warehouseManager.add(fish2);
        this.warehouseManager.add(fish3);
        this.warehouseManager.add(butter1);
        this.warehouseManager.add(tomato);

        assertThat(this.refrigeratorStorages[0].getFoods().contains(fish1), is(true));
        assertThat(this.refrigeratorStorages[0].getFoods().contains(fish2), is(true));
        assertThat(this.refrigeratorStorages[1].getFoods().contains(fish3), is(true));
        assertThat(this.coldStorages[0].getFoods().contains(butter1), is(true));
        assertThat(this.normalStorages[0].getFoods().contains(tomato), is(true));

    }

    @Test
    public void addAll() throws Exception {
        Food fish1 = getFood("fish1", this.frostTemp);
        Food fish2 = getFood("fish2", this.frostTemp);
        Food fish3 = getFood("fish3", this.frostTemp);
        Food butter1 = getFood("butter1", this.coldTemp);
        Food tomato = getFood("tomato", this.normalTemp);

        this.warehouseManager.addAll(Arrays.asList(fish1, fish2, fish3, butter1, tomato));

        assertThat(this.refrigeratorStorages[0].getFoods().contains(fish1), is(true));
        assertThat(this.refrigeratorStorages[0].getFoods().contains(fish2), is(true));
        assertThat(this.refrigeratorStorages[1].getFoods().contains(fish3), is(true));
        assertThat(this.coldStorages[0].getFoods().contains(butter1), is(true));
        assertThat(this.normalStorages[0].getFoods().contains(tomato), is(true));
    }

    @Test
    public void remove() throws Exception {
        Food fish1 = getFood("fish1", this.frostTemp);
        Food fish2 = getFood("fish2", this.frostTemp);
        Food fish3 = getFood("fish3", this.frostTemp);
        Food butter1 = getFood("butter1", this.coldTemp);
        Food tomato = getFood("tomato", this.normalTemp);

        this.warehouseManager.addAll(Arrays.asList(fish1, fish2, fish3, butter1, tomato));
        this.warehouseManager.remove(fish1);
        List<Food> foods = this.warehouseManager.getFoods();
        assertThat(foods.contains(fish1), is(false));
    }

    @Test
    public void getFoods() throws Exception {

    }

    @Test
    public void poolFoods() throws Exception {

    }

    @Test
    public void clear() throws Exception {

    }

    @Test
    public void isFull() throws Exception {

    }

    private Food getFood(String name,int borderTemp){
        return new Vegetable(name, new Date(), new Date(), 0, false, borderTemp - 1);
    }

}