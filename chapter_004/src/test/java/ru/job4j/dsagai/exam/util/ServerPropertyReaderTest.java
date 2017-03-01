package ru.job4j.dsagai.exam.util;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Tests for ServerPropertyReader
 *
 * @author dsagai
 * @version 1.00
 * @since 22.02.2017
 */

public class ServerPropertyReaderTest {

    @Test
    public void getProperty() throws Exception {
        ServerPropertyReader propertyReader = ServerPropertyReader.getInstance();

        assertThat(propertyReader.getIntValue("server.port"), is(24000));
    }

}