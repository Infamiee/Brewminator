package pl.nzi.brewminator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pl.nzi.brewminator.adapter.FermentablesDeserializer;
import pl.nzi.brewminator.adapter.HopsAdapter;
import pl.nzi.brewminator.adapter.MashStepsDeserialzier;
import pl.nzi.brewminator.adapter.MiscAdapter;
import pl.nzi.brewminator.model.FERMENTABLE;
import pl.nzi.brewminator.model.FERMENTABLES;
import pl.nzi.brewminator.model.HOP;
import pl.nzi.brewminator.model.HOPS;
import pl.nzi.brewminator.model.MASHSTEP;
import pl.nzi.brewminator.model.MASHSTEPS;
import pl.nzi.brewminator.model.MISC;
import pl.nzi.brewminator.model.MISCS;
import pl.nzi.brewminator.model.Recipe;
import pl.nzi.brewminator.model.RecipeSearch;
import pl.nzi.brewminator.service.ApiConnector;

public class RecipeView extends AppCompatActivity {
    private final static String TAG = "Recipe View";
    private Recipe recipe;
    RecipeDatabaseHelper db;
    ImageButton brewButton;
    Button saveButton;
    ImageView imageView;
    AnimationDrawable animation;
    boolean isSaved;
    SavedRecipesDatabaseHelper helper;
    private String recipeString;
    ImageButton commentButton;
    int recipeId;
    private ApiConnector connector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.cropped_logo);
        imageView = findViewById(R.id.loading);
        imageView.setBackgroundResource(R.drawable.loading);
        animation = (AnimationDrawable) imageView.getBackground();
        animation.start();
        View logoView = getToolbarLogoIcon(toolbar);
        logoView.setOnClickListener(v -> {
            Intent intent1 = new Intent(RecipeView.this, HomeActivity.class);
            startActivity(intent1);
            finish();

        });
        helper = new SavedRecipesDatabaseHelper(this);
        connector = new ApiConnector(this);
        new LoadRecipe().execute();



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

    @SuppressLint("StaticFieldLeak")
    public class LoadRecipe extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Intent intent = getIntent();
            int id = intent.getIntExtra("id", -1);
            recipeId = id;
            isSaved = helper.isSaved( id);
            Log.d(TAG, "doInBackground: "+ isSaved);

            Log.d(TAG, "onCreate: " + id);
            connector.get("/recipe/"+String.valueOf(recipeId),null,Request.Method.GET,
                    response -> {
                        Gson gson = new GsonBuilder()
                                .registerTypeAdapter(MASHSTEPS.class, new MashStepsDeserialzier())
                                .registerTypeAdapter(FERMENTABLES.class, new FermentablesDeserializer())
                                .registerTypeAdapter(HOPS.class, new HopsAdapter())
                                .registerTypeAdapter(MISCS.class, new MiscAdapter())
                                .create();
                        Log.d(TAG, "onCreate: " + response);
                        recipe = gson.fromJson(response, Recipe.class);
                        System.out.println(recipe.getClass());
                        if (recipe == null) {
                            setContentView(R.layout.couldn_load_recipe);
                            Toolbar toolbar = findViewById(R.id.toolbar);
                            setSupportActionBar(toolbar);
                        } else {
                            Log.d(TAG, "doInBackground: " +recipe.getNAME());
                            recipeString = response;
                            updateRecipeView(id);
                        }
                    }, error -> {
                setContentView(R.layout.couldn_load_recipe);
                Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setIcon(R.drawable.cropped_logo);
            });


            return null;
        }

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

    private void updateRecipeView(int id) {
        setContentView(R.layout.activity_recipe_view);
        commentButton = findViewById(R.id.comment_button);
        commentButton.setOnClickListener(v -> {
            Intent intent = new Intent(this,FeedbackActivity.class);
            intent.putExtra("id",recipeId);
            startActivity(intent);
        });
        brewButton = findViewById(R.id.brew_button);
        brewButton.setOnClickListener(v-> {
            Intent intent = new Intent(this,BrewTimeLineActivity.class);
            intent.putExtra("recipe",recipeString);
            intent.putExtra("id",recipeId);
            startActivity(intent);
        });
        saveButton = findViewById(R.id.save_button);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.cropped_logo);
        if (isSaved) {
            saveButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_star_gold, 0, 0);

        } else {
            saveButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_star_border_black_24dp, 0, 0);

        }

        saveButton.setOnClickListener(click -> {
            isSaved = !isSaved;
            if (isSaved) {
                saveButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_star_gold, 0, 0);
                helper.addData(id,recipe.getNAME(),recipe.getSTYLE().getNAME());
            } else {
                saveButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_star_border_black_24dp, 0, 0);
                helper.remove(id);
            }
        });
        View logoView = getToolbarLogoIcon(toolbar);
        logoView.setOnClickListener(v -> {
            Intent intent1 = new Intent(RecipeView.this, HomeActivity.class);
            startActivity(intent1);
            finish();

        });

        updateName();
        updateGravities();
        updateStats();
        updateFermentables();
        updateHops();
        updateMashGuidlines();
        updateOtherIngredients();
        updateYeast();
    }


    private void updateName() {
        TextView textView = findViewById(R.id.recipe_name_textview);
        textView.setText(recipe.getNAME());
    }

    private void updateGravities() {
        TextView og = findViewById(R.id.gravities_og_textview);
        if (!recipe.getOG().trim().isEmpty()) {
            og.setText(recipe.getOG());
        } else {
            og.setText("N/A");
        }
        TextView fg = findViewById(R.id.gravities_fg_textview);
        if (!recipe.getOG().trim().isEmpty()) {
            fg.setText(recipe.getFG());
        } else {
            fg.setText("N/A");
        }
        TextView ibu = findViewById(R.id.gravities_ibu_textview);
        if (!recipe.getOG().trim().isEmpty()) {
            ibu.setText(recipe.getIBU());
        } else {
            ibu.setText("N/A");
        }

    }

    private void updateStats() {
        TextView textView = findViewById(R.id.Type_textView);
        String s = recipe.getTYPE().trim();
        if (!s.isEmpty()) {
            textView.setText(s);
        } else {
            textView.setText("N/A");
        }
        textView = findViewById(R.id.style_textview);
        s = recipe.getSTYLE().getNAME().trim();
        if (!s.isEmpty()) {
            textView.setText(s);
        } else {
            textView.setText("N/A");
        }
        textView = findViewById(R.id.boiltime_textView);
        s = recipe.getBOILTIME().trim();
        if (!s.isEmpty()) {
            textView.setText(s);
        } else {
            textView.setText("N/A");
        }

        textView = findViewById(R.id.preboil_textView);
        s = recipe.getBATCHSIZE().trim();
        if (!s.isEmpty()) {
            Double a = Double.parseDouble(s);
            textView.setText(String.format("%.2f", a));
        } else {
            textView.setText("N/A");
        }

        textView = findViewById(R.id.sizeafter_textView);
        s = recipe.getBOILSIZE().trim();
        if (!s.isEmpty()) {
            Double a = Double.parseDouble(s);
            textView.setText(String.format("%.2f", a));
        } else {
            textView.setText("N/A");
        }

        textView = findViewById(R.id.efficiency_textView);
        s = recipe.getEFFICIENCY().trim();
        if (!s.isEmpty()) {
            textView.setText(s);
        } else {
            textView.setText("N/A");
        }

    }

    private void updateFermentables() {

        List<FERMENTABLE> fermentables = recipe.getFERMENTABLES().getFERMENTABLE();
        TableLayout tableLayout = findViewById(R.id.fermentables_table);
        for (FERMENTABLE fermentable : recipe.getFERMENTABLES().getFERMENTABLE()) {
            TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.fermentables_row, tableLayout, false);
            TextView amount = tableRow.findViewById(R.id.fermentables_amount);
            TextView name = tableRow.findViewById(R.id.fermentables_name);
            TextView type = tableRow.findViewById(R.id.fermentables_type);
            String s = fermentable.getAMOUNT();
            if (s != null && !s.trim().isEmpty()) {
                Double a = Double.parseDouble(s);
                amount.setText(String.format("%.2f", a));
            } else {
                amount.setText("N/A");
            }
            s = fermentable.getNAME();
            if (s != null && !s.trim().isEmpty()) {
                name.setText(s.trim());
            } else {
                name.setText("N/A");
            }
            s = fermentable.getTYPE();
            if (s != null && !s.trim().isEmpty()) {
                type.setText(s.trim());
            } else {
                type.setText("N/A");
            }

            tableLayout.addView(tableRow);
        }


    }

    private void updateHops() {
        List<HOP> hops;
        try {
            hops = recipe.getHOPS().getHOP();

        } catch (Exception e) {
            findViewById(R.id.hops_layout).setVisibility(View.GONE);
            return;
        }

        TableLayout tableLayout = findViewById(R.id.hops_table);

        for (HOP hop : hops) {
            TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.hops_row, tableLayout, false);
            TextView amount = tableRow.findViewById(R.id.hops_amount);
            TextView name = tableRow.findViewById(R.id.hops_name);
            TextView type = tableRow.findViewById(R.id.hops_type);
            TextView use = tableRow.findViewById(R.id.hops_use);
            TextView time = tableRow.findViewById(R.id.hops_time);

            String s = hop.getAMOUNT();
            if (s != null && !s.trim().isEmpty()) {
                Double a = Double.parseDouble(s) * 1000;
                amount.setText(String.format("%.2f", a) + " g");
            } else {
                amount.setText("N/A");
            }
            s = hop.getNAME();
            if (s != null && !s.trim().isEmpty()) {
                name.setText(s.trim());
            } else {
                name.setText("N/A");
            }
            s = hop.getFORM();
            if (s != null && !s.trim().isEmpty()) {
                type.setText(s.trim());
            } else {
                type.setText("N/A");
            }
            s = hop.getUSE();
            if (s != null && !s.trim().isEmpty()) {
                use.setText(s.trim());
            } else {
                use.setText("N/A");
            }
            s = hop.getTIME();
            if (s != null && !s.trim().isEmpty()) {
                int t = Integer.parseInt(s);
                int hours = t / 60;
                int minutes = t % 60;
                if (hours == 0) {
                    time.setText(minutes + " min");
                } else if (minutes == 0) {
                    time.setText(hours + " h");
                } else {
                    time.setText(hours + ":" + minutes);
                }

            } else {
                time.setText("N/A");
            }

            tableLayout.addView(tableRow);

        }

    }

    private void updateMashGuidlines() {
        List<MASHSTEP> mashsteps;
        try {
            mashsteps = recipe.getMASH().getMASHSTEPS().getMASHSTEP();

        } catch (Exception e) {
            findViewById(R.id.mashguid_layout).setVisibility(View.GONE);
            return;
        }

        TableLayout tableLayout = findViewById(R.id.mash_table);

        for (MASHSTEP mashstep : mashsteps) {
            TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.mash_guidlines_row, tableLayout, false);
            TextView amount = tableRow.findViewById(R.id.mash_amount);
            TextView temp = tableRow.findViewById(R.id.mash_temp);
            TextView type = tableRow.findViewById(R.id.mash_type);
            TextView time = tableRow.findViewById(R.id.mash_time);
            String s = mashstep.getINFUSEAMOUNT();
            if (s != null && !s.trim().isEmpty()) {
                Double a = Double.parseDouble(s);
                amount.setText(String.format("%.2f", a) + " g");
            } else {
                amount.setText("N/A");
            }
            s = mashstep.getTYPE();
            if (s != null && !s.trim().isEmpty()) {
                type.setText(s.trim());
            } else {
                type.setText("N/A");
            }
            s = mashstep.getSTEPTIME();
            if (s != null && !s.trim().isEmpty()) {
                time.setText(s.trim());
            } else {
                time.setText("N/A");
            }
            s = mashstep.getSTEPTEMP();
            if (s != null && !s.trim().isEmpty()) {
                temp.setText(s.substring(0, 2));
            } else {
                temp.setText("N/A");
            }


            tableLayout.addView(tableRow);
        }


    }

    private void updateOtherIngredients() {
        List<MISC> miscs;
        try {
            miscs = recipe.getMISCS().getMISC();

        } catch (Exception e) {
            findViewById(R.id.misc_layout).setVisibility(View.GONE);
            return;
        }


        TableLayout tableLayout = findViewById(R.id.misc_table);

        for (MISC misc : miscs) {
            TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.misc_row, tableLayout, false);
            TextView amount = tableRow.findViewById(R.id.misc_amount);
            TextView name = tableRow.findViewById(R.id.misc_name);
            TextView time = tableRow.findViewById(R.id.misc_time);
            TextView use = tableRow.findViewById(R.id.misc_use);
            String s = misc.getAMOUNT();
            if (s != null && !s.trim().isEmpty()) {
                Double a = Double.parseDouble(s) * 1000;
                amount.setText(String.format("%.2f", a) + " g");
            } else {
                amount.setText("N/A");
            }
            s = misc.getNAME();
            if (s != null && !s.trim().isEmpty()) {
                name.setText(s);
            } else {
                name.setText("N/A");
            }
            s = misc.getNAME();
            if (s != null && !s.trim().isEmpty()) {
                name.setText(s);
            } else {
                name.setText("N/A");
            }
            s = misc.getTIME();
            if (s != null && !s.trim().isEmpty()) {
                int t = Integer.parseInt(s);
                int hours = t / 60;
                int minutes = t % 60;
                if (hours == 0) {
                    time.setText(minutes + " min");
                } else if (minutes == 0) {
                    time.setText(hours + " h");
                } else {
                    time.setText(hours + ":" + minutes);
                }
            } else {
                time.setText("N/A");
            }
            s = misc.getUSE();
            if (s != null && !s.trim().isEmpty()) {
                use.setText(s);
            } else {
                use.setText("N/A");
            }

            tableLayout.addView(tableRow);


        }

    }

    private void updateYeast() {
        TextView textView = findViewById(R.id.yeastname_textView);
        String s = recipe.getYEASTS().getYEAST().getNAME();
        if (s != null && !s.trim().isEmpty()) {
            textView.setText(s);
        } else {
            textView.setText("N/A");
        }
        textView = findViewById(R.id.yeastlaboratory_textView);
        s = recipe.getYEASTS().getYEAST().getLABORATORY();
        if (s != null && !s.trim().isEmpty()) {
            textView.setText(s);
        } else {
            textView.setText("N/A");
        }
        textView = findViewById(R.id.yeastype_textView);
        s = recipe.getYEASTS().getYEAST().getTYPE();
        if (s != null && !s.trim().isEmpty()) {
            textView.setText(s);
        } else {
            textView.setText("N/A");
        }
        textView = findViewById(R.id.yeastamount_textview);
        s = recipe.getYEASTS().getYEAST().getAMOUNT();
        if (s != null && !s.trim().isEmpty()) {
            textView.setText(s);
        } else {
            textView.setText("N/A");
        }
        textView = findViewById(R.id.attenuation_textView);
        s = recipe.getYEASTS().getYEAST().getATTENUATION();
        if (s != null && !s.trim().isEmpty()) {
            textView.setText(s);
        } else {
            textView.setText("N/A");
        }
        textView = findViewById(R.id.flocculation_textView);
        s = recipe.getYEASTS().getYEAST().getFLOCCULATION();
        if (s != null && !s.trim().isEmpty()) {
            textView.setText(s);
        } else {
            textView.setText("N/A");
        }
        textView = findViewById(R.id.optimum_textView);
        s = recipe.getYEASTS().getYEAST().getMINTEMPERATURE().substring(0, 2) + "-" + recipe.getYEASTS().getYEAST().getMAXTEMPERATURE().substring(0, 2);

        if (s != null && !s.trim().isEmpty()) {
            textView.setText(s);
        } else {
            textView.setText("N/A");
        }


    }


}
