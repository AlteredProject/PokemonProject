package entity;

import main.GamePanel;

public class NPC_Male6 extends Entity {

    public NPC_Male6(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 3;

        getNPCImage();
        setDialogue();
    }

    public void getNPCImage() {
        up1 = setup("/npc/male_6/up_1");
        up2 = setup("/npc/male_6/up_2");
        up3 = setup("/npc/male_6/up_3");
        left1 = setup("/npc/male_6/left_1");
        left2 = setup("/npc/male_6/left_2");
        left3 = setup("/npc/male_6/left_3");
        down1 = setup("/npc/male_6/down_1");
        down2 = setup("/npc/male_6/down_2");
        down3 = setup("/npc/male_6/down_3");
        right1 = setup("/npc/male_6/right_1");
        right2 = setup("/npc/male_6/right_2");
        right3 = setup("/npc/male_6/right_3");
    }

    public void setDialogue() {
        dialogues[0] = "It is such a lovely day to catch some \nMagikarp for dinner i thought.";
        dialogues[1] = "But of course i went and forgot my \nfishing rod at home..";
        dialogues[2] = "My wife is gonna be so mad when i \ncome home without dinner...";
    }

    public void speak() {
        if (dialogues[dialogueIndex] == dialogues[2]) {
            gp.player.keyH.ePressed = false;
        }
        super.speak();
    }
}
