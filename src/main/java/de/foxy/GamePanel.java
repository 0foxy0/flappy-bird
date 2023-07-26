package de.foxy;

import de.foxy.objects.Pipes;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    public void render(Graphics g) {
        setBackground(Color.decode("#71c4d2"));

        Game.getBird().draw(g);

        ArrayList<Pipes> pipesList = Game.getPipesList();
        pipesList.removeIf(Pipes::isOutOfWindow);

        for (Pipes pipes : pipesList) {
            pipes.draw(g);
        }
    }

    public static BufferedImage rotateImage(BufferedImage bufferedImage, Double angle) {
        double sin = Math.abs(Math.sin(Math.toRadians(angle))),
                cos = Math.abs(Math.cos(Math.toRadians(angle)));

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        int newWidth = (int) Math.floor(width * cos + height * sin),
                newHeight = (int) Math.floor(height * cos + width * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, bufferedImage.getType());

        Graphics2D graphic = rotated.createGraphics();
        graphic.translate((newWidth - width) /2, (newHeight - height) /2);
        graphic.rotate(Math.toRadians(angle), (double) width /2, (double) height /2);
        graphic.drawRenderedImage(bufferedImage, null);
        graphic.dispose();

        return rotated;
    }
}
