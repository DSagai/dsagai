package ru.job4j.dsagai.lesson1.model;




import javax.persistence.*;
import javax.xml.bind.annotation.*;
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
@XmlRootElement(name = "TodoTask")
@XmlAccessorType(XmlAccessType.FIELD)
public class TodoTask {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @XmlAttribute(name = "id")
    private int id;

    @Basic
    @Column(name = "description", nullable = false)
    @XmlAttribute(name = "description")
    private String description;

    @Basic
    @Column(name = "create_date", nullable = false)
    @XmlAttribute(name = "created")
    private Date created = new Date();

    @Basic
    @Column(name = "done", nullable = false, columnDefinition = "boolean default false")
    @XmlAttribute(name = "done")
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
        this.created = created;
    }


    public void setId(int id) {
        this.id = id;
    }


    public void setDescription(String description) {
        this.description = description;
    }



    public void setCreated(Date created) {
        this.created = created;
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
        return created;
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

        TodoTask todoTask = (TodoTask) o;

        if (done != todoTask.done) return false;
        return description.equals(todoTask.description);
    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + (done ? 1 : 0);
        return result;
    }
}
