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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;

public class AssetsManager {

    private static HashMap<String, TextureRegion> texturesMap = new HashMap<String, TextureRegion>();
    private static HashMap<String, Animation> animationsMap = new HashMap<String, Animation>();
    private static TextureAtlas textureAtlas;
    private static BitmapFont smallFont;
    private static BitmapFont smallestFont;
    private static BitmapFont largeFont;

    private AssetsManager() {

    }

    public static void loadAssets() {

        // Background
        texturesMap.put(Constants.BACKGROUND_ASSETS_ID,
                new TextureRegion(new Texture(Gdx.files.internal(Constants.BACKGROUND_IMAGE_PATH))));

        // Ground
        texturesMap.put(Constants.GROUND_ASSETS_ID,
                new TextureRegion(new Texture(Gdx.files.internal(Constants.GROUND_IMAGE_PATH))));

        textureAtlas = new TextureAtlas(Constants.SPRITES_ATLAS_PATH);

        // Runner
        texturesMap.put(Constants.RUNNER_JUMPING_ASSETS_ID,
                textureAtlas.findRegion(Constants.RUNNER_JUMPING_REGION_NAME));
        texturesMap.put(Constants.RUNNER_DODGING_ASSETS_ID,
                textureAtlas.findRegion(Constants.RUNNER_DODGING_REGION_NAME));
        texturesMap.put(Constants.RUNNER_HIT_ASSETS_ID,
                textureAtlas.findRegion(Constants.RUNNER_HIT_REGION_NAME));
        animationsMap.put(Constants.RUNNER_RUNNING_ASSETS_ID, createAnimation(textureAtlas,
                Constants.RUNNER_RUNNING_REGION_NAMES));

        // Enemies
        animationsMap.put(Constants.RUNNING_SMALL_ENEMY_ASSETS_ID, createAnimation(textureAtlas,
                Constants.RUNNING_SMALL_ENEMY_REGION_NAMES));
        animationsMap.put(Constants.RUNNING_BIG_ENEMY_ASSETS_ID, createAnimation(textureAtlas,
                Constants.RUNNING_BIG_ENEMY_REGION_NAMES));
        animationsMap.put(Constants.RUNNING_LONG_ENEMY_ASSETS_ID, createAnimation(textureAtlas,
                Constants.RUNNING_LONG_ENEMY_REGION_NAMES));
        animationsMap.put(Constants.RUNNING_WIDE_ENEMY_ASSETS_ID, createAnimation(textureAtlas,
                Constants.RUNNING_WIDE_ENEMY_REGION_NAMES));
        animationsMap.put(Constants.FLYING_SMALL_ENEMY_ASSETS_ID, createAnimation(textureAtlas,
                Constants.FLYING_SMALL_ENEMY_REGION_NAMES));
        animationsMap.put(Constants.FLYING_WIDE_ENEMY_ASSETS_ID, createAnimation(textureAtlas,
                Constants.FLYING_WIDE_ENEMY_REGION_NAMES));

        // Tutorial
        texturesMap.put(Constants.TUTORIAL_LEFT_REGION_NAME,
                textureAtlas.findRegion(Constants.TUTORIAL_LEFT_REGION_NAME));
        texturesMap.put(Constants.TUTORIAL_RIGHT_REGION_NAME,
                textureAtlas.findRegion(Constants.TUTORIAL_RIGHT_REGION_NAME));

        // Fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_NAME));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        smallFont = generator.generateFont(parameter);
        smallFont.setColor(.21f, .22f, .21f, 1f);
        parameter.size = 72;
        largeFont = generator.generateFont(parameter);
        largeFont.setColor(.21f, .22f, .21f, 1f);
        parameter.size = 24;
        smallestFont = generator.generateFont(parameter);
        smallestFont.setColor(.21f, .22f, .21f, 1f);
        generator.dispose();

    }

    public static TextureRegion getTextureRegion(String key) {
        return texturesMap.get(key);
    }

    public static Animation getAnimation(String key) {
        return animationsMap.get(key);
    }

    private static Animation createAnimation(TextureAtlas textureAtlas, String[] regionNames) {

        TextureRegion[] runningFrames = new TextureRegion[regionNames.length];

        for (int i = 0; i < regionNames.length; i++) {
            String path = regionNames[i];
            runningFrames[i] = textureAtlas.findRegion(path);
        }

        return new Animation(0.1f, runningFrames);

    }

    public static TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public static BitmapFont getSmallFont() {
        return smallFont;
    }

    public static BitmapFont getLargeFont() {
        return largeFont;
    }

    public static BitmapFont getSmallestFont() {
        return smallestFont;
    }

    public static void dispose() {
        textureAtlas.dispose();
        smallestFont.dispose();
        smallFont.dispose();
        largeFont.dispose();
        texturesMap.clear();
        animationsMap.clear();
    }
}
