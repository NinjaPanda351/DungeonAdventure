package view;

import controller.GameController;
import model.AnimationSystem.AssetManager;
import model.PlayerInventory.Item;

import java.awt.*;
import java.util.List;

import static model.GameConfig.TILE_SIZE;

public class GameScreen {
    private final GameController myGameController;

    private final UIButton myMapButton;
    private final UIButton myInventoryButton;

    private final UIButton myFirstPillarSlot;
    //private final UIButton mySecondPillerSlot;
    //private final UIButton myThirdPillarSlot;
    //private final UIButton myFourthPillerSlot;

    private boolean isMapVisible;
    private boolean isInventoryVisible;

    public GameScreen(final AssetManager theAssetManager, final GameController theGameController) {
        this.myGameController = theGameController;

        myMapButton = new UIButton(theAssetManager.getAsset("mapButton"), new Rectangle(412, 344, 60, 60));
        myInventoryButton = new UIButton(theAssetManager.getAsset("inventoryButton"), new Rectangle(478, 350, 48, 48));

        isMapVisible = false;
        isInventoryVisible = false;

        myFirstPillarSlot = new UIButton(theAssetManager.getAsset("abstractionImage"), new Rectangle(TILE_SIZE * 9, 215, TILE_SIZE, TILE_SIZE));
    }

    public void draw(final Graphics2D theGraphics2D) {
        myMapButton.draw(theGraphics2D);
        myInventoryButton.draw(theGraphics2D);

        if (isMapVisible) drawMapScreen(theGraphics2D);
        if (isInventoryVisible) drawInventoryScreen(theGraphics2D);
    }

    public void handleHoverUpdate(final Point theMousePoint) {
        myMapButton.setHovered(myMapButton.contains(theMousePoint));
        myInventoryButton.setHovered(myInventoryButton.contains(theMousePoint));

        if (isInventoryVisible) {
            myFirstPillarSlot.setHovered(myFirstPillarSlot.contains(theMousePoint));

        }
    }

    public void handleClick(final Point theClickPoint) {
        if (myMapButton.contains(theClickPoint)) {
            toggleMapScreen();
        }
        if (myInventoryButton.contains(theClickPoint)) {
            toggleInventoryScreen();
        }
    }

    private void drawMapScreen(final Graphics2D theGraphics2D) {
        drawSubWindow(TILE_SIZE, TILE_SIZE,TILE_SIZE * 7, TILE_SIZE * 7, theGraphics2D);
    }

    private void drawInventoryScreen(final Graphics2D theGraphics2D) {
        drawSubWindow(TILE_SIZE * 8, TILE_SIZE, TILE_SIZE * 7, TILE_SIZE * 7, theGraphics2D);
        drawSubWindow(TILE_SIZE * 8, TILE_SIZE * 8, TILE_SIZE * 7, TILE_SIZE * 3, theGraphics2D);

        List<Item> items = myGameController.getInventory().getItems();

        int itemY = TILE_SIZE + 20;
        for (Item item : items) {
            theGraphics2D.drawString(item.getName(), TILE_SIZE * 8 + 10, itemY);
            theGraphics2D.drawString(item.getMyDescription(), TILE_SIZE * 8 + 10, itemY + 15);
            itemY += 40;
        }
    }

    private void toggleMapScreen() {
        isMapVisible = !isMapVisible;
    }

    private void toggleInventoryScreen() {
        isInventoryVisible = !isInventoryVisible;
    }

    private void drawSubWindow(final int theX, final int theY, final int theWidth, final int theHeight, final Graphics2D theGraphics2D) {
        theGraphics2D.setColor(new Color(0, 0, 0, 210));
        theGraphics2D.fillRoundRect(theX, theY, theWidth, theHeight, 35, 35);
        theGraphics2D.setColor(Color.WHITE);
        theGraphics2D.setStroke(new BasicStroke(5));
        theGraphics2D.drawRoundRect(theX + 5, theY + 5, theWidth - 10, theHeight - 10, 25, 25);
    }
}
