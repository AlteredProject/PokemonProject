package main;

import pokedex.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    ClickHandler clickH;
    Pokemon pokemon;
    Pokedex pokedex;
    UtilityTool uTool = new UtilityTool();
    public BufferedImage dialogueWindowImage, pokedexBoy, pokedexGirl, pokedexIcon, searchButtonReleased, searchButtonPressed, previousButtonReleased, nextButtonReleased, previousButtonPressed, nextButtonPressed, onOffButtonOn, onOffButtonOff, titleScreenBackground, logo, opal, rowan, lucas, dawn, turtwig, chimchar, piplup;

    public Font pkmnFont;
    private boolean showPokedexStartText = true;
    public String currentDialogue = "";
    public String inputBuffer = "";
    public boolean drawingInput = false;
    private int grassFadeCounter = 0;
    public int grassFadeCounterMax = 90;
    public boolean enterWild = false;
    // === Area Icons ===
    Image[] areaIcons = new Image[10];
    String[] areaNames = new String[8];
    Image[] wildIntro = new Image[7];
    private int currentArea = -1;
    private long areaDisplayStartTime = 0;
    private static final long AREA_DISPLAY_DURATION = 3000; // 3 seconds
    int animatedIconY = -200;

    int resetter = 0;
    boolean display;

    public UI(GamePanel gp, ClickHandler clickH, Pokemon pokemon, Pokedex pokedex) {
        this.gp = gp;
        this.clickH = clickH;
        this.pokemon = pokemon;
        this.pokedex = pokedex;

        InputStream is = getClass().getResourceAsStream("/font/pkmnFont.ttf");
        try {
            pkmnFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        getUIImages();
        getAreaNames();
    }

    public void getUIImages() {
        dialogueWindowImage = setup("/ui/dialogueBox");

        pokedexBoy = setup("/pokedexSprites/boy");
        pokedexGirl = setup("/pokedexSprites/girl");
        pokedexIcon = setup("/pokedexSprites/pokedexIcon");
        searchButtonReleased = setup("/pokedexSprites/searchPokemonGreen");
        searchButtonPressed = setup("/pokedexSprites/searchPokemonOrange");
        previousButtonReleased = setup("/pokedexSprites/directionBlueLeft");
        previousButtonPressed = setup("/pokedexSprites/directionRedLeft");
        nextButtonReleased = setup("/pokedexSprites/directionBlueRight");
        nextButtonPressed = setup("/pokedexSprites/directionRedRight");
        onOffButtonOn = setup("/pokedexSprites/onOffButtonOn");
        onOffButtonOff = setup("/pokedexSprites/onOffButtonOff");


        titleScreenBackground = setup("/titleScreen/background");
        logo = setup("/titleScreen/logo");
        opal = setup("/titleScreen/opal");
        rowan = setup("/titleScreen/rowan");
        lucas = setup("/titleScreen/lucas");
        dawn = setup("/titleScreen/dawn");

        turtwig = setup("/starterPokemon/turtwig");
        turtwig = uTool.scaleImage(turtwig, 192, 192);
        chimchar = setup("/starterPokemon/chimchar");
        chimchar = uTool.scaleImage(chimchar, 192, 192);
        piplup = setup("/starterPokemon/piplup");
        piplup = uTool.scaleImage(piplup, 192, 192);

        areaIcons[0] = setup("/ui/zoneVillage");
        areaIcons[1] = setup("/ui/zoneOcean");
        areaIcons[2] = setup("/ui/zoneBeach");
        areaIcons[3] = setup("/ui/zonePlains");
        areaIcons[4] = setup("/ui/zoneForest");
        areaIcons[5] = setup("/ui/zoneOcean");
        areaIcons[6] = setup("/ui/zoneSmallCity");
        areaIcons[7] = setup("/ui/zoneMountain");

        wildIntro[0] = setup("/battleIntro/wildIntro1");
        wildIntro[0] = uTool.scaleImage((BufferedImage) wildIntro[0], 1024, 768);
        wildIntro[1] = setup("/battleIntro/wildIntro2");
        wildIntro[1] = uTool.scaleImage((BufferedImage) wildIntro[1], 1024, 768);
        wildIntro[2] = setup("/battleIntro/wildIntro3");
        wildIntro[2] = uTool.scaleImage((BufferedImage) wildIntro[2], 1024, 768);
        wildIntro[3] = setup("/battleIntro/wildIntro4");
        wildIntro[3] = uTool.scaleImage((BufferedImage) wildIntro[3], 1024, 768);
        wildIntro[4] = setup("/battleIntro/wildIntro5");
        wildIntro[4] = uTool.scaleImage((BufferedImage) wildIntro[4], 1024, 768);
        wildIntro[5] = setup("/battleIntro/wildIntro6");
        wildIntro[5] = uTool.scaleImage((BufferedImage) wildIntro[5], 1024, 768);
        wildIntro[6] = setup("/battleIntro/wildIntro7");
        wildIntro[6] = uTool.scaleImage((BufferedImage) wildIntro[6], 1024, 768);
    }

    public void drawBattleIntro() {
        enterWild = true;
        if (getGrassFadeCounter() <= 40) {
            g2.drawImage(wildIntro[0], 0, 0, 1024, 768, null);
        } else if (getGrassFadeCounter() <= 55) {
            g2.drawImage(wildIntro[1], 0, 0, 1024, 768, null);
        } else if (getGrassFadeCounter() <= 65) {
            g2.drawImage(wildIntro[2], 0, 0, 1024, 768, null);
        } else if (getGrassFadeCounter() <= 70) {
            g2.drawImage(wildIntro[3], 0, 0, 1024, 768, null);
        } else if (getGrassFadeCounter() <= 75) {
            g2.drawImage(wildIntro[4], 0, 0, 1024, 768, null);
        } else if (getGrassFadeCounter() <= 80) {
            g2.drawImage(wildIntro[5], 0, 0, 1024, 768, null);
        } else if (getGrassFadeCounter() <= 90) {
            g2.drawImage(wildIntro[6], 0, 0, 1024, 768, null);
        }
    }

    public void getAreaNames() {
        areaNames[0] = "Twinleaf Town";
        areaNames[1] = "Route 201";
        areaNames[2] = "Opal Springs";
        areaNames[3] = "Floaroma Fields";
        areaNames[4] = "Eterna Forest";
        areaNames[5] = "Route 202";
        areaNames[6] = "Celestic Town";
        areaNames[7] = " Mt.Coronet";
    }

    public BufferedImage setup(String imagePath) {
        BufferedImage image;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    public BufferedImage setup(String imagePath, int scaleX, int scaleY) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, scaleX, scaleY);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(pkmnFont);
        g2.setColor(Color.white);

        // TITLE SCREEN STATE
        if (gp.gameState == gp.titleScreenState) {
            drawTitleScreen();
        }

        // PLAY STATE
        if (gp.gameState == gp.playState) {
            if (enterWild && gp.battle==null){
                drawBattleIntro();
            } else {
                drawPokedexIcon();
                drawAreaIcons();
            }
        }

        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        // DIALOGUE STATE
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }

        // POKEDEX STATE
        if (gp.gameState == gp.pokedexState) {
            drawPokedexScreen();
        }

        // POKEMON CHOICE STATE
        if (gp.gameState == gp.starterChoiceState) {
            drawStartersScreen();
        }
    }

    public void drawTitleScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18));
        g2.setColor(Color.BLACK);

        g2.drawImage(titleScreenBackground, 0, 0, gp.screenWidth, gp.screenHeight, null);
        g2.drawImage(logo, (gp.screenWidth / 2) - 350, 25, 700, 250, null);

        if (!display) {
            g2.drawString("Press enter to start", gp.screenWidth / 2 - 150, gp.screenHeight / 2 + 100);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16));
        g2.drawString("Made By: Andreas, Jakob, Theis & Bertram", 25, 750);
        g2.drawImage(opal, gp.screenWidth / 2 - 150, 250, 350, 150, null);
        g2.drawImage(rowan, 750, 400, 94 * 2, 139 * 2, null);
        g2.drawImage(lucas, 100, 400, 56 * 2, 123 * 2, null);
        g2.drawImage(dawn, 200, 450, 63 * 2, 125 * 2, null);

        resetter += 1;

        if (resetter < 75) {
            display = false;
        }
        if (resetter > 75) {
            display = true;
        }
        if (resetter > 150) {
            resetter = 0;
        }
    }

    public void drawPokedexIcon() {
        int x = 25;
        int y = 690;
        BufferedImage image = pokedexIcon;
        g2.drawImage(image, x, y, image.getWidth() * 2, image.getHeight() * 2, null);
    }

    // ===== STARTERS =====
    public void drawStartersScreen() {
        int width = gp.screenWidth;
        int height = 200;
        int x = 0;
        int y = (gp.screenHeight - height) / 2;
        Color c = new Color(50, 50, 50, 150);
        g2.setColor(c);
        g2.fillRect(x, y, width, height);

        g2.drawImage(turtwig, 99, y + 4, null);
        g2.drawImage(chimchar, 416, y + 4, null);
        g2.drawImage(piplup, 733, y + 4, null);
    }

    // ===== PAUSE =====
    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80));

        String text = "PAUSED";

        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    // ===== DIALOGUE =====
    public void drawDialogueScreen() {
        // WINDOW
        int x = (gp.screenWidth - (dialogueWindowImage.getWidth() * 4)) / 2;
        int y = gp.screenHeight - (dialogueWindowImage.getHeight() * 4) - (gp.tileSize / 8);
        drawDialogueWindow(x, y, dialogueWindowImage);

        // TEXT
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 26));
        x += gp.tileSize - 5;
        y += gp.tileSize + 10;
        g2.setColor(Color.black);
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 55;
        }
    }

    public void drawDialogueWindow(int x, int y, BufferedImage image) {
        g2.drawImage(image, x, y, image.getWidth() * 4, image.getHeight() * 4, null);
    }

    // ===== AREA ICONS =====
    public void drawAreaIcons() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18));
        g2.setColor(Color.BLACK);

        int x = (gp.player.worldX / gp.tileSize) + 1;
        int y = (gp.player.worldY / gp.tileSize) + 1;

        long elapsed = System.currentTimeMillis() - areaDisplayStartTime;

        int iconX = 635;
        int iconY = -200;

        int nameX = 660;
        int nameY = -130;

        int iconWidth = 345;
        int iconHeight = 95;

        int newArea = -1;

        if (x > 42 && x <= 71 && y >= 5 && y <= 22) {
            newArea = 3;
        } else if (x >= 49 && x <= 55 && y >= 62 && y <= 66) {
            newArea = 5;
        } else if (x >= 38 && x < 94 && y >= 83 && y <= 94) {
            newArea = 7;
        } else if (x <= 38 && y >= 62 && y <= 91) {
            newArea = 6;
        } else if (x >= 27 && x <= 54 && y >= 17 && y <= 65) {
            newArea = 1;
        } else if (x >= 40 && x <= 92 && y >= 55 && y <= 73) {
            newArea = 5;
        } else if (x <= 27 && y >= 38 && y <= 57) {
            newArea = 0;
        } else if (x <= 42 && y < 34) {
            newArea = 2;
        } else if (x >= 71 && x <= 92 && y >= 7 && y <= 54) {
            newArea = 4;
        }

        // If area changed, reset timer
        if (newArea != -1 && newArea != currentArea) {
            currentArea = newArea;
            areaDisplayStartTime = System.currentTimeMillis();
            animatedIconY = -200;
        }

        // Draw only if within 3 seconds
        if (currentArea != -1 && elapsed <= AREA_DISPLAY_DURATION) {
            if (animatedIconY < 0) {
                animatedIconY += 4;
            }
            g2.drawImage(areaIcons[currentArea], iconX, animatedIconY, iconWidth, iconHeight, null);
            g2.drawString(areaNames[currentArea], nameX, animatedIconY + 70);
        }

        if (elapsed > AREA_DISPLAY_DURATION) {
            if (animatedIconY > -200) {
                animatedIconY -= 4;
            }
            g2.drawImage(areaIcons[currentArea], iconX, animatedIconY, iconWidth, iconHeight, null);
            g2.drawString(areaNames[currentArea], nameX, animatedIconY + 70);
        }
    }

    // ===== POKEDEX =====
    public void drawPokedexScreen() {
        // POKEDEX
        int x = 0;
        int y = 0;
        drawPokedex(x, y, pokedexGirl, gp.genderState);

        // BUTTONS
        drawButtons();

        // INFO
        if (pokemon.name != null) {
            showPokedexStartText = false;
            drawPokemonSprite();
            drawPokemonInfo();
        }
        if (showPokedexStartText) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 12));
            g2.setColor(Color.black);
            g2.drawString("PÓKEDEX", 695, 396);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 8));
            g2.drawString("Welcome to the International", 655, 376);
            g2.drawString("Search for a Pokémon by", 667, 450);
            g2.drawString("name or number", 697, 465);
        }
        if (clickH.searching) {
            drawCustomInputBox();
        }
    }

    public void drawPokedex(int x, int y, BufferedImage image, int genderState) {
        try {
            if (genderState == 1) {
                image = pokedexGirl;
            }
            if (genderState == 2) {
                image = pokedexBoy;
            }
            g2.drawImage(image, x, y, image.getWidth() * 4, image.getHeight() * 4, null);
            if (!clickH.onOffAction) {
                gp.gameState = gp.playState;
                clickH.onOffAction = true;
                pokemon.name = null;
                showPokedexStartText = true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void drawButtons() {
        int pButtonX = 190;
        int buttonY = 576;
        int nButtonX = 398;
        int width = 147;
        int height = 64;
        int size = 48;
        int sButtonX = 245;
        int onOffX = 605;
        int onOffY = 220;
        int onOffW = 76;
        int onOffH = 70;

        if (clickH.previousButtonPressed) {
            g2.drawImage(previousButtonPressed, pButtonX, buttonY, size, size, null);
        } else {
            g2.drawImage(previousButtonReleased, pButtonX, buttonY, size, size, null);
        }

        if (clickH.searchButtonPressed) {
            g2.drawImage(searchButtonPressed, sButtonX, buttonY - 11, width, height, null);
        } else {
            g2.drawImage(searchButtonReleased, sButtonX, buttonY - 11, width, height, null);
        }

        if (clickH.nextButtonPressed) {
            g2.drawImage(nextButtonPressed, nButtonX, buttonY, size, size, null);
        } else {
            g2.drawImage(nextButtonReleased, nButtonX, buttonY, size, size, null);
        }
        if (!clickH.onOff) {
            g2.drawImage(onOffButtonOn, onOffX, onOffY, onOffW, onOffH, null);
        } else if (clickH.onOff) {
            g2.drawImage(onOffButtonOff, onOffX, onOffY, onOffW, onOffH, null);
        }
    }

    public void drawPokemonInfo() {
        int x = 625;
        int y = 340;
        final int lineSpace = 15;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 10));
        g2.setColor(Color.black);
        if (pokedex.isSearching()) {
            g2.drawString("Loading...", 700, 675);
            return;
        }

        if (pokemon.getName() == null || pokemon.getId() == 0 || pokemon.getTypes() == null) {
            g2.drawString("No Pokémon Found...", 655, 395);
            g2.drawString("Try again!", 695, 420);
            return;
        }

        // Name
        int xName = getXForCenteredTextAt(pokemon.getName(), 720);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 12));
        g2.drawString(pokemon.getName().toUpperCase(), xName, 675);

        // Base info
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 10));
        g2.drawString("NUMBER #" + pokemon.getId(), x, y);
        y += lineSpace;
        g2.drawString("HEIGHT " + String.format("%.1f", pokemon.getHeight() * 0.1) + " M", x, y);
        y += lineSpace;
        g2.drawString("WEIGHT " + String.format("%.1f", pokemon.getWeight() * 0.1) + " KG", x, y);
        y += lineSpace;

        // Pokemon type
        int typeCounter = 1;
        for (TypeEntry entry : pokemon.getTypes()) {
            g2.drawString("TYPE " + typeCounter + ": " + entry.type.name.toUpperCase(), x, y);
            y += lineSpace;
            typeCounter++;
        }
        y += lineSpace;
        // Pokemon Stats
        if (pokemon.getStats() != null) {
            for (EntryStats entry : pokemon.getStats()) {
                g2.drawString(entry.stat.name.toUpperCase() + ": " + entry.base_stat, x, y);
                y += lineSpace;
            }
        }
        // Pokemon description
        y += lineSpace;

        String description = pokemon.getDescription();
        if (pokemon.getDescription() == null) {
            description = "No description foound for " + pokemon.getName();
        }
        g2.drawString("DESCRIPTION: ", x, y);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 9));
        y += lineSpace;
        drawWrappedText(g2, description, x, y, 258, 12);
    }

    public void drawPokemonSprite() {
        int pokemonX = 225;
        int pokemonY = 300;
        int pokemonSize = 96;

        if (pokedex.pokemonSprite != null) {
            g2.drawImage(pokedex.pokemonSprite, pokemonX, pokemonY, pokemonSize * 2, pokemonSize * 2, null);
        }
    }

    public int drawWrappedText(Graphics2D g2, String text, int startX, int startY, int maxLineWidth, int lineSpacing) {
        FontMetrics fm = g2.getFontMetrics();
        String[] words = text.split(" ");
        String currentLine = "";
        int y = startY;

        for (String word : words) {
            String potentialLine = currentLine.isEmpty() ? word : currentLine + " " + word;
            int potentialWidth = fm.stringWidth(potentialLine);

            if (potentialWidth <= maxLineWidth) {
                currentLine = potentialLine;
            } else {
                if (!currentLine.isEmpty()) {
                    g2.drawString(currentLine, startX, y);
                    y += lineSpacing;
                }
                currentLine = word;
            }
        }
        if (!currentLine.isEmpty()) {
            g2.drawString(currentLine, startX, y);
            y += lineSpacing;
        }

        return y;
    }

    public void inputSetup() {
        InputStream is = getClass().getResourceAsStream("/font/pkmnFont.ttf");
        try {
            pkmnFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getUIImages();
    }

    public void drawCustomInputBox() {
        if (!drawingInput) {
            return;
        }

        int boxX = 625;
        int boxY = 645;
        int boxWidth = 250;
        int boxHeight = 40;

        g2.setColor(new Color(100, 117, 113));
        g2.fillRect(boxX, boxY, boxWidth, boxHeight);

        g2.setFont(pkmnFont.deriveFont(Font.BOLD, 12));
        g2.setColor(Color.BLACK);

        int textX = boxX + 10;
        int textY = boxY + boxHeight - 12;
        int xName = getXForCenteredTextAt(inputBuffer, 743);
        g2.drawString(inputBuffer.toUpperCase(), xName, textY);

        if (gp.getDrawCount() % 60 < 30) {
            FontMetrics fm = g2.getFontMetrics();
            int cursorX = xName + (inputBuffer.length() * 14);
            g2.drawLine(cursorX, textY - fm.getHeight() + 5, cursorX, textY + 5);
        }
    }

    public int getXForCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }

    public int getXForCenteredTextAt(String text, int targetCenterX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return targetCenterX - length / 2;
    }

    public void updateGrassFade() {
        System.out.println(this.grassFadeCounter);
        if (enterWild) {
            this.grassFadeCounter++;
        }
    }

    public int getGrassFadeCounter() {
        return grassFadeCounter;
    }

    public void setGrassFadeCounter(int grassFadeCounter) {
        this.grassFadeCounter = grassFadeCounter;
    }
}
