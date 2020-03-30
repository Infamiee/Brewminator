package pl.nzi.brewminator.calculator;

import com.digidemic.unitof.UnitOf;

import java.util.List;

import pl.nzi.brewminator.exception.WrongGravitiesException;
import pl.nzi.brewminator.model.HOP;

public class IbuCalculator {

    /*
        IBU = (OUNCES OF HOPS * %UTILIZATION * %ALPHA * 7462) / (Batch Volume* (1 + GA))
        Ragers formula uses the Gravity Adjustment factor (GA) to compensate for batches of higher gravity. (anything over 1.050) So If Boil Gravity is less than 1.050 GA = 0.
        otherwise,
        GA = (BOIL_GRAVITY – 1.050) / 0.2
        %UTILIZATION = 18.11 + (13.86 * hyptan[(MINUTES – 31.32) / 18.27] )
     */
    private List<HOP> hops;
    private double batchSize, originalGravity;

    public IbuCalculator(List<HOP> hops, double batchSize, double originalGravity) {
        this.originalGravity = originalGravity;
        UnitOf.Volume gallons = new UnitOf.Volume().fromLiters(batchSize);
        this.batchSize = gallons.toGallonsUS();

        for (HOP hop : hops) {
            UnitOf.Mass ounces = new UnitOf.Mass().fromGrams(Double.parseDouble(hop.getAMOUNT()));
            hop.setAMOUNT(String.valueOf(ounces.toOuncesUS()));

        }
        this.hops = hops;
    }

    private double getGa() throws WrongGravitiesException {
        if (this.originalGravity >= 1 && this.originalGravity < 2) {
            return (this.originalGravity > 1.050) ? (this.originalGravity - 1.050) / 0.2 : 0;
        } else {
            throw new WrongGravitiesException("Gravity should be between 1 and 2");
        }
    }

    private double getHopUtilization(HOP hop) {
        return (18.11 + (13.86 * Math.tanh((Integer.parseInt(hop.getTIME()) - 31.32) / 18.27)));
    }

    private double getHopIbu(HOP hop) throws WrongGravitiesException {
        System.out.println(Double.parseDouble(hop.getAMOUNT()));
        return (Double.parseDouble(hop.getAMOUNT()) * getHopUtilization(hop) / 100 * Double.parseDouble(hop.getALPHA()) / 100 * 7462) / (this.batchSize * (1 + getGa()));
    }

    public double calculate() throws WrongGravitiesException {
        double ibu = 0;
        for (HOP hop : this.hops) {
            ibu += getHopIbu(hop);
        }

        return ibu;
    }


}
