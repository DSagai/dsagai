package ru.job4j.dsagai.lesson1.model.calculations;

/**
 * provides default list of operations for Calculator,
 * and links them to tags.
 * @author dsagai
 * @since 22.12.2016
 */
public enum Operations {
    Add("+", new AddOperation()),
    Subtract("-", new SubstractOperation()),
    Divide("/", new DivOperation()),
    Multiply("*", new MultOperation());


    /**
    * Tag of concrete arithmetic operation
     */
    private final String tag;

    /**
     * Arithmetic operation
     */
    private final ArithmeticOperation operation;

    private Operations(String tag, ArithmeticOperation operation) {
        this.tag = tag;
        this.operation = operation;
    }


    /**
     * Getter
     * @return tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * Getter
     * @return Operation
     */
    public ArithmeticOperation getOperation() {
        return operation;
    }
}
