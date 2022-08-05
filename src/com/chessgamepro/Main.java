package com.chessgamepro;

import com.chessgamepro.GUI.Screen;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String...args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                 new Screen();
            }
        });
    }
}
