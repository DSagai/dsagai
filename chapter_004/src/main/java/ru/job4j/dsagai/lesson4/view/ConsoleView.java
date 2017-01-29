package ru.job4j.dsagai.lesson4.view;



import ru.job4j.dsagai.lesson4.model.Model;
import ru.job4j.dsagai.lesson4.util.JaxbMenuLoader;
import ru.job4j.dsagai.lesson4.util.MenuLoader;
import ru.job4j.dsagai.lesson4.util.PomMenuLoader;
import ru.job4j.dsagai.lesson4.view.menu.Executable;
import ru.job4j.dsagai.lesson4.view.menu.ListConsoleMenu;
import ru.job4j.dsagai.lesson4.view.menu.MultiItemMenu;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Class ConsoleView
 * implementation of View interface for
 * communication with user through console.
 * @author dsagai
 * @version 1.01
 * @since 29.01.2017
 */
public class ConsoleView implements View {
    private static String MENU_CONFIG = "standard.menu.config.xml";

    private final Model model;

    private final ConsoleReader consoleReader;

    private ListConsoleMenu menu;

    private MenuLoader menuLoader;

    /**
     * Default constructor.
     * @param model Model.
     */
    public ConsoleView(Model model) {
        this.model = model;
        this.consoleReader = new ConsoleReader(this);
    }

    @Override
    /**
     * sets up and inits Menu
     */
    public void initView() {
        initMenu();
        this.consoleReader.start();
        this.menu.draw();

    }

    /**
     * method inits menu object.
     */
    private void initMenu() {
        if (this.menuLoader == null) {
            throw new RuntimeException("menuLoader was not initialized!");
        }
        this.menuLoader.init(MENU_CONFIG);
        this.menuLoader.loadMenu(this);
    }

    /**
     * setter for MenuLoader field
     * @param menuLoader MenuLoader.
     */
    public void setMenuLoader(MenuLoader menuLoader) {
        this.menuLoader = menuLoader;
    }

    @Override
    /**
     * updates view after result is ready
     */
    public void update() {
        System.out.println("-----MODEL STATE---");
        System.out.println(this.model.getState());
        System.out.println("-------------------");
        System.out.println();
        this.menu.draw();
    }

    @Override
    /**
     * terminates program.
     */
    public void terminate() {
        System.out.println("Closing program");
        this.consoleReader.interrupt();
    }

    @Override
    /**
     * setter for menu property
     * @param menu MultiItemMenu
     */
    public void setMenu(MultiItemMenu menu) {
        if (menu instanceof ListConsoleMenu) {
            this.menu = (ListConsoleMenu) menu;
        }
    }


    /**
     * method executes command received
     * from ConsoleListener.
     * @param menuCode
     */
    private void executeString(String menuCode) {
        //this.menu.execute(menuCode);
        Executable item = (Executable)this.menu.getItem(menuCode);
        if (item != null) {
            item.execute();
        }
    }

    @Override
    /**
     * method returns appropriate class of menu object for this View implementation
     * @return Class menu class
     */
    public Class getMenuClass() {
        return ListConsoleMenu.class;
    }

    /**
     * ConsoleReader
     * service class for ConsoleView
     * listening console input.
     * @author dsagai
     * @version 1.00
     * @since 19.01.2017
     */
    private static class ConsoleReader extends Thread {
        /**
         * ConsoleView link
         */
        private final ConsoleView listener;

        /**
         * reader
         */
        private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        /**
         * default constructor.
         * @param listener ConsoleView.
         */
        public ConsoleReader(final ConsoleView listener) {
            this.listener = listener;
        }

        /**
         * main loop.
         * listens console input
         * and ask View class for execution.
         */
        private void listen() {
            String execStr;
            try {
                while (!isInterrupted()) {
                    execStr = this.reader.readLine();
                    listener.executeString(execStr);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        /**
         * starts thread
         */
        public void run() {
            listen();
        }
    }
}
