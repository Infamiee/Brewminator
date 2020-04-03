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
import pl.nzi.brewminator.model.MASHSTEP;
import pl.nzi.brewminator.model.MASHSTEPS;

public class HopsAdapter implements JsonDeserializer<HOPS> {

    private static final String TAG = "HopsAdapter";

    @Override
    public HOPS deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JSONObject jsonObject;
        try {
            jsonObject = new Gson().fromJson(json.getAsJsonObject(), JSONObject.class);
        }catch (NullPointerException e){
            return null;
        }


        HOPS hops = new HOPS();
        List<HOP> list = new ArrayList<>();
        Log.d("TAG", "deserialize: " + jsonObject.get("HOP"));
        Gson gson = new Gson();

        if (jsonObject.get("HOP").getClass() == ArrayList.class) {

            ArrayList arrayList = (ArrayList) jsonObject.get("HOP");
            Log.d(TAG, "deserialize: " + arrayList.get(0).getClass());
            ArrayList<LinkedTreeMap<String, String>> objects = (ArrayList<LinkedTreeMap<String, String>>) jsonObject.get("HOP");
            for (LinkedTreeMap<String, String> object : objects) {

                list.add(parseMash(gson.toJsonTree(object).getAsJsonObject()));

            }


        }else {
            list.add(parseMash(gson.toJsonTree(jsonObject.get("HOP")).getAsJsonObject()));
        }
        hops.setHOP(list);
        return hops;

    }

    private HOP parseMash(JsonObject step) {

        HOP hop = new HOP();
        try {
            hop.setALPHA(step.get("ALPHA").getAsString());
        }catch (NullPointerException e){
            hop.setALPHA(null);
        }
        try {
            hop.setAMOUNT(step.get("AMOUNT").getAsString());
        }catch (NullPointerException e){
            hop.setAMOUNT(null);
        }
        try {
            hop.setFORM(step.get("FORM").getAsString());
        }catch (NullPointerException e){
            hop.setFORM(null);
        }
        try {
            hop.setNAME(step.get("NAME").getAsString());
        }catch (NullPointerException e){
            hop.setNAME(null);
        }
        try {
            hop.setTIME(step.get("TIME").getAsString());
        }catch (NullPointerException e){
            hop.setTIME(null);
        }
        try {
            hop.setUSE(step.get("USE").getAsString());
        }catch (NullPointerException e){
            hop.setUSE(null);
        }
        try {
            hop.setUSERHOPUSE(step.get("USER_HOP_USE").getAsString());
        }catch (NullPointerException e){
            hop.setUSERHOPUSE(null);
        }
        try {
            hop.setHOPTEMP(step.get("HOP_TEMP").getAsString());
        }catch (NullPointerException e){
            hop.setHOPTEMP(null);
        }
        try {
            hop.setTEMPERATURE(step.get("TEMPERATURE").getAsString());
        }catch (NullPointerException e){
            hop.setTEMPERATURE(null);
        }
        try {
            hop.setUTILIZATION(step.get("UTILIZATION").getAsString());
        }catch (NullPointerException e){
            hop.setUTILIZATION(null);
        }
        try {
            hop.setVERSION(step.get("VERSION").getAsString());
        }catch (NullPointerException e){
            hop.setVERSION(null);
        }



        Log.d(TAG, "parseMash: " + hop.toString());
        return hop;
    }
}

