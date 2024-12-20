package view;

import controller.GameStateManager;
import model.AnimationSystem.AssetManager;
import controller.SoundManager;

import java.awt.*;

import static model.GameConfig.SCREEN_HEIGHT;
import static model.GameConfig.SCREEN_WIDTH;

/**
 * This is the PauseScreen Class.
 */
public class PauseScreen implements Screen{
    private static final int SLIDER_WIDTH = 200;
    private static final int SLIDER_HEIGHT = 10;
    private int myBackgroundMusicVolume = 50;
    private int mySFXVolume = 50;
    private Rectangle myBgmSliderBounds, mySfxSliderBounds;
    private final UIButton mySaveButton;
    private final UIButton myLoadButton;
    private final GameStateManager myGameStateManager;
    private final UIButton myBackButton;

    /**
     * Pause Screen Constructor.
     *
     * @param theAssetManager AssetManager.
     * @param theGameStateManager GameStateManager.
     */
    public PauseScreen(final AssetManager theAssetManager, final GameStateManager theGameStateManager) {
        this.myGameStateManager = theGameStateManager;
        mySaveButton = new UIButton(theAssetManager.getAsset("saveButton"), new Rectangle(10, 10, 55, 55));
        myLoadButton = new UIButton(theAssetManager.getAsset("loadButton"), new Rectangle(70, 10, 55, 55));
        myBackButton = new UIButton(theAssetManager.getAsset("backButton"), new Rectangle(25, 325, 70, 70));
    }

    /**
     * This method draws the screen.
     *
     * @param theGraphics2D Graphics.
     */
    public void draw(final Graphics2D theGraphics2D) {
        theGraphics2D.setColor(new Color(0, 0, 0, 150));
        theGraphics2D.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        theGraphics2D.setColor(Color.WHITE);
        theGraphics2D.setFont(new Font("Arial", Font.BOLD, 48));
        theGraphics2D.drawString("PAUSED", SCREEN_WIDTH / 2 - 100, SCREEN_HEIGHT / 2 - 100);

        drawVolumeOptions(theGraphics2D);

        mySaveButton.draw(theGraphics2D);
        myLoadButton.draw(theGraphics2D);
        myBackButton.draw(theGraphics2D);
    }

    /**
     * This method handles clicking.
     *
     * @param theClickPoint Clicked point.
     */
    public void handleClick(final Point theClickPoint) {
        if (myBgmSliderBounds.contains(theClickPoint)) {
            int relativeX = theClickPoint.x - myBgmSliderBounds.x;
            myBackgroundMusicVolume = Math.min(100, Math.max(0, (relativeX * 100) / myBgmSliderBounds.width));
            SoundManager.getInstance().setBackgroundVolume(myBackgroundMusicVolume);
        }

        if (mySfxSliderBounds.contains(theClickPoint)) {
            int relativeX = theClickPoint.x - mySfxSliderBounds.x;
            mySFXVolume = Math.min(100, Math.max(0, (relativeX * 100) / mySfxSliderBounds.width));
            SoundManager.getInstance().setEffectsVolume(mySFXVolume);
        }

        if (mySaveButton.contains(theClickPoint)) {
            myGameStateManager.setState(GameStateManager.State.SAVE);
        }
        if (myLoadButton.contains(theClickPoint)) {
            myGameStateManager.setState(GameStateManager.State.LOAD);
        }
        if (myBackButton.contains(theClickPoint)) {
            myGameStateManager.setState(GameStateManager.State.MENU);
        }
    }

    /**
     * This method handles hovering.
     *
     * @param theMousePoint Hovering point.
     */
    public void handleHoverUpdate(final Point theMousePoint) {
        mySaveButton.setHovered(mySaveButton.contains(theMousePoint));
        myLoadButton.setHovered(myLoadButton.contains(theMousePoint));
        myBackButton.setHovered(myBackButton.contains(theMousePoint));
    }

    /**
     * This method draws volume options.
     *
     * @param theGraphics2D Graphics.
     */
    private void drawVolumeOptions(final Graphics2D theGraphics2D) {
        int x = SCREEN_WIDTH / 2 - SLIDER_WIDTH / 2;
        int y = SCREEN_HEIGHT / 2;

        drawSlider(x, y, myBackgroundMusicVolume, "BGM Volume", theGraphics2D);
        myBgmSliderBounds = new Rectangle(x, y, SLIDER_WIDTH, SLIDER_HEIGHT);

        int sfxY = y + 60;
        drawSlider(x, sfxY, mySFXVolume, "SFX Volume", theGraphics2D);
        mySfxSliderBounds = new Rectangle(x, sfxY, SLIDER_WIDTH, SLIDER_HEIGHT);
    }

    /**
     * This method draws the sliders.
     *
     * @param theX X position of sliders.
     * @param theY Y position of sliders.
     * @param theVolume Volume level.
     * @param theLabel Volume Label
     * @param theGraphics2D Graphics
     */
    private void drawSlider(final int theX, final int theY, final int theVolume, final String theLabel, final Graphics2D theGraphics2D) {
        theGraphics2D.setColor(Color.DARK_GRAY);
        theGraphics2D.fillRect(theX, theY, SLIDER_WIDTH, SLIDER_HEIGHT);

        int knobX = theX + (SLIDER_WIDTH * theVolume / 100) - 5;
        theGraphics2D.setColor(Color.WHITE);
        theGraphics2D.fillOval(knobX, theY - 5, 10, SLIDER_HEIGHT + 10);

        theGraphics2D.setFont(new Font("Arial", Font.PLAIN, 18));
        theGraphics2D.setColor(Color.WHITE);
        theGraphics2D.drawString(theLabel + ": " + theVolume + "%", theX, theY - 20);
    }
}
