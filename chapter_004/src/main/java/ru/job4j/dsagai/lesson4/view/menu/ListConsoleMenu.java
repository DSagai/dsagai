package ru.job4j.dsagai.lesson4.view.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * ListConsoleMenu class is base implementation
 * of MultiItemMenu for console application.
 *
 * @author dsagai
 * @version 1.00
 * @since 21.01.2017
 */

public class ListConsoleMenu implements NavigableMenu, Drawable {
    private List<MenuItem> items;

    /**
     * default constructor.
     */
    public ListConsoleMenu() {
        this.items = new ArrayList<>();
    }


    @Override
    /**
     * method calls output of the object to the screen.
     */
    public void draw() {
        for (MenuItem item :this.items){
            item.draw();
        }
    }

    @Override
    /**
     * method sends request to a storage for creating and placing into it new menu item with defined name.
     * if creation was successful then method returns created item, otherwise it returns null.
     * @param name String.
     * @return MenuItem created item if creation was successful, otherwise returns null.
     */
    public MenuItem addItem(String name) {
        MenuItem item = new ConsoleMenuItem(String.valueOf(this.items.size() + 1), name, "");
        this.items.add(item);
        return item;
    }

    @Override
    /**
     * method extracts menu item from the storage.
     * @param key String identifier of the child item.
     * @return MenuItem child item from the storage.
     * if item was not found, then returns null.
     */
    public MenuItem getItem(String key) {
        MenuItem result = null;
        if (key != null) {
            for (MenuItem item : this.items) {
                if (item.getKey().equals(key)) {
                    result = item;
                    break;
                } else if (key.indexOf(item.getKey()) == 0) {
                    if (item instanceof NavigableMenu){
                        result = ((NavigableMenu)item).getItem(key);
                    }
                    break;
                }
            }
        }
        return result;
    }
}
