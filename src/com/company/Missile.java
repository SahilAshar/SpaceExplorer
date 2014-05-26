package com.company;

/**
 * Created by Sahil on 5/23/2014.
 * Version 1.0.0
 */
import javax.swing.*;
import java.awt.*;

class Missile
{

    private int x;
    private final int y;
    private final Image image;
    private final Image eImage;
    private boolean visible;

    public Missile(int x, int y) {

        ImageIcon ii = new ImageIcon("resources/missile.png");
        image = ii.getImage();

        ImageIcon ww = new ImageIcon("resources/coin.png");
        eImage = ww.getImage();

        visible = true;
        this.x = x;
        this.y = y;
    }


    public Image getImage()
    {
        if(Board.easter)
            return eImage;
        else
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
        int MISSILE_SPEED = 2;
        x += MISSILE_SPEED;
        int BOARD_WIDTH = 790;
        if (x > BOARD_WIDTH)
            visible = false;
    }
}
