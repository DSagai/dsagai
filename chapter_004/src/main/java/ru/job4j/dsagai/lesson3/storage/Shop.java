package ru.job4j.dsagai.lesson3.storage;


import ru.job4j.dsagai.lesson3.util.ConfigReader;

/**
 * Shop Storage
 * @author dsagai
 * @version 1.00
 * @since 10.01.2017
 */
public class Shop extends Storage {
    private final static String FRESH_UPPER_BORDER_KEY = "borderFresh.expire";
    private final static String FRESH_BOTTOM_BORDER_KEY = "borderFresh.fresh";
    private final static String DEFAULT_CAPACITY_KEY = "default.warehouseCapacity";


    public Shop() {
        super(Integer.parseInt(ConfigReader.getInstance().getProperty(DEFAULT_CAPACITY_KEY, "0")),
                Double.parseDouble(ConfigReader.getInstance().getProperty(FRESH_UPPER_BORDER_KEY, "0")),
                Double.parseDouble(ConfigReader.getInstance().getProperty(FRESH_BOTTOM_BORDER_KEY, "0")));
    }

    public Shop(int maxCapacity){
        super(maxCapacity,
                Double.parseDouble(ConfigReader.getInstance().getProperty(FRESH_UPPER_BORDER_KEY, "0")),
                Double.parseDouble(ConfigReader.getInstance().getProperty(FRESH_BOTTOM_BORDER_KEY, "0")));
    }

}
