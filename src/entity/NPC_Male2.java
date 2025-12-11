package entity;

import main.GamePanel;

public class NPC_Male2 extends Entity {

    public NPC_Male2(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 3;

        getNPCImage();
        setDialogue();
    }

    public void getNPCImage() {
        up1 = setup("/npc/male_2/up_1");
        up2 = setup("/npc/male_2/up_2");
        up3 = setup("/npc/male_2/up_3");
        left1 = setup("/npc/male_2/left_1");
        left2 = setup("/npc/male_2/left_2");
        left3 = setup("/npc/male_2/left_3");
        down1 = setup("/npc/male_2/down_1");
        down2 = setup("/npc/male_2/down_2");
        down3 = setup("/npc/male_2/down_3");
        right1 = setup("/npc/male_2/right_1");
        right2 = setup("/npc/male_2/right_2");
        right3 = setup("/npc/male_2/right_3");
    }

    public void setDialogue() {
        dialogues[0] = "A recent rockslide has blocked the \nentrance to the cave";
        dialogues[1] = "and no human is strong enough to move \nor break those rocks";
        dialogues[2] = "So unless you have the move Rock Smash,\ni suggest you turn back.";
    }

    public void speak() {
        if (dialogues[dialogueIndex] == dialogues[2]) {
            gp.player.keyH.ePressed = false;
        }
        super.speak();
    }
}
