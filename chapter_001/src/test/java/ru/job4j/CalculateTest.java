package ru.job4j;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Fake test for fake class
 *
 * @author dsagai
 * @version 1
 * @since 03.02.2017
 */

public class CalculateTest {

    @Test
    public void test() {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream writer = new PrintStream(out);
        System.setOut(writer);
        Calculate.main(null);
        assertThat(out.toString(), is("Привет мир\r\n"));
    }
}