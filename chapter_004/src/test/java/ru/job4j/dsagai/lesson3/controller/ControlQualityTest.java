package ru.job4j.dsagai.lesson3.controller;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.dsagai.lesson3.food.Food;
import ru.job4j.dsagai.lesson3.food.Vegetable;
import ru.job4j.dsagai.lesson3.storage.Storage;
import ru.job4j.dsagai.lesson3.storage.StorageImpl;
import ru.job4j.dsagai.lesson3.storage.Trash;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Tests for ControlQuality class
 * @author dsagai
 * @since 29.12.2016
 */
public class ControlQualityTest {
    private ControlQuality controller;
    private SimpleDateFormat format;

    private double freshBorder;
    private double mediumBorder;
    private double oldBorder;

    @Before
    public void init() throws Exception {
        Storage wareHouse = new StorageImpl(10);
        Storage shop = new StorageImpl(10);
        Storage trash = new Trash();

        this.format = new SimpleDateFormat("dd/MM/yyyy");

        this.controller = new ControlQuality(wareHouse, shop, trash, new Date());

        Properties properties = new Properties();
        ClassLoader loader = ControlQuality.class.getClassLoader();

        properties.load(loader.getResourceAsStream("lesson3/foodStorApp.properties"));
        this.freshBorder = Double.parseDouble(properties.getProperty("borderFresh.fresh", "0.0"));
        this.mediumBorder = Double.parseDouble(properties.getProperty("borderFresh.medium", "0.0"));
        this.oldBorder = Double.parseDouble(properties.getProperty("borderFresh.expire", "0.0"));


    }

    @Test
    /**
     * placing foods by fillController method
     * after it checking: are food items in right places:
     * one at warehouse, two at shop
     */
    public void placeFood() throws Exception {
        fillController();

        Date currentDate = this.controller.getCurrentDate();
        Food food = this.controller.getWarehouse().getFoods().get(0);
        assertTrue(food.getExpireProgress(currentDate) < this.freshBorder &&
                food.getDiscount() == 0d);

        food = this.controller.getShop().getFoods().get(0);
        assertTrue(food.getExpireProgress(currentDate) >= this.freshBorder &&
                food.getExpireProgress(currentDate) < this.mediumBorder &&
                food.getDiscount() == 0d);

        food = this.controller.getShop().getFoods().get(1);
        assertTrue(food.getExpireProgress(currentDate) >= this.mediumBorder &&
                food.getExpireProgress(currentDate) < this.oldBorder &&
                food.getDiscount() > 0d);

        assertThat(this.controller.getTrash().getFoods().size(), is(0));
    }

    @Test
    /**
     * placing food items into controller
     * than changing controller date and replacing foods
     * "fresh" item has to move into shop w/o discount
     * "medium" item has to receive discount
     * "old" item will be at trash
     */
    public void replaceFoods() throws Exception {
        fillController();

        Date newDate = new Date(this.controller.getCurrentDate().getTime() + (10 * 24 * 60 * 60 * 1000));
        this.controller.setCurrentDate(newDate);

        this.controller.replaceFoods();

        assertThat(this.controller.getWarehouse().getFoods().size(), is(0));

        assertThat(this.controller.getShop().getFoods().get(0).getName(), is("fresh"));
        assertThat(this.controller.getShop().getFoods().get(0).getDiscount(), is(0d));

        assertThat(this.controller.getShop().getFoods().get(1).getName(), is("medium"));
        assertTrue(this.controller.getShop().getFoods().get(1).getDiscount() > 0d);

        assertThat(this.controller.getTrash().getFoods().get(0).getName(), is("old"));



    }

    /**
     * method inserts into controller three food items
     * fresh one (at the wear house), medium one (at the shop without discount) and
     * the old one (at the shop with discount)
     * @throws Exception
     */
    private void fillController() throws Exception {
        this.controller.getWarehouse().clear();
        this.controller.getShop().clear();
        this.controller.getTrash().clear();

        Date createDate = this.format.parse("01/01/2016");
        Date currentDate = this.format.parse("11/04/2016");

        this.controller.setCurrentDate(currentDate);

        this.controller.placeFood(getFoodUnderFreshLimit("fresh", createDate, currentDate, this.freshBorder));
        this.controller.placeFood(getFoodUnderFreshLimit("medium", createDate, currentDate, this.mediumBorder));
        this.controller.placeFood(getFoodUnderFreshLimit("old", createDate, currentDate, this.oldBorder));



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