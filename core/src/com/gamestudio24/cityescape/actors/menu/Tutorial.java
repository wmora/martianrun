package com.gamestudio24.cityescape.actors.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.gamestudio24.cityescape.enums.GameState;
import com.gamestudio24.cityescape.utils.AssetsManager;
import com.gamestudio24.cityescape.utils.GameManager;


public class Tutorial extends Actor {

    private TextureRegion textureRegion;
    private Rectangle bounds;
    private BitmapFont font;
    private String text;

    public Tutorial(Rectangle bounds, String assetsId, String text) {
        this.bounds = bounds;
        this.text = text;
        textureRegion = AssetsManager.getTextureRegion(assetsId);
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.delay(4f));
        sequenceAction.addAction(Actions.removeActor());
        addAction(sequenceAction);
        font = AssetsManager.getSmallestFont();
        setWidth(bounds.width);
        setHeight(bounds.height);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (GameManager.getInstance().getGameState() == GameState.OVER) {
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, bounds.x, bounds.y, bounds.width, bounds.height);
        font.drawWrapped(batch, text, bounds.x, bounds.y, bounds.width,
                BitmapFont.HAlignment.CENTER);
    }
}
