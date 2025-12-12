package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Rock extends SuperObject {
    GamePanel gp;

    public OBJ_Rock(GamePanel gp) {
        this.gp = gp;

        name = "Rock";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/rocksmash.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
