package ru.job4j.dsagai.lesson1.model.calculations;

/**
 * Class provides tan operation.
 * @author dsagai
 * @since 25.12.2016
 */
public final class TanOperation implements ArithmeticOperation {

    @Override
    /**
     * tan
     * @param first double unused.
     * @param second double.
     * @return result of arithmetic operation
     */
    public double execute(double first, double second) {
        return Math.tan(second);
    }
}
