package com.gamestudio24.cityescape.actors.menu;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Rectangle;
import com.gamestudio24.cityescape.utils.AudioUtils;
import com.gamestudio24.cityescape.utils.Constants;

public class MusicButton extends GameButton {

    private Music music;


    public MusicButton(Rectangle bounds) {
        super(bounds);
        music = AudioUtils.createMusic(Constants.GAME_MUSIC);
        playMusic();
    }

    protected String getRegionName() {
        return AudioUtils.getMusicRegionName();
    }

    public void touched() {
        if (music.isPlaying()) {
            AudioUtils.pauseMusic(music);
        }
        AudioUtils.toggleMusic();
        playMusic();
    }

    private void playMusic() {
        AudioUtils.playMusic(music);
    }

}
