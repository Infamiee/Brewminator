package pl.nzi.brewminator;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.digidemic.unitof.S;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.stream.Stream;

import pl.nzi.brewminator.adapter.RecipeAdapter;
import pl.nzi.brewminator.model.RecipeSearch;

public class SavedRecipesDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "SavedRecipes";
    private static final String ID = "RecipeId";
    private static final String RECIPE_NAME = "RecipeName";
    private static final String STYLE = "Style";

    public SavedRecipesDatabaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME,null,1);
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


    public List<RecipeSearch> getAll(){
        String sql = String.format("SELECT * FROM %s",TABLE_NAME);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql,null);

        List<RecipeSearch> recipes = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String recipeName = cursor.getString(cursor.getColumnIndex(RECIPE_NAME));
            int id = cursor.getInt(cursor.getColumnIndex(ID));
            String style = cursor.getString(cursor.getColumnIndex(STYLE));
            recipes.add(new RecipeSearch(id,recipeName,style));
        }

        return recipes;
    }

    public boolean addData(int id, String name,String style){
        if (name.isEmpty()){
            return false;
        }
        Log.d(TAG, "addData: "+"xdddd");
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


    public void remove(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,ID+"="+id,null);
    }

    public boolean isSaved(int id){
        List<RecipeSearch> recipes = getAll();

        for(RecipeSearch recipe:recipes){
            if (recipe.getId() == id){
                return true;
            }
        }
        return false;
    }

}
