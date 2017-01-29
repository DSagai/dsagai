package ru.job4j.dsagai.lesson4.view.menu;

/**
 * Menu items storage interface
 *
 * @author dsagai
 * @version 1.01
 * @since 29.01.2017
 */

public interface MultiItemMenu extends Drawable {
    /**
     * method sends request to a storage for creating and placing into it new menu item with defined name.
     * if creation was successful then method returns created item, otherwise it returns null.
     * @param name String.
     * @return MenuItem created item if creation was successful, otherwise returns null.
     */
    MenuItem addItem(String name);

    /**
     * reinitialize values of internal properties: key and formatPrefix
     */
    void init();
}
