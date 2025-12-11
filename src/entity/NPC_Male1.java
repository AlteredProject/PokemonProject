package entity;

import main.GamePanel;

public class NPC_Male1 extends Entity {

    public NPC_Male1(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 3;

        getNPCImage();
        setDialogue();
    }

    public void getNPCImage() {
        up1 = setup("/npc/male_1/up_1");
        up2 = setup("/npc/male_1/up_2");
        up3 = setup("/npc/male_1/up_3");
        left1 = setup("/npc/male_1/left_1");
        left2 = setup("/npc/male_1/left_2");
        left3 = setup("/npc/male_1/left_3");
        down1 = setup("/npc/male_1/down_1");
        down2 = setup("/npc/male_1/down_2");
        down3 = setup("/npc/male_1/down_3");
        right1 = setup("/npc/male_1/right_1");
        right2 = setup("/npc/male_1/right_2");
        right3 = setup("/npc/male_1/right_3");
    }

    public void setDialogue() {
        dialogues[0] = "Look at my Machop go!";
        dialogues[1] = "They're really useful for moving rocks, \nsince they're stronger than us humans";
        dialogues[2] = "Sometimes i wish i was a Machop..\nThen i'd be super strong!";
    }

    public void speak() {
        if (dialogues[dialogueIndex] == dialogues[2]) {
            gp.player.keyH.ePressed = false;
        }
        super.speak();
    }
}
