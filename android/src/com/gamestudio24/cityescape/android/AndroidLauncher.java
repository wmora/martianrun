package com.gamestudio24.cityescape.android;

import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.bugsense.trace.BugSenseHandler;
import com.gamestudio24.cityescape.CityEscape;

public class AndroidLauncher extends AndroidApplication {

    private static final String AD_UNIT_ID = "ca-app-pub-9726805752162406/6658558541";
    private static final String HIGH_SCORES_LEADERBOARD_ID = "CggIpqrhukgQAhAA";
    private static final String BUGSENSE_API_KEY = "3e9f5c76";

    @Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        BugSenseHandler.initAndStartSession(AndroidLauncher.this, BUGSENSE_API_KEY);

        // Create the layout
        RelativeLayout layout = new RelativeLayout(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        // Game view
        View gameView = initializeForView(new CityEscape(), config);
        layout.addView(gameView);

        /*
        adView = createAdView();
        adView.loadAd(createAdRequest());

        layout.addView(adView, getAdParams());
        */

        setContentView(layout);

	}
}
