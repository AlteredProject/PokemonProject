package entity;

import main.GamePanel;

public class NPC_Machop extends Entity {
    public NPC_Machop (GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 3;

        getNPCImage();
    }

    public void getNPCImage() {
        down1 = setup("/npc/machop/down_1");
        left1 = setup("/npc/machop/left_1");
    }
}
