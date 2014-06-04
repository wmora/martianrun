package com.gamestudio24.cityescape.actors.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gamestudio24.cityescape.utils.Constants;

public abstract class Button extends Actor {

    protected TextureAtlas textureAtlas;
    protected Rectangle bounds;
    protected TextureRegion textureRegion;

    public Button(Rectangle bounds) {
        this.bounds = bounds;
        textureAtlas = new TextureAtlas(Constants.SPRITES_ATLAS_PATH);
        loadTextureRegion();
    }

    protected void loadTextureRegion() {
        textureRegion = textureAtlas.findRegion(getRegionName());
    }

    protected abstract String getRegionName();

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public abstract void touched();

    public Rectangle getBounds() {
        return bounds;
    }

}
