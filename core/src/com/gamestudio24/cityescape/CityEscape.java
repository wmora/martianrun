package com.gamestudio24.cityescape;

import com.badlogic.gdx.Game;
import com.gamestudio24.cityescape.screens.GameScreen;
import com.gamestudio24.cityescape.utils.GameEventListener;
import com.gamestudio24.cityescape.utils.GameManager;

public class CityEscape extends Game {

    public CityEscape(GameEventListener listener) {
        GameManager.getInstance().setGameEventListener(listener);
    }

    @Override
    public void create() {
        setScreen(new GameScreen());
    }

}
