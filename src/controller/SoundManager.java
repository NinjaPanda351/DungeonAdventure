package controller;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class SoundManager {
    private static SoundManager myInstance;
    private Clip myBackgroundMusic;
    private final HashMap<String, Clip> mySoundEffects;
    private float myBackgroundVolume;
    private float myEffectsVolume;

    private SoundManager() {
        mySoundEffects = new HashMap<>();
        myBackgroundVolume = 60.0f;
        myEffectsVolume = 75.0f;
    }

    public static SoundManager getInstance() {
        if (myInstance == null) {
            myInstance = new SoundManager();
        }

        return myInstance;
    }

    public void playBackgroundMusic(final String theFilePath) {
        stopBackgroundMusic();

        try {
            myBackgroundMusic = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(theFilePath));
            myBackgroundMusic.open(audioInputStream);
            setVolume(myBackgroundMusic, myBackgroundVolume);
            myBackgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            myBackgroundMusic.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopBackgroundMusic() {
        if (myBackgroundMusic != null && myBackgroundMusic.isRunning()) {
            myBackgroundMusic.stop();
            myBackgroundMusic.close();
        }
    }

    public void loadSoundEffect(final String theKey, final String theFilePath) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(theFilePath));
            clip.open(audioInputStream);
            mySoundEffects.put(theKey, clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void playSoundEffect(final String theKey) {
        Clip clip = mySoundEffects.get(theKey);
        if (clip != null) {
            clip.setFramePosition(0);
            setVolume(clip, myEffectsVolume);
            clip.start();
        } else {
            System.err.println("Sound effect not found: " + theKey);
        }
    }

    private void setVolume(final Clip theClip, final float theVolumePercentage) {
        if (theClip != null) {
            FloatControl volumeControl = (FloatControl) theClip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float volume = min + (max - min) * (theVolumePercentage / 100.0f);
            volumeControl.setValue(volume);
        }
    }

    public void setBackgroundVolume(final float theVolumePercentage) {
        myBackgroundVolume = theVolumePercentage;
        if (myBackgroundMusic != null) {
            setVolume(myBackgroundMusic, myBackgroundVolume);
        }
    }

    public void setEffectsVolume(final float theVolumePercentage) {
        myEffectsVolume = theVolumePercentage;
        for (Clip clip : mySoundEffects.values()) {
            setVolume(clip, myEffectsVolume);
        }
    }

    // Stop All Sounds
    public void stopAllSounds() {
        stopBackgroundMusic();
        for (Clip clip : mySoundEffects.values()) {
            if (clip.isRunning()) {
                clip.stop();
            }
        }
    }
}