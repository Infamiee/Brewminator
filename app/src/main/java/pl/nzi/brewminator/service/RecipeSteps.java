package pl.nzi.brewminator.service;

import com.digidemic.unitof.S;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.nzi.brewminator.model.Recipe;

public class RecipeSteps {
    enum STEPS{
        PREP,MASH,BOIL,FERMENT
    }
    Recipe recipe;

    public RecipeSteps(Recipe recipe) {
        this.recipe = recipe;
    }


}
