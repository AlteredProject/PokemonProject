package battleSystem;

import main.GamePanel;
import pokedex.EntryStats;
import pokedex.Pokemon;
import pokedex.Sprites;

import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Battle {
    private GamePanel gp;
    private BufferedImage battleBG;
    private BufferedImage playerGround;
    private BufferedImage enemyGround;
    private BufferedImage myPokemonPic;
    private BufferedImage enemyPokemonPic;

    private Pokemon playerPokemon;
    private Pokemon enemyPokemon;

    private int playerMaxHp;
    private int playerCurrentHp;
    private Moves[] playerMoves;
    private Moves[] enemyMoves;
    private int selectedMoveIndex = 0;

    private int enemyMaxHp;
    private int enemyCurrentHp;

    private String message = "A wild pokemon has appeared";

    private boolean playerTurn = true;
    private boolean battleFinished = false;


    public Battle(GamePanel gp, Pokemon playerPokemon, Pokemon enemyPokemon) {
        this.gp = gp;
        this.playerPokemon = playerPokemon;
        this.enemyPokemon = enemyPokemon;

        // === HP INIT ===
        playerMaxHp = getBaseStat(playerPokemon, "hp");
        playerCurrentHp = playerMaxHp;

        enemyMaxHp = getBaseStat(enemyPokemon, "hp");
        enemyCurrentHp = enemyMaxHp;

        // === HARCODED MOVES ===
        playerMoves = new Moves[] {
                new Moves("Tackle", 40),
                new Moves("Quick Attack", 40),
                new Moves("Thunderbolt", 90),
                new Moves("Growl", 0)
        };

        enemyMoves = new Moves[] {
                new Moves("Poison Sting", 15),
                new Moves("Fury Attack", 25),
                new Moves("Twineedle", 40),
                new Moves("Bug Bite", 60)
        };

        // === LOAD GRAPHICS ===
        try {
            battleBG = ImageIO.read(getClass().getResourceAsStream("/resources/battle/battleBG.png"));
            playerGround = ImageIO.read(getClass().getResourceAsStream("/resources/battle/playerGround.png"));
            enemyGround = ImageIO.read(getClass().getResourceAsStream("/resources/battle/enemyGround.png"));

            //String myPokeURL = playerPokemon.sprites.front_default;
            String myPokeURL1 = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/25.png";
            myPokemonPic = ImageIO.read(new URL(myPokeURL1));
            //String enemyPokeURL = enemyPokemon.sprites.front_default;
            String enemyPokeURL1 = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/11.png";
            enemyPokemonPic = ImageIO.read(new URL(enemyPokeURL1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(){

    }

    public void draw(Graphics2D g2) {
        // Clear background
        g2.setColor(new Color(200, 230, 255));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Draw arena
        g2.drawImage(battleBG, 0, 0, gp.screenWidth, gp.screenHeight, null);
        g2.drawImage(playerGround, 50, 460, 500, 100, null);
        g2.drawImage(enemyGround, 700, 280, 300, 80, null);


        // Draw placeholder Pokémon (just text for now)

        g2.drawImage(myPokemonPic, 50, 250, 500, 500, null);
        g2.drawImage(enemyPokemonPic, 720, 160, 250, 250, null);

        g2.setColor(Color.BLACK);
        //g2.drawString("Your Pokémon: " + (playerPokemon != null ? playerPokemon.getName() : "None"), 100, 430);

        //g2.drawString("Enemy Pokémon: " + (enemyPokemon != null ? enemyPokemon.getName() : "None"), 620, 180);

        // player hp text
        if (playerPokemon != null) {
            g2.setColor(Color.BLACK);
            g2.drawString(playerPokemon.getName().toUpperCase(), 680, 450);
            g2.drawString("HP: " + playerCurrentHp + "/" + playerMaxHp, 680, 490);


            double ratio = (double) playerCurrentHp / playerMaxHp;
            int barWidth = 200;
            int barHeight = 15;
            int barX = 680;
            int barY = 460;

            g2.setColor(Color.GRAY);
            g2.fillRect(barX, barY, barWidth, barHeight);
            g2.setColor(Color.GREEN);
            g2.fillRect(barX, barY, (int) (barWidth * ratio), barHeight);
            g2.setColor(Color.BLACK);
            g2.drawRect(barX, barY, barWidth, barHeight);
        }

        if (enemyPokemon != null) {
            g2.setColor(Color.BLACK);
            g2.drawString(enemyPokemon.getName().toUpperCase(), 530, 180);
            g2.drawString("HP: " + enemyCurrentHp + "/" + enemyMaxHp, 530, 220);

            double ratio = (double) enemyCurrentHp / enemyMaxHp;
            int barWidth = 200;
            int barHeight = 15;
            int barX = 530;
            int barY = 190;

            g2.setColor(Color.GRAY);
            g2.fillRect(barX, barY, barWidth, barHeight);
            g2.setColor(Color.GREEN);
            g2.fillRect(barX, barY, (int) (barWidth * ratio), barHeight);
            g2.setColor(Color.BLACK);
            g2.drawRect(barX, barY, barWidth, barHeight);
        }


        // Message box at bottom
        g2.setColor(new Color(250, 250, 250));
        g2.fillRoundRect(40, 560, gp.screenWidth - 80, 140, 20, 20);
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(40, 560, gp.screenWidth - 80, 140, 20, 20);

        // g2.drawString(message, 60, 590);

        // Placeholder options
        g2.setFont(g2.getFont().deriveFont(18f));

        int baseX = 60;
        int baseY = 610;
        int boxWidth = 260;
        int boxHeight = 35;
        int gapX = 280;
        int gapY = 45;

        if (playerMoves != null) {
            for (int i = 0; i < playerMoves.length; i++) {
                int row = i / 2; // 0 or 1
                int col = i % 2; // 0 or 1

                int x = baseX + col * gapX;
                int y = baseY + row * gapY;

                // Highlight selected move
                if (i == selectedMoveIndex) {
                    g2.setColor(new Color(220, 220, 255));
                    g2.fillRoundRect(x - 10, y - 25, boxWidth, boxHeight, 10, 10);
                    g2.setColor(Color.BLACK);
                    g2.drawRoundRect(x - 10, y - 25, boxWidth, boxHeight, 10, 10);
                }

                g2.setColor(Color.BLACK);
                String moveName = (playerMoves[i] != null) ? playerMoves[i].name : "-";
                g2.drawString(moveName, x, y);
            }
        }
    }

    private int getBaseStat(Pokemon p, String statName) {
        if (p == null || p.stats == null) return 10;

        for (EntryStats entry : p.stats) {
            if (entry.stat != null &&
                    entry.stat.name != null &&
                    entry.stat.name.equalsIgnoreCase(statName)) {
                return entry.base_stat;
            }
        }
        return 10;
    }

    public void endBattle (){
        gp.gameState = GamePanel.statePlay;
        gp.battle = null;
    }
}
