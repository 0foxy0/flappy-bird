package de.foxy;

import de.foxy.controller.KeyListener;
import de.foxy.objects.Bird;
import de.foxy.objects.Pipes;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends JFrame {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Game game = new Game();
        game.start();
    }

    private static GamePanel gamePanel;
    private static Bird bird;
    private final static int windowWidth = 720, windowHeight = 720;
    private static final ArrayList<Pipes> pipesList = new ArrayList<>();
    private int frameCounter = 0;
    private final int FPS = 60;
    private TimerTask interval;

    public Game() throws IOException, URISyntaxException {
        URL iconUrl = getClass().getResource("/icon.png");
        if (iconUrl == null) {
            throw new RuntimeException("icon.png not found in resources");
        }
        File imageFile = new File(iconUrl.toURI());
        Image image = ImageIO.read(imageFile);


        setTitle("Flappy Bird");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(windowWidth, windowHeight);
        setResizable(false);

        if (Taskbar.isTaskbarSupported()) {
            Taskbar taskbar = Taskbar.getTaskbar();
            taskbar.setIconImage(image);
        }
        setIconImage(image);

        gamePanel = new GamePanel();

        int birdWidth = 70, birdHeight = 50;
        bird = new Bird((getWidth() / 2) - (birdWidth / 2), (getHeight() / 2) - (birdHeight / 2), birdWidth, birdHeight);

        setContentPane(gamePanel);
        addKeyListener(new KeyListener());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void gameLoop() throws URISyntaxException, IOException {
        if (bird.isDead()) {
            interval.cancel();
        }

        bird.update();
        pipesList.forEach(Pipes::update);

        //every 10 seconds a new pipe pair spawns
        if (frameCounter % (10 * FPS) == 0) {
            int randomGap = Pipes.getRandomGap();
            pipesList.add(new Pipes(randomGap));
            frameCounter = 0;
        }
        frameCounter++;
    }

    public void start() {
        interval = new TimerTask() {
            @Override
            public void run(){
                gamePanel.repaint();
                try {
                    gameLoop();
                } catch (URISyntaxException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        new Timer().scheduleAtFixedRate(interval,0,1000 / FPS);
    }

    public static Bird getBird() {
        return bird;
    }

    public static ArrayList<Pipes> getPipesList() {
        return pipesList;
    }

    public static int getWindowWidth() {
        return windowWidth;
    }

    public static int getWindowHeight() {
        return windowHeight;
    }

    public static GamePanel getGamePanel() {
        return gamePanel;
    }
}