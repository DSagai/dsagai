package ru.job4j.dsagai.lesson1.model.calculations;

/**
 * Class provides multiply operation.
 * @author dsagai
 * @since 22.12.2016
 */
public final class MultOperation implements ArithmeticOperation {
    @Override
    /**
     * Multiping
     * @param first double.
     * @param second double.
     * @return result of arithmetic operation
     */
    public double execute(double first, double second) {
        return first * second;
    }
}
