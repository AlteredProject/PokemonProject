package main;

import entity.*;
import object.OBJ_Pokeball;
import object.OBJ_Rock;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new OBJ_Pokeball(gp);
        gp.obj[0].worldX = 18 * gp.tileSize;
        gp.obj[0].worldY = 35 * gp.tileSize;
        gp.obj[0].collision = true;

        gp.obj[1] = new OBJ_Pokeball(gp);
        gp.obj[1].worldX = 41 * gp.tileSize;
        gp.obj[1].worldY = 5 * gp.tileSize;
        gp.obj[1].collision = true;

        gp.obj[2] = new OBJ_Pokeball(gp);
        gp.obj[2].worldX = 91 * gp.tileSize;
        gp.obj[2].worldY = 31 * gp.tileSize;
        gp.obj[0].collision = true;

        gp.obj[3] = new OBJ_Pokeball(gp);
        gp.obj[3].worldX = 31 * gp.tileSize;
        gp.obj[3].worldY = 62 * gp.tileSize;
        gp.obj[3].collision = true;

        gp.obj[4] = new OBJ_Pokeball(gp);
        gp.obj[4].worldX = 7 * gp.tileSize;
        gp.obj[4].worldY = 71 * gp.tileSize;
        gp.obj[4].collision = true;


        gp.obj[5] = new OBJ_Rock(gp);
        gp.obj[5].worldX = 86 * gp.tileSize;
        gp.obj[5].worldY = 89 * gp.tileSize;
        gp.obj[5].collision = true;
    }


    public void setNPC() {
        gp.npc[0] = new NPC_Prof(gp);
        gp.npc[0].worldX = 12 * gp.tileSize;
        gp.npc[0].worldY = 40 * gp.tileSize;
        gp.npc[0].direction = "up";

        gp.npc[1] = new NPC_Female1(gp);
        gp.npc[1].worldX = 23 * gp.tileSize;
        gp.npc[1].worldY = 39 * gp.tileSize;

        gp.npc[2] = new NPC_Female2(gp);
        gp.npc[2].worldX = 9 * gp.tileSize;
        gp.npc[2].worldY = 47 * gp.tileSize;

        gp.npc[3] = new NPC_Female3(gp);
        gp.npc[3].worldX = 52 * gp.tileSize;
        gp.npc[3].worldY = 8 * gp.tileSize;

        gp.npc[4] = new NPC_Male6(gp);
        gp.npc[4].worldX = 5 * gp.tileSize;
        gp.npc[4].worldY = 12 * gp.tileSize;
        gp.npc[4].direction = "left";

        gp.npc[5] = new NPC_Male4(gp);
        gp.npc[5].worldX = 19 * gp.tileSize;
        gp.npc[5].worldY = 12 * gp.tileSize;
        gp.npc[5].direction = "up";

        gp.npc[6] = new NPC_Female5(gp);
        gp.npc[6].worldX = 71 * gp.tileSize;
        gp.npc[6].worldY = 9 * gp.tileSize;

        gp.npc[7] = new NPC_Male3(gp);
        gp.npc[7].worldX = 90 * gp.tileSize;
        gp.npc[7].worldY = 7 * gp.tileSize;
        gp.npc[7].direction = "right";

        gp.npc[8] = new NPC_Male5(gp);
        gp.npc[8].worldX = 87 * gp.tileSize;
        gp.npc[8].worldY = 43 * gp.tileSize;

        gp.npc[9] = new NPC_Female4(gp);
        gp.npc[9].worldX = 51 * gp.tileSize;
        gp.npc[9].worldY = 63 * gp.tileSize;
        gp.npc[9].direction = "up";

        gp.npc[10] = new NPC_Female6(gp);
        gp.npc[10].worldX = 26 * gp.tileSize;
        gp.npc[10].worldY = 76 * gp.tileSize;

        gp.npc[11] = new NPC_Female6(gp);
        gp.npc[11].worldX = 25 * gp.tileSize;
        gp.npc[11].worldY = 76 * gp.tileSize;

        gp.npc[12] = new NPC_GymLeader(gp);
        gp.npc[12].worldX = 11 * gp.tileSize;
        gp.npc[12].worldY = 77 * gp.tileSize;

        gp.npc[13] = new NPC_NurseJoy(gp);
        gp.npc[13].worldX = 11 * gp.tileSize;
        gp.npc[13].worldY = 86 * gp.tileSize;


        gp.npc[14] = new NPC_Male1(gp);
        gp.npc[14].worldX = 60 * gp.tileSize;
        gp.npc[14].worldY = 90 * gp.tileSize;
        gp.npc[14].direction = "up";


        gp.npc[15] = new NPC_Machop(gp);
        gp.npc[15].worldX = 59 * gp.tileSize;
        gp.npc[15].worldY = 89 * gp.tileSize;
        gp.npc[15].direction = "right";

        gp.npc[16] = new NPC_Machop(gp);
        gp.npc[16].worldX = 70 * gp.tileSize;
        gp.npc[16].worldY = 84 * gp.tileSize;
        gp.npc[16].direction = "left";

        gp.npc[17] = new NPC_Machop(gp);
        gp.npc[17].worldX = 85 * gp.tileSize;
        gp.npc[17].worldY = 93 * gp.tileSize;

        gp.npc[18] = new NPC_Male2(gp);
        gp.npc[18].worldX = 84 * gp.tileSize;
        gp.npc[18].worldY = 88 * gp.tileSize;
    }
}
