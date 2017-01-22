package ru.job4j.dsagai.lesson4.view.menu;

/**
 * Extension of  MultiItemMenu for implementing access
 * to the particular menu Item
 *
 * @author dsagai
 * @version 1.00
 * @since 21.01.2017
 */

public interface NavigableMenu extends MultiItemMenu {

    /**
     * method extracts menu item from the storage.
     * @param key String identifier of the child item.
     * @return MenuItem child item from the storage.
     * if item was not found, then returns null.
     */
    MenuItem getItem(String key);
}
