package main;

import entity.NPC_Female1;
import entity.NPC_Machop;
import entity.NPC_Male1;
import entity.NPC_Male2;
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
        gp.npc[0] = new NPC_Female1(gp);
        gp.npc[0].worldX = 23 * gp.tileSize;
        gp.npc[0].worldY = 39 * gp.tileSize;

        gp.npc[1] = new NPC_Female1(gp);
        gp.npc[1].worldX = 41 * gp.tileSize;
        gp.npc[1].worldY = 11 * gp.tileSize;

        gp.npc[2] = new NPC_Male1(gp);
        gp.npc[2].worldX = 60 * gp.tileSize;
        gp.npc[2].worldY = 90 * gp.tileSize;
        gp.npc[2].direction = "up";


        gp.npc[3] = new NPC_Machop(gp);
        gp.npc[3].worldX = 59 * gp.tileSize;
        gp.npc[3].worldY = 89 * gp.tileSize;
        gp.npc[3].direction = "right";

        gp.npc[4] = new NPC_Machop(gp);
        gp.npc[4].worldX = 70 * gp.tileSize;
        gp.npc[4].worldY = 84 * gp.tileSize;
        gp.npc[4].direction = "left";

        gp.npc[5] = new NPC_Machop(gp);
        gp.npc[5].worldX = 85 * gp.tileSize;
        gp.npc[5].worldY = 93 * gp.tileSize;

        gp.npc[6] = new NPC_Male2(gp);
        gp.npc[6].worldX = 84 * gp.tileSize;
        gp.npc[6].worldY = 88 * gp.tileSize;


    }
}
