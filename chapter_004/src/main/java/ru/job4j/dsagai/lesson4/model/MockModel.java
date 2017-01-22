package ru.job4j.dsagai.lesson4.model;

/**
 * Mock model class
 *
 * @author dsagai
 * @version 1.00
 * @since 18.01.2017
 */

public class MockModel implements Model {
    private String modelState;

    @Override
    /**
     * fake model action.
     */
    public void doAction(String param) {
        this.modelState = param;
    }

    @Override
    /**
     * fake model state.
     */
    public String getState() {
        return toString();
    }

    @Override
    public String toString() {
        return "MockModel{" +
                "modelState='" + modelState + '\'' +
                '}';
    }
}
