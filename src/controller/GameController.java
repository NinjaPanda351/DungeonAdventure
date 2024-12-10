package controller;

import model.DungeonCharacters.Hero;
import model.DungeonCharacters.Monster;
import model.DungeonManager.Dungeon;
import model.DungeonManager.Room;
import model.Player.Player;
import model.PlayerInventory.Inventory;
import model.PlayerInventory.Item;
import view.CardLayoutManager;
import view.UI;

import java.awt.*;

public class GameController {
    private Player myPlayer;
    private Dungeon myDungeon;
    private Inventory myInventory;
    private UI myUI;
    private CardLayoutManager myCardLayoutManager;
    private CombatController myCombatController;

    public GameController(final Player thePlayer, final Dungeon theDungeon, final Inventory theInventory) {
        myPlayer = thePlayer;
        myDungeon = theDungeon;
        myInventory = theInventory;
    }

    public void update() {
        if ("CombatPanel".equals(myCardLayoutManager.getCurrentPanel())) {
            // Do nothing if the combat panel is active
            return;
        }
        if (myUI.getGameScreen().isMyInventoryVisible()) {
            myUI.getGameScreen().handleInventoryNavigation(InputListener.getInstance());
        } else {
            myPlayer.update();
            myDungeon.checkDoorCollisions(myPlayer, this);
            
            Monster collidedMonster = myDungeon.getMyCurrentRoom().checkPlayerCollisionWithMonsters(myPlayer);
            Room currentRoom = null;
            if (collidedMonster != null) {
                // start combat with monster
                myCombatController.startCombat(collidedMonster);
                myCardLayoutManager.switchToCombatPanel();
                System.out.println("COLLISION");
                myDungeon.getMyCurrentRoom().removeMonster(collidedMonster);
            }
        }
    }

    public void setCardLayoutManager(final CardLayoutManager theCardLayoutManager) {
        myCardLayoutManager = theCardLayoutManager;
    }

    public void setCombatController(final CombatController theCombatController) {
        myCombatController = theCombatController;
    }

    public void draw(final Graphics2D theGraphics2D) {
        myDungeon.getMyCurrentRoom().draw(theGraphics2D);
        myPlayer.draw(theGraphics2D);
        myUI.drawGameHUD(theGraphics2D);
    }

    /*
    public void useItem(Item theItem) {
        if (theItem != null) {
            myInventory.useItem(theItem, );
        }
    }
    */


    public void setUI(final UI theUI) {
        myUI = theUI;
    }

    public void setMyPlayer(final Player thePlayer) {
        this.myPlayer = thePlayer;
    }

    public void setMyDungeon(final Dungeon theDungeon) {
        this.myDungeon = theDungeon;
    }

    public void setMyInventory(final Inventory theInventory) {
        this.myInventory = theInventory;
    }

    public Player getPlayer() {
        return myPlayer;
    }

    public Dungeon getDungeon() {
        return myDungeon;
    }

    public Inventory getInventory() {
        return myInventory;
    }
}
