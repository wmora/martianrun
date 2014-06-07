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
import com.gamestudio24.cityescape.utils.GameEventListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;
import com.google.games.basegameutils.GameHelper;

public class AndroidLauncher extends AndroidApplication implements GameHelper.GameHelperListener, GameEventListener {

    private AdView adView;

    private static final String AD_UNIT_ID = "ca-app-pub-9726805752162406/6658558541";
    private static final String HIGH_SCORES_LEADERBOARD_ID = "CggIpqrhukgQAhAA";
    private static final String BUGSENSE_API_KEY = "3e9f5c76";
    private GameHelper gameHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        View gameView = initializeForView(new CityEscape(this), config);
        layout.addView(gameView);


        adView = createAdView();
        adView.loadAd(createAdRequest());

        layout.addView(adView, getAdParams());

        setContentView(layout);

        gameHelper = new GameHelper(this, GameHelper.CLIENT_ALL);
        gameHelper.setup(this);
        gameHelper.setMaxAutoSignInAttempts(0);
    }

    private AdRequest createAdRequest() {
        return new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
    }

    private AdView createAdView() {
        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(AD_UNIT_ID);

        return adView;
    }

    private RelativeLayout.LayoutParams getAdParams() {
        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        return adParams;
    }

    @Override
    public void onSignInFailed() {
        // handle sign-in failure (e.g. show Sign In button)
    }

    @Override
    public void onSignInSucceeded() {
        // handle sign-in success
        displayLeaderboard();
    }

    @Override
    public void displayAd() {
        adView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAd() {
        adView.setVisibility(View.GONE);
    }

    @Override
    public void submitScore(int score) {
        if (gameHelper.isSignedIn()) {
            Games.Leaderboards.submitScore(gameHelper.getApiClient(), HIGH_SCORES_LEADERBOARD_ID, score);
        }
    }

    @Override
    public void displayLeaderboard() {
        if (gameHelper.isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
                    HIGH_SCORES_LEADERBOARD_ID), 24);
        } else {
            gameHelper.beginUserInitiatedSignIn();
        }
    }

    @Override
    public void trackEvent(String category, String action, String label, String value) {
        // TODO: Implement events
    }

}
