package ru.job4j.dsagai.exam.util;

import org.junit.Test;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Tests for MessagePropertyReader
 *
 * @author dsagai
 * @version 1.00
 * @since 18.02.2017
 */

public class MessagePropertyReaderTest {

    private MessagePropertyReader reader = MessagePropertyReader.getInstance();


    @Test
    public void enLocaleTest() throws Exception {
        this.reader.setLocale(Locale.ENGLISH);

        String result = "new round begins!";
        assertThat(this.reader.getString("game.round.started"), is(result));

        result = "player number 1 has won the session!";
        assertThat(String.format(this.reader.getString("game.end.win"), 1), is(result));
    }

}