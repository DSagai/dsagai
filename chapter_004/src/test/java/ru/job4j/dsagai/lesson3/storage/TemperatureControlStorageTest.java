package ru.job4j.dsagai.lesson3.storage;

import org.junit.Test;
import ru.job4j.dsagai.lesson3.food.Butter;
import ru.job4j.dsagai.lesson3.food.Food;
import ru.job4j.dsagai.lesson3.food.Vegetable;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Test for TemperatureControlStorage class
 * @author dsagai
 * @version 1.01
 * @since 12.01.2017
 */
public class TemperatureControlStorageTest {

    @Test
    public void isSuitable() throws Exception {

        Storage refrigerator = new TemperatureControlStorage(new Warehouse(), TemperatureControlTypes.Refrigerator.getTemperature());
        Storage cold = new TemperatureControlStorage(new Warehouse(), TemperatureControlTypes.Cold.getTemperature());

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date createDate = format.parse("01/01/2016");
        Date expireDate = format.parse("01/02/2016");

        Food tomato = new Vegetable("tomato", createDate,expireDate,0);
        Food butter = new Butter("butter", createDate, expireDate, 0, 0.82);

        assertThat(refrigerator.isSuitable(tomato, createDate), is(false));
        assertThat(refrigerator.isSuitable(butter, createDate), is(true));

        assertThat(cold.isSuitable(tomato, createDate), is(true));
        assertThat(cold.isSuitable(butter, createDate), is(false));
    }

}