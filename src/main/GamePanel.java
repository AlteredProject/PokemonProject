package main;

import battleSystem.Battle;
import entity.Entity;
import entity.Player;
import object.SuperObject;
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

    // == GENDER STATE ==
    public int genderState = 1;

    // === SYSTEM ===
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    ClickHandler clickH = new ClickHandler(this);
    Pokemon originalPokemon = new Pokemon();
    Pokedex pokedex = new Pokedex(this, keyH, originalPokemon);

    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this, clickH, originalPokemon, pokedex);
    Thread gameThread;

    // == ENTITY & OBJECT ===
    public Player player = new Player(this, keyH);
    public SuperObject[] obj = new SuperObject[10];
    public Entity[] npc = new Entity[20];

    // == GAME STATE ==
    public int gameState;
    public final int titleScreenState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int pokedexState = 4;
    public final int battleState = 5;

    // === WORLD SETTINGS ===
    public final int maxWorldCol = 100;
    public final int maxWorldRow = 100;

    // === SOUND ===
    public Sound music = new Sound(this,player);
    public Sound collisionSound = new Sound(this,player);
    public Sound buttonSound = new Sound(this,player);

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
        this.addMouseListener(clickH);
        this.setFocusable(true);
        this.setOpaque(true);
        this.setIgnoreRepaint(false);
        this.setLayout(null);
    }

    public void setupGame() {
        playMusic();
        aSetter.setObject();
        aSetter.setNPC();
        gameState = titleScreenState;
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

//            if (System.currentTimeMillis() - timer >= 1000) {
//                System.out.println("FPS: " + drawCount);
//                drawCount = 0;
//                timer += 1000;
//            }
        }
    }


    public void update() {
        if (gameState == playState) {
            player.update();
            music.updateMusic();
            music.updateFade();
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }

            if (keyH.bPressed) {
                Pokemon playerPokemon = Pokemon.load("25");
                Pokemon enemyPokemon = Pokemon.load("11");

                this.battle = new Battle(this, playerPokemon, enemyPokemon, clickH);
                this.gameState = battleState;
            }
        }
        if (gameState == pauseState) {

        }

        if (gameState == battleState) {
            // Battle screen
            if (battle != null) {
                battle.update();
            }

            if (keyH.spacePressed && battle != null) {
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


        if (gameState != titleScreenState && gameState != battleState) {

            // Background Layer
            tileM.drawLayer(g2, tileM.mapTileNumBackground);

            // Object Layer
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }

            // Environment Behind player
            tileM.drawLayer(g2, tileM.mapTileNumEnvironmentB);

            // NPCs
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].draw(g2);
                }
            }

            // Player
            player.draw(g2);

            // Environment Front of player
            tileM.drawLayer(g2, tileM.mapTileNumEnvironmentF);
        } else {
            if (battle != null) {
                battle.draw(g2);
            } else {
                g2.setColor(Color.lightGray);
                g2.drawString("BATTLE STATE (no battle object)", 50, 50);

                // probably gonna need to use the g2.draw... function like g2.drawImage();
            }
        }

        // UI
        ui.draw(g2);


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
//                System.out.printf(
//                        "Draw: %.3f ms | Highest: %.3f ms | Average: %.3f ms%n",
//                        passedMs, highestMs, averageMs
//                );
//                System.out.println("xPos: " + ((player.worldX/64)+1) + " yPos: " + ((player.worldY/64)+1));
                frameSincePrint = 0;
            }
        }


        g2.dispose();
    }

        public void playMusic () {
            music.setFile();
            music.play();

        }

        public void stopMusic () {
            music.stop();
        }

    public long getDrawCount() {
        return drawCount;
    }

    public void initializeUIComponents() {
        if (ui != null) {
            ui.inputSetup();
        }
    }
}