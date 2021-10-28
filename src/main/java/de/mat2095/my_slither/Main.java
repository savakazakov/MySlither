package de.mat2095.my_slither;

import javax.swing.*;


public final class Main {

    public static void main(String[] args) {

        System.setProperty("sun.java2d.opengl", "true");

        int dark;

        // workaround to fix issue on linux: https://github.com/bulenkov/Darcula/issues/29
        UIManager.getFont("Label.font");
        if ( args.length >= 1 && args[0].equals("no-dark") ) {
            dark = 0;
        } else {
        try {
            UIManager.setLookAndFeel("com.bulenkov.darcula.DarculaLaf");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        dark = 1;
    }
        
        new Splash(dark);

    }
}
