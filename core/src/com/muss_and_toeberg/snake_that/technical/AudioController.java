package com.muss_and_toeberg.snake_that.technical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.muss_and_toeberg.snake_that.screens.Settings;

/**
 * controls everything related to sounds and music
 * All sounds were done by Eric Matyas:
 * <a href="http://soundimage.org/wp-content/uploads/2016/08/Magical-Getaway_Looping.mp3">
 *     background</a>
 * <a href="http://soundimage.org/wp-content/uploads/2016/04/UI_Quirky1.mp3">pointsGet</a>
 * <a href="http://soundimage.org/wp-content/uploads/2016/04/Explosion1.mp3">explosion</a>
 * <a href="http://soundimage.org/wp-content/uploads/2016/04/Laser-Ricochet3.mp3">loosingALive</a>
 * <a href="http://soundimage.org/wp-content/uploads/2016/04/SynthChime6.mp3">gainingALive</a>
 * @author Niclas Muss
 */
public class AudioController {
    // music & sound objects
    private Music backgroundMusic;
    private Sound pointsGet;
    private Sound explosion;
    private Sound loosingALive;
    private Sound gainingALive;

    /**
     * Constructor which creates the music and sound objects
     */
    public AudioController() {
        backgroundMusic = Gdx.audio.newMusic(
                Gdx.files.internal("audio/background.mp3"));
        backgroundMusic.setLooping(true);
        startBackgroundMusic();

        pointsGet = Gdx.audio.newSound(
                Gdx.files.internal("audio/pointCoin.mp3"));
        explosion = Gdx.audio.newSound(
                Gdx.files.internal("audio/explosion.mp3"));
        loosingALive = Gdx.audio.newSound(
                Gdx.files.internal("audio/looseALive.mp3"));
        gainingALive = Gdx.audio.newSound(
                Gdx.files.internal("audio/gainALive.mp3"));
    }

    /**
     * starts or resumes the background music
     */
    public void startBackgroundMusic() {
        if(!Settings.checkMusicTurnedOn()) {
            return;
        }
        backgroundMusic.play();
    }

    /**
     * pauses the background music
     */
    public void pauseBackgroundMusic() {
        backgroundMusic.pause();
    }

    /**
     * plays the point sound
     */
    public void playPointsSound() {
        if(!Settings.checkSoundsTurnedOn()) {
            return;
        }
        pointsGet.play();
    }

    /**
     * plays the explosion
     */
    public void playExplosionSound() {
        if(!Settings.checkSoundsTurnedOn()) {
            return;
        }
        explosion.play();
    }

    /**
     * plays the sound when the snake looses a live
     */
    public void playLiveLoosingSound() {
        if(!Settings.checkSoundsTurnedOn()) {
            return;
        }
        loosingALive.play();
    }

    /**
     * plays the sound when the snake gains a live
     */
    public void playLiveGainingSound() {
        if(!Settings.checkSoundsTurnedOn()) {
            return;
        }
        gainingALive.play();
    }
}
