package com.gamestudio24.cityescape;

import com.badlogic.gdx.Game;
import com.gamestudio24.cityescape.screens.GameScreen;

public class CityEscape extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen());
    }

    @Override
    public void render() {

    }
}
