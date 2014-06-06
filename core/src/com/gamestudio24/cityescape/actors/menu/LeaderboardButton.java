package com.gamestudio24.cityescape.actors.menu;

import com.badlogic.gdx.math.Rectangle;
import com.gamestudio24.cityescape.enums.GameState;
import com.gamestudio24.cityescape.utils.Constants;
import com.gamestudio24.cityescape.utils.GameStateManager;

public class LeaderboardButton extends GameButton {

    public interface LeaderboardButtonListener {
        public void onLeaderboard();
    }

    private LeaderboardButtonListener listener;

    public LeaderboardButton(Rectangle bounds, LeaderboardButtonListener listener) {
        super(bounds);
        this.listener = listener;
    }

    @Override
    protected String getRegionName() {
        return Constants.LEADERBOARD_REGION_NAME;
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
        listener.onLeaderboard();
    }

}
