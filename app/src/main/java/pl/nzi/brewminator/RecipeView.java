package pl.nzi.brewminator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Objects;

import pl.nzi.brewminator.adapter.FermentablesDeserializer;
import pl.nzi.brewminator.adapter.HopsAdapter;
import pl.nzi.brewminator.adapter.MashStepsDeserialzier;
import pl.nzi.brewminator.adapter.MiscAdapter;
import pl.nzi.brewminator.model.FERMENTABLE;
import pl.nzi.brewminator.model.FERMENTABLES;
import pl.nzi.brewminator.model.HOPS;
import pl.nzi.brewminator.model.MASHSTEPS;
import pl.nzi.brewminator.model.MISCS;
import pl.nzi.brewminator.model.Recipe;

public class RecipeView extends AppCompatActivity {
    private final static String TAG = "Recipe View";
    private Recipe recipe;
    DatabaseHelper db;
    ImageButton brewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.cropped_logo);

        brewButton = findViewById(R.id.brew_button);
        brewButton.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "Starting brewing", Toast.LENGTH_LONG).show());

        Intent intent = getIntent();

        int id = intent.getIntExtra("id", -1);

        View logoView = getToolbarLogoIcon(toolbar);
        logoView.setOnClickListener(v -> {
            Intent intent1 = new Intent(RecipeView.this, HomeActivity.class);
            startActivity(intent1);
            finish();

        });


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.18:5000/recipe?id=" + String.valueOf(id);

        Log.d(TAG, "onCreate: " + id);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(MASHSTEPS.class, new MashStepsDeserialzier())
                            .registerTypeAdapter(FERMENTABLES.class, new FermentablesDeserializer())
                            .registerTypeAdapter(HOPS.class, new HopsAdapter())
                            .registerTypeAdapter(MISCS.class, new MiscAdapter())
                            .create();
                    Log.d(TAG, "onCreate: " + response);
                    recipe = gson.fromJson(response, Recipe.class);
                    updateRecipeView();
                }, Throwable::printStackTrace);

        queue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.search_button:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

    public static View getToolbarLogoIcon(Toolbar toolbar) {
        //check if contentDescription previously was set
        boolean hadContentDescription = android.text.TextUtils.isEmpty(toolbar.getLogoDescription());
        String contentDescription = String.valueOf(!hadContentDescription ? toolbar.getLogoDescription() : "logoContentDescription");
        toolbar.setLogoDescription(contentDescription);
        ArrayList<View> potentialViews = new ArrayList<View>();
        //find the view based on it's content description, set programatically or with android:contentDescription
        toolbar.findViewsWithText(potentialViews, contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        //Nav icon is always instantiated at this point because calling setLogoDescription ensures its existence
        View logoIcon = null;
        if (potentialViews.size() > 0) {
            logoIcon = potentialViews.get(0);
        }
        //Clear content description if not previously present
        if (hadContentDescription)
            toolbar.setLogoDescription(null);
        return logoIcon;
    }

    private void updateRecipeView() {
        updateName();
        updateGravities();
        updateStats();
        updateFermentables();
    }


    private void updateName() {
        TextView textView = findViewById(R.id.recipe_name_textview);
        textView.setText(recipe.getNAME());
    }

    private void updateGravities(){
        TextView og = findViewById(R.id.gravities_og_textview);
        if (!recipe.getOG().trim().isEmpty()){
            og.setText(recipe.getOG());
        }else {
            og.setText("N/A");
        }
        TextView fg = findViewById(R.id.gravities_fg_textview);
        if (!recipe.getOG().trim().isEmpty()){
            fg.setText(recipe.getFG());
        }else {
            fg.setText("N/A");
        }
        TextView ibu = findViewById(R.id.gravities_ibu_textview);
        if (!recipe.getOG().trim().isEmpty()){
            ibu.setText(recipe.getIBU());
        }else {
            ibu.setText("N/A");
        }

    }

    private void updateStats(){
        TextView textView = findViewById(R.id.Type_textView);
        String s = recipe.getTYPE().trim();
        if (!s.isEmpty()){
            textView.setText(s);
        }else {
            textView.setText("N/A");
        }
        textView = findViewById(R.id.style_textview);
        s = recipe.getSTYLE().getNAME().trim();
        if (!s.isEmpty()){
            textView.setText(s);
        }else {
            textView.setText("N/A");
        }
        textView = findViewById(R.id.boiltime_textView);
        s = recipe.getBOILTIME().trim();
        if (!s.isEmpty()){
            textView.setText(s);
        }else {
            textView.setText("N/A");
        }

        textView = findViewById(R.id.preboil_textView);
        s = recipe.getBATCHSIZE().trim();
        if (!s.isEmpty()){
            textView.setText(s);
        }else {
            textView.setText("N/A");
        }

        textView = findViewById(R.id.sizeafter_textView);
        s = recipe.getBOILSIZE().trim();
        if (!s.isEmpty()){
            textView.setText(s);
        }else {
            textView.setText("N/A");
        }

        textView = findViewById(R.id.efficiency_textView);
        s = recipe.getEFFICIENCY().trim();
        if (!s.isEmpty()){
            textView.setText(s);
        }else {
            textView.setText("N/A");
        }

    }

    private void updateFermentables(){

        List<FERMENTABLE> fermentables = recipe.getFERMENTABLES().getFERMENTABLE();
        TableLayout tableLayout = findViewById(R.id.fermentables_table);

    }


}
