package com.gamestudio24.cityescape.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gamestudio24.cityescape.CityEscape;
import com.gamestudio24.cityescape.utils.Constants;
import com.gamestudio24.cityescape.utils.GameEventListener;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Constants.APP_WIDTH;
        config.height = Constants.APP_HEIGHT;
		new LwjglApplication(new CityEscape(new GameEventListener() {
            @Override
            public void displayAd() {
                Gdx.app.log(getClass().getSimpleName(), "displayAd");
            }

            @Override
            public void hideAd() {
                Gdx.app.log(getClass().getSimpleName(), "hideAd");
            }

            @Override
            public void submitScore(int score) {
                Gdx.app.log(getClass().getSimpleName(), "submitScore");
            }

            @Override
            public void displayLeaderboard() {
                Gdx.app.log(getClass().getSimpleName(), "displayLeaderboard");
            }

            @Override
            public void displayAchievements() {
                Gdx.app.log(getClass().getSimpleName(), "displayAchievements");
            }

            @Override
            public void trackEvent(String category, String action, String label, String value) {
                Gdx.app.log(getClass().getSimpleName(), "trackEvent");
            }

            @Override
            public void share() {
                Gdx.app.log(getClass().getSimpleName(), "share");
            }

            @Override
            public void unlockAchievement(String id) {

            }

            @Override
            public void incrementAchievement(String id, int steps) {

            }

            @Override
            public String getGettingStartedAchievementId() {
                return null;
            }

            @Override
            public String getLikeARoverAchievementId() {
                return null;
            }

            @Override
            public String getSpiritAchievementId() {
                return null;
            }

            @Override
            public String getCuriosityAchievementId() {
                return null;
            }

            @Override
            public String get5kClubAchievementId() {
                return null;
            }

            @Override
            public String get10kClubAchievementId() {
                return null;
            }

            @Override
            public String get25kClubAchievementId() {
                return null;
            }

            @Override
            public String get50kClubAchievementId() {
                return null;
            }

            @Override
            public String get10JumpStreetAchievementId() {
                return null;
            }

            @Override
            public String get100JumpStreetAchievementId() {
                return null;
            }

            @Override
            public String get500JumpStreetAchievementId() {
                return null;
            }

        }), config);
	}
}
