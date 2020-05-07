package pl.nzi.brewminator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.SearchManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.net.sip.SipSession;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.digidemic.unitof.V;
import com.google.android.material.navigation.NavigationView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import pl.nzi.brewminator.model.RecipeSearch;
import pl.nzi.brewminator.service.RecipesJobService;


public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    SavedRecipesDatabaseHelper db;
    LinearLayout savedLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.main_activity);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.cropped_logo);

        NavigationView navigationView = findViewById(R.id.nav_home);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        }
        db = new SavedRecipesDatabaseHelper(this);
        setupBestForBegginers();
        savedLayout = findViewById(R.id.saved_layout);
        new Thread(() -> {
            List<RecipeSearch> previous = new ArrayList<>();
            while (true) {
                List<RecipeSearch> current = db.getAll();
                if (!isEqual(current,previous)) {
                    runOnUiThread(()->{
                        savedLayout.removeAllViews();
                    });
                    for (RecipeSearch r: current) {
                        runOnUiThread(()->{
                            savedLayout.addView(getRecipeView(r));
                        });
                    }

                    previous = current;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == R.id.search_button) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.ibucalculatornav:
                Intent intent = new Intent(this, IbuCalculatorActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawers();
                return true;
            case R.id.abvcalculatornav:
                intent = new Intent(this, AbvCalculatorActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawers();
                return true;
            case R.id.savednav:
                intent = new Intent(this, SavedRecipesActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawers();
                return true;

        }
        return false;
    }

    private boolean isEqual(List<RecipeSearch> r1,List<RecipeSearch> r2){
        List<String> n1 = new ArrayList<>();
        List<String> n2 = new ArrayList<>();
        for (RecipeSearch r:r1
             ) {
            n1.add(r.getName());
        }
        for (RecipeSearch r:r2
        ) {
            n2.add(r.getName());
        }

        return n1.equals(n2);
    }

    private void setupBestForBegginers(){
        List<RecipeSearch> recipes = new ArrayList<>();
        RecipeDatabaseHelper helper = new RecipeDatabaseHelper(this);
        Cursor cursor = helper.getAllData();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("RecipeName"));
            int id = cursor.getInt(cursor.getColumnIndex("RecipeId"));
            String style = cursor.getString(cursor.getColumnIndex("Style"));
            recipes.add(new RecipeSearch(id,name,style));
        }
        LinearLayout layout = findViewById(R.id.best_for_begginers_layout);
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            int randomIndex = rand.nextInt(recipes.size());
            RecipeSearch r = recipes.get(randomIndex);
            layout.addView(getRecipeView(r));
            recipes.remove(randomIndex);
        }
    }




    private View getRecipeView(RecipeSearch recipe) {
        View child = getLayoutInflater().inflate(R.layout.recipe_card,null);
        TextView name = child.findViewById(R.id.beer_name);
        name.setText(recipe.getName());
        TextView style = child.findViewById(R.id.beer_style);
        style.setText(recipe.getName());
        child.setOnClickListener(v -> {
            Intent intent = new Intent(this,RecipeView.class);
            intent.putExtra("id",recipe.getId());
            startActivity(intent);
        });

        return child;
    }


}
