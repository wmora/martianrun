package com.gamestudio24.cityescape.actors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gamestudio24.cityescape.box2d.UserData;
import com.gamestudio24.cityescape.enums.GameState;
import com.gamestudio24.cityescape.stages.GameStage;
import com.gamestudio24.cityescape.utils.Constants;

public abstract class GameActor extends Actor implements GameStage.GameListener {

    protected Body body;
    protected UserData userData;
    protected Rectangle screenRectangle;
    protected GameState gameState;

    public GameActor(Body body) {
        this.body = body;
        this.userData = (UserData) body.getUserData();
        screenRectangle = new Rectangle();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (gameState == GameState.PAUSED) {
            return;
        }

        if (body.getUserData() != null) {
            updateRectangle();
        } else {
            // This means the world destroyed the body (enemy or runner went out of bounds)
            remove();
        }

    }

    public abstract UserData getUserData();

    private void updateRectangle() {
        screenRectangle.x = transformToScreen(body.getPosition().x - userData.getWidth() / 2);
        screenRectangle.y = transformToScreen(body.getPosition().y - userData.getHeight() / 2);
        screenRectangle.width = transformToScreen(userData.getWidth());
        screenRectangle.height = transformToScreen(userData.getHeight());
    }

    protected float transformToScreen(float n) {
        return Constants.WORLD_TO_SCREEN * n;
    }

    @Override
    public void onGameStateChange(GameState newState) {
        gameState = newState;
    }

}
