package com.gamestudio24.cityescape.actors.menu;

import com.badlogic.gdx.math.Rectangle;
import com.gamestudio24.cityescape.utils.AudioUtils;

public class SoundButton extends GameButton {

    public SoundButton(Rectangle bounds) {
        super(bounds);
    }

    @Override
    protected String getRegionName() {
        return AudioUtils.getSoundRegionName();
    }

    @Override
    public void touched() {
        AudioUtils.toggleSound();
    }

}
