package ru.job4j.dsagai.exam.client.view;

import ru.job4j.dsagai.exam.client.Controller;
import ru.job4j.dsagai.exam.client.view.components.GameMode;
import ru.job4j.dsagai.exam.client.view.components.MenuMode;
import ru.job4j.dsagai.exam.client.view.components.Screen;
import ru.job4j.dsagai.exam.server.game.round.GameField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Console implementation of View interface.
 *
 * @author dsagai
 * @version 1.00
 * @since 18.02.2017
 */
public class ConsoleView implements View {
    private static String SERVER_ADDRESS = "localhost";
    private static int PORT = 6666;

    private final ConsoleListener listener;
    private final Controller controller;
    private Screen screen;

    /**
     * default constructor.
     */
    public ConsoleView() {
        this.listener = new ConsoleListener();
        this.controller = new Controller(this);
        this.controller.setDaemon(true);
    }

    /**
     * method provides console response
     * from listener to screen.
     * @return
     * @throws InterruptedException
     */
    public String takeConsoleResponse() throws InterruptedException {
        return this.listener.getLine();
    }

    /**
     * method activates new screen.
     * @param screen
     */
    public void setScreen(Screen screen) {
        screen.setDaemon(true);
        if (this.screen != null) {
            this.screen.interrupt();
            if (this.screen instanceof GameMode){
                this.controller.disconnectSession();
            }
        }
        this.screen = screen;
        this.screen.start();
    }

    /**
     * activating MenuMode screen.
     */
    public void activateMenu() {
        setScreen(new MenuMode(this.controller, this));
    }

    @Override
    /**
     * redirects server request to the screen, if
     * it is GameMode.
     */
    public void turnRequest() {
        if (this.screen instanceof GameMode) {
            ((GameMode)this.screen).turnRequest();
        }
    }

    @Override
    /**
     * redirects server request to the screen, if
     * it is GameMode.
     */
    public void updateField(GameField field) {
        if (this.screen instanceof GameMode) {
            ((GameMode)this.screen).updateField(field.getArrayRepresentation());
        }
    }

    @Override
    /**
     * reaction in disconnecting from game session is to
     * activate MenuMode.
     */
    public void disconnectSession() {
        activateMenu();
    }

    @Override
    public void showText(String text) {
        this.screen.showMessage(text);
    }

    /**
     * finish program execution.
     */
    public void exit() {
        this.screen.interrupt();
        this.controller.close();
        this.listener.interrupt();
    }

    public void activate() {
        boolean connected = this.controller.connectServer(SERVER_ADDRESS, PORT);
        if (!connected) {
            System.out.println(String.format("Could not connect to the %s:%d. Application closing.",
                    SERVER_ADDRESS, PORT));
            this.controller.close();
        } else {
            this.listener.start();
            this.controller.start();
            activateMenu();
        }
    }

    /**
     * utility class designed to
     * provide communication with user through console.
     *
     */
    private class ConsoleListener extends Thread {
        private static final int BUFFER_SIZE = 1;

        private final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(BUFFER_SIZE);
        private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    String line = this.reader.readLine();
                    switch (line) {
                        case "exit": exit();
                            break;
                        case "menu": menu();
                            break;
                        case "refresh": refresh();
                            break;
                        default: queue.put(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                System.out.println("closing application.");
            }
            close();
        }

        /**
         * returns next console input to the concumer.
         * @return
         * @throws InterruptedException
         */
        public String getLine() throws InterruptedException {
            return this.queue.take();
        }

        /**
         * closing used resources.
         */
        private void close() {
            try {
                this.reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * command for closing application.
         */
        private void exit() {
            ConsoleView.this.exit();
        }

        /**
         * command for refreshing current screen.
         */
        private void refresh() {
            ConsoleView.this.screen.refresh();
        }

        /**
         * command for switching current screen to MenuMode.
         */
        private void menu() {
            activateMenu();
        }
    }

    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        view.activate();
    }

}
