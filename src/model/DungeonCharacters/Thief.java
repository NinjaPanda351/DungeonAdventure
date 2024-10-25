package model.DungeonCharacters;

import java.util.Random;

public class Thief extends Hero {
    public Thief(final String theName) {
        this.myName = theName;
        this.myHitPoints = 75;
        this.myAttackSpeed = 6;
        this.myChanceToHit = 0.8;
        this.myChanceToBlock = 0.4;
        this.myMinDamage = 20;
        this.myMaxDamage = 40;
    }

    @Override
    public void attack(DungeonCharacter theTarget) {
        Random rand = new Random();
        if ((double) (rand.nextInt(10) + 1)/10 <= myChanceToHit) {
            System.out.println(myName + " performed an attack!"); //Replace with however we'll announce it succeeded
            int damage = calculateDamage(myMinDamage, myMaxDamage);
            theTarget.takeDamage(damage);
        } else {
            System.out.println("MISSED!"); //Replace with however we'll announce it missed
        }
    }

    @Override
    public void useSpecialSkill(DungeonCharacter theTarget) {
        Random rand = new Random();
        if (rand.nextInt(10) + 1 <= 4) {
            attack(theTarget);
            attack(theTarget); //Replace with get another turn
            System.out.println(myName + " used Surprise Attack!"); //Replace with however we'll announce it succeeded
        } else if (rand.nextInt(10) + 1 <= 8) {
            attack(theTarget);
            System.out.println("Half Success?"); //Replace with however we'll announce it sort of succeeded
        } else {
            System.out.println(myName + " was caught!"); //Replace with however we'll announce it failed
        }
    }

    private int calculateDamage(final int theMinDamage, final int theMaxDamage) {
        return theMinDamage + (int) (Math.random() * ((theMaxDamage - theMinDamage) + 1));
    }
}
