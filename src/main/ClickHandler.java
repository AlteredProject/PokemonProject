package main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickHandler implements MouseListener {
    GamePanel gp;
    boolean previousButtonPressed, nextButtonPressed, searchButtonPressed;
    private int x;
    private int y;

    public ClickHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
        // Pressed on podexIcon
        if (mousePressedBox(40, 696, 44, 58)&& gp.gameState == gp.playState) {
            gp.switchPokedexStatus();
            if (gp.gameState != gp.pokedexState) {
                gp.gameState = gp.pokedexState;
            } else if (gp.gameState == gp.pokedexState) {
                gp.gameState = gp.playState;
            }
        }
        // Pressed on Pokedex Search Button
        if (mousePressedBox(245, 565, 147, 64)) {
            if (gp.gameState == gp.pokedexState) {
                searchButtonPressed = true;
            }
        }
        //Pressed on Pokedex left button
        if (mousePressedBox(190, 576, 45, 45)) {
            if (gp.gameState == gp.pokedexState) {
                System.out.println("=== LEFT BUTTON PRESSED ===");
                previousButtonPressed = true;
            }
        }
        //Pressed on Pokedex right button
        if (mousePressedBox(398, 576, 45, 45)) {
            if (gp.gameState == gp.pokedexState) {
                nextButtonPressed = true;
            }
        }
        if (mousePressedBox((gp.screenWidth - (254 * 4)) / 2, gp.screenHeight - (46 * 4) - (gp.tileSize / 8), 254*4, 46*4) && gp.gameState == gp.dialogueState) {
            gp.keyH.enterPressed = true;
            gp.buttonSound.playButtonSound();
            gp.gameState = gp.playState;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("=== RELEASED MOUSE ===");
        previousButtonPressed = false;
        searchButtonPressed = false;
        nextButtonPressed = false;

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public boolean mousePressedBox(int worldX, int worldY, int width, int height) {
        Rectangle rect = new Rectangle(worldX, worldY, width, height);
        boolean isPointInside = (rect.contains(this.x, this.y));
        return isPointInside;
    }
}
