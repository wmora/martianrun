package com.gamestudio24.cityescape.utils;

import com.gamestudio24.cityescape.enums.Difficulty;
import com.gamestudio24.cityescape.enums.GameState;

public class GameManager {
    private static GameManager ourInstance = new GameManager();

    private GameState gameState;
    private Difficulty difficulty;

    public static GameManager getInstance() {
        return ourInstance;
    }

    private GameManager() {
        gameState = GameState.OVER;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isMaxDifficulty() {
        return difficulty == Difficulty.values()[Difficulty.values().length - 1];
    }

    public void resetDifficulty() {
        setDifficulty(Difficulty.values()[0]);
    }
}
