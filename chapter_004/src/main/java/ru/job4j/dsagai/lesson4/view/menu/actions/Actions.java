package ru.job4j.dsagai.lesson4.view.menu.actions;

import ru.job4j.dsagai.lesson4.controller.Controller;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Enum contains available menu actions
 *
 * @author dsagai
 * @version 1.01
 * @since 29.01.2017
 */
@XmlType
@XmlEnum(String.class)
public enum Actions {
    @XmlEnumValue("Empty")
    Empty(new EmptyAction()),
    @XmlEnumValue("FirstAction")
    FirstAction(new FirstAction(Controller.getInstance())),
    @XmlEnumValue("SecondAction")
    SecondAction(new SecondAction(Controller.getInstance())),
    @XmlEnumValue("ExitAction")
    ExitAction(new ExitAction(Controller.getInstance()));

    private final MenuAction action;

    Actions(MenuAction action) {
        this.action = action;
    }

    public MenuAction getAction() {
        return action;
    }
}
