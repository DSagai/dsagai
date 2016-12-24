package ru.job4j.dsagai.lesson1.model;

import ru.job4j.dsagai.lesson1.model.calculations.ArithmeticOperation;
import ru.job4j.dsagai.lesson1.model.calculations.Operations;
import ru.job4j.dsagai.lesson1.exceptions.UnsupportedArithmaticOperation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class Calculator
 * @author dsagai
 * @since 22.12.2016
 */
public class Calculator {


    /**
     * result of execution
     */
    private double result;

    /**
     * available operations
     */
    private final Map<String, ArithmeticOperation> operations;

    /**
     * default constructor inits base arithmetic operations from Operations Enum.
     */
    public Calculator() {
        this.operations = new HashMap<String, ArithmeticOperation>();
        for (Operations operation : Operations.values()){
            this.operations.put(operation.getTag(),operation.getOperation());
        }
    }

    /**
     * Constructor with specified operations
     * @param operations Map<String, ArithmeticOperation> specific outfit of operations
     */
    public Calculator(Map<String, ArithmeticOperation> operations) {
        this.operations = new HashMap<>(operations);
    }

    /**
     * execution of arithmetic operation.
     * you can get result of execution by:
     * @see #getResult
     * @param tag String defines operation type.
     * @param first double operand.
     * @param second double operand.
     * @throws  UnsupportedArithmaticOperation
     */
    public void execute(String tag, double first, double second) throws UnsupportedArithmaticOperation {

        if (!this.operations.containsKey(tag)){
            throw new UnsupportedArithmaticOperation(String.format("Operation %s is not supported by this version of calculator", tag));
        }

        this.result = this.operations.get(tag).execute(first, second);
    }

    /**
     * Getter.
     * @return result of the calculation.
     */
    public double getResult() {
        return result;
    }

    /**
     * Method returns set of available operations
     * @return Set of available operation tags
     */
    public Set<String> getOperationTags(){
        return operations.keySet();
    }

    /**
     * Method clears result field
     */
    public void clear() {
        result = 0.0;
    }
}
