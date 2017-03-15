package ru.job4j.dsagai.exam.client.view;

import ru.job4j.dsagai.exam.client.Controller;
import ru.job4j.dsagai.exam.client.view.components.MenuMode;
import ru.job4j.dsagai.exam.client.view.components.Screen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 18.02.2017
 */
public class ConsoleView implements View {
    private final ConsoleListener listener;
    private final Controller controller;
    private Screen screen;

    public ConsoleView() {
        this.listener = new ConsoleListener();
        this.controller = new Controller(this);
    }

    public String takeConsoleResponse() throws InterruptedException {
        return this.listener.queue.take();
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public void activateMenu() {
        this.screen.interrupt();
        this.screen = new MenuMode(this.controller, this);
        this.screen.start();
    }

    @Override
    public void turnRequest() {

    }

    @Override
    public void updateField(int[][] field) {

    }

    @Override
    public void disconnectSession() {

    }

    @Override
    public void showText(String text) {

    }

    /**
     * finish program execution.
     */
    public void exit() {
        this.listener.interrupt();
        this.controller.close();
    }

    private class ConsoleListener extends Thread {
        private static final int BUFFER_SIZE = 10;

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
                System.out.println("closing apllication.");
            }
            close();
        }

        public String getLine() throws InterruptedException {
            return this.queue.take();
        }

        private void close() {
            try {
                this.reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void exit() {
            ConsoleView.this.exit();
        }

        private void refresh() {
            ConsoleView.this.screen.refresh();
        }

        private void menu() {
            activateMenu();
        }
    }


}
