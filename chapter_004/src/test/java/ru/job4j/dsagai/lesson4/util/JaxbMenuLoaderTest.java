package ru.job4j.dsagai.lesson4.util;

import org.junit.Test;
import ru.job4j.dsagai.lesson4.model.MockModel;
import ru.job4j.dsagai.lesson4.model.Model;
import ru.job4j.dsagai.lesson4.view.ConsoleView;
import ru.job4j.dsagai.lesson4.view.View;
import ru.job4j.dsagai.lesson4.view.menu.MenuItem;
import ru.job4j.dsagai.lesson4.view.menu.MultiItemMenu;

import java.lang.reflect.Field;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Test for JaxbMenuLoader class
 *
 * @author dsagai
 * @version 1.01
 * @since 29.01.2017
 */

public class JaxbMenuLoaderTest {
    @Test
    public void loadMenu() throws Exception {
        MenuLoader loader = JaxbMenuLoader.getInstance();
        loader.init("menu.config.for.test.xml");
        Model model = new MockModel();
        View view = new ConsoleView(model);

        loader.loadMenu(view);


        Field menuField = view.getClass().getDeclaredField("menu");
        menuField.setAccessible(true);
        MultiItemMenu menu = (MultiItemMenu)menuField.get(view);

        loader.loadMenu(view);
        Field itemsField = menu.getClass().getDeclaredField("items");
        itemsField.setAccessible(true);
        List<MenuItem> items = (List<MenuItem>)itemsField.get(menu);
        assertThat(items.get(0).getName(), is("Tasks"));
        assertThat(items.get(1).getName(), is("Exit"));
    }

}