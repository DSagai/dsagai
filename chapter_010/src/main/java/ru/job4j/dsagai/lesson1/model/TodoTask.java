package ru.job4j.dsagai.lesson1.model;




import javax.persistence.*;
import java.util.Date;

/**
 * Entity class, which contains information
 *
 * @author dsagai
 * @version 1.00
 * @since 05.04.2017
 */
@Entity
@Table(name = "items")
public class TodoTask {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Basic
    @Column(name = "description", nullable = false)
    private String description;

    @Basic
    @Column(name = "create_date", nullable = false)
    private Long created = new Date().getTime();

    @Basic
    @Column(name = "done", nullable = false, columnDefinition = "boolean default false")
    private boolean done;

    /**
     * default constructor.
     */
    public TodoTask() {
    }

    /**
     * additional parametrised constructor.
     * @param description String.
     * @param done boolean.
     */
    public TodoTask(int id, String description, boolean done, Date created) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.created = created.getTime();
    }


    public void setId(int id) {
        this.id = id;
    }


    public void setDescription(String description) {
        this.description = description;
    }



    public void setCreated(Date created) {
        this.created = created.getTime();
    }



    public void setDone(boolean done) {
        this.done = done;
    }


    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreated() {
        return new Date(created);
    }

    public boolean isDone() {
        return done;
    }

    @Override
    public String toString() {
        return "TodoTask{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", created=" + created +
                ", done=" + done +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TodoTask task = (TodoTask) o;

        if (id != task.id) return false;
        if (done != task.done) return false;
        if (!description.equals(task.description)) return false;
        return created != null ? created.equals(task.created) : task.created == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + description.hashCode();
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (done ? 1 : 0);
        return result;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
