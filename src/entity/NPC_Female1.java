package entity;

import main.GamePanel;

public class NPC_Female1 extends Entity {

    public NPC_Female1(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 3;

        getNPCImage();
    }

    public void getNPCImage() {
        up1 = setup("/npc/female_1/up_1");
        up2 = setup("/npc/female_1/up_2");
        up3 = setup("/npc/female_1/up_3");
        left1 = setup("/npc/female_1/left_1");
        left2 = setup("/npc/female_1/left_2");
        left3 = setup("/npc/female_1/left_3");
        down1 = setup("/npc/female_1/down_1");
        down2 = setup("/npc/female_1/down_2");
        down3 = setup("/npc/female_1/down_3");
        right1 = setup("/npc/female_1/right_1");
        right2 = setup("/npc/female_1/right_2");
        right3 = setup("/npc/female_1/right_3");
    }
}
