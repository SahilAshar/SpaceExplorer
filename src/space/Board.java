/**
 * @(#)Board.java
 *
 *
 * @author
 * @version 1.00 2014/5/21
 */

package space;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.*;

import java.awt.*;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Character.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.awt.Image;
import javax.swing.ImageIcon;


public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Craft craft;

    BufferedImage background;

    public int health;
    public int ammo;
    public int score;
    private Image image;

    private ArrayList<Block> blocks;

    boolean win = false;


    public Board()
    {
		ImageIcon ii = new ImageIcon("resources/wallpaper-space.png");
		image = ii.getImage();

        addKeyListener(new TAdapter());
        setFocusable(true);
        //background = ImageIO.read(new File("resources\\wallpaper-space.png"));
        //setBackground(Color.BLACK);
        setDoubleBuffered(true);

        craft = new Craft();

        timer = new Timer(5, this);
        timer.start();

        health = 100;
    	ammo = 50;
    	score = 0;

    	blocks = new ArrayList<>();

    	blocks.add(new Block(500, 5, 1, 1));
    	blocks.add(new Block(400, 5, 6, 6));
    	blocks.add(new Block(300, 5, 9, 9));
    	blocks.add(new Block(750, 5, 12, 12));
    	blocks.add(new Block(650, 5, 4, 4));
    }

    @Override
    protected void paintComponent(Graphics g)
	{
    	super.paintComponent(g);
    	int imwidth = image.getWidth(null);
    	int imheight = image.getHeight(null);

    	if(win == true)
    	{
    		craft.inVisible();
    		ImageIcon qq = new ImageIcon("resources/win-screen.png");
			image = qq.getImage();
			g.drawImage(image, 1, 1, null);
    	}
    	else
    		g.drawImage(image, 1, 1, null);
	}

    public void winScreen(Graphics window)
    {
    	int fontSize = 20;

    	window.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));

   		window.drawString("Health :: "+/*scoreBoard.getHealth()*/health, 50, 50);
    	window.drawString("Missiles Left :: "+/*scoreBoard.getAmmo()*/ammo, 250, 50);
   		window.drawString("Score :: "+/*scoreBoard.getScore()*/score, 500, 50);
    }

//    public void gameLoop()
//	{
//    	while(true) //the loop
//   		{
//        	doGameUpdates();
//
//        	Thread.sleep(1); //the timing mechanism
//    	}
//	}


    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(), this);


		for(int q=0; q<blocks.size(); q++)
		{
        	Block b = (Block)blocks.get(q);
        	g2d.drawImage(b.getImage(),b.getX(), b.getY(), this);
        	b.move();
		}

		//drawInstructions(g);

        ArrayList ms = craft.getMissiles();

        for(int i=0; i<ms.size(); i++)
        {
        	Missile m = (Missile)ms.get(i);

        	g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }


    public void actionPerformed(ActionEvent e) {
        ArrayList ms = craft.getMissiles();


        /*Block b = (Block)blocks.get(1);
        Block c = (Block)blocks.get(2);
        Block d = (Block)blocks.get(3);
        Block f = (Block)blocks.get(4);
*/
        for(int i=0; i<ms.size();i++)
        {
        	Missile m = (Missile)ms.get(i);

        	if(m.isVisible())
        	{
        		m.move();

        		/*if(((m.getX()+35)>=a.getY())&&((m.getX()+35)<=a.getY()+75))
        		{
        			ms.remove(i);
        			blocks.remove(0);
        		}
        		else if(((m.getX()+35)>=b.getY())&&((m.getX()+35)<=b.getY()+75))
        		{
        			ms.remove(i);
        			blocks.remove(1);
        		}
        		else if(((m.getX()+35)>=c.getY())&&((m.getX()+35)<=c.getY()+75))
        		{
        			ms.remove(i);
        			blocks.remove(2);
        		}
        		else if(((m.getX()+35)>=d.getY())&&((m.getX()+35)<=d.getY()+75))
        		{
        			ms.remove(i);
        			blocks.remove(3);
        		}
        		else if(((m.getX()+35)>=f.getY())&&((m.getX()+35)<=f.getY()+75))
        		{
        			ms.remove(i);
        			blocks.remove(4);
        		}*/

        		for(int q=0; q<blocks.size(); q++)
        		{
        			Block a = (Block)blocks.get(q);

        			if((m.getX()+35 >= a.getX()) && ((m.getY()>=a.getY()) && (m.getY()<=a.getY()+75)))
        			{
        				ms.remove(i);
        				blocks.remove(q);
        				if(blocks.size() == 0)
        				{
        					win = true;
        					repaint();
        				}
        			}
        		}


        		/*else if(m.getX()+35 >= b.getX())
        		{
        			ms.remove(i);
        			blocks.remove(1);
        		}
        		else if(m.getX()+35 >= c.getX())
        		{
        			ms.remove(i);
        			blocks.remove(2);
        		}
        		else if(m.getX()+35 >= d.getX())
        		{
        			ms.remove(i);
        			blocks.remove(3);
        		}
        		else if(m.getX()+35 >= f.getX())
        		{
        			ms.remove(i);
        			blocks.remove(4);
        		}*/
        	}
        	else
        		ms.remove(i);
        }

        craft.move();
        repaint();
    }

    public void keyPressed(KeyEvent e)
    {
    	int key = e.getKeyCode();

    	if(key == KeyEvent.VK_SPACE)
    	{
    		ammo = ammo-1;
    		repaint();
    	}
    }


    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            craft.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            craft.keyPressed(e);
        }
    }

}