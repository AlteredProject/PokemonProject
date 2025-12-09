package main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickHandler implements MouseListener {
    public boolean clicked;
    private int count;
    private int x;
    private int y;

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (count == 1) {
                clicked = false;
                count = 0;
            } else {
                clicked = true;
                count++;
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
