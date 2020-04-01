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
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;
import java.util.Objects;

import pl.nzi.brewminator.service.RecipesJobService;


public class HomeActivity extends AppCompatActivity {
    Button button;
    Button button1;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.main_activity);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.cropped_logo);

        NavigationView navigationView = findViewById(R.id.nav_home);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        }






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        if (item.getItemId()==R.id.search_button){
            Intent intent = new Intent(this,SearchActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()){
            case R.id.ibucalculatornav:
                Intent intent = new Intent(this,IbuCalculatorActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawers();
                return true;
            case R.id.abvcalculatornav:
                intent = new Intent(this,AbvCalculatorActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawers();
                return true;

        }
        return false;
    }



}
