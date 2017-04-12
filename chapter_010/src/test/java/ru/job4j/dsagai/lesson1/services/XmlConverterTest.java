package ru.job4j.dsagai.lesson1.services;

import org.junit.Test;
import ru.job4j.dsagai.lesson1.model.TodoList;
import ru.job4j.dsagai.lesson1.model.TodoTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Tests for XmlConverter class
 *
 * @author dsagai
 * @version 1.00
 * @since 12.04.2017
 */
public class XmlConverterTest {

    @Test
    public void testXmlConvertion() throws Exception {
        List<TodoTask> tasks = new ArrayList<>();
        tasks.add(new TodoTask(1, "AAA", true, new Date(0)));
        tasks.add(new TodoTask(2, "BBB", false, new Date(0)));
        TodoList list = new TodoList(tasks);
        String result = XmlConverter.getInstance().toXml(list);
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<TodoList>" +
                "<TodoTask id=\"1\" Description=\"AAA\" created=\"1970-01-01T01:00:00+01:00\" done=\"true\"/>" +
                "<TodoTask id=\"2\" Description=\"BBB\" created=\"1970-01-01T01:00:00+01:00\" done=\"false\"/>" +
                "</TodoList>";
        assertThat(result, is(expected));
    }
}