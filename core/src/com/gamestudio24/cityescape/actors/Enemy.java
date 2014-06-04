package com.gamestudio24.cityescape.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.gamestudio24.cityescape.box2d.EnemyUserData;
import com.gamestudio24.cityescape.enums.GameState;
import com.gamestudio24.cityescape.utils.Constants;

public class Enemy extends GameActor {

    private Animation animation;
    private float stateTime;

    public Enemy(Body body) {
        super(body);
        TextureAtlas textureAtlas = new TextureAtlas(Constants.SPRITES_ATLAS_PATH);
        TextureRegion[] runningFrames = new TextureRegion[getUserData().getTextureRegions().length];
        for (int i = 0; i < getUserData().getTextureRegions().length; i++) {
            String path = getUserData().getTextureRegions()[i];
            runningFrames[i] = textureAtlas.findRegion(path);
        }
        animation = new Animation(0.1f, runningFrames);
        stateTime = 0f;
    }

    @Override
    public EnemyUserData getUserData() {
        return (EnemyUserData) userData;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setLinearVelocity(getUserData().getLinearVelocity());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (gameState == GameState.RUNNING) {
            stateTime += Gdx.graphics.getDeltaTime();
        }

        batch.draw(animation.getKeyFrame(stateTime, true), (screenRectangle.x - (screenRectangle.width * 0.1f)),
                screenRectangle.y, screenRectangle.width * 1.2f, screenRectangle.height * 1.1f);
    }
}
