package pl.nzi.brewminator.service;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import pl.nzi.brewminator.model.FERMENTABLE;
import pl.nzi.brewminator.model.HOP;
import pl.nzi.brewminator.model.MASHSTEP;
import pl.nzi.brewminator.model.MISC;
import pl.nzi.brewminator.model.Recipe;
import pl.nzi.brewminator.model.Step;
import pl.nzi.brewminator.model.YEAST;

public class RecipeSteps {


    private static final String TAG = "RecipeSteps";

    public enum STEP {
        YEAST, PREP, MASH, BOIL, FERMENT
    }

    private Recipe recipe;
    private List<Step> steps;
    private int currentId;
    private boolean isStarter;

    public RecipeSteps(Recipe recipe) {
        this.recipe = recipe;
        steps = new ArrayList<>();
        currentId = 0;
        isStarter = false;
        if (recipe.getYEASTSTARTER().equals("true")) {
            isStarter = true;
            setupPrepareYeast();
        }
        setupSteps();



    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    private void setupSteps() {
        setupFermentables();
        setupMashsteps();
        setupBoil();
        prepareYeast();
        setupAfterBoil();
    }

    private void setupAfterBoil() {
        HashMap<Integer, List<String>> hops = getHops("Dry Hop");
        Log.d(TAG, "Hopssdasfsafgasfas: " + hops.values().toString());
        HashMap<Integer, List<String>> miscs = getMiscs("Secondary");
        hops= mergeMaps(hops,miscs);
        HashMap<Integer, List<String>> ferment = new HashMap<>();
        for (Map.Entry<Integer, List<String>> integerStringEntry : hops.entrySet()) {

            int time = (int) ((Map.Entry) integerStringEntry).getKey();

            List<String> addition = (List<String>) ((Map.Entry) integerStringEntry).getValue();
            Log.d(TAG, "setupAfterBoil: " + addition);
            if (ferment.containsKey(time)) {
                Objects.requireNonNull(ferment.get(time)).addAll(addition);
            } else {

                ferment.put(time, addition);
            }
        }


        List<Integer> times = ferment.keySet().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());


        Log.d(TAG, "setupBoil: " + times.toString());
        for (int time : times) {
            String message = "Add";
            currentId++;
            Step step = new Step(currentId, message, ferment.get(time), time, STEP.FERMENT);
            steps.add(step);
        }

    }


    private void prepareYeast() {
        if (!isStarter) {
            setupPrepareYeast();
        }
        currentId++;
        steps.add(new Step(currentId, "Add Yeast", null, 0, STEP.YEAST));
    }

    private HashMap<Integer, List<String>> mergeMaps(HashMap<Integer, List<String>> map1, HashMap<Integer, List<String>> map2) {
        HashMap<Integer, List<String>> merged = new HashMap<>();
        for (Map.Entry<Integer, List<String>> entry : map1.entrySet()) {
            int key = entry.getKey();
            List<String> strings = entry.getValue();
            if (merged.containsKey(key)) {
                merged.get(key).addAll(strings);
            }
            else {
                merged.put(key,strings);
            }
        }
        for (Map.Entry<Integer, List<String>> entry : map2.entrySet()) {
            int key = entry.getKey();
            List<String> strings = entry.getValue();
            if (merged.containsKey(key)) {
                merged.get(key).addAll(strings);
            }else {
                merged.put(key,strings);
            }
        }

        return merged;
    }

    private void setupFermentables() {
        List<FERMENTABLE> fermentables = recipe.getFERMENTABLES().getFERMENTABLE();
        List<String> additions = new ArrayList<>();
        String message = "Prepare starter:";
        for (FERMENTABLE fermentable : fermentables) {
            double amount = Double.parseDouble(fermentable.getAMOUNT());
            String addition = fermentable.getNAME() + ": " + String.format("%.2f", amount) + " kg";
            additions.add(addition);
        }
        HashMap<Integer, List<String>> mashMisc = getMiscs("Mash");
        Iterator<Map.Entry<Integer, List<String>>> iterator = mashMisc.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, List<String>> entry = iterator.next();
            List<String> miscs = entry.getValue();
            additions.addAll(miscs);
        }
        currentId++;
        steps.add(new Step(currentId, message, additions, 0, STEP.MASH));
    }

    private void setupPrepareYeast() {
        List<String> addition = new ArrayList<>();
        YEAST yeast = recipe.getYEASTS().getYEAST();
        String message = "Prepare";
        addition.add(yeast.getNAME());
        addition.add(yeast.getLABORATORY());
        addition.add(yeast.getTYPE());
        currentId++;
        Step step = new Step(currentId, message, addition, 0, STEP.YEAST);
        steps.add(step);
    }

    private void setupMashsteps() {
        try {
            List<MASHSTEP> mashsteps = recipe.getMASH().getMASHSTEPS().getMASHSTEP();
            String message = "Mash:";

            for (MASHSTEP mashstep : mashsteps) {
                List<String> additions = new ArrayList<>();
                try {
                    double amount = Double.parseDouble(mashstep.getINFUSEAMOUNT());
                    additions.add("Water: " + String.format("%.2f l", amount));

                } catch (Exception ignored) {
                }
                additions.add(mashstep.getNAME());
                additions.add(mashstep.getTYPE());
                try {
                    double temp = Double.parseDouble(mashstep.getSTEPTEMP());
                    String t = "Temp: " + String.format("%.2f", temp) + (char) 0x2103;
                    additions.add(t);
                } catch (Exception ignored) {
                }
                ;
                int time = 0;
                try {
                    time = Integer.parseInt(mashstep.getSTEPTIME());
                } catch (Exception ignored) {
                }
                currentId++;
                Step step = new Step(currentId, message, additions, time, STEP.MASH);
                steps.add(step);
            }
        }catch (Exception e){
            currentId++;
            Step step = new Step(currentId, "Mash", Collections.singletonList("Temp: 65" +(char)0x2103 ), 60, STEP.MASH);
            steps.add(step);
        }
        try {
            double boilSize = Double.parseDouble(recipe.getBOILSIZE());
            currentId++;
            steps.add(new Step(currentId,"Top it with water to",Collections.singletonList(String.format("%.2f",boilSize)+" l"),0,STEP.MASH));
        } catch (Exception ignored){}



    }

    private HashMap<Integer, List<String>> getMiscs(String type) {
        HashMap<Integer, List<String>> miscMap = new HashMap<>();
        try {
            List<MISC> miscs = recipe.getMISCS().getMISC();
            for (MISC misc : miscs) {
                if (misc.getUSE().equals(type)) {
                    int time = 0;
                    try {
                        time = Integer.parseInt(misc.getTIME());
                    } catch (Exception ignored) {
                    }

                    double amount = Double.parseDouble(misc.getAMOUNT()) * 1000;
                    String addition = misc.getNAME() + ": " + String.format("%.2f", amount);
                    if (misc.getAMOUNTISWEIGHT().equals("TRUE")) {
                        addition += " g";
                    } else {
                        addition += " ml";
                    }
                    if (miscMap.containsKey(time)) {
                        miscMap.get(time).add(addition);
                    } else {
                        List<String> list = new ArrayList<>();
                        list.add(addition);
                        miscMap.put(time, list);
                    }

                }
            }
            return miscMap;
        } catch (Exception e) {
            return new HashMap<>();
        }

    }

    private HashMap<Integer, List<String>> getHops(String... types) {
        HashMap<Integer, List<String>> hopMap = new HashMap<>();
        try {
            List<HOP> hops = recipe.getHOPS().getHOP();
            for (HOP hop : hops) {
                if (Arrays.asList(types).contains(hop.getUSE())) {
                    Log.d(TAG, "getHops: " + hop.getNAME() + "   " + hop.getUSE());
                    int time = 0;
                    try {
                        time = Integer.parseInt(hop.getTIME());
                    } catch (Exception ignored) {
                    }
                    double amount = Double.parseDouble(hop.getAMOUNT()) * 1000;
                    String addition = hop.getNAME() + ": " + String.format("%.2f", amount) + " g";
                    if (hopMap.containsKey(time)) {
                        hopMap.get(time).add(addition);
                    } else {
                        List<String> list = new ArrayList<>();
                        list.add(addition);
                        hopMap.put(time, list);
                    }

                }
            }
            return hopMap;

        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    private void setupBoil() {
        HashMap<Integer, List<String>> hops = getHops("Boil", "Whirlpool", "Aroma");
        HashMap<Integer, List<String>> miscs = getMiscs("Boil");
        hops=mergeMaps(hops,miscs);

        HashMap<Integer, List<String>> boil = new HashMap<>();
        int boilTime = Integer.parseInt(recipe.getBOILTIME());
        boil.put(boilTime, new ArrayList<>());
        for (Map.Entry<Integer, List<String>> entry : hops.entrySet()) {

            int time = entry.getKey();

            List<String> addition = entry.getValue();
            if (boil.containsKey(time)) {
                Objects.requireNonNull(boil.get(time)).addAll(addition);
            } else {
                boil.put(time, addition);
            }
        }


        List<Integer> times = boil.keySet().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());


        Log.d(TAG, "setupBoil: " + times.toString());
        for (int i = 0; i < times.size(); i++) {
            String message = "Add and Boil";
            if (times.get(i) == 0) {
                message = "Add";
            }
            currentId++;
            int t;
            if (i != times.size() - 1) {
                t = times.get(i) - times.get(i + 1);
            } else {
                t = times.get(i);
            }
            Log.d(TAG, "setupBoil: " + t);

            Step step = new Step(currentId, message, boil.get(times.get(i)), t, STEP.BOIL);
            steps.add(step);
        }

        currentId++;
        steps.add(new Step(currentId, "Filter and chill", Arrays.asList("Below 32" + (char) 0x2103), 0, STEP.BOIL));
    }


}
