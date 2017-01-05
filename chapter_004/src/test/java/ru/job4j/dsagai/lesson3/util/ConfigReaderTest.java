package ru.job4j.dsagai.lesson3.util;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Tests for ConfigReader class
 * @author dsagai
 * @since 01.01.2017
 */
public class ConfigReaderTest {
    private ConfigReader reader = ConfigReader.getInstance();

    @Test
    public void getProperty() throws Exception {
        assertThat(reader.getProperty("borderFresh.expire"), is("1"));
    }

    @Test
    public void getPropertyReturnsDefault() throws Exception {
        assertThat(reader.getProperty("fake.key","777"), is("777"));
    }

}