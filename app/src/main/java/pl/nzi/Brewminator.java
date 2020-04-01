package pl.nzi;

import android.app.Application;
import android.content.Context;

public class Brewminator extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        Brewminator.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return Brewminator.context;
    }
}
