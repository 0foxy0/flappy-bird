package de.foxy.objects.util;

import de.foxy.Game;

import java.awt.*;

public class Pipe {
    private int x;
    private final int y;
    private final Image image;

    public Pipe(int x, int y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, Game.getGamePanel());
    }

    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
}
