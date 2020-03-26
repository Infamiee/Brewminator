package pl.nzi.brewminator.calculator;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import pl.nzi.brewminator.exception.WrongGravitiesException;
import pl.nzi.brewminator.model.Hop;

import static org.junit.Assert.*;

public class IbuCalculatorTest {




    @Test
    public void should_calculate() throws WrongGravitiesException {
        List<Hop> hops = new ArrayList<>();
        hops.add(new Hop(1.5,6.4,45));
        hops.add(new Hop(1,5,15));

        IbuCalculator ibuCalculator = new IbuCalculator(hops, 5, 1.050, false);


        assertEquals(44.67 ,ibuCalculator.calculate(),0.5);
    }


}