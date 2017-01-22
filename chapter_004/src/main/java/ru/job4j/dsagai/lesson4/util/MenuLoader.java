package ru.job4j.dsagai.lesson4.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.job4j.dsagai.lesson4.view.menu.Executable;
import ru.job4j.dsagai.lesson4.view.menu.MenuItem;
import ru.job4j.dsagai.lesson4.view.menu.MultiItemMenu;
import ru.job4j.dsagai.lesson4.view.menu.actions.Actions;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Singleton util class for initializing
 * MultiItemMenu object from configuration XML file.
 *
 * @author dsagai
 * @version 1.00
 * @since 21.01.2017
 */

public class MenuLoader {
    private static MenuLoader instance;
    private static String PATH = "lesson4/";

    private Document document;


    public static MenuLoader getInstance(){
        if (instance == null) {
            instance = new MenuLoader();
        }
        return instance;
    }


    private MenuLoader() {
    }

    /**
     * Initializing of the loader with
     * XLM configuration file
     * @param fileName String name of the config file.
     */
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

    /**
     * loads menu structure from XML to the menu object.
     * @param menu MultiItemMenu.
     */
    public void loadMenu(MultiItemMenu menu) {
       if (this.document == null) {
           throw new RuntimeException("Loader is not initialized!. Run init method first!");
       }
       processNode(document.getElementsByTagName("menu").item(0), menu);
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
                    ((Executable)menuItem).setMenuAction(Actions.valueOf(element.getAttribute("action")).getAction());
                }
                if (menuItem instanceof MultiItemMenu) {
                    processNode(child, (MultiItemMenu)menuItem);
                }
            }
        }
    }


}
