package ru.job4j.dsagai.lesson1.servlets;

import ru.job4j.dsagai.lesson1.model.TodoList;
import ru.job4j.dsagai.lesson1.model.TodoTask;
import ru.job4j.dsagai.lesson1.services.XmlConverter;
import ru.job4j.dsagai.lesson1.storage.TaskStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Servlet processes requests from TodoTable web application.
 *
 * @author dsagai
 * @version 1.00
 * @since 11.04.2017
 */
@WebServlet("/TodoTaskServlet")
public class TodoServlet extends HttpServlet {

    private final TaskStorage storage = TaskStorage.getInstance();
    private final XmlConverter xmlConverter = XmlConverter.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String command = req.getParameter("command");
            if (command == null) {
                command = "List";
            }
            switch (command) {
                case "List":
                    sendList(req, resp);
                    break;
                case "add_update":
                    addUpdate(req, resp);
                    break;
                default:
                    sendList(req, resp);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException();
        }
    }

    /**
     * adds new task or updates existing one.
     * @param req
     * @param resp
     * @throws ParseException
     */
    private void addUpdate(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
        String idParam = req.getParameter("id");
        String description = req.getParameter("description");
        String createdParam = req.getParameter("created");
        String doneParam = req.getParameter("done");

        TodoTask task = new TodoTask();
        if (idParam != null) {
            task.setId(Integer.parseInt(idParam));
        }

        task.setDescription(description);

        if (createdParam != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss", Locale.ENGLISH);
            task.setCreated(dateFormat.parse(createdParam));
        }

        if (doneParam != null) {
            task.setDone(Boolean.parseBoolean(doneParam));
        }

        this.storage.addUpdateTask(task);

    }

    /**
     * returns to ajax client list of todoTask in xml representation.
     * @param req
     * @param resp
     * @throws IOException
     */
    private void sendList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean showAll = Boolean.parseBoolean(req.getParameter("showAll"));
        TodoList list = new TodoList(this.storage.getTaskList(showAll));
        if (list.getSize() > 0) {
            resp.setContentType("text/xml");
            resp.getWriter().write(this.xmlConverter.toXml(list));
        } else {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
}
