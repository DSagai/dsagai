package ru.job4j.dsagai.exam.util;

import java.util.Properties;

/**
 * Utility class
 * provides access to property config file.
 * Singleton.
 * @author dsagai
 * @version 1.00
 * @since 22.02.2017
 */

public class ServerPropertyReader {
    private static ServerPropertyReader instance;
    //path to the property file, which defines quality borders
    private final static String PROP_PATH = "exam/server/serverSettings.properties";
    //property file
    private final Properties properties;

    /**
     * default constructor
     */
    private ServerPropertyReader() {
        ClassLoader loader = this.getClass().getClassLoader();
        this.properties = new Properties();
        try {
            this.properties.load(loader.getResourceAsStream(PROP_PATH));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return instance of ServerPropertyReader
     */
    public static ServerPropertyReader getInstance() {
        if (instance == null) {
            instance = new ServerPropertyReader();
        }
        return instance;
    }

    /**
     * returns property value from property file.
     * @param key String.
     * @return String property value.
     */
    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }

    /**
     * returns property value from property file.
     * @param key String.
     * @return int property value.
     */
    public int getIntValue(String key) {
        return Integer.parseInt(getProperty(key));
    }


}
