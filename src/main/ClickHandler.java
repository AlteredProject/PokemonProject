package main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickHandler implements MouseListener {
    GamePanel gp;
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
        if (mousePressedBox(40, 696, 44, 58)) {
           gp.switchPokedexStatus();
                if (gp.gameState != gp.pokedexState) {
                    gp.gameState = gp.pokedexState;
                } else if (gp.gameState == gp.pokedexState) {
                    gp.gameState = gp.playState;
                }
        }

        if (mousePressedBox(225,575,196,86)){
            if (gp.gameState == gp.pokedexState) {
                if (gp.gameState == gp.pokedexState) {
                    gp.gameState = gp.pokedexSearchState;
                } else if (gp.gameState == gp.pokedexSearchState) {
                    gp.gameState = gp.pokedexState;
                }
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
