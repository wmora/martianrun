package com.gamestudio24.cityescape.enums;

import com.gamestudio24.cityescape.utils.Constants;

public enum EnemyType {

    RUNNING_SMALL(1f, 1f, Constants.ENEMY_X, Constants.RUNNING_SHORT_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.RUNNING_SMALL_ENEMY_ASSETS_ID),
    RUNNING_WIDE(2f, 1f, Constants.ENEMY_X, Constants.RUNNING_SHORT_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.RUNNING_WIDE_ENEMY_ASSETS_ID),
    RUNNING_LONG(1f, 2f, Constants.ENEMY_X, Constants.RUNNING_LONG_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.RUNNING_LONG_ENEMY_ASSETS_ID),
    RUNNING_BIG(2f, 2f, Constants.ENEMY_X, Constants.RUNNING_LONG_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.RUNNING_BIG_ENEMY_ASSETS_ID),
    FLYING_SMALL(1f, 1f, Constants.ENEMY_X, Constants.FLYING_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.FLYING_SMALL_ENEMY_ASSETS_ID),
    FLYING_WIDE(2f, 1f, Constants.ENEMY_X, Constants.FLYING_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.FLYING_WIDE_ENEMY_ASSETS_ID);

    private float width;
    private float height;
    private float x;
    private float y;
    private float density;
    private String animationAssetsId;

    EnemyType(float width, float height, float x, float y, float density, String animationAssetsId) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.density = density;
        this.animationAssetsId = animationAssetsId;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getDensity() {
        return density;
    }

    public String getAnimationAssetId() {
        return animationAssetsId;
    }
}
