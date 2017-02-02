package ru.job4j.dsagai.lesson4.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.job4j.dsagai.lesson4.view.View;
import ru.job4j.dsagai.lesson4.view.menu.Executable;
import ru.job4j.dsagai.lesson4.view.menu.MenuItem;
import ru.job4j.dsagai.lesson4.view.menu.MultiItemMenu;
import ru.job4j.dsagai.lesson4.view.menu.actions.Actions;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.lang.reflect.Constructor;

/**
 * Singleton util class for initializing
 * MultiItemMenu object from configuration XML file.
 *
 * @author dsagai
 * @version 1.01
 * @since 29.01.2017
 */

public class PomMenuLoader implements MenuLoader {
    private static MenuLoader instance;
    private static String PATH = "lesson4/";

    private Document document;


    /**
     * returns MenuLoader instance.
     * @return MenuLoader
     */
    public static MenuLoader getInstance(){
        if (instance == null) {
            instance = new PomMenuLoader();
        }
        return instance;
    }


    /**
     * private default constructor
     */
    private PomMenuLoader() {
    }

    /**
     * Initializing of the loader with
     * XLM configuration file
     * @param fileName String name of the config file.
     */
    @Override
    public void init(String fileName) {
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            this.document = documentBuilder.parse(classLoader.getResourceAsStream(PATH + fileName),
                    classLoader.getResource(PATH).getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    /**
     * loads menu structure from XML to the menu object
     * and inserts into View object
     * @param view View.
     */
    public void loadMenu(View view) {
        Class menuClass = view.getMenuClass();
        MultiItemMenu menu = null;
        try {
            Constructor constructor = menuClass.getConstructor();
            menu = (MultiItemMenu)constructor.newInstance();
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(String.format("Unable to create instance of %s", menuClass.getSimpleName()));
        }
        if (this.document == null) {
            throw new RuntimeException("Loader is not initialized!. Run init method first!");
        }
        processNode(document.getElementsByTagName("menu").item(0), menu);
        view.setMenu(menu);
    }

    /**
     * Handler for the XML node containing menu items to load.
     * @param node Node.
     * @param menu MultiItem menu.
     */
    private void processNode(Node node, MultiItemMenu menu) {
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node child = list.item(i);
            if (child instanceof Element) {
                Element element = (Element) child;
                MenuItem menuItem = menu.addItem(element.getAttribute("name"));
                if (menuItem instanceof Executable) {
                    ((Executable)menuItem).setMenuAction(Actions.valueOf(element.getAttribute("action")));
                }
                if (menuItem instanceof MultiItemMenu) {
                    processNode(child, (MultiItemMenu)menuItem);
                }
            }
        }
    }


}
