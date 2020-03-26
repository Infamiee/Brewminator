package pl.nzi.brewminator.model;

public class Hop {
    private double weight, alphaAcids;
    private int minutes;

    public Hop(double weight, double alphaAcids, int minutes) {
        this.weight = weight;
        this.alphaAcids = alphaAcids;
        this.minutes = minutes;
    }

    public double getWeight() {
        return weight;
    }

    public double getAlphaAcids() {
        return alphaAcids;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setAlphaAcids(double alphaAcids) {
        this.alphaAcids = alphaAcids;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
