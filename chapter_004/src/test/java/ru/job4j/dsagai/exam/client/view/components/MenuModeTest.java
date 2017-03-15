package ru.job4j.dsagai.exam.client.view.components;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.job4j.dsagai.exam.client.Controller;
import ru.job4j.dsagai.exam.client.view.ConsoleView;
import ru.job4j.dsagai.exam.server.game.GameSession;
import ru.job4j.dsagai.exam.server.game.GameSessionInfo;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;


import static org.mockito.Mockito.*;

/**
 * Tests for MenuMode
 *
 * @author dsagai
 * @version 1.00
 * @since 14.03.2017
 */
public class MenuModeTest {
    @Mock
    private Controller controller;
    @Mock
    private ConsoleView view;
    private ByteArrayOutputStream out;

    @Before
    public void init() {
        this.out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this.out));
        MockitoAnnotations.initMocks(this);

        GameSession session = mock(GameSession.class);
        Mockito.when(session.getUid()).thenReturn("uid1");
        Mockito.when(session.getAvailablePlayerConnectionsCount()).thenReturn(2);
        Mockito.when(session.getAvailableSpectatorConnectionsCount()).thenReturn(10);
        GameSessionInfo sessionInfo = new GameSessionInfo(session);
        Mockito.when(this.controller.getActiveSessions()).thenReturn(Arrays.asList(sessionInfo));
    }

    @Test
    public void whenChooseOneThenReceiveJoinPlayer() throws Exception {
        Mockito.when(this.view.takeConsoleResponse()).thenReturn("1");
        Screen menuMode = new MenuMode(this.controller, this.view);
        menuMode.start();
        menuMode.join();
        verify(this.view, atLeastOnce()).setScreen(any(JoinSession.class));
    }
}