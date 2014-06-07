package com.gamestudio24.cityescape.utils;

import com.gamestudio24.cityescape.enums.GameState;

public class GameManager {
    private static GameManager ourInstance = new GameManager();

    private GameState gameState;

    public static GameManager getInstance() {
        return ourInstance;
    }

    private GameManager() {
        gameState = GameState.RUNNING;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

}
