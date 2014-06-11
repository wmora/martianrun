package com.gamestudio24.cityescape.android;

import android.content.Intent;
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
import com.gamestudio24.cityescape.utils.GameManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.games.Games;
import com.google.games.basegameutils.GameHelper;

public class AndroidLauncher extends AndroidApplication implements GameHelper.GameHelperListener, GameEventListener {

    private static String SAVED_LEADERBOARD_REQUESTED = "SAVED_LEADERBOARD_REQUESTED";

    private static final String AD_UNIT_ID = "ca-app-pub-9726805752162406/6658558541";
    private static final String HIGH_SCORES_LEADERBOARD_ID = "CggIpqrhukgQAhAA";
    private static final String BUGSENSE_API_KEY = "3e9f5c76";
    private GameHelper gameHelper;

    private AdView mAdView;
    private boolean mLeaderboardRequested;

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


        mAdView = createAdView();
        mAdView.loadAd(createAdRequest());

        layout.addView(mAdView, getAdParams());

        setContentView(layout);

        gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
        gameHelper.setup(this);
        gameHelper.setMaxAutoSignInAttempts(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        gameHelper.onStart(this);
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameHelper.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gameHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_LEADERBOARD_REQUESTED, mLeaderboardRequested);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mLeaderboardRequested = savedInstanceState.getBoolean(SAVED_LEADERBOARD_REQUESTED, false);
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
        mLeaderboardRequested = false;
    }

    @Override
    public void onSignInSucceeded() {
        // handle sign-in success
        if (GameManager.getInstance().hasSavedMaxScore()) {
            GameManager.getInstance().submitSavedMaxScore();
        }

        if (mLeaderboardRequested) {
            displayLeaderboard();
            mLeaderboardRequested = false;
        }
    }

    @Override
    public void displayAd() {
        mAdView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAd() {
        mAdView.setVisibility(View.GONE);
    }

    @Override
    public void submitScore(int score) {
        if (gameHelper.isSignedIn()) {
            Games.Leaderboards.submitScore(gameHelper.getApiClient(), HIGH_SCORES_LEADERBOARD_ID, score);
        } else {
            GameManager.getInstance().saveScore(score);
        }
    }

    @Override
    public void displayLeaderboard() {
        if (gameHelper.isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
                    HIGH_SCORES_LEADERBOARD_ID), 24);
        } else {
            gameHelper.beginUserInitiatedSignIn();
            mLeaderboardRequested = true;
        }
    }

    @Override
    public void trackEvent(String category, String action, String label, String value) {
        // TODO: Implement events
    }

}
