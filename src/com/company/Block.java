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
    private Image image;
    private Image eImage;
    private final boolean visible;
    private boolean moving;


    public Block(int ix, int dy, int dx)
    {
        ImageIcon ii = new ImageIcon("resources/wowBlock.png");
        image = ii.getImage();
        eImage = ii.getImage();

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

    public void setEBlock()
    {
        if(Board.easter)
        {
            int q = Board.getBlocks().size();
            int u = (int) (Math.random()*q);

            switch(u)
            {
                case 0: ImageIcon qq = new ImageIcon("resources/2+4.png");
                    image = qq.getImage(); break;
                case 1: ImageIcon ww = new ImageIcon("resources/18-5.png");
                    image = ww.getImage(); break;
                case 2: ImageIcon uu = new ImageIcon("resources/6div3.png");
                    image = uu.getImage(); break;
                case 3: ImageIcon yy = new ImageIcon("resources/723.png");
                    image = yy.getImage(); break;
                case 4: ImageIcon hh = new ImageIcon("resources/24div8.png");
                    image = hh.getImage(); break;

            }

        }
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