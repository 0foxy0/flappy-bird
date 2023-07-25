package de.foxy.controller;

import de.foxy.Game;

import java.awt.event.KeyEvent;

public class KeyListener implements java.awt.event.KeyListener {
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() != 32) {
            return;
        }
        Game.getBird().up();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
