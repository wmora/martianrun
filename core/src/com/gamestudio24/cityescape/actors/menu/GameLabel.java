package com.gamestudio24.cityescape.actors.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gamestudio24.cityescape.utils.AssetsManager;
import com.gamestudio24.cityescape.utils.Constants;

public class GameLabel extends Actor {

    private Rectangle bounds;
    private BitmapFont font;

    public GameLabel(Rectangle bounds) {
        this.bounds = bounds;
        setWidth(bounds.width);
        setHeight(bounds.height);
        font = AssetsManager.getLargeFont();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        font.drawWrapped(batch, Constants.GAME_NAME, bounds.x, bounds.y, bounds.width, BitmapFont.HAlignment.CENTER);
    }

}
