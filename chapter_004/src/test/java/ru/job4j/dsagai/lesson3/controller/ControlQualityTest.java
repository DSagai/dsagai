package ru.job4j.dsagai.lesson3.controller;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.dsagai.lesson3.food.Food;
import ru.job4j.dsagai.lesson3.food.Vegetable;
import ru.job4j.dsagai.lesson3.storage.Shop;
import ru.job4j.dsagai.lesson3.storage.Trash;
import ru.job4j.dsagai.lesson3.storage.Warehouse;
import ru.job4j.dsagai.lesson3.util.ConfigReader;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Test for ControlQuality class
 * @author dsagai
 * @version 1.00
 * @since 10.01.2017
 */
public class ControlQualityTest {
    private ControlQuality controller;
    private Warehouse warehouse;
    private Shop shop;
    private Trash trash;

    private Date createDate;
    private Date currentDate;
    private SimpleDateFormat format;


    private double freshBorder;
    private double mediumBorder;
    private double oldBorder;

    @Before
    public void init() {
        this.warehouse = new Warehouse();
        this.shop = new Shop();
        this.trash = new Trash();

        this.format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.createDate = this.format.parse("01/01/2016");
            this.currentDate = this.format.parse("11/04/2016");
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.controller = new ControlQuality(this.currentDate, Arrays.asList(this.shop, this.warehouse, this.trash));


        this.freshBorder = Double.parseDouble(ConfigReader.getInstance().getProperty("borderFresh.fresh", "0.0"));
        this.mediumBorder = Double.parseDouble(ConfigReader.getInstance().getProperty("borderFresh.medium", "0.0"));
        this.oldBorder = Double.parseDouble(ConfigReader.getInstance().getProperty("borderFresh.expire", "0.0"));
    }



    @Test
    /**
     * placing food items into controller
     * after it checking: are food items in right places?:
     * one at warehouse, two at shop, one at trash
     * old item at shop has to have discount.
     */
    public void placeFood() throws Exception {
        Food fresh = getFoodUnderFreshLimit("fresh", this.createDate, this.currentDate, this.freshBorder);
        Food medium = getFoodUnderFreshLimit("medium", this.createDate, this.currentDate, this.mediumBorder);
        Food old = getFoodUnderFreshLimit("old", this.createDate, this.currentDate, this.oldBorder);
        Food extraOld = getFoodUnderFreshLimit("extraOld", this.createDate, this.currentDate, this.oldBorder + 0.5);

        this.controller.placeFood(fresh);
        this.controller.placeFood(medium);
        this.controller.placeFood(old);
        this.controller.placeFood(extraOld);

        assertThat(this.warehouse.getFoods().contains(fresh), is(true));
        assertThat(fresh.getDiscount() == 0, is(true));

        assertThat(this.shop.getFoods().contains(medium), is(true));
        assertThat(medium.getDiscount() == 0, is(true));

        assertThat(this.shop.getFoods().contains(old), is(true));
        assertThat(old.getDiscount() != 0, is(true));

        assertThat(this.trash.getFoods().contains(extraOld), is(true));


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
        Food fresh = getFoodUnderFreshLimit("fresh", this.createDate, this.currentDate, this.freshBorder);
        Food medium = getFoodUnderFreshLimit("medium", this.createDate, this.currentDate, this.mediumBorder);
        Food old = getFoodUnderFreshLimit("old", this.createDate, this.currentDate, this.oldBorder);

        this.controller.placeFood(fresh);
        this.controller.placeFood(medium);
        this.controller.placeFood(old);

        this.controller.setCurrentDate(new Date(this.controller.getCurrentDate().getTime() + (10 * 24 * 60 * 60 * 1000)));
        this.controller.replaceFoods();

        assertThat(this.warehouse.getFoods().contains(fresh), is(false));
        assertThat(this.shop.getFoods().contains(fresh), is(true));
        assertThat(fresh.getDiscount() == 0, is(true));

        assertThat(this.shop.getFoods().contains(medium), is(true));
        assertThat(medium.getDiscount() != 0, is(true));

        assertThat(this.shop.getFoods().contains(old), is(false));
        assertThat(this.trash.getFoods().contains(old), is(true));
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