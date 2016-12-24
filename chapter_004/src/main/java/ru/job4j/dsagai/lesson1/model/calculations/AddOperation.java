package ru.job4j.dsagai.lesson1.model.calculations;

/**
 * Class provides add operation.
 * @author dsagai
 * @since 22.12.2016
 */
public final class AddOperation implements ArithmeticOperation {


    @Override
    /**
     * Adding
     * @param first double.
     * @param second double.
     * @return result of arithmetic operation
     */
    public double execute(double first, double second) {
        return first + second;
    }
}
