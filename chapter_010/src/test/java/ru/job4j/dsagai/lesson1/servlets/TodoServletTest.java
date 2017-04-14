package ru.job4j.dsagai.lesson1.servlets;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import org.mockito.MockitoAnnotations;
import ru.job4j.dsagai.lesson1.model.TodoTask;
import ru.job4j.dsagai.lesson1.storage.TaskStorage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.regex.Pattern;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


/**
 * Tests for TodoServlet class
 *
 * @author dsagai
 * @version 1.00
 * @since 12.04.2017
 */
public class TodoServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    private TaskStorage storage = TaskStorage.getInstance();

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(this.response.getWriter()).thenReturn(new PrintWriter(new ByteArrayOutputStream()));
    }

    @Test
    public void createTask() throws Exception {

        when(this.request.getParameter("command")).thenReturn("add_update");
        when(this.request.getParameter("description")).thenReturn("AAA");
        when(this.request.getParameter("showAll")).thenReturn("true");
        new TodoServlet().doPost(this.request, this.response);

        boolean result = false;
        TodoTask expected = new TodoTask(0, "AAA", false, new Date(0));
        for (TodoTask task : this.storage.getTaskList(true)) {
            if (task.equals(expected)){
                result = true;
                this.storage.deleteTask(task);
                break;
            }
        }
        assertThat(result, is(true));
    }

    @Test
    public void updateTask() throws Exception {
        String description = "AAA";
        TodoTask updated = new TodoTask(0, description, false, new Date(0));
        this.storage.addUpdateTask(updated);
        int id = 0;

        for( TodoTask task : this.storage.getTaskList(true)) {
            if (task.equals(updated)) {
                id = task.getId();
                break;
            }
        }

        when(this.request.getParameter("command")).thenReturn("add_update");
        when(this.request.getParameter("showAll")).thenReturn("true");
        when(this.request.getParameter("description")).thenReturn(description);
        when(this.request.getParameter("id")).thenReturn(String.valueOf(id));
        when(this.request.getParameter("done")).thenReturn("true");

        new TodoServlet().doPost(request, response);

        boolean result = false;

        for( TodoTask task : this.storage.getTaskList(true)) {
            if (task.getId() == id && description.equals(task.getDescription()) &&
                    task.isDone() == true) {
                result = true;
                this.storage.deleteTask(task);
                break;
            }
        }
        assertThat(result, is(true));
    }

    @Test
    public void getList() throws Exception {
        when(this.request.getParameter("command")).thenReturn("List");
        when(this.request.getParameter("showAll")).thenReturn("true");

        clearDataBase();
        this.storage.addUpdateTask(new TodoTask(0, "AAA", true, new Date(0)));
        this.storage.addUpdateTask(new TodoTask(0, "BBB", false, new Date(0)));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);
        when(this.response.getWriter()).thenReturn(writer);

        new TodoServlet().doPost(this.request, this.response);
        writer.flush();
        clearDataBase();

        String expected = "\\[\\{\"id\":\\d+?,\"description\":\"AAA\",\"created\":0,\"done\":true}," +
                "\\{\"id\":\\d+?,\"description\":\"BBB\",\"created\":0,\"done\":false}]";

        Pattern pattern = Pattern.compile(expected);
        assertThat(pattern.matcher(out.toString()).matches(), is(true));

    }

    private void clearDataBase() {
        for (TodoTask task : this.storage.getTaskList(true)) {
            this.storage.deleteTask(task);
        }
    }
}