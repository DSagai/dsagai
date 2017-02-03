package ru.job4j.dsagai.lesson5.templates;

import ru.job4j.dsagai.lesson5.exceptions.KeyException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SimpleGenerator class generates string in defined format
 *
 * @author dsagai
 * @version 1.00
 * @since 03.02.2017
 */

public class SimpleGenerator implements Template {
    private final Pattern pattern = Pattern.compile("\\$\\{.+?}");

    @Override
    /**
     * Input: "Hello ${name}, I'm your ${destiny}", {"name":"Bobby", "destiny":"death"}
     * Output: "Hello Bobby, I'm your death"
     * @param template String contains text marked with keys, which need to be replaced by data
     * @param data Map<String, String> contains set of pairs key - data
     * @return
     */
    public String generate(String template, Map<String, String> data) throws KeyException {
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> usedKeys = new HashSet<>();
        String key = null;

        Matcher matcher = this.pattern.matcher(template);
        int beginIndex = 0;
        while (matcher.find()){
            key = matcher.group().substring(2, matcher.group().length() - 1);
            if (!data.containsKey(key)) {
                throw new KeyException(String.format("Key %s was not found!", key));
            }
            stringBuilder.append(template.substring(beginIndex, matcher.start()));
            stringBuilder.append(data.get(key));
            beginIndex = matcher.end();
            usedKeys.add(key);
        }
        stringBuilder.append(template.substring(beginIndex, template.length()));

        if (usedKeys.size() != data.size()){
            throw new KeyException("data contains redundant keys!");
        }
        return stringBuilder.toString();
    }
}
