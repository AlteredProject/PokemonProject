package entity;

import main.GamePanel;

public class NPC_Female2 extends Entity {

    public NPC_Female2(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 3;

        getNPCImage();
        setDialogue();
    }

    public void getNPCImage() {
        up1 = setup("/npc/female_2/up_1");
        up2 = setup("/npc/female_2/up_2");
        up3 = setup("/npc/female_2/up_3");
        left1 = setup("/npc/female_2/left_1");
        left2 = setup("/npc/female_2/left_2");
        left3 = setup("/npc/female_2/left_3");
        down1 = setup("/npc/female_2/down_1");
        down2 = setup("/npc/female_2/down_2");
        down3 = setup("/npc/female_2/down_3");
        right1 = setup("/npc/female_2/right_1");
        right2 = setup("/npc/female_2/right_2");
        right3 = setup("/npc/female_2/right_3");
    }

    public void setDialogue() {
        dialogues[0] = "Such a lovely weather outside today \ndon't you think?";
        dialogues[1] = "My husband went to the beach at \nOpal Springs to fish for Magikarp";
        dialogues[2] = "But he has forgotten his fishing rod \nhere at home";
        dialogues[3] = "What should we eat for dinner then?";
    }

    public void speak() {
        if (dialogues[dialogueIndex] == dialogues[3]) {
            gp.player.keyH.ePressed = false;
        }
        super.speak();
    }
}
