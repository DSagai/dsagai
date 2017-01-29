package ru.job4j.dsagai.lesson4.view;

import org.junit.Test;
import ru.job4j.dsagai.lesson4.controller.Controller;
import ru.job4j.dsagai.lesson4.model.MockModel;
import ru.job4j.dsagai.lesson4.model.Model;
import ru.job4j.dsagai.lesson4.util.JaxbMenuLoader;
import ru.job4j.dsagai.lesson4.view.menu.Executable;
import ru.job4j.dsagai.lesson4.view.menu.MultiItemMenu;
import ru.job4j.dsagai.lesson4.view.menu.NavigableMenu;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Test for MVC application with executable console menu.
 *
 * @author dsagai
 * @version 1.01
 * @since 29.01.2017
 */

public class ConsoleViewTest {


    @Test
    public void update() throws Exception {
        Model model = new MockModel();
        View view = new ConsoleView(model);
        view.setMenuLoader(JaxbMenuLoader.getInstance());
        Controller controller = Controller.getInstance();
        controller.setModel(model);
        controller.setView(view);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Field field = view.getClass().getDeclaredField("menu");
        field.setAccessible(true);
        NavigableMenu menu = (NavigableMenu) field.get(view);
        Executable item = (Executable) menu.getItem("1.1.1");
        item.execute();
        assertThat(out.toString().indexOf("Action1") > -1, is(true));
        out.reset();
        item = (Executable) menu.getItem("1.1.2");
        item.execute();
        assertThat(out.toString().indexOf("Action2") > -1, is(true));
        out.reset();
        item = (Executable) menu.getItem("1.2");
        item.execute();
        assertThat(out.toString().indexOf("Closing program") > -1, is(true));


    }

}