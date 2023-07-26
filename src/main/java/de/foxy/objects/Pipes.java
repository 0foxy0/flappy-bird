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
    private int x;
    private final Pipe lowerPipe, upperPipe;
    private final Image image;

    public Pipes(int gap) throws URISyntaxException, IOException {
        URL imageUrl = getClass().getResource("/pipe.png");
        if (imageUrl == null) {
            throw new RuntimeException("pipe.png not found in resources");
        }
        File imageFile = new File(imageUrl.toURI());
        image = ImageIO.read(imageFile);

        x = Game.getWindowWidth();

        int minPipeHeight = 100;
        int lowestY = gap + minPipeHeight;
        int lowerPipeY = new Random().nextInt(Game.getWindowHeight() - minPipeHeight - lowestY) + lowestY;

        Image upperPipeImg = GamePanel.rotateImage((BufferedImage) image, 180.00);
        int upperPipeY = lowerPipeY - gap - image.getHeight(Game.getGamePanel());

        lowerPipe = new Pipe(x, lowerPipeY, image);
        upperPipe = new Pipe(x, upperPipeY, upperPipeImg);
    }

    public void update() {
        move();

        Bird bird = Game.getBird();
        if (bird.collides(this) && !bird.isDead()) {
            bird.dies();
        }
    }

    public void draw(Graphics g) {
        lowerPipe.draw(g);
        upperPipe.draw(g);
    }

    public static int getRandomGap() {
        int tiniestGap = 100;
        return new Random().nextInt(200 - tiniestGap) + tiniestGap;
    }

    public Boolean isOutOfWindow() {
        return x < -image.getWidth(Game.getGamePanel());
    }

    private void move() {
        x -= 1;
        lowerPipe.setX(x);
        upperPipe.setX(x);
    }

    public int getX() {
        return x;
    }

    public Pipe getLowerPipe() {
        return lowerPipe;
    }

    public Pipe getUpperPipe() {
        return upperPipe;
    }

    public int getWidth() {
        return image.getWidth(Game.getGamePanel());
    }

    public int getHeight() {
        return image.getHeight(Game.getGamePanel());
    }
}
