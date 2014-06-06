package com.gamestudio24.cityescape.actors.menu;

import com.badlogic.gdx.math.Rectangle;
import com.gamestudio24.cityescape.enums.GameState;
import com.gamestudio24.cityescape.utils.Constants;
import com.gamestudio24.cityescape.utils.GameStateManager;

public class StartButton extends GameButton {

    public interface StartButtonListener {
        public void onStart();
    }

    private StartButtonListener listener;

    public StartButton(Rectangle bounds, StartButtonListener listener) {
        super(bounds);
        this.listener = listener;
    }

    @Override
    protected String getRegionName() {
        return Constants.BIG_PLAY_REGION_NAME;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (GameStateManager.getInstance().getGameState() != GameState.OVER) {
            remove();
        }
    }

    @Override
    public void touched() {
        listener.onStart();
    }

}
