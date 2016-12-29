package ru.job4j.dsagai.lesson3.food;

import java.util.Date;


/**
 * Abstract class Food
 * @author dsagai
 * @since 28.12.2016
 */
public abstract class Food {
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
}
