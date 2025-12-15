package entity;

import main.GamePanel;

public class NPC_Prof extends Entity {

    public NPC_Prof(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 3;

        getNPCImage();
        setDialogue();
    }

    public void getNPCImage() {
        up1 = setup("/npc/prof_peter/up_1");
        up2 = setup("/npc/prof_peter/up_2");
        up3 = setup("/npc/prof_peter/up_3");
        left1 = setup("/npc/prof_peter/left_1");
        left2 = setup("/npc/prof_peter/left_2");
        left3 = setup("/npc/prof_peter/left_3");
        down1 = setup("/npc/prof_peter/down_1");
        down2 = setup("/npc/prof_peter/down_2");
        down3 = setup("/npc/prof_peter/down_3");
        right1 = setup("/npc/prof_peter/right_1");
        right2 = setup("/npc/prof_peter/right_2");
        right3 = setup("/npc/prof_peter/right_3");
    }

    public void setDialogue() {
        dialogues[0] = "Ah, there you are, i was wondering when \nyou would show up.";
        dialogues[1] = "Anyways, i've talked to your mother \nabout you wanting to learn about pokemon";
        dialogues[2] = "She hesitated at first, but has since \ndecided that you are old enough.";
        dialogues[3] = "I can therefore with great pride award \nyou with your first ever pokemon!";
        dialogues[4] = "But before i do, i just wanna show you \nthis video i took of my Glameow";
        dialogues[5] = "Isn't she adorable? look how she \nfetches the stick for me!";
        dialogues[6] = "Oh..";
        dialogues[7] = "Right..";
        dialogues[8] = "Silly me, going on about my Glameow again. \nYou're here for a pokemon";
        dialogues[9] = "Please pick one of the pokemon \nyou see before you.";
        dialogues[10] = "";
    }

    public void speak() {
        if (dialogues[dialogueIndex] == dialogues[10]) {
            gp.gameState = gp.starterChoiceState;
            gp.player.keyH.ePressed = false;
        }
        super.speak();
    }
}
