package de.foxy.objects;

import de.foxy.Game;
import de.foxy.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;

public class Pipes {
    private int x, lowerPipeY, upperPipeY;
    private final int gap;
    private final Image image;

    public Pipes(int gap) throws URISyntaxException, IOException {
        URL imageUrl = getClass().getResource("/pipe.png");
        if (imageUrl == null) {
            throw new RuntimeException("pipe.png not found in resources");
        }
        File imageFile = new File(imageUrl.toURI());
        image = ImageIO.read(imageFile);

        x = Game.getWindowWidth();
        this.gap = gap;
    }

    public void update() {
        x -= 1;

        Bird bird = Game.getBird();
        if (bird.collides(this) && !bird.isDead()) {
            bird.dies();
        }
    }

    public void draw(Graphics g) {
        lowerPipeY = Game.getWindowHeight() - 200;
        upperPipeY = lowerPipeY - gap - image.getHeight(Game.getGamePanel());

        g.drawImage(image, x, lowerPipeY, Game.getGamePanel());

        BufferedImage upperPipe = GamePanel.rotate((BufferedImage) image, 180.00);
        g.drawImage(upperPipe, x, upperPipeY, Game.getGamePanel());
    }

    public static int getRandomGap() {
        return new Random().nextInt(200) + 50;
    }

    public Boolean isOutOfWindow() {
        return x < -image.getWidth(Game.getGamePanel());
    }

    public int getX() {
        return x;
    }

    public int getLowerPipeY() {
        return lowerPipeY;
    }

    public int getUpperPipeY() {
        return upperPipeY;
    }

    public int getWidth() {
        return image.getWidth(Game.getGamePanel());
    }

    public int getHeight() {
        return image.getHeight(Game.getGamePanel());
    }
}
