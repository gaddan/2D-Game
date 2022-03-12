package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_key extends SuperObject{
	public OBJ_key() {
		name = "Key";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/objects_sprite_sheet.png")).getSubimage(0, 0, 16, 16);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
