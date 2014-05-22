/**
 * @(#)Missile.java
 *
 *
 * @author
 * @version 1.00 2014/5/21
 */


package space;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Missile {

    private int x, y;
    private Image image;
    boolean visible;

    private final int BOARD_WIDTH = 790;
    private final int MISSILE_SPEED = 2;

    public Missile(int x, int y) {

        ImageIcon ii = new ImageIcon("resources/missile.png");
        image = ii.getImage();
        visible = true;
        this.x = x;
        this.y = y;
    }


    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void move() {
        x += MISSILE_SPEED;
        if (x > BOARD_WIDTH)
            visible = false;
    }
}