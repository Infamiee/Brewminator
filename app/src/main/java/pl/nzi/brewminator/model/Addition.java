package pl.nzi.brewminator.model;

public class Addition {

    private double amount;
    private String name;
    private int time;

    public Addition(double amount, String name, int time) {
        this.amount = amount;
        this.name = name;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
