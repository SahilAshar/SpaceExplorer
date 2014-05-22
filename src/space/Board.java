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


public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Craft craft;

    public int health;
    public int ammo;
    public int score;


    public Board() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        craft = new Craft();

        timer = new Timer(5, this);
        timer.start();

        health = 100;
    	ammo = 50;
    	score = 0;
    }

    public void drawInstructions(Graphics window)
    {

    	int fontSize = 20;

    	window.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));

    	window.drawString("Health :: "+/*scoreBoard.getHealth()*/health, 50, 50);
    	window.drawString("Missiles Left :: "+/*scoreBoard.getAmmo()*/ammo, 250, 50);
    	window.drawString("Score :: "+/*scoreBoard.getScore()*/score, 500, 50);
    }


    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(), this);

        drawInstructions(g);

        ArrayList ms = craft.getMissiles();

        for(int i=0; i<ms.size(); i++)
        {
        	Missile m = (Missile)ms.get(i);

        	g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
        }
        //System.out.println("yum");

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }


    public void actionPerformed(ActionEvent e) {
        ArrayList ms = craft.getMissiles();

        for(int i=0; i<ms.size();i++)
        {
        	Missile m = (Missile)ms.get(i);

        	if(m.isVisible())
        		m.move();
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