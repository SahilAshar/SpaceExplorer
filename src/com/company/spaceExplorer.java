package com.company;

import javax.swing.*;

public class spaceExplorer extends JFrame {

    private spaceExplorer()
    {

        Board game = new Board();

        add(game);

        setSize(800, 800);
        setLocationRelativeTo(null);
        setTitle("wow such explorer");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args)
    {
        new spaceExplorer();
    }
}