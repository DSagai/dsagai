package ru.job4j.dsagai;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Test for SessionPeriodProcessor class
 *
 * @author dsagai
 * @version 000
 * @since 27.03.2017
 */
public class SessionPeriodProcessorTest {


    @Test
    public void calcMaxLoadedInterval() throws Exception {
        SessionPeriodProcessor sessionPeriodProcessor = new SessionPeriodProcessor();


        sessionPeriodProcessor.pushInterval(new Date(1), new Date(1000));
        sessionPeriodProcessor.pushInterval(new Date(200), new Date(1200));
        sessionPeriodProcessor.pushInterval(new Date(300), new Date(700));
        sessionPeriodProcessor.pushInterval(new Date(800), new Date(1000));
        sessionPeriodProcessor.pushInterval(new Date(500), new Date(750));

        SessionPeriodProcessor.MaxLoadedInterval expected =
                new SessionPeriodProcessor.MaxLoadedInterval(new Date(500), new Date(700), 4);
        SessionPeriodProcessor.MaxLoadedInterval actual = sessionPeriodProcessor.getMaxLoadedInterval();
        assertThat(actual, is(expected));

    }

}