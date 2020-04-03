package pl.nzi.brewminator.adapter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.internal.LinkedTreeMap;


import org.json.simple.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Map;

import pl.nzi.brewminator.model.MASH;
import pl.nzi.brewminator.model.MASHSTEP;
import pl.nzi.brewminator.model.MASHSTEPS;

public class MashStepsDeserialzier implements JsonDeserializer<MASHSTEPS> {
    private final static String TAG = "MashStepsDeserialzier";

    @Override
    public MASHSTEPS deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JSONObject jsonObject;
        try {
            jsonObject = new Gson().fromJson(json.getAsJsonObject(), JSONObject.class);
        }catch (NullPointerException e){
            return null;
        }


        MASHSTEPS mashsteps = new MASHSTEPS();
        List<MASHSTEP> mashstepList = new ArrayList<>();
        Log.d("TAG", "deserialize: " + jsonObject.get("MASH_STEP"));
        Gson gson = new Gson();

        if (jsonObject.get("MASH_STEP").getClass() == ArrayList.class) {

            ArrayList arrayList = (ArrayList) jsonObject.get("MASH_STEP");
            Log.d(TAG, "deserialize: " + arrayList.get(0).getClass());
            ArrayList<LinkedTreeMap<String, String>> objects = (ArrayList<LinkedTreeMap<String, String>>) jsonObject.get("MASH_STEP");
            for (LinkedTreeMap<String, String> object : objects) {

                mashstepList.add(parseMash(gson.toJsonTree(object).getAsJsonObject()));

            }


        }else {
            mashstepList.add(parseMash(gson.toJsonTree(jsonObject.get("MASH_STEP")).getAsJsonObject()));
        }
        mashsteps.setMASHSTEP(mashstepList);
        return mashsteps;

    }

    private MASHSTEP parseMash(JsonObject step) {

        MASHSTEP mash = new MASHSTEP();
        try {
            mash.setINFUSEAMOUNT(step.get("INFUSE_AMOUNT").getAsString());
        }catch (NullPointerException e){
            mash.setINFUSEAMOUNT(null);
        }
        try {
            mash.setNAME(step.get("NAME").getAsString());
        }catch (NullPointerException e){
            mash.setNAME(null);
        }
        try {
            mash.setSTEPTEMP(step.get("STEP_TEMP").getAsString());
        }catch (NullPointerException e){
            mash.setSTEPTEMP(null);
        }
        try {
            mash.setSTEPTIME(step.get("STEP_TIME").getAsString());
        }catch (NullPointerException e){
            mash.setSTEPTIME(null);
        }
        try {
            mash.setTYPE(step.get("TYPE").getAsString());
        }catch (NullPointerException e){
            mash.setTYPE(null);
        }


        Log.d(TAG, "parseMash: " + mash.toString());
        return mash;
    }
}





