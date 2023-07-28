package de.foxy;

import de.foxy.controller.KeyController;
import de.foxy.controller.MouseController;
import de.foxy.enums.GameStatus;
import de.foxy.objects.Bird;
import de.foxy.objects.Pipes;
import de.foxy.objects.util.Rectangle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends JFrame implements MouseController {
    public static void main(String[] args) throws IOException, URISyntaxException {
        new Game();
    }

    private static GamePanel gamePanel;
    private static Bird bird;
    private final static int windowWidth = 720, windowHeight = 720;
    private static final ArrayList<Pipes> pipesList = new ArrayList<>();
    private static int frameCounter = 0;
    private final int FPS = 60;
    private TimerTask interval;
    private static int score;
    private static GameStatus status = GameStatus.START_MENU;
    public static final String title = "Flappy Bird";

    public Game() throws IOException, URISyntaxException {
        URL iconUrl = getClass().getResource("/icon.png");
        if (iconUrl == null) {
            throw new RuntimeException("icon.png not found in resources");
        }
        File imageFile = new File(iconUrl.toURI());
        Image image = ImageIO.read(imageFile);


        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(windowWidth, windowHeight);
        setResizable(false);

        if (Taskbar.isTaskbarSupported()) {
            Taskbar taskbar = Taskbar.getTaskbar();
            taskbar.setIconImage(image);
        }
        setIconImage(image);

        gamePanel = new GamePanel();

        setContentPane(gamePanel);
        addMouseListener(this);
        addKeyListener(new KeyController());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (status == GameStatus.IN_GAME) {
            return;
        }
        Rectangle playButton = GamePanel.getPlayButton();

        if (playButton.contains(e.getPoint())) {
            try {
                start();
            } catch (URISyntaxException | IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void gameLoop() throws URISyntaxException, IOException {
        if (bird.isDead()) {
            interval.cancel();
            status = GameStatus.DEATH_MENU;
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

    public void start() throws URISyntaxException, IOException {
        status = GameStatus.IN_GAME;

        int birdWidth = 70, birdHeight = 50;
        bird = new Bird((getWidth() / 2) - (birdWidth / 2), getHeight() /2 - birdHeight /2, birdWidth, birdHeight);

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

        new Timer().scheduleAtFixedRate(interval, 0, 1000 / FPS);
    }

    public static void reset() {
        bird = null;
        pipesList.clear();
        frameCounter = 0;
        score = 0;
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

    public static int getScore() {
        return score;
    }

    public static void increaseScore() {
        score++;
    }

    public static GameStatus getStatus() {
        return status;
    }
}