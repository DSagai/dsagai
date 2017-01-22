package ru.job4j.dsagai.lesson4.view;



import ru.job4j.dsagai.lesson4.model.Model;
import ru.job4j.dsagai.lesson4.util.MenuLoader;
import ru.job4j.dsagai.lesson4.view.menu.Executable;
import ru.job4j.dsagai.lesson4.view.menu.ListConsoleMenu;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Class ConsoleView
 * implementation of View interface for
 * communication with user through console.
 * @author dsagai
 * @version 1.00
 * @since 19.01.2017
 */
public class ConsoleView implements View {
    private static String MENU_CONFIG = "standard.menu.config.xml";

    private final Model model;

    private final ConsoleReader consoleReader;

    private ListConsoleMenu menu;

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
        this.menu = new ListConsoleMenu();
        MenuLoader loader = MenuLoader.getInstance();
        loader.init(MENU_CONFIG);
        loader.loadMenu(menu);
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
