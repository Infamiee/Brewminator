package pl.nzi.brewminator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import pl.nzi.brewminator.service.RecipesJobService;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_OUT=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scheduleJob(getCurrentFocus());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN_OUT);

    }

    public void scheduleJob(View v){
        ComponentName componentName = new ComponentName(this, RecipesJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(1,componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(30*60*1000)
                .setPersisted(true)
                .setMinimumLatency(0)
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int result = jobScheduler.schedule(jobInfo);

        if (result == JobScheduler.RESULT_SUCCESS){
            Log.d("Scheduler","job scheduled");
        }else {
            Log.d("Scheduler","job failed");
        }
    }


}
