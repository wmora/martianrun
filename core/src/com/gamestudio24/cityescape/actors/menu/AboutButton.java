package com.gamestudio24.cityescape.actors.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.gamestudio24.cityescape.utils.Constants;

public class AboutButton extends GameButton {

    public interface AboutButtonListener {
        public void onAbout();
    }

    private AboutButtonListener listener;

    public AboutButton(Rectangle bounds, AboutButtonListener listener) {
        super(bounds);
        this.listener = listener;
    }

    @Override
    protected String getRegionName() {
        return Constants.ABOUT_REGION_NAME;
    }

    @Override
    public void touched() {
        listener.onAbout();
    }

}
