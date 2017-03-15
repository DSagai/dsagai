package ru.job4j.dsagai.exam.client.view.components;

import ru.job4j.dsagai.exam.client.Controller;
import ru.job4j.dsagai.exam.client.view.ConsoleView;

/**
 * Main menu screen for console view.
 *
 * @author dsagai
 * @version 1.00
 * @since 18.02.2017
 */
public class MenuMode extends Screen {
    /**
     * default constructor.
     * @param controller Controller.
     * @param view ConsoleView.
     */
    public MenuMode(Controller controller, ConsoleView view) {
        super(controller, view);
    }

    @Override
    // restarts MenuMode thread.
    public void refresh() {
        this.getView().activateMenu();
    }

    @Override
    public void run() {
        System.out.println(MenuModeSelection.allItemsToString());
        System.out.println("Choose next step. Enter number:");
        try {
            int response = Integer.parseInt(this.getView().takeConsoleResponse()) - 1;
            Screen nextScreen = MenuModeSelection.values()[response]
                    .getInstance(this.getController(), this.getView());
            nextScreen.start();
            getView().setScreen(nextScreen);
        } catch (InterruptedException e) {
            this.getView().activateMenu();
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
            this.getView().exit();
        }
    }
}
