package model.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<Item> myItems;

    public Inventory() {
        myItems = new ArrayList<>();
    }

    public void addItem(Item theItem) {
        myItems.add(theItem);
        System.out.println(theItem.getName() + " added to inventory.");
    }

    public void removeItem(Item theItem) {
        myItems.remove(theItem);
        System.out.println(theItem.getName() + " removed from inventory.");
    }

    public List<Item> getItems() {
        return myItems;
    }

    public Item getItem(String theName) {
        for (Item item : myItems) {
            if (item.getName().equalsIgnoreCase(theName)) {
                return item;
            }
        }
        return null;
    }

    public void displayInventory() {
        System.out.println("Inventory: ");
        for (Item item : myItems) {
            System.out.println("- " + item.getName() + ": " + item.getMyDescription());
        }
    }

    // additional methods for using items/organizing.
}