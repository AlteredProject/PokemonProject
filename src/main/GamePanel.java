package main;

import battleSystem.Battle;
import entity.Player;
import object.SuperObject;
import pokedex.InteractiveBotton;
import pokedex.Pokedex;
import pokedex.Pokemon;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

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

    // === SYSTEM ===
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    ClickHandler leftClick = new ClickHandler();
    Sound music = new Sound();
    Sound sfx = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    Thread gameThread;

    // === GAME STATE ===
    public static final int statePlay = 0;
    public static final int statePause = 1;
    public static final int stateBattle = 2;

    public int gameState = statePlay;

    // == ENTITY & PLAYER ===
    public Player player = new Player(this, keyH);
    public SuperObject[] obj = new SuperObject[10];

    // == POKEDEX & BUTTONS
    public InteractiveBotton button = new InteractiveBotton(this, keyH, leftClick);
    public Pokedex pokedex = new Pokedex(this, keyH);

    // === WORLD SETTINGS ===
    public final int maxWorldCol = 100;
    public final int maxWorldRow = 100;

    // === BATTLE SYSTEM ===
    public Battle battle;

    // === FPS ===
    int FPS = 60;

    // === CONSTRUCTOR ===
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new java.awt.Color(120, 192, 248));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(leftClick);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        playMusic(0);
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

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            while (delta >= 1) {
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

        if (gameState == statePlay) {
            // Normal Overworld
            player.update();

            if (keyH.bPressed){
                Pokemon playerPokemon = Pokemon.load("25");
                Pokemon enemyPokemon = Pokemon.load("11");

                this.battle = new Battle(this, playerPokemon, enemyPokemon);
                this.gameState = stateBattle;
            }

        } else if (gameState == stateBattle){
            // Battle screen
            if (battle != null){
                battle.update();
            }

            if (keyH.spacePressed){
                battle.endBattle();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);


        // DEBUG
        long drawStart = System.nanoTime();

        if (gameState == statePlay) {
            tileM.drawLayer(g2, tileM.mapTileNumBackground);
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }
            tileM.drawLayer(g2, tileM.mapTileNumEnvironmentB);
            player.draw(g2);
            tileM.drawLayer(g2, tileM.mapTileNumEnvironmentF);

            //Pokedex Icon
            button.drawpokedexIcon(g2);

            //Pokedex
            if (keyH.pPressed ||leftClick.clicked && leftClick.mousePressedBox(40, 696, 44, 58) && leftClick.getCount() == 1) {
                    pokedex.draw(g2);
                    button.drawpokedexButtons(g2);
            }
        } else if (gameState == stateBattle){
            if (battle != null){
                battle.draw(g2);
            } else{
                g2.setColor(Color.lightGray);
                g2.drawString("BATTLE STATE (no battle object)", 50, 50);

                // probably gonna need to use the g2.draw... function like g2.drawImage();
            }
        }


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
            int printInterval = 30;
            if (frameSincePrint >= printInterval) {
                System.out.printf(
                        "Draw: %.3f ms | Highest: %.3f ms | Average: %.3f ms%n",
                        passedMs, highestMs, averageMs
                );
                System.out.println("xPos: " + player.worldX/64 + " yPos: " + player.worldY/64);
                frameSincePrint = 0;
            }
        }


        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        // music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSFX(int i) {
        sfx.setFile(i);
        sfx.play();
    }
}