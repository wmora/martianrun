package com.gamestudio24.cityescape.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.gamestudio24.cityescape.enums.Difficulty;
import com.gamestudio24.cityescape.enums.GameState;

public class GameManager implements GameEventListener {
    private static GameManager ourInstance = new GameManager();

    public static final String PREFERENCES_NAME = "preferences";
    private static final String MAX_SCORE_PREFERENCE = "max_score";

    private GameState gameState;
    private Difficulty difficulty;
    private GameEventListener gameEventListener;

    public static GameManager getInstance() {
        return ourInstance;
    }

    private GameManager() {
        gameState = GameState.OVER;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isMaxDifficulty() {
        return difficulty == Difficulty.values()[Difficulty.values().length - 1];
    }

    public void resetDifficulty() {
        setDifficulty(Difficulty.values()[0]);
    }

    public void setGameEventListener(GameEventListener gameEventListener) {
        this.gameEventListener = gameEventListener;
    }

    @Override
    public void displayAd() {
        gameEventListener.displayAd();
    }

    @Override
    public void hideAd() {
        gameEventListener.hideAd();
    }

    @Override
    public void submitScore(int score) {
        gameEventListener.submitScore(score);
    }

    @Override
    public void displayLeaderboard() {
        gameEventListener.displayLeaderboard();
    }

    @Override
    public void trackEvent(String category, String action, String label, String value) {
        gameEventListener.trackEvent(category, action, label, value);
    }

    @Override
    public void share() {
        gameEventListener.share();
    }

    private Preferences getPreferences() {
        return Gdx.app.getPreferences(PREFERENCES_NAME);
    }

    public void saveScore(int score) {
        Preferences preferences = getPreferences();
        int maxScore = preferences.getInteger(MAX_SCORE_PREFERENCE, 0);
        if (score > maxScore) {
            preferences.putInteger(MAX_SCORE_PREFERENCE, score);
            preferences.flush();
        }
    }

    public boolean hasSavedMaxScore() {
        return getPreferences().getInteger(MAX_SCORE_PREFERENCE, 0) > 0;
    }

    public void submitSavedMaxScore() {
        Preferences preferences = getPreferences();
        submitScore(preferences.getInteger(MAX_SCORE_PREFERENCE, 0));
        preferences.remove(MAX_SCORE_PREFERENCE);
        preferences.flush();
    }

}
