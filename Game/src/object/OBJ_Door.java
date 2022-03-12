package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Door extends SuperObject{
	public OBJ_Door() {
		name = "Door";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/objects_sprite_sheet.png")).getSubimage(32, 0, 16, 16);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
