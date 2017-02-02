package ru.job4j.dsagai.lesson4.view.menu;


import ru.job4j.dsagai.lesson4.view.menu.actions.Actions;


/**
 * Definition for menu item data type which supports executing some defined actions
 *
 * @author dsagai
 * @version 1.01
 * @since 29.01.2017
 */

public interface Executable extends MenuItem {

    /**
     * executes some menu action.
     */
    void execute();

    /**
     * method setups action for the menu item.
     * @param action MenuAction
     */
    void setMenuAction(Actions action);
}
