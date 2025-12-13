package entity;

import main.GamePanel;

public class NPC_Male5 extends Entity {

    public NPC_Male5(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 3;

        getNPCImage();
        setDialogue();
    }

    public void getNPCImage() {
        up1 = setup("/npc/male_5/up_1");
        up2 = setup("/npc/male_5/up_2");
        up3 = setup("/npc/male_5/up_3");
        left1 = setup("/npc/male_5/left_1");
        left2 = setup("/npc/male_5/left_2");
        left3 = setup("/npc/male_5/left_3");
        down1 = setup("/npc/male_5/down_1");
        down2 = setup("/npc/male_5/down_2");
        down3 = setup("/npc/male_5/down_3");
        right1 = setup("/npc/male_5/right_1");
        right2 = setup("/npc/male_5/right_2");
        right3 = setup("/npc/male_5/right_3");
    }

    public void setDialogue() {
        dialogues[0] = "My friend told me to meet her at the \nentrance of the forest.";
        dialogues[1] = "Here i am. \nBut shes nowhere to be seen.";
        dialogues[2] = "I've been waiting for hours.. \nDo you think she pranked me?";
    }

    public void speak() {
        if (dialogues[dialogueIndex] == dialogues[2]) {
            gp.player.keyH.ePressed = false;
        }
        super.speak();
    }
}
