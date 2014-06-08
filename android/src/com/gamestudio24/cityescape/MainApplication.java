package com.gamestudio24.cityescape;

import android.app.Application;
import com.gamestudio24.cityescape.android.R;
import com.google.android.gms.analytics.GoogleAnalytics;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GoogleAnalytics.getInstance(this).newTracker(R.xml.app_tracker_config);
    }

}
