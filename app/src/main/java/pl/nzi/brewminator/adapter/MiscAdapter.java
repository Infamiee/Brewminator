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

import pl.nzi.brewminator.model.HOP;
import pl.nzi.brewminator.model.HOPS;
import pl.nzi.brewminator.model.MISC;
import pl.nzi.brewminator.model.MISCS;

public class MiscAdapter implements JsonDeserializer<MISCS> {
    private static final String TAG = "MiscAdapter";

    @Override
    public MISCS deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JSONObject jsonObject;
        try {
            jsonObject = new Gson().fromJson(json.getAsJsonObject(), JSONObject.class);
        }catch (NullPointerException e){
            return null;
        }


        MISCS miscs = new MISCS();
        List<MISC> list = new ArrayList<>();
        Log.d("TAG", "deserialize: " + jsonObject.get("MISC"));
        Gson gson = new Gson();

        if (jsonObject.get("MISC").getClass() == ArrayList.class) {

            ArrayList arrayList = (ArrayList) jsonObject.get("MISC");
            Log.d(TAG, "deserialize: " + arrayList.get(0).getClass());
            ArrayList<LinkedTreeMap<String, String>> objects = (ArrayList<LinkedTreeMap<String, String>>) jsonObject.get("MISC");
            for (LinkedTreeMap<String, String> object : objects) {

                list.add(parseMash(gson.toJsonTree(object).getAsJsonObject()));

            }


        }else {
            list.add(parseMash(gson.toJsonTree(jsonObject.get("MISC")).getAsJsonObject()));
        }
        miscs.setMISC(list);
        return miscs;

    }

    private MISC parseMash(JsonObject step) {

        MISC misc = new MISC();
        try {
            misc.setAMOUNT(step.get("AMOUNT").getAsString());
        }catch (NullPointerException e){
            misc.setAMOUNT(null);
        }
        try {
            misc.setAMOUNTISWEIGHT(step.get("AMOUNT_IS_WEIGHT").getAsString());
        }catch (NullPointerException e){
            misc.setAMOUNTISWEIGHT(null);
        }
        try {
            misc.setNAME(step.get("NAME").getAsString());
        }catch (NullPointerException e){
            misc.setNAME(null);
        }
        try {
            misc.setTIME(step.get("TIME").getAsString());
        }catch (NullPointerException e){
            misc.setTIME(null);
        }
        try {
            misc.setTYPE(step.get("TYPE").getAsString());
        }catch (NullPointerException e){
            misc.setTYPE(null);
        }
        try {
            misc.setUSE(step.get("USE").getAsString());
        }catch (NullPointerException e){
            misc.setUSE(null);
        }
        try {
            misc.setVERSION(step.get("VERSION").getAsString());
        }catch (NullPointerException e){
            misc.setVERSION(null);
        }



        Log.d(TAG, "parseMash: " + misc.toString());
        return misc;
    }
}

