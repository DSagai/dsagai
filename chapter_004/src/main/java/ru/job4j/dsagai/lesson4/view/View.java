package ru.job4j.dsagai.lesson4.view;


import ru.job4j.dsagai.lesson4.util.MenuLoader;
import ru.job4j.dsagai.lesson4.view.menu.MultiItemMenu;

/**
 * Interface View
 * specifies mandatory methods for view part
 * @author dsagai
 * @version 1.01
 * @since 29.01.2017
 */
public interface View {


    /**
     * sets up and inits Menu
     */
    void initView();

    /**
     * updates view after result is ready
     */
    void update();

    /**
     * terminates program.
     */
    void terminate();

    void setMenu(MultiItemMenu menu);

    /**
     * method returns appropriate class of menu object for this View implementation
     * @return Class menu class
     */
    Class getMenuClass();

    /**
     * setter for MenuLoader field
     * @param menuLoader MenuLoader.
     */
    void setMenuLoader(MenuLoader menuLoader);

}
