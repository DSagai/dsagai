package ru.job4j.dsagai.exam.client.view.components;

import ru.job4j.dsagai.exam.client.Controller;
import ru.job4j.dsagai.exam.client.view.ConsoleView;
import ru.job4j.dsagai.exam.client.view.components.actions.Action;
import ru.job4j.dsagai.exam.client.view.components.actions.ActionReadConsole;
import ru.job4j.dsagai.exam.client.view.components.actions.ActionShowText;
import ru.job4j.dsagai.exam.client.view.components.actions.TextConsumer;
import ru.job4j.dsagai.exam.server.game.round.GameCell;


import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * GameMode user's screen.
 *
 * @author dsagai
 * @version 1.00
 * @since 18.02.2017
 */
public class GameMode extends Screen {
    private static final Properties CONSOLE_MARKS = new Properties();
    private final static String PROP_PATH = "exam/client/console.marks.properties";

    {
        try {
            CONSOLE_MARKS.load(GameMode.class.getClassLoader().getResourceAsStream(PROP_PATH));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Queue of commands, which would be processed by main loop of this thread.
    private final Queue<Action> actions = new ConcurrentLinkedQueue<>();

    /*utility object, which receives user response on turn request.
    if response is in correct format, then textConsumer produces TURN_RESPONSE
    message to server through controller.
    */
    private final TextConsumer textConsumer = new TextConsumer() {
        @Override
        /**
         * if parse returns GameCell object, then sending game turn response to server.
         * Otherwise if Exception was thrown, then initiates new attempt.
         */
        public void feed(String text) {

            try {
                getController().sendGameTurn(parse(text));
            } catch (Exception e) {
                GameMode.this.actions.offer(new ActionShowText("Wrong format. Try again!"));
                turnRequest();
            }
        }

        /**
         * method tries to parse string to GameCell object.
         * If try was unsuccessful, then throws Exception.
         * @param text String users response.
         * @return GameCell object.
         * @throws Exception if parsing to GameCell was unsuccessful.
         */
        private GameCell parse(String text) throws Exception {
            String[] coordinates = text.split(",");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            return new GameCell(x, y);
        }
    };

    // actual game field information.
    private int[][] gameField;

    /**
     * default constructor.
     * @param controller Controller.
     * @param view ConsoleView.
     */
    public GameMode(Controller controller, ConsoleView view) {
        super(controller, view);
    }

    @Override
    /**
     * The main loop of the thread.
     * Process queue of actions until thread was interrupted.
     */
    public void run() {
        try {
            while (!isInterrupted()) {
                Action action = this.actions.poll();
                if (action != null) {
                    action.doAction();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Closing game");
            this.getView().activateMenu();
        }
    }

    @Override
    /**
     * Redraw to the screen actual game field.
     */
    public void refresh() {
        this.actions.offer(new ActionShowText(getTextRepresentation()));
    }

    /**
     * adds "turn request" action to the actions queue.
     */
    public void turnRequest() {
        showText(String.format("Make you turn! Enter text in format \"X,Y\",%n%s",
                "where X is horizontal coordinate, Y is vertical coordinate."));
        this.actions.offer(new ActionReadConsole(this.textConsumer, getView()));
    }

    /**
     * adds "show text" action to the actions queue
     * @param text
     */
    public void showText(String text) {
        this.actions.offer(new ActionShowText(text));
    }

    /**
     * setter for gameField.
     * @param gameField int[][].
     */
    public void setGameField(int[][] gameField) {
        this.gameField = gameField;
    }

    /**
     * sets up new gameField and make Output to the screen.
     * @param field int[][].
     */
    public void updateField(int[][] field) {
        setGameField(field);
        refresh();
    }

    /**
     * interrupts main thread and go to the Menu screen.
     */
    public void disconnectSession() {
        interrupt();
        getView().activateMenu();
    }


    /**
     * generates text representation of gameField.
     * @return String.
     */
    public String getTextRepresentation() {
        StringBuilder builder = new StringBuilder();
        createHeader(builder);
        for (int i = 0; i < this.gameField[0].length; i++) {
            processRow(i, builder);
        }

        return builder.toString();
    }

    /**
     * utility method for getTextRepresentation().
     * Forms header of string representation of gameField.
     * @param builder StringBuilder.
     */
    private void createHeader(StringBuilder builder) {
        builder.append(" |");
        for (int i = 0; i < this.gameField.length; i++) {
            builder.append(String.format(" %d |", i + 1));
        }
        builder.append(String.format("%n_|"));
        for (int i = 0; i < this.gameField.length; i++) {
            builder.append("___|");
        }
    }

    /**
     * utility method for getTextRepresentation().
     * Forms text row of gameField.
     * @param row int processed row.
     * @param builder StringBuilder.
     */
    private void processRow(int row, StringBuilder builder) {

        builder.append(String.format("%n |"));
        for (int i = 0; i < this.gameField.length; i++) {
            builder.append("   |");
        }
        builder.append(String.format("%n%d|", row + 1));
        for (int i = 0; i < this.gameField.length; i++) {
            String cellValue = this.gameField[row][i] == 0
                    ? " "
                    : CONSOLE_MARKS.getProperty(String.valueOf(this.gameField[row][i]));
            builder.append(String.format(" %s |", cellValue));
        }
        builder.append(String.format("%n_|"));
        for (int i = 0; i < this.gameField.length; i++) {
            builder.append("___|");
        }
    }
}
