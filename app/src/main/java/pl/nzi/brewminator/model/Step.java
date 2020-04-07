package pl.nzi.brewminator.model;

import java.util.List;

public class Step {
    private String name;
    private int time;
    List<Addition> additions;

    public Step(String name, int time, List<Addition> additions) {
        this.name = name;
        this.time = time;
        this.additions = additions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<Addition> getAdditions() {
        return additions;
    }

    public void setAdditions(List<Addition> additions) {
        this.additions = additions;
    }
}
