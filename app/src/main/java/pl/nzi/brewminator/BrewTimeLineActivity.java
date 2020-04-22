package pl.nzi.brewminator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
import pl.nzi.brewminator.model.Step;
import pl.nzi.brewminator.service.RecipeSteps;


public class BrewTimeLineActivity extends AppCompatActivity {
    private final static String TAG = "BrewTimeLineActivity";
    private RelativeLayout wholeBar;
    private RelativeLayout progressBar;

    private int fullTime;
    private int timeElapsed;
    List<Step> steps;
    private LinearLayout stepsLayout;
    private Step activeStep;
    private Recipe recipe;
    private int nextStepSound, finishSound;

    private SoundPool soundPool;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brew_time_line);
        Toolbar toolbar = findViewById(R.id.toolbar);
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
        recipe = gson.fromJson(recipeString, Recipe.class);
        RecipeSteps recipeSteps = new RecipeSteps(recipe);
        stepsLayout = findViewById(R.id.steps_view);
        steps = recipeSteps.getSteps();
        fullTime = 0;
        for (Step s : steps) {
            if (s.getStep()!= RecipeSteps.STEP.FERMENT) {
                fullTime += s.getTime() * 60;
            }
            addStepsLayout(s);
            setupTime(s);
        }
        setupStepsButtons();

        setupActiveStep(steps.get(0));

        setupSoundPool();

    }

    private void setupSoundPool() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(6)
                .setAudioAttributes(audioAttributes)
                .build();

        finishSound = soundPool.load(this, R.raw.finish_sound, 1);
    }

    private void setupActiveStep(Step step) {


        activeStep = step;
        showStep(activeStep);
        LinearLayout layout = activeStep.getView().findViewById(R.id.step_layout);

        layout.setBackgroundResource(R.drawable.active_step);
        Button button = activeStep.getView().findViewById(R.id.step_button);
        button.setVisibility(View.VISIBLE);
        button.setText("OK!");
        button.setOnClickListener(v -> {
            if (step.getTime() > 0 && step.getStep()!= RecipeSteps.STEP.FERMENT) {
                step.getTimer().start();
                button.setText("Skip!");
                button.setOnClickListener(v1 -> nextStep());
            } else {
                changeActiveStep();
            }
        });
        if (step.getStep()== RecipeSteps.STEP.FERMENT){
            Button calendarButton = activeStep.getView().findViewById(R.id.callendar_button);
            calendarButton.setOnClickListener(v -> setCalendar(step.getTime(),recipe.getNAME()));
            calendarButton.setVisibility(View.VISIBLE);
        }


    }

    private void setCalendar(int time,String name) {
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis()+time*60*60*1000);
        intent.putExtra("allDay", false);
        intent.putExtra("title", "Take out hops from "+name+" beer brewing");
        startActivity(intent);
    }

    private void nextStep() {
        vibrate(0.5f);
        Step step = activeStep;
        step.getTimer().cancel();
        step.getView().findViewById(R.id.step_timer).setVisibility(View.GONE);
        int t = 0;
        for (Step s : steps) {
            if (s == step) {
                t += s.getTime();
                this.timeElapsed = t * 60;
                float percent = ((float) timeElapsed / (float) fullTime) * 100;
                new UpdateProgressBar().execute(percent);
                Log.d(TAG, "skipStep: " + timeElapsed);
                changeActiveStep();
            }
            t += s.getTime();
        }


    }

    private void vibrate(float s) {
        final long[] pattern = {0, Math.round(s * 1000)};
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        assert vibrator != null;
        vibrator.vibrate(pattern, -1);

    }

    private void playSound(int sound) {

        soundPool.play(sound, 1, 1, 0, 0, 1);

    }


    private void setupTime(Step step) {

        if (step.getTime() > 0) {
            step.setTimer(
                    new CountDownTimer(step.getTime() * 1000 * 60, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            updateStepTimer(millisUntilFinished / 1000, step);
                            timeElapsed += 1;
                            Log.d(TAG, "onTick: " + timeElapsed);
                            float percent = ((float) timeElapsed / (float) fullTime) * 100;
                            Log.d(TAG, "onTick: " + percent);
                            new UpdateProgressBar().execute(percent);
                        }

                        @Override
                        public void onFinish() {
                            nextStep();

                        }

                    });
        }

    }


    private void updateStepTimer(long l, Step step) {
        TextView textView = step.getView().findViewById(R.id.step_timer);
        textView.setText(parseTime((int) l));
    }

    private void setupTimeline() {

        RelativeLayout timeLineLayout = findViewById(R.id.timeline_layout);
        wholeBar = timeLineLayout.findViewById(R.id.timeline_bar);
        progressBar = wholeBar.findViewById(R.id.progress_bar);

    }


    private void addStepButton(float percent, Step step) {

        View child = getLayoutInflater().inflate(R.layout.step_timeline, null);
        Button button = child.findViewById(R.id.button);
        button.setOnClickListener(v -> showStep(step));
        wholeBar.addView(child);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) child.getLayoutParams();
        params.setMargins(0, Math.round(wholeBar.getHeight() * percent / 100) - 5, 0, 0);
        child.setLayoutParams(params);


    }

    private void changeActiveStep() {
        Step oldStep = activeStep;
        oldStep.getView().findViewById(R.id.step_layout).setBackgroundResource(R.drawable.ended_step);
        oldStep.getView().findViewById(R.id.step_button).setVisibility(View.GONE);
        oldStep.getView().findViewById(R.id.callendar_button).setVisibility(View.GONE);
        try {
            setupActiveStep(steps.get(steps.indexOf(oldStep) + 1));
        } catch (IndexOutOfBoundsException e) {
            finishBrewing();
        }
    }

    private void finishBrewing() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View dialogView = getLayoutInflater().inflate(R.layout.finish_brewing_dialog, null);
        ImageView anim = dialogView.findViewById(R.id.finish_anim);
        AnimationDrawable animation;
        anim.setBackgroundResource(R.drawable.loading);
        animation = (AnimationDrawable) anim.getBackground();
        animation.start();

        Button returnButton = dialogView.findViewById(R.id.return_button);

        Button commentButton = dialogView.findViewById(R.id.comment_button);
        commentButton.setOnClickListener(v -> Toast.makeText(this, "Comment recipe", Toast.LENGTH_SHORT).show());
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        returnButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
            alertDialog.dismiss();

        });
        playSound(finishSound);
    }

    private void addStepsLayout(Step step) {

        View child = getLayoutInflater().inflate(R.layout.timeline_step_layout, null);
        LinearLayout message = child.findViewById(R.id.addition_layout);
        TextView timer = child.findViewById(R.id.step_timer);
        message.addView(addtionLayout(step.getMessage()));
        if (step.getAdditions() != null) {
            for (String addtition : step.getAdditions()) {
                message.addView(addtionLayout(addtition));
            }
        }
        stepsLayout.addView(child);
        if (step.getTime() == 0) {
            timer.setVisibility(View.GONE);
        } else {
            timer.setText(parseTime(step.getTime() * 60));
        }
        step.setView(child);


    }

    private View addtionLayout(String text) {
        View child = getLayoutInflater().inflate(R.layout.addition_textview, null);
        TextView textView = child.findViewById(R.id.step_message_text);
        textView.setText(text);
        return child;
    }

    private void showStep(Step step) {
        View view = step.getView();
        ScrollView scroll = findViewById(R.id.timeline_scroll);
        new Handler().post(() -> {
            scroll.smoothScrollTo(0, view.getTop());
        });
    }


    private void setupStepsButtons() {

        wholeBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                wholeBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int elapsedtime = 0;
                for (Step step : steps) {
                    float percent = ((float) elapsedtime / (float) fullTime) * 100;
                    addStepButton(percent, step);
                    elapsedtime += step.getTime() * 60;
                }
            }
        });
    }

    public String parseTime(int s) {
        int minutes = (s % 3600) / 60;
        int hours = s / 3600;
        int seconds = s % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);

    }


    @SuppressLint("StaticFieldLeak")
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }

}
