package ru.job4j.dsagai.lesson1.model.calculations;

/**
 * Class provides natural logarithm (base e) operation.
 * @author dsagai
 * @since 25.12.2016
 */
public final class LnOperation implements ArithmeticOperation {

    @Override
    /**
     * natural logarithm (base e)
     * @param first double unused.
     * @param second double.
     * @return result of arithmetic operation
     */
    public double execute(double first, double second) {
        return Math.log(second);
    }
}
