package com.company;

/**
 * Created by Sahil on 5/23/2014.
 * Version 1.0.0
 */

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


class Board extends JPanel implements ActionListener {

    public static boolean win = false;
    public static boolean easter = false;
    public static boolean devTools = false;
    private final Craft craft;
    private final int health;
    private final int score;
    private static ArrayList<Block> blocks;
    Song dinklage;
    Song dogesong;
    Song currentSong;
    private int ammo;
    //private Image image;
    private Image background;
    private Image winscreen;
    public boolean canPress = true;
    private Block block;



    public Board() throws IOException, UnsupportedAudioFileException, LineUnavailableException, MidiUnavailableException, InvalidMidiDataException {


        addKeyListener(new TAdapter());
        setFocusable(true);
        //background = ImageIO.read(new File("resources\\wallpaper-space.png"));
        //setBackground(Color.BLACK);
        setDoubleBuffered(true);

        craft = new Craft();

        Timer timer = new Timer(5, this);
        timer.start();

        health = 100;
        ammo = 50;
        score = 0;

        blocks = new ArrayList<Block>();
        blocks.add(new Block(500, 1, 1));
        blocks.add(new Block(400, 6, 6));
        blocks.add(new Block(300, 9, 9));
        blocks.add(new Block(750, 12, 12));
        blocks.add(new Block(650, 4, 4));

        dinklage = new Song(new File("resources/dinklage.wav"));
        dogesong = new Song(new File("resources/dogesong.wav"));
        currentSong = dinklage;
        currentSong.play();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (win && easter)
        {

            ImageIcon qq = new ImageIcon("resources/dogewin-screen.png");
            winscreen = qq.getImage();
            g.drawImage(winscreen, 1, 1, null);
        }
        else if (win)
        {
            ImageIcon qq = new ImageIcon("resources/win-screen.png");
            winscreen = qq.getImage();
            g.drawImage(winscreen, 1, 1, null);
        }
        else if (!win && easter)
        {
            ImageIcon ii = new ImageIcon("resources/dogemoon.png");
            background = ii.getImage();
            g.drawImage(background, 1, 1, null);
        }
        else if (!win)
        {
            ImageIcon ii = new ImageIcon("resources/wallpaper-space.png");
            background = ii.getImage();
            g.drawImage(background, 1, 1, null);
        }

    }


    public void paint(Graphics g) {
        super.paint(g);

        if(!win)
        {
            Graphics2D g2d = (Graphics2D)g;
            g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(), this);


            for (Block b : blocks)
            {
                g2d.drawImage(b.getImage(), b.getX(), b.getY(), this);
                b.move();
            }

            //drawInstructions(g);

            ArrayList ms = craft.getMissiles();

            for (Object m1 : ms) {
                Missile m = (Missile) m1;

                g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
            }

            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        }
    }

    public static ArrayList<Block> getBlocks()
    {
        return blocks;
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
                    Block a = blocks.get(q);

                    if(((m.getX()) >= a.getX()) && ((m.getY() >= a.getY()) && (m.getY() <= (a.getY() + 50))))
                    {
                        ms.remove(i);
                        blocks.remove(q);
                        if(blocks.size() == 0)
                        {
                            win = true;
                            canPress = false;
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

        if(!win)
        {
            moveCraft();
        }
        repaint();
    }

    public void setEaster()
    {
        easter = true;

        for(Block b:blocks)
        {
            b.setEBlock();
        }
        currentSong.stop();
        currentSong = dogesong;
        currentSong.play();
        repaint();
    }

    public void moveCraft()
    {
        craft.move();
    }


    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            craft.keyReleased(e);
        }

        public void keyPressed(KeyEvent e)
        {
            if(canPress)
            {
                craft.keyPressed(e);

                switch (e.getKeyChar()) {
                    case '`':
                        setEaster();
                        break;
                    case '=':
                        devTools = true;
                        break;
                }
            }
        }
    }

}
