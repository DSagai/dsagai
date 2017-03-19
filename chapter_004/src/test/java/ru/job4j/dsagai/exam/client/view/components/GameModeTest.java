package ru.job4j.dsagai.exam.client.view.components;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.job4j.dsagai.exam.client.Controller;
import ru.job4j.dsagai.exam.client.view.ConsoleView;
import ru.job4j.dsagai.exam.server.game.round.GameCell;
import ru.job4j.dsagai.exam.server.game.round.GameField;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * GameMode tests
 *
 * @author dsagai
 * @version 1.00
 * @since 15.03.2017
 */
public class GameModeTest {
    @Mock
    private Controller controller;

    @Mock
    private ConsoleView view;
    private ByteArrayOutputStream out;
    //private GameField field = new GameField(3);

    private GameMode gameMode;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.gameMode = new GameMode(this.controller, this.view, 1);
        this.out = new ByteArrayOutputStream();
        this.gameMode.initGameField(3);
        this.gameMode.updateCell(new GameCell(0, 2, 3));
        this.gameMode.updateCell(new GameCell(1, 0, 1));
        this.gameMode.updateCell(new GameCell(1, 2, 2));
        this.gameMode.updateCell(new GameCell(2, 1, 1));
        System.setOut(new PrintStream(this.out));
    }

    @Test
    public void testOfStringRepresentationOfGameField() {



        String expectedOutput = String.format("%s%s%s%s%s%s%s%s%s%s%s",
                " | 1 | 2 | 3 |",
                String.format("%n_|___|___|___|"),
                String.format("%n |   |   |   |"),
                String.format("%n1|   |   | * |"),
                String.format("%n_|___|___|___|"),
                String.format("%n |   |   |   |"),
                String.format("%n2| X |   | O |"),
                String.format("%n_|___|___|___|"),
                String.format("%n |   |   |   |"),
                String.format("%n3|   | X |   |"),
                String.format("%n_|___|___|___|"));
        assertThat(this.gameMode.getTextRepresentation(), is(expectedOutput));
    }

    @Test
    /**
     * constructs fake actions queue. Then checks, that every action
     * was properly processed.
     */
    public void checkActionsQueue() throws Exception {
        this.gameMode.start();
        when(this.view.takeConsoleResponse()).thenReturn("34dd").thenReturn("1,23").thenReturn("1,2");

        this.gameMode.showMessage("Message1");

        this.gameMode.showMessage("Message2");
        this.gameMode.turnRequest();

        Thread.currentThread().sleep(1000);
        this.gameMode.disconnectSession();
        this.gameMode.join();

        assertThat(this.out.toString().contains("Message1"), is(true));
        assertThat(this.out.toString().contains("Message2"), is(true));
        assertThat(this.out.toString().contains("GAME IS STARTING..."), is(true));

        Pattern pattern = Pattern.compile("Wrong format");
        Matcher matcher = pattern.matcher(out.toString());
        int count = 0;
        while (matcher.find()){
            count++;
        }
        assertThat(count, is(2));

        verify(this.controller,times(1)).sendGameTurn(new GameCell(0, 1, 1));
    }

    @Test
    public void whenDisconnectThenMenuMode() throws Exception {
        this.gameMode.start();
        this.gameMode.disconnectSession();
        this.gameMode.join();
        verify(this.view,times(1)).activateMenu();
    }
}