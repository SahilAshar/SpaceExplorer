/**
 * 
 *
 *
 * @author
 * @version 1.00 2014/5/21
 */

package space;

import javax.swing.JFrame;
import space.Board;

public class spaceExplorer extends JFrame {

    public spaceExplorer()
    {

        Board game = new Board();

        add(game);

        setSize(800, 800);
        setLocationRelativeTo(null);
        setTitle("Space Explorer");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args)
    {
        new spaceExplorer();
    }
}