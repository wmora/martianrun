package com.gamestudio24.cityescape.utils;

import com.gamestudio24.cityescape.enums.GameState;

public class GameStateManager {
    private static GameStateManager ourInstance = new GameStateManager();

    private GameState gameState;

    public static GameStateManager getInstance() {
        return ourInstance;
    }

    private GameStateManager() {
        gameState = GameState.RUNNING;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

}
