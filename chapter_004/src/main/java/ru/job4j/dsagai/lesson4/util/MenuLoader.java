package ru.job4j.dsagai.lesson4.util;

import ru.job4j.dsagai.lesson4.view.View;


/**
 * Interface declares standard methods for menu loader
 *
 * @author dsagai
 * @version 1.01
 * @since 29.01.2017
 */

public interface MenuLoader {
    /**
     * Initializing of the loader with
     * XLM configuration file
     * @param fileName String name of the config file.
     */
    void init(String fileName);




    /**
     * loads menu structure from XML to the menu object
     * and inserts into View object
     * @param view View.
     */
    void loadMenu(View view);
}
