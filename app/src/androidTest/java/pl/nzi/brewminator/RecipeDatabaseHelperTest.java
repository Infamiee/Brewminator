package pl.nzi.brewminator;

import android.content.Context;
import android.database.Cursor;

import androidx.test.core.app.ApplicationProvider;

import com.google.gson.Gson;


import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RecipeDatabaseHelperTest {


    private RecipeDatabaseHelper db;


    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = new RecipeDatabaseHelper(context);
        db.onUpgrade(db.getWritableDatabase(),1,2);
    }

    @After
    public void closeDb() throws IOException {
        db.close();
        db.onUpgrade(db.getWritableDatabase(),1,2);
    }


    @Test
    public void addData() {
        //Test if adding
        assertTrue(db.addData(1, "First Recipe","Style"));
        //Test if not adding empty name
        assertFalse(db.addData(2,"","Style"));
        //Test if not adding repeating id
        assertFalse(db.addData(1,"Second","Style"));
    }

    @Test
    public void getAllData() {
        String name1 = "First Recipe";
        String name2 = "Second Recipe";
        String name3 = "Third Recipe";

        db.addData(1, name1,"Style");
        db.addData(2, name2,"Style");
        db.addData(3, name3,"Style");
        Cursor cursor = db.getAllData();

        List<String> names = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            names.add(cursor.getString(cursor.getColumnIndex("RecipeName")));
        }
        //Test if size is good
        assertEquals(names.size(),3);

        //Test if names are good
        assertEquals(names.get(0),name1);
        assertEquals(names.get(1),name2);
        assertEquals(names.get(2),name3);

    }

    @Test
    public void getRecipesByKeyword() {
        String name1 = "First Recipe";
        String name2 = "First Recipe 1";
        String name3 = "Third Recipe";

        db.addData(1, name1,"Style");
        db.addData(2, name2,"Style");
        db.addData(3, name3,"Style");

        String keyword = "fir";

        Cursor cursor = db.getRecipesByKeyword(keyword);
        List<String> names = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            names.add(cursor.getString(cursor.getColumnIndex("RecipeName")));
        }

        //Test if names matches
        assertEquals(names.size(),2);
        assertEquals(names.get(0),name1);
        assertEquals(names.get(1),name2);

        keyword = "THIR";

        cursor = db.getRecipesByKeyword(keyword);
        names = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            names.add(cursor.getString(cursor.getColumnIndex("RecipeName")));
        }

        //Test if names matches
        assertEquals(names.size(),1);
        assertEquals(names.get(0),name3);

        keyword = "dfgsgsdagafds";

        cursor = db.getRecipesByKeyword(keyword);
        names = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            names.add(cursor.getString(cursor.getColumnIndex("RecipeName")));
        }

        //Test if none returned if not keyword doesn't match
        assertEquals(names.size(),0);

    }

    @Test
    public void saveAllFromJson() throws ParseException {
        String json = "{\n" +
                "    \"1\": {\n" +
                "        \"name\": \"Blonde Stout\",\n" +
                "        \"style\": \"Experimental Beer\"\n" +
                "    },\n" +
                "    \"2\": {\n" +
                "        \"name\": \"Brut IPA\",\n" +
                "        \"style\": \"American IPA\"\n" +
                "    },\n" +
                "    \"3\": {\n" +
                "        \"name\": \"Citra Pale Ale Recipe\",\n" +
                "        \"style\": \"American Pale Ale\"\n" +
                "    },\n" +
                "    \"4\": {\n" +
                "        \"name\": \"Citra SMaSH\",\n" +
                "        \"style\": \"American Pale Ale\"\n" +
                "    },\n" +
                "    \"5\": {\n" +
                "        \"name\": \"Classic German Pilsner\",\n" +
                "        \"style\": \"German Pilsner (Pils)\"\n" +
                "    },\n" +
                "    \"6\": {\n" +
                "        \"name\": \"Corona Clone?\",\n" +
                "        \"style\": \"International Pale Lager\"\n" +
                "    },\n" +
                "    \"7\": {\n" +
                "        \"name\": \"Guinness Clone\",\n" +
                "        \"style\": \"Dry Stout\"\n" +
                "    },\n" +
                "    \"8\": {\n" +
                "        \"name\": \"Hazy Juicy IPA\",\n" +
                "        \"style\": \"American IPA\"\n" +
                "    },\n" +
                "    \"9\": {\n" +
                "        \"name\": \"Hopfenweisse\",\n" +
                "        \"style\": \"Weizen/Weissbier\"\n" +
                "    },\n" +
                "    \"10\": {\n" +
                "        \"name\": \"Mexican Cerveza\",\n" +
                "        \"style\": \"Light American Lager\"\n" +
                "    },\n" +
                "    \"11\": {\n" +
                "        \"name\": \"NEIPA\",\n" +
                "        \"style\": \"American IPA\"\n" +
                "    },\n" +
                "    \"12\": {\n" +
                "        \"name\": \"Nutella Stout\",\n" +
                "        \"style\": \"Sweet Stout\"\n" +
                "    },\n" +
                "    \"13\": {\n" +
                "        \"name\": \"Old Rasputin Clone\",\n" +
                "        \"style\": \"Russian Imperial Stout\"\n" +
                "    },\n" +
                "    \"14\": {\n" +
                "        \"name\": \"Peach Wheat\",\n" +
                "        \"style\": \"Fruit Beer\"\n" +
                "    },\n" +
                "    \"15\": {\n" +
                "        \"name\": \"Pineapple IPA\",\n" +
                "        \"style\": \"American IPA\"\n" +
                "    },\n" +
                "    \"16\": {\n" +
                "        \"name\": \"Session IPA\",\n" +
                "        \"style\": \"American IPA\"\n" +
                "    },\n" +
                "    \"17\": {\n" +
                "        \"name\": \"Strawberry Vanilla Milkshake IPA\",\n" +
                "        \"style\": \"American IPA\"\n" +
                "    },\n" +
                "    \"18\": {\n" +
                "        \"name\": \"TH Haze Clone\",\n" +
                "        \"style\": \"Imperial IPA\"\n" +
                "    },\n" +
                "    \"19\": {\n" +
                "        \"name\": \"Traditional German Gose\",\n" +
                "        \"style\": \"Gueuze\"\n" +
                "    },\n" +
                "    \"20\": {\n" +
                "        \"name\": \"west coast ipa\",\n" +
                "        \"style\": \"American IPA\"\n" +
                "    },\n" +
                "    \"21\": {\n" +
                "        \"name\": \"White Stout\",\n" +
                "        \"style\": \"Sweet Stout\"\n" +
                "    }\n" +
                "}";
        JSONObject jsonObject;
        Gson gson = new Gson();
        jsonObject = gson.fromJson(json, JSONObject.class);
        db.saveAllFromJson(jsonObject);

        Cursor cursor = db.getAllData();

        List<String> names = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            names.add(cursor.getString(cursor.getColumnIndex("RecipeName")));
        }

        assertEquals(names.size(),21);
        assertEquals(names.get(20),"White Stout");

    }
}