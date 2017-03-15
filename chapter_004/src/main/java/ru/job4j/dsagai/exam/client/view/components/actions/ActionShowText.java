package ru.job4j.dsagai.exam.client.view.components.actions;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 15.03.2017
 */
public class ActionShowText implements Action {
    private final String text;

    public ActionShowText(String text) {
        this.text = text;
    }

    @Override
    public void doAction() {
        System.out.println(this.text);
    }
}
