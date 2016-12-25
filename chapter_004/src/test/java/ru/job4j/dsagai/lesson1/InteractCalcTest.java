package ru.job4j.dsagai.lesson1;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.dsagai.lesson1.model.Calculator;
import ru.job4j.dsagai.lesson1.model.calculations.Operations;
import ru.job4j.dsagai.lesson1.view.ConsoleView;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Tests for InteractCalc class
 * @author dsagai
 * @since 24.12.2016
 */
public class InteractCalcTest {
    private InteractCalc interactCalc;

    @Before
    public void init() {
        this.interactCalc = new InteractCalc(new Calculator(), new ConsoleView());
    }

    @Test
    /**
     * basic scenario test: operation and two operands
     */
    public void twoOperandsTest() throws Exception {
        double first = 2.5;
        double second = 3.2;
        this.interactCalc.execute(Operations.Add.getTag(), first, second);
        assertThat(this.interactCalc.getResult(), is(5.7));
    }

    @Test
    /**
     * test execution with one operand (second one)
     * the first operand is taken from result of previous operation.
     */
    public void oneOperandTest() throws Exception {
        double first = 2.5;
        double second = 3.2;
        this.interactCalc.execute(Operations.Add.getTag(), first, second);
        this.interactCalc.execute(Operations.Subtract.getTag(), first);
        assertThat(this.interactCalc.getResult(), is(second));
    }

    @Test
    /**
     * test of repeat operation (no operation and operands was specified)
     * as operation program uses previous operation
     * as first operand - result of previous operation
     * as second operand - second operand of previous execution (stored in lastOperand property)
     */
    public void repeatOperationTest() throws Exception {
        double first = 2.5;
        double second = 3.2;
        this.interactCalc.execute(Operations.Add.getTag(), first, second);
        this.interactCalc.execute();
        assertThat(this.interactCalc.getResult(), is(8.9));
    }

}