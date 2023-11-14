package de.foxy;

import de.foxy.objects.Pipes;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import de.foxy.objects.util.Rectangle;

public class GamePanel extends JPanel {
    private static Rectangle playButton;
    private final String backgroundHexcode = "#71c4d2";

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch (Game.getGameState()) {
            case IN_GAME -> renderGame(g);
            case DEATH_MENU -> renderDeathMenu(g);
            default -> renderStartMenu(g);
        }
    }

    public void renderStartMenu(Graphics g) {
        setBackground(Color.decode(backgroundHexcode));

        drawTitle(g, Game.title, 50);
        drawPlayButton(g, "Play");
    }

    public void renderGame(Graphics g) {
        setBackground(Color.decode(backgroundHexcode));

        Game.getBird().draw(g);

        ArrayList<Pipes> pipesList = Game.getPipesList();
        pipesList.removeIf(Pipes::isOutOfWindow);

        for (Pipes pipes : pipesList) {
            pipes.draw(g);
        }

        drawScore(g);
    }

    public void renderDeathMenu(Graphics g) {
        renderGame(g);
        drawTitle(g, "You're dead!", 40);
        drawPlayButton(g, "Retry");
        Game.reset();
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.BLACK);

        String scoreText = "Score: " + Game.getScore();

        Font font = new Font("Arial", Font.BOLD, 24);
        g.setFont(font);

        FontMetrics metrics = g.getFontMetrics(font);
        int x = Game.getWindowWidth() /2 - metrics.stringWidth(scoreText) / 2;

        g.drawString(scoreText, x, metrics.getHeight());
    }

    private void drawTitle(Graphics g, String title, int fontSize) {
        g.setColor(Color.BLACK);

        Font font = new Font("Arial", Font.BOLD, fontSize);
        g.setFont(font);

        FontMetrics titleFontMetrics = g.getFontMetrics(font);
        int x = Game.getWindowWidth() /2 - titleFontMetrics.stringWidth(title) / 2;

        g.drawString(title, x, Game.getWindowHeight() / 2 - 150);
    }

    private void drawPlayButton(Graphics g, String label) {
        int windowWidth = Game.getWindowWidth(), windowHeight = Game.getWindowHeight();
        g.setColor(Color.WHITE);

        int buttonWidth = 100, buttonHeight = 50;
        playButton = new Rectangle((windowWidth - buttonWidth) /2, (windowHeight - buttonHeight) /2, buttonWidth, buttonHeight);
        g.fillRect(playButton.getX(), playButton.getY(), playButton.getWidth(), playButton.getHeight());

        g.setColor(Color.BLACK);
        Font labelFont = new Font("Arial", Font.BOLD, 25);
        g.setFont(labelFont);

        FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);
        int labelX = (windowWidth - labelFontMetrics.stringWidth(label)) /2;
        int labelY = (windowHeight - labelFontMetrics.getHeight()) /2;

        g.drawString(label, labelX, labelY);
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

    public static Rectangle getPlayButton() {
        return playButton;
    }
}
