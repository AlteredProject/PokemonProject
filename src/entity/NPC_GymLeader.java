package entity;

import battleSystem.Battle;
import main.ClickHandler;
import main.GamePanel;
import pokedex.Pokemon;



public class NPC_GymLeader extends Entity {
    ClickHandler clickH;

    public NPC_GymLeader(GamePanel gp, ClickHandler clickH) {
        super(gp);
        this.clickH = clickH;

        direction = "down";
        speed = 3;

        getNPCImage();
        setDialogue();
    }

    public void getNPCImage() {
        up1 = setup("/npc/gym_leader/up_1");
        up2 = setup("/npc/gym_leader/up_2");
        up3 = setup("/npc/gym_leader/up_3");
        left1 = setup("/npc/gym_leader/left_1");
        left2 = setup("/npc/gym_leader/left_2");
        left3 = setup("/npc/gym_leader/left_3");
        down1 = setup("/npc/gym_leader/down_1");
        down2 = setup("/npc/gym_leader/down_2");
        down3 = setup("/npc/gym_leader/down_3");
        right1 = setup("/npc/gym_leader/right_1");
        right2 = setup("/npc/gym_leader/right_2");
        right3 = setup("/npc/gym_leader/right_3");
    }

    public void setDialogue() {
        dialogues[0] = "Have you come to challenge me?";
        dialogues[1] = "That's quite brave of you kid.";
        dialogues[2] = "Are you ready for a battle against \nthe strongest guy in this town?!";
        dialogues[3] = "";
    }

    public void speak() {
        if (dialogues[dialogueIndex] == dialogues[3]) {
            gp.player.keyH.ePressed = false;
            gp.startGymBattle();
        }
        super.speak();

    }
}
