package entity;

import main.GamePanel;

import java.util.Objects;

public class NPC_Machop extends Entity {
    public NPC_Machop (GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 3;

        getNPCImage();
        setDialogue();
    }

    public void getNPCImage() {
        up1 = setup("/npc/machop/up_1");
        down1 = setup("/npc/machop/down_1");
        left1 = setup("/npc/machop/left_1");
        right1 = setup("/npc/machop/right_1");

    }

    public void setDialogue() {
        dialogues[0] = "MACHOP: Gwooh! Gogogooh!";
        dialogues[1] = "MACHOP: Gwagooh! Gwogaah!";
    }

    public void speak() {
        if (Objects.equals(dialogues[dialogueIndex], dialogues[1])) {
            gp.player.keyH.ePressed = false;
        }
        super.speak();
    }
}
