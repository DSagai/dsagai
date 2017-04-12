package ru.job4j.dsagai.lesson1.storage;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.dsagai.lesson1.model.TodoTask;


import java.util.List;


/**
 * Tests for taskStorage class.
 *
 * @author dsagai
 * @version 1.00
 * @since 11.04.2017
 */
public class TaskStorageTest {
    private TaskStorage storage;

    @Before
    public void init() {
        this.storage = TaskStorage.getInstance();
    }

    @Test
    public void whenShowAllTrueThenListAll() throws Exception {
        TodoTask completed = new TodoTask();
        completed.setDescription("AAA");
        completed.setDone(true);

        TodoTask uncompleted = new TodoTask();
        uncompleted.setDescription("BBB");
        uncompleted.setDone(false);

        this.storage.addUpdateTask(completed);
        this.storage.addUpdateTask(uncompleted);

        List<TodoTask> tasks = this.storage.getTaskList(true);
        boolean result = (tasks.contains(completed) && tasks.contains(uncompleted) ? true : false);
        for (TodoTask response : tasks) {
            if (completed.equals(response) || uncompleted.equals(response)) {
                this.storage.deleteTask(response);
            }
        }

        Assert.assertThat(result, Is.is(true));

    }


    @Test
    public void whenShowAllFalseThenListUncompleted() throws Exception {
        TodoTask completed = new TodoTask();
        completed.setDescription("AAA");
        completed.setDone(true);

        TodoTask uncompleted = new TodoTask();
        uncompleted.setDescription("BBB");
        uncompleted.setDone(false);

        this.storage.addUpdateTask(completed);
        this.storage.addUpdateTask(uncompleted);

        List<TodoTask> tasks = this.storage.getTaskList(false);
        boolean result = (!tasks.contains(completed) && tasks.contains(uncompleted) ? true : false);

        tasks = this.storage.getTaskList(true);
        for (TodoTask response : tasks) {
            if (completed.equals(response) || uncompleted.equals(response)) {
                this.storage.deleteTask(response);
            }
        }

        Assert.assertThat(result, Is.is(true));
    }

    @Test
    public void whenIdIsZeroThenAdd() throws Exception {
        TodoTask todoTask = new TodoTask();
        todoTask.setDescription("AAA");
        todoTask.setDone(true);
        this.storage.addUpdateTask(todoTask);
        List<TodoTask> tasks = this.storage.getTaskList(true);
        boolean result = false;
        for (TodoTask task : tasks) {
            if (task.equals(todoTask)) {
                result = true;
                this.storage.deleteTask(task);
                break;
            }
        }
        Assert.assertThat(result, Is.is(true));
    }

    @Test
    public void deleteTask() throws Exception {
        TodoTask task = new TodoTask();
        task.setDescription("QQQ");
        task.setDone(true);

        this.storage.addUpdateTask(task);
        List<TodoTask> tasks = this.storage.getTaskList(true);
        for (TodoTask response : tasks) {
            if (task.equals(response)) {
                this.storage.deleteTask(response);
                break;
            }
        }

        tasks = this.storage.getTaskList(true);
        Assert.assertThat(tasks.contains(task), Is.is(false));
    }
}