/*
 * Copyright (c) 2014. William Mora
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gamestudio24.martianrun.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.gamestudio24.martianrun.enums.Difficulty;
import com.gamestudio24.martianrun.enums.GameState;

/**
 * A utility singleton that holds the current {@link com.gamestudio24.martianrun.enums.Difficulty}
 * and {@link com.gamestudio24.martianrun.enums.GameState} of the game as well as the
 * {@link com.gamestudio24.martianrun.utils.GameEventListener} instance responsible for dispatching
 * all game events for the platform running the game
 */
public class GameManager implements GameEventListener {
    private static GameManager ourInstance = new GameManager();

    public static final String PREFERENCES_NAME = "preferences";
    private static final String MAX_SCORE_PREFERENCE = "max_score";
    private static final String ACHIEVEMENT_COUNT_PREFERENCE_SUFFIX = "_count";
    private static final String ACHIEVEMENT_UNLOCKED_PREFERENCE_SUFFIX = "_unlocked";

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

    /**
     * Submits a score and unlocks a score-based achievement depending on the total
     */
    @Override
    public void submitScore(int score) {
        gameEventListener.submitScore(score);

        if (score > 5000 && !isAchievementUnlocked(get5kClubAchievementId())) {
            unlockAchievement(get5kClubAchievementId());
        }

        if (score > 10000 && !isAchievementUnlocked(get10kClubAchievementId())) {
            unlockAchievement(get10kClubAchievementId());
        }

        if (score > 25000 && !isAchievementUnlocked(get25kClubAchievementId())) {
            unlockAchievement(get25kClubAchievementId());
        }

        if (score > 50000 && !isAchievementUnlocked(get50kClubAchievementId())) {
            unlockAchievement(get50kClubAchievementId());
        }
    }

    @Override
    public void displayLeaderboard() {
        gameEventListener.displayLeaderboard();
    }

    @Override
    public void displayAchievements() {
        gameEventListener.displayAchievements();
    }

    @Override
    public void share() {
        gameEventListener.share();
    }

    @Override
    public void unlockAchievement(String id) {
        gameEventListener.unlockAchievement(id);
    }

    @Override
    public void incrementAchievement(String id, int steps) {
        gameEventListener.incrementAchievement(id, steps);
    }

    @Override
    public String getGettingStartedAchievementId() {
        return gameEventListener.getGettingStartedAchievementId();
    }

    @Override
    public String getLikeARoverAchievementId() {
        return gameEventListener.getLikeARoverAchievementId();
    }

    @Override
    public String getSpiritAchievementId() {
        return gameEventListener.getSpiritAchievementId();
    }

    @Override
    public String getCuriosityAchievementId() {
        return gameEventListener.getCuriosityAchievementId();
    }

    @Override
    public String get5kClubAchievementId() {
        return gameEventListener.get5kClubAchievementId();
    }

    @Override
    public String get10kClubAchievementId() {
        return gameEventListener.get10kClubAchievementId();
    }

    @Override
    public String get25kClubAchievementId() {
        return gameEventListener.get25kClubAchievementId();
    }

    @Override
    public String get50kClubAchievementId() {
        return gameEventListener.get50kClubAchievementId();
    }

    @Override
    public String get10JumpStreetAchievementId() {
        return gameEventListener.get10JumpStreetAchievementId();
    }

    @Override
    public String get100JumpStreetAchievementId() {
        return gameEventListener.get100JumpStreetAchievementId();
    }

    @Override
    public String get500JumpStreetAchievementId() {
        return gameEventListener.get500JumpStreetAchievementId();
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

    public void addGamePlayed() {

        // No need to keep counting if all achievements have been unlocked
        if (getAchievementCount(getCuriosityAchievementId()) > 500) {
            return;
        }

        if (!isAchievementUnlocked(getGettingStartedAchievementId())) {
            unlockAchievement(getGettingStartedAchievementId());
        }

        if (getAchievementCount(getLikeARoverAchievementId()) <= 10) {
            incrementAchievement(getLikeARoverAchievementId(), 1);
        }

        if (getAchievementCount(getSpiritAchievementId()) <= 100) {
            incrementAchievement(getSpiritAchievementId(), 1);
        }

        incrementAchievement(getCuriosityAchievementId(), 1);

    }

    public void addJumpCount(int count) {

        if (count <= 0) {
            return;
        }

        if (getAchievementCount(get500JumpStreetAchievementId()) > 500) {
            return;
        }

        if (getAchievementCount(get500JumpStreetAchievementId()) <= 10) {
            incrementAchievement(get10JumpStreetAchievementId(), count);
        }

        if (getAchievementCount(get500JumpStreetAchievementId()) <= 100) {
            incrementAchievement(get100JumpStreetAchievementId(), count);
        }

        incrementAchievement(get500JumpStreetAchievementId(), count);

    }

    public void setAchievementUnlocked(String id) {
        getPreferences().putBoolean(getAchievementUnlockedId(id), true);
    }

    public void incrementAchievementCount(String id, int steps) {
        Preferences preferences = getPreferences();
        int count = preferences.getInteger(getAchievementCountId(id), 0);
        count += steps;
        preferences.putInteger(getAchievementCountId(id), count);
        preferences.flush();
    }

    private int getAchievementCount(String id) {
        return getPreferences().getInteger(getAchievementCountId(id), 0);
    }

    private boolean isAchievementUnlocked(String id) {
        return getPreferences().getBoolean(getAchievementUnlockedId(id), false);
    }

    private String getAchievementCountId(String id) {
        return id + ACHIEVEMENT_COUNT_PREFERENCE_SUFFIX;
    }

    private String getAchievementUnlockedId(String id) {
        return id + ACHIEVEMENT_UNLOCKED_PREFERENCE_SUFFIX;
    }
}
