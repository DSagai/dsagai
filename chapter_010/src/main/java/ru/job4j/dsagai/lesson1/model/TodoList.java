package ru.job4j.dsagai.lesson1.model;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * List of tasks. Used to be converted to XML
 *
 * @author dsagai
 * @version 1.00
 * @since 11.04.2017
 */
@XmlRootElement(name = "TodoList")
public class TodoList {

    @XmlAnyElement
    private List<TodoTask> tasks;

    /**
     * default constructor.
     * @param tasks List<TodoTask>
     */
    public TodoList(List<TodoTask> tasks) {
        this.tasks = tasks;
    }

    /**
     * no argument constructor.
     */
    public TodoList() {
    }

    /**
     * setter for field tasks.
     * @param tasks TodoTask.
     */
    public void setTasks(List<TodoTask> tasks) {
        this.tasks = tasks;
    }

    /**
     * method return size of included list
     * @return int.
     */
    public int getSize() {
        return this.tasks.size();
    }
}
