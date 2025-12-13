package entity;

import main.GamePanel;

public class NPC_Female3 extends Entity {

    public NPC_Female3(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 3;

        getNPCImage();
        setDialogue();
    }

    public void getNPCImage() {
        up1 = setup("/npc/female_3/up_1");
        up2 = setup("/npc/female_3/up_2");
        up3 = setup("/npc/female_3/up_3");
        left1 = setup("/npc/female_3/left_1");
        left2 = setup("/npc/female_3/left_2");
        left3 = setup("/npc/female_3/left_3");
        down1 = setup("/npc/female_3/down_1");
        down2 = setup("/npc/female_3/down_2");
        down3 = setup("/npc/female_3/down_3");
        right1 = setup("/npc/female_3/right_1");
        right2 = setup("/npc/female_3/right_2");
        right3 = setup("/npc/female_3/right_3");
    }

    public void setDialogue() {
        dialogues[0] = "Oh hello there dear, have you come \nto see my pretty flowers?";
        dialogues[1] = "I've been growing them for as long \nas i can remember.";
        dialogues[2] = "You're welcome to look around, \nbut please don't tramble any of them.";
    }

    public void speak() {
        if (dialogues[dialogueIndex] == dialogues[2]) {
            gp.player.keyH.ePressed = false;
        }
        super.speak();
    }
}
