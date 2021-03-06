package ru.job4j.dsagai.lesson4.util;

import ru.job4j.dsagai.lesson4.view.View;
import ru.job4j.dsagai.lesson4.view.menu.ConsoleMenuItem;
import ru.job4j.dsagai.lesson4.view.menu.ListConsoleMenu;
import ru.job4j.dsagai.lesson4.view.menu.MultiItemMenu;
import ru.job4j.dsagai.lesson4.view.menu.actions.Actions;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import java.io.InputStream;


/**
 * Singleton util class for initializing
 * MultiItemMenu object from configuration XML file.
 * Uses JAXB serialization
 *
 * @author dsagai
 * @version 1.01
 * @since 29.01.2017
 */

public class JaxbMenuLoader implements MenuLoader {
    private static MenuLoader instance;
    private static String PATH = "lesson4/";
    //configuration xml file
    private InputStream in;
    private final JAXBContext context;


    /**
     * returns MenuLoader instance.
     * @return MenuLoader
     */
    public static MenuLoader getInstance(){
        if (JaxbMenuLoader.instance == null) {
            try {
                JaxbMenuLoader.instance = new JaxbMenuLoader();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return JaxbMenuLoader.instance;
    }

    /**
     * private default constructor
     */
    private JaxbMenuLoader() throws JAXBException {
        this.context = JAXBContext.newInstance(ListConsoleMenu.class, ConsoleMenuItem.class, Actions.class);
    }

    @Override
    /**
     * Initializing of the loader with
     * XLM configuration file
     * @param fileName String name of the config file.
     */
    public void init(String fileName) {
        this.in = this.getClass().getClassLoader().getResourceAsStream(PATH + fileName);
    }



    @Override
    /**
     * loads menu structure from XML to the menu object
     * and inserts into View object
     * @param view View.
     */
    public void loadMenu(View view) {
        {
            MultiItemMenu menu = null;
            try {
                Unmarshaller unmarshaller = context.createUnmarshaller();
                menu  = (MultiItemMenu)unmarshaller.unmarshal(this.in);
                menu.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
            view.setMenu(menu);
        }


    }
}
