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

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                String url ="http://192.168.1.18:5000/recipe/all";

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        response -> {
                            JSONObject jsonObject;
                            Gson gson = new Gson();
                            jsonObject = gson.fromJson(response, JSONObject.class);

                            db.saveAllFromJson(jsonObject);
                        },error -> {
                    Log.d(TAG,"FAILED");
                    jobFinished(params,true);
                });
                queue.add(stringRequest);
                Log.d(TAG,"Job finished");
                jobFinished(params,false);
            }
        }).start();

    }
}
