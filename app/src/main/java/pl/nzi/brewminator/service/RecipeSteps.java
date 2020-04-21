package pl.nzi.brewminator.service;

import android.os.AsyncTask;
import android.widget.LinearLayout;

import com.google.android.material.badge.BadgeDrawable;

import java.time.chrono.MinguoEra;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import pl.nzi.brewminator.model.FERMENTABLE;
import pl.nzi.brewminator.model.HOP;
import pl.nzi.brewminator.model.MASHSTEP;
import pl.nzi.brewminator.model.MISC;
import pl.nzi.brewminator.model.Recipe;
import pl.nzi.brewminator.model.Step;

public class RecipeSteps {


    public enum STEP {
        YEAST, PREP, MASH, BOIL, FERMENT
    }

    private Recipe recipe;
    private List<Step> steps;
    private int currentId;
    public RecipeSteps(Recipe recipe) {
        this.recipe = recipe;
        steps = new ArrayList<>();
        currentId=0;
        setupSteps();



    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    private void setupSteps(){
        setupFermentables();
        setupMashsteps();
    }


    private void setupFermentables(){
        List<FERMENTABLE> fermentables = recipe.getFERMENTABLES().getFERMENTABLE();
        List<String> additions = new ArrayList<>();
        String message = "Prepare:";
        for (FERMENTABLE fermentable:fermentables){
            double amount = Double.parseDouble(fermentable.getAMOUNT());
            String addition = fermentable.getNAME() + ": " + String.format("%.2f",amount)+" kg";
            additions.add(addition);
        }
        currentId++;
        steps.add(new Step(currentId,message,additions,0,STEP.MASH));
    }

    private void setupMashsteps() {
        List<MASHSTEP> mashsteps = recipe.getMASH().getMASHSTEPS().getMASHSTEP();
        String message = "Mash:";

        for (MASHSTEP mashstep:mashsteps){
            List<String> additions = new ArrayList<>();
            try {
                double amount = Double.parseDouble(mashstep.getINFUSEAMOUNT());
                additions.add("Water: " + String.format("%.2f l",amount) );

            }catch (Exception ignored){
            }
            additions.add( mashstep.getNAME());
            additions.add(mashstep.getTYPE());
            try {
                double temp = Double.parseDouble(mashstep.getSTEPTEMP());
                String t = "Temp: "+String.format("%.2f",temp)+(char)0x2103;
                additions.add(t);
            }catch (Exception ignored){};
            int time= 0;
            try {
                time = Integer.parseInt(mashstep.getSTEPTIME());
            }catch (Exception ignored){
            }
            currentId++;
            Step step = new Step(currentId,message,additions,time, STEP.MASH);
            steps.add(step);
        }
    }







}
