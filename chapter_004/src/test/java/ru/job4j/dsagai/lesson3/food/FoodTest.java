package ru.job4j.dsagai.lesson3.food;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Test for Food class
 * @author dsagai
 * @since 28.12.2016
 */
public class FoodTest {
    @Test
    public void getExpireProgress() throws Exception {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date createDate = format.parse("11/12/2015");
        Date expireDate = format.parse("31/12/2015");
        Food food = new Food("test", createDate, expireDate, 0.0) {};
        assertThat(food.getExpireProgress(format.parse("16/12/2015")), is(0.25));
        assertThat(food.getExpireProgress(format.parse("26/12/2015")), is(0.75));
        assertThat(food.getExpireProgress(format.parse("31/12/2015")), is(1.0));
    }

}