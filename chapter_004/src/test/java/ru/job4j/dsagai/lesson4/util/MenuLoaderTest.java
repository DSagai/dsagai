package ru.job4j.dsagai.lesson4.util;

import org.junit.Test;
import ru.job4j.dsagai.lesson4.view.menu.ListConsoleMenu;
import ru.job4j.dsagai.lesson4.view.menu.MenuItem;
import ru.job4j.dsagai.lesson4.view.menu.MultiItemMenu;

import java.lang.reflect.Field;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Test for MenuLoader class
 *
 * @author dsagai
 * @version 1.00
 * @since 22.01.2017
 */

public class MenuLoaderTest {


    @Test
    public void loadMenu() throws Exception {
        MenuLoader loader = MenuLoader.getInstance();
        loader.init("menu.config.for.test.xml");

        MultiItemMenu menu = new ListConsoleMenu();
        loader.loadMenu(menu);
        Field field = menu.getClass().getDeclaredField("items");
        field.setAccessible(true);
        List<MenuItem> items = (List<MenuItem>)field.get(menu);
        assertThat(items.get(0).getName(), is("Tasks"));
        assertThat(items.get(1).getName(), is("Exit"));

    }

}