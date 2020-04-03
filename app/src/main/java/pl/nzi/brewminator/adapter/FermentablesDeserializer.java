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
import java.util.List;

import pl.nzi.brewminator.model.FERMENTABLE;
import pl.nzi.brewminator.model.FERMENTABLES;

public class FermentablesDeserializer implements JsonDeserializer<FERMENTABLES> {
    private final static String TAG = "FermentablesDeserializer";

    @Override
    public FERMENTABLES deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JSONObject jsonObject;
        try {
            jsonObject = new Gson().fromJson(json.getAsJsonObject(), JSONObject.class);
        }catch (NullPointerException e){
            return null;
        }

        FERMENTABLES fermentables = new FERMENTABLES();
        ArrayList<FERMENTABLE> fermentableArrayList = new ArrayList<>();
        Log.d("TAG", "deserialize: " + jsonObject.get("FERMENTABLE"));
        Gson gson = new Gson();
        if (jsonObject.get("FERMENTABLE").getClass() == ArrayList.class) {

            ArrayList arrayList = (ArrayList) jsonObject.get("FERMENTABLE");
            Log.d(TAG, "deserialize: " + arrayList.get(0).getClass());
            ArrayList<LinkedTreeMap<String, String>> objects = (ArrayList<LinkedTreeMap<String, String>>) jsonObject.get("FERMENTABLE");
            for (LinkedTreeMap<String, String> object : objects) {

                fermentableArrayList.add(parseMash(gson.toJsonTree(object).getAsJsonObject()));

            }


        } else {
            fermentableArrayList.add(parseMash(gson.toJsonTree(jsonObject.get("FERMENTABLE")).getAsJsonObject()));
        }
        fermentables.setFERMENTABLE(fermentableArrayList);
        return fermentables;

    }

    private FERMENTABLE parseMash(JsonObject step) {
        FERMENTABLE fermentable = new FERMENTABLE();
        try {
            fermentable.setADDAFTERBOIL(step.get("ADD_AFTER_BOIL").getAsString());
        } catch (NullPointerException e) {
            fermentable.setADDAFTERBOIL(null);
        }
        try {
            fermentable.setAMOUNT(step.get("AMOUNT").getAsString());
        } catch (NullPointerException e) {
            fermentable.setAMOUNT(null);
        }
        try {
            fermentable.setCOLOR(step.get("COLOR").getAsString());
        } catch (NullPointerException e) {
            fermentable.setCOLOR(null);
        }
        try {
            fermentable.setDIASTATICPOWER(step.get("DIASTATIC_POWER").getAsString());
        } catch (NullPointerException e) {
            fermentable.setDIASTATICPOWER(null);
        }
        try {
            fermentable.setNAME(step.get("NAME").getAsString());
        } catch (NullPointerException e) {
            fermentable.setNAME(null);
        }
        try {
            fermentable.setORIGIN(step.get("ORIGIN").getAsString());
        } catch (NullPointerException e) {
            fermentable.setORIGIN(null);
        }
        try {
            fermentable.setTYPE(step.get("TYPE").getAsString());
        } catch (NullPointerException e) {
            fermentable.setTYPE(null);
        }
        try {
            fermentable.setVERSION(step.get("VERSION").getAsString());
        } catch (NullPointerException e) {
            fermentable.setVERSION(null);
        }
        try {
            fermentable.setYIELD(step.get("YIELD").getAsString());
        } catch (NullPointerException e) {
            fermentable.setYIELD(null);
        }

        return fermentable;
    }

}
