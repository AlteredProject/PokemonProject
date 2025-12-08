package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Pokeball extends SuperObject {
    public OBJ_Pokeball() {
        name = "Pokeball";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/pokeball.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
