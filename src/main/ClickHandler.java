package main;

import pokedex.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class ClickHandler implements MouseListener {
    GamePanel gp;
    boolean previousButtonPressed, nextButtonPressed, searchButtonPressed, searching, onOff, onOffAction;
    public boolean clicked = false;
    private int x;
    private int y;

    public ClickHandler(GamePanel gp) {
        this.gp = gp;
        onOffAction = true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }


    @Override
    public void mousePressed(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
        clicked = true;

        if (SwingUtilities.isRightMouseButton(e)){
            handleRightClick();
        }
        // Pressed on podexIcon
        if (mousePressedBox(40, 696, 44, 58) && gp.gameState == gp.playState) {
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
                searching = true;
                gp.ui.drawingInput = true;
                gp.ui.inputBuffer = "";
                gp.repaint();
            }
        }

        //Pressed on Pokedex left button
        if (mousePressedBox(190, 576, 45, 45)) {
            if (gp.gameState == gp.pokedexState) {
                previousButtonPressed = true;
                String input = String.valueOf((gp.originalPokemon.getId() - 1));
                if (!input.isEmpty()) {
                    gp.pokedex.search(input);
                }
            }
        }
        //Pressed on Pokedex right button
        if (mousePressedBox(398, 576, 45, 45)) {
            if (gp.gameState == gp.pokedexState) {
                nextButtonPressed = true;
                String input = String.valueOf((gp.originalPokemon.getId() + 1));
                if (!input.isEmpty()) {
                    gp.pokedex.search(input);
                }
            }
        }
        // Pressed on Pokedex ON/OFF button
        if (mousePressedBox(605, 220, 66, 60)) {
            if (gp.gameState == gp.pokedexState) {
                onOff = true;
                int reset = 0;
                gp.originalPokemon.setId(reset);
                gp.pokedex.pokemonSprite = null;
            }
        }

        // Pressed on Dialogue
        if (mousePressedBox((gp.screenWidth - (254 * 4)) / 2, gp.screenHeight - (46 * 4) - (gp.tileSize / 8), 254 * 4, 46 * 4)) {
            if (gp.gameState == gp.dialogueState) {
                gp.keyH.enterPressed = true;
                gp.buttonSound.playButtonSound();
                gp.gameState = gp.playState;
            }
        }

        // Pressed on Turtwig
        if (mousePressedBox(99, ((gp.screenHeight-200)/2)+4, 192, 192)) {
            if (gp.gameState == gp.starterChoiceState) {
                gp.playerPokemon = 387;
                gp.music.playSound(23);
                gp.gameState = gp.playState;
            }
        }

        // Pressed on Chimchar
        if (mousePressedBox(416, ((gp.screenHeight-200)/2)+4, 192, 192)) {
            if (gp.gameState == gp.starterChoiceState) {
                gp.playerPokemon = 390;
                gp.music.playSound(24);
                gp.gameState = gp.playState;
            }
        }

        // Pressed on Chimchar
        if (mousePressedBox(733, ((gp.screenHeight-200)/2)+4, 192, 192)) {
            if (gp.gameState == gp.starterChoiceState) {
                gp.playerPokemon = 393;
                gp.music.playSound(25);
                gp.gameState = gp.playState;
            }
        }
    }

    public void handleRightClick(){
        if (gp.gameState == gp.battleState){
            if (gp.battle != null){
                gp.battle.rightClick();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        searchButtonPressed = false;
        previousButtonPressed = false;
        nextButtonPressed = false;
        onOff = false;

        if (!onOff && mousePressedBox(605,220,66,60)){
            onOffAction = false;
        }


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

    public boolean consumeClick(int rx, int ry, int rw, int rh) {
        if (!clicked) return false;

        Rectangle r = new Rectangle(rx, ry, rw, rh);
        if (r.contains(x, y)) {
            clicked = false;   // 🔥 VERY IMPORTANT
            return true;
        }
        return false;
    }
}
