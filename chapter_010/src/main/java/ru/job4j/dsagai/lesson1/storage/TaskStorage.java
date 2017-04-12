package ru.job4j.dsagai.lesson1.storage;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.job4j.dsagai.lesson1.model.TodoTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class provides database connections.
 * Implemented using hibernate framework.
 *
 * @author dsagai
 * @version 1.00
 * @since 05.04.2017
 */
public class TaskStorage {
    private static TaskStorage instance;

    public static TaskStorage getInstance() {
        if (instance == null) {
            instance = new TaskStorage();
        }
        return instance;
    }

    private final SessionFactory factory;



    private TaskStorage() {
        this.factory = new Configuration().configure().buildSessionFactory();;
    }

    public List<TodoTask> getTaskList(final boolean showAll) {
        List<TodoTask> result = new ArrayList<>();
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            if (showAll) {
                result = session.createQuery("from TodoTask", TodoTask.class)
                        .getResultList();
            } else {
                result = session.createQuery("from TodoTask where done = :done")
                        .setParameter("done", false)
                        .getResultList();
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return result;
    }

    public void addUpdateTask(TodoTask task) {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.saveOrUpdate(task);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    public void deleteTask(TodoTask task) {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();

        try {

            session.delete(task);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }
}
