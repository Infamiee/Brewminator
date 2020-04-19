package pl.nzi.brewminator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Objects;

import pl.nzi.brewminator.adapter.FermentablesDeserializer;
import pl.nzi.brewminator.adapter.HopsAdapter;
import pl.nzi.brewminator.adapter.MashStepsDeserialzier;
import pl.nzi.brewminator.adapter.MiscAdapter;
import pl.nzi.brewminator.model.FERMENTABLES;
import pl.nzi.brewminator.model.HOPS;
import pl.nzi.brewminator.model.MASHSTEPS;
import pl.nzi.brewminator.model.MISCS;
import pl.nzi.brewminator.model.Recipe;


public class BrewTimeLineActivity extends AppCompatActivity {
    private final static String TAG = "BrewTimeLineActivity";
    private Toolbar toolbar;
    private RelativeLayout timeLineLayout;
    private RelativeLayout wholeBar;
    private RelativeLayout progressBar;
    private RelativeLayout timeLineRel;
    private int barHeight;
    private TextView textView;
    private int fullTime;
    private int timeElapsed;
    private Recipe recipe;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brew_time_line);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.cropped_logo);
        setupTimeline();
        Intent intent = getIntent();
        String recipeString = intent.getStringExtra("recipe");
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(MASHSTEPS.class, new MashStepsDeserialzier())
                .registerTypeAdapter(FERMENTABLES.class, new FermentablesDeserializer())
                .registerTypeAdapter(HOPS.class, new HopsAdapter())
                .registerTypeAdapter(MISCS.class, new MiscAdapter())
                .create();
        Log.d(TAG, "onCreate: " + recipeString);
        recipe = gson.fromJson(recipeString, Recipe.class);
        Log.d(TAG, "onCreate: "+recipe.getNAME());

        timeElapsed = 60;
        fullTime = 5 * 60;
        textView = findViewById(R.id.timer);

        wholeBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                wholeBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                addStepButton(60, 1);
                addStepButton(20, 3);
                addStepButton(91.2f, 2);

            }
        });

        setupTimer(1, 1);


    }

    private void setupTimer(int time, int stepId) {

        new CountDownTimer(time * 1000 * 60, 1000) {

            public void onTick(long millisUntilFinished) {
                updateStepTimer(millisUntilFinished/1000);
                timeElapsed += 1;
                Log.d(TAG, "onTick: " + timeElapsed);
                float percent = ((float) timeElapsed / (float) fullTime) * 100;
                Log.d(TAG, "onTick: " + percent);
                new UpdateProgressBar().execute(percent);


            }

            public void onFinish() {
                showStep(stepId);
                textView.setText("done!");
            }
        }.start();
    }

    private void updateStepTimer(long l) {
        //#TODO
        //Add Timer
        textView.setText(String.valueOf( l));
    }

    private void setupTimeline() {

        timeLineLayout = findViewById(R.id.timeline_layout);
        wholeBar = timeLineLayout.findViewById(R.id.timeline_bar);
        progressBar = wholeBar.findViewById(R.id.progress_bar);

    }


    private void addStepButton(float percent, int stepId) {

        View child = getLayoutInflater().inflate(R.layout.step_timeline, null);
        Button button = child.findViewById(R.id.button);
        button.setOnClickListener(v -> showStep(stepId));
        wholeBar.addView(child);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) child.getLayoutParams();
        params.setMargins(0, Math.round(wholeBar.getHeight() * percent / 100) - 5, 0, 0);
        child.setLayoutParams(params);


    }

    private void showStep(int stepId) {
        //TODO
        //Add showing step
        Toast.makeText(this, "Clicked step" + stepId, Toast.LENGTH_LONG).show();
    }


    private class UpdateProgressBar extends AsyncTask<Float, Void, Void> {

        @Override
        protected Void doInBackground(Float... floats) {
            float percent = floats[0];
            runOnUiThread(() -> {
                int height = wholeBar.getHeight();
                float pos = height * percent / 100;
                progressBar.getLayoutParams().height = Math.round(pos);
                progressBar.requestLayout();

            });

            return null;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        if (item.getItemId() == R.id.search_button) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
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

}
