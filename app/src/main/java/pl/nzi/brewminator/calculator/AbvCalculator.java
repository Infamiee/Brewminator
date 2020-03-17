package pl.nzi.brewminator.calculator;

import pl.nzi.brewminator.exception.WrongGravitiesException;

public class AbvCalculator {

    //ABV =(76.08 * (og-fg) / (1.775-og)) * (fg / 0.794)
    public double calculate(double og, double fg) throws WrongGravitiesException {
        if ((og>=1 && og<2) && (fg>=1 && fg<2)){
            if (og > fg){
            return  ((og - fg)*131.25);
            }
            else throw new WrongGravitiesException("Original gravity should be higher than final gravity");
        }else throw new WrongGravitiesException("Gravities should be between 1 and 2");


    }
}
