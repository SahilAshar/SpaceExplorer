package com.company;

/**
 * Created by Sahil on 5/23/2014.
 * Version 1.0.0
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

class Craft
{

    // private int dx;
    private int dy;
    private int x;
    private int y;
    private final Image image;

    private final ArrayList<Missile> missiles;


    public Craft() {
        ImageIcon ii = new ImageIcon("resources/dogeCraft.png");
        image = ii.getImage();
        missiles = new ArrayList<Missile>();
        x = 40;
        y = 60;
    }


    public void move() {
        //x += dx;
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
    }

    public Image getImage() {
        return image;
    }

    public ArrayList<Missile> getMissiles() {
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

        if (key == KeyEvent.VK_SPACE)
        {

            if(!Board.win)
            {
                fire();
            }

        }
        if (key == KeyEvent.VK_UP) {
            dy = -1;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 1;
        }
    }

    void fire(){
        int CRAFT_SIZE = 20;
        missiles.add(new Missile(x + CRAFT_SIZE, y + CRAFT_SIZE /2));
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
