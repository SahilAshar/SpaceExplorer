/**
 * @(#)Craft.java
 *
 *
 * @author
 * @version 1.00 2014/5/21
 */


package space;


import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.ImageIcon;

import java.util.ArrayList;

public class Craft
{

    private String craft = "craft.png";

    private int dx;
    private int dy;
    private int x;
    private int y;
    private Image image;
    boolean isVisible;

    private final int CRAFT_SIZE = 20;
    private ArrayList missiles;

    public Craft() {
      	ImageIcon ii = new ImageIcon("resources/dogeCraft.png");
		image = ii.getImage();
		missiles = new ArrayList<>();
        x = 40;
        y = 60;
        isVisible = true;
    }


    public void move() {
        x += dx;
        y += dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void inVisible()
    {
    	isVisible = false;
    }

    public Image getImage() {
        return image;
    }

    public ArrayList getMissiles() {
    	return missiles;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

//      if (key == KeyEvent.VK_LEFT) {
//            dx = -1;
//      }
//
//      if (key == KeyEvent.VK_RIGHT) {
//            dx = 1;
//      }

		if (key == KeyEvent.VK_SPACE){
			fire();
		}
        if (key == KeyEvent.VK_UP) {
            dy = -1;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 1;
        }
    }

    public void fire(){
    	missiles.add(new Missile(x + CRAFT_SIZE, y + CRAFT_SIZE/2));
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

//        if (key == KeyEvent.VK_LEFT) {
//            dx = 0;
//        }
//
//        if (key == KeyEvent.VK_RIGHT) {
//            dx = 0;
//        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}