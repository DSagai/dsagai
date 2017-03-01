package ru.job4j.dsagai.exam.server.game.conditions;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Tests for WinConditionTypes
 *
 * @author dsagai
 * @version 1.00
 * @since 28.02.2017
 */

public class WinConditionTypesTest {

    @Test
    public void whenCallForSequenceConditionThenReceiveSequenceCondition() throws Exception {
        WinCondition condition = WinConditionTypes.SEQUENCE_CONDITION.getWinConditionObject(5);
        assertThat(condition instanceof SequenceCondition, is(true));
    }
}