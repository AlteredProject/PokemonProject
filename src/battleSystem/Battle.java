package battleSystem;

import main.GamePanel;
import pokedex.EntryStats;
import pokedex.Pokemon;

import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class Battle {

    private GamePanel gp;

    // === Pictures ===
    private BufferedImage battleBG;
    private BufferedImage playerGround;
    private BufferedImage enemyGround;
    private BufferedImage myPokemonPic;
    private BufferedImage enemyPokemonPic;
    private BufferedImage playerInfoPanel;
    private BufferedImage enemyInfoPanel;
    private BufferedImage dialogBox;

    // === Pokemons ===
    private Pokemon playerPokemon;
    private Pokemon enemyPokemon;

    // === Pokemon HP ===
    private int playerMaxHp;
    private int playerCurrentHp;
    private int enemyMaxHp;
    private int enemyCurrentHp;

    // === Pokemon Moves ===
    private Moves[] playerMoves;
    private Moves[] enemyMoves;
    private int selectedMoveIndex = 0;

    // === Turn Base Control ===
    private boolean isPlayerTurn = true;
    private boolean isBattleFinished = false;

    // Messages
    private String message = "";
    private long messageUntil = 0;
    private final long messageDuration = 1300; //Millisecond

    private Random rng = new Random();

    // === UI Menu ===
    private final int mainMenu = 0;
    private final int fightMenu = 1;
    private final int bagMenu = 2;
    private final int pokeMenu = 3;
    private final int runAway = 4;
    private int menuState = mainMenu;


    public Battle(GamePanel gp, Pokemon playerPokemon, Pokemon enemyPokemon) {
        this.gp = gp;
        this.playerPokemon = playerPokemon;
        this.enemyPokemon = enemyPokemon;

        // === HP INIT ===
        playerMaxHp = getBaseStat(playerPokemon, "hp");
        playerCurrentHp = playerMaxHp;
        enemyMaxHp = getBaseStat(enemyPokemon, "hp");
        enemyCurrentHp = enemyMaxHp;

        // === HARCODED MOVES === will change or add more later
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
            playerInfoPanel = ImageIO.read(getClass().getResourceAsStream("/resources/battle/myHpBar.png"));
            enemyInfoPanel = ImageIO.read(getClass().getResourceAsStream("/resources/battle/enemyHpBar.png"));
            dialogBox = ImageIO.read(getClass().getResourceAsStream("/resources/ui/dialogueBox.png"));

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
        // Timer to show messages
        if (System.currentTimeMillis() < messageUntil) {
            return;
        }

        // If player run or Pokémon feint, ends battle
        if (isBattleFinished) {
            endBattle();
        }

        // Battle flow
        if (isPlayerTurn){
            mouseClick();
        } else {
            enemyTurn();
        }
    }

    private void mouseClick(){
        if (!gp.leftClick.clicked) return;

        int menuRightX = 680;
        int menuRightY = 580;
        int buttonW = 120;
        int buttonH = 50;
        int gapX = 5;
        int gapY = 5;

        // === Main Menu Buttons ===
        Rectangle fightButton = new Rectangle(menuRightX, menuRightY, buttonW, buttonH);
        Rectangle bagButton = new Rectangle(menuRightX + buttonW + gapX, menuRightY, buttonW, buttonH);
        Rectangle pokeButton = new Rectangle(menuRightX, menuRightY + buttonH + gapY, buttonW, buttonH);
        Rectangle runButton = new Rectangle(menuRightX + buttonW + gapX, menuRightY + buttonH + gapY, buttonW, buttonH);

        Rectangle[] moveBoxes = new Rectangle[4];
        moveBoxes[0] = new Rectangle(menuRightX, menuRightY, buttonW, buttonH);
        moveBoxes[1] = new Rectangle(menuRightX + buttonW + gapX, menuRightY, buttonW, buttonH);
        moveBoxes[2] = new Rectangle(menuRightX, menuRightY + buttonH + gapY, buttonW, buttonH);
        moveBoxes[3] = new Rectangle(menuRightX + buttonW + gapX, menuRightY + buttonH + gapY, buttonW, buttonH);

        if (menuState == mainMenu){

            if(gp.leftClick.mousePressedBox(fightButton.x, fightButton.y, fightButton.width, fightButton.height)){
                menuState = fightMenu;
                gp.leftClick.clicked = false;
                return;
            }

            if(gp.leftClick.mousePressedBox(bagButton.x, bagButton.y, bagButton.width, bagButton.height)){
                menuState = bagMenu;
                showMessage("You opened your bag... (left click)");
                gp.leftClick.clicked = false;
                return;
            }

            if(gp.leftClick.mousePressedBox(pokeButton.x, pokeButton.y, pokeButton.width, pokeButton.height)){
                menuState = pokeMenu;
                showMessage("You look at your Pokemons... (left click)");
                gp.leftClick.clicked = false;
                return;
            }

            if(gp.leftClick.mousePressedBox(runButton.x, runButton.y, runButton.width, runButton.height)){
                showMessage("You ran away safely!");
                menuState = runAway;
                isBattleFinished = true;
                gp.leftClick.clicked = false;
                return;
            }
        } else if (menuState == fightMenu){
            for (int i = 0; i < moveBoxes.length; i++){
                Rectangle r = moveBoxes[i];

                if (gp.leftClick.mousePressedBox(r.x, r.y, r.width, r.height)){
                    if (i < playerMoves.length){
                        usePlayerMove(i);
                        // return to main menu
                        menuState = mainMenu;
                        gp.leftClick.clicked = false;
                        return;
                    } else {
                        showMessage("No move in that slot.");
                        return;
                    }
                }

//                if (gp.leftClick.mousePressedBox(menuRightX - 40, menuRightY - 40, (buttonW+gapX) * 2 + 80, (buttonH+gapY) * 2 + 80)) {
//                    menuState = mainMenu;
//                    gp.leftClick.clicked = false;
//                    return;
//                }
            }
        } else {
            showMessage("Menu not implemented");
            menuState = mainMenu;
            gp.leftClick.clicked = false;
            return;
        }

    gp.leftClick.clicked = false;
    }

    private void usePlayerMove(int moveIndex){
        if (moveIndex < 0 || moveIndex >= playerMoves.length) return;
        Moves move = playerMoves[moveIndex];
        if (move == null) return;

        if (move.power <= 0){
            showMessage(playerPokemon.getName().toUpperCase() + " used " + move.name + "! It did no damage...");
        } else {
            int damage = calculateDamage(move.power);
            enemyCurrentHp = Math.max(0, enemyCurrentHp - damage);
            showMessage(playerPokemon.getName().toUpperCase() + " used " + move.name + " and dealt " + damage + "HP!");
        }

        if (enemyCurrentHp <= 0) {
            showMessage("Enemy " + enemyPokemon.getName().toUpperCase() + " fainted!");
            isBattleFinished = true;
        }

        isPlayerTurn = false;
    }

    private void enemyTurn(){
        if (enemyMoves == null){
            showMessage("Enemy has no moves...");
            isBattleFinished = true;
            return;
        }

        Moves move = enemyMoves[rng.nextInt(enemyMoves.length)];
        if (move.power <=0){
            showMessage("Enemy " + enemyPokemon.getName().toUpperCase() + " used " + move.name + "! It did no damage...");
        } else {
            int damage = calculateDamage(move.power);
            playerCurrentHp = Math.max(0, playerCurrentHp - damage);
            showMessage("Enemy " + enemyPokemon.getName().toUpperCase() + " used " + move.name + " and dealt " + damage + "HP!");
        }

        if (playerCurrentHp <= 0){
            showMessage("Your " + playerPokemon.getName().toUpperCase() + " fainted!");
            isBattleFinished = true;
            return;
        }

        isPlayerTurn = true;
    }

    private int calculateDamage(int basePower){
        // lower = longer battle
        double scale = 0.10;
        return (int) Math.ceil(basePower * scale);
    }

    public void draw(Graphics2D g2) {
        // Clear background
        g2.setColor(new Color(200, 230, 255));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Draw arena
        g2.drawImage(battleBG, 0, 0, gp.screenWidth, gp.screenHeight, null);
        g2.drawImage(playerGround, 50, 460, 500, 100, null);
        g2.drawImage(enemyGround, 700, 280, 300, 80, null);

        // Place Pokémon
        g2.drawImage(myPokemonPic, 50, 250, 500, 500, null);
        g2.drawImage(enemyPokemonPic, 720, 160, 250, 250, null);

        // Enemy info box (top-left)
        g2.drawImage(enemyInfoPanel,0,160, 500, 100, null);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20));
        g2.setColor(Color.WHITE);

        g2.drawString((enemyPokemon != null ? enemyPokemon.getName().toUpperCase() : "Enemy"), 50, 200);
        g2.drawString("??", 350, 202);

        // enemy HP bar in that box
        double eRatio = (double) enemyCurrentHp / Math.max(1, enemyMaxHp);
        int eBarX = 206, eBarY = 232, eBarW = 195, eBarH = 5;
        g2.setColor(Color.GREEN);
        g2.fillRect(eBarX, eBarY, (int) (eBarW * eRatio), eBarH);
        g2.setColor(Color.BLACK);
        g2.drawRect(eBarX, eBarY, eBarW, eBarH);

        // Player info box (lower-right)
        g2.drawImage(playerInfoPanel,530,390, 500, 105, null);

        g2.setColor(Color.WHITE);
        g2.drawString((playerPokemon != null ? playerPokemon.getName().toUpperCase() : "You"), 650, 425);
        g2.drawString("1", 940, 428);

        double pRatio = (double) playerCurrentHp / Math.max(1, playerMaxHp);
        int pBarX = 809, pBarY = 449, pBarW = 184, pBarH = 5;
        g2.drawString(String.valueOf(playerCurrentHp), 840, 477);
        g2.drawString(String.valueOf(playerMaxHp), 910, 477);
        g2.setColor(Color.GREEN);
        g2.fillRect(pBarX, pBarY, (int) (pBarW * pRatio), pBarH);
        g2.setColor(Color.BLACK);
        g2.drawRect(pBarX, pBarY, pBarW, pBarH);

        // Message box bottom-left
        g2.drawImage(dialogBox, 0, 560, gp.screenWidth, 145, null);

        // Message Text
        g2.setColor(Color.BLACK);
        g2.drawString(message, 50, 620);

        // MAIN MENU / MOVE UI bottom-right
        int menuRightX = 630;
        int menuRightY = 580;
        int buttonW = 150;
        int buttonH = 50;
        int gapX = 5;
        int gapY = 5;

        // Colors for each button
        Color fightColor = new Color(220, 80, 80);   // red
        Color bagColor = new Color(200,140,60);     // brown/yellow
        Color pokeColor = new Color(80,160,120);   // green
        Color runColor = new Color(60,120,200);   // blue

        // button rectangles
        Rectangle btnFight = new Rectangle(menuRightX, menuRightY, buttonW, buttonH);
        Rectangle btnBag = new Rectangle(menuRightX + buttonW + gapX, menuRightY, buttonW, buttonH);
        Rectangle btnPoke = new Rectangle(menuRightX, menuRightY + buttonH + gapY, buttonW, buttonH);
        Rectangle btnRun = new Rectangle(menuRightX + buttonW + gapX, menuRightY + buttonH + gapY, buttonW, buttonH);

        // Draw either the main buttons or the move grid depending on state
        if (menuState == mainMenu) {
            // FIGHT
            g2.setColor(fightColor);
            g2.fillRoundRect(btnFight.x, btnFight.y, btnFight.width, btnFight.height, 3, 3);
            g2.setColor(Color.BLACK);
            g2.drawRoundRect(btnFight.x, btnFight.y, btnFight.width, btnFight.height, 3, 3);
            g2.drawString("FIGHT", btnFight.x + 24, btnFight.y + 32);

            // BAG
            g2.setColor(bagColor);
            g2.fillRoundRect(btnBag.x, btnBag.y, btnBag.width, btnBag.height, 3, 3);
            g2.setColor(Color.BLACK);
            g2.drawRoundRect(btnBag.x, btnBag.y, btnBag.width, btnBag.height, 3, 3);
            g2.drawString("BAG", btnBag.x + 36, btnBag.y + 32);

            // POKéMON
            g2.setColor(pokeColor);
            g2.fillRoundRect(btnPoke.x, btnPoke.y, btnPoke.width, btnPoke.height, 3, 3);
            g2.setColor(Color.BLACK);
            g2.drawRoundRect(btnPoke.x, btnPoke.y, btnPoke.width, btnPoke.height, 3, 3);
            g2.drawString("POKÉMON", btnPoke.x + 12, btnPoke.y + 32);

            // RUN
            g2.setColor(runColor);
            g2.fillRoundRect(btnRun.x, btnRun.y, btnRun.width, btnRun.height, 3, 3);
            g2.setColor(Color.BLACK);
            g2.drawRoundRect(btnRun.x, btnRun.y, btnRun.width, btnRun.height, 3, 3);
            g2.drawString("RUN", btnRun.x + 42, btnRun.y + 32);

        } else if (menuState == fightMenu) {
            // Draw the 4 move boxes and highlight on mouse hover (optional)
            for (int i = 0; i < 4; i++) {
                int col = i % 2;
                int row = i / 2;
                int x = menuRightX + col * (buttonW + gapX);
                int y = menuRightY + row * (buttonH + gapY);

                g2.setColor(Color.BLACK);
                g2.drawRoundRect(x, y, buttonW, buttonH, 8, 8);

                String name = "-";
                if (i < playerMoves.length && playerMoves[i] != null) name = playerMoves[i].name;
                g2.drawString(name, x + 10, y + 30);
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
        gp.gameState = gp.playState;
        gp.battle = null;
    }

    public void rightClick() {
        menuState = mainMenu;
    }

    public void showMessage(String text){
        this.message = text;
        this.messageUntil = System.currentTimeMillis() + messageDuration;
    }
}
