package entity;

import main.GamePanel;

public class NPC_NurseJoy extends Entity {

    public NPC_NurseJoy(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 3;

        getNPCImage();
        setDialogue();
    }

    public void getNPCImage() {
        up1 = setup("/npc/nurse_joy/up_1");
        up2 = setup("/npc/nurse_joy/up_2");
        up3 = setup("/npc/nurse_joy/up_3");
        left1 = setup("/npc/nurse_joy/left_1");
        left2 = setup("/npc/nurse_joy/left_2");
        left3 = setup("/npc/nurse_joy/left_3");
        down1 = setup("/npc/nurse_joy/down_1");
        down2 = setup("/npc/nurse_joy/down_2");
        down3 = setup("/npc/nurse_joy/down_3");
        right1 = setup("/npc/nurse_joy/right_1");
        right2 = setup("/npc/nurse_joy/right_2");
        right3 = setup("/npc/nurse_joy/right_3");
    }

    public void setDialogue() {
        dialogues[0] = "Hi there! I'm Nurse Joy. \nAnd this is the Pokecenter.";
        dialogues[1] = "Normally you would heal your wounded \n or fainted pokemon here.";
        dialogues[2] = "but yesterday a group of Bidoofs \nravaged the place. So we had so close it.";
        dialogues[3] = "We're waiting for someone to come help \nus get rid of all the Bidoofs";
    }

    public void speak() {
        if (dialogues[dialogueIndex] == dialogues[3]) {
            gp.player.keyH.ePressed = false;
        }
        super.speak();
    }
}
