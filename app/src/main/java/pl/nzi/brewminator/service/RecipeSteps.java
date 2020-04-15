package pl.nzi.brewminator.service;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import pl.nzi.brewminator.model.MASHSTEP;
import pl.nzi.brewminator.model.MISC;
import pl.nzi.brewminator.model.Recipe;

public class RecipeSteps {


    enum STEPS{
        PREP,MASH,BOIL,FERMENT
    }
    private Recipe recipe;
    private int boilTime;
    private int mashTime;
    private List<MISC> fermentMisc;
    private List<MISC> boilMisc;
    private List<MISC> mashMisc;
    public RecipeSteps(Recipe recipe) {
        this.recipe = recipe;
        setupTimes();
        setMiscs();


    }

    private void setupTimes(){
        this.boilTime = Integer.parseInt(recipe.getBOILTIME());
        List<MASHSTEP> mashsteps = recipe.getMASH().getMASHSTEPS().getMASHSTEP();
        int mashTime = 0;
        if (mashsteps != null){
            for (MASHSTEP mashstep : mashsteps){
                if(mashstep.getSTEPTIME() != null){
                    mashTime += Integer.parseInt(mashstep.getSTEPTIME());
                }
            }
        }

        this.mashTime = mashTime;
    }

    private void setMiscs(){
        fermentMisc = new ArrayList<>();
        mashMisc = new ArrayList<>();
        boilMisc = new ArrayList<>();
        for (MISC misc:recipe.getMISCS().getMISC()){
            if(misc.getUSE().equals("Mash")){
                mashMisc.add(misc);
            }if(misc.getUSE().equals("Boil")){
                boilMisc.add(misc);
            }
            if(misc.getUSE().equals("Secondary")){
                fermentMisc.add(misc);
            }
        }
    }










}
