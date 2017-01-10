package ru.job4j.dsagai.lesson3.food;

import java.util.Date;


/**
 * Abstract class Food
 * @author dsagai
 * @since 28.12.2016
 */
public abstract class Food {
    private static int DEFAULT_TEMPERATURE = 40;
    private static boolean DEFAULT_RECYCLABLE = true;

    //Brand or name
    private final String name;
    //Date of creation
    private final Date createDate;
    //Final date when food unqualified
    private final Date expireDate;
    //base price for one item
    private final double price;
    //discount
    private double discount;
    //can be recycled
    private final boolean recyclable;
    //store temperature degrees Celsius
    private final int storeTemp;

    /**
     * Default constructor.
     * @param name String.
     * @param createDate Date.
     * @param expireDate Date.
     * @param price double.
     */
    public Food(String name, Date createDate, Date expireDate, double price) {
        this.name = name;
        this.createDate = createDate;
        this.expireDate = expireDate;
        this.price = price;
        this.discount = 0d;
        this.recyclable = Food.DEFAULT_RECYCLABLE;
        this.storeTemp = Food.DEFAULT_TEMPERATURE;
    }

    /**
     * Extended constructor.
     * @param name String.
     * @param createDate Date.
     * @param expireDate Date.
     * @param price double.
     * @param recyclable boolean.
     * @param storeTemp int.
     */
    public Food(String name, Date createDate, Date expireDate, double price, boolean recyclable, int storeTemp) {
        this.name = name;
        this.createDate = createDate;
        this.expireDate = expireDate;
        this.price = price;
        this.discount = 0d;
        this.recyclable = recyclable;
        this.storeTemp = storeTemp;
    }

    /**
     * getter name.
     * @return String product name
     */
    public String getName() {
        return name;
    }

    /**
     * getter createDate.
     * @return Date createDate.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * gettet expireDate.
     * @return Date expireDate.
     */
    public Date getExpireDate() {
        return expireDate;
    }

    /**
     * getter price.
     * @return double price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * getter discount.
     * @return double discount.
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * setter discount.
     * @param discount double.
     */
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * method returns product expiration progress.
     * @param curDate Date.
     * @return double.
     */
    public double getExpireProgress(Date curDate){
        long fullPeriod = this.expireDate.getTime() - this.createDate.getTime();
        long timeLeft = curDate.getTime() - this.createDate.getTime();
        return (double)timeLeft / fullPeriod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Food food = (Food) o;

        if (!name.equals(food.name)) return false;
        return createDate.equals(food.createDate);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + createDate.hashCode();
        return result;
    }

    /**
     * getter recyclable.
     * @return boolean recyclable
     */
    public boolean isRecyclable() {
        return recyclable;
    }

    /**
     * getter storeTemp.
     * @return int storeTemp.
     */
    public int getStoreTemp() {
        return storeTemp;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
