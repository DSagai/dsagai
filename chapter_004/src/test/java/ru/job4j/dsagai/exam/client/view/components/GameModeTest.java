package ru.job4j.dsagai.exam.client.view.components;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.job4j.dsagai.exam.client.Controller;
import ru.job4j.dsagai.exam.client.view.ConsoleView;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

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

    private GameMode gameMode;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.gameMode = new GameMode(this.controller, this.view);
    }

    @Test
    public void testOfStringRepresentationOfGameField() {
        int[][] field = {{0, 0, 3},
                         {1, 0, 2},
                         {0, 1, 0}};
        this.gameMode.setGameField(field);

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
}