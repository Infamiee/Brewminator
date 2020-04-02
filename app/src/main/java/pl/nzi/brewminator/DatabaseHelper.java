package pl.nzi.brewminator;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


import com.google.gson.internal.LinkedTreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;
import java.util.Set;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "Recipes";
    private static final String ID = "RecipeId";
    private static final String RECIPE_NAME = "RecipeName";
    private static final String STYLE = "Style";

    public DatabaseHelper(@Nullable Context context ) {
        super(context, TABLE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY NOT NULL," +
                "                                   %s TEXT NOT NULL," +
                "                                   %s TEXT NOT NULL )",TABLE_NAME,ID,RECIPE_NAME,STYLE);
                db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = String.format("DROP TABLE IF EXISTS %s",TABLE_NAME);
        db.execSQL(sql);
        onCreate(db);
    }

    public boolean addData(int id, String name,String style){
        if (name.isEmpty()){
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID,id);
        values.put(RECIPE_NAME,name);
        values.put(STYLE,style);
        long result = db.insert(TABLE_NAME,null,values);

        if (result == -1 ){
            return false;
        }
        return true;
    }


    public Cursor getAllData(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        return cursor;
    }

    public Cursor getRecipesByKeyword(String keyword){
        String word = keyword.trim().toLowerCase();
        if (word.isEmpty()){
            return getAllData();
        }
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE RecipeName LIKE '%"+word+"%'";
        return db.rawQuery(sql,null);

    }

    public void saveAllFromJson(JSONObject jsonObject){
        Set<String> keys = jsonObject.keySet();

        onUpgrade(getWritableDatabase(),1,2);

        for (String key:keys){
            try {
                LinkedTreeMap<String,String> value = (LinkedTreeMap<String, String>) jsonObject.get(key);
                String name = (String) value.get("name");
                String style = (String) value.get("style");

                addData(Integer.parseInt(key),name,style);
                Log.d(TAG, "saveAllFromJson: "+key+" "+name+" "+style);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG,"exception " + e.getMessage());
            }

        }

    }

    public int getIdByName(String name){
        SQLiteDatabase db = getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT "+ID+" FROM " +TABLE_NAME+ " WHERE " +RECIPE_NAME+ "= '"+name+"'",null);
        return  cursor.getInt(cursor.getColumnIndex(ID));
    }
}
