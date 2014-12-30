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

package com.gamestudio24.cityescape.utils;

public interface GameEventListener {

    public void displayAd();

    public void hideAd();

    public void submitScore(int score);

    public void displayLeaderboard();

    public void displayAchievements();

    public void share();

    public void unlockAchievement(String id);

    public void incrementAchievement(String id, int steps);

    public String getGettingStartedAchievementId();

    public String getLikeARoverAchievementId();

    public String getSpiritAchievementId();

    public String getCuriosityAchievementId();

    public String get5kClubAchievementId();

    public String get10kClubAchievementId();

    public String get25kClubAchievementId();

    public String get50kClubAchievementId();

    public String get10JumpStreetAchievementId();

    public String get100JumpStreetAchievementId();

    public String get500JumpStreetAchievementId();

}
