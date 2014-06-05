package com.gamestudio24.cityescape.actors.menu;

import com.badlogic.gdx.math.Rectangle;
import com.gamestudio24.cityescape.enums.GameState;
import com.gamestudio24.cityescape.utils.Constants;
import com.gamestudio24.cityescape.utils.GameStateManager;

public class PauseButton extends GameButton {

    public interface PauseButtonListener {
        public void onPause();

        public void onResume();
    }

    private PauseButtonListener listener;

    public PauseButton(Rectangle bounds, PauseButtonListener listener) {
        super(bounds);
        this.listener = listener;
    }

    @Override
    protected String getRegionName() {
        return GameStateManager.getInstance().getGameState() == GameState.PAUSED ? Constants.PLAY_REGION_NAME : Constants.PAUSE_REGION_NAME;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (GameStateManager.getInstance().getGameState() == GameState.OVER) {
            remove();
        }
    }

    @Override
    public void touched() {
        if (GameStateManager.getInstance().getGameState() == GameState.PAUSED) {
            listener.onResume();
        } else {
            listener.onPause();
        }
    }

}
