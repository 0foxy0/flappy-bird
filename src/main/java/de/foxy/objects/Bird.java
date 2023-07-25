package de.foxy.objects;

import de.foxy.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Bird {
    private int y, velocity = 0;
    private final int x, width, height, bottom;
    private final int gravity = 1;
    private final Image image;
    private Boolean dead = false;

    public Bird(int x, int y, int width, int height) throws URISyntaxException, IOException {
        URL imageUrl = getClass().getResource("/bird.png");
        if (imageUrl == null) {
            throw new RuntimeException("bird.png not found in resources");
        }
        File imageFile = new File(imageUrl.toURI());
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        image = bufferedImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bottom = (int) (Game.getWindowHeight() - height * 1.5);
    }

    public void update() {
        velocity += gravity;
        y += velocity;

        if (y > bottom) {
            y = bottom;
            velocity = 0;
        }
        if (y < 0) {
            y = 0;
            velocity = 0;
        }
    }

    public void up() {
        velocity -= 15;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, Game.getGamePanel());
    }

    public Boolean collides(Pipes pipes) {
        Boolean xCollides = x + width > pipes.getX() && x < pipes.getX() + pipes.getWidth();
        Boolean yCollides = (y + height > pipes.getUpperPipeY() && y < pipes.getUpperPipeY() + pipes.getHeight())
                || (y + height > pipes.getLowerPipeY() && y < pipes.getLowerPipeY() + pipes.getHeight());

        return xCollides && yCollides;
    }

    public Boolean isDead() {
        return dead;
    }

    public void dies() {
        dead = true;
    }
}
