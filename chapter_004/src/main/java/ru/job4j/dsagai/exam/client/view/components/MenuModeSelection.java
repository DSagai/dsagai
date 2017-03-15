package ru.job4j.dsagai.exam.client.view.components;

import ru.job4j.dsagai.exam.client.Controller;
import ru.job4j.dsagai.exam.client.view.ConsoleView;

import java.lang.reflect.InvocationTargetException;

/**
 * Configuration for MenuMode
 *
 * @author dsagai
 * @version 1.00
 * @since 14.03.2017
 */
public enum  MenuModeSelection {
    JoinPlayer("Join existing session as Player", JoinSession.class, true),
    JoinSpectator("Join existing session as Spectator", JoinSession.class, false),
    CreateGame("Start own game session", GameCreateMode.class, true);

    //text shown to user
    private final String textDescription;
    //Screen class linked to the selected item.
    private final Class<? extends Screen> clazz;
    //true - player, false - spectator.
    private final boolean player;

    /**
     * default constructor.
     */
    MenuModeSelection(String textDescription, Class<? extends Screen> clazz, boolean player) {
        this.textDescription = textDescription;
        this.clazz = clazz;
        this.player = player;
    }

    /**
     *
     * @return String representation of all menu options.
     */
    public static String allItemsToString() {
        StringBuilder builder = new StringBuilder();
        for (MenuModeSelection selection : values()){
            builder.append(String.format("%d. %s%n", selection.ordinal() + 1, selection.textDescription));
        }
        return builder.toString();
    }

    /**
     * Method return instance of Screen object.
     * @param controller Controller.
     * @param view ConsoleView.
     * @return Screen. Instance of Screen object.
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public Screen getInstance(Controller controller, ConsoleView view) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return this.clazz.getConstructor(Controller.class, ConsoleView.class, boolean.class)
                .newInstance(controller, view, this.player);
    }
}
