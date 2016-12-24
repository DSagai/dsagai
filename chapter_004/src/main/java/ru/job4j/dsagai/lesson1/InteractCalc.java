package ru.job4j.dsagai.lesson1;

import ru.job4j.dsagai.lesson1.model.Calculator;
import ru.job4j.dsagai.lesson1.view.ConsoleView;
import ru.job4j.dsagai.lesson1.view.View;

/**
 * Class InteractCalc
 * starter class of Interactive calculator
 * and controller of MVC
 * @author dsagai
 * @since 22.12.2016
 */
public class InteractCalc {
    /**
     * Model
     * Provides arithmetic calculations
     */
    private final   Calculator calculator;

    /**
     * View
     */
    private final View view;

    /**
     * stores last operation used
     */
    private String lastOperation;

    /**
     * stores last operand used
     */
    private double lastOperand;

    /**
     * default constructor
     * sets up and inits private properties
     */
    public InteractCalc() {
        this.calculator = new Calculator();
        this.view = new ConsoleView();
        this.view.init(this, this.calculator.getOperationTags());
        this.lastOperation = this.calculator.getOperationTags().iterator().next();
        this.lastOperand = 0;
    }


    /**
     * Base calc operation
     * @param operation String.
     * @param first double.
     * @param last double.
     */
    public void execute(String operation, double first, double last) {
        try {
            calculator.execute(operation, first, last);
            this.lastOperand = last;
            this.lastOperation = operation;
            view.update(calculator.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Calc operation, which uses previous calculation result as first operand.
     * @param operation String.
     * @param first double.
     */
    public void execute (String operation, double first) {
        execute(operation, this.calculator.getResult(), first);
    }

    /**
     * Calc operation, which uses previous operation stored in lastOperation property
     * result of previous calculation as first operand
     * lastOperand property as second operand
     */
    public void execute() {
        execute(this.lastOperation, this.calculator.getResult(), this.lastOperand);
    }

    /**
     * returns result of calculation.
     * @return double.
     */
    public double  getResult(){
        return calculator.getResult();
    }

    /**
     * starting point.
     * @param args String[].
     */
    public static void main(String[] args) {
        InteractCalc interactCalc = new InteractCalc();
    }

}
