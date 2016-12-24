package ru.job4j.dsagai.lesson1.model.calculations;

/**
 * Class provides divide operation.
 * @author dsagai
 * @since 22.12.2016
 */
public final class DivOperation implements ArithmeticOperation {
    @Override
    /**
     * Dividing
     * @param first double.
     * @param second double.
     * @return result of arithmetic operation
     */
    public double execute(double first, double second) {
        return first / second;
    }
}
