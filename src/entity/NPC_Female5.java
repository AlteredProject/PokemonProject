package entity;

import main.GamePanel;

public class NPC_Female5 extends Entity {

    public NPC_Female5(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 3;

        getNPCImage();
        setDialogue();
    }

    public void getNPCImage() {
        up1 = setup("/npc/female_5/up_1");
        up2 = setup("/npc/female_5/up_2");
        up3 = setup("/npc/female_5/up_3");
        left1 = setup("/npc/female_5/left_1");
        left2 = setup("/npc/female_5/left_2");
        left3 = setup("/npc/female_5/left_3");
        down1 = setup("/npc/female_5/down_1");
        down2 = setup("/npc/female_5/down_2");
        down3 = setup("/npc/female_5/down_3");
        right1 = setup("/npc/female_5/right_1");
        right2 = setup("/npc/female_5/right_2");
        right3 = setup("/npc/female_5/right_3");
    }

    public void setDialogue() {
        dialogues[0] = "I told my friend to come meet me \nat the entrance of the forest";
        dialogues[1] = "but i've been waiting here for hours, \nand he's still not here.";
        dialogues[2] = "Do you think he forgot? \nor maybe he doesn't like me..";
    }

    public void speak() {
        if (dialogues[dialogueIndex] == dialogues[2]) {
            gp.player.keyH.ePressed = false;
        }
        super.speak();
    }
}
