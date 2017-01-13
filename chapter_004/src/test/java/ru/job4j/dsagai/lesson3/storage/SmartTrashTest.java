package ru.job4j.dsagai.lesson3.storage;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.dsagai.lesson3.FoodFactoryForTests;
import ru.job4j.dsagai.lesson3.controller.ControlQuality;
import ru.job4j.dsagai.lesson3.food.Butter;
import ru.job4j.dsagai.lesson3.food.Food;
import ru.job4j.dsagai.lesson3.food.Meat;
import ru.job4j.dsagai.lesson3.food.Sausage;
import ru.job4j.dsagai.lesson3.util.ConfigReader;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Test for  SmartTrashTest class
 * @author dsagai
 * @version 1.02
 * @since 13.01.2017
 */
public class SmartTrashTest {
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

        this.controller = new ControlQuality(this.currentDate);
        this.controller.addStorage(this.shop);
        this.controller.addStorage(this.warehouse);
        this.controller.addStorage(new SmartTrash(this.trash, this.controller));


        this.freshBorder = Double.parseDouble(ConfigReader.getInstance().getProperty("borderFresh.fresh", "0.0"));
        this.mediumBorder = Double.parseDouble(ConfigReader.getInstance().getProperty("borderFresh.medium", "0.0"));
        this.oldBorder = Double.parseDouble(ConfigReader.getInstance().getProperty("borderFresh.expire", "0.0"));
    }

    @Test
    public void whenReproducibleThenToReproductionElseToTrash() throws Exception {
        Food oldMeat = FoodFactoryForTests.getFoodUnderFreshLimit("old meat", this.createDate, this.currentDate, 1.2,  Meat.class);
        Food oldButter = FoodFactoryForTests.getFoodUnderFreshLimit("old butter", this.createDate, this.currentDate, 1.2,  Butter.class);

        this.controller.placeFood(oldButter);
        this.controller.placeFood(oldMeat);

        assertThat(this.trash.getFoods().contains(oldButter), is(true));
        assertThat(this.trash.getFoods().contains(oldMeat), is(false));

        assertThat(this.warehouse.getFoods().get(0) instanceof Sausage, is(true));


    }

}