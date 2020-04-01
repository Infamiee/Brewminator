package pl.nzi.brewminator.calculator;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import pl.nzi.brewminator.exception.WrongGravitiesException;
import pl.nzi.brewminator.model.HOP;

import static org.junit.Assert.*;

public class IbuCalculatorTest {




    @Test
    public void should_calculate() throws WrongGravitiesException {
        List<HOP> hops = new ArrayList<>();
        hops.add(new HOP(String.valueOf(6.4),String.valueOf(42),String.valueOf(45)));
        hops.add(new HOP(String.valueOf(5),String.valueOf(28),String.valueOf(15)));

        IbuCalculator ibuCalculator = new IbuCalculator(hops, 20, 1.050);


        assertEquals(44.67 ,ibuCalculator.calculate(),0.5);
    }


}