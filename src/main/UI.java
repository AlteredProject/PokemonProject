package main;

import pokedex.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Pokemon pokemon;
    Pokedex pokedex;
    ClickHandler clickH = new ClickHandler(gp);
    UtilityTool uTool = new UtilityTool();
    public BufferedImage dialogueWindowImage, pokedexBoy, pokedexGirl, pokedexIcon, searchButtonReleased, searchButtonPressed, previousButtonReleased, nextButtonReleased, previousButtonPressed, nextButtonPressed, onOffButton;

    public Font pkmnFont;
    public boolean messageOn = false;
    private boolean showPokedexStartText = true;
    private int stage = 0;
    public String message = "";
    int messageCounter = 0;
    public String currentDialogue = "";
    public String inputBuffer = "";
    public boolean drawingInput = false;

    public UI(GamePanel gp, ClickHandler clickH, Pokemon pokemon, Pokedex pokedex) {
        this.gp = gp;
        this.clickH = clickH;
        this.pokemon = pokemon;
        this.pokedex = pokedex;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void getUIImages() {
        try {
            dialogueWindowImage = ImageIO.read(getClass().getResourceAsStream("/ui/dialogueBox.png"));
            uTool.scaleImage(dialogueWindowImage, 3, 3);
            pokedexBoy = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/boy.png"));
            pokedexGirl = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/girl.png"));
            pokedexIcon = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/pokedexIcon.png"));
            searchButtonReleased = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/searchPokemonGreen.png"));
            searchButtonPressed = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/searchPokemonOrange.png"));
            previousButtonReleased = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/directionBlueLeft.png"));
            previousButtonPressed = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/directionRedLeft.png"));
            nextButtonReleased = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/directionBlueRight.png"));
            nextButtonPressed = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/directionRedRight.png"));
            onOffButton = ImageIO.read(getClass().getResourceAsStream("/pokedexSprites/onOffButton.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(pkmnFont);
        g2.setColor(Color.white);

        // PLAY STATE
        if (gp.gameState == gp.playState) {
            drawPokedexIcon();
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
    }

    public void drawPokedexIcon() {
        int x = 25;
        int y = 690;
        BufferedImage image = pokedexIcon;
        g2.drawImage(image, x, y, image.getWidth() * 2, image.getHeight() * 2, null);
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80));

        String text = "PAUSED";

        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

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

    public void drawPokedexScreen() {
        // POKEDEX
        int x = 0;
        int y = 0;
        drawPokedex(x, y, pokedexGirl, 1);

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
        if (genderState == 1) {
            image = pokedexGirl;
        }
        if (genderState == 2) {
            image = pokedexBoy;
        }
        g2.drawImage(image, x, y, image.getWidth() * 4, image.getHeight() * 4, null);
    }

    public void drawButtons() {
        int pButtonX = 190;
        int buttonY = 576;
        int nButtonX = 398;
        int width = 147;
        int height = 64;
        int size = 48;
        int sButtonX = 245;

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
            g2.drawString("No Pokémon Found...Try again!", 655, 395);
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
        String description = PokemonDescription.getDescription(pokemon.getName());
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

    public int getXForCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }

    public int getXForCenteredTextAt(String text, int targetCenterX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return targetCenterX - length / 2;
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
            int cursorX = xName+(inputBuffer.length()*14) ;
            g2.drawLine(cursorX, textY - fm.getHeight() + 5, cursorX, textY + 5);
        }

    }
}
