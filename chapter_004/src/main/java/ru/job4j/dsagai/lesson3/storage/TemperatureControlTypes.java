package ru.job4j.dsagai.lesson3.storage;

import ru.job4j.dsagai.lesson3.util.ConfigReader;

/**
 * Enum defines available Warehouses types with temperature control.
 * @author dsagai
 * @version 1.01
 * @since 12.01.2017
 */
public enum TemperatureControlTypes {
    Refrigerator(Integer.parseInt(ConfigReader.getInstance().getProperty("borderTemp.refrigerator"))),
    Cold(Integer.parseInt(ConfigReader.getInstance().getProperty("borderTemp.cold"))),
    Normal(Integer.parseInt(ConfigReader.getInstance().getProperty("borderTemp.normal")));

    private final int temperature;

    TemperatureControlTypes(int temperature) {
        this.temperature = temperature;
    }

    public int getTemperature() {
        return temperature;
    }
}
