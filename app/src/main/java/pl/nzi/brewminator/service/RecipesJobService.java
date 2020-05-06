package pl.nzi.brewminator.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.simple.JSONObject;


import pl.nzi.brewminator.RecipeDatabaseHelper;

public class RecipesJobService extends JobService {
    private final String TAG = "Recipe Job Service";
    private RecipeDatabaseHelper db;
    private boolean jobCancelled = false;
    private ApiConnector connector;



    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG,"Started");
        saveAllRecipes(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        jobCancelled = true;
        return true;
    }


    public void saveAllRecipes(JobParameters params){

        new Thread(new Runnable() {
            @Override
            public void run() {

                db = new RecipeDatabaseHelper(getApplicationContext());

                connector = new ApiConnector(getApplicationContext());
                connector.get("/recipe/all",null,Request.Method.GET,
                        response -> {
                            JSONObject jsonObject;
                            Gson gson = new Gson();
                            jsonObject = gson.fromJson(response, JSONObject.class);

                            db.saveAllFromJson(jsonObject);
                        },error -> {
                    Log.d(TAG,"FAILED");
                    jobFinished(params,true);
                });
                Log.d(TAG,"Job finished");
                jobFinished(params,false);
            }
        }).start();

    }
}
