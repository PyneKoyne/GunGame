package main;

import javax.swing.*;
import java.awt.*;

public class Window extends Canvas{
    private static final long serialVersionUID = 1L;
    public Window(int width, int height, String title, GunGame game){

        JFrame frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width, height)); //1024x768, 1600/900\


        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.requestFocus();


        frame.add(game);
        frame.setVisible(true);
        game.start();
    }
}

