package ru.job4j.dsagai.exam.client.view.components;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ru.job4j.dsagai.exam.client.Controller;
import ru.job4j.dsagai.exam.client.view.ConsoleView;
import ru.job4j.dsagai.exam.server.game.InitGameSessionInfo;
import ru.job4j.dsagai.exam.server.game.conditions.WinConditionTypes;
import ru.job4j.dsagai.exam.server.game.round.GameTypes;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * CreateGameMode tests
 *
 * @author dsagai
 * @version 1.00
 * @since 15.03.2017
 */
public class CreateGameModeTest {
    @Mock
    private Controller controller;
    @Mock
    private ConsoleView view;

    private ByteArrayOutputStream out;
    private Screen screen;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.screen = new CreateGameMode(this.controller, this.view, true);
        this.out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this.out));
    }

    @Test
    /**
     * checks how InitGameSessionInfo corresponds to defined input.
     */
    public void checkActionsChain() throws Exception {
        when(view.takeConsoleResponse()).thenReturn("1").thenReturn("1").thenReturn("1").thenReturn("0");
        when(this.controller.createSession(any(InitGameSessionInfo.class))).thenReturn(true);

        this.screen.start();
        this.screen.join();
        InitGameSessionInfo expected = new InitGameSessionInfo(WinConditionTypes.values()[0],
                1,
                GameTypes.values()[0],
                0);
        verify(this.controller, times(1)).createSession(expected);
    }

    @Test
    public void whenServerReturnFalseThenRefresh() throws Exception {
        when(view.takeConsoleResponse()).thenReturn("1").thenReturn("1").thenReturn("1").thenReturn("0");
        when(this.controller.createSession(any(InitGameSessionInfo.class))).thenReturn(false);

        this.screen.start();
        this.screen.join();

        verify(this.view, times(1)).setScreen(any(CreateGameMode.class));
    }


}