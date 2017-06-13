package ru.job4j.dsagai.lesson1.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.dsagai.lesson1.model.TodoTask;
import ru.job4j.dsagai.lesson1.storage.TaskStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.util.List;


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
    private final ObjectMapper jsonMapper = new ObjectMapper();

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
    private void addUpdate(HttpServletRequest req, HttpServletResponse resp) throws ParseException, IOException {
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
            task.setCreated(Long.parseLong(createdParam));
        }

        if (doneParam != null) {
            task.setDone(Boolean.parseBoolean(doneParam));
        }

        this.storage.addUpdateTask(task);
        sendList(req, resp);

    }

    /**
     * returns to ajax client list of todoTask in json representation.
     * @param req
     * @param resp
     * @throws IOException
     */
    private void sendList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean showAll = Boolean.parseBoolean(req.getParameter("showAll"));
        List<TodoTask> list = this.storage.getTaskList(showAll);
        if (list.size() > 0) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("utf-8");
            Writer writer = resp.getWriter();
            this.jsonMapper.writeValue(writer, list);
        } else {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
}
