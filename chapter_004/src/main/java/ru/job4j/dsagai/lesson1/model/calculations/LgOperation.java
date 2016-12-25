package ru.job4j.dsagai.lesson1.model.calculations;

/**
 * Class provides base 10 logarithm operation.
 * @author dsagai
 * @since 25.12.2016
 */
public final class LgOperation implements ArithmeticOperation {

    @Override
    /**
     *  base 10 logarithm
     * @param first double unused.
     * @param second double.
     * @return result of arithmetic operation
     */
    public double execute(double first, double second) {
        return Math.log10(second);
    }
}
