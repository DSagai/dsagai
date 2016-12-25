package ru.job4j.dsagai.lesson1.view;


import org.junit.*;
import ru.job4j.dsagai.lesson1.InteractCalc;
import ru.job4j.dsagai.lesson1.model.Calculator;
import ru.job4j.dsagai.lesson1.model.calculations.Operations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Tests for InteractCalc class
 * @author dsagai
 * @since 24.12.2016
 */
public class ConsoleViewTest {
    private ConsoleView consoleView;
    private ByteArrayOutputStream out;

    @Before
    public void init() {
        this.consoleView = new ConsoleView();
        Calculator calculator = new Calculator();
        InteractCalc interactCalc = new InteractCalc(new Calculator(), consoleView);
        this.consoleView.init(interactCalc, calculator.getOperationTags());

        this.out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this.out));
    }



    @Test
    /**
     * two operand string format test
     */
    public void executeStringTwoOperands() throws Exception {
        consoleView.executeString(String.format("5.2 %s 4.2", Operations.Add.getTag()));
        assertThat(out.toString(),is(String.format("Result equals to %.2f \n", 9.4)));
    }

    @Test
    /**
     * one operand string format test
     */
    public void executeStringOneOperand() throws Exception {
        consoleView.executeString(String.format("5.2 %s 4.2", Operations.Add.getTag()));
        out.reset();

        consoleView.executeString(String.format("%s 4.0", Operations.Add.getTag()));
        assertThat(out.toString(),is(String.format("Result equals to %.2f \n", 13.4)));
    }

    @Test
    /**
     * no operand test (repeat operation)
     */
    public void executeStringNoOperands() throws Exception {
        consoleView.executeString(String.format("5.2 %s 4.2", Operations.Add.getTag()));
        out.reset();

        consoleView.executeString("");
        assertThat(out.toString(),is(String.format("Result equals to %.2f \n", 13.6)));
    }
}