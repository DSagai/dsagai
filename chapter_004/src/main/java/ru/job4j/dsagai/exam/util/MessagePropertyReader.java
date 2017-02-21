package ru.job4j.dsagai.exam.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class for retrieving stored messages in custom Language
 *
 * @author dsagai
 * @version 1.00
 * @since 18.02.2017
 */

public class MessagePropertyReader {
    private static MessagePropertyReader instance;
    private static final String PATH = "exam.server.serverMessages";


    private ResourceBundle resourceBundle;

    private MessagePropertyReader() {
        setLocale(Locale.getDefault());
    }

    public void setLocale(Locale locale) {
        this.resourceBundle = ResourceBundle.getBundle(PATH, locale);
    }

    public static MessagePropertyReader getInstance() {
        if (instance == null) {
            instance = new MessagePropertyReader();
        }
        return instance;
    }


    public String getString(String key) {
        return this.resourceBundle.getString(key);
    }
}
