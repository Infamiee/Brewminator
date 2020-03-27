package pl.nzi.brewminator.calculator;

import com.digidemic.unitof.UnitOf;

import java.util.List;

import pl.nzi.brewminator.exception.WrongGravitiesException;
import pl.nzi.brewminator.model.Hop;

public class IbuCalculator {

    /*
        IBU = (OUNCES OF HOPS * %UTILIZATION * %ALPHA * 7462) / (Batch Volume* (1 + GA))
        Ragers formula uses the Gravity Adjustment factor (GA) to compensate for batches of higher gravity. (anything over 1.050) So If Boil Gravity is less than 1.050 GA = 0.
        otherwise,
        GA = (BOIL_GRAVITY – 1.050) / 0.2
        %UTILIZATION = 18.11 + (13.86 * hyptan[(MINUTES – 31.32) / 18.27] )
     */
    private List<Hop> hops;
    private double batchSize, originalGravity;

    public IbuCalculator(List<Hop> hops, double batchSize, double originalGravity) {

        this.originalGravity = originalGravity;
        UnitOf.Volume gallons = new UnitOf.Volume().fromLiters(batchSize);
        this.batchSize = gallons.toGallonsUS();

        for (Hop hop : hops) {
            UnitOf.Mass ounces = new UnitOf.Mass().fromOuncesUS(hop.getWeight());
            hop.setWeight(ounces.toOuncesUS());

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

    private double getHopUtilization(Hop hop) {
        return (18.11 + (13.86 * Math.tanh((hop.getMinutes() - 31.32) / 18.27)));
    }

    private double getHopIbu(Hop hop) throws WrongGravitiesException {
        return (hop.getWeight() * getHopUtilization(hop) / 100 * hop.getAlphaAcids() / 100 * 7462) / (this.batchSize * (1 + getGa()));
    }

    public double calculate() throws WrongGravitiesException {
        double ibu = 0;
        for (Hop hop : this.hops) {
            ibu += getHopIbu(hop);
        }

        return ibu;
    }


}
