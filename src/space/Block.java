/**
 * @(#)Blocks.java
 *
 *
 * @author
 * @version 1.00 2014/5/22
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


public class Block
{
	private int ySpeed, xSpeed;
	private int x;
	private int y;
	private Image image;
	private final int yBorder = 5;
	boolean visible;
	boolean moving;


    public Block(int ix, int iy, int dy, int dx)
    {
    	ImageIcon ii = new ImageIcon("resources/wowBlock.png");
    	image = ii.getImage();

    	x = ix;
    	y = iy;
    	ySpeed = dy;
    	xSpeed = dx;

    	visible = true;
    	moving = false;

    }


	public void move()
	{
		this.y = (int)(getY() + ySpeed);

		int overshoot = (int)(y + 120-800);
		if(y<=0)
		{
			y = 0;
			ySpeed = -ySpeed;
		}
		else if(overshoot>0)
		{
			y = (int)(800-120);
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

    public boolean isVisible()
    {
    	return visible;
    }

    public boolean isMoving()
    {
    	return moving;
    }




}