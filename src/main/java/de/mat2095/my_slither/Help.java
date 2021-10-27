package de.mat2095.my_slither;

import javax.swing.*;
import java.awt.*;

public class Help {

    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private GridLayout layout = new GridLayout(2,2);

    /**
     * Class constructor
     */
    Help() {
        // set layout
        panel.setLayout(layout);
        
        // about game label
        JLabel aboutGame = new JLabel("<html><h2>About</h2>MySlither is an implementation of Slither.io in Java, using the Slither.io servers via websockets.<br>The user interface is created in Swing and is themed with a dark-theme called Darcula.</html>");
        panel.add(aboutGame);

        // how to play label
        JLabel howToPlay = new JLabel("<html><h2>How To Play</h2>MySlither is a very simple game to play.<ul><li>Click 'connect' to join a game.</li><li>Use your mouse to move the snake around.</li><li>Eat pellets to grow larger and increase your score</li><li>Eat smaller snakes to increase your size and gain points.</li></ul></html>");
        panel.add(howToPlay);

        // additional features
        JLabel additionalFeatures = new JLabel("<html><h2>Top Tips!</h2><p>Whilst MySlither is easy, there are some additional tricks you can do to help you win.<ul><li>Click down and you will 'boost'. This will use some of your score and make you smaller.</li><li>Change the player dropdown from mouse input to 'EaterBot' if you want the built-in bot to take over!</li></ul></html>");
        panel.add(additionalFeatures);

        // credits
        JLabel credits = new JLabel("<html><h2>Credits</h2>Original project created by Mat2095, using Java-Websocket, SLF4J and Darcula.<br><br>Extended by SCC-210 Group 3 (Allan, Daniel, J, Jess, Liu, Sam and Sava).</html>");
        panel.add(credits);
        
        // configure frame
        frame.setContentPane(panel);
        frame.setTitle("MySlither - Help");
        frame.setSize(600,400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

    }

}
