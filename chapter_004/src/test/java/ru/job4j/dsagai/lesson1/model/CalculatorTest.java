package ru.job4j.dsagai.lesson1.model;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.job4j.dsagai.lesson1.exceptions.UnsupportedArithmaticOperation;
import ru.job4j.dsagai.lesson1.model.calculations.AddOperation;
import ru.job4j.dsagai.lesson1.model.calculations.ArithmeticOperation;
import ru.job4j.dsagai.lesson1.model.calculations.Operations;
import ru.job4j.dsagai.lesson1.model.calculations.SubstractOperation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Tests for Calculator class
 * @author dsagai
 * @since 24.12.2016
 */
public class CalculatorTest {

    private Calculator calculator;
    @Rule
    public ExpectedException exception = ExpectedException.none();



    @Before
    public void init(){
        this.calculator = new Calculator();
    }

    @Test
    public void whenUnsupportedOperationThenException() throws Exception {
        this.exception.expect(UnsupportedArithmaticOperation.class);
        this.calculator.execute("fakeOperation",1d, 2d);
    }

    @Test
    /**
     * test add operation.
     */
    public void arithmeticOperationTestAdd() throws Exception {

        this.calculator.execute(Operations.Add.getTag(),2.0, 2.0);
        assertThat(this.calculator.getResult(),is(4.0));
    }

    @Test
    /**
     * test subtract operation
     */
    public void arithmeticOperationTestSubstract() throws Exception {
        this.calculator.execute(Operations.Subtract.getTag(),6.5, 2.0);
        assertThat(this.calculator.getResult(),is(4.5));
    }

    @Test
    /**
     * test multiply operation
     */
    public void arithmeticOperationTestMultiply() throws Exception {
        this.calculator.execute(Operations.Multiply.getTag(),6.5, 2.0);
        assertThat(this.calculator.getResult(),is(13.0));
    }

    @Test
    /**
     * test divide operation
     */
    public void arithmeticOperationTestDivide() throws Exception {
        this.calculator.execute(Operations.Divide.getTag(),6.5, 0.0);
        assertThat(this.calculator.getResult(),is(Double.POSITIVE_INFINITY));
    }

    @Test
    /**
     * test sin operation
     */
    public void arithmeticOperationTestSin() throws Exception {
        this.calculator.execute(Operations.Sin.getTag(),0.0, Math.PI / 2.0);
        assertThat(this.calculator.getResult(),is(1.0));
    }

    @Test
    /**
     * test cos operation
     */
    public void arithmeticOperationTestCos() throws Exception {
        this.calculator.execute(Operations.Cos.getTag(),0.0, Math.PI);
        assertThat(this.calculator.getResult(),is(-1.0));
    }

    @Test
    /**
     * test tan operation
     */
    public void arithmeticOperationTestTan() throws Exception {
        this.calculator.execute(Operations.Tan.getTag(),0.0, 0.0);
        assertThat(this.calculator.getResult(),is(0.0));
    }


    @Test
    /**
     * test pow operation
     */
    public void arithmeticOperationTestPow() throws Exception {
        this.calculator.execute(Operations.Pow.getTag(),2.0, 3.0);
        assertThat(this.calculator.getResult(),is(8.0));
    }

    @Test
    /**
     * test Ln operation
     */
    public void arithmeticOperationTestLn() throws Exception {
        this.calculator.execute(Operations.Ln.getTag(),0.0, Math.exp(1));
        assertThat(this.calculator.getResult(),is(1.0));
    }

    @Test
    /**
     * test Lg operation
     */
    public void arithmeticOperationTestLg() throws Exception {
        this.calculator.execute(Operations.Lg.getTag(),0.0, 10.0);
        assertThat(this.calculator.getResult(),is(1.0));
    }

    @Test
    /**
     * test Log operation
     */
    public void arithmeticOperationTestLog() throws Exception {
        this.calculator.execute(Operations.Log.getTag(),2d, 8d);
        assertThat(this.calculator.getResult(),is(3d));
    }

    @Test
    /**
     * test initialization by default constructor.
     * must by initialized by Operations enum
     */
    public void whenDefaultConstructorThenInitsByOperationsEnum() throws Exception {
        Set<String> operationTags = calculator.getOperationTags();

        for (Operations operation : Operations.values()){
            assertTrue(operationTags.contains(operation.getTag()));
        }

        assertThat(operationTags.size(), is(Operations.values().length));
    }

    @Test
    /**
     * test clear method
     */
    public void whenClearTherResultIsNil() throws Exception {
        this.calculator.execute(Operations.Multiply.getTag(),6.5, 2.0);
        this.calculator.clear();
        assertThat(this.calculator.getResult(),is(0.0));
    }

    @Test
    /**
     * test of custom initialization.
     * tags and ArithmeticOperation classes mixed in different way.
     */
    public void customInitiationTest() throws Exception {
        Map<String,ArithmeticOperation> operationMap = new HashMap<>();
        String wrongAdd = "wrong add";
        String wrongSub = "wrong substract";
        operationMap.put(wrongAdd, new SubstractOperation());
        operationMap.put(wrongSub, new AddOperation());

        Calculator customCalculator = new Calculator(operationMap);
        customCalculator.execute(wrongAdd, 8.0, 8.5);
        assertThat(customCalculator.getResult(), is(-0.5));

        customCalculator.execute(wrongSub, 8.0, 8.5);
        assertThat(customCalculator.getResult(), is(16.5));
    }
}