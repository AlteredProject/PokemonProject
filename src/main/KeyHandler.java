package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, leftPressed, downPressed, rightPressed, shiftPressed, pPressed, enterPressed, ePressed, bPressed, spacePressed;
    private int count = 0;
    private final int MAX_INPUT_LENGTH = 15;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

        // USER INPUT POKEDEX
        if (gp.gameState == gp.pokedexState && gp.ui.drawingInput) {
            char key = e.getKeyChar();
            if (Character.isLetterOrDigit(key) || key == ' ') {
                if (gp.ui.inputBuffer.length() < MAX_INPUT_LENGTH) {
                    gp.ui.inputBuffer += key;
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // SEARCH
        if (gp.gameState == gp.pokedexState && gp.ui.drawingInput) {
            if (code == KeyEvent.VK_BACK_SPACE) {
                if (gp.ui.inputBuffer.length() > 0) {
                    gp.ui.inputBuffer = gp.ui.inputBuffer.substring(0, gp.ui.inputBuffer.length() - 1);
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                String input = gp.ui.inputBuffer.trim();

                gp.ui.inputBuffer = "";
                gp.ui.drawingInput = false;

                if (!input.isEmpty()) {
                    gp.pokedex.search(input);
                }
                gp.requestFocusInWindow();
            }
            return;
        }

        // TITLE SCREEN STATE
        if (gp.gameState == gp.titleScreenState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
        }

        // PLAY STATE
        if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_B){
                bPressed = true;
            }
            if (code == KeyEvent.VK_E) {
                ePressed = true;
            }
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.pokedexState;
            }
            if (code == KeyEvent.VK_SHIFT) {
                shiftPressed = true;
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.pauseState;
            }
        }

        // PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }
        }

        // DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
                gp.buttonSound.playButtonSound();
                gp.gameState = gp.playState;
            }
        }

        // POKEDEX STATE
        else if (gp.gameState == gp.pokedexState) {
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }
        }

        // BATTLE STATE
        else if (gp.gameState == gp.battleState){
            //
            if (code == KeyEvent.VK_SPACE){
                spacePressed = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_B){
            bPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_SHIFT) {
            shiftPressed = false;
        }
        if (code == KeyEvent.VK_SPACE){
            spacePressed = false;
        }
    }
}