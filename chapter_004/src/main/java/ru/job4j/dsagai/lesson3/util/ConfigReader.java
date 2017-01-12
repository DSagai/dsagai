package ru.job4j.dsagai.lesson3.util;

import java.util.Properties;

/**
 * Utility class
 * provides access to property config file.
 * Singleton.
 * @author dsagai
 * @version 1.00
 * @since 10.01.2017
 */
public class ConfigReader {
    //path to the property file, which defines quality borders
    private final static String PROP_PATH = "lesson3/foodStorApp.properties";

    //class instance
    private static ConfigReader instance;
    //property file
    private final Properties properties;

    /**
     * default constructor.
     */
    private ConfigReader(){
        ClassLoader loader = ConfigReader.class.getClassLoader();
        this.properties = new Properties();
        try {
            this.properties.load(loader.getResourceAsStream(ConfigReader.PROP_PATH));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * returns class instance
     * @return ConfigReader class instance.
     */
    public static ConfigReader getInstance() {
        if (instance == null){
            instance = new ConfigReader();
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
     * returns property value from property file
     * or default value, if property key doesn't exist.
     * @param key String.
     * @param defaultValue String.
     * @return String property value or default value, if property key doesn't exist.
     */
    public String getProperty(String key, String defaultValue) {
        return this.properties.getProperty(key, defaultValue);
    }
}