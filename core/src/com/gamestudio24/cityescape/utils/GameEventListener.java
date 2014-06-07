package com.gamestudio24.cityescape.utils;

public interface GameEventListener {

    public void displayAd();

    public void hideAd();

    public void submitScore(int score);

    public void displayLeaderboard();

    public void trackEvent(String category, String action, String label, String value);

}
