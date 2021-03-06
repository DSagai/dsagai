package ru.job4j.dsagai.lesson4.view.menu;

/**
 * Interface defines base data type for menu items
 *
 * @author dsagai
 * @version 1.01
 * @since 29.01.2017
 */

public interface MenuItem extends Drawable {

    /**
     * method returns key of the item
     * @return String.
     */
    String getKey();

    /**
     * method returns name of the item
     * @return String.
     */
    String getName();

    /**
     * method setups key and prefix properties
     * @param key String.
     * @param formatPrefix String.
     */
    void init(String key, String formatPrefix);
}
