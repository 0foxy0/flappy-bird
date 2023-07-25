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

    public static BufferedImage rotate(BufferedImage bufferedImage, Double angle) {
        double sin = Math.abs(Math.sin(Math.toRadians(angle))),
                cos = Math.abs(Math.cos(Math.toRadians(angle)));

        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();

        int newWidth = (int) Math.floor(w*cos + h*sin),
                newHeight = (int) Math.floor(h*cos + w*sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, bufferedImage.getType());

        Graphics2D graphic = rotated.createGraphics();
        graphic.translate((newWidth-w)/2, (newHeight-h)/2);
        graphic.rotate(Math.toRadians(angle), (double) w /2, (double) h /2);
        graphic.drawRenderedImage(bufferedImage, null);
        graphic.dispose();

        return rotated;
    }
}
