package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // === DEBUG ===
    private int warmupFrames = 30;
    private long highestDrawTime = 0;
    private long totalDrawTime = 0;
    private long drawCount = 0;
    private int frameSincePrint = 0;

    // === SCREEN SETTINGS ===
    final int originalTileSize = 16;    // 16x16 px
    public final int ScaleMultiplier = 4;

    public final int tileSize = originalTileSize * ScaleMultiplier;     // 64x64 px
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;

    public final int screenWidth = tileSize * maxScreenCol;     // 1024 px
    public final int screenHeight = tileSize * maxScreenRow;    // 768 px

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this,keyH);


    // === WORLD SETTINGS ===
    public final int maxWorldCol = 100;
    public final int maxWorldRow = 100;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // === FPS ===
    int FPS = 60;


    // === CONSTRUCTOR ===
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new java.awt.Color(120,192,248));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // === GAME LOOP ===
    @Override
    public void run() {
        double drawInterval = 1_000_000_000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        int drawCount = 0;

        while(gameThread != null)  {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            while(delta >= 1) {
                update();
                delta--;
                drawCount++;
                repaint();
            }

            if (System.currentTimeMillis() - timer >= 1000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer += 1000;
            }
        }
    }


    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);


        // DEBUG
        long drawStart = System.nanoTime();

        tileM.drawLayer(g2, tileM.mapTileNumBackground);
        player.draw(g2);
        tileM.drawLayer(g2, tileM.mapTileNumEnvironment);

        // DEBUG
        long passedTime = System.nanoTime() - drawStart;

        double passedMs = passedTime / 1_000_000.0;

        if (warmupFrames > 0) {
            warmupFrames--;
        } else {
            // TRACK AVG & MAX
            if (passedTime > highestDrawTime) {
                highestDrawTime = passedTime;
            }

            totalDrawTime += passedTime;
            drawCount++;

            double averageMs = (totalDrawTime / (double) drawCount) / 1_000_000.0;
            double highestMs = highestDrawTime / 1_000_000.0;

            frameSincePrint++;
            int printInterval = 15;
            if (frameSincePrint >= printInterval) {
                System.out.printf(
                        "Draw: %.3f ms | Highest: %.3f ms | Average: %.3f ms%n",
                        passedMs, highestMs, averageMs
                );
                frameSincePrint = 0;
            }
        }

        g2.dispose();

    }
}