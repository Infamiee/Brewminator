package pl.nzi.brewminator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.nzi.Brewminator;
import pl.nzi.brewminator.adapter.RecipeAdapter;
import pl.nzi.brewminator.model.Recipe;
import pl.nzi.brewminator.model.RecipeSearch;


public class SearchActivity extends AppCompatActivity implements RecipeAdapter.OnClickRecipeListener {
    SearchView searchView;
    Toolbar toolbar;
    RecipeAdapter recipeAdapter;
    DatabaseHelper db;
    List<RecipeSearch> fullList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = findViewById(R.id.search_view);
        searchView.setIconified(false);

        db = new DatabaseHelper(Brewminator.getAppContext());

        setUpRecyclerView();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recipeAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createFullList(){
        List<RecipeSearch> recipeList = new ArrayList<>();
        Cursor cursor = db.getAllData();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("RecipeName"));
            int id = cursor.getInt(cursor.getColumnIndex("RecipeId"));
            String style = cursor.getString(cursor.getColumnIndex("Style"));
            recipeList.add(new RecipeSearch(id,name,style));
        }
        Collections.sort(recipeList);
        this.fullList = recipeList;


    }





    private void setUpRecyclerView() {
        createFullList();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recipeAdapter = new RecipeAdapter(this.fullList,this::onRecipeClick);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recipeAdapter);
    }

    @Override
    public void onRecipeClick(int position) {
        Intent intent = new Intent(this, RecipeView.class);
        intent.putExtra("id",this.fullList.get(position).getId());
        startActivity(intent);
        finish();
    }


}
