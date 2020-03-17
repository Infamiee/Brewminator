package pl.nzi.brewminator.calculator;

import org.junit.Test;

import pl.nzi.brewminator.exception.WrongGravitiesException;

import static org.junit.Assert.*;

public class AbvCalculatorTest {
    AbvCalculator calculator = new AbvCalculator();
    @Test
    public void should_calculate() throws WrongGravitiesException {
        assertEquals(calculator.calculate(1.050,1.010),5.25,0.05);
        assertEquals(calculator.calculate(1.086,1.010),9.98,0.05);
        assertEquals(calculator.calculate(1.076,1.075),0.13,0.05);
        assertEquals(calculator.calculate(1.775,1.075),91.88,0.05);
    }



    @Test(expected = WrongGravitiesException.class)
    public void should_throw() throws WrongGravitiesException {
        calculator.calculate(1.12,1.2);
        calculator.calculate(1.2,1.12);
        calculator.calculate(1.3,-1.12);


    }
}