package ru.job4j.dsagai.lesson5.templates;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;
import ru.job4j.dsagai.lesson5.exceptions.KeyException;

/**
 * Tests for SimpleGenerator class
 *
 * @author dsagai
 * @version 1.00
 * @since 03.02.2017
 */

public class SimpleGeneratorTest {
    private Template generator;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() {
        this.generator = new SimpleGenerator();
    }

    @Test
    public void whenTemplateContainsOneKeyAndDataContainsTheSameKeyThenKeysAreReplaced() throws Exception {
        String oneKeyTemplate = "Hello ${world}!";
        Map<String, String> oneKeyData = new HashMap<>();
        oneKeyData.put("world","World");

        String expectedOutput = "Hello World!";
        assertThat(this.generator.generate(oneKeyTemplate, oneKeyData), is(expectedOutput));

    }

    @Test
    public void whenTemplateContainsMultiEntriesOfOneKeyAndDataContainsTheSameKeyThenKeysAreReplaced()
            throws Exception {
        String multiEntriesOfOneKey = "Help! ${sos}, ${sos}, ${sos}";
        Map<String, String> oneKeyData = new HashMap<>();
        oneKeyData.put("sos","AAA!!!");
        String expectedOutput = "Help! AAA!!!, AAA!!!, AAA!!!";
        assertThat(this.generator.generate(multiEntriesOfOneKey, oneKeyData), is(expectedOutput));
    }

    @Test
    public void whenTemplateContainsMultiEntriesOfMultiKeyAndDataContainsTheSameKeysThenKeysAreReplaced()
            throws Exception {
        String multiEntriesMultiKeys = "${name} ${action} a ${item}. \n" +
                "A ${item} ${name} ${action}. \n" +
                "If ${name} ${action} a ${item}, \n" +
                "Where's the ${item} ${name} ${action}?";
        Map<String, String> multiKeyData = new HashMap<>();
        multiKeyData.put("name", "Peter Piper");
        multiKeyData.put("action", "picked");
        multiKeyData.put("item", "peck of pickled peppers");

        String expectedOutput = "Peter Piper picked a peck of pickled peppers. \n" +
                "A peck of pickled peppers Peter Piper picked. \n" +
                "If Peter Piper picked a peck of pickled peppers, \n" +
                "Where's the peck of pickled peppers Peter Piper picked?";

        assertThat(this.generator.generate(multiEntriesMultiKeys, multiKeyData), is(expectedOutput));
    }

    @Test
    public void whenDataDoesNotContainAllKeysThenThrowsException()
            throws Exception {
        String template = "Dog says ${dogTalks}. Cat says ${catTalks}.";
        Map<String, String> data = new HashMap<>();
        data.put("dogTalks", "Gav!");

        this.thrown.expect(KeyException.class);
        this.generator.generate(template, data);

    }

    @Test
    public void whenDataContainsRedundantKeyThenThrowsException()
            throws Exception {
        String template = "Dog says ${dogTalks}.";
        Map<String, String> data = new HashMap<>();
        data.put("dogTalks", "Gav!");
        data.put("catTalks", "Miau!");

        this.thrown.expect(KeyException.class);
        this.generator.generate(template, data);


    }
}