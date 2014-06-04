package com.gamestudio24.cityescape.actors.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gamestudio24.cityescape.utils.AudioUtils;
import com.gamestudio24.cityescape.utils.Constants;

public class SoundButton extends Actor {

    private Rectangle bounds;
    private TextureRegion textureRegion;

    public SoundButton(Rectangle bounds) {
        this.bounds = bounds;
        loadTextureRegion();
    }

    private void loadTextureRegion() {
        TextureAtlas textureAtlas = new TextureAtlas(Constants.SPRITES_ATLAS_PATH);
        textureRegion = textureAtlas.findRegion(AudioUtils.getSoundRegionName());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public void toggle() {
        AudioUtils.toggleSound();
        loadTextureRegion();
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
