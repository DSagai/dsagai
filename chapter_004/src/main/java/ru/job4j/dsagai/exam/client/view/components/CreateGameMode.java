package ru.job4j.dsagai.exam.client.view.components;

import ru.job4j.dsagai.exam.client.Controller;
import ru.job4j.dsagai.exam.client.view.ConsoleView;
import ru.job4j.dsagai.exam.client.view.components.actions.Action;
import ru.job4j.dsagai.exam.client.view.components.actions.ActionReadConsole;
import ru.job4j.dsagai.exam.client.view.components.actions.ActionShowText;
import ru.job4j.dsagai.exam.client.view.components.actions.TextConsumer;
import ru.job4j.dsagai.exam.server.game.InitGameSessionInfo;
import ru.job4j.dsagai.exam.server.game.conditions.WinConditionTypes;
import ru.job4j.dsagai.exam.server.game.round.GameTypes;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Create new game user's screen.
 *
 * @author dsagai
 * @version 1.00
 * @since 18.02.2017
 */
public class CreateGameMode extends Screen {
    private final Queue<Action> actions = new ArrayDeque<>();
    private final boolean player;

    private final NewGameConstructor textConsumer = new NewGameConstructor();

    /**
     * Default constructor.
     * Predefines actions queue.
     * @param controller Controller.
     * @param view ConsoleView.
     * @param player boolean.
     */
    public CreateGameMode(Controller controller, ConsoleView view, boolean player) {
        super(controller, view);
        this.player = player;

        this.actions.offer(new ActionShowText("Select game type:"));
        this.actions.offer(new ActionShowText(GameTypes.allItemsToString()));
        this.actions.offer(new ActionReadConsole(this.textConsumer, getView()));

        this.actions.offer(new ActionShowText("Select win condition:"));
        this.actions.offer(new ActionShowText(WinConditionTypes.allItemsToString()));
        this.actions.offer(new ActionReadConsole(this.textConsumer, getView()));

        this.actions.offer(new ActionShowText("Select count to reach win condition:"));
        this.actions.offer(new ActionReadConsole(this.textConsumer, getView()));

        this.actions.offer(new ActionShowText(String.format("Select bot configuration:%s%s%s",
                String.format("%n0 - don't add bot player"),
                String.format("%n1 - add bot as the first player"),
                String.format("%n2 - add bot as the second player"))));

        this.actions.offer(new ActionReadConsole(this.textConsumer, getView()));
    }

    @Override
    /**
     * Main loop of the thread.
     * It processes predefined actions queue.
     * If the queue was precessed, then creating InitGameSessionInfo object
     * and trying to create new game session.
     */
    public void run() {
        try {
            while (!isInterrupted() && !this.actions.isEmpty()) {
                Action action = this.actions.poll();
                if (action != null) {
                    action.doAction();
                }
            }
            if (!isInterrupted()) {
                InitGameSessionInfo sessionInfo = new InitGameSessionInfo(this.textConsumer.getWinCondition(),
                        this.textConsumer.getCount(),
                        this.textConsumer.getGameType(),
                        this.textConsumer.getAddBotOption());

                boolean result = getController().createSession(sessionInfo);
                if (result) {
                    Screen screen = new GameMode(getController(), getView());
                    getView().setScreen(screen);
                } else {
                    System.out.println("Creation of game session fails. Try again.");
                    refresh();
                }
            }
        } catch (InterruptedException e) {
            //refresh();
        }
    }

    @Override
    /**
     * restarts CreateGameMode thread.
     */
    public void refresh() {
        Screen createSession = new CreateGameMode(getController(), getView(), this.player);
        getView().setScreen(createSession);
    }

    /**
     * NewGameConstructor collects information needed
     * for filling InitGameSessionInfo.
     */
    private class NewGameConstructor implements TextConsumer {
        //counter which allow to determine, which field'll be filled next.
        private int i;

        private WinConditionTypes winCondition;
        private int count;
        private GameTypes gameType;
        private int addBotOption = 0;

        @Override
        public void feed(String text) {
            try {
                int selection = Integer.parseInt(text);
                switch (this.i) {
                    case 0: this.gameType = GameTypes.values()[selection - 1];
                        break;
                    case 1: this.winCondition = WinConditionTypes.values()[selection - 1];
                        break;
                    case 2: this.count = selection;
                        break;
                    case 3: this.addBotOption = selection;
                        break;
                }
                if (this.addBotOption < 0 || this.addBotOption > 2) {
                    throw new IndexOutOfBoundsException();
                }
                i++;
            } catch (Exception e) {
                System.out.println("Incorrect input! Try again.");
                refresh();
            }
        }

        public WinConditionTypes getWinCondition() {
            return winCondition;
        }

        public int getCount() {
            return count;
        }

        public GameTypes getGameType() {
            return gameType;
        }

        public int getAddBotOption() {
            return addBotOption;
        }
    }

}
