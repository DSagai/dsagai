package ru.job4j.dsagai.exam.client.view.components.actions;

/**
 * Action, which makes text output to the screen.
 *
 * @author dsagai
 * @version 1.00
 * @since 15.03.2017
 */
public class ActionShowText implements Action {
    private final String text;

    /**
     * default constructor.
     * @param text String.
     */
    public ActionShowText(String text) {
        this.text = text;
    }

    @Override
    /**
     * Taking an action.
     */
    public void doAction() {
        System.out.println(this.text);
    }
}
