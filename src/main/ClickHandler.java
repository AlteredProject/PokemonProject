package main;

import pokedex.InteractiveButton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickHandler implements MouseListener {
    GamePanel gp;
    InteractiveButton interactiveButton;
    public boolean clicked;
    private int count;
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
        if (mousePressedBox(40, 696, 44, 58)) {
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
                gp.button.isSearching = true;
                gp.button.drawTimer = gp.button.drawDuration;
                gp.repaint();

                new Thread(() -> {
                    gp.pokedex.searchForPokemon();
                }).start();
            }
        }
        //Pressed on Pokedex left button
        if (mousePressedBox(190, 576, 45, 45)) {
            if (gp.gameState == gp.pokedexState) {
                gp.button.isDirectionLeft = true;
                gp.button.drawTimer = gp.button.drawDuration;
                gp.repaint();

                new Thread(() -> {
                    // Søg efter forrige pokemon metode insættes her
                }).start();
            }
        }
        //Pressed on Pokedex right button
        if (mousePressedBox(398, 576, 45, 45)) {
            if (gp.gameState == gp.pokedexState) {
                gp.button.isDirectionRight = true;
                gp.button.drawTimer = gp.button.drawDuration;
                gp.repaint();

                new Thread(() -> {
                    // Søg efter næste pokemon metode insættes her
                }).start();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

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

    public int getCount() {
        return count;
    }
}
