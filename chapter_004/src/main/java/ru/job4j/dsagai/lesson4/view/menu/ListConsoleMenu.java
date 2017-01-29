package ru.job4j.dsagai.lesson4.view.menu;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ListConsoleMenu class is base implementation
 * of MultiItemMenu for console application.
 *
 * @author dsagai
 * @version 1.01
 * @since 29.01.2017
 */

@XmlRootElement(name = "menu")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListConsoleMenu implements NavigableMenu, Drawable {

    @XmlAnyElement(lax = true)
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
     * reinitialize values of internal properties: key and formatPrefix
     */
    public void init() {
        for (int i = 0; i < this.items.size(); i++) {
            this.items.get(i).init(String.valueOf(i + 1), "");
        }
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
