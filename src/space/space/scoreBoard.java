/**
 * @(#)scoreBoard.java
 *
 *
 * @author
 * @version 1.00 2014/5/22
 */
package space;

public class scoreBoard {

	public int health;
    public int ammo;
    public int score;

    public scoreBoard()
    {
    	health = 100;
    	ammo = 50;
    	score = 0;
    }

    public static int getScore()
    {
    	return score;
    }

    public static int getHealth()
    {
    	return health;
    }

    public static int getAmmo()
    {
    	return ammo;
    }

}