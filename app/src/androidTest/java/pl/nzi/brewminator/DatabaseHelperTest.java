package pl.nzi.brewminator;

import android.content.Context;
import android.database.Cursor;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class DatabaseHelperTest {


    private DatabaseHelper db;


    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = new DatabaseHelper(context);
        db.onUpgrade(db.getWritableDatabase(),1,2);
    }

    @After
    public void closeDb() throws IOException {

        db.close();
    }


    @Test
    public void addData() {
        //Test if adding
        assertTrue(db.addData(1, "First Recipe"));
        //Test if not adding empty name
        assertFalse(db.addData(2,""));
        //Test if not adding repeating id
        assertFalse(db.addData(1,"Second"));
    }

    @Test
    public void getAllData() {
        String name1 = "First Recipe";
        String name2 = "Second Recipe";
        String name3 = "Third Recipe";

        db.addData(1, name1);
        db.addData(2, name2);
        db.addData(3, name3);
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
    public void get_Recipes_by_keyword() {
        String name1 = "First Recipe";
        String name2 = "First Recipe 1";
        String name3 = "Third Recipe";

        db.addData(1, name1);
        db.addData(2, name2);
        db.addData(3, name3);

        String keyword = "fir";

        Cursor cursor = db.get_Recipes_by_keyword(keyword);
        List<String> names = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            names.add(cursor.getString(cursor.getColumnIndex("RecipeName")));
        }

        //Test if names matches
        assertEquals(names.size(),2);
        assertEquals(names.get(0),name1);
        assertEquals(names.get(1),name2);

        keyword = "THIR";

        cursor = db.get_Recipes_by_keyword(keyword);
        names = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            names.add(cursor.getString(cursor.getColumnIndex("RecipeName")));
        }

        //Test if names matches
        assertEquals(names.size(),1);
        assertEquals(names.get(0),name3);

        keyword = "dfgsgsdagafds";

        cursor = db.get_Recipes_by_keyword(keyword);
        names = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            names.add(cursor.getString(cursor.getColumnIndex("RecipeName")));
        }

        //Test if none returned if not keyword doesn't match
        assertEquals(names.size(),0);




    }
}