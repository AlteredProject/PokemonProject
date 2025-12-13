package entity;

import main.GamePanel;

public class NPC_Female4 extends Entity {

    public NPC_Female4(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 3;

        getNPCImage();
        setDialogue();
    }

    public void getNPCImage() {
        up1 = setup("/npc/female_4/up_1");
        up2 = setup("/npc/female_4/up_2");
        up3 = setup("/npc/female_4/up_3");
        left1 = setup("/npc/female_4/left_1");
        left2 = setup("/npc/female_4/left_2");
        left3 = setup("/npc/female_4/left_3");
        down1 = setup("/npc/female_4/down_1");
        down2 = setup("/npc/female_4/down_2");
        down3 = setup("/npc/female_4/down_3");
        right1 = setup("/npc/female_4/right_1");
        right2 = setup("/npc/female_4/right_2");
        right3 = setup("/npc/female_4/right_3");
    }

    public void setDialogue() {
        dialogues[0] = "Hi there, im miss Stone. \nI'm what you would call a Geologist";
        dialogues[1] = "I study rocks, like this Geodude here.";
        dialogues[2] = "What? you're telling me this \nisn't a Geodude but just a normal rock?";
        dialogues[3] = "You think i don't know the difference \nbetween a rock and a Geodude?";
        dialogues[4] = "There's a reason i'm a geologist \nand you aren't.";
        dialogues[5] = "It's clearly just, uh.. \nsleeping. Yeah, sleeping..";
    }

    public void speak() {
        if (dialogues[dialogueIndex] == dialogues[5]) {
            gp.player.keyH.ePressed = false;
        }
        super.speak();
    }
}
