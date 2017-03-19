package ru.job4j.dsagai.exam.client.view.components;


import org.junit.Before;

import org.junit.Test;
import org.mockito.Mockito;
import ru.job4j.dsagai.exam.client.Controller;
import ru.job4j.dsagai.exam.client.view.ConsoleView;

import ru.job4j.dsagai.exam.server.game.GameSession;
import ru.job4j.dsagai.exam.server.game.GameSessionInfo;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.util.Arrays;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * JoinSession tests
 *
 * @author dsagai
 * @version 1.00
 * @since 13.03.2017
 */
public class JoinSessionTest {
    private Controller controller;
    private ConsoleView view;
    private GameSession session;
    private GameSessionInfo sessionInfo;
    private ByteArrayOutputStream out;

    @Before
    public void init() {
        this.out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this.out));
        this.controller = mock(Controller.class);
        this.view = mock(ConsoleView.class);
        this.session = mock(GameSession.class);
        Mockito.when(this.session.getUid()).thenReturn("uid1");
        Mockito.when(this.session.getAvailablePlayerConnectionsCount()).thenReturn(2);
        Mockito.when(this.session.getAvailableSpectatorConnectionsCount()).thenReturn(10);
        this.sessionInfo = new GameSessionInfo(this.session);
        Mockito.when(this.controller.getActiveSessions()).thenReturn(Arrays.asList(this.sessionInfo));
    }

    @Test
    public void whenWrongChoiceThenTryAgain() throws Exception {
        Mockito.when(this.view.takeConsoleResponse()).thenReturn("u");
        JoinSession joinSession = new JoinSession(this.controller, this.view, true);

        joinSession.start();
        joinSession.join();

        assertThat(this.out.toString().contains("Incorrect input. Try again."), is(true));
    }

    @Test
    public void whenConnectionUnsuccessfulThenTryAgain() throws Exception {
        Mockito.when(this.view.takeConsoleResponse()).thenReturn("1");
        Mockito.when(this.controller.connectGameSession("uid1", true)).thenReturn(-1);
        JoinSession joinSession = new JoinSession(this.controller, this.view, true);

        joinSession.start();
        joinSession.join();

        assertThat(this.out.toString().contains("Connection was unsuccessful. Try again."), is(true));
    }

    @Test
    public void whenInterruptedExceptionThenGoToMenu() throws Exception {

        Mockito.when(this.view.takeConsoleResponse()).thenThrow(InterruptedException.class);


        JoinSession joinSession = new JoinSession(this.controller, this.view, true);
        joinSession.start();
        joinSession.join();
        assertThat(this.out.toString().contains("Closing JoinSession menu."), is(true));
    }
}