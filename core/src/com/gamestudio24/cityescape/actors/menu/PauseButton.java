package com.gamestudio24.cityescape.actors.menu;

import com.badlogic.gdx.math.Rectangle;
import com.gamestudio24.cityescape.enums.GameState;
import com.gamestudio24.cityescape.stages.GameStage;
import com.gamestudio24.cityescape.utils.Constants;

public class PauseButton extends GameButton implements GameStage.GameListener {

    public interface PauseButtonListener {
        public void onPause();

        public void onResume();
    }

    private PauseButtonListener listener;
    private GameState gameState;

    public PauseButton(Rectangle bounds, PauseButtonListener listener) {
        super(bounds);
        this.listener = listener;
    }

    @Override
    protected String getRegionName() {
        return gameState == GameState.PAUSED ? Constants.PLAY_REGION_NAME : Constants.PAUSE_REGION_NAME;
    }

    @Override
    public void touched() {
        if (gameState == GameState.PAUSED) {
            listener.onResume();
        } else {
            listener.onPause();
        }
    }

    @Override
    public void onGameStateChange(GameState newState) {
        if (newState == GameState.OVER) {
            remove();
        }
        gameState = newState;
    }

}
