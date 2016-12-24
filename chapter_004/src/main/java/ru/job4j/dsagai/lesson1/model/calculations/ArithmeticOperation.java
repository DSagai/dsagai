package ru.job4j.dsagai.lesson1.model.calculations;

/**
 * Interface Arithmetic Operation
 * provides base operations for Calculator class.
 * @author dsagai
 * @since 22.12.2016
 */
public interface ArithmeticOperation {

    /**
     * Execution
     * @param first double.
     * @param second double.
     * @return result of arithmetic operation
     */
    double execute(double first, double second);

}
