package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Key extends SuperObject{
	
	GamePanel gamePanel;
	
	public OBJ_Key(GamePanel gamePanel) {
		name = "Key";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/objects_sprite_sheet.png")).getSubimage(0, 0, 16, 16);
			uTool.scaledImage(image, gamePanel.tileSize, gamePanel.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
