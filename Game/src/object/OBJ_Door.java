package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Door extends SuperObject{
	
	GamePanel gamePanel;
	
	public OBJ_Door(GamePanel gamePanel) {
		name = "Door";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/objects_sprite_sheet.png")).getSubimage(32, 0, 16, 16);
			uTool.scaledImage(image, gamePanel.tileSize, gamePanel.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		collision = true;
	}
}
