package controller;

import utilities.GameConfig;
import utilities.SoundManager;
import view.UI;

import java.awt.*;

public class GameStateManager {
    public enum State {
        MENU,
        GAME,
        PAUSE,
        COMBAT,
        OPTION
    }

    private final GameController myGameController;
    private SoundManager mySoundManager;
    private UI myUI;

    private State myCurrentState;

    public GameStateManager(final GameController theGameController) {
        myGameController = theGameController;
        mySoundManager = SoundManager.getInstance();
        myCurrentState = State.MENU;
        onEnterState(State.MENU);
    }

    public State getCurrentState() { return myCurrentState; }

    public void setState(final State theNewState) {
        if (theNewState != myCurrentState) {
            onExitState(myCurrentState);
            myCurrentState = theNewState;
            onEnterState(theNewState);
        }
    }

    private void onEnterState(final State theState) {
        switch (myCurrentState) {
            case MENU:
                mySoundManager.playBackgroundMusic(GameConfig.MENU_THEME);
                break;
            case GAME:
                mySoundManager.playBackgroundMusic(GameConfig.GAME_THEME);
                break;
            case PAUSE:
                mySoundManager.playSoundEffect(GameConfig.PAUSE_SOUND);
                break;
            case COMBAT:
                mySoundManager.playBackgroundMusic(GameConfig.COMBAT_THEME);
                break;
            case OPTION:
                mySoundManager.playSoundEffect(GameConfig.PAUSE_SOUND);
                mySoundManager.playBackgroundMusic(GameConfig.MENU_THEME);
                break;
        }
    }

    private void onExitState(final State theState) {
        mySoundManager.stopBackgroundMusic();
        switch (myCurrentState) {
            case MENU:
                break;
            case GAME:
                break;
            case PAUSE:
                break;
            case COMBAT:
                break;
            case OPTION:
                break;
        }
    }

    public void update() {
        handleInput();

        switch (myCurrentState) {
            case MENU:
                break;
            case GAME:
                myGameController.update();
                break;
            case PAUSE:
                break;
            case COMBAT:
                break;
            case OPTION:
                break;
        }
    }

    public void paint(final Graphics2D theGraphics2D) {
        switch (myCurrentState) {
            case MENU:
                myUI.drawTitleScreen(theGraphics2D);
                break;
            case GAME:
                myGameController.draw(theGraphics2D);
                break;
            case PAUSE:
                myGameController.draw(theGraphics2D);
                myUI.drawPauseScreen(theGraphics2D);
                break;
            case COMBAT:
                break;
            case OPTION:
                break;
        }
    }

    private void handleInput() {
        if (InputListener.getInstance().isPauseJustPressed()) {
            if (myCurrentState == State.PAUSE) {
                setState(State.GAME); // Resume the game
            } else if (myCurrentState == State.GAME) {
                setState(State.PAUSE); // Pause the game
            }
        }
    }

    public void setUI(final UI theUI) {
        myUI = theUI;
    }
}