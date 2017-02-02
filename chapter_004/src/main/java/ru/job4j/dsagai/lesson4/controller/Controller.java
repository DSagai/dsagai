package ru.job4j.dsagai.lesson4.controller;

import ru.job4j.dsagai.lesson4.model.MockModel;
import ru.job4j.dsagai.lesson4.model.Model;
import ru.job4j.dsagai.lesson4.util.JaxbMenuLoader;
import ru.job4j.dsagai.lesson4.view.ConsoleView;
import ru.job4j.dsagai.lesson4.view.View;

/**
 * Mock controller.
 *
 * @author dsagai
 * @version 1.01
 * @since 29.01.2017
 */

public class Controller {
    private static Controller instance;

    private View view;
    private Model model;


    /**
     * returns instance of Controller
     * @return Controller
     */
    public static Controller getInstance(){
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    /**
     * default constructor.
     */
    private Controller() {
    }


    /**
     * setter for view
     * @param view View.
     */
    public void setView(View view) {
        this.view = view;
        this.view.initView();
    }

    /**
     * setter for model
     * @param model Model.
     */
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * getter for view.
     * @return View.
     */
    public View getView() {
        return view;
    }


    /**
     * getter for model
     * @return Model.
     */
    public Model getModel() {
        return model;
    }

    /**
     * fake action method
     */
    public void action1(){
        check();
        this.model.doAction("Action1 result");
        this.view.update();
    }

    /**
     * fake action method
     */
    public void action2(){
        check();
        this.model.doAction("Action2 result");
        this.view.update();
    }

    /**
     * termination program command.
     */
    public void terminate() {
        check();
        this.view.terminate();
    }

    private void check() {
        if (this.model == null || this.view == null) {
            throw new RuntimeException("class was not properly initialized! setup first setModel and setView!");
        }
    }

    public static void main(String[] args) {
        Model model = new MockModel();
        View view = new ConsoleView(model);
        view.setMenuLoader(JaxbMenuLoader.getInstance());

        Controller controller = Controller.getInstance();
        controller.setModel(model);
        controller.setView(view);

    }
}
