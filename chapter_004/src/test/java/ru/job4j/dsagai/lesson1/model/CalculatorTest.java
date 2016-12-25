package ru.job4j.dsagai.lesson1.model;

import org.junit.Before;
import org.junit.Test;
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

    @Before
    public void init(){
        this.calculator = new Calculator();
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