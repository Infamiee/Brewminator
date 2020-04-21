package pl.nzi.brewminator.model;

import android.os.CountDownTimer;
import android.view.View;

import java.util.List;

import pl.nzi.brewminator.service.RecipeSteps;

public class Step {
    private int id;
    private String message;
    private List<String> additions;
    private int time;
    private RecipeSteps.STEP step;
    private CountDownTimer timer;
    private View view;


    public Step(int id, String message, List<String> additions, int time, RecipeSteps.STEP step) {
        this.id = id;
        this.message = message;
        this.additions = additions;
        this.time = time;
        this.step = step;
    }

    public List<String> getAdditions() {
        return additions;
    }

    public void setAdditions(List<String> additions) {
        this.additions = additions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public CountDownTimer getTimer() {
        return timer;
    }

    public void setTimer(CountDownTimer timer) {
        this.timer = timer;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public RecipeSteps.STEP getStep() {
        return step;
    }

    public void setStep(RecipeSteps.STEP step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "Step{" +
                "message='" + message + '\'' +
                ", time=" + time +
                ", step=" + step +
                '}';
    }
}
