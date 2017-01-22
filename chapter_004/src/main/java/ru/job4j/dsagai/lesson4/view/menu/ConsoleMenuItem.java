package ru.job4j.dsagai.lesson4.view.menu;

import ru.job4j.dsagai.lesson4.view.menu.actions.MenuAction;


import java.util.ArrayList;
import java.util.List;

/**
 * ConsoleMenuItem class provides base functionality
 * for menu item at console application.
 *
 * @author dsagai
 * @version 1.00
 * @since 18.01.2017
 */

public  class ConsoleMenuItem implements NavigableMenu, Executable{
    private final static String PREFIX = "----";

    private final String key;
    private final String name;

    private final String formatPrefix;
    private MenuAction action;

    private final List<MenuItem> children = new ArrayList<>();


    /**
     * defaul constructor
     * @param key String.
     * @param name String.
     * @param formatPrefix String.
     */
    public ConsoleMenuItem(String key, String name, String formatPrefix) {
        this.key = key;
        this.name = name;
        this.formatPrefix = formatPrefix;
    }


    /**
     * getter for key.
     * @return String.
     */
    public String getKey() {
        return this.key;
    }


    /**
     * getter for name.
     * @return String.
     */
    public String getName() {
        return name;
    }

    @Override
    /**
     * method sends request to a storage for creating and placing into it new menu item with defined name.
     * if creation was successful then method returns created item, otherwise it returns null.
     * @param name String.
     * @return MenuItem created item if creation was successful, otherwise returns null.
     */
    public MenuItem addItem(String name) {
        String key = String.format("%s.%s",this.key,this.children.size() + 1);
        String format = this.formatPrefix + PREFIX;
        ConsoleMenuItem item = new ConsoleMenuItem(key, name, format);
        this.children.add(item);
        return item;
    }


    @Override
    /**
     * method calls output of the object to the screen.
     */
    public void draw(){
        System.out.printf("%s%s %s\n", this.formatPrefix, this.name, this.key);
        for (MenuItem child : children){
            child.draw();
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
            for (MenuItem item : this.children) {
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




    @Override
    /**
     * executes some menu action.
     */
    public void execute() {
        if (this.action != null) {
            this.action.execute();
        }
    }

    @Override
    /**
     * method setups action for the menu item.
     * @param action MenuAction
     */
    public void setMenuAction(MenuAction action) {
        this.action = action;
    }


}
