package ru.job4j.dsagai.lesson5.templates;

import ru.job4j.dsagai.lesson5.exceptions.KeyException;

import java.util.Map;

/**
 * SimpleGenerator class generates string in defined format
 *
 * @author dsagai
 * @version 1.00
 * @since 03.02.2017
 */

public class SimpleGenerator implements Template {

    @Override
    /**
     * Input: "Hello ${name}, I'm your ${destiny}", {"name":"Bobby", "destiny":"death"}
     * Output: "Hello Bobby, I'm your death"
     * @param template String contains text marked with keys, which need to be replaced by data
     * @param data Map<String, String> contains set of pairs key - data
     * @return
     */
    public String generate(String template, Map<String, String> data) throws KeyException {
        return null;
    }
}
