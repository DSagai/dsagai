package ru.job4j.dsagai.lesson1.model.calculations;

/**
 * Class provides sin operation.
 * @author dsagai
 * @since 25.12.2016
 */
public final class SinOperation implements ArithmeticOperation {

    @Override
    /**
     * sin
     * @param first double unused.
     * @param second double.
     * @return result of arithmetic operation
     */
    public double execute(double first, double second) {
        return Math.sin(second);
    }
}
