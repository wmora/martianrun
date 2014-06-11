package com.gamestudio24.cityescape.actors.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gamestudio24.cityescape.enums.GameState;
import com.gamestudio24.cityescape.utils.AssetsManager;
import com.gamestudio24.cityescape.utils.Constants;
import com.gamestudio24.cityescape.utils.GameManager;

public class PausedLabel extends Actor {

    private Rectangle bounds;
    private BitmapFont font;

    public PausedLabel(Rectangle bounds) {
        this.bounds = bounds;
        setWidth(bounds.width);
        setHeight(bounds.height);
        font = AssetsManager.getSmallFont();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (GameManager.getInstance().getGameState() == GameState.PAUSED) {
            font.drawWrapped(batch, Constants.PAUSED_LABEL, bounds.x, bounds.y, bounds.width,
                    BitmapFont.HAlignment.CENTER);
        }
    }

}
