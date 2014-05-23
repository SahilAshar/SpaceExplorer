package com.company;

/**
 * Created by Sahil on 5/23/2014.
 * Version 1.0.0
 */
import javax.swing.*;
import java.awt.Image;


class Block
{
    private int ySpeed;
    private final int xSpeed;
    private final int x;
    private int y;
    private final Image image;
    private final boolean visible;
    private boolean moving;


    public Block(int ix, int dy, int dx)
    {
        ImageIcon ii = new ImageIcon("resources/wowBlock.png");
        image = ii.getImage();

        x = ix;
        y = 5;
        ySpeed = dy;
        xSpeed = dx;

        visible = true;
        moving = false;

    }


    public void move()
    {
        this.y = getY() + ySpeed;

        int overshoot = y + 120-800;
        if(y<=0)
        {
            y = 0;
            ySpeed = -ySpeed;
        }
        else if(overshoot>0)
        {
            y = 680;
            ySpeed = -ySpeed;
        }
    }


    public void setMoving()
    {
        moving = true;
    }
    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Image getImage()
    {
        return image;
    }

    public int getYSpeed()
    {
        return ySpeed;
    }

    public int getXSpeed()
    {
        return xSpeed;
    }


}