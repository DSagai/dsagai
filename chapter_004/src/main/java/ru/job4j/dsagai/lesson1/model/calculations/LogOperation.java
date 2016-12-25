package ru.job4j.dsagai.lesson1.model.calculations;

/**
 * Class provides general logarithm operation.
 * @author dsagai
 * @since 25.12.2016
 */
public final class LogOperation implements ArithmeticOperation {

    @Override
    /**
     *  general logarithm
     * @param first double - base of logarithm.
     * @param second double.
     * @return result of arithmetic operation
     */
    public double execute(double first, double second) {
        return Math.log(second) / Math.log(first);
    }
}
