package de.foxy.controller;

import de.foxy.Game;
import de.foxy.enums.GameStatus;

import java.awt.event.*;

public class KeyController implements KeyListener {
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() != 32 || Game.getStatus() != GameStatus.IN_GAME) {
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
